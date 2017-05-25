/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.boundary;

import de.chott.tracker.model.Donator;
import de.chott.tracker.model.Event;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cot
 */
@Stateless
public class DonatorService {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Donator> loadAllDonators(){
        return em.createNamedQuery("Donator.findAll", Donator.class).getResultList();
    }
    
    public List<Donator> loadDonatorsForEvent(Event e){
        return em.createNamedQuery("Donator.findByEvent", Donator.class)
                .setParameter("paramEvent", e).getResultList();
    }
    
}
