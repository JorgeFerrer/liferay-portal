<%@ page import="com.liferay.petra.string.StringUtil" %>
<%@ page import="com.liferay.fragment.renderer.FragmentRendererContext" %>
<%@ page import="com.liferay.fragment.model.FragmentEntryLink" %>

<%
FragmentRendererContext fragmentRendererContext = (FragmentRendererContext)request.getAttribute("fragmentRendererContext");
FragmentEntryLink fragmentEntryLink = fragmentRendererContext.getFragmentEntryLink();
%>

<h3>My JSP Component</h3>
<ul>
<li>Added by: <%= fragmentEntryLink.getUserName() %>
<li>Added in: <%=fragmentEntryLink.getCreateDate() %>
<li>Locale: <%=fragmentRendererContext.getLocale() %>
<li>Mode: <%=fragmentRendererContext.getMode() %>
<li>PreviewClassPK: <%=fragmentRendererContext.getPreviewClassPK() %>
<li>PreviewType: <%=fragmentRendererContext.getPreviewType() %>
<li>Segment experiences: <%= StringUtil.merge(fragmentRendererContext.getSegmentsExperienceIds(), ", ") %>
</ul>