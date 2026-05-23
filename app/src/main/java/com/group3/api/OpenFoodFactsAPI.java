package com.group3.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OpenFoodFactsAPI {

	private static final String PREFIX_URL = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=";
	private static final String SUFFIX_URL = "&search_simple=1&action=process&json=1&page_size=5&cc=vn&lc=vi";

	public String fetchNutritionData(String productName) {
		try {
			String encodedName = URLEncoder.encode(productName, StandardCharsets.UTF_8);
			String url = PREFIX_URL + encodedName + SUFFIX_URL;

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
					.header("User-Agent", "GymTrackingApp - Ver 1.0").GET().build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Return raw json to adapter
			return response.body();

		} catch (Exception e) {
			System.err.println("API Connected error: " + e.getMessage());
			return null;
		}
	}
}