package ru.otus.web.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.web.helpers.FileSystemHelper;
import ru.otus.web.service.TemplateProcessor;
import ru.otus.web.service.UserAuthService;
import ru.otus.web.servlet.AuthorizationFilter;
import ru.otus.web.servlet.ClientsServlet;
import ru.otus.web.servlet.LoginServlet;

import java.util.Arrays;


public class ClientsWebServerSimple implements ClientsWebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final UserAuthService authService;
    private final DBServiceClient dbServiceClient;
    protected final TemplateProcessor templateProcessor;
    private final Server server;

    public ClientsWebServerSimple(int port,
                                  UserAuthService userAuthService,
                                  DBServiceClient dbServiceClient,
                                  TemplateProcessor templateProcessor) {
        this.authService = userAuthService;
        this.dbServiceClient = dbServiceClient;
        this.templateProcessor = templateProcessor;
        this.server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (this.server.getHandlers().length == 0) {
            initContext();
        }
        this.server.start();
    }

    @Override
    public void join() throws Exception {
        this.server.join();
    }

    @Override
    public void stop() throws Exception {
        this.server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/clients"));

        this.server.setHandler(handlers);
        return this.server;
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String ...paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(
                new ServletHolder(new ClientsServlet(
                        this.templateProcessor,
                        this.dbServiceClient)),
                "/clients");
        return servletContextHandler;
    }
}
