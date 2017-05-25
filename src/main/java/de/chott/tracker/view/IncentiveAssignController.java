/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.view;

import de.chott.tracker.boundary.DonationService;
import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.model.Donation;
import de.chott.tracker.model.Donation_IncentiveAmount;
import de.chott.tracker.model.Event;
import de.chott.tracker.model.IncentiveValue;
import de.chott.tracker.session.EventSelectionController;
import de.chott.tracker.webapp.utils.MessageProvider;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author cot
 */
@ManagedBean
@ViewScoped
public class IncentiveAssignController {

    @Inject
    private MessageProvider messageProvider;

    @Inject
    private DonationService donationService;

    @Inject
    private IncentiveService incentiveService;

    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;
    
    @ManagedProperty("#{donationIncentiveController}")
    private DonationIncentiveController donationIncentiveController;

    private Donation donation;
    
    private List<IncentiveValue> values;
    
    private IncentiveValue selectedIncentiveValue;
    
    @PostConstruct
    public void init() {
        if (eventSelectionController.isEventSelected()) {
            Event e = eventSelectionController.getSelectedEvent();

            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("donationId")) {
                String s = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("donationId");
                
                donation = donationService.load(Long.valueOf(s));
                values=donationIncentiveController.getValues();
                
                Iterator<IncentiveValue> it = values.iterator();
                while(it.hasNext()){
                    IncentiveValue value = it.next();
                    if (value.getId()==0) it.remove();
                }
            }
        }
    }

    public String assignIncentive(){
        
        Donation_IncentiveAmount dia = new Donation_IncentiveAmount();
        
        dia.setAmount(donation.getAmount());
        dia.setDonation(donation);
        dia.setIncentiveValue(selectedIncentiveValue);
        
        donationService.saveDonationIncentiveAmount(dia);
        
        return "/page/administration/incentiveAssignOverview.xhtml?faces-redirect=true";
    }

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    public List<IncentiveValue> getValues() {
        return values;
    }

    public void setValues(List<IncentiveValue> values) {
        this.values = values;
    }

    public IncentiveValue getSelectedIncentiveValue() {
        return selectedIncentiveValue;
    }

    public void setSelectedIncentiveValue(IncentiveValue selectedIncentiveValue) {
        this.selectedIncentiveValue = selectedIncentiveValue;
    }

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }

    public void setDonationIncentiveController(DonationIncentiveController donationIncentiveController) {
        this.donationIncentiveController = donationIncentiveController;
    }
    
}
