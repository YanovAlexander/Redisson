package com.redisson.configs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.redisson.cache.RedissonCacheManagerProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileNotFoundException;
import java.util.Objects;

public class AppConfig implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(AppConfig.class);

    private static final String LOG4J_CONFIG_LOC_PARAM_NAME = "log4jConfigLocation";
    private static final String LOG4J_CONFIG_LOC_PARAM_NAME_RESERVE = "log4jConfigLocationReserve";
    private static final String CACHE_CONFIG_PARAM_NAME = "cacheConfigLocation";
    private static final String CACHE_CONFIG_PARAM_NAME_RESERVE = "cacheConfigLocationReserve";
    private static final String HIBERNATE_CONFIG_PARAM_NAME = "hibernateConfigLocation";


    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {

            System.out.println("contextInitialized: Start (logger doesn't init yet)");
            ServletContext ctx = event.getServletContext();

            try {
                Log4JLogger.init(getLog4jConfigLocation(ctx));
                LOG.debug("Logger init already");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                Log4JLogger.init(getLog4jConfigLocationReserve(ctx));
                LOG.warn("Logger log4j start from WEB-INF properties");
            }
            LOG.debug("Logger init already");

            LOG.debug("Init redisson cache");
            try {
                RedissonCacheManagerProvider.init(getCacheConfigLocation(ctx));
            } catch (Exception e) {
                LOG.warn("Cache config not found in /etc/redisson/, will be using default config from WEB-INF");
                RedissonCacheManagerProvider.init(getCacheConfigLocationReserve(ctx));
            }

            LOG.debug("Init hibernate session factory");
            HibernateSessionProvider.init(getHibernateConfigLocation(ctx));

            LOG.debug("Create guice injector");
            SL.load(createInjector());

        } catch (RuntimeException e) {
            String msg = "contextInitialized: SL initialization is failed";
            LOG.error(msg);
            throw e;
        } catch (Throwable e) {
            String msg = "contextInitialized: SL initialization is failed";
            LOG.error(msg);
            throw new RuntimeException(e);
        }
    }

    private String getHibernateConfigLocation(ServletContext ctx) {
        Objects.requireNonNull(ctx, "ctx is null");

        return ctx.getInitParameter(HIBERNATE_CONFIG_PARAM_NAME);
    }

    private String getCacheConfigLocationReserve(ServletContext ctx) {
        Objects.requireNonNull(ctx, "ctx is null");

        return ctx.getRealPath(ctx.getInitParameter(CACHE_CONFIG_PARAM_NAME_RESERVE));
    }

    private String getCacheConfigLocation(ServletContext ctx) {
        Objects.requireNonNull(ctx, "ctx is null");

        return ctx.getInitParameter(CACHE_CONFIG_PARAM_NAME);
    }

    private String getLog4jConfigLocationReserve(ServletContext ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("getLog4jConfigLocationReserve method. ctx is null");
        }

        return ctx.getRealPath(ctx.getInitParameter(LOG4J_CONFIG_LOC_PARAM_NAME_RESERVE));
    }


    private String getLog4jConfigLocation(ServletContext ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("getLog4jConfigLocation method. ctx is null");
        }

        return ctx.getInitParameter(LOG4J_CONFIG_LOC_PARAM_NAME);
    }

    private Injector createInjector() {
        return Guice.createInjector(new DiConfig());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            LOG.debug("contextDestroyed: Destroy redisson cache");
            RedissonCacheManagerProvider.destroy();

            SL.destroy();
        } catch (Exception e) {
            LOG.error("contextDestroyed: error");
        }
    }

}
