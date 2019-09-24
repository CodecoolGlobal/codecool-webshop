package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.Cart;
import com.codecool.shop.dao.implementation.ProductDaoMem;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart", "/cart-add", "/cart-remove", "/cart-removeall"})
public class CartController extends HttpServlet {

    Cart cart = new Cart();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        int productId = Integer.parseInt(req.getParameter("product_id"));

        // !! as it stands, any post request sent here should pass a product_id param !!

        switch (url) {
            case "/cart":
                editCart(req, resp, productId, "add");
                resp.sendRedirect("/");
                break;
            case "/cart-add":
                editCart(req, resp, productId, "add");
                resp.sendRedirect("/cart");
                break;
            case "/cart-remove":
                editCart(req, resp, productId, "remove");
                resp.sendRedirect("/cart");
                break;
            case "/cart-removeall":
                editCart(req, resp, productId, "removeall");
                resp.sendRedirect("/cart");
                break;
        }
    }

    private void editCart(HttpServletRequest req, HttpServletResponse resp, int productId, String action) throws IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        Product product = productDataStore.find(productId);
        HttpSession session = req.getSession();

        switch (action) {
            case "add":
                cart.addProduct(product);
                break;
            case "remove":
                cart.removeProduct(product);
                break;
            case "removeall":
                cart.removeAllProductInstances(product);
                break;
        }

        session.setAttribute("cart", cart.getProductsInCart());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        HttpSession session = req.getSession();

        List<Product> cartProductList = (List<Product>) session.getAttribute("cart");
        Map<Product, Integer> productQuantities = new HashMap<>();
        int totalPrice = cart.getTotalPrice();

        if (cartProductList != null) {
            setupCart(cartProductList, productQuantities);
        }

        context.setVariable("total_price", totalPrice);
        context.setVariable("product_map", productQuantities);
        engine.process("product/cart.html", context, resp.getWriter());
    }

    private void setupCart(List<Product> productList, Map<Product, Integer> productQuantities) {
        for (Product product : productList){

            if (productQuantities.containsKey(product)) {
                productQuantities.put(product, productQuantities.get(product) + 1);
            } else {
                productQuantities.put(product, 1);
            }
        }
    }

}
