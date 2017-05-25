/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.admin.boundary;

import de.chott.tracker.model.Account;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cot
 */
@Stateless
public class AccountService {
    
    @PersistenceContext
    private EntityManager em;
    
    public Account loadByUsername(String username){
        return em.createNamedQuery("Account.loadByUsername", Account.class)
                .setParameter("paramUsername", username)
                .getSingleResult();
    }
    
    public List<Account> loadAllAccounts(){
        return em.createNamedQuery("Account.loadAll").getResultList();
    }
    
    public Account load (long accountId){
        return em.find(Account.class, accountId);
    }
    
    public Account saveAccount(Account a){
        return em.merge(a);
    }
    
    public void deleteAccount(Account a){
        Account account = em.find(Account.class, a.getId());
        em.remove(account);
    }
    
    public String getEncryptedPassword(String pw){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pw.getBytes("UTF-8"));
            byte[] digest = md.digest();
            String output = Base64.getEncoder().encodeToString(digest);
            
            return output;
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
