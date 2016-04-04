package com.x.tinyweb;

import java.util.List;
import java.util.Map;
import java.util.Random;

import scala.collection.mutable.HashMap;

public class GreetingController extends TemplateController{
	private Random random;
	public GreetingController(View view) {
		super(view);
		random = new Random();
	}
	@Override
	protected Map<String, List<String>> doRequest(HttpRequest httpRequest) {
		HashMap<String , List<String> > model = new HashMap<String, List<String>>();
		model.put("greetings", generateGreetings(httpRequest.getBody()));
		return null;
	}
	private List<String> generateGreetings(String body) {
		return null;
	}

}
