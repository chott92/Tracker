/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.admin.view;

import de.chott.tracker.admin.boundary.AccountService;
import de.chott.tracker.enums.AccountType;
import de.chott.tracker.model.Account;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author cot
 */
@ManagedBean
@ViewScoped
public class AccountController {

    @Inject
    private AccountService accountService;

    private Logger logger;
    
    private Account selectedAccount;
    private boolean editView;

    private String oldPassword;
    private String password;
    private String passwordConfirm;

    @PostConstruct
    public void init() {
        logger = Logger.getLogger(this.getClass().getName());
        if (FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().containsKey("accountId")) {
            long accountId = Long.valueOf(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("accountId"));
            selectedAccount = accountService.load(accountId);
            editView = true;
        } else {
            selectedAccount = new Account();
            editView = false;
        }
    }

    public String saveAccount() {
        if (!password.equals(passwordConfirm)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Speichern Fehlgeschlagen", "Passwörter stimmen nicht überein!"));
        }

        selectedAccount.setPassword(accountService.getEncryptedPassword(password));
        accountService.saveAccount(selectedAccount);

        return "/page/administration/account/accountOverview.xhtml?faces-redirect=true";
    }
    
    public String cancel(){
        return "/page/administration/account/accountOverview.xhtml?faces-redirect=true";
    }

    public List<SelectItem> getAccountTypeItems() {
        List<SelectItem> retVal = new ArrayList<SelectItem>();
        for (AccountType at : AccountType.values()) {
            retVal.add(new SelectItem(at, at.getType()));
        }

        return retVal;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
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

    public boolean isEditView() {
        return editView;
    }

    public void setEditView(boolean editView) {
        this.editView = editView;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

}
