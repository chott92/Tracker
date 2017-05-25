/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.view;

import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.enums.IncentiveType;
import de.chott.tracker.model.Donation_IncentiveAmount;
import de.chott.tracker.model.Event;
import de.chott.tracker.model.Incentive;
import de.chott.tracker.model.IncentiveValue;
import de.chott.tracker.session.EventSelectionController;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author cot
 */
@ManagedBean
@ViewScoped
public class DonationIncentiveController {

    @Inject
    private IncentiveService incentiveService;

    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;

    private IncentiveValue selectedIncentiveValue;

    private Incentive selectedBidwar;

    private List<IncentiveValue> values;
    private List<Incentive> bidWarList;
    
    private String newBidWarValue;
    
    private float incentiveAmountString;
    
    private List<Donation_IncentiveAmount> diaList;
    private float donationAmountTotal;

    @PostConstruct
    public void init() {
        diaList = new ArrayList<>();
        donationAmountTotal = 0.0f;
        if (eventSelectionController.isEventSelected()) {
            Event event = eventSelectionController.getSelectedEvent();

            values = incentiveService.loadIncentiveValuesForEvent(event);
            bidWarList = incentiveService.loadUpcomingBidWarsForEvent(event);
            
            Iterator<IncentiveValue> it = values.iterator();
            while (it.hasNext()){
                IncentiveValue iv = it.next();
                if(iv.getIncentive().getIncentiveType().equals(IncentiveType.INCENTIVE)
                        && incentiveService.loadCurrentIncentiveAmount(iv) >= iv.getIncentive().getTargetAmount()){
                    it.remove();
                }
            }
            
            for (Incentive i : bidWarList){
                IncentiveValue iv = new IncentiveValue();
                iv.setIncentive(i);
                
                values.add(iv);
            }
        } else {
            eventSelectionController.init();
            init();
        }
    }

    public List<IncentiveValue> completeValues(String query) {
        List<IncentiveValue> retVal = new ArrayList<>();
        for (IncentiveValue iv : values) {
            if (getIncentiveValueLabel(iv).toLowerCase().contains(query.toLowerCase())) {
                retVal.add(iv);
            }
        }
        return retVal;
    }

    public String getIncentiveValueLabel(IncentiveValue iv) {
        String retVal;
        if (iv != null) {
            if (iv.getIncentive().getIncentiveType().equals(IncentiveType.INCENTIVE)) {
                retVal = iv.getIncentive().getGame().getName() + " - " + iv.getIncentive().getIncentiveName();
            } else {
                retVal = iv.getIncentive().getGame().getName() + " - " + iv.getIncentive().getIncentiveName() + ": " 
                        + ((iv.getId()==0 && iv.getValue()==null) ? "(Neuer Wert)" : iv.getValue());

            }
        return retVal;
        }
        else return "";
    }
    
    public void addDIA(){
        
        donationAmountTotal+=incentiveAmountString;
        
        Donation_IncentiveAmount dia = new Donation_IncentiveAmount();
        dia.setAmount(incentiveAmountString);
        dia.setIncentiveValue(selectedIncentiveValue);
        
        diaList.add(dia);
        
        selectedIncentiveValue = null;
        incentiveAmountString = 0f;
        
    }
    
    public void removeDIA(Donation_IncentiveAmount dia){
        diaList.remove(dia);
    }
    
    public boolean displayNewAmount(){
        return selectedIncentiveValue!=null && selectedIncentiveValue.getId()==0;
    }

    public EventSelectionController getEventSelectionController() {
        return eventSelectionController;
    }

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }

    public IncentiveValue getSelectedIncentiveValue() {
        return selectedIncentiveValue;
    }

    public void setSelectedIncentiveValue(IncentiveValue selectedIncentiveValue) {
        this.selectedIncentiveValue = selectedIncentiveValue;
    }
    
    public List<IncentiveValue> getValues() {
        return values;
    }

    public void setValues(List<IncentiveValue> values) {
        this.values = values;
    }

    public Incentive getSelectedBidwar() {
        return selectedBidwar;
    }

    public void setSelectedBidwar(Incentive selectedBidwar) {
        this.selectedBidwar = selectedBidwar;
    }

    public List<Incentive> getBidWarList() {
        return bidWarList;
    }

    public void setBidWarList(List<Incentive> bidWarList) {
        this.bidWarList = bidWarList;
    }

    public String getNewBidWarValue() {
        return newBidWarValue;
    }

    public void setNewBidWarValue(String newBidWarValue) {
        this.newBidWarValue = newBidWarValue;
    }

    public float getIncentiveAmountString() {
        return incentiveAmountString;
    }

    public void setIncentiveAmountString(float incentiveAmountString) {
        this.incentiveAmountString = incentiveAmountString;
    }

    public List<Donation_IncentiveAmount> getDiaList() {
        return diaList;
    }

    public void setDiaList(List<Donation_IncentiveAmount> diaList) {
        this.diaList = diaList;
    }
    
}
