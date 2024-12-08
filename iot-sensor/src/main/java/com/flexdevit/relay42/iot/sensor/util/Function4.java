package com.flexdevit.relay42.iot.sensor.util;

@FunctionalInterface
public interface Function4<T, U, V, F, R> {

    R apply(T t, U u, V v, F f);

}
