package info.samratcliff.utils;

import com.google.inject.Inject;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Sam Ratcliff
 * Date: 18/08/13
 * Time: 14:12
 */
public class ServiceManager implements Service {
    private Set<Service> managedServices;

    @Inject
    public ServiceManager(Set<Service> managedServices) {
        this.managedServices = managedServices;
    }


    @Override
    public boolean start() {
        boolean allSuccessful = false;
        for (Service innerService : managedServices) {
            allSuccessful = innerService.start() && allSuccessful;
        }
        return allSuccessful;
    }

    @Override
    public boolean stop() {
        boolean allSuccessful = false;
        for (Service innerService : managedServices) {
            allSuccessful = innerService.stop() && allSuccessful;
        }
        return allSuccessful;
    }

    @Override
    public boolean isRunning() {
        boolean allRunning = true;
        for (Service innerService : managedServices) {
            allRunning = allRunning && innerService.isRunning();
        }
        return allRunning;
    }
}
