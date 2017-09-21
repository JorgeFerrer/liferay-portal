
# How to Contribute

Liferay is developed by its community consisting of Liferay users, enthusiasts, employees, customers, partners, and others. We strongly encourage you to contribute to Liferay's open source projects by implementing new features, enhancing existing features, and fixing bugs. We also welcome your participation in our forums, writing documentation, and translating existing documentation.

Liferay is known for its innovative top quality features. To maintain this reputation, all code changes are reviewed by a core set of Liferay project maintainers. We encourage you to introduce yourself to the core maintainer(s) and engage them as you contribute to the areas they maintain.

To get a deeper understanding of Liferay in general, make sure to read Liferay’s official documentation on the [Liferay Developer Network](http://dev.liferay.com), for [using](https://dev.liferay.com/discover/portal) and [deploying](https://dev.liferay.com/discover/deployment) Liferay Portal, and [developing](https://dev.liferay.com/develop/tutorials) on Liferay. This documentation contains extensive explanations, examples, and reference material for you to consult time and time again.

For more information, visit the links listed in the Additional Resources section, below.

# Building Liferay Portal

System Requirements:
* Quad Core i7 Processor
* 8 GB Memory
* JDK 8
* Apache Ant
* Gradle

The first step to contributing to Liferay Portal is to clone the Liferay Central (**liferay-portal**) repo from Github and build the platform using Apache Ant.  All changes from the Sub Repos are synced to Central so no Sub Repos need to be involved in the build process.  

At the time of this writing the Liferay Central repo weighs in a 12 GB’s.  In order to cut down on the download size the repo can cloned with the options of leaving out commit history and selecting only one branch.

## Build from Central Repo:

To build Liferay Portal from source do the following:

* Fork Liferay Portal Central Repo on Github - [https://github.com/liferay/liferay-portal](https://github.com/liferay/liferay-portal)

* Clone the forked repo’s master branch (or preferred branch) with no commit history:

      git clone https://github.com/<github username>/liferay-portal --branch master --single-branch --depth 1

* Add main liferay-portal repo as an upstream for fetching changes:

      git remote add upstream https://github.com/liferay/liferay-portal

* Build master branch from source for testing customizations:

	    cd liferay-portal
          ant all

* Test the newly built bundle:

      cd ../bundles
      ./tomcat-8.0.32/bin/startup.sh

## Building a Module in Central

In order to build a module that is maintained from within Central (aka it isn’t maintained in a Sub Repo as of yet) it will have to be built from within the Central repo.  The good news is that since it’s a module the whole platform does not have to be recompiled.  To build a module located within central do the following:

* The platform must be completely compiled once using the method above.  This is due to the fact that the initial build initializes the build environment.

* To build a module located in: **liferay-portal/modules/apps/collaboration/comment/comment-web** run: 

      cd liferay-portal/modules
      ../gradlew :apps:collaboration:comment:comment-web:deploy

* If all goes well the following should appear in the bundle logs:

      20:31:55,899 INFO  [fileinstall-/Users/jamie/liferay/repos/contrib/bundles/osgi/modules][BundleStartStopLogger:38] STOPPED com.liferay.comment.web_1.1.8 [243]

      20:31:56,038 INFO  [Refresh Thread: Equinox Container: 208f839d-2b6b-0017-1f31-cc9c6b1fc91b][BundleStartStopLogger:35] STARTED com.liferay.comment.web_1.1.8 [243]

## Build from Sub Repo:

Current list of Sub Repos:

* [https://github.com/liferay/com-liferay-adaptive-media](https://github.com/liferay/com-liferay-adaptive-media)
* [https://github.com/liferay/com-liferay-dynamic-data-mapping](https://github.com/liferay/com-liferay-dynamic-data-mapping)
* [https://github.com/liferay/com-liferay-portal-workflow](https://github.com/liferay/com-liferay-portal-workflow)
* [https://github.com/liferay/com-liferay-vulcan](https://github.com/liferay/com-liferay-vulcan)
* [https://github.com/liferay/com-liferay-journal](https://github.com/liferay/com-liferay-journal)

To build source from a Sub Repo do the following:

* Fork the Sub Repo on Github (this example uses the Journal Sub Repo): - [https://github.com/liferay/com-liferay-journal](https://github.com/liferay/com-liferay-journal)

* Clone the forked repo: 

      git clone https://github.com/<github username>/com-liferay-journal

* Add main Sub Repo as an upstream for fetching changes:

      git remote add upstream https://github.com/liferay/com-liferay-journal

* Deploy a module located in the Sub Repo and test it on bundle built above:

        cd com-liferay-journal/journal-terms-of-use
        gradle deploy

* If all goes well the following should appear in the bundles log:
	
      20:14:32,464 INFO  [fileinstall-/Users/jamie/liferay/repos/contrib/bundles/osgi/modules][BundleStartStopLogger:38] STOPPED com.liferay.journal.terms.of.use_2.0.25 [385]

      20:14:32,630 INFO  [Refresh Thread: Equinox Container: 208f839d-2b6b-0017-1f31-cc9c6b1fc91b][BundleStartStopLogger:35] STARTED com.liferay.journal.terms.of.use_2.0.25 [385]

# Making Changes

Regardless of if the change is in Liferay Central or a Sub Repo, the process for making a change and submitting to Liferay is pretty much the same.  It’s best to start off by creating a ticket in JIRA and referencing the ticket number from within any commits and pull requests.  

## JIRA

Create a ticket in JIRA by doing the following:

* Sign up for a [JIRA Account](https://issues.liferay.com)] to track progress on the feature, improvement, or bug fix you want to implement. We'll refer to these as *issues* from now on.

* [Submit a ticket](https://issues.liferay.com) for your issue, following the [established JIRA process](http://www.liferay.com/community/wiki/-/wiki/Main/JIRA). If a ticket already exists for the issue, participate via the existing ticket.

* Describe the issue clearly. If it is a bug, include steps to reproduce it.

* Select an appropriate category for the issue.

* Select the earliest version of the product affected by the issue.

* Respond to the Contributor License Agreement displayed by clicking the Contribute Solution button.

## Github

Submit your custom changes to Github using the following process:

* Create a topic branch to hold your changes based on upstream/master:

      git fetch upstream 
      git checkout -b my-custom-change upstream/master

* Commit logical units of work including a reference to your ticket in JIRA.  For example:

      LPS-83432 Make the example in CONTRIBUTING imperative and concrete

* Test your changes thoroughly! Consider the wide variety of operating systems, databases, application servers, and other related technologies Liferay supports. Make sure your changes in one environment don't break something in another environment.

* Before pushing your branch to your fork on Github it’s often a good idea to rebase on the updated version of upstream/master:

      git fetch upstream
      git rebase upstream/master

* Push changes in your branch to your fork: 

      git push origin my-custom-change

* If the change is for something in Portal Core or a module that resides in the Central Repo, submit the pull request to the main liferay-portal repo.  If the change is in an active Sub Repo such as: com-liferay-journal, submit the pull request to that specific repo.

* In the LPS ticket, provide a link to your GitHub pull request and* You're done! Well, not quite ... be sure to respond to comments and questions to your pull request until it is closed.

# Tooling

Creating customizations and debugging code can be made easier using tooling.  Customizations to Sub Repos work the best with the tooling since they work like any custom module project.

Install the following tools to aid in development:

* Blade Tools - [https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-0/installing-blade-cli](https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-0/installing-blade-cli)

* IDE 
  * Liferay IDE - [https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-0/liferay-ide](https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-0/liferay-ide).   Use the [generate-modules-classpath](https://web.liferay.com/web/jorge.diaz/blog/-/blogs/debugging-liferay-7-0-generate-modules-classpath-in-eclipse-using-generate_modules_classpath-sh) script to import Portal Core and all of the modules located in Central.  

  * IntelliJ -  Use [liferay-intellij](https://github.com/holatuwol/liferay-intellij) to help configure the environment.

* Liferay Sub Repos can be used with the tooling like many other custom modules including importing them into an IDE using the built-in Gradle support.

* If using Liferay IDE, modules that reside from within a Sub Repo can be deployed to the Liferay Server by dragging and dropping it to the server or use **'gradle deploy'** from the CLI for IntelliJ or text editors.

# Additional Resources

* [Liferay and JIRA](http://www.liferay.com/community/wiki/-/wiki/Main/JIRA)
* [Liferay Community Site](http://community.liferay.com)
* [Liferay Community Slack Chat](https://communitychat.liferay.com/)
* [Contributor License Agreement](https://www.liferay.com/legal/contributors-agreement)
* [General GitHub documentation](http://help.github.com/)
* [GitHub pull request documentation](http://help.github.com/send-pull-requests/)