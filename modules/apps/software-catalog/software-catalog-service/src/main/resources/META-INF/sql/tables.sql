create table SCFrameworkVersi_SCProductVers (
	companyId LONG not null,
	frameworkVersionId LONG not null,
	productVersionId LONG not null,
	primary key (companyId, frameworkVersionId, productVersionId)
);

create table SCFrameworkVersion (
	frameworkVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	url VARCHAR(75) null,
	active_ BOOLEAN,
	priority INTEGER
);

create table SCLicense (
	licenseId LONG not null primary key,
	companyId LONG,
	name VARCHAR(75) null,
	url VARCHAR(75) null,
	openSource BOOLEAN,
	active_ BOOLEAN,
	recommended BOOLEAN
);

create table SCLicenses_SCProductEntries (
	companyId LONG not null,
	licenseId LONG not null,
	productEntryId LONG not null,
	primary key (companyId, licenseId, productEntryId)
);

create table SCProductEntry (
	productEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null,
	tags VARCHAR(75) null,
	shortDescription VARCHAR(75) null,
	longDescription VARCHAR(75) null,
	pageURL VARCHAR(75) null,
	author VARCHAR(75) null,
	repoGroupId VARCHAR(75) null,
	repoArtifactId VARCHAR(75) null
);

create table SCProductScreenshot (
	productScreenshotId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	productEntryId LONG,
	thumbnailId LONG,
	fullImageId LONG,
	priority INTEGER
);

create table SCProductVersion (
	productVersionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	productEntryId LONG,
	version VARCHAR(75) null,
	changeLog VARCHAR(75) null,
	downloadPageURL VARCHAR(75) null,
	directDownloadURL VARCHAR(75) null,
	repoStoreArtifact BOOLEAN
);