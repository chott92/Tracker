/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.boundary;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import de.chott.tracker.TrackerApplication;
import de.chott.tracker.enums.DonationState;
import de.chott.tracker.model.Donation;
import de.chott.tracker.model.Donator;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author cot
 */
@Stateless
public class DonationVerificationService {

    Logger log = Logger.getLogger(this.getClass().getName());

    @Inject
    private TrackerApplication trackerApplication;

    @Inject
    private DonationService donationService;

    public void verifyDonation(String paypalText) {

        Map<String, String> paypalParams = getPaypalParamMap(paypalText);

        if (paypalParams.get("payment_status").equalsIgnoreCase("Completed")) {
            boolean fieldsVerified = false;
            
            log.info("getting donation-id");
            
            long donationId = Long.valueOf(paypalParams.get("custom"));
            Donation donation = donationService.load(donationId);
            if (donation != null) {
                fieldsVerified = verifyFields(paypalParams, donation);

                log.info("verifying fields and paypal");
                if (fieldsVerified && sendVerificationRequest(paypalText)) {
                    log.info("verification okay, saving donation.");
                    String donatorMail = paypalParams.get("payer_email");

                    Donator d = donationService.findDonatorByMail(donatorMail);

                    if (d == null) {
                        d = new Donator();
                        d.setMailAddress(paypalParams.get("payer_email"));
                        d.setName(donation.getDonatorName());

                        d = donationService.saveDonator(d);
                    } else if (!d.getName().equals(donation.getDonatorName())){
                        d.setName(donation.getDonatorName());
                        
                        d= donationService.saveDonator(d);
                    }

                    donation.setDonator(d);
                    donation.setDonationState(DonationState.PAID);
                    donationService.save(donation);
                    log.info("donation saved.");
                } else {
                    log.info("verification failed.");
                }
            } else log.info("donation not found.");

        } else{
            log.info("payment not completed.");
            log.info(paypalText);
        }
    }

    private boolean sendVerificationRequest(String message) {
        log.info("verifying at PP-Endpoint");
        message = "cmd=_notify-validate&" + message;
        String paypalHost = trackerApplication.getPaypalEndpointHost() + "/cgi-bin/webscr";

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource resource = client.resource(UriBuilder.fromUri(paypalHost)
                .build());

        ClientResponse response = resource.header("Accept-Charset", "UTF-8").post(ClientResponse.class, message);

        String resp = response.getEntity(String.class);

        log.info("verification from Endpoint: " + resp);

        return !resp.equals("INVALID");
    }

    public Map<String, String> getPaypalParamMap(String paypalString) {
        Map<String, String> retVal = new HashMap<>();

        String[] paramStrings = paypalString.split("&");

        try {
            for (String s : paramStrings) {
                String[] param = s.split("=");
                if (param.length < 2) {
                    String key = param[0];
                    param = new String[2];
                    param[0] = key;
                    param[1] = "";
                }
                retVal.put(URLDecoder.decode(param[0], "UTF-8"), URLDecoder.decode(param[1], "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    private boolean verifyFields(Map<String, String> paypalParams, Donation donation) {
        log.info("verifying fields");
        boolean retValue = true;

        if (!paypalParams.get("invoice").equals(donationService.getInvoiceLabel(donation))) {
            retValue = false;
        }

        if (!paypalParams.get("receiver_email").equals(donation.getEvent().getCharityPaypalAdress())) {
            retValue = false;
        }

        if (!paypalParams.get("mc_currency").equals("EUR")) {
            retValue = false;
        }

        if (Float.valueOf(paypalParams.get("mc_gross")) != donation.getAmount()) {
            retValue = false;
        }
        log.info("Field verified: " + Boolean.toString(retValue));
        return retValue;
    }

}
