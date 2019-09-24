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

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    Cart cart = new Cart();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductDao productDataStore = ProductDaoMem.getInstance();

        int addedProductId = Integer.parseInt(req.getParameter("product"));
        Product selectedProduct = productDataStore.find(addedProductId);

        HttpSession session = req.getSession();

        cart.addProduct(selectedProduct);
        session.setAttribute("cart", cart.getAddedProducts());

        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        HttpSession session = req.getSession();

        List<Product> cartProductList = (List<Product>) session.getAttribute("cart");

        Map<Product, Integer> productQuantities = new HashMap<>();

        if (cartProductList != null) {
            setupCart(cartProductList, productQuantities);

            if (req.getParameter("change-quantity") != null) {

                int quantityModifier = Integer.parseInt(req.getParameter("change-quantity"));
                editCart(cartProductList, productQuantities, quantityModifier);
            }
        }

        context.setVariable("product_map", productQuantities);
        engine.process("product/cart.html", context, resp.getWriter());
    }

    private void setupCart(List<Product> productList, Map<Product, Integer> productQuantities) {
        for (Product product : productList){

            if (productQuantities.containsKey(product)) {
                productQuantities.put(product, productQuantities.get(product) + 1);
            }

            else {
                productQuantities.put(product, 1);
            }
        }
    }

    private void editCart(List<Product> productList, Map<Product, Integer> productQuantities, int quantityModifier) {
        for (Product product : productList){

            if (productQuantities.containsKey(product)) {

                int newQuantity = productQuantities.get(product) + quantityModifier;

                if (newQuantity > 0) {
                    productQuantities.put(product, newQuantity);

                }
                else {
                    productQuantities.remove(product);
                    cart.removeProduct(product);
                }

                break;
            }
        }
    }
}
