package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
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
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        try {
            setupContextForPage(context, req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        engine.process("product/index.html", context, resp.getWriter());
    }

    private void setupContextForPage(WebContext context, HttpServletRequest req) throws SQLException {

        CartDaoJDBC cartDao = new CartDaoJDBC();
        ProductCategoryDaoJDBC productCategoryDao = new ProductCategoryDaoJDBC();
        SupplierDaoJDBC supplierDao = new SupplierDaoJDBC();

        ProductDaoJDBC productDao = new ProductDaoJDBC(supplierDao, productCategoryDao);


        ProductCategory categoryToSearch = productCategoryDao.find(req.getParameter("category"));
        Supplier supplierToSearch = supplierDao.find(req.getParameter("supplier"));

        Map<BaseModel, List<Product>> results = new HashMap<>();
        List<Product> matches;

        HttpSession session = req.getSession();

        if (session.getAttribute("cart") != null) {
            int cartSize = ((List<Product>) session.getAttribute("cart")).size();
            context.setVariable("cartSize", cartSize);
        }


        if (categoryToSearch != null) {

            matches = productDao.getBy("Category", categoryToSearch.getId());
            results.put(categoryToSearch, matches);

            context.setVariable("searched", "category");
        }

        else if (supplierToSearch != null) {

            matches = productDao.getBy("Supplier", supplierToSearch.getId());
            results.put(supplierToSearch, matches);

            context.setVariable("searched", "supplier");
        }

        else {
//            for (ProductCategory category : productCategoryDao.getAll()) {
//                results.put(category, category.getFirstThreeProducts());
//                // used instead of category.getFirstThreeProducts(), works only if categery has at least 3 products!!
//            }



            for (ProductCategory category : productCategoryDao.getAll()) {
                System.out.println(category);

                results.put(category, productDao.getBy("Category" ,category.getId()));
            }
        }
        context.setVariable("results", results);
    }
}
