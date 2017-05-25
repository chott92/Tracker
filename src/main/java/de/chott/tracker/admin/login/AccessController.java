/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.admin.login;

import de.chott.tracker.webapp.utils.ServiceLocator;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

/**
 *
 * @author cot
 */
public class AccessController implements PhaseListener {
    
    
    @Override
    public void afterPhase(PhaseEvent event) {
        String loginRequiredPattern = "/page/administration/.+";
        String adminRequiredPattern = "/page/administration/account/.+";
        
        FacesContext context = event.getFacesContext();
	context.getExternalContext().getSession(true);
        String viewId = context.getViewRoot().getViewId();
        
        Pattern loginPattern = Pattern.compile(loginRequiredPattern);
        Matcher loginMatcher = loginPattern.matcher(viewId);
        
        Pattern adminPattern = Pattern.compile(adminRequiredPattern);
        Matcher adminMatcher = adminPattern.matcher(viewId);
        
        LoginBean login = ServiceLocator.getInstance(LoginBean.class);
        
        if (loginMatcher.find()){
            if (!login.isLoggedIn()){
                redirectLogin(context);
            } else if (!login.isSuperAdmin() && adminMatcher.find()){
                redirectAccessDenied(context);
            }
            
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
    protected void redirectLogin(FacesContext context) {
		if (context != null && context.getPartialViewContext() != null
				&& context.getPartialViewContext().isAjaxRequest()) {
			return;
		}
                String path = context.getExternalContext().getRequestServletPath();
                Map<String, String> requestMap = context.getExternalContext().getRequestParameterMap();
                StringBuilder sb = new StringBuilder(path + "?");
                sb.append("faces-redirect=true&");
                for (String key : requestMap.keySet()){
                    sb.append(key).append("=").append(requestMap.get(key)).append("&");
                }
                String param="";
            try {
                param = URLEncoder.encode(sb.substring(0, sb.length()-1), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
            }
		String redirectURL = "/adminLogin.xhtml?faces-redirect=true&redirect="+param;

		if (context != null && context.getApplication() != null
				&& context.getApplication().getNavigationHandler() != null) {
			context.getApplication().getNavigationHandler()
					.handleNavigation(context, null, redirectURL);
		}
	}
    
    protected void redirectAccessDenied(FacesContext context) {
		String redirectURL = "/errorpages/403.xhtml?faces-redirect=true";

		context.getApplication().getNavigationHandler()
				.handleNavigation(context, null, redirectURL);
	}
    
}
