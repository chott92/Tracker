/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.rest;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import de.chott.tracker.model.Event;
import de.chott.tracker.model.Donation;
import de.chott.tracker.boundary.EventService;
import de.chott.tracker.boundary.DonationService;
import javax.inject.Inject;

@Path("/donations")
public class DonationRestService {
    
    @Inject
    private EventService eventService;
    
    @Inject
    private DonationService donationService;
    
    @GET
    @Path("/event/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Donation> getForEvent(@PathParam("eventId") String idString){
        Long id = Long.valueOf(idString);
        Event event = eventService.loadEvent(id);
        
        return donationService.loadDonationsForEvent(event);
    }
            
    
}
