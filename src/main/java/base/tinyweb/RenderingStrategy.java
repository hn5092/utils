package base.tinyweb;

import java.util.List;
import java.util.Map;

public interface RenderingStrategy {
	public String renderView(Map<String , List<String>> model);
}
