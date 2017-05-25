/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.rest;

import de.chott.tracker.boundary.DonationService;
import de.chott.tracker.boundary.EventService;
import de.chott.tracker.model.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author cot
 */
@Path("/totalDonations")
public class TotalDonationRestService {
    
    @Inject
    private EventService eventService;
    
    @Inject
    private DonationService donationService;
    
    @GET
    @Path("/{eventId}")
    @Produces(MediaType.TEXT_HTML)
    public String getTotalDonations(@PathParam("eventId")String eventId){
        long idValue = Long.valueOf(eventId);
        Event event = eventService.loadEvent(idValue);
        double d = donationService.loadTotalDonationAmountForEvent(event);
        
        long centAmount = Math.round(d*100);        
        double amount = centAmount / 100.0;
        
        return Double.toString(amount);
    }
}
