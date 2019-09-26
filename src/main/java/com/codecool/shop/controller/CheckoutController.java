package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
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
import java.util.List;

//TODO format ugly ass shit checkout html
@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("product/checkout2.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String buyerName = req.getParameter("buyer-name");
        String buyerPhoneNumber = req.getParameter("buyer-phone-number");
        String buyerEmailAddress = req.getParameter("buyer-email");
        String buyerBillingAddress = formatBuyerAddress("billing", req);
        String buyerShippingAddress = formatBuyerAddress("shipping", req);

        HttpSession session = req.getSession();

        List<Product> cart = (List<Product>) session.getAttribute("cart");

        Order order = new Order(cart, buyerName, buyerPhoneNumber, buyerEmailAddress, buyerShippingAddress, buyerBillingAddress);
        session.setAttribute("order", order);

        resp.sendRedirect("/payment");
    }

    private String formatBuyerAddress(String addressType, HttpServletRequest req) {
        String buyerAddress;

        if (addressType == "shipping" && req.getParameter("buyer-shipping-city") == null) {
            return formatBuyerAddress("billing", req);
        }

        buyerAddress = req.getParameter("buyer-" + addressType + "-country");
        buyerAddress += ", " + req.getParameter("buyer-" + addressType + "-city");
        buyerAddress += ", " + req.getParameter("buyer-" + addressType + "-zip");
        buyerAddress += ", " + req.getParameter("buyer-" + addressType + "-address");

        return buyerAddress;
    }
}
