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
public enum EventStatus {
    
    CREATED("Erstellt"), ACTIVE("Aktiv"), CLOSED("Geschlossen"), ARCHIVE("Archiviert");
    
    private String eventStatus;
    
    EventStatus(String s){
        this.eventStatus = s;
    }

    private EventStatus(){}
    
    public String getEvetStatus(){
        return eventStatus;
    }
}
