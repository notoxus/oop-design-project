package com.group3.model;

import java.util.ArrayList;
import java.util.List;
import java.io.StringReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.group3.util.OpenFoodFactsAPI;

public class OpenFoodFactsAdapter implements INutrition {

    private OpenFoodFactsAPI api;

    public OpenFoodFactsAdapter() {
        this.api = new OpenFoodFactsAPI();
    }

    @Override
    public List<NutritionLog> getNutritionInfo(String productName) {
        List<NutritionLog> results = new ArrayList<>();
        String jsonResponse = api.fetchNutritionData(productName);

        if (jsonResponse == null || jsonResponse.isBlank() || jsonResponse.contains("\"count\":0")) {
            System.out.println("No matching product found from OpenFoodFacts.");
            return results;
        }

        try {
            JsonObject root = parseJsonObject(jsonResponse);
            JsonArray products = root.getAsJsonArray("products");
            if (products == null || products.size() == 0) {
                return results;
            }

            for (int i = 0; i < products.size(); i++) {
                JsonObject p = products.get(i).getAsJsonObject();
                
                String realName = productName;
                if (p.has("product_name_vi") && !p.get("product_name_vi").isJsonNull() && !p.get("product_name_vi").getAsString().isEmpty()) {
                    realName = p.get("product_name_vi").getAsString();
                } else if (p.has("product_name_en") && !p.get("product_name_en").isJsonNull() && !p.get("product_name_en").getAsString().isEmpty()) {
                    realName = p.get("product_name_en").getAsString();
                } else if (p.has("product_name") && !p.get("product_name").isJsonNull()) {
                    realName = p.get("product_name").getAsString();
                }

                JsonObject nutriments = p.getAsJsonObject("nutriments");

                NutritionLog item = new NutritionLog.Builder()
                        .setProductID((int) (System.currentTimeMillis() % 100000) + i)
                        .setProductName(realName)
                        .setEnergy(getSafeDouble(nutriments, "energy-kcal_100g"))
                        .setProtein(getSafeDouble(nutriments, "proteins_100g"))
                        .setFat(getSafeDouble(nutriments, "fat_100g"))
                        .setCarbohydrates(getSafeDouble(nutriments, "carbohydrates_100g"))
                        .build();
                
                results.add(item);
            }

        } catch (Exception e) {
            System.err.println("Failed to parse OpenFoodFacts response: " + e.getMessage());
        }
        return results;
    }

    private JsonObject parseJsonObject(String jsonResponse) {
        JsonReader reader = new JsonReader(new StringReader(jsonResponse));
        reader.setLenient(true);
        return JsonParser.parseReader(reader).getAsJsonObject();
    }

    private Double getSafeDouble(JsonObject obj, String key) {
        if (obj != null && obj.has(key) && !obj.get(key).isJsonNull()) {
            try {
                return obj.get(key).getAsDouble();
            } catch (NumberFormatException | UnsupportedOperationException e) {
                return null;
            }
        }
        return null;
    }
}
