package com.group3.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class JsonCategoryDatabase extends AJsonDatabase<List<ExerciseCategory>> {
	public JsonCategoryDatabase() {
        super("cat.json", new TypeToken<ArrayList<ExerciseCategory>>() {}.getType());
    }
	
	@Override
    protected List<ExerciseCategory> getDefaultValue() {
        return new ArrayList<>();
    }
}
