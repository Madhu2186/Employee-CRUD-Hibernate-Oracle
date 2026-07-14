package com.employee.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Builds one SessionFactory for the whole application.
public class HibernateUtil {

	private static SessionFactory sessionFactory;

	static {
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}
