/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.view;

import de.chott.tracker.boundary.EventService;
import de.chott.tracker.enums.EventStatus;
import de.chott.tracker.model.Event;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author cot
 */
@ManagedBean
@ViewScoped
public class EventOverviewController {
    
    @Inject
    private EventService eventService;
    
    private List<Event> eventList;
    
    private Event selectedEvent;

    @PostConstruct
    public void init(){
        
        if (FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().containsKey("eventId")) {
            long eventId = Long.valueOf(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("eventId"));
            selectedEvent = eventService.loadEvent(eventId);
            
        } else {
            selectedEvent = null;
        }
        
        eventList = eventService.loadAllEvents();
    }
    
    public void activateEvent(Event e){
        e.setEventStatus(EventStatus.ACTIVE);
        
        eventService.saveEvent(e);
    }
    
    public void deactivateEvent (Event e){
        e.setEventStatus(EventStatus.CLOSED);
        
        eventService.saveEvent(e);
    }
    
    public void archiveEvent(Event e){
        e.setEventStatus(EventStatus.ARCHIVE);
        
        eventService.saveEvent(e);
        init();
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
    
}
