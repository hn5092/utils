package hadoop.yarn.yarnapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.junit.Test;

public class TestYarnAPI {
	private static final Log LOG = LogFactory.getLog(TestYarnAPI.class);
	
	public static void main(String[] args) {
		System.out.println("go");
		YarnClient yarnClient = YarnClient.createYarnClient();
		yarnClient.init(new Configuration());
		yarnClient.start();
		
	}
	@Test
	public void testServer(){
		
	}
}
