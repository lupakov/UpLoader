package ru.magnit.co.tmp;

import java.util.ArrayList;
import java.util.Vector;

public abstract class CommonSrcData implements SrcData {
private ArrayList<LoadEngineListener> listeners = new ArrayList<>();
	
	public void addListener(LoadEngineListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	public void removeListener(LoadEngineListener listener) {
		listeners.remove(listener);
	}

	protected void fireRowCount(int rowCount) {
		for(LoadEngineListener listener : listeners) {
			listener.onStateChanged(2, rowCount);
		}
	}



}
