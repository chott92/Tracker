/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.model;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author cot
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Game.loadEventSchedule", query = "SELECT g FROM Game g WHERE g.event = :paramEvent ORDER BY g.scheduleOrder")
})
public class Game extends BaseEntity {
    
    private String name;
    
    private String category;
    
    private String runner;
    
    private int estimateMinutes;
    
    private int estimateSetup;
    
    private int scheduleOrder;
    
    private LocalDateTime startTime;
    
    @ManyToOne
    private Event event;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEstimateMinutes() {
        return estimateMinutes;
    }

    public void setEstimateMinutes(int estimateMinutes) {
        this.estimateMinutes = estimateMinutes;
    }

    public int getEstimateSetup() {
        return estimateSetup;
    }

    public void setEstimateSetup(int estimateSetup) {
        this.estimateSetup = estimateSetup;
    }

    public int getScheduleOrder() {
        return scheduleOrder;
    }

    public void setScheduleOrder(int scheduleOrder) {
        this.scheduleOrder = scheduleOrder;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRunner() {
        return runner;
    }

    public void setRunner(String runner) {
        this.runner = runner;
    }
    
}
