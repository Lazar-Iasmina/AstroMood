# AstroMood - A Mood-Based Space Image Finder

## Introduction
AstroMood is a simple Java console application that fetches space images based on the user's mood. The program uses NASA's public image API to retrieve relevant space-related images that align with the user's emotional state. Whether you're feeling happy, sad, curious, or inspired, AstroMood will provide a celestial image to match your mood.

## Features
- Accepts user input for mood categories.
- Maps moods to predefined space-related themes.
- Retrieves images from NASA's public image API.
- Displays a relevant image URL for the user.
- Defaults to a general space image if an unrecognized mood is entered.

## Code Overview

### AstroMoodClient.java
This file contains the client-side logic for interacting with the NASA API and processing user mood inputs.

#### Key Sections:

##### Accepting User Input
```java
Scanner scanner = new Scanner(System.in);
System.out.println("How are you feeling today? (happy, sad, curious, inspired)");
String mood = scanner.nextLine().toLowerCase().trim();
```
- Prompts the user for their current mood.
- Converts input to lowercase and trims whitespace to ensure consistent handling.

##### Mapping Moods to Queries
```java
Map<String, String> moodThemes = new HashMap<>();
moodThemes.put("happy", "galaxy");
moodThemes.put("sad", "nebula");
moodThemes.put("curious", "mars");
moodThemes.put("inspired", "earth");

String query = moodThemes.getOrDefault(mood, "universe");
```
- Defines a mapping between moods and space-related themes.
- Uses a default theme (`universe`) if the mood is unrecognized.

##### Making the API Request
```java
String urlString = "https://images-api.nasa.gov/search?q=" + query;
URL url = new URL(urlString);
HttpURLConnection connection = (HttpURLConnection) url.openConnection();
connection.setRequestMethod("GET");
```
- Constructs a query URL for NASA's image API based on the user's mood.
- Sends an HTTP GET request to retrieve search results.

##### Parsing the API Response
```java
BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
StringBuilder response = new StringBuilder();
String line;
while ((line = in.readLine()) != null) {
    response.append(line);
}
in.close();
```
- Reads the API response and constructs a JSON string.

##### Extracting the First Image URL
```java
JSONObject jsonResponse = new JSONObject(response.toString());
JSONObject firstItem = jsonResponse.getJSONObject("collection")
    .getJSONArray("items")
    .getJSONObject(0)
    .getJSONArray("links")
    .getJSONObject(0);

String imageUrl = firstItem.getString("href");
```
- Parses the JSON response to extract the first available image link.

##### Displaying the Result
```java
System.out.println("\nBased on your mood (" + mood + "), here's an inspiring space image:");
System.out.println(imageUrl);
```
- Prints the retrieved image URL to the console.

## Running the Application
### Prerequisites
- Java Development Kit (JDK) installed
- An active internet connection
- A NASA API key (optional but recommended)

### Steps
1. **Obtain a NASA API Key (Optional, if required by the API)**
   - Visit [NASA API Portal](https://api.nasa.gov/) and sign up for an API key.
   - Replace the `API_KEY` value in the code with your key.

2. **Compile the Code:**
   ```sh
   javac AstroMoodClient.java
   ```

3. **Run the Program:**
   ```sh
   java AstroMoodClient
   ```

4. **Enter Your Mood:**
   - Example input:
     ```
     happy
     ```

5. **Receive an Image Link:**
   - Example output:
     ```
     Based on your mood (happy), here's an inspiring space image:
     https://example.nasa.gov/image.jpg
     ```

### Notes
- If an error occurs during API retrieval, ensure you have a working internet connection.
- If the API structure changes, you may need to update the JSON parsing logic.
- Extend functionality by supporting more moods or displaying images directly in a GUI.

This project provides a fun way to explore space imagery based on your mood. 🚀

