package view;

import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI {

    private JFrame frame;
    private final JComboBox<String> productsList;
    private final JButton addToCartButton;
    private final JButton checkoutButton;
    private final JLabel offersLabel;
    private final JLabel keyboardOfferLabel;
    private final JLabel monitorOfferLabel;
    private final JLabel shippingOfferLabel;


    public GUI() {
        frame = new JFrame("Mini e-shop");
        frame.setBounds(350,200,730,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        productsList = new JComboBox<>();
        productsList.setBounds(50, 100, 100, 30);
        frame.add(productsList);

        addToCartButton = new JButton("Add to cart");
        addToCartButton.setBounds(160, 100, 100, 30);
        addToCartButton.setBackground(Color.gray);
        addToCartButton.setForeground(Color.cyan);
        frame.add(addToCartButton);

        checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(160, 150, 100, 30);
        checkoutButton.setBackground(Color.gray);
        checkoutButton.setForeground(Color.cyan);
        frame.add(checkoutButton);

        offersLabel = new JLabel("Special offers:");
        offersLabel.setBounds(300, 80, 300, 30);
        offersLabel.setForeground(Color.cyan);
        frame.add(offersLabel);

        monitorOfferLabel = new JLabel("Buy 2 Monitors and get a desk lamp at half price");
        monitorOfferLabel.setBounds(300, 110, 300, 30);
        monitorOfferLabel.setForeground(Color.cyan);
        frame.add(monitorOfferLabel);

        shippingOfferLabel = new JLabel("Buy any 2 items or more and get a maximum of $10 off shipping fees");
        shippingOfferLabel.setBounds(300, 140, 400, 30);
        shippingOfferLabel.setForeground(Color.cyan);
        frame.add(shippingOfferLabel);

        keyboardOfferLabel = new JLabel("Keyboards are 10% off");
        keyboardOfferLabel.setBounds(300, 170, 300, 30);
        keyboardOfferLabel.setForeground(Color.cyan);
        frame.add(keyboardOfferLabel);

        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setVisible(true);
    }

    public void addAddToCartActionListener(ActionListener actionListener) {
        addToCartButton.addActionListener(actionListener);
    }

    public void addCheckoutActionListener(ActionListener actionListener) {
        checkoutButton.addActionListener(actionListener);
    }

    public String getSelectedItem() {
        return (String) productsList.getSelectedItem();
    }

    public void populateComboBox(List<Product> products) {
        products.forEach(product -> productsList.addItem(product.getName()));
    }

}
