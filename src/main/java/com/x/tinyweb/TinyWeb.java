package com.x.tinyweb;

import java.util.List;
import java.util.Map;

public class TinyWeb {
	private Map<String , Controller> controllers;
	private List<Filter> filters;
	public TinyWeb(Map<String , Controller> controllers, List<Filter> filters) {
		this.controllers = controllers;
		this.filters = filters;
	}
	public HttpResponse handleRequest(HttpRequest httpRequest){
		//不修改元数据
		HttpRequest currentRequest = httpRequest;
		for(Filter f : filters){
			currentRequest = f.dofilter(currentRequest);
		}
		Controller controller = controllers.get(currentRequest.getPath());
		if(null == controller){
			return null;
		}
		return controller.handleRequest(currentRequest);
	}
}
