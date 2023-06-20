package org.example.task2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GetCommentsToLastPost {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        List<UserEntity> users = getUsers();
        if (users == null || users.isEmpty()) {
            System.out.println("No.");
            return;
        }

        UserEntity user = users.get(0);
        int userId = user.getId();

        List<PostEntity> posts = getPosts(userId);
        if (posts == null || posts.isEmpty()) {
            System.out.println("Пости не знайдені.");
            return;
        }

        PostEntity lastPost = posts.get(posts.size() - 1);
        int postId = lastPost.getId();

        List<CommentEntity> comments = getComments(postId);
        if (comments == null || comments.isEmpty()) {
            System.out.println("Коментарі не знайдені.");
            return;
        }

        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";
        writeToFile(fileName, comments);
        System.out.println("Коментарі успішно записані у файл " + fileName);
    }

    public static List<UserEntity> getUsers() {
        try {
            URL url = new URL(BASE_URL + "/users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Type userListType = new TypeToken<List<UserEntity>>() {
                    }.getType();
                    return gson.fromJson(response.toString(), userListType);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<PostEntity> getPosts(int userId) {
        try {
            URL url = new URL(BASE_URL + "/users/" + userId + "/posts");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Type postListType = new TypeToken<List<PostEntity>>() {
                    }.getType();
                    return gson.fromJson(response.toString(), postListType);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CommentEntity> getComments(int postId) {
        try {
            URL url = new URL(BASE_URL + "/posts/" + postId + "/comments");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Type commentListType = new TypeToken<List<CommentEntity>>() {
                    }.getType();
                    return gson.fromJson(response.toString(), commentListType);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void writeToFile(String fileName, List<CommentEntity> comments) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(gson.toJson(comments));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

