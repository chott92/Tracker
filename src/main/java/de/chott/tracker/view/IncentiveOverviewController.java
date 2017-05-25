/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.view;

import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.comparator.IncentiveComparator;
import de.chott.tracker.enums.IncentiveType;
import de.chott.tracker.model.Incentive;
import de.chott.tracker.model.IncentiveValue;
import de.chott.tracker.session.EventSelectionController;
import de.chott.tracker.webapp.utils.MessageProvider;
import java.util.ArrayList;
import java.util.Collections;
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
public class IncentiveOverviewController {
    
    @Inject
    private IncentiveService incentiveService;
    
    @Inject
    private MessageProvider messageProvider;

    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;
    
    private List<Incentive> incentiveList;
    
    @PostConstruct
    public void init(){
        incentiveList = incentiveService.getIncentivesForEvent(eventSelectionController.getSelectedEvent());
        Collections.sort(incentiveList, new IncentiveComparator());
    }

    public String getCurrentIncentiveAmount(Incentive incentive){
        if (!incentive.getIncentiveType().equals(IncentiveType.INCENTIVE)){
            return "-";
        }
        else {
            return Double.toString(incentiveService.loadCurrentIncentiveAmount(incentive));
        }
    }
    
    public List<IncentiveValue> getBidwarValues(Incentive incentive){
        if (!incentive.getIncentiveType().equals(IncentiveType.INCENTIVE)){
            
            return incentiveService.loadBidwarValues(incentive);
        } else{
            return new ArrayList<IncentiveValue>();
        }
    }
    
    public String getCurrentIncentiveValueAmount(IncentiveValue value){
        if (!value.getIncentive().getIncentiveType().equals(IncentiveType.INCENTIVE)){
            return Double.toString(incentiveService.loadCurrentIncentiveAmount(value));
        }
        else {
            return "";
        }
    }
    
    public void deleteIncentive(Incentive incentive){
        if (incentiveService.loadCurrentIncentiveAmount(incentive)!=0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    messageProvider.getString("incentive.delete.alreadyDonated"), ""));
        } else {
            
            for (IncentiveValue iv : incentiveService.loadBidwarValues(incentive)){
                incentiveService.deleteIncentiveValue(iv);
            }
            
            incentiveService.deleteIncentive(incentive);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    messageProvider.getString("incentive.delete.success"), ""));
            
            incentiveList.remove(incentive);
        }
    }
    
    public List<Incentive> getIncentiveList() {
        return incentiveList;
    }

    public void setIncentiveList(List<Incentive> incentiveList) {
        this.incentiveList = incentiveList;
    }

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }
    
    
    
}