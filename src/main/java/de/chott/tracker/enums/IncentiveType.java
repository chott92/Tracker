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
public enum IncentiveType {
    
    INCENTIVE("Incentive"), BID_WAR("Bid War"), STAITC_BID_WAR("Bid War(vordefiniert)");
    
    private String type;
    
    private IncentiveType(){}
    IncentiveType(String s){
        this.type = s;
    }
    
    public String getType(){
        return type;
    }
}
