package org.openntf.xsp.starter.el;

import javax.faces.application.Application;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

import com.ibm.xsp.binding.BindingFactory;
import com.ibm.xsp.util.ValueBindingUtil;

public class StarterELBindingFactoryImpl implements BindingFactory {
	private final static String _prefix = "starter";

	@SuppressWarnings("rawtypes")
	public MethodBinding createMethodBinding(Application application, String expression, Class[] parameters) {
		String str = ValueBindingUtil.parseSimpleExpression(expression);
		return new StarterMethodBinding(str);
	}

	public ValueBinding createValueBinding(Application application, String expression) {
		String as[] = ValueBindingUtil.splitFormula(getPrefix(), expression);
		return new StarterValueBinding(as[0]);
	}

	public String getPrefix() {
		return _prefix;
	}

}
