/**
 * 
 */
package org.interview.twitter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.interview.TestUtils;
import org.interview.repository.MessageRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StopWatch;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

/**
 * @author resulav
 *
 */
@RunWith(SpringRunner.class)
public class TwitterStreamerTest {

	@Mock
	private TwitterService twitterService;

	@Mock
	private MessageRepository messageRepository;

	@Mock
	private TwitterStream twitterStream;

	@Mock
	private Executor executor;

	@InjectMocks
	private TwitterStreamer twitterStreamer;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * StartStreaming test
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStartStreaming() throws Exception {
		Mockito.when(twitterService.createTwitterStream(Mockito.any(StatusListener.class))).thenReturn(twitterStream);
		Mockito.doNothing().when(twitterStream).filter(Mockito.any(FilterQuery.class));
		// no exception means passed
		twitterStreamer.startStreaming();
	}

	/**
	 * test for stop If streaming duration reaches maxStreamingDuration
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStartStreamingMaxStreamingDuration() throws Exception {
		int maxStreamingDuration = 1;
		CountDownLatch latch = new CountDownLatch(5);
		ReflectionTestUtils.setField(twitterStreamer, "maxMessageCount", 5);
		ReflectionTestUtils.setField(twitterStreamer, "maxStreamingDuration", maxStreamingDuration);
		ReflectionTestUtils.setField(twitterStreamer, "latch", latch);

		Mockito.when(twitterService.createTwitterStream(Mockito.any(StatusListener.class))).thenReturn(twitterStream);
		Mockito.doNothing().when(twitterStream).filter(Mockito.any(FilterQuery.class));
		StopWatch sw = new StopWatch();
		sw.start();
		// no exception means passed
		twitterStreamer.startStreaming();
		sw.stop();

		Assert.assertTrue("Elapsed time is smaller", sw.getTotalTimeMillis() > maxStreamingDuration * 1000);
		Assert.assertTrue("Elapsed time is greater", sw.getTotalTimeMillis() < maxStreamingDuration * 1000 + 10);
	}

	/**
	 * test for stop If message count reaches maxMessageCount
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStartStreamingMaxCount() throws Exception {
		int maxMessageCount = 5;
		CountDownLatch latch = new CountDownLatch(maxMessageCount);
		AtomicInteger counter = new AtomicInteger(0);
		// create and set real Status listener
		StatusListener listener = twitterStreamer.getStatusListener(latch, counter);

		ReflectionTestUtils.setField(twitterStreamer, "maxMessageCount", maxMessageCount);
		ReflectionTestUtils.setField(twitterStreamer, "maxStreamingDuration", 1);
		ReflectionTestUtils.setField(twitterStreamer, "latch", latch);
		ReflectionTestUtils.setField(twitterStreamer, "listener", listener);

		Mockito.when(twitterService.createTwitterStream(Mockito.any(StatusListener.class))).thenReturn(twitterStream);
		Mockito.doNothing().when(twitterStream).filter(Mockito.any(FilterQuery.class));

		StopWatch sw = new StopWatch();
		sw.start();

		startDummyStreamer(listener, maxMessageCount);

		// no exception means passed
		twitterStreamer.startStreaming();
		sw.stop();

		Assert.assertTrue("Elapsed time is smaller", sw.getTotalTimeMillis() < 1000);
	}

	/**
	 * test for TwitterStreamer service init
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInit() throws Exception {
		ReflectionTestUtils.setField(twitterStreamer, "messageTrack", "bieber");
		ReflectionTestUtils.setField(twitterStreamer, "maxMessageCount", 100);
		ReflectionTestUtils.setField(twitterStreamer, "maxStreamingDuration", 30);
		// no exception means passed
		twitterStreamer.init();
	}

	/**
	 * test for empty messageTrack value
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitEmptyMessageTrack() throws Exception {
		ReflectionTestUtils.setField(twitterStreamer, "messageTrack", "");
		// no exception means passed
		twitterStreamer.init();
	}

	/**
	 * test for null messageTrack value
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitNullMessageTrack() throws Exception {
		// no exception means passed
		twitterStreamer.init();
	}

	/**
	 * test for invalid maxMessageCount value
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalStateException.class)
	public void testInitInvalidMessageCount() throws Exception {
		ReflectionTestUtils.setField(twitterStreamer, "messageTrack", "bieber");
		ReflectionTestUtils.setField(twitterStreamer, "maxMessageCount", 0);
		// no exception means passed
		twitterStreamer.init();
	}

	/**
	 * test for invalid maxStreamDuration value
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalStateException.class)
	public void testInitInvalidMaxStreaminDuration() throws Exception {
		ReflectionTestUtils.setField(twitterStreamer, "messageTrack", "bieber");
		ReflectionTestUtils.setField(twitterStreamer, "maxMessageCount", 100);
		ReflectionTestUtils.setField(twitterStreamer, "maxStreamingDuration", 0);
		// no exception means passed
		twitterStreamer.init();
	}

	/**
	 * Status listener test
	 * 
	 * @throws Exception
	 */
	@Test
	public void testListener() throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		AtomicInteger count = new AtomicInteger(0);
		StatusListener listener = twitterStreamer.getStatusListener(latch, count);
		listener.onStatus(TestUtils.dummyStatus());
		listener.onDeletionNotice(null);
		listener.onException(new RuntimeException("dummy Exception"));
		listener.onTrackLimitationNotice(100);
		listener.onScrubGeo(100, 100);
		listener.onStallWarning(null);

	}

	private void startDummyStreamer(StatusListener listener, int messageCount) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(() -> {
			try {
				// wait while streamer preparing
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < 5; i++) {
				listener.onStatus(TestUtils.dummyStatus(TestUtils.dummyUser(i)));
			}
		});
		executor.shutdown();
	}

}
