package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.Connector;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    UserDaoJDBC userDaoJDBC = new UserDaoJDBC(Connector.getInstance());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        resp.setCharacterEncoding("UTF-8");
        engine.process("product/register.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("user-name");
        String password = req.getParameter("password");
        String Confirmpassword = req.getParameter("confirm-password");
        String email = req.getParameter("email");

        if (isRegistrationValid(userName, password, Confirmpassword, email)) {
            resp.sendRedirect("/");
        }
    }

    private boolean isRegistrationValid(String userName, String password, String confirmpassword, String email) {
        if ((userDaoJDBC.find(userName)).size() == 0) {
            if (password.equals(confirmpassword)) {
                userDaoJDBC.add(userName, hashPassword(password), email);
                return true;
            }

        }
        return false;
    }

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

}
