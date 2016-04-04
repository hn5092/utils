package com.x.tinyweb;

import java.util.List;
import java.util.Map;
/**
 * 代理RenderingStrategy类
 * @author imad
 *
 */
public class StrategyView implements View {
	private RenderingStrategy viewRender;

	public StrategyView(RenderingStrategy reStrategy) {
		this.viewRender = reStrategy;
	}

	@Override
	public String render(Map<String, List<String>> model) {
		return viewRender.renderView(model);
	}

}
