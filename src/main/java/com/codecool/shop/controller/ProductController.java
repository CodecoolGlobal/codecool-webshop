package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.Cart;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.BaseModel;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
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

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        setupContextForPage(context, req);
        engine.process("product/index.html", context, resp.getWriter());
    }

    private void setupContextForPage(WebContext context, HttpServletRequest req) {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        ProductCategory categoryToSearch = productCategoryDataStore.find(req.getParameter("category"));
        Supplier supplierToSearch = supplierDataStore.find(req.getParameter("supplier"));

        Map<BaseModel, List<Product>> results = new HashMap<>();
        List<Product> matches;

        HttpSession session = req.getSession();

        if (session.getAttribute("cart") != null) {
            int cartSize = ((List<Product>) session.getAttribute("cart")).size();
            context.setVariable("cart_size", cartSize);
        } else {
            context.setVariable("cart_size", 0);
        }


        if (categoryToSearch != null) {

            matches = productDataStore.getBy(categoryToSearch);
            results.put(categoryToSearch, matches);

            context.setVariable("searched", "category");
        }

        else if (supplierToSearch != null) {

            matches = productDataStore.getBy(supplierToSearch);
            results.put(supplierToSearch, matches);

            context.setVariable("searched", "supplier");
        }

        else {
            for (ProductCategory category : productCategoryDataStore.getAll()) {
                results.put(category, category.getFirstThreeProducts());
                // used instead of category.getFirstThreeProducts(), works only if categery has at least 3 products!!
            }
        }
        context.setVariable("results", results);
    }
}
