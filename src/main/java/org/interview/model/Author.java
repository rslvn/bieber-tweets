/**
 * 
 */
package org.interview.model;

/**
 * Model for Author objects whose are received from streaming process
 * 
 * @author resulav
 *
 */
public class Author {

	private Long userId;
	private Long creationDate;
	private String userName;
	private String screenName;

	/**
	 * @param userId       id of user
	 * @param creationDate creation date of user
	 * @param userName     name of user
	 * @param screenName   screen name of user
	 */
	public Author(Long userId, Long creationDate, String userName, String screenName) {
		super();
		this.userId = userId;
		this.creationDate = creationDate;
		this.userName = userName;
		this.screenName = screenName;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @return the creationDate
	 */
	public Long getCreationDate() {
		return creationDate;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
}
