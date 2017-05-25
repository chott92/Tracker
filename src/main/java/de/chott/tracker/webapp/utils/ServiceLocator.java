package de.chott.tracker.webapp.utils;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.ejb.SessionContext;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Utility class for EJBs. There's a {@link #getInstance(Class, Annotation...)} method which allows
 * you to lookup the current instance of a given EJB class from the JNDI context. This utility class
 * assumes that EJBs are deployed in the WAR as you would do in Java EE 6 Web Profile. For EARs,
 * you'd need to alter the <code>EJB_CONTEXT</code> to add the EJB module name or to add another
 * lookup() method.
 * 
 * @see <a
 *      href="http://balusc.blogspot.com/2011/09/communication-in-jsf-20.html#GettingAnEJBInFacesConverterAndFacesValidator">http://balusc.blogspot.com/2011/09/communication-in-jsf-20.html#GettingAnEJBInFacesConverterAndFacesValidator</a>
 * 
 * @author fah
 * 
 */
public final class ServiceLocator {

	private ServiceLocator() {
		// Utility class, so hide default constructor.
	}

	/**
	 * Get the managed instance of the given type from the BeanManager. This method can be used
	 * where dependency injection doesn't work (i.e. in a FacesConvert or inside a Listener class
	 * like RevListener, ...)
	 * 
	 * @param type
	 *            the type of the bean
	 * @return the managed bean instance
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(final Class<T> type, Annotation... qualifiers) {
		T result = null;
		try {
			// Access to the current context.
			InitialContext ctx = new InitialContext();
			// Resolve the bean manager
			BeanManager manager = (BeanManager) ctx.lookup("java:comp/BeanManager");
			// Retrieve all beans of that type
			Set<Bean<?>> beans = manager.getBeans(type, qualifiers);
			Bean<T> bean = (Bean<T>) manager.resolve(beans);
			if (bean != null) {
				CreationalContext<T> context = manager.createCreationalContext(bean);
				if (context != null) {
					result = (T) manager.getReference(bean, bean.getBeanClass(), context);
				}
			}
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * Get the current {@link SessionContext} via lookup.
	 * 
	 * @return the current SessionContext
	 */
	public static SessionContext getSessionContext() {
		try {
			InitialContext ic = new InitialContext();
			return (SessionContext) ic.lookup("java:comp/EJBContext");
		} catch (NamingException e) {
			throw new RuntimeException("Naming Exception occured.", e);
		}
	}
}