/*
 * © Copyright GBS Inc 2011
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.openntf.xsp.starter.resolver;

import javax.faces.FacesException;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;

import org.openntf.xsp.starter.Activator;

import com.ibm.xsp.util.Delegation;

public class PropertyResolver extends javax.faces.el.PropertyResolver {
	protected final javax.faces.el.PropertyResolver _resolver;
	private final static boolean _debug = Activator._debug;

	public PropertyResolver() throws FacesException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		this._resolver = ((javax.faces.el.PropertyResolver) Delegation.getImplementation("property-resolver"));
		if (_debug) {
			System.out.println(getClass().getName() + " created using " + (_resolver != null ? _resolver.getClass().getName() : "null"));
		}
	}

	public PropertyResolver(javax.faces.el.PropertyResolver resolver) {
		if (_debug) {
			System.out.println(getClass().getName() + " created from delegate " + resolver.getClass().getName());
		}
		_resolver = resolver;
	}

	@Override
	public Object getValue(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.getValue(base, property);
	}

	@Override
	public Object getValue(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.getValue(base, index);
	}

	@Override
	public void setValue(Object base, Object property, Object value) throws EvaluationException,
			PropertyNotFoundException {
		// your code goes here
		_resolver.setValue(base, property, value);
	}

	@Override
	public void setValue(Object base, int index, Object value) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		_resolver.setValue(base, index, value);
	}

	@Override
	public boolean isReadOnly(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.isReadOnly(base, property);
	}

	@Override
	public boolean isReadOnly(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.isReadOnly(base, index);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getType(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.getType(base, property);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getType(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.getType(base, index);
	}

}
