package com.group3.model;

import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class JsonExerciseDatabase extends AJsonDatabase<List<Exercise>> {
    public JsonExerciseDatabase() {
        super("exerciseLibrary.json", new TypeToken<ArrayList<Exercise>>() {}.getType());
    }

    @Override
    protected List<Exercise> getDefaultValue() {
        return new ArrayList<>();
    }
}