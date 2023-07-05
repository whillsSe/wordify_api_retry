package com.wordify.api.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.naming.NamingException;
import javax.sql.DataSource;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.controller.SearchEntriesController;
import com.wordify.api.service.EntryService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/entries/*")
public class searchEntriesServlet extends HttpServlet{
    private SearchEntriesController controller;
    @Override
    public void init() {
        try {
            ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
            EntryService service = new EntryService(ConnectionPool.getInstance());
            controller = new SearchEntriesController(executor,service);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        controller.handleGetRequest(req, resp);
    }
}
