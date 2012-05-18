package com.liferay.portlet.portalsettings.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.sites.action.ActionUtil;

public class ImportPortalSettingsAction extends PortletAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liferay.portal.struts.PortletAction#processAction(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.portlet.PortletConfig, javax.portlet.ActionRequest,
	 * javax.portlet.ActionResponse)
	 */
	@Override
	public void processAction(ActionMapping mapping, ActionForm form,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		// TODO Auto-generated method stub
		super.processAction(mapping, form, portletConfig, actionRequest,
				actionResponse);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liferay.portal.struts.PortletAction#render(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.portlet.PortletConfig, javax.portlet.RenderRequest,
	 * javax.portlet.RenderResponse)
	 */
	@Override
	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse) throws Exception {
		try {
			ActionUtil.getGroup(renderRequest);
		} catch (Exception e) {
			if (e instanceof NoSuchGroupException
					|| e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.portal_settings.error");
			} else {
				throw e;
			}
		}

		return mapping.findForward(getForward(renderRequest,
				"portlet.portal_settings.export_settings"));
	}

	private static Log _log = LogFactoryUtil
			.getLog(ImportPortalSettingsAction.class);

}
