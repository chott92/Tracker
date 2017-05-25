/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.session;

import de.chott.tracker.boundary.EventService;
import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.model.Event;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author cot
 */
@ManagedBean
@SessionScoped
public class EventSelectionController {
    
    private Event selectedEvent;
    private boolean eventSelected;
    private boolean hasIncentives;
    
    @Inject
    private EventService eventService;
    
    @Inject
    private IncentiveService incentiveService;

    @PostConstruct
    public void init(){
        
        if (FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().containsKey("eventId")) {
            long eventId = Long.valueOf(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("eventId"));
            selectedEvent = eventService.loadEvent(eventId);
            eventSelected = true;
            
            hasIncentives = incentiveService.getIncentivesForEvent(selectedEvent).isEmpty();
        } else {
            selectedEvent = null;
            eventSelected = false;
            hasIncentives = false;
        }
        
    }
    
    public String selectEvent(Event e){
        selectedEvent = e;
        eventSelected = true;
        
        return "/page/donations.xhtml?faces-redirect=true&eventId=" + selectedEvent.getId();
    }
    
    public String navigateToDonationPage(Event e){
        selectEvent(e);
        return "/page/donate.xhtml?faces-redirect=true&eventId=" + selectedEvent.getId();
    }
    
    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public boolean isEventSelected() {
        return eventSelected;
    }

    public void setEventSelected(boolean eventSelected) {
        this.eventSelected = eventSelected;
    }
    
    public boolean hasIncentives(){
        return hasIncentives;
    }
    
}
