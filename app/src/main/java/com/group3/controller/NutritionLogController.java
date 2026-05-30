package com.group3.controller;

import java.util.ArrayList;
import java.util.List;

import com.group3.model.DataConnection;
import com.group3.model.INutrition;
import com.group3.model.LogCollection;
import com.group3.model.NutritionLog;
import com.group3.model.Observer;
import com.group3.model.Subject;

public class NutritionLogController implements Subject {
	private DataConnection<LogCollection> nutritionDB;
    private INutrition nutrition;
    private List<Observer> observers;
    public NutritionLogController(DataConnection<LogCollection> nutritionDB, INutrition nutritionAPI) {
        this.nutritionDB = nutritionDB;
        this.nutrition = nutritionAPI;
        this.observers = new ArrayList<>();
    }
    // Look up nutrition product depend on product name
    public List<NutritionLog> lookupNutrition(String productName) {
        if (nutrition == null) {
            System.err.println("Nutrition API is not initialized.");
            return new ArrayList<>();
        }
        return nutrition.getNutritionInfo(productName); 
    }
    // Add that one to our nutrition log
    public boolean addNutritionLog(NutritionLog newNutritionLog) {
        if (newNutritionLog == null) {
            System.err.println("Cannot save a null nutrition log.");
            return false;
        }

        try {
            LogCollection currentData = nutritionDB.loadData();
            currentData.getNutritionLogs().add(newNutritionLog);
            boolean isSaved = nutritionDB.saveData(currentData);

            if (isSaved) {
                notifyObservers();
                System.out.println("Nutrition log added successfully: " + newNutritionLog.getProductName());
            } else {
                System.err.println("Unable to write nutrition log data to storage.");
            }
            return isSaved;

        } catch (Exception e) {
            System.err.println("Failed to add nutrition log: " + e.getMessage());
            return false;
        }
    }
    public boolean removeNutritionLog(int logID) {
        try {
            LogCollection currentData = nutritionDB.loadData();
            boolean isRemoved = currentData.getNutritionLogs().removeIf(log -> log.getLogID() == logID);
            
            if (isRemoved) {
                boolean saved = nutritionDB.saveData(currentData);
                if (saved) notifyObservers();
                return saved;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Failed to remove nutrition log: " + e.getMessage());
            return false;
        }
    }
    // Extra method for showing Nutrition Log
    public List<NutritionLog> getAllLogs() {
        return nutritionDB.loadData().getNutritionLogs();
    }
    @Override
	public void add(Observer o) {
		if (!observers.contains(o)) observers.add(o);
	}

	@Override
	public void remove(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}
}
