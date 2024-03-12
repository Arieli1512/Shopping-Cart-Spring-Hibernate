package com.jakubw.zaip.Utils;

import com.jakubw.zaip.Models.Product;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CartItem {
    private Product item;
    private int counter;
    private double price;

    public CartItem(Product item) {
        this.item = item;
        this.counter = 1;
        this.price = item.getPrice();
    }

    public void increaseCounter() {
        counter++;
        recalculate();
    }

    public void decreaseCounter() {
        if (counter > 0 ) {
            counter--;
            recalculate();
        }
    }

    public boolean hasZeroItems() {
        return counter == 0;
    }

    private void recalculate() {
        price = item.getPrice()*counter;
    }

    public boolean idEquals(Product item) {
        return this.item.getId().equals(item.getId());
    }
}
