/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.view;

import de.chott.tracker.boundary.DonationService;
import de.chott.tracker.boundary.EventService;
import de.chott.tracker.model.Donation;
import de.chott.tracker.model.Event;
import de.chott.tracker.session.EventSelectionController;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;

/**
 *
 * @author cot
 */
@ManagedBean
@ViewScoped
public class DonationOverviewController {
    
    @Inject
    private DonationService donationService;
    
    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;
    
    private List<Donation> donationList;
    
    @PostConstruct
    public void init (){
        donationList = donationService.loadDonationsForEvent(eventSelectionController.getSelectedEvent());
        
    }
    
    public double getTotalDonations(){
        double d = donationService.loadTotalDonationAmountForEvent(eventSelectionController.getSelectedEvent());
        
        long centAmount = Math.round(d*100);        
        double amount = centAmount / 100.0;
        
        return amount;
    }

    public List<Donation> getDonationList() {
        return donationList;
    }

    public void setDonationList(List<Donation> donationList) {
        this.donationList = donationList;
    }

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }
    
}
