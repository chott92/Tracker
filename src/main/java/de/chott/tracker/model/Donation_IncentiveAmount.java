/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author cot
 */
@Entity
@NamedQueries({@NamedQuery(name="Donation_IncentiveAmount.getSumByIncentiveAmount", query = "SELECT SUM(dia.amount) FROM Donation_IncentiveAmount dia WHERE dia.incentiveValue = :paramIncentiveValue"),
               @NamedQuery(name="Donation_IncentiveAmount.getByDonation", query = "SELECT dia FROM Donation_IncentiveAmount dia WHERE dia.donation = :paramDonation")})
public class Donation_IncentiveAmount extends BaseEntity{
    
    private float amount;
    
    @ManyToOne
    private Donation donation;
    
    @ManyToOne
    private IncentiveValue incentiveValue;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    public IncentiveValue getIncentiveValue() {
        return incentiveValue;
    }

    public void setIncentiveValue(IncentiveValue incentiveValue) {
        this.incentiveValue = incentiveValue;
    }
    
    
}
