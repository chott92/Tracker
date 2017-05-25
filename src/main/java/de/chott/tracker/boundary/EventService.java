/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.boundary;

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
public class EventService {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Event> loadAllEvents(){
        return em.createNamedQuery("Event.loadAll", Event.class).getResultList();
    }
    
    public Event loadEvent(long id){
        return em.find(Event.class, id);
    }
    
    public Event saveEvent(Event e){
        return em.merge(e);
    }
    
    public void deleteEvent(Event e){
        Event event = loadEvent(e.getId());
        em.remove(event);
    }
    
}
