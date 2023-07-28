package com.wordify.auth.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;

public abstract class AbstractController {

    private ExecutorService executor;

    public AbstractController(ExecutorService executor) {
        this.executor = executor;
    }

    protected void handleAsyncRequest(Callable<String> task, HttpServletResponse resp) throws java.io.IOException {
        Future<String> future = executor.submit(task);
        String json = null;
        try {
            json = future.get(); // 非同期タスクの実行とJSON化
        } catch (ExecutionException e) {
            // タスク内での例外
            //ExecutionExceptionは、Future.get()がthrowするエラーで、今回で言うtaskがthrowするエラーをラップしたもの。
            Throwable cause = e.getCause();
                            Logger logger = Logger.getLogger(AbstractController.class.getName());
                logger.info(cause.getMessage());
            if(cause instanceof LoginException){
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("unmatched parameters.");
            }else if(cause instanceof JwtException){
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("please re-login.");
            }else{
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            resp.getWriter().write(e.getCause().toString());
            return;
        } catch (InterruptedException e) {
            // タスクが中断された場合
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Task was interrupted");
            return;
        }
        resp.getWriter().write(json); // レスポンスに書き込む
    }
}
