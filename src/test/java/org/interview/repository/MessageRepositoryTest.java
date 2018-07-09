/**
 * 
 */
package org.interview.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.interview.model.Author;
import org.interview.model.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author resulav
 *
 */
@RunWith(SpringRunner.class)
public class MessageRepositoryTest {

	@InjectMocks
	private MessageRepository messageRepository;

	private Message message1;
	private Message message2;
	private Message message3;

	private Author author1;
	private Author author2;

	private long id1 = 1l;
	private long id2 = 2l;
	private long id3 = 3l;

	private long epoch1 = System.currentTimeMillis();
	private long epoch2 = epoch1 - 10000;
	private long epoch3 = epoch1 - 20000;

	private String dummyName = "dummyName";
	private String dummyScreenName = "dummyScreenName";
	private String dummyText = "dummyText";

	private String errorMessageSize = "Message size mismatched";
	private String errorMessageValueSize = "Message size mismatched";
	private String errorMessageNotSame = "Messages not same";

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		author1 = new Author(id1, epoch1, dummyName, dummyScreenName);
		author2 = new Author(id2, epoch2, dummyName, dummyScreenName);

		message1 = new Message(id1, epoch1, dummyText, author1);
		message2 = new Message(id2, epoch2, dummyText, author2);
		message3 = new Message(id3, epoch3, dummyText, author2);

	}

	/**
	 * add a message to repository test
	 */
	@Test
	public void testAdd() {
		messageRepository.add(message1);
		assertEquals(errorMessageSize, 1, messageRepository.getMessageMap().size());
		assertEquals(errorMessageValueSize, message1,
				messageRepository.getMessageMap().values().iterator().next().get(0));
	}

	/**
	 * group by author and ascending chronologically sort test
	 */
	@Test
	public void testAddGroupByAuthor() {
		messageRepository.add(message1);
		messageRepository.add(message2);
		messageRepository.add(message3);
		assertEquals(errorMessageSize, 2, messageRepository.getMessageMap().size());

		// messages exist validation
		validate(author1, message1);
		validate(author2, message2, message3);

		// received sorted messages
		List<Message> messageList = messageRepository.getSortedMessageList();
		assertNotNull("messageList can not be null", messageList);

		// validate user and message chronologically, ascending order
		assertEquals(errorMessageSize, 3, messageList.size());
		assertEquals(errorMessageNotSame, message3, messageList.get(0));
		assertEquals(errorMessageNotSame, message1, messageList.get(2));
	}

	/**
	 * validation test for null message
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNullMessage() {
		messageRepository.add(null);
	}

	/**
	 * validation test for null epoch date of message
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNullMessageCreationDate() {
		messageRepository.add(new Message(id1, null, dummyText, author1));
	}

	/**
	 * validation test for null author date of message
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNullAuthor() {
		messageRepository.add(new Message(id1, epoch1, dummyText, null));
	}

	/**
	 * validation test for null epoch date date of author
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNullAuthorCreationDate() {
		Author author = new Author(id1, null, dummyName, dummyScreenName);
		messageRepository.add(new Message(id1, epoch1, dummyText, author));
	}

	/**
	 * validate method messages of the author
	 * 
	 * @param author   Author instance
	 * @param messages messages of the authors
	 */
	private void validate(Author author, Message... messages) {
		List<Message> messageList = messageRepository.getMessageMap().get(author);
		Assert.assertEquals(errorMessageValueSize, messages.length, messageList.size());

		for (Message message : messages) {
			Assert.assertTrue("Message is not exist in repository", messageList.contains(message));
		}

	}

}
