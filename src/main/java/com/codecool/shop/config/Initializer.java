package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier apple = new Supplier("Apple", "All kinds of useless overpriced hardware");
        supplierDataStore.add(apple);
        Supplier samsung = new Supplier("Samsung", "All kinds of actually useful hardware. Still overpriced");
        supplierDataStore.add(samsung);
        Supplier tesla = new Supplier("Tesla", "The stuff Elon Musk makes when he's bored");
        supplierDataStore.add(tesla);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);

        ProductCategory desktop = new ProductCategory("Desktop", "Hardware", "Weird boxes with black magic happening inside.");
        productCategoryDataStore.add(desktop);

        ProductCategory smartphone = new ProductCategory("Smartphone", "Hardware", "That thing you use all day.");
        productCategoryDataStore.add(smartphone);

        ProductCategory car = new ProductCategory("Car", "Vehicles", "vroom vroom");
        productCategoryDataStore.add(car);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Apple Macbook", 1300, "USD", "It's a desktop machine.", desktop, apple));
        productDataStore.add(new Product("Apple Macbook 2", 1899, "USD", "It's a desktop machine but newer. 50% more power efficiency, also uses 50% more power. Only 46% more expensive", desktop, apple));
        productDataStore.add(new Product("Samsung Galaxy S nagyonsok X Pro 2019", 1000, "USD", "I'm running out of ideas.", smartphone, samsung));
        productDataStore.add(new Product("Self-driving car", 999999999, "USD", "If you're already living in 3019.", car, tesla));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
