package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.*;


@WebListener
public class Initializer implements ServletContextListener {

    Connector connector = new Connector();
    DataSource dataSource = connector.connect();

    private CartDaoJDBC cartDao;
    private ProductCategoryDaoJDBC productCategoryDao;
    private ProductDaoJDBC productDao;
    private SupplierDaoJDBC supplierDao;

    public Initializer() throws SQLException {
            cartDao = new CartDaoJDBC(dataSource);
            productCategoryDao = new ProductCategoryDaoJDBC(dataSource);
            productDao = new ProductDaoJDBC(dataSource);
            supplierDao = new SupplierDaoJDBC(dataSource);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println(cartDao.testSelect());
        /*
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
        Supplier stofi = new Supplier("Stofi", "A phenomenon that nobody will ever really understand.");
        supplierDataStore.add(stofi);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "Thin, flat mobile computer with a touchscreen display");
        productCategoryDataStore.add(tablet);

        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "Weird flat boxes with black magic happening inside");
        productCategoryDataStore.add(laptop);

        ProductCategory smartphone = new ProductCategory("Smartphone", "Hardware", "That thing you use all day");
        productCategoryDataStore.add(smartphone);

        ProductCategory car = new ProductCategory("Car", "Vehicles", "vroom vroom");
        productCategoryDataStore.add(car);

        ProductCategory accessory = new ProductCategory("Accessory", "Whatever", "Literally just accessories");
        productCategoryDataStore.add(accessory);

        //setting up products and printing it

        // product_1
        productDataStore.add(new Product("Amazon Fire", 49.99f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));

        // product_2
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));

        // product_3
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));

        // product_4
        productDataStore.add(new Product("Apple Macbook", 1300, "USD", "It's a desktop machine.", laptop, apple));

        // product_5
        productDataStore.add(new Product("Apfel Macbuch Luft", 1899, "USD", "It's a desktop machine but newer. 50% more power efficiency, also uses 50% more power. Only 46% more expensive", laptop, apple));

        // product_6
        productDataStore.add(new Product("Samsung Galaxy S nagyonsok Pro X 2019.v42", 1000, "USD", "I'm running out of ideas.", smartphone, samsung));

        // product_7
        productDataStore.add(new Product("Self-driving car", 9999999, "USD", "If you're already living in 3019.", car, tesla));

        // product_8
        productDataStore.add(new Product("Wireless phone charger", 49, "USD", "More oomph for your phone, anywhere!", accessory, tesla));

        // product_9
        productDataStore.add(new Product("Tesla Shirt", 39, "USD", "Wearing this will double your IQ.", accessory, tesla));

        // product_10
        productDataStore.add(new Product("I don't even know what this is", 19, "USD", "some screen cleaner i guess", accessory, apple));

        // product_11
        productDataStore.add(new Product("Death Ray", 0, "USD", "Or the Peace Ray, as Tesla called it. " +
                "Tesla believed that by accelerating mercury isotopes to 48 times the speed of sound, the resulting " +
                "beam would produce enough energy to destroy entire armies at a distance limited only by " +
                "the curvature of the Earth.", accessory, tesla));

        // product_12
        productDataStore.add(new Product("Zsiguli", 195, "USD", "Some things just never get old.", car,  amazon));

        // product_13
        productDataStore.add(new Product("Bambuszfogpiszkalo", 0.79f, "USD", "Bamboozled.", accessory, stofi));

        // product_14
        productDataStore.add(new Product("Tompahegyu nyil", 19.99f, "USD", "Used by the Sami to hunt squirrels.", accessory, stofi));

        // product_15
        productDataStore.add(new Product("Mr. Bean's Mini", 60000, "USD", "Tiny, cozy, fit for purpose.", car, amazon));

        // product_16
        productDataStore.add(new Product("iPhone 11", 1099, "USD", "Now with revolutionary fidget spinner camera.", smartphone, apple));

        // product_17
        productDataStore.add(new Product("iLight 7s", 85, "USD", "For those who want to fit the world in their pockets.", smartphone, amazon));

        // product_18
        productDataStore.add(new Product("Dreamcom 10", 1800, "USD", "Fully adjustable. Looks ugly.", laptop, amazon));*/

        //TODO csutkapipa
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
