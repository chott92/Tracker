/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.model;

import de.chott.tracker.enums.EventStatus;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author cot
 */
@Entity
@NamedQueries({@NamedQuery(name = "Event.loadAll", query = "SELECT e FROM Event e where e.eventStatus not like 'ARCHIVE'")})
public class Event extends BaseEntity{
    
    private String name;
    
    private String charityName;
    
    private String charityPaypalAdress;
    
    private String charityHomePage;
    
    private String charityPurpose;
    
    private float minDonation;
    
    private boolean displayIncentives;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Lob
    private String description;
    
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCharityPaypalAdress() {
        return charityPaypalAdress;
    }

    public void setCharityPaypalAdress(String charityPaypalAdress) {
        this.charityPaypalAdress = charityPaypalAdress;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCharityHomePage() {
        return charityHomePage;
    }

    public void setCharityHomePage(String charityHomePage) {
        this.charityHomePage = charityHomePage;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getCharityPurpose() {
        return charityPurpose;
    }

    public void setCharityPurpose(String charityPurpose) {
        this.charityPurpose = charityPurpose;
    }

    public float getMinDonation() {
        return minDonation;
    }

    public void setMinDonation(float minDonation) {
        this.minDonation = minDonation;
    }

    public boolean isDisplayIncentives() {
        return displayIncentives;
    }

    public void setDisplayIncentives(boolean displayIncentives) {
        this.displayIncentives = displayIncentives;
    }
    
}
