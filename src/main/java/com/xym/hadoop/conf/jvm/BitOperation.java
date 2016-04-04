package com.xym.hadoop.conf.jvm;

public class BitOperation {
	public static void main(String[] args) {
		int a = -6;
		for(int i = 0; i < 32; i++){
			int t =(a & 0x8000000>>>i)>>>(31-i);
			System.out.println(t);
		}
	}
}
