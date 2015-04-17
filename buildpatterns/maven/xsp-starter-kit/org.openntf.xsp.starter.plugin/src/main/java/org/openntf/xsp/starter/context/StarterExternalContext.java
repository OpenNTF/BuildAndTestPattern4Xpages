package org.openntf.xsp.starter.context;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;

import org.openntf.xsp.starter.Activator;

import com.ibm.xsp.context.Conversation;
import com.ibm.xsp.context.ExternalContextEx;
import com.ibm.xsp.domino.context.DominoExternalContext;

public class StarterExternalContext extends ExternalContextEx {
	private final DominoExternalContext delegate;
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug)
			System.out.println(StarterExternalContext.class.getName() + " loaded");
	}

	public StarterExternalContext(ExternalContext delegate) {
		super(delegate);
		if (delegate instanceof DominoExternalContext) {
			this.delegate = (DominoExternalContext) delegate;
		} else {
			throw new IllegalStateException();
		}
		if (_debug) {
			System.out.println(getClass().getName() + " created from " + delegate.getClass().getName());
		}
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getDelegate()
	 */
	@Override
	public ExternalContext getDelegate() {
		return delegate.getDelegate();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#getHomePage()
	 */
	@Override
	public String getHomePage() {
		return delegate.getHomePage();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#optimizeForDominoServer()
	 */
	public boolean optimizeForDominoServer() {
		return delegate.optimizeForDominoServer();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#optimizeForNotesClient()
	 */
	public boolean optimizeForNotesClient() {
		return delegate.optimizeForNotesClient();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#dominoDocumentUrls()
	 */
	public boolean dominoDocumentUrls() {
		return delegate.dominoDocumentUrls();
	}

	/**
	 * @param url
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#encodeActionURL(java.lang.String)
	 */
	@Override
	public String encodeActionURL(String url) {
		return delegate.encodeActionURL(url);
	}

	/**
	 * @param path
	 * @throws IOException
	 * @see com.ibm.xsp.context.ExternalContextEx#dispatch(java.lang.String)
	 */
	@Override
	public void dispatch(String path) throws IOException {
		delegate.dispatch(path);
	}

	/**
	 * @param name
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#encodeNamespace(java.lang.String)
	 */
	@Override
	public String encodeNamespace(String name) {
		return delegate.encodeNamespace(name);
	}

	/**
	 * @param url
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#encodeResourceURL(java.lang.String)
	 */
	@Override
	public String encodeResourceURL(String url) {
		return delegate.encodeResourceURL(url);
	}

	/**
	 * @param name
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getInitParameter(java.lang.String)
	 */
	@Override
	public String getInitParameter(String name) {
		return delegate.getInitParameter(name);
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getInitParameterMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getInitParameterMap() {
		return delegate.getInitParameterMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRemoteUser()
	 */
	@Override
	public String getRemoteUser() {
		return delegate.getRemoteUser();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequest()
	 */
	@Override
	public Object getRequest() {
		return delegate.getRequest();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestContextPath()
	 */
	@Override
	public String getRequestContextPath() {
		return delegate.getRequestContextPath();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestCookieMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getRequestCookieMap() {
		return delegate.getRequestCookieMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestHeaderMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getRequestHeaderMap() {
		return delegate.getRequestHeaderMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestHeaderValuesMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getRequestHeaderValuesMap() {
		return delegate.getRequestHeaderValuesMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestLocale()
	 */
	@Override
	public Locale getRequestLocale() {
		return delegate.getRequestLocale();
	}

	/**
	 * @param paramString
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#changeParameters(java.lang.String)
	 */
	@Override
	public void changeParameters(String paramString) {
		delegate.changeParameters(paramString);
	}

	/**
	 * @return
	 * @deprecated
	 * @see com.ibm.xsp.context.ExternalContextEx#getConversation()
	 */
	@Deprecated
	@Override
	public Conversation getConversation() {
		return delegate.getConversation();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getApplicationMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getApplicationMap() {
		return delegate.getApplicationMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getAuthType()
	 */
	@Override
	public String getAuthType() {
		return delegate.getAuthType();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getContext()
	 */
	@Override
	public Object getContext() {
		return delegate.getContext();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestLocales()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<Object> getRequestLocales() {
		return delegate.getRequestLocales();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getRequestMap() {
		return delegate.getRequestMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestParameterMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getRequestParameterMap() {
		return delegate.getRequestParameterMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestParameterNames()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<String> getRequestParameterNames() {
		return delegate.getRequestParameterNames();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestParameterValuesMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getRequestParameterValuesMap() {
		return delegate.getRequestParameterValuesMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestPathInfo()
	 */
	@Override
	public String getRequestPathInfo() {
		return delegate.getRequestPathInfo();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestServletPath()
	 */
	@Override
	public String getRequestServletPath() {
		return delegate.getRequestServletPath();
	}

	/**
	 * @param path
	 * @return
	 * @throws MalformedURLException
	 * @see com.ibm.xsp.context.ExternalContextEx#getResource(java.lang.String)
	 */
	@Override
	public URL getResource(String path) throws MalformedURLException {
		return delegate.getResource(path);
	}

	/**
	 * @param path
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getResourceAsStream(java.lang.String)
	 */
	@Override
	public InputStream getResourceAsStream(String path) {
		return delegate.getResourceAsStream(path);
	}

	/**
	 * @param path
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getResourcePaths(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<Object> getResourcePaths(String path) {
		return delegate.getResourcePaths(path);
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getResponse()
	 */
	@Override
	public Object getResponse() {
		return delegate.getResponse();
	}

	/**
	 * @param create
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getSession(boolean)
	 */
	@Override
	public Object getSession(boolean create) {
		return delegate.getSession(create);
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getSessionMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSessionMap() {
		return delegate.getSessionMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getUserPrincipal()
	 */
	@Override
	public Principal getUserPrincipal() {
		return delegate.getUserPrincipal();
	}

	/**
	 * @param role
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#isUserInRole(java.lang.String)
	 */
	@Override
	public boolean isUserInRole(String role) {
		return delegate.isUserInRole(role);
	}

	/**
	 * @param message
	 * @see com.ibm.xsp.context.ExternalContextEx#log(java.lang.String)
	 */
	@Override
	public void log(String message) {
		delegate.log(message);
	}

	/**
	 * @param message
	 * @param exception
	 * @see com.ibm.xsp.context.ExternalContextEx#log(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void log(String message, Throwable exception) {
		delegate.log(message, exception);
	}

	/**
	 * @param url
	 * @throws IOException
	 * @see com.ibm.xsp.context.ExternalContextEx#redirect(java.lang.String)
	 */
	@Override
	public void redirect(String url) throws IOException {
		delegate.redirect(url);
	}

	/**
	 * @param donotEncodeUrl
	 * @see com.ibm.xsp.context.ExternalContextEx#setDonotEncodeUrl(boolean)
	 */
	@Override
	public void setDonotEncodeUrl(boolean donotEncodeUrl) {
		delegate.setDonotEncodeUrl(donotEncodeUrl);
	}

}
