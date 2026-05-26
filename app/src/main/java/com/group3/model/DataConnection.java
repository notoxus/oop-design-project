package com.group3.model;

public interface DataConnection<T> {
	T loadData();
	boolean saveData(T data);
}
