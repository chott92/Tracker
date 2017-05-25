/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.boundary;

import de.chott.tracker.admin.boundary.AccountService;
import de.chott.tracker.model.Donation;
import de.chott.tracker.model.Donation_IncentiveAmount;
import de.chott.tracker.model.Donator;
import de.chott.tracker.model.Event;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cot
 */
@Stateless
public class DonationService {

    @PersistenceContext
    private EntityManager em;

    public Donation load(long id) {
        try {
            return em.find(Donation.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    public Donation save(Donation d) {
        return em.merge(d);
    }

    public Donation_IncentiveAmount saveDonationIncentiveAmount(Donation_IncentiveAmount dia) {
        return em.merge(dia);
    }

    public List<Donation> loadAllDonations() {
        return em.createNamedQuery("Donation.findAll", Donation.class).getResultList();
    }

    public List<Donation> loadDonationsForEvent(Event e) {
        return em.createNamedQuery("Donation.findForEvent", Donation.class)
                .setParameter("paramEvent", e).getResultList();
    }

    public List<Donation> loadDonationsForDonator(Donator donator) {
        return em.createNamedQuery("Donation.findForDonator", Donation.class)
                .setParameter("paramDonator", donator).getResultList();
    }

    public List<Donation> loadDonationsForDonatorAndEvent(Donator donator, Event e) {
        return em.createNamedQuery("Donation.findForDonatorAndEvent", Donation.class)
                .setParameter("paramDonator", donator)
                .setParameter("paramEvent", e)
                .getResultList();
    }

    public List<Donation> loadUnassignedDonationsForEvent(Event e) {
        return em.createNamedQuery("Donation.findByEventWithoutIncentive")
                .setParameter("paramEvent", e)
                .getResultList();
    }

    public double loadTotalDonationAmountForEvent(Event event) {
        try {
            return em.createNamedQuery("Donation.getTotalDonationAmountForEvent", Double.class)
                    .setParameter("paramEvent", event)
                    .getSingleResult();
        } catch(NullPointerException e){
            return 0.0;
        }
    }

    public String getInvoiceLabel(Donation donation){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String donationString = "Mr.Tiger-Tracker_" + sdf.format(donation.getDonationDate()) + donation.getId();
        
        return encrypString(donationString);
    }
    
    private String encrypString(String s){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(s.getBytes("UTF-8"));
            byte[] digest = md.digest();
            String output = Base64.getEncoder().encodeToString(digest);
            
            return output;
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }
    
    public Donator findDonatorByMail(String mailAddress) {
        try {
            return em.createNamedQuery("Donator.findByMail", Donator.class)
                    .setParameter("paramMail", mailAddress)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Donator saveDonator(Donator d) {
        return em.merge(d);
    }
}
