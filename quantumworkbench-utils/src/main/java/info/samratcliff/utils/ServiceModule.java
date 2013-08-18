package info.samratcliff.utils;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Sam Ratcliff
 * Date: 18/08/13
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
public class ServiceModule extends AbstractModule {
    private final String serviceName;

    @Inject
    public ServiceModule(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    protected void configure() {
        //To change body of implemented methods use File | Settings | File Templates.

    }
}
