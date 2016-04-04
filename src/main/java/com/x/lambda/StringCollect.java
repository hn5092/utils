package com.x.lambda;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class StringCollect implements Collector<String, StringCombiner, String> {
	String delim;
	String prefix;
	String suffix;
	public StringCollect(String delim, String prefix, String suffix) {
		this.delim = delim;
		this.prefix = prefix;
		this.suffix = suffix;
	}

	/**
	 * 创建容器的工厂
	 */
	@Override
	public Supplier<StringCombiner> supplier() {
		return () -> new StringCombiner(this.delim, this.prefix, this.suffix);
	}

	/**
	 * 将当前元素叠加到收集器中
	 */
	@Override
	public BiConsumer<StringCombiner, String> accumulator() {
		return StringCombiner::add;
	}

	/**
	 * 合并两个容器
	 */
	@Override
	public BinaryOperator<StringCombiner> combiner() {
		// TODO Auto-generated method stub
		return StringCombiner::meger;
	}
	/**
	 * 返回收集操作的最终结果
	 */
	@Override
	public Function<StringCombiner,String> finisher() {
		return StringCombiner::toString;
	}
	
	@Override
	public Set characteristics() {
		return null;
	}

}
