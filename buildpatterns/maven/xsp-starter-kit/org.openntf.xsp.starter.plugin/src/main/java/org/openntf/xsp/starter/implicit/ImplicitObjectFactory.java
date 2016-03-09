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

package org.openntf.xsp.starter.implicit;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.openntf.xsp.starter.Activator;

import com.ibm.xsp.context.FacesContextEx;
import com.ibm.xsp.util.TypedUtil;

public class ImplicitObjectFactory implements com.ibm.xsp.el.ImplicitObjectFactory {
	private final String[][] implicitObjectList = { { "server", ImplicitObject.class.getName() } };
	private final static boolean _debug = Activator._debug;

	public ImplicitObjectFactory() {
		if (_debug)
			System.out.println(getClass().getName() + " created");
	}

	public void createImplicitObjects(FacesContextEx context) {
		Map<String, Object> localMap = TypedUtil.getRequestMap(context.getExternalContext());
		localMap.put("server", new ImplicitObject());
	}

	public Object getDynamicImplicitObject(FacesContextEx context, String name) {
		return null;
	}

	public void destroyImplicitObjects(FacesContext context) {

	}

	public String[][] getImplicitObjectList() {
		return this.implicitObjectList;
	}

}
