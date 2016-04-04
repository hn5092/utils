package util;

public class ipToLong {
	public static void main(String[] args) {
		int[] ip = {101,230,11,19};
		System.out.println( (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3]);
	}
}
