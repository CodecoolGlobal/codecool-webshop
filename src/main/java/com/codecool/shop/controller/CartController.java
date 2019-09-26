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

@WebServlet(urlPatterns = {"/cart", "/cart-add", "/cart-remove", "/cart-remove-all"})
public class CartController extends HttpServlet {

    private Cart cart = new Cart();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String url = req.getServletPath();

        Reader reader = req.getReader();
        Gson gson = new Gson();

        int productId= getProductIdFromJSON(reader, gson);

        switch(url) {
            case "/cart-add":
                addProductToCart(resp, productId, session, gson);
                break;
            case "/cart-remove":
                removeProductFromCart(resp, productId, session, gson);
            case "/cart-remove-all":
                removeAllProductInstancesFromCart(resp, productId, session, gson);
                break;
        }

    }

    private void removeAllProductInstancesFromCart(HttpServletResponse resp, int productId, HttpSession session, Gson gson) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();

        cart.removeAllProductInstances(productDataStore.find(productId));
        session.setAttribute("cart", cart.getProductsInCart());

        Writer out = resp.getWriter();
        gson.toJson(productId, out);
    }

    private void removeProductFromCart( HttpServletResponse resp, int productId, HttpSession session, Gson gson) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();

        cart.removeProduct(productDataStore.find(productId));
        session.setAttribute("cart", cart.getProductsInCart());

        Writer out = resp.getWriter();
        gson.toJson(productId, out);
    }

    private int getProductIdFromJSON(Reader reader, Gson gson) throws IOException {
        String requestBodyJSON = org.apache.commons.io.IOUtils.toString(reader);
//        System.out.println(requestBodyJSON);

        Map requestBodyMap = gson.fromJson(requestBodyJSON, Map.class);
        return Integer.parseInt((String) requestBodyMap.get("product_id"));
    }

    private void addProductToCart(HttpServletResponse resp, int productId, HttpSession session, Gson gson) throws IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();

        cart.addProduct(productDataStore.find(productId));
        session.setAttribute("cart", cart.getProductsInCart());

        Writer out = resp.getWriter();
        gson.toJson(productId, out);
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
