package model;

public record Product(String name,
                      Double price,
                      ShippedFrom shippedFrom,
                      Double weight) {

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public ShippedFrom getShippedFrom() {
        return shippedFrom;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return name + " - $" + price + "\n";
    }

}
