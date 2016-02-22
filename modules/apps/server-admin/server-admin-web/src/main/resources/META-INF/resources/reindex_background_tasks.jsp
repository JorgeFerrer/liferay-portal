<%@ include file="/init.jsp" %>

<%
com.liferay.portal.kernel.portlet.PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

PortletURL portletURL = currentURLObj;

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", "create-date");
	orderByType = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", "desc");
}

OrderByComparator<BackgroundTask> orderByComparator = BackgroundTaskComparatorFactoryUtil.getBackgroundTaskOrderByComparator(orderByCol, orderByType);

String[] classNames = new String[] {
	BackgroundTaskExecutorNames.RIENDEX_SINGLE_BACKGROUND_TASK_EXECUTOR,
	BackgroundTaskExecutorNames.RIENDEX_PORTAL_BACKGROUND_TASK_EXECUTOR
};
%>

<portlet:renderURL var="redirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/server_admin/renidex_background_tasks" />
	<portlet:param name="orderByCol" value="<%= orderByCol %>" />
	<portlet:param name="orderByType" value="<%= orderByType %>" />
</portlet:renderURL>

<liferay-ui:search-container
	emptyResultsMessage="no-reindex-processes-were-found"
	iteratorURL="<%= portletURL %>"
	orderByCol="<%= orderByCol %>"
	orderByComparator="<%= orderByComparator %>"
	orderByType="<%= orderByType %>"
	total="<%= BackgroundTaskManagerUtil.getBackgroundTasksCount(CompanyConstants.SYSTEM, classNames) %>"
>
	<liferay-ui:search-container-results
		results="<%= BackgroundTaskManagerUtil.getBackgroundTasks(CompanyConstants.SYSTEM, classNames, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.backgroundtask.BackgroundTask"
		keyProperty="backgroundTaskId"
		modelVar="backgroundTask"
	>
		<liferay-ui:search-container-column-text
			cssClass="background-task-user-column"
			name="user"
		>
			<liferay-ui:user-display
				displayStyle="3"
				showUserDetails="<%= false %>"
				showUserName="<%= false %>"
				userId="<%= backgroundTask.getUserId() %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			cssClass="background-task-type-column"
			name="type"
		>
			<%
			String reindexType = "portal";

			if (BackgroundTaskExecutorNames.RIENDEX_SINGLE_BACKGROUND_TASK_EXECUTOR.equals(backgroundTask.getTaskExecutorClassName())) {
				Map<String, Serializable> taskContextMap = backgroundTask.getTaskContextMap();

				reindexType = GetterUtil.getString(taskContextMap.get("className"), "single");
			}
			%>

			<liferay-ui:message key="<%= reindexType %>"/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			cssClass="background-task-status-column"
			name="status"
		>
			<h5 class="background-task-status-<%= BackgroundTaskConstants.getStatusLabel(backgroundTask.getStatus()) %> <%= BackgroundTaskConstants.getStatusCssClass(backgroundTask.getStatus()) %>">
				<liferay-ui:message key="<%= backgroundTask.getStatusLabel() %>" />
			</h5>
		</liferay-ui:search-container-column-text>	

		<liferay-ui:search-container-column-date
			name="create-date"
			orderable="<%= true %>"
			value="<%= backgroundTask.getCreateDate() %>"
		/>

		<liferay-ui:search-container-column-date
			name="completion-date"
			orderable="<%= true %>"
			value="<%= backgroundTask.getCompletionDate() %>"
		/>

		<liferay-ui:search-container-column-text>
			<c:if test="<%= !backgroundTask.isInProgress() %>">

				<%
				Date completionDate = backgroundTask.getCompletionDate();
				%>

				<liferay-portlet:actionURL name="deleteBackgroundTask" portletName="<%= PortletKeys.SERVER_ADMIN %>" var="deleteBackgroundTaskURL">
					<portlet:param name="redirect" value="<%= redirectURL %>" />
					<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
					<liferay-ui:icon-delete
						label="<%= true %>"
						message='<%= ((completionDate != null) && completionDate.before(new Date())) ? "clear" : "cancel" %>'
						url="<%= deleteBackgroundTaskURL %>"
					/>
				</liferay-ui:icon-menu>
			</c:if>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" />
</liferay-ui:search-container>