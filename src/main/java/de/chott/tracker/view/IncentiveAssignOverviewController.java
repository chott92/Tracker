/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.view;

import de.chott.tracker.boundary.DonationService;
import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.model.Donation;
import de.chott.tracker.model.Event;
import de.chott.tracker.session.EventSelectionController;
import de.chott.tracker.webapp.utils.MessageProvider;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author cot
 */
@ManagedBean
@ViewScoped
public class IncentiveAssignOverviewController {
    
    @Inject
    private DonationService donationService;
    
    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;
    
    private List<Donation> unassignedDonations;
    
    @PostConstruct
    public void init(){
        if (eventSelectionController.isEventSelected()){
            Event e = eventSelectionController.getSelectedEvent();
            
            unassignedDonations = donationService.loadUnassignedDonationsForEvent(e);
        }
    }

    public List<Donation> getUnassignedDonations() {
        return unassignedDonations;
    }

    public void setUnassignedDonations(List<Donation> unassignedDonations) {
        this.unassignedDonations = unassignedDonations;
    }    

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }
}
