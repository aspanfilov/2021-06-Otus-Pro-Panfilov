package ru.otus.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.web.service.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_RANDOM_CLIENT = "randomClient";

    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
//        userDao.findRandomUser().ifPresent(randomUser -> paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, randomUser));
        this.dbServiceClient.getClient(1).ifPresent(client -> paramsMap.put(TEMPLATE_ATTR_RANDOM_CLIENT, client));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

}
