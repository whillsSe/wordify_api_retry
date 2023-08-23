package com.wordify.api.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.naming.NamingException;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.controller.DefinitionController;
import com.wordify.api.controller.EntriesController;
import com.wordify.api.service.definitionService.DefinitionService;
import com.wordify.api.service.definitionService.DefinitionServiceImpl;
import com.wordify.api.service.entryService.EntryService;
import com.wordify.api.service.entryService.EntryServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/entries/*")
public class EntriesServlet extends HttpServlet{
    private EntriesController entriesController;
    private DefinitionController definitionController;
    @Override
    public void init() {
        try {
            ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
            EntryService entryService = new EntryServiceImpl(ConnectionPool.getInstance());
            entriesController = new EntriesController(executor,entryService);
            DefinitionService definitionService = new DefinitionServiceImpl(ConnectionPool.getInstance());
            definitionController = new DefinitionController(executor,definitionService);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        entriesController.handleGetEntriesRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
        definitionController.handlePostRequest(req, res);
    }
    @Override
    protected void doPut(HttpServletRequest req,HttpServletResponse res)throws IOException{
        definitionController.handlePutRequest(req,res);
    }
    @Override
    protected void doDelete(HttpServletRequest req,HttpServletResponse res)throws IOException{
        definitionController.handleDeleteRequest(req,res);
    }
}
