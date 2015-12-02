create index IX_630CC727 on SCFrameworkVersi_SCProductVers (companyId);
create index IX_3BB93ECA on SCFrameworkVersi_SCProductVers (frameworkVersionId);
create index IX_E8D33FF9 on SCFrameworkVersi_SCProductVers (productVersionId);

create index IX_C98C0D78 on SCFrameworkVersion (companyId);
create index IX_6E1764F on SCFrameworkVersion (groupId, active_);

create index IX_5327BB79 on SCLicense (active_, recommended);

create index IX_2EE8A074 on SCLicenses_SCProductEntries (companyId);
create index IX_27006638 on SCLicenses_SCProductEntries (licenseId);
create index IX_D7710A66 on SCLicenses_SCProductEntries (productEntryId);

create index IX_5D25244F on SCProductEntry (companyId);
create index IX_98E6A9CB on SCProductEntry (groupId, userId);
create index IX_7311E812 on SCProductEntry (repoGroupId[$COLUMN_LENGTH:75$], repoArtifactId[$COLUMN_LENGTH:75$]);

create index IX_AE8224CC on SCProductScreenshot (fullImageId);
create index IX_DA913A55 on SCProductScreenshot (productEntryId, priority);
create index IX_6C572DAC on SCProductScreenshot (thumbnailId);

create index IX_7020130F on SCProductVersion (directDownloadURL[$COLUMN_LENGTH:75$]);
create index IX_8377A211 on SCProductVersion (productEntryId);