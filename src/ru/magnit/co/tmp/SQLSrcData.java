package ru.magnit.co.tmp;

import java.util.ArrayList;
import java.util.Vector;

public class SQLSrcData implements SrcData {
	Vector<Vector<String>> data;
	Vector<String> headers;
	Vector<Integer> types;
	private String url = null;
	private String server = null;
	private String user = null;
	private String password = null;
	private boolean trusted = false;
	private String logMech = null;
	
	
	public SQLSrcData() {
		headers = new Vector<>();
		headers.add("NoName");
		data = new Vector<Vector<String>>();
		types = new Vector<>();
		types.add(1);
	}
	@Override
	public Vector<String> getHeaders() {

		return this.headers;
	}

	@Override
	public Vector<Vector<String>> getData() {
		// TODO Auto-generated method stub
		return this.data;
	}

	@Override
	public Vector<Integer> getTypes() {
		// TODO Auto-generated method stub
		return this.types;
	}

}
