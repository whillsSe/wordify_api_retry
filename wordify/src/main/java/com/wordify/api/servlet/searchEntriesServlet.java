package com.wordify.api.servlet;

import java.util.concurrent.ExecutorService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/entries/*")
public class searchEntriesServlet extends HttpServlet{
    private SampleController controller;
    @Override
    public void init() {
        ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
        controller = new SampleController(executor);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        controller.handleRequest(req, resp);
    }
}
