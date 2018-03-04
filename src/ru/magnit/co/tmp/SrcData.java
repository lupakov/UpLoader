package ru.magnit.co.tmp;

import java.util.Vector;

public interface SrcData {
	public Vector<String> getHeaders();
	public Vector<Vector<String>> getData();
	public Vector<Integer> getTypes();

}
