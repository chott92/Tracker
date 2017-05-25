/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.converter;

import de.chott.tracker.boundary.GameService;
import de.chott.tracker.model.Game;
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
public class GameConverter implements Converter{
    
    @Inject
    private GameService gameService;
    
    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Game> gameList = gameService.loadEventSchedule(eventSelectionController.getSelectedEvent());
        for (Game game : gameList){
            if (gameService.getGameLabel(game).equals(value)){
                return game;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Game game = (Game) value;
        if (game!=null)
        return (gameService.getGameLabel(game));
        else return "";
    }

    public EventSelectionController getEventSelectionController() {
        return eventSelectionController;
    }

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }

    
    
}
