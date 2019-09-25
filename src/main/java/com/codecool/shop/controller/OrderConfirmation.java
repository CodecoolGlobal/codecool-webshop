package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.test.Order;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(urlPatterns = {"/order_confirmation"})
public class OrderConfirmation extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        Order order = new Order();
        String orderToSave = gson.toJson(order);
        FileWriter file = new FileWriter("src/main/webapp/static/orders/order" + order.getId() + ".txt");

//        String to = "mrbrowngarden@gmail.com";
//        String from = "webshopnigg4z@gmail.com";
//        final String username = "webshopnigg4z@gmail.com";
//        final String password = "JavaIsGood";
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(to));
//            message.setSubject("Diamond SexShop");
//            message.setText("Thank you for your order! We will deliver it in 2-3 workdays." +
//                    "Diamond SexShop ");
//            Transport.send(message);
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }

        try {
            file.write(orderToSave);
        } catch (IOException e){
            e.printStackTrace();

        } finally {
            file.flush();
            file.close();
        }



        engine.process("product/confirmation.html", context, resp.getWriter());
    }
}
