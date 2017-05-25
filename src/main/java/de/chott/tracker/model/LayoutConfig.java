/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.model;

import de.chott.tracker.enums.LayoutConfigType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 *
 * @author cot
 */
@Entity
public class LayoutConfig extends BaseEntity{
    
    private String layoutUrl;
    
    @Enumerated(EnumType.STRING)
    private LayoutConfigType configType;
    
    @Lob
    private String cssText;
    
    @ManyToOne
    private Event event;

    public String getLayoutUrl() {
        return layoutUrl;
    }

    public void setLayoutUrl(String layoutUrl) {
        this.layoutUrl = layoutUrl;
    }

    public LayoutConfigType getConfigType() {
        return configType;
    }

    public void setConfigType(LayoutConfigType configType) {
        this.configType = configType;
    }

    public String getCssText() {
        return cssText;
    }

    public void setCssText(String cssText) {
        this.cssText = cssText;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
    
    
}
