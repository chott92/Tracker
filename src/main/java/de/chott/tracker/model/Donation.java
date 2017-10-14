/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.chott.tracker.enums.DonationState;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author cot
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Donation.findAll", query = "SELECT d FROM Donation d WHERE d.donationState = 'PAID'"),
    @NamedQuery(name = "Donation.findForEvent", query = "SELECT d FROM Donation d WHERE d.event = :paramEvent AND d.donationState = 'PAID' ORDER BY d.donationDate DESC"),
    @NamedQuery(name = "Donation.findForDonator", query = "SELECT d FROM Donation d WHERE d.donator = :paramDonator AND d.donationState = 'PAID'"),
    @NamedQuery(name = "Donation.findForDonatorAndEvent", query = "SELECT d FROM Donation d WHERE d.donator = :paramDonator AND d.event = :paramEvent AND d.donationState = 'PAID'"),
    @NamedQuery(name = "Donation.getTotalDonationAmountForEvent", query = "SELECT SUM(d.amount) FROM Donation d WHERE d.event = :paramEvent AND d.donationState = 'PAID'"),
    @NamedQuery(name = "Donation.findByEventWithoutIncentive", query="SELECT d FROM Donation d WHERE NOT EXISTS (SELECT dia FROM Donation_IncentiveAmount dia where dia.donation = d) AND d.donationState = 'PAID' AND d.event = :paramEvent")
})
public class Donation extends BaseEntity {
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date donationDate;
    
    private float amount;
    
    private String donatorName;
    
    @Lob
    private String message;
    
    @ManyToOne
    @JsonIgnore
    private Donator donator;
    
    @ManyToOne
    @JsonIgnore
    private Event event;

    @Enumerated(EnumType.STRING)
    private DonationState donationState;
    
    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Donator getDonator() {
        return donator;
    }

    public void setDonator(Donator donator) {
        this.donator = donator;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public DonationState getDonationState() {
        return donationState;
    }

    public void setDonationState(DonationState donationState) {
        this.donationState = donationState;
    }

    public String getDonatorName() {
        return donatorName;
    }

    public void setDonatorName(String donatorName) {
        this.donatorName = donatorName;
    }
    
}
