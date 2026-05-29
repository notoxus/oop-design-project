package com.group3.model;

public interface Subject {
	void add(Observer o);
    void remove(Observer o);
    void notifyObservers();
}
