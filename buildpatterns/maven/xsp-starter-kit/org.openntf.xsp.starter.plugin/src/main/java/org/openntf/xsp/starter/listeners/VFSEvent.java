package org.openntf.xsp.starter.listeners;

import org.openntf.xsp.starter.Activator;

import com.ibm.commons.vfs.VFS;
import com.ibm.commons.vfs.VFSEventAdapter;

public class VFSEvent extends VFSEventAdapter {
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug)
			System.out.println(VFSEvent.class.getName() + " loaded");
	}

	public VFSEvent() {
		if (_debug) {
			System.out.println(getClass().getName() + " created");
		}
	}

	@Override
	public void onBeginRefresh(VFS vfs) {
		if (_debug) {
			System.out.println(getClass().getName() + " refresh beginning");
		}
		super.onBeginRefresh(vfs);
	}

	@Override
	public void onEndRefresh(VFS vfs) {
		super.onEndRefresh(vfs);
		if (_debug) {
			System.out.println(getClass().getName() + " refresh completed");
		}
	}

}
