package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.Connector;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    UserDaoJDBC userDaoJDBC = new UserDaoJDBC(Connector.getInstance());


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("user-name");
        String password = req.getParameter("password");

        HashMap userDatas = userDaoJDBC.find(userName);
        HttpSession session = req.getSession();

        if(checkPass(password, (String) userDatas.get("password"))){
            HashMap userDetails = userDaoJDBC.find(userName);
            session.setAttribute("user_id", userDetails.get("id"));
            session.setAttribute("user_name", userDetails.get("userName"));
            resp.sendRedirect("/");
        }

    }

    private boolean checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            System.out.println("The password matches.");
            return true;
        }
        else {
            System.out.println("The password does not match.");
            return false;
        }
    }
}
