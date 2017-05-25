/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;

/**
 *
 * @author cot
 */
@Named
@Singleton
@Startup
public class TrackerApplication {

    private Logger log;
    private final String PROPERTY_FILE = "tracker.properties";
    private Properties properties;
    
    private String currentSystemHost;
    private String paypalEndpointHost;
    
    @PostConstruct
    public void startup() {
        log = Logger.getLogger(this.getClass().getName());
        loadExternalProperties();
    }

    private void loadExternalProperties() {
        String extConfigHome = System.getProperty("jboss.server.config.dir");
        if (extConfigHome == null) {
            log.log(Level.WARNING, "JBoss.server.config nicht gesetzt.");
            return;
        }

        String configPath = extConfigHome + File.separator + PROPERTY_FILE;
        configPath = configPath.replace("/", File.separator);
        configPath = configPath.replace(File.separator + File.separator, File.separator);
        log.log(Level.INFO, "Searching properties: {0}", configPath);

        InputStreamReader isr = null;
        try {
            Properties props = new Properties();
            isr = new InputStreamReader(new FileInputStream(configPath), "UTF-8");
            props.load(isr);
            properties = props;
        } catch (Exception e1) {
            log.log(Level.WARNING, "Not using external configuration.", e1.getMessage());
            return;
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    log.log(Level.WARNING, "Couldn't close config.");
                }
            }
        }
        parseProperties();
    }
    
    private void parseProperties(){
        if (properties!=null){
            currentSystemHost = properties.getProperty("domain.host");
            paypalEndpointHost = properties.getProperty("paypal.endpoint");
            
            String logMsg = "Set Domain Host to " + currentSystemHost + " and paypal endpoint to " + paypalEndpointHost;
            log.info(logMsg);
        }
    }

    public String getCurrentSystemHost() {
        return currentSystemHost;
    }

    public String getPaypalEndpointHost() {
        return paypalEndpointHost;
    }
    
}
