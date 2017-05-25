/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.webapp.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;

/**
 *
 * @author cot
 */
@Named
@RequestScoped
public class MessageProvider {

	private static final String MESSAGEBUNDLE = "messages";

	private Locale locale;
	private ResourceBundle resourceBundle;

	/**
	 * Initialize the bean. This will set the default locale (from FacesContext or system default)
	 * and load the resource bundle.
	 */
	@PostConstruct
	public void init() {
		if (Faces.getFaceletContext() != null) {
			locale = Faces.getLocale();
		} else {
			// if the request is from the rest webservice, there is no locale
			locale = Locale.getDefault();
		}

		resourceBundle = getResourceBundle();
	}

	/**
	 * Get the ResourceBundle of the current locale set.
	 * 
	 * @return current ResourceBundle
	 */
	private ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(MESSAGEBUNDLE, locale);
	}

	/**
	 * Get the internationalized string for given key.
	 * 
	 * @param key
	 *            message-key
	 * @return the message / value from the resource bundle to the given key
	 */
	public String getString(String key) {
		return resourceBundle.getString(key);
	}

	/**
	 * Get the internationalized string for given key and replace the parameters in the string with
	 * given values.
	 * 
	 * @param key
	 *            message-key
	 * @param params
	 *            parameters to set in the string
	 * @return the message / value from the resource bundle to the given key with parameters set
	 */
	public String getStringWithParameters(String key, Object... params) {
		return MessageFormat.format(resourceBundle.getString(key), params);
	}
}