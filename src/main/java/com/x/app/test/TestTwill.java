package com.x.app.test;


import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.twill.api.AbstractTwillRunnable;
import org.apache.twill.api.TwillController;
import org.apache.twill.api.TwillRunnerService;
import org.apache.twill.internal.Services;
import org.apache.twill.yarn.YarnTwillRunnerService;

import com.google.common.util.concurrent.Service;

public class TestTwill {
	static Log LOG = LogFactory.getLog(TestTwill.class);

	static class HelloWorldRunnable extends AbstractTwillRunnable {
		@Override
		public void run() {
			LOG.info("Hello World");
		}
	}

	public static void main(String[] args) {
		YarnConfiguration configuration = new YarnConfiguration();
		configuration.setSocketAddr("yarn.resourcemanager.address",
				new InetSocketAddress("192.168.80.103", 8032));
		TwillRunnerService trs = new YarnTwillRunnerService(configuration,
				"192.168.80.101:2181");
		trs.start();
		TwillController controller = trs.prepare(new HelloWorldRunnable()).start();
	}
}
