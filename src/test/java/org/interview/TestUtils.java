package org.interview;

import java.util.Date;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

/**
 * Created by resulav on 08.07.2018.
 */
public class TestUtils {

	/**
	 * creates a dummy Status instance with not null user
	 * 
	 * @return Status instance
	 */
	public static Status dummyStatus() {
		return dummyStatus(dummyUser(1));
	}

	/**
	 * creates a dummy Status instance with given user
	 * 
	 * @param user User object
	 * @return Status instance
	 */
	public static Status dummyStatus(User user) {
		return new Status() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public UserMentionEntity[] getUserMentionEntities() {

				return null;
			}

			@Override
			public URLEntity[] getURLEntities() {

				return null;
			}

			@Override
			public SymbolEntity[] getSymbolEntities() {

				return null;
			}

			@Override
			public MediaEntity[] getMediaEntities() {

				return null;
			}

			@Override
			public HashtagEntity[] getHashtagEntities() {

				return null;
			}

			@Override
			public RateLimitStatus getRateLimitStatus() {

				return null;
			}

			@Override
			public int getAccessLevel() {

				return 0;
			}

			@Override
			public int compareTo(Status o) {

				return 0;
			}

			@Override
			public boolean isTruncated() {

				return false;
			}

			@Override
			public boolean isRetweetedByMe() {

				return false;
			}

			@Override
			public boolean isRetweeted() {

				return false;
			}

			@Override
			public boolean isRetweet() {

				return false;
			}

			@Override
			public boolean isPossiblySensitive() {

				return false;
			}

			@Override
			public boolean isFavorited() {

				return false;
			}

			@Override
			public String[] getWithheldInCountries() {

				return null;
			}

			@Override
			public User getUser() {
				return user;
			}

			@Override
			public String getText() {

				return null;
			}

			@Override
			public String getSource() {

				return null;
			}

			@Override
			public Scopes getScopes() {

				return null;
			}

			@Override
			public Status getRetweetedStatus() {

				return null;
			}

			@Override
			public int getRetweetCount() {

				return 0;
			}

			@Override
			public long getQuotedStatusId() {

				return 0;
			}

			@Override
			public Status getQuotedStatus() {

				return null;
			}

			@Override
			public Place getPlace() {

				return null;
			}

			@Override
			public String getLang() {

				return null;
			}

			@Override
			public long getInReplyToUserId() {

				return 0;
			}

			@Override
			public long getInReplyToStatusId() {

				return 0;
			}

			@Override
			public String getInReplyToScreenName() {

				return null;
			}

			@Override
			public long getId() {
				return 0;
			}

			@Override
			public GeoLocation getGeoLocation() {

				return null;
			}

			@Override
			public int getFavoriteCount() {

				return 0;
			}

			@Override
			public int getDisplayTextRangeStart() {

				return 0;
			}

			@Override
			public int getDisplayTextRangeEnd() {

				return 0;
			}

			@Override
			public long getCurrentUserRetweetId() {

				return 0;
			}

			@Override
			public Date getCreatedAt() {
				return new Date();
			}

			@Override
			public long[] getContributors() {

				return null;
			}
		};
	}

	/**
	 * creates a User object with given userId
	 * 
	 * @param userId id of the user
	 * @return User instance
	 */
	public static User dummyUser(long userId) {
		return new User() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3297445540382640926L;

			@Override
			public RateLimitStatus getRateLimitStatus() {

				return null;
			}

			@Override
			public int getAccessLevel() {

				return 0;
			}

			@Override
			public int compareTo(User o) {

				return 0;
			}

			@Override
			public boolean isVerified() {

				return false;
			}

			@Override
			public boolean isTranslator() {

				return false;
			}

			@Override
			public boolean isShowAllInlineMedia() {

				return false;
			}

			@Override
			public boolean isProtected() {

				return false;
			}

			@Override
			public boolean isProfileUseBackgroundImage() {

				return false;
			}

			@Override
			public boolean isProfileBackgroundTiled() {

				return false;
			}

			@Override
			public boolean isGeoEnabled() {

				return false;
			}

			@Override
			public boolean isFollowRequestSent() {

				return false;
			}

			@Override
			public boolean isDefaultProfileImage() {

				return false;
			}

			@Override
			public boolean isDefaultProfile() {

				return false;
			}

			@Override
			public boolean isContributorsEnabled() {

				return false;
			}

			@Override
			public String[] getWithheldInCountries() {

				return null;
			}

			@Override
			public int getUtcOffset() {

				return 0;
			}

			@Override
			public URLEntity getURLEntity() {

				return null;
			}

			@Override
			public String getURL() {

				return null;
			}

			@Override
			public String getTimeZone() {

				return null;
			}

			@Override
			public int getStatusesCount() {

				return 0;
			}

			@Override
			public Status getStatus() {

				return null;
			}

			@Override
			public String getScreenName() {
				return "screenName";
			}

			@Override
			public String getProfileTextColor() {

				return null;
			}

			@Override
			public String getProfileSidebarFillColor() {

				return null;
			}

			@Override
			public String getProfileSidebarBorderColor() {

				return null;
			}

			@Override
			public String getProfileLinkColor() {

				return null;
			}

			@Override
			public String getProfileImageURLHttps() {

				return null;
			}

			@Override
			public String getProfileImageURL() {

				return null;
			}

			@Override
			public String getProfileBannerURL() {

				return null;
			}

			@Override
			public String getProfileBannerRetinaURL() {

				return null;
			}

			@Override
			public String getProfileBannerMobileURL() {

				return null;
			}

			@Override
			public String getProfileBannerMobileRetinaURL() {

				return null;
			}

			@Override
			public String getProfileBannerIPadURL() {

				return null;
			}

			@Override
			public String getProfileBannerIPadRetinaURL() {

				return null;
			}

			@Override
			public String getProfileBackgroundImageUrlHttps() {

				return null;
			}

			@Override
			public String getProfileBackgroundImageURL() {

				return null;
			}

			@Override
			public String getProfileBackgroundColor() {

				return null;
			}

			@Override
			public String getOriginalProfileImageURLHttps() {

				return null;
			}

			@Override
			public String getOriginalProfileImageURL() {

				return null;
			}

			@Override
			public String getName() {

				return "username";
			}

			@Override
			public String getMiniProfileImageURLHttps() {

				return null;
			}

			@Override
			public String getMiniProfileImageURL() {

				return null;
			}

			@Override
			public String getLocation() {

				return null;
			}

			@Override
			public int getListedCount() {

				return 0;
			}

			@Override
			public String getLang() {

				return null;
			}

			@Override
			public long getId() {

				return userId;
			}

			@Override
			public int getFriendsCount() {

				return 0;
			}

			@Override
			public int getFollowersCount() {

				return 0;
			}

			@Override
			public int getFavouritesCount() {

				return 0;
			}

			@Override
			public String getEmail() {

				return null;
			}

			@Override
			public URLEntity[] getDescriptionURLEntities() {

				return null;
			}

			@Override
			public String getDescription() {

				return null;
			}

			@Override
			public Date getCreatedAt() {

				return new Date();
			}

			@Override
			public String getBiggerProfileImageURLHttps() {

				return null;
			}

			@Override
			public String getBiggerProfileImageURL() {

				return null;
			}
		};
	}

}
