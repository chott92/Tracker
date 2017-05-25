/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.boundary;

import de.chott.tracker.model.Donation;
import de.chott.tracker.model.Donation_IncentiveAmount;
import de.chott.tracker.model.Event;
import de.chott.tracker.model.Incentive;
import de.chott.tracker.model.IncentiveValue;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cot
 */
@Stateless
public class IncentiveService {

    @PersistenceContext
    private EntityManager em;

    public Incentive load(long id) {
        return em.find(Incentive.class, id);
    }

    public Incentive save(Incentive i) {
        return em.merge(i);
    }

    public IncentiveValue saveIncentiveValue(IncentiveValue iv) {
        return em.merge(iv);
    }

    public List<Incentive> getIncentivesForEvent(Event e) {
        return em.createNamedQuery("Incentive.findByEvent", Incentive.class)
                .setParameter("paramEvent", e)
                .getResultList();
    }

    public double loadCurrentIncentiveAmount(Incentive i) {
        try {
            return em.createNamedQuery("IncentiveValue.getCurrentIncentiveAmountByIncentive", Double.class)
                    .setParameter("paramIncentive", i).getSingleResult();
        }catch(NullPointerException e){
            return 0.0;
        }
    }

    public double loadCurrentIncentiveAmount(IncentiveValue iv) {
        try{
            return em.createNamedQuery("Donation_IncentiveAmount.getSumByIncentiveAmount", Double.class)
                    .setParameter("paramIncentiveValue", iv).getSingleResult();
        }catch(NullPointerException e){
            return 0.0;
        }
    }

    public List<Donation_IncentiveAmount> loadDIAByDonation(Donation d) {
        return em.createNamedQuery("Donation_IncentiveAmount.getByDonation", Donation_IncentiveAmount.class)
                .setParameter("paramDonation", d).getResultList();
    }

    public List<IncentiveValue> loadBidwarValues(Incentive i) {
        return em.createNamedQuery("IncentiveValue.getValuesByIncentive", IncentiveValue.class)
                .setParameter("paramIncentive", i).getResultList();
    }

    public List<IncentiveValue> loadIncentiveValuesForEvent(Event event){
        return em.createNamedQuery("IncentiveValue.getUpcomingValuesByEvent", IncentiveValue.class)
                .setParameter("paramEvent", event)
                .getResultList();
    }
    
    public List<Incentive> loadUpcomingBidWarsForEvent(Event event){
        return em.createNamedQuery("Incentive.findUpcomingBidwarsForEvent", Incentive.class)
                .setParameter("paramEvent", event)
                .getResultList();
    }
    
    public void deleteIncentive(Incentive i) {
        Incentive in = load(i.getId());
        em.remove(in);
    }
    
    public void deleteIncentiveValue(IncentiveValue iv){
        IncentiveValue ivalue = em.find(IncentiveValue.class, iv.getId());
        em.remove(ivalue);
                
    }

}
