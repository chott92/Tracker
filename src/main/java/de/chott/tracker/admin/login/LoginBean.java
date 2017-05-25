/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.admin.login;

import de.chott.tracker.admin.boundary.AccountService;
import de.chott.tracker.enums.AccountType;
import de.chott.tracker.model.Account;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.util.Faces;

/**
 *
 * @author cot
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private AccountService accountService;
    
    private String username;
    private String password;
    private Account currentUser;
    
    private String redirect;

    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
         if (context.getExternalContext()
                .getRequestParameterMap().containsKey("redirect")) {
            try {
                redirect = URLDecoder.decode(context.getExternalContext().getRequestParameterMap().get("redirect"), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                redirect = "/page/home.xhtml?faces-redirect=true";
            }
         } else {
            redirect = "/page/home.xhtml?faces-redirect=true";
         }
    }

    public String login() {
        HttpServletRequest request = Faces.getRequest();
        try {
            if (request.getUserPrincipal() != null) {
                request.logout();
                currentUser=null;
            }
            request.login(username, password);
            
            currentUser = accountService.loadByUsername(username);
            
            if (currentUser ==null)
                throw new ServletException("User Does not Exist!");
            
            currentUser.setLastLogin(new Date());
            
            currentUser = accountService.saveAccount(currentUser);
            
            return redirect;
            
        } catch (ServletException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        String red="";
        try {
            red = URLEncoder.encode(redirect, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
        return "login.xhtml?redirect=" + red;
    }

    public String logout() {
HttpServletRequest request = Faces.getRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, e);
        }
        Faces.invalidateSession();
        currentUser = null;
        return "/page/home.xhtml?faces-redirect=true";
    }
    
    public boolean isCurrentUser(Account a){
        return a.getUsername().equals(currentUser.getUsername());
    }
    
    public boolean isSuperAdmin (){
        return currentUser.getAccountType().equals(AccountType.SUPER_ADMIN);
    }
    
    public boolean isEventAdmin(){
        return currentUser.getAccountType().equals(AccountType.SUPER_ADMIN) || currentUser.getAccountType().equals(AccountType.EVENT_ADMIN);
    }
    
    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getCurrentUser() {
        if (currentUser == null){
                Principal p = Faces.getRequest().getUserPrincipal();
                if (p!=null){
                String name = Faces.getRequest().getUserPrincipal().getName();
                currentUser = accountService.loadByUsername(name);
            }
        }
        return currentUser;
    }

    public void setCurrentUser(Account currentUser) {
        this.currentUser = currentUser;
    }

}
