import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import static java.sql.Types.NULL;

public class populate {
    private static Connection connection;

    private static void dbConnection() {
        // Connect to the Database
        System.out.println("Connecting to Database ...");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Error Loading Driver: " + cnfe);
        }

        String hostName = "localhost";
        String dbName = "mouhu";
        int port = 1521;

        String oracleURL = "jdbc:oracle:thin:@" + hostName + ":" + port + ":" + dbName;

        String username = "scott";
        String password = "tiger";

        try {
            connection = DriverManager.getConnection(oracleURL, username, password);
            System.out.println("Database Connect Successful!");
        } catch (SQLException se) {
            System.out.println("Connection Error: " + se);
        }
    }

    private static void dropIndex() {
        try {
            Statement statement = connection.createStatement();

            String genreIndex = "DROP INDEX Genre_Index";
            String yearIndex = "DROP INDEX Year_Index";
            String countryIndex = "DROP INDEX Country_Index";
            String actorIndex = "DROP INDEX Actor_Index";
            String directorIndex = "DROP INDEX Director_Index";
            String tagIndex = "DROP INDEX Tag_Index";
            String movieTagIndex = "DROP INDEX MovieTag_Index";

            // Drop Index
            System.out.println("Dropping Index ...");

            statement.executeUpdate(movieTagIndex);
            statement.executeUpdate(tagIndex);
            statement.executeUpdate(directorIndex);
            statement.executeUpdate(actorIndex);
            statement.executeUpdate(countryIndex);
            statement.executeUpdate(yearIndex);
            statement.executeUpdate(genreIndex);

            System.out.println("Drop Index Successful!");
        } catch (SQLException se) {
            System.out.println("Don't need to Drop Index this time!");
        }
    }

    private static void clearData() {
        try {
            // Clear the data populated last time
            System.out.println("Clearing User_TaggedMovies ...");

            PreparedStatement deleteUserTagStatement = connection.prepareStatement(
                    "DELETE FROM UserTag_Movie"
            );
            deleteUserTagStatement.executeUpdate();
            System.out.println("Clear User_TaggedMovies Successful!");

            System.out.println("Clearing User_RatedMovies ...");

            PreparedStatement deleteUserRateStatement = connection.prepareStatement(
                    "DELETE FROM UserRate_Movie"
            );
            deleteUserRateStatement.executeUpdate();
            System.out.println("Clear User_RatedMovies Successful!");

            System.out.println("Clearing Movie_Tags ...");

            PreparedStatement deleteMovieTagStatement = connection.prepareStatement(
                    "DELETE FROM MovieHas_Tag"
            );
            deleteMovieTagStatement.executeUpdate();
            System.out.println("Clear Movie_Tags Successful!");

            System.out.println("Clearing Tags ...");

            PreparedStatement deleteTagStatement = connection.prepareStatement(
                    "DELETE FROM Tag"
            );
            deleteTagStatement.executeUpdate();
            System.out.println("Clear Tags Successful!");

            System.out.println("Clearing Movie_Directors ...");

            PreparedStatement deleteDirectorStatement = connection.prepareStatement(
                    "DELETE FROM Direct_Movie"
            );
            deleteDirectorStatement.executeUpdate();
            System.out.println("Clear Movie_Directors Successful!");

            System.out.println("Clearing Movie_Actors ...");

            PreparedStatement deleteActorStatement = connection.prepareStatement(
                    "DELETE FROM Cast_Movie"
            );
            deleteActorStatement.executeUpdate();
            System.out.println("Clear Movie_Actors Successful!");

            System.out.println("Clearing Movie_Locations ...");

            PreparedStatement deleteLocationStatement = connection.prepareStatement(
                    "DELETE FROM Location"
            );
            deleteLocationStatement.executeUpdate();
            System.out.println("Clear Movie_Locations Successful!");

            System.out.println("Clearing Movie_Genres ...");

            PreparedStatement deleteGenreStatement = connection.prepareStatement(
                    "DELETE FROM Genres"
            );
            deleteGenreStatement.executeUpdate();
            System.out.println("Clear Movie_Genres Successful!");

            System.out.println("Clearing Movies and Movie_Countries ...");

            PreparedStatement deleteMovieStatement = connection.prepareStatement(
                    "DELETE FROM Movie"
            );
            deleteMovieStatement.executeUpdate();
            System.out.println("Clear Movies and Movie_Countries Successful!");
        } catch (SQLException se) {
            System.out.println("Error Clearing Data: " + se);
        }
    }

    private static void populateMovie() throws Exception{
        // Populate movies.dat and movie_countries.dat
        System.out.println("Populating Movies and Movie_Countries ...");

        // Populate movies.dat
        BufferedReader movieReader = new BufferedReader(new FileReader("./dataSet/movies.dat"));
        String movieLine;
        PreparedStatement movieStatement = connection.prepareStatement(
                "INSERT INTO Movie VALUES ( ?, ?, NULL, ?, NULL, NULL, ?, ? )"
        );

        movieReader.readLine();

        while ((movieLine = movieReader.readLine()) != null) {
            String[] movieText = movieLine.split("\t");

            if (movieText[17].equals("\\N") || movieText[18].equals("\\N")) {
                movieStatement.setInt(1, Integer.parseInt(movieText[0]));
                movieStatement.setString(2, movieText[1]);
                movieStatement.setInt(3, Integer.parseInt(movieText[5]));
                movieStatement.setDouble(4, NULL);
                movieStatement.setInt(5, NULL);
            } else {
                movieStatement.setInt(1, Integer.parseInt(movieText[0]));
                movieStatement.setString(2, movieText[1]);
                movieStatement.setInt(3, Integer.parseInt(movieText[5]));
                movieStatement.setDouble(4, Double.parseDouble(movieText[17]));
                movieStatement.setInt(5, Integer.parseInt(movieText[18]));
            }

            movieStatement.executeUpdate();
        }

        // Populate movie_countries.dat
        BufferedReader countryReader = new BufferedReader(new FileReader("./dataSet/movie_countries.dat"));
        String countryLine;
        PreparedStatement countryStatement = connection.prepareStatement(
                "UPDATE Movie SET Country = ? WHERE Series_Num = ?"
        );

        countryReader.readLine();

        while ((countryLine = countryReader.readLine()) != null) {
            String[] countryText = countryLine.split("\t");

            if (countryText.length == 2) {
                countryStatement.setString(1, countryText[1]);
                countryStatement.setInt(2, Integer.parseInt(countryText[0]));
            }

            countryStatement.executeUpdate();
        }

        System.out.println("Populate Movies and Movie_Countries Successful!");
    }

    private static void populateGenre() throws Exception {
        // Populate movie_genres.dat
        System.out.println("Populating Movie_Genres ...");

        BufferedReader genreReader = new BufferedReader(new FileReader("./dataSet/movie_genres.dat"));
        String genreLine;
        PreparedStatement genreStatement = connection.prepareStatement(
                "INSERT INTO Genres VALUES (?, ?)"
        );

        genreReader.readLine();

        while ((genreLine = genreReader.readLine()) != null) {
            String[] genreText = genreLine.split("\t");

            genreStatement.setString(2, genreText[1]);
            genreStatement.setInt(1, Integer.parseInt(genreText[0]));

            genreStatement.executeUpdate();
        }

        System.out.println("Populate Movie_Genres Successful!");
    }

    private static void populateLocation() throws Exception {
        // Populate movie_locations.dat
        System.out.println("Populating Movie_Locations ...");

        BufferedReader locationReader = new BufferedReader(new FileReader("./dataSet/movie_locations.dat"));
        String locationLine;
        PreparedStatement locationStatement = connection.prepareStatement(
                "INSERT INTO Location VALUES (?, ?, ?, ?, ?)"
        );

        locationReader.readLine();

        while ((locationLine = locationReader.readLine()) != null) {
            String[] locationText = locationLine.split("\t");
            String[] locationTemp = new String[5];

            for (int i = 0; i < locationText.length; i++)
                locationTemp[i] = locationText[i];

            locationStatement.setInt(1, Integer.parseInt(locationTemp[0]));
            locationStatement.setString(2, locationTemp[1]);
            locationStatement.setString(3, locationTemp[2]);
            locationStatement.setString(4, locationTemp[3]);
            locationStatement.setString(5, locationTemp[4]);

            locationStatement.executeUpdate();
        }

        System.out.println("Populate Movie_Locations Successful!");
    }

    private static void populateActor() throws Exception {
        // Populate movie_actors.dat
        System.out.println("Populating Movie_Actors ...");

        BufferedReader actorReader = new BufferedReader(new FileReader("./dataSet/movie_actors.dat"));
        String actorLine;
        PreparedStatement actorStatement = connection.prepareStatement(
                "INSERT INTO Cast_Movie VALUES (?, ?, ?, NULL)"
        );

        actorReader.readLine();

        while ((actorLine = actorReader.readLine()) != null) {
            String[] actorText = actorLine.split("\t");

            actorStatement.setString(1, actorText[1]);
            actorStatement.setInt(2, Integer.parseInt(actorText[0]));
            actorStatement.setString(3, actorText[2]);

            actorStatement.executeUpdate();
        }

        System.out.println("Populate Movie_Actors Successful!");
    }

    private static void populateDirector() throws Exception {
        // Populate movie_directors.dat
        System.out.println("Populating Movie_Directors ...");

        BufferedReader directorReader = new BufferedReader(new FileReader("./dataSet/movie_directors.dat"));
        String directorLine;
        PreparedStatement directorStatement = connection.prepareStatement(
                "INSERT INTO Direct_Movie VALUES (?, ?, ?)"
        );

        directorReader.readLine();

        while ((directorLine = directorReader.readLine()) != null) {
            String[] directorText = directorLine.split("\t");

            directorStatement.setString(3, directorText[2]);
            directorStatement.setInt(2, Integer.parseInt(directorText[0]));
            directorStatement.setString(1, directorText[1]);

            directorStatement.executeUpdate();
        }

        System.out.println("Populate Movie_Directors Successful!");
    }

    private static void populateTag() throws Exception {
        // Populate tags.dat
        System.out.println("Populating Tags ...");

        BufferedReader tagReader = new BufferedReader(new FileReader("./dataSet/tags.dat"));
        String tagLine;
        PreparedStatement tagStatement = connection.prepareStatement(
                "INSERT INTO Tag VALUES (?, ?)"
        );

        tagReader.readLine();

        while ((tagLine = tagReader.readLine()) != null) {
            String[] tagText = tagLine.split("\t");

            tagStatement.setInt(1, Integer.parseInt(tagText[0]));
            tagStatement.setString(2, tagText[1]);

            tagStatement.executeUpdate();
        }

        System.out.println("Populate Tags Successful!");
    }

    private static void populateMovieTag() throws Exception {
        // Populate movie_tags.dat
        System.out.println("Populating Movie_Tags ...");

        BufferedReader movieTagReader = new BufferedReader(new FileReader("./dataSet/movie_tags.dat"));
        String movieTagLine;
        PreparedStatement movieTagStatement = connection.prepareStatement(
                "INSERT INTO MovieHas_Tag VALUES (?, ?, ?)"
        );

        movieTagReader.readLine();

        while ((movieTagLine = movieTagReader.readLine()) != null) {
            String[] movieTagText = movieTagLine.split("\t");

            movieTagStatement.setInt(1, Integer.parseInt(movieTagText[0]));
            movieTagStatement.setInt(2, Integer.parseInt(movieTagText[1]));
            movieTagStatement.setInt(3, Integer.parseInt(movieTagText[2]));

            movieTagStatement.executeUpdate();
        }

        System.out.println("Populate Movie_Tags Successful!");
    }

    private static void populateUserRate() throws Exception {
        // Populate user_ratedmovies.dat
        System.out.println("Populating User_RatedMovies ...");

        BufferedReader userRateReader = new BufferedReader(new FileReader("./dataSet/user_ratedmovies.dat"));
        String userRateLine;
        PreparedStatement userRateStatement = connection.prepareStatement(
                "INSERT INTO UserRate_Movie VALUES (?, ?, ?)"
        );

        userRateReader.readLine();

        while ((userRateLine = userRateReader.readLine()) != null) {
            String[] userRateText = userRateLine.split("\t");

            userRateStatement.setInt(1, Integer.parseInt(userRateText[0]));
            userRateStatement.setInt(2, Integer.parseInt(userRateText[1]));
            userRateStatement.setDouble(3, Double.parseDouble(userRateText[2]));

            userRateStatement.executeUpdate();
        }

        System.out.println("Populate User_RatedMovies Successful!");
    }

    private static void populateUserTag() throws Exception {
        // Populate user_taggedmovies.dat
        System.out.println("Populating User_TaggedMovies ...");
        BufferedReader userTagReader = new BufferedReader(new FileReader("./dataSet/user_taggedmovies.dat"));
        String userTagLine;
        PreparedStatement userTagStatement = connection.prepareStatement(
                "INSERT INTO UserTag_Movie VALUES (?, ?, ?)"
        );

        userTagReader.readLine();

        while ((userTagLine = userTagReader.readLine()) != null) {
            String[] userTagText = userTagLine.split("\t");

            userTagStatement.setInt(3, Integer.parseInt(userTagText[2]));
            userTagStatement.setInt(2, Integer.parseInt(userTagText[1]));
            userTagStatement.setInt(1, Integer.parseInt(userTagText[0]));

            userTagStatement.executeUpdate();
        }

        System.out.println("Populate User_TaggedMovies Successful!");
    }

    private static void createIndex() {
        try {
            Statement statement = connection.createStatement();

            String genreIndex = "CREATE INDEX Genre_Index ON Genres (Series_Num, Genre)";
            String yearIndex = "CREATE INDEX Year_Index ON Movie (Release_Year)";
            String countryIndex = "CREATE INDEX Country_Index ON Movie (Series_Num, Country)";
            String actorIndex = "CREATE INDEX Actor_Index ON Cast_Movie (Series_Num, Actor_Name)";
            String directorIndex = "CREATE INDEX Director_Index ON Direct_Movie (Series_Num, Director_Name)";
            String tagIndex = "CREATE INDEX Tag_Index ON MovieHas_Tag (Series_Num, Tag_ID, Weight)";
            String movieTagIndex = "CREATE INDEX MovieTag_Index ON UserTag_Movie (User_ID, Series_Num, Tag_ID)";

            // Create Index
            System.out.println("Creating Index ...");

            statement.executeUpdate(genreIndex);
            statement.executeUpdate(yearIndex);
            statement.executeUpdate(countryIndex);
            statement.executeUpdate(actorIndex);
            statement.executeUpdate(directorIndex);
            statement.executeUpdate(tagIndex);
            statement.executeUpdate(movieTagIndex);

            System.out.println("Create Index Successful!");
        } catch (SQLException se) {
            System.out.println("Error Creating Index: " + se);
        }
    }

    public static void main(String[] args) throws Exception {
        // Connect to the Database
        dbConnection();

        // Drop Index
        dropIndex();

        // Clear Data
        clearData();

        // Populate data
        populateMovie();
        populateGenre();
        populateLocation();
        populateActor();
        populateDirector();
        populateTag();
        populateMovieTag();
        populateUserRate();
        populateUserTag();

        // Create Index
        createIndex();

        // Close the connection
        connection.close();
        System.out.println("Database Connection Closed!");
    }
}
