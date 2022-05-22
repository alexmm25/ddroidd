package model;

import java.util.HashMap;

public class Cart {

    private final HashMap<Product, Integer> products;

    public Cart() {
        products = new HashMap<>();
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void addToCart(Product product) {
        products.merge(product, 1, Integer::sum);
    }

    public String getCartProductsAsString() {
        StringBuilder stringBuilder = new StringBuilder("Shopping cart contents\n");
        products.keySet().forEach(product -> stringBuilder.append(product.getName()).append(" x").
                append(products.get(product)).append("\n"));
        return stringBuilder.toString();
    }

    public void clearCart() {
        products.clear();
    }

}
