package com.group3.model;

public interface Subject {
	void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
