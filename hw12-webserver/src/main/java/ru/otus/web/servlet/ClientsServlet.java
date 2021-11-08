package ru.otus.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.web.service.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_ALL_CLIENTS = "clients";

    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.dbServiceClient.saveClient(new Client(
                req.getParameter("clientName"),
                new Address(
                        req.getParameter("country").isEmpty() ? null : req.getParameter("country"),
                        req.getParameter("city").isEmpty() ? null : req.getParameter("city"),
                        req.getParameter("street").isEmpty() ? null : req.getParameter("street"),
                        req.getParameter("houseNumber").isEmpty() ? 0 : Integer.parseInt(req.getParameter("houseNumber")),
                        req.getParameter("buildingNumber").isEmpty() ? 0 : Integer.parseInt(req.getParameter("buildingNumber")),
                        req.getParameter("apartmentNumber").isEmpty() ? 0 : Integer.parseInt(req.getParameter("apartmentNumber"))),
                List.of(new Phone(req.getParameter("phone")))));
        refreshClientTable(resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        refreshClientTable(resp);
    }

    private void refreshClientTable(HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTR_ALL_CLIENTS, this.dbServiceClient.findAll());

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

}
