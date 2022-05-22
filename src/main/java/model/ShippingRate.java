package model;

public record ShippingRate(ShippedFrom country,
                           Integer rate) {

    public ShippedFrom getCountry() {
        return country;
    }

    public Integer getRate() {
        return rate;
    }

}
