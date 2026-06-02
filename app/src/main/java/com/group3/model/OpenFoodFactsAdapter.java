package com.group3.model;

import java.util.ArrayList;
import java.util.List;
import java.io.StringReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.group3.util.OpenFoodFactsAPI;

public class OpenFoodFactsAdapter implements INutrition {

    private final OpenFoodFactsAPI api;

    public OpenFoodFactsAdapter() {
        this(new OpenFoodFactsAPI());
    }

    OpenFoodFactsAdapter(OpenFoodFactsAPI api) {
        this.api = api;
    }

    @Override
    public List<NutritionLog> getNutritionInfo(String productName) {
        List<NutritionLog> results = new ArrayList<>();
        if (productName == null || productName.isBlank()) {
            return results;
        }

        String jsonResponse = api.fetchNutritionData(productName);

        if (jsonResponse == null || jsonResponse.isBlank()) {
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
                JsonElement productElement = products.get(i);
                if (productElement == null || !productElement.isJsonObject()) {
                    continue;
                }

                JsonObject p = productElement.getAsJsonObject();
                JsonObject nutriments = getSafeObject(p, "nutriments");
                Double energy = getEnergyKcal(nutriments);
                Double protein = getFirstSafeDouble(nutriments, "proteins_100g", "proteins");
                Double fat = getFirstSafeDouble(nutriments, "fat_100g", "fat");
                Double carbohydrates = getFirstSafeDouble(nutriments, "carbohydrates_100g", "carbohydrates");

                if (energy == null && protein == null && fat == null && carbohydrates == null) {
                    continue;
                }

                NutritionLog item = new NutritionLog.Builder()
                        .setProductID((int) (System.currentTimeMillis() % 100000) + i)
                        .setProductName(resolveProductName(p, productName))
                        .setEnergy(energy)
                        .setProtein(protein)
                        .setFat(fat)
                        .setCarbohydrates(carbohydrates)
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

    private String resolveProductName(JsonObject product, String fallbackName) {
        String vietnameseName = getSafeString(product, "product_name_vi");
        if (vietnameseName != null) {
            return vietnameseName;
        }

        String englishName = getSafeString(product, "product_name_en");
        if (englishName != null) {
            return englishName;
        }

        String defaultName = getSafeString(product, "product_name");
        return defaultName != null ? defaultName : fallbackName;
    }

    private String getSafeString(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || obj.get(key).isJsonNull()) {
            return null;
        }
        try {
            String value = obj.get(key).getAsString().trim();
            return value.isEmpty() ? null : value;
        } catch (RuntimeException e) {
            return null;
        }
    }

    private JsonObject getSafeObject(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || obj.get(key).isJsonNull() || !obj.get(key).isJsonObject()) {
            return null;
        }
        return obj.getAsJsonObject(key);
    }

    private Double getEnergyKcal(JsonObject nutriments) {
        Double kcal = getFirstSafeDouble(nutriments, "energy-kcal_100g", "energy-kcal", "energy-kcal_serving");
        if (kcal != null) {
            return kcal;
        }

        Double energyKj = getFirstSafeDouble(nutriments, "energy_100g", "energy");
        return energyKj != null ? energyKj / 4.184 : null;
    }

    private Double getFirstSafeDouble(JsonObject obj, String... keys) {
        for (String key : keys) {
            Double value = getSafeDouble(obj, key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    private Double getSafeDouble(JsonObject obj, String key) {
        if (obj != null && obj.has(key) && !obj.get(key).isJsonNull()) {
            try {
                return obj.get(key).getAsDouble();
            } catch (NumberFormatException | UnsupportedOperationException | IllegalStateException e) {
                return null;
            }
        }
        return null;
    }
}
