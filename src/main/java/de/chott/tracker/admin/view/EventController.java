/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.admin.view;

import de.chott.tracker.boundary.EventService;
import de.chott.tracker.enums.EventStatus;
import de.chott.tracker.model.Event;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
public class EventController {
    
    @Inject
    private EventService eventService;
    
    private Event selectedEvent;
    
    @PostConstruct
    public void init(){
        
        if (FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().containsKey("eventId")) {
            long eventId = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestParameterMap().get("eventId"));
            selectedEvent = eventService.loadEvent(eventId);
        } else {
            selectedEvent = new Event();
            selectedEvent.setEventStatus(EventStatus.CREATED);
        }
        
    }
    
    public String saveEvent(){
        
        if (selectedEvent.getStartDate()!=null && selectedEvent.getEndDate() !=null){
            if(selectedEvent.getEndDate().isBefore(selectedEvent.getStartDate())){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Fehler", "Enddatum liegt aktuell vor dem Startdatum!"));
                return null;
            } else {
                selectedEvent = eventService.saveEvent(selectedEvent);
        
                return "/page/administration/eventOverview.xhtml?faces-redirect=true";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Fehler", "Weder Start- noch Enddatum düfen leer sein. Bitte ausfüllen."));
            return null;
        }
    }
    
    public String cancel(){
        return "/page/administration/eventOverview.xhtml?faces-redirect=true";
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
    
    
}
