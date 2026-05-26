package com.group3.controller;

import java.util.ArrayList;
import java.util.List;

import com.group3.model.DataConnection;
import com.group3.model.LogCollection;
import com.group3.model.Observer;
import com.group3.model.Subject;
import com.group3.model.WorkoutLog;

public class WorkoutLogController implements Subject {
	private DataConnection<LogCollection> workoutDB;
	private List<Observer> observers;
	
	public WorkoutLogController(DataConnection<LogCollection> workoutDB) {
		this.workoutDB = workoutDB;
		this.observers = new ArrayList<>();
	}
	@Override
	public void addObserver(Observer o) {
		if (!observers.contains(o)) observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}
	public boolean addWorkoutLog(WorkoutLog newWorkoutLog) {
		if (newWorkoutLog == null) {
			System.err.println("Lỗi: Không thể lưu một log rỗng!");
			return false;
		}
		try {
			LogCollection currentData = workoutDB.loadData();
			currentData.getWorkoutLogs().add(newWorkoutLog);
			boolean isSaved = workoutDB.saveData(currentData);
			if (isSaved) {
				notifyObservers();
			}
			return isSaved;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteWorkoutLog(int logID) {
		try {
			LogCollection currentData = workoutDB.loadData();
			boolean isRemoved = currentData.getWorkoutLogs().removeIf(log -> log.getLogID() == logID);

			if (isRemoved) {
				boolean saved = workoutDB.saveData(currentData);
				if (saved) notifyObservers();
				return saved;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public List<WorkoutLog> getAllLogs() {
	    return workoutDB.loadData().getWorkoutLogs();
	}
}
