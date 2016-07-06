package com.capgemini.analytics.samples;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public final class ConfigHelperJava {

    // My singleton instance
    private static ConfigHelperJava instance = new ConfigHelperJava();

    // Configuration container
    private final CompositeConfiguration configuration_ = new CompositeConfiguration();

    /**
     * Private constructor.
     * Base Setting for the Configuration container.
     */
    private ConfigHelperJava() {
        try {
            // Throw an exception instead of a null value if a key is not found
            // in the configuration container
            configuration_.setThrowExceptionOnMissing(true);

            // Add job.properties form the Classpath or Working directory
            configuration_.addConfiguration(new PropertiesConfiguration("job.properties"));

        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public final static String getString(final String key) {
        return instance.configuration_.getString(key);
    }

}
