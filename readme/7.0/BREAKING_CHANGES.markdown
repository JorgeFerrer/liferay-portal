# What are the Breaking Changes for Liferay 7.0?

This document presents a chronological list of changes that break existing
functionality, APIs, or contracts with third party Liferay developers or users.
We try our best to minimize these disruptions, but sometimes they are
unavoidable.

Here are some of the types of changes documented in this file:

* Functionality that is removed or replaced
* API incompatibilities: Changes to public Java or JavaScript APIs
* Changes to context variables available to templates
* Changes in CSS classes available to Liferay themes and portlets
* Configuration changes: Changes in configuration files, like
 portal.properties`, `system.properties`, etc.
* Execution requirements: Java version, J2EE Version, browser versions, etc.
* Deprecations or end of support: For example, warning that a certain
feature or API will be dropped in an upcoming version.
* Recommendations: For example, recommending using a newly introduced API that
replaces an old API, in spite of the old API being kept in Liferay Portal for
backwards compatibility.

## Breaking Changes

Each change must have a brief descriptive title and contain the following
information:

* **Date:** Specify the date you submitted the change. Format the date as
*YYYY-MMM* (e.g. 2014-Mar) or *YYYY-MMM-DD* (e.g., 2014-Feb-25).
* **Jira Ticket:** Reference the related Jira ticket (e.g., LPS-123456)
(Optional).
* **What changed?** Identify the affected component and the type of change that
was made.
* **Who is affected?** Are end users affected? Are developers affected? If the
only affected people are those using a certain feature or API, say so.
* **How should I update my code?** Explain any client code changes required.
* **Why was this change made?** Explain the reason for the change. If
applicable, justify why the breaking change was made instead of following a
deprecation process.

Here's the template to use for each breaking change:

### [Title]
Date:
Jira Ticket:

#### What changed?

#### Who is affected?

#### How should I update my code?

#### Why was this change made?


### Stop of support for message boards and wiki e-mail signature configuration
* Date: 28th February 2014
* Jira Ticket: LPS-44599

#### What changed?
The configuration for e-mail signatures of notifications from message boards and 
wiki has been removed. An automatic update process has been developed to 
append the signatures into the bodies of the e-mail messages for wiki and 
message boards notifications. This process only works if you have configured 
your signatures in database. In case your signatures were declared in 
portal.properties the automatic process will not migrate it.

#### Who is affected?
Users who have configured e-mail signatures for wiki or message boards 
notifications. Especially those who have done it in their portal.properties 
file.

#### How should I update my code?
You should modify your portal.properties file to remove the properties 
message.boards.email.message.added.signature, 
message.boards.email.message.updated.signature, wiki.email.page.added.signature,
and wiki.email.page.updated.signature. Then, you should append the contents of 
the signatures to the bodies you have configured in you portal.properties file.

**Example**

Replace:
```
wiki.email.page.updated.body=A wiki page was updated.
wiki.email.page.updated.signature=For any doubts e-mail the system administrator
```

With:
```
wiki.email.page.updated.body=A wiki page was updated.\n--\nFor any doubts e-mail the system administrator
```

#### Why was this change made?
The signatures have been removed because they can easily be set inside the body
and there's no benefit in having both things separated.
---------------------------------------