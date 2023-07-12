package com.wordify.api.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.naming.NamingException;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.controller.GetContextController;
import com.wordify.api.service.contextService.ContextService;
import com.wordify.api.service.contextService.ContextServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/entries/*/context")
public class EntriesContxtServlet extends HttpServlet{
    private GetContextController controller;
    @Override
    public void init(){
        try {    
            ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
            ContextService service;
            service = new ContextServiceImpl(ConnectionPool.getInstance());
            controller = new GetContextController(executor, service);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException{
        controller.handleGetRequest(req, res);
    }
}
