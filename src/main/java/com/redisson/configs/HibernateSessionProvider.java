package com.redisson.configs;

import com.google.inject.Provider;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.File;
import java.util.Objects;

public class HibernateSessionProvider implements Provider<SessionFactory> {
    private static Logger LOG = Logger.getLogger(HibernateSessionProvider.class);
    private static SessionFactory sessionFactory = null;

    public synchronized static void init(String fileName) {
        if (sessionFactory != null) {
            return;
        }

        Objects.requireNonNull(fileName, "configFileName is null");

        try {
            Configuration cfg = new Configuration();
            ServiceRegistry registry = null;

            File file = new File(fileName);
            if(!file.exists()) {
                String msg = String.format("Hibernate configuration file %s doesn't exist. Hibernate initialization is failed",
                        fileName);
                LOG.warn(msg);
                throw new RuntimeException(msg);
            }

            cfg.configure(file);

            registry = new ServiceRegistryBuilder()
                    .applySettings(cfg.getProperties()).buildServiceRegistry();

            sessionFactory = registry != null ? cfg.buildSessionFactory(registry) : null;
        } catch (Exception e) {
            LOG.error("init: Error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public SessionFactory get() {
        return sessionFactory;
    }

    public synchronized static void destroy() {
        if(sessionFactory == null) {
            return;
        }

        sessionFactory.close();
        sessionFactory = null;
    }
}
