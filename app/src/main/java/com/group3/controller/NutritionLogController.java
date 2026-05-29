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
            System.err.println("API chưa được khởi tạo!");
            return new ArrayList<>();
        }
        return nutrition.getNutritionInfo(productName); 
    }
    // Add that one to our nutrition log
    public boolean addNutritionLog(NutritionLog newNutritionLog) {
        if (newNutritionLog == null) {
            System.err.println("Lỗi: Không thể lưu một nhật ký dinh dưỡng rỗng!");
            return false;
        }

        try {
            LogCollection currentData = nutritionDB.loadData();
            currentData.getNutritionLogs().add(newNutritionLog);
            boolean isSaved = nutritionDB.saveData(currentData);

            if (isSaved) {
                System.out.println("Đã thêm sản phẩm: " + newNutritionLog.getProductName() + " thành công!");
            } else {
                System.err.println("Lỗi: không thể ghi dữ liệu vào DB!");
            }
            return isSaved;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean removeNutritionLog(int logID) {
        try {
            LogCollection currentData = nutritionDB.loadData();
            boolean isRemoved = currentData.getNutritionLogs().removeIf(log -> log.getLogID() == logID);
            
            if (isRemoved) {
                return nutritionDB.saveData(currentData);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
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