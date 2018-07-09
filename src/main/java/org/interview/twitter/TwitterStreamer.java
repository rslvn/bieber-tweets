/**
 * 
 */
package org.interview.twitter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.interview.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

/**
 * This class is responsible for starting and stopping streaming process for
 * criterias. The criterias are read from configuration file and validated here.
 * 
 * @author resulav
 *
 */
@Component
public class TwitterStreamer {

	private static final Logger LOG = LoggerFactory.getLogger(TwitterStreamer.class);

	@Autowired
	private TwitterService twitterService;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private Executor executor;

	@Value("${message.track}")
	private String messageTrack;

	@Value("${message.max.count}")
	private int maxMessageCount;

	@Value("${message.streaming.max.duration}")
	private int maxStreamingDuration;

	private CountDownLatch latch;
	private StatusListener listener;

	@PostConstruct
	public void init() {
		Assert.hasText(messageTrack, "Configuration issue: messageTrack can not be empty");
		Assert.state(maxMessageCount > 0, "Configuration issue: maxMessageCount should be positive");
		Assert.state(maxStreamingDuration > 0, "Configuration issue: maxStreamingDuration should be positive");
		LOG.info("{} initialized", getClass().getName());
	}

	/**
	 * starts streaming for given track value, maxMessageCount and maxStreamingDuration
	 * 
	 * @return elapsed time for streaming
	 * 
	 * @throws InterruptedException
	 */
	public long startStreaming() throws InterruptedException {
		createCountDownLatch();
		AtomicInteger counter = new AtomicInteger(0);
		StatusListener statusListener = getStatusListener(latch, counter);
		FilterQuery trackFilterQuery = createTrackerFilterQuery(messageTrack);

		TwitterStream twitterStream = twitterService.createTwitterStream(statusListener);

		StopWatch sw = new StopWatch();
		sw.start();

		twitterStream.filter(trackFilterQuery);

		// wait maximum maxStreamingDuration seconds
		boolean stopped = latch.await(maxStreamingDuration, TimeUnit.SECONDS);
		sw.stop();

		destroyTwitterStream(twitterStream, statusListener);

		LOG.info("twitterStream destroyed:{} elapsed time: {}, tweet count: {}", stopped, sw.getTotalTimeMillis(),
				counter.get());

		return sw.getTotalTimeMillis();
	}

	/**
	 * creates a CountDownLatch with configured maxMessageCount
	 * 
	 * @return
	 */
	public void createCountDownLatch() {
		if (latch != null) {
			return;
		}
		latch = new CountDownLatch(maxMessageCount);
	}

	/**
	 * creates track filter with given values
	 * 
	 * @param filters
	 * @return
	 */
	private FilterQuery createTrackerFilterQuery(String... filters) {
		FilterQuery tweetFilterQuery = new FilterQuery();
		tweetFilterQuery.track(filters);

		return tweetFilterQuery;
	}

	/**
	 * removes the given listener from twitterStream and then shutdowns it
	 * 
	 * @param twitterStream
	 * @param statusListener
	 */
	private void destroyTwitterStream(TwitterStream twitterStream, StatusListener statusListener) {
		LOG.info("Wait for destroying twitterStream");
		twitterStream.removeListener(statusListener);
		twitterStream.shutdown();
	}

	/**
	 * Streaming status listener for tracking values
	 * 
	 * @param latch   to handle max streaming message count
	 * @param counter the counter for received messages, this just for information
	 * 
	 * @return a Status listener instance
	 */
	public StatusListener getStatusListener(CountDownLatch latch, AtomicInteger counter) {
		if (listener != null) {
			return listener;
		}

		return new StatusListener() {

			public void onStatus(Status status) {
				LOG.info("StatusListener onStatus");
				latch.countDown();

				// maybe stream not stopped yet
				if (counter.incrementAndGet() <= maxMessageCount) {
					executor.execute(new TweetStatusTask(messageRepository, status));
				}
			}

			@Override
			public void onException(Exception ex) {
				LOG.error("StatusListener onException: ", ex);
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				LOG.info("StatusListener onDeletionNotice");

			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				LOG.info("StatusListener onTrackLimitationNotice");

			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				LOG.info("StatusListener onScrubGeo");

			}

			@Override
			public void onStallWarning(StallWarning warning) {
				LOG.info("StatusListener onStallWarning");

			}
		};
	}

}
