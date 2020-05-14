package com.xyz.caofancpu.streamTest;


import com.xyz.caofancpu.core.NumberUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * FileName: ProductDataSource
 */

public class ProductDataSource {
    public static int size = 10;
    public static List<ProductItem> productItemList;

    static {
        productItemList = new ArrayList<>(size);
        initDataSource();
    }

    public static void initDataSource() {
        IntStream.range(1, size)
                .forEach(i -> {
                    BigDecimal price = BigDecimal.valueOf(NumberUtil.getInteger(1) + i + 0.0f);
                    String color = Color.getElementById((NumberUtil.getInteger(1) + i) % 4).getName();
                    int salesNum = NumberUtil.getInteger(1) + i * NumberUtil.getInteger(1);
                    productItemList.add(new ProductItem(i, price, color, salesNum));
                });
    }

    public static List<ProductItem> loadProductItemList() {
        return productItemList;
    }

    public enum Color {
        BLACK(0, "Black"),
        RED(1, "Red"),
        GREEN(2, "Green"),
        MAGENTA(3, "Magenta"),

        ;
        private int id;

        private String name;

        Color(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Color getElementById(int id) {
            return Arrays.stream(Color.values())
                    .filter(item -> item.getId() == id)
                    .findFirst()
                    .orElse(null);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
