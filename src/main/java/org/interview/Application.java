/**
 * 
 */
package org.interview;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.interview.model.Message;
import org.interview.repository.MessageRepository;
import org.interview.twitter.TwitterService;
import org.interview.twitter.TwitterStreamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.google.gson.GsonBuilder;

/**
 * Application main
 * 
 * @author resulav
 *
 */
@SpringBootApplication
@Profile(value = { "dev", "default" })
public class Application implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(TwitterStreamer.class);

	@Autowired
	private TwitterService twitterService;

	@Autowired
	private TwitterStreamer twitterStreamer;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private Environment environment;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Executor bean for processing streamed messages
	 * 
	 * @return
	 */
	@Bean
	public Executor executor() {
		ThreadFactory factory = new CustomizableThreadFactory("bieber-pool-");
		return Executors.newFixedThreadPool(1, factory);
	}

	@Override
	public void run(String... args) throws Exception {
		// authorize by a user, get pin and receive accesstoken
		twitterService.setAccessToken();

		// streaming
		long elapsedTime = twitterStreamer.startStreaming();

		// prepare sorted message list
		List<Message> messageList = messageRepository.getSortedMessageList();

		// prepare a JSON formatted output data and write it in a file.
		String output = new GsonBuilder().setPrettyPrinting().create().toJson(messageList);
		Files.write(Paths.get(AppConstant.OUTPUT_FILE_NAME), output.getBytes(), StandardOpenOption.CREATE);

		LOG.info(
				"FINISHED! elapsedTime: {}, received message count:{}. Run the following command to see the received messages",
				elapsedTime, messageList.size());
		LOG.info("\nRun: cat {}\n", AppConstant.OUTPUT_FILE_NAME);

		// don't exit in dev profile
		boolean isDevProfile = environment.acceptsProfiles("dev");
		if (!isDevProfile) {
			System.exit(0);
		}
	}
}
