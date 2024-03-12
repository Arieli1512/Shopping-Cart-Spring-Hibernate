package com.jakubw.zaip.Utils;

import com.jakubw.zaip.Models.Product;
import lombok.Getter;
import org.decimal4j.util.DoubleRounder;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();
    private int counter = 0;
    private double sum = 0;

    public void addItem(Product item) {
        getCartItemByItem(item).ifPresentOrElse(
                CartItem::increaseCounter,
                () -> cartItems.add(new CartItem(item))
        );
        recalculatePriceAndCounter();
    }

    public void decreaseItem(Product item) {
        Optional<CartItem> oCartItem = getCartItemByItem(item);
        if(oCartItem.isPresent()){
            CartItem cartItem = oCartItem.get();
            cartItem.decreaseCounter();
            if(cartItem.hasZeroItems()) {
                removeAllItems(item);
            }else {
                recalculatePriceAndCounter();
            }
        }
    }

    public void removeAllItems(Product item) {
        cartItems.removeIf(i -> i.idEquals(item));
        recalculatePriceAndCounter();
    }

    private void recalculatePriceAndCounter() {
        sum = DoubleRounder.round(cartItems.stream()
                .mapToDouble(CartItem::getPrice)
                .reduce(0, Double::sum),2);
        counter = cartItems.stream().mapToInt(CartItem::getCounter)
                .reduce(0, Integer::sum);
    }

    private Optional<CartItem> getCartItemByItem(Product item) {
        return cartItems.stream()
                .filter(i -> i.idEquals(item))
                .findFirst();
    }

    public void cleanCart() {
        cartItems.clear();
        counter = 0;
        sum = 0;
    }
}
