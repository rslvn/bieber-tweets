/**
 * 
 */
package org.interview.twitter;

import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class reads twitter parameters from configuration file validates them.
 * Prepare objects and parameters for streaming process
 * 
 * @author resulav
 *
 */
@Service
public class TwitterService {

	private static final Logger logger = LoggerFactory.getLogger(TwitterService.class);

	@Value("${twitter.consumer.key}")
	private String consumerKey;

	@Value("${twitter.consumer.secret}")
	private String consumerSecret;

	private Twitter twitter;
	private AccessToken accessToken;

	@PostConstruct
	public void init() {
		Assert.hasText(consumerKey, "Configuration issue: consumerKey can not be empty");
		Assert.hasText(consumerSecret, "Configuration issue: consumerSecret can not be empty");
		logger.info("{} initialized", getClass().getName());
	}

	/**
	 * create an access token for an authorized via Twitter API. keep the
	 * accessToken for the streaming process
	 * 
	 * @throws TwitterException
	 */
	public void setAccessToken() throws TwitterException {
		// Get the root twitter object
		createTwitter();

		RequestToken requestToken = twitter.getOAuthRequestToken();
		String pin = readPIN(requestToken);

		accessToken = twitter.getOAuthAccessToken(requestToken, pin);
		twitter.setOAuthAccessToken(accessToken);

		logger.info("Received Token key: {} secret: {}", accessToken.getToken(), accessToken.getTokenSecret());
	}

	/**
	 * create Twitter instance if not exist
	 */
	private void createTwitter() {
		// This method for unit test
		if (twitter != null) {
			return;
		}

		// Get the root twitter object
		twitter = TwitterFactory.getSingleton();
		// Set up the access tokens and keys to get permission to access
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
	}

	/**
	 * Read pin code from console
	 * 
	 * @param requestToken
	 * @return
	 */
	private String readPIN(RequestToken requestToken) {
		String pin = null;
		Scanner scanner = new Scanner(System.in);
		try {
			logger.info("\nGo to the following link in your browser:\n{}\n", requestToken.getAuthorizationURL());
			logger.info("\nPlease enter the retrieved PIN:");
			pin = scanner.next();
		} finally {
			scanner.close();
		}
		Assert.notNull(pin, "Unable to read entered PIN");
		return pin;
	}

	/**
	 * create a TwitterStreamer for streaming process
	 * 
	 * @param statusListener
	 * @return
	 */
	public TwitterStream createTwitterStream(StatusListener statusListener) {
		// Validations
		Assert.notNull(statusListener, "statusListener can not be null");
		Assert.notNull(accessToken, "accessToken can not be null");

		// Twitter Stream configuration
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken.getToken()).setOAuthAccessTokenSecret(accessToken.getTokenSecret());

		TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
		twitterStream.addListener(statusListener);

		return twitterStream;
	}
}
