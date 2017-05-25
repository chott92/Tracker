/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.admin.view;

import de.chott.tracker.admin.boundary.AccountService;
import de.chott.tracker.admin.login.LoginBean;
import de.chott.tracker.model.Account;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 *
 * @author cot
 */
@ManagedBean
@ViewScoped
public class ChangePasswordController {
    
    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;
    
    @Inject
    private AccountService accountService;

    private Account selectedAccount;
    
    private String oldPassword;
    private String password;
    private String passwordConfirm;
    
    private Logger logger;
    
    @PostConstruct
    public void init(){
        
        logger = Logger.getLogger(this.getClass().getName());
        selectedAccount = loginBean.getCurrentUser();
        
    }
    
    public String changePassword() {
        if (accountService.getEncryptedPassword(oldPassword).equals(selectedAccount.getPassword())) {
            if (!password.equals(passwordConfirm)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Speichern Fehlgeschlagen", "Passwörter stimmen nicht überein!"));
                logger.info("pw change fail: confirm");
            } else {
                selectedAccount.setPassword(accountService.getEncryptedPassword(password));
                loginBean.setCurrentUser(accountService.saveAccount(selectedAccount));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Passwort ändern fehlgeschlagen", "Altes Passwort ist nicht korrekt!"));
            logger.info("pw change fail: old");
        }

        return "/page/home.xhtml?faces-redirect=true";
    }
    
    public String cancel(){
        return "/page/home.xhtml?faces-redirect=true";
    }
    
    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
}
