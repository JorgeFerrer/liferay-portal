# What are the Breaking Changes for Liferay 7.0?

This document presents a chronological list of changes that break existing
functionality, APIs, or contracts with third party liferay developers or
users. We try our best to minimize these disruptions, but sometimes they are
unavoidable.

Here are some of the types of changes documented in this file:

* Functionality that is removed or replaced
* API incompatibilities: Changes to public Java or JavaScript APIs
* Changes to context variables available to templates
* Changes in CSS classes available to Liferay themes and portlets
* Configuration changes: Changes in configuration files, like portal.properties,
system.properties, etc.
* Execution requirements: Java version, J2EE Version, browser versions, etc.
* Deprecations or end of support: For example, warning that a certain
feature or API will be dropped in an upcoming version.
* Recommendations: For example, recommending using a newly introduced API that
replaces an old API, in spite of the old API being kept in Liferay Portal for
backwards compatibility.

## Breaking Changes

Each change must have a brief descriptive title and contain the following
information:

* *Date:* Specify the date you submitted the change. Format the date as
*YYYY-MM-DD* (e.g., 2014-02-25).
* *What changed?* Identify the affected component and the type of change that
was made.
* *Who is affected?* Is it an end user? A developer? If the only affected people
are those using a certain feature or API, say so.
* *How should I update my code?* Explain any client code changes required.
* *Why was this change made?* Explain the reason for the change. If applicable,
justify why the breaking change was made instead of following a deprecation
process.

Here's the template to use for each breaking change:

### `[Title]`
Date:

#### What changed?

#### Who is affected?

#### How should I update my code?

#### Why was this change made?


