package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.Cart;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {
    double totalPrice = 0;

    private double getTotalPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Reader reader = req.getReader();
        Gson gson = new Gson();

        String requestBodyJSON = org.apache.commons.io.IOUtils.toString(reader);
        Map requestBodyMap = gson.fromJson(requestBodyJSON, Map.class);

        Writer out = resp.getWriter();
        gson.toJson(requestBodyJSON, out);

        return (double) requestBodyMap.get("total_price");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        Map<String, String[]> paymentDetails = req.getParameterMap();
        //paymentDetails.forEach((key, value) -> System.out.println("Key : " + key + " value : " + Arrays.toString(value)));


        if (req.getHeader("referer").contains("cart")) {
            totalPrice = getTotalPrice(req, resp);
            session.setAttribute("totalPrice", totalPrice);
        }

        session.setAttribute("payment details", paymentDetails);

        resp.sendRedirect("/order_confirmation");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());


        context.setVariable("totalPrice", totalPrice);
        engine.process("product/payment.html", context, resp.getWriter());
    }
}
