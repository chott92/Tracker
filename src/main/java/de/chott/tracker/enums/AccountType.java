/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.enums;

/**
 *
 * @author cot
 */
public enum AccountType {
    USER("Benutzer"), EVENT_ADMIN("Event-Admin"), SUPER_ADMIN("Super-Admin");
    private String type;
    
    private AccountType(){}
    AccountType (String s){
        this.type = s;
    }
    
    public String getType(){
        return type;
    }
}
