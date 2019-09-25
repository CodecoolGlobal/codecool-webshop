package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.Cart;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("product/checkout.html", context, resp.getWriter());
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

        buyerAddress = req.getParameter("buyer-" + addressType + "-country");
        buyerAddress += ", " + req.getParameter("buyer-" + addressType + "-city");
        buyerAddress += ", " + req.getParameter("buyer-" + addressType + "-zip");
        buyerAddress += ", " + req.getParameter("buyer-" + addressType + "-address");

        return buyerAddress;
    }
}
