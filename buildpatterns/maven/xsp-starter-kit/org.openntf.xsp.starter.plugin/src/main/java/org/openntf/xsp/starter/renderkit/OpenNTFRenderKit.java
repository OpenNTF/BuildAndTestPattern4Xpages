package org.openntf.xsp.starter.renderkit;

import java.io.OutputStream;
import java.io.Writer;

import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

import org.openntf.xsp.starter.Activator;
import com.sun.faces.renderkit.RenderKitImpl;

public class OpenNTFRenderKit extends RenderKitImpl {
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug)
			System.out.println(OpenNTFRenderKit.class.getName() + " loaded");
	}

	public OpenNTFRenderKit() {
		super();
		if (_debug) {
			System.out.println(getClass().getName() + " created");
		}
	}

	@Override
	public void addRenderer(String family, String rendererType, Renderer renderer) {
		super.addRenderer(family, rendererType, renderer);
	}

	@Override
	public ResponseStream createResponseStream(OutputStream out) {
		return super.createResponseStream(out);
	}

	@Override
	public ResponseWriter createResponseWriter(Writer writer, String contentTypeList, String characterEncoding) {
		return super.createResponseWriter(writer, contentTypeList, characterEncoding);
	}

	@Override
	public Renderer getRenderer(String family, String rendererType) {
		return super.getRenderer(family, rendererType);
	}

	@Override
	public ResponseStateManager getResponseStateManager() {
		return super.getResponseStateManager();
	}

}
