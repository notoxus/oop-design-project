package com.group3.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class OpenFoodFactsAPI {

	private static final String PREFIX_URL = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=";
	private static final String SEARCH_OPTIONS = "&search_simple=1&action=process&json=1&page_size=5";
	private static final String VI_LOCALE = "&cc=vn&lc=vi";
	private static final String EN_LOCALE = "&cc=us&lc=en";
	private static final HttpClient CLIENT = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

	public String fetchNutritionData(String productName) {
		if (productName == null || productName.isBlank()) {
			return null;
		}

		try {
			String encodedName = URLEncoder.encode(productName, StandardCharsets.UTF_8);
			String vietnameseResponse = fetchByLocale(encodedName, VI_LOCALE);
			if (!isEmptyResult(vietnameseResponse)) {
				return vietnameseResponse;
			}

			System.out.println("No Vietnamese OpenFoodFacts result. Retrying with English locale.");
			return fetchByLocale(encodedName, EN_LOCALE);

		} catch (Exception e) {
			System.err.println("OpenFoodFacts request failed: " + e.getMessage());
			return null;
		}
	}

	private String fetchByLocale(String encodedName, String localeQuery) throws Exception {
		String url = PREFIX_URL + encodedName + SEARCH_OPTIONS + localeQuery;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
				.header("User-Agent", "GymTrackingApp - Ver 1.0").timeout(Duration.ofSeconds(15)).GET().build();
		HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() < 200 || response.statusCode() >= 300) {
			System.err.println("OpenFoodFacts returned HTTP status " + response.statusCode());
			return null;
		}
		return response.body();
	}

	private boolean isEmptyResult(String responseBody) {
		return responseBody == null || responseBody.isBlank() || responseBody.contains("\"count\":0")
				|| responseBody.contains("\"products\":[]");
	}
}
