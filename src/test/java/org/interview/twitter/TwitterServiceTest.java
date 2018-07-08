/**
 * 
 */
package org.interview.twitter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.interview.TestConstants;
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

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.auth.RequestToken;

/**
 * @author resulav
 *
 */
@RunWith(SpringRunner.class)
public class TwitterServiceTest {

	@Mock
	TwitterFactory twitterFactory;

	@Mock
	private Twitter twitter;

	@InjectMocks
	TwitterService twitterService;

	private RequestToken requestToken = new RequestToken(TestConstants.TOKEN, TestConstants.TOKEN_SECRET);

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(twitter.getOAuthRequestToken()).thenReturn(requestToken);

		InputStream in = new ByteArrayInputStream(TestConstants.INPUT.getBytes());
		System.setIn(in);

		Mockito.when(twitter.getOAuthAccessToken(requestToken, TestConstants.INPUT))
				.thenReturn(TestConstants.ACCESS_TOKEN);
	}

	@Test
	public void testInit() throws Exception {
		ReflectionTestUtils.setField(twitterService, "consumerKey", "consumerKey");
		ReflectionTestUtils.setField(twitterService, "consumerSecret", "consumerSecret");
		// no exception means passed
		twitterService.init();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitEmptyConsumerKey() throws Exception {
		ReflectionTestUtils.setField(twitterService, "consumerKey", "");
		// no exception means passed
		twitterService.init();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitNullConsumerSecret() throws Exception {
		ReflectionTestUtils.setField(twitterService, "consumerKey", "consumerKey");
		// no exception means passed
		twitterService.init();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitEmptyConsumerSecret() throws Exception {
		ReflectionTestUtils.setField(twitterService, "consumerKey", "consumerKey");
		ReflectionTestUtils.setField(twitterService, "consumerSecret", "");
		// no exception means passed
		twitterService.init();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitNullConsumerKey() throws Exception {
		// no exception means passed
		twitterService.init();
	}

	/**
	 * test for SetAccessToken process
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSetAccessToken() throws Exception {
		// no exception means passed
		twitterService.setAccessToken();
	}

	/**
	 * test to create TwitterStream
	 */
	@Test
	public void testCreateTwitterStream() throws TwitterException {
		twitterService.setAccessToken();
		TwitterStream twitterStream = twitterService.createTwitterStream(new StatusListener() {

			@Override
			public void onException(Exception ex) {

			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

			}

			@Override
			public void onStatus(Status status) {

			}

			@Override
			public void onStallWarning(StallWarning warning) {

			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

			}
		});
		Assert.assertNotNull("twitterStream can not be null", twitterStream);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTwitterStreamNullListener() {
		twitterService.createTwitterStream(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTwitterStreamNullAccessToken() {
		twitterService.createTwitterStream(new StatusListener() {

			@Override
			public void onException(Exception ex) {

			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

			}

			@Override
			public void onStatus(Status status) {

			}

			@Override
			public void onStallWarning(StallWarning warning) {

			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

			}
		});
	}
}
