package base.tinyweb;

public interface Controller {
	public HttpResponse handleRequest(HttpRequest httpRequest);
}
