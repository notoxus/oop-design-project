package com.group3.controller;

import java.util.List;

import com.group3.model.DataConnection;
import com.group3.model.LogCollection;
import com.group3.model.WorkoutLog;

public class WorkoutLogController {
	private DataConnection<LogCollection> workoutDB;

	public WorkoutLogController(DataConnection<LogCollection> workoutDB) {
		this.workoutDB = workoutDB;
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
				System.out.println("Đã lưu log thành công, ID: " + newWorkoutLog.getLogID());
			} else {
				System.err.println("Lỗi khi ghi file JSON!");
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
				return workoutDB.saveData(currentData);
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<WorkoutLog> getAllLogs() {
	    return workoutDB.loadData().getWorkoutLogs();
	}
}
