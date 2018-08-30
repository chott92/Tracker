/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.admin.view;

import de.chott.tracker.boundary.GameService;
import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.enums.IncentiveType;
import de.chott.tracker.model.Event;
import de.chott.tracker.model.Game;
import de.chott.tracker.model.Incentive;
import de.chott.tracker.model.IncentiveValue;
import de.chott.tracker.session.EventSelectionController;
import de.chott.tracker.webapp.utils.MessageProvider;
import java.util.ArrayList;
import java.util.Arrays;
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
public class IncentiveController {

    @Inject
    private IncentiveService incentiveService;

    @Inject
    private GameService gameService;

    @Inject
    transient MessageProvider messageProvider;

    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;

    private Incentive incentive;
    private boolean editView;
    private Event event;

    private List<IncentiveValue> incentiveValues;
    private List<IncentiveValue> removableValues = new ArrayList<>();

    @PostConstruct
    public void init() {
        event = eventSelectionController.getSelectedEvent();
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("incentiveId")) {
            String s = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("incentiveId");
            long incentiveId = Long.valueOf(s);
            incentive = incentiveService.load(incentiveId);
            editView = true;
            if (!incentive.getIncentiveType().equals(IncentiveType.INCENTIVE)) {
                incentiveValues = incentiveService.loadBidwarValues(incentive);
            } else {
                incentiveValues = new ArrayList<>();
            }
        } else {
            incentive = new Incentive();
            incentive.setEvent(event);
            editView = false;
            incentiveValues = new ArrayList<>();
        }
    }

    public List<Game> completeGames(String input) {
        List<Game> retVal = new ArrayList<>();
        for (Game g : gameService.loadEventSchedule(event)) {
            if (gameService.getGameLabel(g).contains(input)) {
                retVal.add(g);
            }
        }
        return retVal;
    }

    public String saveIncentive() {
        if (incentive.getIncentiveType().equals(IncentiveType.STAITC_BID_WAR)
                && incentiveValues.size() < 2) {
            String message = messageProvider.getString("validate.incentive.staticBidWarOptions");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
            return null;
        }

        if (!incentive.getIncentiveType().equals(IncentiveType.INCENTIVE)) {
            incentive.setTargetAmount(0f);

            incentive = incentiveService.save(incentive);

            if (incentive.getIncentiveType().equals(IncentiveType.STAITC_BID_WAR)) {
                for (IncentiveValue iv : incentiveValues) {
                    iv.setIncentive(incentive);
                    incentiveService.saveIncentiveValue(iv);
                }
                for (IncentiveValue iv : removableValues) {
                    incentiveService.deleteIncentiveValue(iv);
                }
            }
        } else {

            if (incentive.getId() == 0) {
                incentive = incentiveService.save(incentive);
                IncentiveValue iv = new IncentiveValue();
                iv.setValue("Ziel");
                iv.setIncentive(incentive);
                incentiveService.saveIncentiveValue(iv);
            }else {
                incentive = incentiveService.save(incentive);
            }
        }
        return "/page/administration/incentiveOverview.xhtml";
    }

    public String cancel() {
        return "/page/administration/incentiveOverview.xhtml";
    }

    public void addIncentiveValue() {
        incentiveValues.add(new IncentiveValue());
    }

    public void removeIncentiveValue(IncentiveValue iv) {
        incentiveValues.remove(iv);
        if (iv.getId() != 0) {
            removableValues.add(iv);
        }
    }

    public Incentive getIncentive() {
        return incentive;
    }

    public void setIncentive(Incentive incentive) {
        this.incentive = incentive;
    }

    public boolean isEditView() {
        return editView;
    }

    public void setEditView(boolean editView) {
        this.editView = editView;
    }

    public String getGameLabel(Game g) {
        return gameService.getGameLabel(g);
    }

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }

    public List<IncentiveType> getIncentiveTypes() {
        return Arrays.asList(IncentiveType.values());
    }

    public List<IncentiveValue> getIncentiveValues() {
        return incentiveValues;
    }

    public void setIncentiveValues(List<IncentiveValue> incentiveValues) {
        this.incentiveValues = incentiveValues;
    }
}
