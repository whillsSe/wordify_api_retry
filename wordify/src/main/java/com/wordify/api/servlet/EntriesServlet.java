package com.wordify.api.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.naming.NamingException;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.controller.SearchEntriesController;
import com.wordify.api.service.entryService.EntryService;
import com.wordify.api.service.entryService.EntryServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/entries/*")
public class EntriesServlet extends HttpServlet{
    private SearchEntriesController controller;
    @Override
    public void init() {
        try {
            ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
            EntryService service = new EntryServiceImpl(ConnectionPool.getInstance());
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
