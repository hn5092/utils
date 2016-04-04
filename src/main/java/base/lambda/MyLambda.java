package base.lambda;
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}
