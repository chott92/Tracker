/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.rest;

import de.chott.tracker.boundary.DonationVerificationService;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;

/**
 *
 * @author cot
 */
@Path("/verification")
public class DonationVerificationRestService {
    
    @Inject
    private DonationVerificationService donationVerificationService;
    
    @POST
    public Response verifyDonation(String message){
        Logger log = Logger.getLogger(this.getClass().getName());
        log.info("verification started");
        
        donationVerificationService.verifyDonation(message);
        
        return Response.ok().build();
    }
    
}
