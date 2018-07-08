/**
 * 
 */
package org.interview.model;

/**
 * Model for Message objects whose are received from streaming process
 * 
 * @author resulav
 *
 */
public class Message {
	private Long messageId;
	private Long creationDate;
	private String text;
	private Author author;

	/**
	 * @param messageId id of the message
	 * @param creationDate creation date of the message
	 * @param text content of the message
	 * @param author owner of the message
	 */
	public Message(Long messageId, Long creationDate, String text, Author author) {
		super();
		this.messageId = messageId;
		this.creationDate = creationDate;
		this.text = text;
		this.author = author;
	}

	/**
	 * @return the messageId
	 */
	public Long getMessageId() {
		return messageId;
	}

	/**
	 * @return the creationDate
	 */
	public Long getCreationDate() {
		return creationDate;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return author;
	}

}
