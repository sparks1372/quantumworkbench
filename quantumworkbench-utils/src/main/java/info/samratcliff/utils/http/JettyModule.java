package info.samratcliff.utils.http;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;
import info.samratcliff.utils.Service;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

/**
 * Created with IntelliJ IDEA.
 * User: Sam Ratcliff
 * Date: 18/08/13
 * Time: 17:06
 */
public class JettyModule extends AbstractModule {
    public static class Props {
        public static final String PORT_NUMBER = "http.port";
    }

    @Override
    protected void configure() {
        Multibinder<Service> serviceMultiBinder = Multibinder.newSetBinder(binder(), Service.class);
        serviceMultiBinder.addBinding().to(HttpService.class);
    }

    @Provides
    public Server provideServer(final @Named(Props.PORT_NUMBER) String portNumber, Handler handler) {
        Preconditions.checkArgument(StringUtils.isNumeric(portNumber));
        Server server = new Server(Integer.parseInt(portNumber));
        server.setHandler(handler);
        return server;
    }

    @Provides
    public Handler provideHandler(Servlet servlet) {
        final ServletHolder servletHolder = new ServletHolder();
        final ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/");
        return contextHandler;
    }

    private class HttpService implements Service {
        final Server innerServer;

        @Inject
        private HttpService(Server innerServer) {
            this.innerServer = innerServer;
        }

        @Override
        public boolean start() {
            try {
                innerServer.start();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return innerServer.isStarted();
        }

        @Override
        public boolean stop() {
            try {
                innerServer.stop();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return innerServer.isStopped();
        }

        @Override
        public boolean isRunning() {
            return innerServer.isRunning();
        }
    }
}
