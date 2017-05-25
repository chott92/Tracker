/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.view;

import de.chott.tracker.boundary.DonationService;
import de.chott.tracker.boundary.DonatorService;
import de.chott.tracker.model.Donation;
import de.chott.tracker.model.Donator;
import de.chott.tracker.model.Event;
import de.chott.tracker.session.EventSelectionController;
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
public class DonatorOverviewController {
    
    @Inject
    private DonatorService donatorService;
    
    @Inject
    private DonationService donationService;
    
    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;
    
    private Event event;
    private List<Donator> donators;
    
    @PostConstruct
    public void init(){
        event = eventSelectionController.getSelectedEvent();
        donators = donatorService.loadDonatorsForEvent(event);
    }
    
    public List<Donation> loadDonationsForDonator(Donator donator){
        return donationService.loadDonationsForDonatorAndEvent(donator, event);
    }
    
    public String getDonationTotalForDonator(Donator d){
        List<Donation> donations = loadDonationsForDonator(d);
        float amount = 0f;
        for (Donation donation : donations){
            amount += donation.getAmount();
        }
        
        return Float.toString(amount);
    }

    public List<Donator> getDonators() {
        return donators;
    }

    public void setDonators(List<Donator> donators) {
        this.donators = donators;
    }

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }
    
}
