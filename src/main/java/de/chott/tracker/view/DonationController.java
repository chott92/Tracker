/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.view;

import de.chott.tracker.boundary.DonationService;
import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.enums.DonationState;
import de.chott.tracker.model.Donation;
import de.chott.tracker.model.Donation_IncentiveAmount;
import de.chott.tracker.model.Incentive;
import de.chott.tracker.model.IncentiveValue;
import de.chott.tracker.session.EventSelectionController;
import de.chott.tracker.webapp.utils.MessageProvider;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
public class DonationController {
    
    @Inject
    private DonationService donationService;
    
    @Inject
    private IncentiveService incentiveService;
    
    @Inject
    private MessageProvider messageProvider;
    
    private Donation donation;
    private String amountString;
    
    private IncentiveValue selectedValue;
    
    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;
    
    @ManagedProperty("#{donationIncentiveController}")
    private DonationIncentiveController donationIncentiveController;
    
    @PostConstruct
    public void init(){
        
        if (FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().containsKey("donationId")) {
            long accountId = Long.valueOf(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("donationId"));
            donation = donationService.load(accountId);
        } else {
            donation = new Donation();
        }
    }
    
    public String sendPaypalDonation(){
        
        List<Donation_IncentiveAmount> diaList = donationIncentiveController.getDiaList();
        
        float sum = 0f;
        for (Donation_IncentiveAmount dia : diaList){
            sum+=dia.getAmount();
        }
        
        if (sum>donation.getAmount()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageProvider.getString("validate.donate.incentiveAmountOverDonationAmount"), ""));
            return null;
        }
        
        if (donation.getAmount()<eventSelectionController.getSelectedEvent().getMinDonation()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageProvider.getString("validate.donate.amountLessThenEventMin"), "Angegebener Betrag: " + donation.getAmount() + " von " + donation.getDonatorName()));
            return null;
        }
        
        int centAmount = Math.round(donation.getAmount()*100);        
        float amount = centAmount / 100f;
        
        donation.setAmount(amount);
        donation.setDonationState(DonationState.CREATED);
        donation.setDonationDate(LocalDateTime.now());
        donation.setEvent(eventSelectionController.getSelectedEvent());
        
        donation = donationService.save(donation);
        
        for (Donation_IncentiveAmount dia : diaList){
            if (dia.getIncentiveValue().getId()==0){
                dia.setIncentiveValue(incentiveService.saveIncentiveValue(dia.getIncentiveValue()));
            }
            dia.setDonation(donation);
            donationService.saveDonationIncentiveAmount(dia);
        }
        
        return "/page/paypalForm.xhtml?faces-redirect=true&donationId=" + donation.getId();
    }
    
    public String getInvoiceString(Donation d){
        return donationService.getInvoiceLabel(donation);
    }

    public String getIncentiveLabel (Incentive i){
        if (i==null) return null;
        else return i.getGame().getName() + " - " + i.getIncentiveName();
    }
    
    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    public String getAmountString() {
        return amountString;
    }

    public void setAmountString(String amountString) {
        this.amountString = amountString;
    }

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }

    public void setDonationIncentiveController(DonationIncentiveController donationIncentiveController) {
        this.donationIncentiveController = donationIncentiveController;
    }

    public IncentiveValue getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(IncentiveValue selectedValue) {
        this.selectedValue = selectedValue;
    }
    
}