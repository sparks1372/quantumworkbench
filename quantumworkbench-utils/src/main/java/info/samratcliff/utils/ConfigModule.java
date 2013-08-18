package info.samratcliff.utils;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.name.Names;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Sam Ratcliff
 * Date: 18/08/13
 * Time: 14:40
 */
public class ConfigModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigModule.class);
    private final String serviceName;

    @Inject
    public ConfigModule(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    protected void configure() {
        try (BufferedReader reader = new BufferedReader(new FileReader(serviceName + ".properties"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    String[] split = StringUtils.split("=");
                    if (split.length == 2) {
                        String configName = split[0];
                        String configValue = split[1];
                        bind(String.class).annotatedWith(Names.named(configName)).toInstance(configValue);
                    } else {
                        throw new IllegalArgumentException("each config line must contain a single key and a single value separated by =. " + line + " is malformed");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.warn("No properties file found for service " + serviceName);
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem reading configuration from " + serviceName + ".properties");
        }
    }
}
