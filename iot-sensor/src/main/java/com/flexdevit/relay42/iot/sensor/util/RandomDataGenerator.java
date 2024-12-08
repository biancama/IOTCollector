package com.flexdevit.relay42.iot.sensor.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public interface RandomDataGenerator {
    static double nextDouble(double origin, double bound) {
        var value =  ThreadLocalRandom.current().nextDouble(origin, bound);
        return round(value, 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
