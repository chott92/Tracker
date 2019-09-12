/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.model;

import de.chott.tracker.enums.AccountType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author cot
 */
@Entity
@NamedQueries({@NamedQuery(name = "Account.loadAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.loadByUsername", query = "SELECT a FROM Account a WHERE a.username = :paramUsername")})
public class Account extends BaseEntity {
    
    private LocalDateTime createdDate;
    
    private String username;
    
    private String password;
    
    private LocalDateTime lastLogin;    

     @Enumerated(EnumType.STRING)
    private AccountType accountType;
    
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

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date LocalDateTime) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    
}
