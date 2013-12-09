package com.liferay.portal.service.domain;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

@ProviderType
public final class UserDetails {

	public static class Builder {

		public Builder birthday(int year, int month, int day) {
			_birthdayParams = new DateParams(year, month, day);

			return this;
		}

		public UserDetails build() {
			return new UserDetails(this);
		}

		public Builder comments(String comments) {
			_comments = comments;

			return this;
		}

		public Builder emailAddress(String emailAddress) {
			_emailAddress = emailAddress;

			return this;
		}

		public Builder facebookId(long facebookId) {
			_facebookId = facebookId;

			return this;
		}

		public Builder female() {
			_male = false;

			return this;
		}

		public Builder greeting(String greeting) {
			_greeting = greeting;

			return this;
		}

		public Builder jobTitle(String jobTitle) {
			_jobTitle = jobTitle;

			return this;
		}

		public Builder language(String languageId) {
			_languageId = languageId;

			return this;
		}

		public Builder male() {
			_male = true;

			return this;
		}

		public Builder male(boolean male) {
			_male = male;

			return this;
		}

		public Builder name(Name name) {
			_name = name;

			return this;
		}

		public Builder name(
			String firstName, String middleName, String lastName) {

			_name = new Name(firstName, middleName, lastName);

			return this;
		}

		public Builder name(
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId) {

			_name = new Name(
				firstName, middleName, lastName, prefixId, suffixId);

			return this;
		}

		public Builder openId(String openId) {
			_openId = openId;

			return this;
		}

		public Builder screenName(String screenName) {
			_screenName = screenName;

			return this;
		}

		public Builder timeZoneId(String timeZoneId) {
			_timeZoneId = timeZoneId;

			return this;
		}

		private DateParams _birthdayParams;
		private String _comments;
		private String _emailAddress;
		private long _facebookId;
		private String _greeting;
		private String _jobTitle;
		private String _languageId;
		private boolean _male;
		private Name _name;
		private String _openId;
		private String _screenName;
		private String _timeZoneId;

	}

	public DateParams getBirthdayParams() {
		return _birthdayParams;
	}

	public String getComments() {
		return _comments;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public long getFacebookId() {
		return _facebookId;
	}

	public String getGreeting() {
		return _greeting;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public Locale getLocale() {
		return LocaleUtil.fromLanguageId(_languageId);
	}

	public Name getName() {
		return _name;
	}

	public String getOpenId() {
		return _openId;
	}

	public String getScreenName() {
		return _screenName;
	}

	public String getTimeZoneId() {
		return _timeZoneId;
	}

	public boolean isMale() {
		return _male;
	}

	private UserDetails(Builder builder) {
		_birthdayParams = builder._birthdayParams;
		_comments = builder._comments;
		_emailAddress = builder._emailAddress;
		_facebookId = builder._facebookId;
		_greeting = builder._greeting;
		_jobTitle = builder._jobTitle;
		_languageId = builder._languageId;
		_male = builder._male;
		_name = builder._name;
		_openId = builder._openId;
		_screenName = builder._screenName;
		_timeZoneId = builder._timeZoneId;
	}

	private final DateParams _birthdayParams;
	private final String _comments;
	private String _emailAddress;
	private final long _facebookId;
	private final String _greeting;
	private final String _jobTitle;
	private String _languageId;
	private final boolean _male;
	private final Name _name;
	private String _openId;
	private String _screenName;
	private final String _timeZoneId;

}
