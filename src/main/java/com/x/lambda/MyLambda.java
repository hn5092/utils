package com.x.lambda;
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}
