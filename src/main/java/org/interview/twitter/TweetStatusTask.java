/**
 * 
 */
package org.interview.twitter;

import org.interview.model.Author;
import org.interview.model.Message;
import org.interview.repository.MessageRepository;
import org.springframework.util.Assert;

import twitter4j.Status;

/**
 * This class converts Status objects to Messages and puts it to repository
 * 
 * @author resulav
 *
 */
public class TweetStatusTask implements Runnable {

	private Status status;
	private MessageRepository messageRepository;

	/**
	 * Constructor for TweetStatusTask
	 * 
	 * @param messageRepository instance of messageRepository
	 * @param status            new status
	 */
	public TweetStatusTask(MessageRepository messageRepository, Status status) {
		Assert.notNull(messageRepository, "messageRepository can not be null");
		Assert.notNull(status, "status can not be null");
		Assert.notNull(status.getUser(), "status.user can not be null");
		this.status = status;
		this.messageRepository = messageRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Author author = new Author(status.getUser().getId(), status.getUser().getCreatedAt().getTime(),
				status.getUser().getName(), status.getUser().getScreenName());
		Message message = new Message(status.getId(), status.getCreatedAt().getTime(), status.getText(), author);

		messageRepository.add(message);
	}
}
