package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.CheckoutDaoJDBC;
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


@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    CheckoutDaoJDBC checkout = new CheckoutDaoJDBC();

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
        int cartId = (int) session.getAttribute("cart_id");
        int userId = Integer.parseInt((String) session.getAttribute("user_id"));


        Order order = new Order(cartId, buyerName, buyerPhoneNumber, buyerEmailAddress, buyerShippingAddress, buyerBillingAddress, userId);
        checkout.add(order);
        session.setAttribute("order", order);

        resp.sendRedirect("/payment");
    }

    private String formatBuyerAddress(String addressType, HttpServletRequest req) {
        String buyerAddress;

        String valami =  req.getParameter("buyer-shipping-city");

        if (addressType.equals("shipping") && valami.equals("")) {
            return formatBuyerAddress("billing", req);
        }

        buyerAddress = req.getParameter("buyer-" + addressType + "-country");
        buyerAddress += ", " + req.getParameter("buyer-" + addressType + "-city");
        buyerAddress += ", " + req.getParameter("buyer-" + addressType + "-zip");
        buyerAddress += ", " + req.getParameter("buyer-" + addressType + "-address");

        return buyerAddress;
    }


}
