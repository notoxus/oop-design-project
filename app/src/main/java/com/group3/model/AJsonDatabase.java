package com.group3.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.lang.reflect.Type;

public abstract class AJsonDatabase<T> implements DataConnection<T> {
    protected final String filePath;
    protected final Gson gson;
    protected final Type typeOfT;
    // Default template constructor
    public AJsonDatabase(String filePath, Type typeOfT) {
        this.filePath = filePath;
        this.typeOfT = typeOfT;
        this.gson = createGson();
    }
    protected Gson createGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
    // Template pattern
    @Override
    public T loadData() {
        try (Reader reader = new FileReader(filePath)) {
            T data = gson.fromJson(reader, typeOfT);
            return data != null ? data : getDefaultValue();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath + ". Creating a default data set.");
            return getDefaultValue();
        } catch (IOException e) {
            System.err.println("Failed to read JSON file " + filePath + ": " + e.getMessage());
            return getDefaultValue();
        }
    }
    @Override
    public boolean saveData(T data) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to write JSON file " + filePath + ": " + e.getMessage());
            return false;
        }
    }
    protected abstract T getDefaultValue(); 
}
