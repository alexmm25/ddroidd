import model.Product;
import model.ShippedFrom;
import model.ShippingRate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PriceComputationTest {

    private final List<ShippingRate> shippingRates = new ArrayList<>();
    private final HashMap<Product, Integer> cart = new HashMap<>();

    @Before
    public void initializeRates() {
        shippingRates.add(new ShippingRate(ShippedFrom.RO, 1));
        shippingRates.add(new ShippingRate(ShippedFrom.UK, 2));
        shippingRates.add(new ShippingRate(ShippedFrom.US, 3));
    }

    @Before
    public void initializeCart() {
        cart.put(new Product("Mouse", 10.99, ShippedFrom.RO, 0.2), 1);
        cart.put(new Product("Monitor", 164.99, ShippedFrom.US, 1.9), 2);
    }

    @Test
    public void cartProductsPriceTest() {
        double productsPrice = 0;
        for(Map.Entry<Product, Integer> entry : cart.entrySet())
            productsPrice += entry.getKey().getPrice() * entry.getValue();
        Assert.assertEquals(340.97, productsPrice, 0.0001);
    }

    @Test
    public void shippingPriceTest() {
        AtomicReference<Double> shippingPrice = new AtomicReference<>((double) 0);
        cart.keySet().forEach(product ->
                shippingPrice.set(shippingPrice.get() + shippingRates.stream().filter(shippingRate ->
                        shippingRate.getCountry().equals(product.getShippedFrom())).findFirst().get().
                        getRate() * 10 * product.getWeight() * cart.get(product))
        );
        Assert.assertEquals(116.00, shippingPrice.get(), 0.0001);
    }
}
