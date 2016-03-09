package org.openntf.xsp.starter.el;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodNotFoundException;

import com.ibm.xsp.binding.MethodBindingEx;

public class StarterMethodBinding extends MethodBindingEx {
	private String _expression;

	public StarterMethodBinding() {
		super();
	}

	public StarterMethodBinding(String expression) {
		super();
		_expression = expression;
	}

	@Override
	public Class<?> getType(FacesContext context) throws MethodNotFoundException {
		// TODO Determine what your resulting type for this method binding is
		return null;
	}

	@Override
	public Object invoke(FacesContext context, Object[] arguments) throws EvaluationException, MethodNotFoundException {
		// TODO Whatever execution behavior you want with a return of whatever you want
		return null;
	}

	@Override
	public Object saveState(FacesContext context) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = super.saveState(context);
		arrayOfObject[1] = this._expression;
		return arrayOfObject;
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		Object[] arrayOfObject = (Object[]) state;
		super.restoreState(context, arrayOfObject[0]);
		this._expression = ((String) arrayOfObject[1]);
	}

}
