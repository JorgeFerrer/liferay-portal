package com.liferay.portal.service.domain;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

@ProviderType
public final class UserDetails {

	public static class Builder {

		public Builder aimSn(String aimSn) {
			_aimSn = aimSn;

			return this;
		}

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

		public Builder facebookSn(String facebookSn) {
			_facebookSn = facebookSn;

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

		public Builder icqSn(String icqSn) {
			_icqSn = icqSn;

			return this;
		}
		public Builder jabberSn(String jabberSn) {
			_jabberSn = jabberSn;

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

		public Builder msnSn(String msnSn) {
			_msnSn = msnSn;

			return this;
		}

		public Builder mySpaceSn(String mySpaceSn) {
			_mySpaceSn = mySpaceSn;

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

		public Builder skypeSn(String skypeSn) {
			_skypeSn = skypeSn;

			return this;
		}

		public Builder smsSn(String smsSn) {
			_smsSn = smsSn;

			return this;
		}

		public Builder timeZoneId(String timeZoneId) {
			_timeZoneId = timeZoneId;

			return this;
		}

		public Builder twitterSn(String twitterSn) {
			_twitterSn = twitterSn;

			return this;
		}

		public Builder ymSn(String ymSn) {
			_ymSn = ymSn;

			return this;
		}

		private String _aimSn;
		private DateParams _birthdayParams;
		private String _comments;
		private String _emailAddress;
		private long _facebookId;
		private String _facebookSn;
		private String _greeting;
		private String _icqSn;
		private String _jabberSn;
		private String _jobTitle;
		private String _languageId;
		private boolean _male;
		private String _msnSn;
		private String _mySpaceSn;
		private Name _name;
		private String _openId;
		private String _screenName;
		private String _smsSn;
		private String _timeZoneId;
		private String _twitterSn;
		private String _skypeSn;
		private String _ymSn;

	}

	public String getAimSn() {
		return _aimSn;
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

	public String getFacebookSn() {
		return _facebookSn;
	}

	public String getGreeting() {
		return _greeting;
	}

	public String getIcqSn() {
		return _icqSn;
	}

	public String getJabberSn() {
		return _jabberSn;
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

	public String getMsnSn() {
		return _msnSn;
	}

	public String getMySpaceSn() {
		return _mySpaceSn;
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

	public String getSkypeSn() {
		return _skypeSn;
	}

	public String getSmsSn() {
		return _smsSn;
	}

	public String getTimeZoneId() {
		return _timeZoneId;
	}

	public String getTwitterSn() {
		return _twitterSn;
	}

	public String getYmSn() {
		return _ymSn;
	}
	public boolean isMale() {
		return _male;
	}

	private UserDetails(Builder builder) {
		_aimSn = _lower(builder._aimSn);
		_birthdayParams = builder._birthdayParams;
		_comments = builder._comments;
		_emailAddress = builder._emailAddress;
		_facebookId = builder._facebookId;
		_facebookSn = _lower(builder._facebookSn);
		_icqSn = _lower(builder._icqSn);
		_greeting = builder._greeting;
		_jabberSn = _lower(builder._jabberSn);
		_jobTitle = builder._jobTitle;
		_languageId = builder._languageId;
		_male = builder._male;
		_msnSn = _lower(builder._msnSn);
		_mySpaceSn = _lower(builder._mySpaceSn);
		_name = builder._name;
		_openId = builder._openId;
		_screenName = builder._screenName;
		_skypeSn = _lower(builder._skypeSn);
		_smsSn = _lower(builder._smsSn);
		_timeZoneId = builder._timeZoneId;
		_twitterSn = _lower(builder._twitterSn);
		_ymSn = _lower(builder._ymSn);
	}

	private String _lower(String value) {
		return StringUtil.toLowerCase(value.trim());
	}

	private final String _aimSn;
	private final DateParams _birthdayParams;
	private final String _comments;
	private final String _emailAddress;
	private final long _facebookId;
	private final String _facebookSn;
	private final String _greeting;
	private final String _icqSn;
	private final String _jabberSn;
	private final String _jobTitle;
	private final String _languageId;
	private final boolean _male;
	private final String _msnSn;
	private final String _mySpaceSn;
	private final Name _name;
	private final String _openId;
	private final String _screenName;
	private final String _smsSn;
	private final String _timeZoneId;
	private final String _twitterSn;
	private final String _skypeSn;
	private final String _ymSn;

}
