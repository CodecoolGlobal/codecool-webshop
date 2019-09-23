package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

//        Map<String, List<Product>> allCategories = new HashMap<>();
//        Map<String, List<Product>> allSuppliers = new HashMap<>();
        Map<String, List<Product>> result = new HashMap<>();

        String searchCategory = req.getParameter("category");
        String searchSupplier = req.getParameter("supplier");



        if (searchCategory != null) {
            ProductCategory foundProduct = productCategoryDataStore.find(searchCategory);
            result.put(foundProduct.getName(), foundProduct.getProducts());
        }

        else if (searchSupplier != null) {
            Supplier foundSupplier = supplierDataStore.find(searchSupplier);
            result.put(foundSupplier.getName(), foundSupplier.getProducts());
        }

        else {
            for (ProductCategory category : productCategoryDataStore.getAll()) {
                result.put(category.getName(), category.getProducts());
            }
        }


        context.setVariable("results", result);
        engine.process("product/index.html", context, resp.getWriter());

    }

}
