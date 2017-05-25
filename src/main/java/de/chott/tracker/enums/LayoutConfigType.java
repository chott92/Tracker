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
public enum LayoutConfigType {
    
    SIXTEEN_NINE("16zu9"), FOUR_THREE("4zu3"), FOUR_THREE_CAM("4zu3 Cam"), SIXTEEN_NINE_CAM("16zu9 Cam"),
    DS("Ninetendo DS"), DS_CAM("Nintendo DS Cam"), FOUR_THREE_RACE("4zu3 Race"), SIXTEEN_NINE_RACE("16zu9 Race"),
    FOUR_THREE_RACE_CAM("4zu3 Race Cam"), SIXTEEN_NINE_RACE_CAM("16zu9 Race Cam");
    
    private String type;
    
    private LayoutConfigType(){}
    
    LayoutConfigType(String s){
        this.type = s;
    }
    
    public String getType(){
        return type;
    }
    
}
