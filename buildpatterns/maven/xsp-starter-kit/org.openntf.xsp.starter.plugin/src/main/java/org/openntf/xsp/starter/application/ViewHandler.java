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

package org.openntf.xsp.starter.application;

import java.io.IOException;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.openntf.xsp.starter.Activator;

public class ViewHandler extends com.ibm.xsp.application.ViewHandlerExImpl {
	private final javax.faces.application.ViewHandler _handler;
	private final static boolean _debug = Activator._debug;

	public ViewHandler(javax.faces.application.ViewHandler delegate) {
		super(delegate);
		_handler = delegate;
		if (_debug) {
			System.out.println(getClass().getName() + " created using " + (_handler != null ? _handler.getClass().getName() : "null"));
		}
	}

	@Override
	public Locale calculateLocale(FacesContext context) {
		// your code goes here
		return super.calculateLocale(context);
	}

	@Override
	public String calculateRenderKitId(FacesContext context) {
		// your code goes here
		return super.calculateRenderKitId(context);
	}

	@Override
	public UIViewRoot createView(FacesContext context, String viewId) {
		// your code goes here
		return super.createView(context, viewId);
	}

	@Override
	public String getActionURL(FacesContext context, String viewId) {
		// your code goes here
		return super.getActionURL(context, viewId);
	}

	@Override
	public String getResourceURL(FacesContext context, String path) {
		// your code goes here
		return super.getResourceURL(context, path);
	}

	@Override
	public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
		// your code goes here
		super.renderView(context, viewToRender);
	}

	@Override
	public UIViewRoot restoreView(FacesContext context, String viewId) {
		// your code goes here
		return super.restoreView(context, viewId);
	}

	@Override
	public void writeState(FacesContext context) throws IOException {
		// your code goes here
		super.writeState(context);
	}

}
