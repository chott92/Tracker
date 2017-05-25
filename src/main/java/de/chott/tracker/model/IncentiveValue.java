/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author cot
 */
@Entity
@NamedQueries({
    @NamedQuery(name="IncentiveValue.getCurrentIncentiveAmountByIncentive", 
        query="SELECT SUM(dia.amount) FROM IncentiveValue iv, Donation_IncentiveAmount dia WHERE dia.incentiveValue = iv AND iv.incentive = :paramIncentive"),
    @NamedQuery(name="IncentiveValue.getValuesByIncentive", query = "SELECT iv FROM IncentiveValue iv WHERE iv.incentive = :paramIncentive"),
    @NamedQuery(name="IncentiveValue.getUpcomingValuesByEvent", 
            query="SELECT iv FROM IncentiveValue iv WHERE iv.incentive.game.startTime>=CURRENT_TIMESTAMP AND iv.incentive.event = :paramEvent ORDER BY iv.incentive.game.startTime")
})
public class IncentiveValue extends BaseEntity{

    private String value;
    
    @ManyToOne
    private Incentive incentive; 

    public Incentive getIncentive() {
        return incentive;
    }

    public void setIncentive(Incentive incentive) {
        this.incentive = incentive;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.value);
        hash = 71 * hash + Objects.hashCode(this.incentive);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IncentiveValue other = (IncentiveValue) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Objects.equals(this.incentive, other.incentive)) {
            return false;
        }
        return true;
    }
    
}
