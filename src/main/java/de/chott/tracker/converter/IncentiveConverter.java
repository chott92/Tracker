/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.converter;

import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.model.Incentive;
import de.chott.tracker.session.EventSelectionController;
import java.util.List;
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
public class IncentiveConverter implements Converter {

    @Inject
    private IncentiveService incentiveService;
    
    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Incentive> incentives = incentiveService.getIncentivesForEvent(eventSelectionController.getSelectedEvent());
        for (Incentive i : incentives){
            if (value.equals(getIncentiveLabel(i))){
                return i;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Incentive i = (Incentive) value;
        return getIncentiveLabel(i);
    }
    
    public String getIncentiveLabel (Incentive i){
        if (i==null) return null;
        else return i.getGame().getName() + " - " + i.getIncentiveName();
    }

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }
    
}
