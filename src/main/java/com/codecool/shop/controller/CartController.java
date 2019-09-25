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
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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
            for (Product product : cartProductList){
                if(productQuantities.containsKey(product)){
                    productQuantities.put(product, productQuantities.get(product) + 1);
                } else {
                    productQuantities.put(product, 1);
                }
            }
        }

        /*Map<Product, Long> productQuantities =
                cartProductList.stream().collect(
                        groupingBy(
                                x -> x, Collectors.counting()
                        )
                );*/

        context.setVariable("product_map", productQuantities);
        engine.process("product/payment.html", context, resp.getWriter());
    }
}
