/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.view;

import de.chott.tracker.boundary.GameService;
import de.chott.tracker.model.Event;
import de.chott.tracker.model.Game;
import de.chott.tracker.session.EventSelectionController;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class GameOverviewController {
    
    @Inject
    private GameService gameService;
    
    @ManagedProperty("#{eventSelectionController}")
    private EventSelectionController eventSelectionController;
    
    private Event event;
    
    private List<Game> eventSchedule;
    
    @PostConstruct
    public void init(){
        event = eventSelectionController.getSelectedEvent();
        eventSchedule = gameService.loadEventSchedule(event);
    }

    public void setEventSelectionController(EventSelectionController eventSelectionController) {
        this.eventSelectionController = eventSelectionController;
    }

    public String getEstimateString(int estimateMinutes){
        int estimateHours = estimateMinutes/60;
        estimateMinutes = estimateMinutes%60;
        NumberFormat format = new DecimalFormat();
        format.setMinimumIntegerDigits(2);
        format.setMaximumIntegerDigits(2);
        
        return format.format(estimateHours) + ":" + format.format(estimateMinutes) + ":00";
    }
    
    public void addGame(){
        Game game = new Game();
        game.setEvent(event);
        if (!eventSchedule.isEmpty())
            adjustGameStartTime(game, eventSchedule.get(eventSchedule.size()-1));
        eventSchedule.add(game);
    }
    
    public void saveAllGames(){
        eventSchedule.get(0).setStartTime(event.getStartDate());
        eventSchedule.get(0).setScheduleOrder(0);
        for (int i = 1; i<eventSchedule.size();i++){
            adjustGameStartTime(eventSchedule.get(i), eventSchedule.get(i-1));
            eventSchedule.get(i).setScheduleOrder(i);
        }
        
        gameService.saveEventSchedule(eventSchedule);
        init();
    }
    
    private void adjustGameStartTime(Game target, Game source){
        LocalDateTime time = source.getStartTime()
                .plusMinutes(source.getEstimateMinutes())
                .plusMinutes(source.getEstimateSetup());
        
        target.setStartTime(time);
    }
    
    public String getGameLabel (Game g){
        return gameService.getGameLabel(g);
    }

    public List<Game> getEventSchedule() {
        return eventSchedule;
    }

    public void setEventSchedule(List<Game> eventSchedule) {
        this.eventSchedule = eventSchedule;
    }
    
}
