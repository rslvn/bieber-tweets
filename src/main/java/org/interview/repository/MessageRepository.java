/**
 * 
 */
package org.interview.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.interview.model.Author;
import org.interview.model.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * This class is responsible for keep messages whose are received from streaming
 * process. And also preparing output are doing here.
 * 
 * @author resulav
 *
 */
@Service
public class MessageRepository {

	private final Map<Author, List<Message>> authorToMessageListMap = new ConcurrentHashMap<>();

	/**
	 * adds the message to the repository
	 * 
	 * @param message new message that is received from streaming process
	 * @return the message is added or no
	 */
	public boolean add(Message message) {
		// validate message
		Assert.notNull(message, "Message can not be null");
		Assert.notNull(message.getCreationDate(), "Message.creationDate can not be null");
		Assert.notNull(message.getAuthor(), "Message.author can not be null");
		Assert.notNull(message.getAuthor().getCreationDate(), "Author.creationDate can not be null");

		return authorToMessageListMap.computeIfAbsent(message.getAuthor(), key -> new ArrayList<>()).add(message);
	}

	/**
	 * creates a new Map and puts all current data to the new Map in the repository
	 * 
	 * @return messages in a Map
	 */
	protected Map<Author, List<Message>> getMessageMap() {
		return new HashMap<>(authorToMessageListMap);
	}

	/**
	 * 
	 * returns sorted messages chronologically, ascending by epoch date and groups
	 * by author
	 * 
	 * @return list of messages
	 */
	public List<Message> getSortedMessageList() {
		List<Message> messageList = new ArrayList<>();

		final Map<Author, List<Message>> tempMap = getMessageMap();

		// sort Authors by CreationDate
		List<Author> authorIdSortedList = new ArrayList<>(tempMap.keySet());
		authorIdSortedList.sort((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate()));

		for (Author author : authorIdSortedList) {
			// sort the messages of the author by CreationDate of the message
			messageList.addAll(getSortedMessageListByAuthorId(author, tempMap));
		}

		return messageList;
	}

	/**
	 * Receives messages from the repository for the given author and sorts messages
	 * by creation date.
	 *
	 * @param author message owner
	 * @param map    a copy of the current repository
	 * 
	 * @return list of sorted messages
	 */
	private Collection<? extends Message> getSortedMessageListByAuthorId(Author author,
			Map<Author, List<Message>> map) {
		return map.get(author).stream().sorted((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate()))
				.collect(Collectors.toList());
	}

}
