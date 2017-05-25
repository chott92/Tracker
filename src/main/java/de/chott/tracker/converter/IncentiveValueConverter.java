/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.converter;

import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.enums.IncentiveType;
import de.chott.tracker.model.IncentiveValue;
import de.chott.tracker.session.EventSelectionController;
import de.chott.tracker.view.DonationIncentiveController;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

/**
 *
 * @author cot
 */
@ManagedBean
@ViewScoped
public class IncentiveValueConverter implements Converter{
    
    @ManagedProperty("#{donationIncentiveController}")
    private DonationIncentiveController donationIncentiveController;
    
    private List<IncentiveValue> values;
    
    @PostConstruct
    public void init(){
        values = donationIncentiveController.getValues();
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (values==null) init();
        for (IncentiveValue iv : values){
            if (getIncentiveValueLabel(iv).equals(value)){
                return iv;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return getIncentiveValueLabel((IncentiveValue) value);
    }
    
    public String getIncentiveValueLabel(IncentiveValue iv) {
        String retVal;
        if (iv != null) {
            if (iv.getIncentive().getIncentiveType().equals(IncentiveType.INCENTIVE)) {
                retVal = iv.getIncentive().getGame().getName() + " - " + iv.getIncentive().getIncentiveName();
            } else {
                retVal = iv.getIncentive().getGame().getName() + " - " + iv.getIncentive().getIncentiveName() + ": " 
                        + ((iv.getId()!=0) ? iv.getValue() : "(Neuer Wert)");

            }
        return retVal;
        }
        else return "";
    }

    public void setDonationIncentiveController(DonationIncentiveController donationIncentiveController) {
        this.donationIncentiveController = donationIncentiveController;
    }

}
