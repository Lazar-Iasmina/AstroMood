package org.example;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AstroMoodClient {
    private static final String API_KEY = "49aWoiuxdGfvhvBji8Nr57jZjckmFv0OIjBB3zne"; // Replace with your actual NASA API key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How are you feeling today? (happy, sad, curious, inspired)");
        String mood = scanner.nextLine().toLowerCase().trim();

        // Define mood-based queries
        Map<String, String> moodThemes = new HashMap<>();
        moodThemes.put("happy", "galaxy");
        moodThemes.put("sad", "nebula");
        moodThemes.put("curious", "mars");
        moodThemes.put("inspired", "earth");

        // Default to a general space image if mood is not recognized
        String query = moodThemes.getOrDefault(mood, "universe");

        try {
            String urlString = "https://images-api.nasa.gov/search?q=" + query;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            // Parse JSON response to get image details
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject firstItem = jsonResponse.getJSONObject("collection")
                    .getJSONArray("items")
                    .getJSONObject(0) // First search result
                    .getJSONArray("links")
                    .getJSONObject(0);

            String imageUrl = firstItem.getString("href");

            System.out.println("\nBased on your mood (" + mood + "), here's an inspiring space image:");
            System.out.println(imageUrl);
        } catch (Exception e) {
            System.err.println("Error retrieving data from NASA API. Please try again.");
            e.printStackTrace();
        }
    }
}
