package com.cardview.demo.model;

import java.util.HashMap;
import java.util.Map;



public enum LoanType {
    REFUNDABLE(1),
    NONREFUNDABLE(2);

    private int value;
    private static Map map = new HashMap<>();

    private LoanType(int value) {
        this.value = value;
    }

    static {
        for (LoanType pageType : LoanType.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static LoanType valueOf(int pageType) {
        return (LoanType) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}
