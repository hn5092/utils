package com.x.lambda;

import scala.collection.mutable.StringBuilder;

public class StringCombiner {
	String delim;
	String prefix;
	String suffix;
	StringBuilder sb = new StringBuilder();
	boolean atStart;

	public StringCombiner(String delim, String prefix, String suffix) {
		this.delim = delim;
		this.prefix = prefix;
		this.suffix = suffix;
		atStart = true;
	}

	public StringCombiner add(String element) {
		if (atStart) {
			sb.append(prefix);
			atStart=false;
		}else{
			sb.append(delim);
		}
		sb.append(element);
		return this;
	}
	public StringCombiner meger(StringCombiner other){
		sb.append(other.sb);
		return this;
	}
	@Override
	public String toString() {
		sb.append(suffix);
		return this.sb.toString();
	}
}
