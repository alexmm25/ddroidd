package control;

import model.Cart;
import model.Product;
import model.ShippedFrom;
import model.ShippingRate;
import view.GUI;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Controller {

    private final List<Product> products;
    private final List<ShippingRate> shippingRates;
    private final GUI gui;
    private final Cart cart;
    private static DecimalFormat decimalFormat;
    private final HashMap<Product, Integer> productsMap;

    public Controller() {
        products = new ArrayList<>();
        addProductsToCatalog();
        displayProducts();
        shippingRates = new ArrayList<>();
        addShippingRatesToCatalog();
        cart = new Cart();
        gui = new GUI();
        gui.populateComboBox(products);
        decimalFormat = new DecimalFormat("0.00");
        addAddToCartActionListener();
        addCheckoutActionListener();
        productsMap = cart.getProducts();
    }

    private void displayProducts() {
        System.out.println("Catalog of products" + "\n");
        products.forEach(product -> System.out.println(product.toString()));
    }

    private void addProductsToCatalog() {
        products.add(new Product("Mouse", 10.99, ShippedFrom.RO, 0.2));
        products.add(new Product("Keyboard", 40.99, ShippedFrom.UK, 0.7));
        products.add(new Product("Monitor", 164.99, ShippedFrom.US, 1.9));
        products.add(new Product("Webcam", 84.99, ShippedFrom.RO, 0.2));
        products.add(new Product("Headphones", 59.99, ShippedFrom.US, 0.6));
        products.add(new Product("Desk Lamp", 89.99, ShippedFrom.UK, 1.3));
    }

    private void addShippingRatesToCatalog() {
        shippingRates.add(new ShippingRate(ShippedFrom.RO, 1));
        shippingRates.add(new ShippingRate(ShippedFrom.UK, 2));
        shippingRates.add(new ShippingRate(ShippedFrom.US, 3));
    }

    private void addAddToCartActionListener() {
        gui.addAddToCartActionListener(e -> {
            cart.addToCart(products.stream().filter(product ->
                    product.getName().equals(gui.getSelectedItem())).collect(Collectors.toList()).get(0));
            System.out.println(cart.getCartProductsAsString());
        });
    }

    private void addCheckoutActionListener() {
        gui.addCheckoutActionListener(e -> {
            double orderPrice = checkout() - applyDiscounts();
            System.out.println("\n" + "Total: $" + decimalFormat.format(orderPrice));
            cart.clearCart();
            JOptionPane.showMessageDialog(null, "Cart cleared!");
        });
    }

    private Double checkout() {
        double shippingPrice = computeShippingFee();
        double productsPrice = computeProductsPrice();
        double VAT = computeVAT();
        System.out.println( "Invoice" + "\n" +
                "Subtotal: $" + decimalFormat.format(productsPrice) + "\n" +
                "Shipping: $" + decimalFormat.format(shippingPrice) + "\n" +
                "VAT: $" + decimalFormat.format(VAT) + "\n");
        return productsPrice + shippingPrice + VAT;
    }

    private Double computeProductsPrice() {
        double productsPrice = 0;
        for (Map.Entry<Product, Integer> entry : productsMap.entrySet())
            productsPrice += entry.getKey().getPrice() * entry.getValue();
        return productsPrice;
    }

    private Double computeShippingFee() {
        AtomicReference<Double> shippingPrice = new AtomicReference<>((double) 0);
        productsMap.keySet().forEach(product ->
                shippingPrice.set(shippingPrice.get() + shippingRates.stream().filter(shippingRate ->
                        shippingRate.getCountry().equals(product.getShippedFrom())).findFirst().get().
                        getRate() * 10 * product.getWeight() * productsMap.get(product))
        );
        return shippingPrice.get();
    }

    private Double computeVAT() {
        AtomicReference<Double> VAT = new AtomicReference<>((double) 0);
        productsMap.keySet().forEach(product -> VAT.set(VAT.get() +
                productsMap.get(product) * product.getPrice() * 19 / 100));
        return VAT.get();
    }

    private Double applyDiscounts() {
        double discount = 0;
        System.out.println("Discounts:");
        discount += applyShippingDiscount();
        for (Map.Entry<Product, Integer> entry : productsMap.entrySet())
            discount += applyKeyboardDiscount(entry) + applyMonitorDiscount(entry);
        return discount;
    }

    private Double applyShippingDiscount() {
        double discount = 0;
        if (getCartSize(productsMap) >= 2) {
            double shippingFee = computeShippingFee();
            double shippingDiscount = shippingFee >= 10 ? 10 : shippingFee;
            System.out.println(shippingDiscount + "$ off shipping: -$" + shippingDiscount);
            discount += 10;
        }
        return discount;
    }

    private Double applyKeyboardDiscount(Map.Entry<Product, Integer> entry) {
        double discount = 0;
        if (entry.getKey().getName().equals("Keyboard")) {
            double keyboardDiscount = entry.getKey().getPrice() * entry.getValue() / 10;
            discount += keyboardDiscount;
            System.out.println("10% off keyboards: -$" + decimalFormat.format(keyboardDiscount));
        }
        return discount;
    }

    private Double applyMonitorDiscount(Map.Entry<Product, Integer> entry) {
        double discount = 0;
        if (entry.getKey().getName().equals("Monitor") && entry.getValue() >= 2) {
            Product deskLamp = null;
            try {
                deskLamp = productsMap.keySet().stream().filter(product ->
                        product.getName().equals("Desk Lamp")).collect(Collectors.toList()).get(0);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No lamp selected...");
            }
            if (deskLamp != null) {
                System.out.println("$44.99 off Desk Lamp");
                discount += 44.995;
            }
        }
        return discount;
    }

    private Integer getCartSize(HashMap<Product, Integer> productsMap) {
        Integer cartSize = 0;
        for (Map.Entry<Product, Integer> entry : productsMap.entrySet())
            cartSize += entry.getValue();
        return cartSize;
    }

}
