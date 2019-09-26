package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.Cart;
import com.codecool.shop.dao.implementation.ProductDaoMem;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@WebServlet(urlPatterns = {"/cart", "/cart-add", "/cart-remove", "/cart-removeall"})
public class CartController extends HttpServlet {

    private Cart cart = new Cart();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        int productId= getProductIdFromJSON(req, resp);

        switch(url) {
            case "/cart-add":
                addProductToCart(req, resp, productId);
                break;
        }

    }

    private int getProductIdFromJSON(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Reader reader = req.getReader();
        Gson gson = new Gson();

        String requestBodyJSON = org.apache.commons.io.IOUtils.toString(reader);
        System.out.println(requestBodyJSON);

        Map<String, String> requestBodyMap = gson.fromJson(requestBodyJSON, Map.class);
        return Integer.parseInt(requestBodyMap.get("product_id"));

    }

    private void addProductToCart(HttpServletRequest req, HttpServletResponse resp, int productId) throws IOException {
        HttpSession session = req.getSession();
        ProductDao productDataStore = ProductDaoMem.getInstance();

        cart.addProduct(productDataStore.find(productId));
        session.setAttribute("cart", cart.getProductsInCart());

        Writer out = resp.getWriter();
        Gson gson = new Gson();

        gson.toJson(productId, out);
    }

    private void checkIfFiltered(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getHeader("referer");

        if (url.contains("category")) resp.sendRedirect(url);
        else if (url.contains("supplier")) resp.sendRedirect(url);
        else resp.sendRedirect("/");
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
        double totalPrice = cart.getTotalPrice();

        if (cartProductList != null) {
            setupCart(cartProductList, productQuantities);
        }

        session.setAttribute("totalPrice", totalPrice);

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
