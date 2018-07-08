/**
 * 
 */
package org.interview.twitter;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.interview.TestUtils;
import org.interview.model.Message;
import org.interview.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import twitter4j.Status;
import twitter4j.User;

/**
 * @author resulav
 *
 */
@RunWith(SpringRunner.class)
public class TwitterStatusTaskTest {

	@Mock
	private MessageRepository messageRepository;

	private Executor executor = Executors.newFixedThreadPool(1);
	private long userId = 5l;
	private Status status;
	private User user;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(messageRepository.add(Mockito.any(Message.class))).thenReturn(true);
		user = TestUtils.dummyUser(userId);
		status = TestUtils.dummyStatus(user);
	}

	@Test
	public void testSuccessfully() {
		executor.execute(new TweetStatusTask(messageRepository, status));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullMessageRepository() {
		executor.execute(new TweetStatusTask(null, status));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullStatus() {
		executor.execute(new TweetStatusTask(messageRepository, null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullUser() {
		executor.execute(new TweetStatusTask(messageRepository, TestUtils.dummyStatus(null)));
	}

}
