/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.admin.view;

import de.chott.tracker.admin.boundary.AccountService;
import de.chott.tracker.model.Account;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author cot
 */
@ManagedBean
@ViewScoped
public class AccountOverviewController {
    
    @Inject
    private AccountService accountService;
    
    private List<Account> accounts;
    
    @PostConstruct
    public void init(){
        accounts = accountService.loadAllAccounts();
    }
    
    public void deleteAccount(Account account){
        accountService.deleteAccount(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
