package com.x.tinyweb;

import java.util.List;
import java.util.Map;

public abstract class TemplateController implements Controller{
	private View view;
	public TemplateController(View view){
		this.view = view;
	}
	@Override
	public HttpResponse handleRequest(HttpRequest httpRequest) {
		Integer responseCode = 200;
		String responseBody = "";
		Map<String ,List<String> > model = doRequest(httpRequest);
		responseBody = view.render(model);
		return null;
	}
	abstract protected Map<String, List<String>> doRequest(HttpRequest httpRequest);
	

}
