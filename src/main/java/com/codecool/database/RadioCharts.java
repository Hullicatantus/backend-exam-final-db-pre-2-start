package com.codecool.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RadioCharts {
    //TODO: implement this class
    private final String url;
    private final String user;
    private final String password;

    public RadioCharts(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public String getMostPlayedSong() {
        List<Song> songs = new ArrayList<>();
        String SQL = "SELECT artist, song FROM music_broadcast GROUP BY artist ORDER BY COUNT (times_aired) DESC LIMIT 1;";

        try (Connection connection = getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);

            while (resultSet.next()) {
                String title = resultSet.getString(1);
                Integer timesAired = resultSet.getInt(2);
                songs.add(new Song(title, timesAired));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();


        }
        if (songs.isEmpty()) {
            return "";
        }
        return songs.get(1).toString();
    }

    public String getMostActiveArtist() {
        List<Song> songs = new ArrayList<>();
        String SQL = "SELECT artist, song FROM music_broadcast GROUP BY artist ORDER BY COUNT (times_aired) DESC LIMIT 1;";

        try (Connection connection = getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);
        } catch (SQLException throwable) {
            throwable.printStackTrace();

        }
        return "";
    }
}
