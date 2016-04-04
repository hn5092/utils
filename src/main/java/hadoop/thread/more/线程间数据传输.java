package hadoop.thread.more;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class 线程间数据传输 {
	public static void main(String[] args) throws IOException {
		PipedReader in = new PipedReader();
		PipedWriter out = new PipedWriter();
		in.connect(out);
		Thread thread = new Thread(new Print(in), "printThread");
		thread.start();
		int recive = 0;
		try {
			while ((recive = System.in.read()) != -1) {
				out.write(recive);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			out.close();
		}

	}

	static class Print implements Runnable {
		private PipedReader in;

		public Print(PipedReader in) {
			this.in = in;

		}

		@Override 
		public void run() {
			// TODO Auto-generated method stub
			int recive = 0;
			try {
				while ((recive = in.read()) != -1) {
					System.out.println((char) recive);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}
}
