/**
 * 
 */
package org.interview;

import java.io.File;

import org.interview.repository.MessageRepository;
import org.interview.twitter.TwitterService;
import org.interview.twitter.TwitterStreamer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @author resulav
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("dev")
public class ApplicationTest {

	@MockBean
	private TwitterService twitterService;

	@MockBean
	private TwitterStreamer twitterStreamer;

	@InjectMocks
	private Application application;

	@Autowired
	MessageRepository messageRepository;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void contextLoads() throws Exception {
		Assert.notNull(messageRepository, "messageRepository not inialized");
		Assert.isTrue(new File("./output/result.json").exists(), "output file does not exist");
	}

}
