/**
 * 
 */
package org.interview;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private TwitterService twitterService;

	@Autowired
	private TwitterStreamer twitterStreamer;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private Environment environment;

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
		twitterService.setAccessToken();
		twitterStreamer.startStreaming();

		String output = messageRepository.getPrintableText();

		Files.write(Paths.get("./output.txt"), output.getBytes(), StandardOpenOption.CREATE);

		LOG.info("{}", output);

		boolean isDevProfile = environment.acceptsProfiles("dev");
		if (!isDevProfile) {
			System.exit(0);
		}
	}
}
