package ru.magnit.co.tmp;

import java.util.ArrayList;

public class LoadEngine {
	private ArrayList<LoadEngineListener> listeners = new ArrayList<>();
	
	public void addListener(LoadEngineListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	public void removeListener(LoadEngineListener listener) {
		listeners.remove(listener);
	}

	protected void fireCurrentRow(int row) {
		for(LoadEngineListener listener : listeners) {
			listener.onStateChanged(1, row);
		}
	}

}
