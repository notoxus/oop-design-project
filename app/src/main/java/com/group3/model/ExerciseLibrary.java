package com.group3.model;

import java.util.ArrayList;
import java.util.List;

public class ExerciseLibrary implements Subject {
	private List<Exercise> lib;
	private transient List<Observer> observers;

	public ExerciseLibrary() {
		this.lib = new ArrayList<>();
	}

	public List<Exercise> getLib() {
		return lib;
	}

	@Override
	public void add(Observer o) {
		if (observers == null)
			observers = new ArrayList<>();
		if (!observers.contains(o))
			observers.add(o);
	}

	@Override
	public void remove(Observer o) {
		if (observers != null)
			observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		if (observers != null) {
			for (Observer o : observers) {
				o.update();
			}
		}
	}

	public void addExercise(Exercise exercise) {
		if (exercise != null) {
			lib.add(exercise);
			notifyObservers();
		}
	}

	public void removeExercise(Exercise exercise) {
		if (exercise != null) {
			lib.remove(exercise);
			notifyObservers();
		}
	}

	public List<Exercise> getByCategory(ExerciseCategory cat) {
		List<Exercise> result = new ArrayList<>();
		for (Exercise exercises : lib) {
			if (cat.equals(exercises.getCategory())) {
				result.add(exercises);
			}
		}
		return result;
	}

	public List<Exercise> getCategoryByName(String catName) {
		List<Exercise> result = new ArrayList<>();
		for (Exercise exercises : lib) {
			if (exercises.getCategory().getCatName().equalsIgnoreCase(catName)) {
				result.add(exercises);
			}
		}
		return result;
	}

	public Exercise searchExercise(String name) {
		for (Exercise exercises : lib) {
			if (exercises.getExerciseName().equalsIgnoreCase(name)) {
				return exercises;
			}
		}
		return null;
	}
}
