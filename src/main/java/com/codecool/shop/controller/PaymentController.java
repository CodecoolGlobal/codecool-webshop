package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.Cart;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        Map<String, String[]> paymentDetails = req.getParameterMap();
        //paymentDetails.forEach((key, value) -> System.out.println("Key : " + key + " value : " + Arrays.toString(value)));

        session.setAttribute("payment details", paymentDetails);

        resp.sendRedirect("/order_confirmation");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        int totalPrice = 0;

        if (req.getSession().getAttribute("totalPrice") != null){
            totalPrice = (int) req.getSession().getAttribute("totalPrice");
        }

        context.setVariable("totalPrice", totalPrice);
        engine.process("product/payment.html", context, resp.getWriter());
    }
}
