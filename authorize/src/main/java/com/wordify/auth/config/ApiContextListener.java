package com.wordify.auth.config;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebListener
public class ApiContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        servletContextEvent.getServletContext().setAttribute("executor", executor);
        System.out.println("Executor service initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ExecutorService executor = (ExecutorService) servletContextEvent.getServletContext().getAttribute("executor");
        executor.shutdown();
    }
}
