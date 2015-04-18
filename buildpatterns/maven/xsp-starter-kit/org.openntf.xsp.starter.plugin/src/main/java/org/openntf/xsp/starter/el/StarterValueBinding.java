package org.openntf.xsp.starter.el;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;

import com.ibm.xsp.binding.ValueBindingEx;

public class StarterValueBinding extends ValueBindingEx {
	private String _expression;

	public StarterValueBinding() {
		super();
	}

	public StarterValueBinding(String expression) {
		super();
		_expression = expression;
	}

	@Override
	public Class<?> getType(FacesContext context) throws EvaluationException, PropertyNotFoundException {
		// TODO Insert your code that would determine the class to be returned
		// In this sample, we'll just always return a string
		return String.class;
	}

	@Override
	public Object getValue(FacesContext context) throws EvaluationException, PropertyNotFoundException {
		// TODO Insert your code that would generate the value to return
		// In this sample, we simply reflect the original expression...
		return _expression;
	}

	@Override
	public boolean isReadOnly(FacesContext context) throws EvaluationException, PropertyNotFoundException {
		// TODO Insert your code that determines whether the binding is readonly.
		// In this sample, we are always readonly
		return true;
	}

	@Override
	public void setValue(FacesContext context, Object value) throws EvaluationException, PropertyNotFoundException {
		// TODO Insert your code that does whatever you want from an active set on the value
		// In this sample, we do nothing

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
