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
        String SQL = "SELECT song, times_aired FROM music_broadcast;";

        try (Connection connection = getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);

            while (resultSet.next()) {
                String title = resultSet.getString(1);
                Integer timesAired = resultSet.getInt(2);
                Song song = getSong(songs, title);
                song.addAirTime(timesAired);
                songs.add(new Song(title, timesAired));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        if (songs.isEmpty()) {
            return "";
        }
        return mostPlayed(songs);
    }

    private String mostPlayed(List<Song> songs) {
        int max = Integer.MIN_VALUE;
        String winner = "";
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            if (song.getTimesAired() > max) {
                max = song.getTimesAired();
                winner = song.getTitle();
            }
        }
        return winner;
    }

    private Song getSong(List<Song> songs, String title) {
        Song newSong = new Song(title, 0);
        final int whereIsSong = songs.indexOf(newSong);
        if (whereIsSong == -1) {
            songs.add(newSong);
            return newSong;
        }
        return songs.get(whereIsSong);
    }

    public String getMostActiveArtist() {
        List<Artist> artists = new ArrayList<>();
        String SQL = "SELECT artist, song FROM music_broadcast;";

        try (Connection connection = getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String title = resultSet.getString(2);
                Artist artist = getArtist(artists, name);
                artist.addSongTitle(title);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();

        }
        if (artists.isEmpty()) {
            return "";
        }
        return mostActive(artists);
    }

    private String mostActive(List<Artist> artists) {
        int max = Integer.MIN_VALUE;
        String winner = "";
        for (int i = 0; i < artists.size(); i++) {
            Artist artist = artists.get(i);
            if (artist.getAmountOfSongs() > max) {
                max = artist.getAmountOfSongs();
                winner = artist.getName();
            }
        }
        return winner;
    }

    private Artist getArtist(List<Artist> artists, String name) {
        Artist newArtist = new Artist(name);
        final int whereIsArtist = artists.indexOf(newArtist);
        if (whereIsArtist == -1) {
            artists.add(newArtist);
            return newArtist;
        }
        return artists.get(whereIsArtist);
    }
}
