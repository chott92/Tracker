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
public enum DonationState {

    CREATED("Erstellt"),PAID("Zahlung getätigt");
    
    private String state;
    
    private DonationState(){}
    
    DonationState(String s){
        this.state = s;
    }
    
    public String getState(){
        return state;
    }
    
}
