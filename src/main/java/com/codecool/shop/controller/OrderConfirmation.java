package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.test.Order;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet(urlPatterns = {"/order_confirmation"})
public class OrderConfirmation extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        Order order = new Order();
        String orderToSave = gson.toJson(order);
        FileWriter file = new FileWriter("/home/mrbrown/codecool/gson/" + order.getId() + ".txt");

        try {
            file.write(orderToSave);
        } catch (IOException e){
            e.printStackTrace();

        } finally {
            file.flush();
            file.close();
        }



        engine.process("product/index.html", context, resp.getWriter());
    }
}
