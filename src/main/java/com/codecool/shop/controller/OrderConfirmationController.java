package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/order_confirmation"})
public class OrderConfirmationController extends HttpServlet {

    final String FROM = "webshopnigg4z@gmail.com";
    final String USERNAME = "webshopnigg4z@gmail.com";
    final String PASSWORD = "JavaIsGood";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/confirmation.html", context, resp.getWriter());
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        orderConfirmation(order, USERNAME, PASSWORD, FROM);
        gsonWriter(order);
        session.invalidate();
    }

    private void orderConfirmation(Order order, String username, String password, String from) {
        String to = order.getBuyerEmailAddress();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session sessionEmail = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
//
        try {
            Message message = new MimeMessage(sessionEmail);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("CodeCool Webshop");
            message.setText("Thank you for the order!" +
                    "\nYour order will be delivered within 2-3 workdays." +
                    "\n" +
                    "\nOrder details: " +
                    "\n" +
                    "\nName: " + order.getBuyerName() +
                    "\nPhone number: " + order.getBuyerPhoneNumber() +
                    "\nBilling address: " + order.getBuyerBillingAddress() +
                    "\nShipping address: " + order.getBuyerShippingAddress() +
                    "\n" +
                    "\nWebshop Niggaz");
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private void gsonWriter(Order order) throws IOException {
        Gson gson = new Gson();
        String orderToSave = gson.toJson(order);
        FileWriter file = new FileWriter("src/main/webapp/static/orders/order" + order.getBuyerName() + ".txt");

        try {
            file.write(orderToSave);
        } catch (IOException e){
            e.printStackTrace();

        } finally {
            file.flush();
            file.close();
        }
        order.getCart().clear();
    }


}
