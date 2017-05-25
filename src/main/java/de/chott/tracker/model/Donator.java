/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author cot
 */
@Entity
@NamedQueries({@NamedQuery(name = "Donator.findAll", query = "SELECT d FROM Donator d"),
        @NamedQuery(name="Donator.findByEvent", query="SELECT d FROM Donator d WHERE EXISTS(SELECT don FROM Donation don WHERE don.donator = d AND don.event = :paramEvent)"),
        @NamedQuery(name="Donator.findByMail", query="SELECT d FROM Donator d WHERE d.mailAddress = :paramMail")
})
public class Donator extends BaseEntity{
    
    private String name;
    private String mailAddress;
    
    @OneToMany(targetEntity = Donation.class)
    @JoinColumn(name = "DONATOR_ID")
    private List<Donation> donations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }
    
}
