package java7.dynamic;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Before;
import org.junit.Test;

public class TestEngineManager {
	ScriptEngineManager sem;
	ScriptEngine js;
	ScriptContext context;

	@Before
	public void init() {
		ScriptEngineManager sem = new ScriptEngineManager();
		js = sem.getEngineByName("JavaScript");
		context = js.getContext();

	}
	/**
	 * 执行一个js的语句
	 * @throws ScriptException
	 */
	@Test
	public void testGetEngineManager() throws ScriptException {
		js.eval("var a = 10");
	}
	/**
	 * 如何进行变量的传递
	 */
	@Test
	public void testArgs() {
		context.setAttribute("name", "alex", ScriptContext.ENGINE_SCOPE);
	}
	/**
	 * 对一个脚本进行编译 编译之后的运行速度比之前的要快很多
	 * @throws ScriptException
	 */
	@Test
	public void testComplie() throws ScriptException{
		CompiledScript script = null ;
		if(js instanceof Compilable){
			 script = ((Compilable) js).compile("var a = 10");
		}
		if (script != null){
			script.eval();
			System.out.println("开始执行");
		}
	}
}
