import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class hw3 {
    private static JFrame frame = new JFrame("IMDB Search Engine");
    private static JPanel mainPanel = new JPanel();
    private static JPanel leftTopPanel = new JPanel();
    private static JPanel centerTopPanel = new JPanel();
    private static JPanel rightTopPanel = new JPanel();
    private static JPanel leftDownPanel = new JPanel();
    private static JPanel centerDownPanel = new JPanel();
    private static JPanel rightDownPanel = new JPanel();
    private static JPanel genrePanel = new JPanel();
    private static JPanel yearRelationPanel = new JPanel();
    private static JPanel yearPanel = new JPanel();
    private static JPanel yearSelectPanel = new JPanel();
    private static JPanel startYearPanel = new JPanel();
    private static JPanel endYearPanel = new JPanel();
    private static JPanel relationPanel = new JPanel();
    private static JPanel relationSelectPanel = new JPanel();
    private static JPanel relationTopPanel = new JPanel();
    private static JPanel relationDownPanel = new JPanel();
    private static JPanel countryPanel = new JPanel();
    private static JPanel castPanel = new JPanel();
    private static JPanel actorPanel = new JPanel();
    private static JPanel actorInputPanel = new JPanel();
    private static JPanel actressPanel = new JPanel();
    private static JPanel actressInput1Panel = new JPanel();
    private static JPanel actressInput2Panel = new JPanel();
    private static JPanel directorPanel = new JPanel();
    private static JPanel directorInputPanel = new JPanel();
    private static JPanel tagPanel = new JPanel();
    private static JPanel tagValuePanel = new JPanel();
    private static JPanel tagWeightPanel = new JPanel();
    private static JPanel tagWeightSelectPanel = new JPanel();
    private static JPanel tagWeightTopPanel = new JPanel();
    private static JPanel tagWeightDownPanel = new JPanel();
    private static JPanel searchMoviePanel = new JPanel();
    private static JPanel searchMovieButtonPanel = new JPanel();
    private static JPanel movieQueryResultPanel = new JPanel();
    private static JPanel userQueryResultPanel = new JPanel();
    private static JPanel searchPanel = new JPanel();
    private static JPanel searchButtonPanel = new JPanel();
    private static JPanel userResultPanel = new JPanel();
    private static JLabel genre = new JLabel("Genre", JLabel.CENTER);
    private static JLabel year = new JLabel("Year", JLabel.CENTER);
    private static JLabel from = new JLabel("From: ");
    private static JLabel to = new JLabel("To: ");
    private static JLabel relation = new JLabel("Relation between Values", JLabel.CENTER);
    private static JLabel selectRelation = new JLabel("<html>Select the Relation <br>Between <br>Values of the Attribute: </html>");
    private static JLabel country = new JLabel("Country", JLabel.CENTER);
    private static JLabel actor = new JLabel("Actor / Actress", JLabel.CENTER);
    private static JLabel director = new JLabel("Director", JLabel.CENTER);
    private static JLabel tag = new JLabel("Tag", JLabel.CENTER);
    private static JLabel tagWeight = new JLabel("Tag Weight", JLabel.CENTER);
    private static JLabel selectTagWeight = new JLabel("<html>Select the Range <br>Of <br>Tag Weight: ");
    private static JLabel searchResult = new JLabel("Select to Show", JLabel.CENTER);
    private static JLabel movieResult = new JLabel("Movie Result", JLabel.CENTER);
    private static JLabel movieQuery = new JLabel("Movie Search Query", JLabel.CENTER);
    private static JLabel userQuery = new JLabel("User Search Query", JLabel.CENTER);
    private static JLabel search = new JLabel("Execute Query to Search", JLabel.CENTER);
    private static JLabel userResult = new JLabel("User Result", JLabel.CENTER);
    private static JScrollPane genreScroll = new JScrollPane();
    private static JScrollPane countryScroll = new JScrollPane();
    private static JScrollPane tagScroll = new JScrollPane();
    private static JScrollPane movieScroll = new JScrollPane();
    private static JScrollPane movieQueryScroll = new JScrollPane();
    private static JScrollPane userQueryScroll = new JScrollPane();
    private static JScrollPane userScroll = new JScrollPane();
    private static JList<Object> genreList = new JList<>();
    private static JList<Object> countryList = new JList<>();
    private static JList<Object> tagList = new JList<>();
    private static JList<Object> movieList = new JList<>();
    private static JList<Object> userList = new JList<>();
    private static JComboBox<Number> startYear = new JComboBox<>();
    private static JComboBox<Number> endYear = new JComboBox<>();
    private static JComboBox<String> andOR = new JComboBox<>();
    private static JComboBox<String> actor1Input = new JComboBox<>();
    private static JComboBox<String> actor2Input = new JComboBox<>();
    private static JComboBox<String> actor3Input = new JComboBox<>();
    private static JComboBox<String> directorInput = new JComboBox<>();
    private static JComboBox<String> range = new JComboBox<>();
    private static DefaultListModel<Object> genreListModel = new DefaultListModel<>();
    private static DefaultListModel<Object> countryListModel = new DefaultListModel<>();
    private static DefaultListModel<Object> tagListModel = new DefaultListModel<>();
    private static DefaultListModel<Object> movieListModel = new DefaultListModel<>();
    private static DefaultListModel<Object> userListModel = new DefaultListModel<>();
    private static JTextField weightInput = new JTextField();
    private static JTextArea movieQueryContent = new JTextArea();
    private static JTextArea userQueryContent = new JTextArea();
    private static JButton countryShow = new JButton("Show Country");
    private static JButton castShow = new JButton("Show Cast");
    private static JButton tagShow = new JButton("Show Tag");
    private static JButton movieQueryShow = new JButton("Show Movie Search Query");
    private static JButton userQueryShow = new JButton("Show User Search Query");
    private static JButton movieSearch = new JButton("Search Movie");
    private static JButton userSearch = new JButton("Search User");
    private static ImageIcon search1 = new ImageIcon("./img/Lolipop.png");
    private static ImageIcon search2 = new ImageIcon("./img/Chicken.png");
    private static ImageIcon search3 = new ImageIcon("./img/Lolipop.png");
    private static ImageIcon search4 = new ImageIcon("./img/Chicken.png");

    private static Connection connection;
    private static String[] selectedGenre;
    private static int selectedStartYear;
    private static int selectedEndYear;
    private static String selectedRelation;
    private static String[] selectedCountry;
    private static String selectedActor1;
    private static String selectedActor2;
    private static String selectedActor3;
    private static ArrayList<String> selectedActor = new ArrayList<>();
    private static String selectedDirector;
    private static int[] selectedTag;
    private static String selectedRange;
    private static int selectedWeight;
    private static int[] selectedMovie;

    private static String genreSelect = "EXISTS ( SELECT g1.Series_Num FROM Genres g1 WHERE g1.Series_Num = m.Series_Num AND g1.Genre = ? )";
    private static String yearSelect = "m.Release_Year >= ? AND m.Release_Year <= ? AND ( ";
    private static String countrySelect = "EXISTS ( SELECT m1.Series_Num FROM Movie m1 WHERE m1.Series_Num = m.Series_Num AND m1.Country = ? )";
    private static String actorSelect = "EXISTS ( SELECT c1.Series_Num FROM Cast_Movie c1 WHERE c1.Series_Num = m.Series_Num AND c1.Actor_Name = ? )";
    private static String directorSelect = "EXISTS ( SELECT d1.Series_Num FROM Direct_Movie d1 WHERE d1.Series_Num = m.Series_Num AND d1.Director_Name = ? )";
    private static String tagSelect = "EXISTS ( SELECT mt1.Series_Num FROM MovieHas_Tag mt1 WHERE mt1.Series_Num = m.Series_Num AND mt1.Tag_ID = ? ";
    private static String userSelect = "EXISTS ( SELECT utm1.Series_Num FROM UserTag_Movie utm1 WHERE utm1.Series_Num = utm.Series_Num AND utm1.Tag_ID = utm.Tag_ID AND utm1.Tag_ID = ? AND utm1.Series_Num = ? )";

    private hw3() {
        mainPanel.setLayout(new GridLayout(2, 3));
        mainPanel.add(leftTopPanel);
        mainPanel.add(centerTopPanel);
        mainPanel.add(rightTopPanel);
        mainPanel.add(leftDownPanel);
        mainPanel.add(centerDownPanel);
        mainPanel.add(rightDownPanel);

        leftTopPanel.setLayout(new GridLayout(1, 3));
        leftTopPanel.add(genrePanel);
        leftTopPanel.add(yearRelationPanel);
        leftTopPanel.add(countryPanel);

        genrePanel.setLayout(new BorderLayout());
        genrePanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        genrePanel.add(genre, BorderLayout.NORTH);
        genrePanel.add(genreScroll);

        genre.setPreferredSize(new Dimension(20, 40));

        genreScroll.setViewportView(genreList);

        genreList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        genreList.setModel(genreListModel);

        yearRelationPanel.setLayout(new GridLayout(2, 1));
        yearRelationPanel.add(yearPanel);
        yearRelationPanel.add(relationPanel);

        yearPanel.setLayout(new BorderLayout());
        yearPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        yearPanel.add(year, BorderLayout.NORTH);
        yearPanel.add(yearSelectPanel);

        year.setPreferredSize(new Dimension(20, 40));

        yearSelectPanel.setLayout(new GridLayout(2, 1));
        yearSelectPanel.add(startYearPanel);
        yearSelectPanel.add(endYearPanel);

        startYearPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 30));
        startYearPanel.setBackground(Color.WHITE);
        startYearPanel.add(from);
        startYearPanel.add(startYear);

        startYear.setBackground(Color.WHITE);
        startYear.setPreferredSize(new Dimension(85, 30));

        endYearPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        endYearPanel.setBackground(Color.WHITE);
        endYearPanel.add(to);
        endYearPanel.add(endYear);

        endYear.setBackground(Color.WHITE);
        endYear.setPreferredSize(new Dimension(85, 30));

        relationPanel.setLayout(new BorderLayout());
        relationPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        relationPanel.add(relation, BorderLayout.NORTH);
        relationPanel.add(relationSelectPanel);

        relation.setPreferredSize(new Dimension(20, 40));

        relationSelectPanel.setLayout(new GridLayout(2, 1));
        relationSelectPanel.add(relationTopPanel);
        relationSelectPanel.add(relationDownPanel);

        relationTopPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        relationTopPanel.setBackground(Color.WHITE);
        relationTopPanel.add(selectRelation);

        relationDownPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        relationDownPanel.setBackground(Color.WHITE);
        relationDownPanel.add(andOR);

        andOR.setBackground(Color.WHITE);
        andOR.addItem("AND");
        andOR.addItem("OR");
        andOR.setPreferredSize(new Dimension(130, 30));

        countryPanel.setLayout(new BorderLayout());
        countryPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        countryPanel.add(country, BorderLayout.NORTH);
        countryPanel.add(countryScroll);

        country.setPreferredSize(new Dimension(20, 40));

        countryScroll.setViewportView(countryList);

        countryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        countryList.setModel(countryListModel);

        centerTopPanel.setLayout(new GridLayout(1, 3));
        centerTopPanel.add(castPanel);
        centerTopPanel.add(tagPanel);
        centerTopPanel.add(searchMoviePanel);

        castPanel.setLayout(new GridLayout(3, 1));
        castPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        castPanel.add(actorPanel);
        castPanel.add(actressPanel);
        castPanel.add(directorPanel);

        actorPanel.setLayout(new BorderLayout());
        actorPanel.add(actor, BorderLayout.NORTH);
        actorPanel.add(actorInputPanel);

        actor.setPreferredSize(new Dimension(20, 40));

        actorInputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        actorInputPanel.setBackground(Color.WHITE);
        actorInputPanel.add(actor1Input);

        actor1Input.setBackground(Color.WHITE);
        actor1Input.setPreferredSize(new Dimension(120, 30));
        // actor1Input.setEditable(true);

        actressPanel.setLayout(new BorderLayout());
        actressPanel.setBackground(Color.WHITE);
        actressPanel.add(actressInput1Panel, BorderLayout.NORTH);
        actressPanel.add(actressInput2Panel);

        actressInput1Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actressInput1Panel.setBackground(Color.WHITE);
        actressInput1Panel.add(actor2Input);

        actor2Input.setBackground(Color.WHITE);
        actor2Input.setPreferredSize(new Dimension(120, 30));
        // actor2Input.setEditable(true);

        actressInput2Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 25));
        actressInput2Panel.setBackground(Color.WHITE);
        actressInput2Panel.add(actor3Input);

        actor3Input.setBackground(Color.WHITE);
        actor3Input.setPreferredSize(new Dimension(120, 30));
        // actor3Input.setEditable(true);

        directorPanel.setLayout(new BorderLayout());
        directorPanel.add(director, BorderLayout.NORTH);
        directorPanel.add(directorInputPanel);

        director.setPreferredSize(new Dimension(20, 40));

        directorInputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        directorInputPanel.setBackground(Color.WHITE);
        directorInputPanel.add(directorInput);

        directorInput.setBackground(Color.WHITE);
        directorInput.setPreferredSize(new Dimension(120, 30));
        // directorInput.setEditable(true);

        tagPanel.setLayout(new GridLayout(2, 1));
        tagPanel.add(tagValuePanel);
        tagPanel.add(tagWeightPanel);

        tagValuePanel.setLayout(new BorderLayout());
        tagValuePanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        tagValuePanel.add(tag, BorderLayout.NORTH);
        tagValuePanel.add(tagScroll);

        tag.setPreferredSize(new Dimension(20, 40));

        tagScroll.setViewportView(tagList);

        tagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tagList.setModel(tagListModel);

        tagWeightPanel.setLayout(new BorderLayout());
        tagWeightPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        tagWeightPanel.add(tagWeight, BorderLayout.NORTH);
        tagWeightPanel.add(tagWeightSelectPanel);

        tagWeight.setPreferredSize(new Dimension(20, 40));

        tagWeightSelectPanel.setLayout(new GridLayout(2, 1));
        tagWeightSelectPanel.add(tagWeightTopPanel);
        tagWeightSelectPanel.add(tagWeightDownPanel);

        tagWeightTopPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        tagWeightTopPanel.setBackground(Color.WHITE);
        tagWeightTopPanel.add(selectTagWeight);

        tagWeightDownPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        tagWeightDownPanel.setBackground(Color.WHITE);
        tagWeightDownPanel.add(range);
        tagWeightDownPanel.add(weightInput);

        range.setBackground(Color.WHITE);
        range.addItem(">");
        range.addItem(">=");
        range.addItem("=");
        range.addItem("<=");
        range.addItem("<");
        range.setPreferredSize(new Dimension(40, 30));

        weightInput.setBackground(Color.WHITE);
        weightInput.setPreferredSize(new Dimension(90, 30));
        weightInput.setFont(new java.awt.Font("Dialog", 1, 12));

        searchMoviePanel.setLayout(new BorderLayout());
        searchMoviePanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        searchMoviePanel.add(searchResult, BorderLayout.NORTH);
        searchMoviePanel.add(searchMovieButtonPanel);

        searchResult.setPreferredSize(new Dimension(20, 40));

        searchMovieButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        searchMovieButtonPanel.setBackground(Color.WHITE);
        searchMovieButtonPanel.add(countryShow);
        searchMovieButtonPanel.add(castShow);
        searchMovieButtonPanel.add(tagShow);

        Image temp1 = search1.getImage().getScaledInstance(15, 15, search1.getImage().SCALE_DEFAULT);
        search1 = new ImageIcon(temp1);
        Image temp2 = search2.getImage().getScaledInstance(15, 15, search2.getImage().SCALE_DEFAULT);
        search2 = new ImageIcon(temp2);

        countryShow.setPreferredSize(new Dimension(140, 60));
        countryShow.setIcon(search1);

        castShow.setPreferredSize(new Dimension(140, 60));
        castShow.setIcon(search2);

        tagShow.setPreferredSize(new Dimension(140, 60));
        tagShow.setIcon(search1);

        rightTopPanel.setLayout(new BorderLayout());
        rightTopPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        rightTopPanel.add(movieResult, BorderLayout.NORTH);
        rightTopPanel.add(movieScroll);

        movieResult.setPreferredSize(new Dimension(20, 40));

        movieScroll.setViewportView(movieList);

        movieList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        movieList.setModel(movieListModel);

        Image temp3 = search3.getImage().getScaledInstance(20, 20, search3.getImage().SCALE_DEFAULT);
        search3 = new ImageIcon(temp3);
        Image temp4 = search4.getImage().getScaledInstance(20, 20, search4.getImage().SCALE_DEFAULT);
        search4 = new ImageIcon(temp4);

        leftDownPanel.setLayout(new BorderLayout());
        leftDownPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        leftDownPanel.add(movieQuery, BorderLayout.NORTH);
        leftDownPanel.add(movieQueryResultPanel);

        movieQuery.setPreferredSize(new Dimension(20, 40));

        movieQueryResultPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        movieQueryResultPanel.setBackground(Color.WHITE);
        movieQueryResultPanel.add(movieQueryScroll);
        movieQueryResultPanel.add(movieQueryShow);

        movieQueryScroll.setPreferredSize(new Dimension(400, 200));
        movieQueryScroll.setViewportView(movieQueryContent);

        movieQueryContent.setEditable(false);
        movieQueryContent.setLineWrap(true);
        movieQueryContent.setWrapStyleWord(true);
        movieQueryContent.setFont(new java.awt.Font("Dialog", 1, 12));

        movieQueryShow.setPreferredSize(new Dimension(220, 80));
        movieQueryShow.setIcon(search3);

        centerDownPanel.setLayout(new BorderLayout());
        centerDownPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        centerDownPanel.add(userQuery, BorderLayout.NORTH);
        centerDownPanel.add(userQueryResultPanel);

        userQuery.setPreferredSize(new Dimension(20, 40));

        userQueryResultPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        userQueryResultPanel.setBackground(Color.WHITE);
        userQueryResultPanel.add(userQueryScroll);
        userQueryResultPanel.add(userQueryShow);

        userQueryScroll.setPreferredSize(new Dimension(400, 200));
        userQueryScroll.setViewportView(userQueryContent);

        userQueryContent.setEditable(false);
        userQueryContent.setLineWrap(true);
        userQueryContent.setWrapStyleWord(true);
        userQueryContent.setFont(new java.awt.Font("Dialog", 1, 12));

        userQueryShow.setPreferredSize(new Dimension(220, 80));
        userQueryShow.setIcon(search4);

        rightDownPanel.setLayout(new GridLayout(1, 2));
        rightDownPanel.add(searchPanel);
        rightDownPanel.add(userResultPanel);

        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        searchPanel.add(search, BorderLayout.NORTH);
        searchPanel.add(searchButtonPanel);

        search.setPreferredSize(new Dimension(20, 40));

        searchButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 60));
        searchButtonPanel.setBackground(Color.WHITE);
        searchButtonPanel.add(movieSearch);
        searchButtonPanel.add(userSearch);

        movieSearch.setPreferredSize(new Dimension(200, 80));
        movieSearch.setIcon(search3);

        userSearch.setPreferredSize(new Dimension(200, 80));
        userSearch.setIcon(search4);

        userResultPanel.setLayout(new BorderLayout());
        userResultPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        userResultPanel.add(userResult, BorderLayout.NORTH);
        userResultPanel.add(userScroll);

        userResult.setPreferredSize(new Dimension(20, 40));

        userScroll.setViewportView(userList);

        userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        userList.setModel(userListModel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setSize(1400, 800);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static void dbConnection() {
        // Connect to the Database
        System.out.println("Connecting to Database ...");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error Loading Driver: " + e);
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

    private static void initialization() {
        try {
            Statement statement = connection.createStatement();

            String loadGenre = "SELECT DISTINCT g.Genre FROM Genres g ORDER BY g.Genre";
            ResultSet genre = statement.executeQuery(loadGenre);

            while (genre.next()) {
                genreListModel.addElement(genre.getString(1));
            }

            String loadYear = "SELECT MIN(m.Release_Year), MAX(m.Release_Year) FROM Movie m";
            int start = 0;
            int end = 0;
            ResultSet year = statement.executeQuery(loadYear);

            while (year.next()) {
                start = year.getInt(1);
                end = year.getInt(2);
            }

            for (int i = start; i <= end; i++) {
                startYear.addItem(i);
                endYear.addItem(i);
                endYear.setSelectedItem(end);
            }

            andOR.setSelectedItem("AND");
            range.setSelectedItem(">=");
            weightInput.setText("1");

            selectedStartYear = Integer.parseInt(startYear.getSelectedItem().toString());
            selectedEndYear = Integer.parseInt(endYear.getSelectedItem().toString());
            selectedRelation = andOR.getSelectedItem().toString();
            selectedActor1 = "";
            selectedActor2 = "";
            selectedActor3 = "";
            selectedDirector = "";
            selectedRange = range.getSelectedItem().toString();
            selectedWeight = Integer.parseInt(weightInput.getText());
        } catch (SQLException se) {
            System.out.println("Error Initialize: " + se);
        }
    }

    private static void genreListener() {
        genreList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    List<Object> values = genreList.getSelectedValuesList();
                    selectedGenre = values.toArray(new String[values.size()]);

                    System.out.println("Selected Genres: " + Arrays.toString(selectedGenre));
                }
            }
        });
    }

    private static void yearListener() {
        startYear.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedStartYear = Integer.parseInt(startYear.getSelectedItem().toString());

                    System.out.println("Selected Start Year: " + selectedStartYear);
                }
            }
        });

        endYear.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedEndYear = Integer.parseInt(endYear.getSelectedItem().toString());

                    System.out.println("Selected End Year: " + selectedEndYear);
                }
            }
        });
    }

    private static void relationListener() {
        andOR.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedRelation = andOR.getSelectedItem().toString();

                    System.out.println("Selected Relation: " + selectedRelation);
                }
            }
        });
    }

    private static void countryListener() {
        countryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    List<Object> values = countryList.getSelectedValuesList();
                    selectedCountry = values.toArray(new String[values.size()]);

                    System.out.println("Selected Countries: " + Arrays.toString(selectedCountry));
                }
            }
        });
    }

    private static void actorListener() {
        /*
        Component actor1Component = actor1Input.getEditor().getEditorComponent();
        Component actor2Component = actor2Input.getEditor().getEditorComponent();
        Component actor3Component = actor3Input.getEditor().getEditorComponent();

        actor1Component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                selectedActor1 = actor1Input.getEditor().getItem().toString();

                System.out.println(selectedActor1);
            }
        });

        actor2Component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                selectedActor2 = actor2Input.getEditor().getItem().toString();

                System.out.println(selectedActor2);
            }
        });

        actor3Component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                selectedActor3 = actor3Input.getEditor().getItem().toString();

                System.out.println(selectedActor3);
            }
        });
        */

        actor1Input.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedActor.clear();

                    selectedActor1 = actor1Input.getSelectedItem().toString();

                    if (!selectedActor1.equals("")) {
                        selectedActor.add(selectedActor1);
                    }
                    if (!selectedActor2.equals("")) {
                        selectedActor.add(selectedActor2);
                    }
                    if (!selectedActor3.equals("")) {
                        selectedActor.add(selectedActor3);
                    }

                    System.out.println("Selected Actors: " + Arrays.toString(selectedActor.toArray()));
                }
            }
        });

        actor2Input.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedActor.clear();

                    selectedActor2 = actor2Input.getSelectedItem().toString();

                    if (!selectedActor1.equals("")) {
                        selectedActor.add(selectedActor1);
                    }
                    if (!selectedActor2.equals("")) {
                        selectedActor.add(selectedActor2);
                    }
                    if (!selectedActor3.equals("")) {
                        selectedActor.add(selectedActor3);
                    }

                    System.out.println("Selected Actors: " + Arrays.toString(selectedActor.toArray()));
                }
            }
        });

        actor3Input.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedActor.clear();

                    selectedActor3 = actor3Input.getSelectedItem().toString();

                    if (!selectedActor1.equals("")) {
                        selectedActor.add(selectedActor1);
                    }
                    if (!selectedActor2.equals("")) {
                        selectedActor.add(selectedActor2);
                    }
                    if (!selectedActor3.equals("")) {
                        selectedActor.add(selectedActor3);
                    }

                    System.out.println("Selected Actors: " + Arrays.toString(selectedActor.toArray()));
                }
            }
        });
    }

    private static void directorListener() {
        /*
        Component directorComponent = directorInput.getEditor().getEditorComponent();

        directorComponent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                selectedDirector = directorInput.getEditor().getItem().toString();

                System.out.println(selectedDirector);
            }
        });
        */

        directorInput.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedDirector = directorInput.getSelectedItem().toString();

                    System.out.println("Selected Director: " + selectedDirector);
                }
            }
        });
    }

    private static void tagListener() {
        tagList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    List<Object> values = tagList.getSelectedValuesList();
                    String[] temp = values.toArray(new String[values.size()]);
                    selectedTag = new int[values.size()];

                    for (int i = 0; i < values.size(); i++) {
                        String[] temp1 = temp[i].split("        ");

                        selectedTag[i] = Integer.parseInt(temp1[0]);
                    }

                    System.out.println("Selected Tags: " + Arrays.toString(selectedTag));
                }
            }
        });

        range.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedRange = range.getSelectedItem().toString();

                    System.out.println("Selected Range: " + selectedRange);
                }
            }
        });

        Document document = weightInput.getDocument();

        document.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Document document = e.getDocument();
                int length = document.getLength();
                try {
                    String str = document.getText(0, length);

                    if (!str.equals("")) {
                        selectedWeight = Integer.parseInt(str);
                    }

                    System.out.println("Selected Weight: " + selectedWeight);
                } catch (BadLocationException be) {
                    System.out.println("BadLocationException happens: " + be);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                Document document = e.getDocument();
                int length = document.getLength();
                try {
                    String str = document.getText(0, length);

                    if (!str.equals("")) {
                        selectedWeight = Integer.parseInt(str);
                    }

                    System.out.println("Selected Weight: " + selectedWeight);
                } catch (BadLocationException be) {
                    System.out.println("BadLocationException happens: " + be);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                Document document = e.getDocument();
                int length = document.getLength();
                try {
                    String str = document.getText(0, length);

                    if (!str.equals("")) {
                        selectedWeight = Integer.parseInt(str);
                    }

                    System.out.println("Selected Weight: " + selectedWeight);
                } catch (BadLocationException be) {
                    System.out.println("BadLocationException happens: " + be);
                }
            }
        });

        weightInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                weightInput.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (weightInput.getText().equals("")) {
                    weightInput.setText("1");
                    selectedWeight = 1;
                }
            }
        });
    }

    private static void movieListener() {
        movieList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    List<Object> values = movieList.getSelectedValuesList();
                    String[] temp = values.toArray(new String[values.size()]);
                    selectedMovie = new int[values.size()];

                    for (int i = 0; i < values.size(); i++) {
                        String[] temp1 = temp[i].split("        ");

                        selectedMovie[i] = Integer.parseInt(temp1[0]);
                    }

                    System.out.println("Selected Movies: " + Arrays.toString(selectedMovie));
                }
            }
        });
    }

    private static void showCountry() {
        countryShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String relationSelect = " " + selectedRelation + " ";
                StringBuilder sb = new StringBuilder();

                if (selectedGenre == null || selectedGenre.length == 0) {
                    JOptionPane.showMessageDialog(null, "Genre must be Selected, Please Select and Continue!", "Genre Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    sb.append("SELECT DISTINCT m.Country FROM Movie m WHERE ");
                    sb.append(yearSelect);

                    for (int i = 0; i < selectedGenre.length; i++) {
                        if (i == 0) {
                            sb.append(genreSelect);
                        } else {
                            sb.append(relationSelect);
                            sb.append(genreSelect);
                        }
                    }

                    sb.append(" ) ORDER BY m.Country");

                    System.out.println(sb.toString());

                    // Reset UI
                    countryListModel.removeAllElements();
                    actor1Input.removeAllItems();
                    actor2Input.removeAllItems();
                    actor3Input.removeAllItems();
                    directorInput.removeAllItems();
                    tagListModel.removeAllElements();
                    range.setSelectedItem(">=");
                    weightInput.setText("1");
                    movieListModel.removeAllElements();
                    userListModel.removeAllElements();
                    movieQueryContent.setText("");
                    userQueryContent.setText("");
                    actor1Input.addItem("");
                    actor2Input.addItem("");
                    actor3Input.addItem("");
                    directorInput.addItem("");

                    // Reset Data
                    selectedCountry = null;
                    selectedActor1 = "";
                    selectedActor2 = "";
                    selectedActor3 = "";
                    selectedActor.clear();
                    selectedDirector = "";
                    selectedTag = null;
                    selectedWeight = 1;
                    selectedRange = ">=";
                    selectedMovie = null;

                    try {
                        PreparedStatement statement = connection.prepareStatement(sb.toString());

                        statement.setInt(1, selectedStartYear);
                        statement.setInt(2, selectedEndYear);

                        for (int i = 0; i < selectedGenre.length; i++) {
                            statement.setString(i + 3, selectedGenre[i]);
                        }

                        ResultSet country = statement.executeQuery();

                        while (country.next()) {
                            countryListModel.addElement(country.getString(1));
                        }
                    } catch (SQLException se) {
                        System.out.println("SQLException occurs: " + se);
                    }

                    // No Movie can have more than one Country!
                    if (selectedRelation.equals("AND")) {
                        countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    } else if (selectedRelation.equals("OR")) {
                        countryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                    }
                }
            }
        });
    }

    private static void showCast() {
        castShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String relationSelect = " " + selectedRelation + " ";
                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();

                if (selectedGenre == null || selectedGenre.length == 0) {
                    JOptionPane.showMessageDialog(null, "Genre must be Selected, Please Select and Continue!", "Genre Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    // Show Actor
                    sb1.append("SELECT DISTINCT c.Actor_Name FROM Movie m, Cast_Movie c WHERE m.Series_Num = c.Series_Num AND ");
                    sb1.append(yearSelect);

                    for (int i = 0; i < selectedGenre.length; i++) {
                        if (i == 0) {
                            sb1.append(genreSelect);
                        } else {
                            sb1.append(relationSelect);
                            sb1.append(genreSelect);
                        }
                    }

                    sb1.append(" )");

                    if (selectedCountry != null && selectedCountry.length != 0) {
                        sb1.append(" AND ( ");

                        for (int i = 0; i < selectedCountry.length; i++) {
                            if (i == 0) {
                                sb1.append(countrySelect);
                            } else {
                                sb1.append(relationSelect);
                                sb1.append(countrySelect);
                            }
                        }

                        sb1.append(" )");
                    }

                    sb1.append(" ORDER BY c.Actor_Name");

                    System.out.println(sb1.toString());

                    // Reset UI
                    actor1Input.removeAllItems();
                    actor2Input.removeAllItems();
                    actor3Input.removeAllItems();
                    actor1Input.addItem("");
                    actor2Input.addItem("");
                    actor3Input.addItem("");
                    directorInput.removeAllItems();
                    directorInput.addItem("");
                    tagListModel.removeAllElements();
                    range.setSelectedItem(">=");
                    weightInput.setText("1");
                    movieListModel.removeAllElements();
                    userListModel.removeAllElements();
                    movieQueryContent.setText("");
                    userQueryContent.setText("");

                    // Reset Data
                    selectedActor1 = "";
                    selectedActor2 = "";
                    selectedActor3 = "";
                    selectedActor.clear();
                    selectedDirector = "";
                    selectedTag = null;
                    selectedRange = ">=";
                    selectedWeight = 1;
                    selectedMovie = null;

                    try {
                        PreparedStatement statement = connection.prepareStatement(sb1.toString());

                        statement.setInt(1, selectedStartYear);
                        statement.setInt(2, selectedEndYear);

                        for (int i = 0; i < selectedGenre.length; i++) {
                            statement.setString(i + 3, selectedGenre[i]);
                        }

                        if (selectedCountry != null && selectedCountry.length != 0) {
                            for (int i = 0; i < selectedCountry.length; i++) {
                                statement.setString(i + 3 + selectedGenre.length, selectedCountry[i]);
                            }
                        }

                        ResultSet actor = statement.executeQuery();

                        while (actor.next()) {
                            actor1Input.addItem(actor.getString(1));
                            actor2Input.addItem(actor.getString(1));
                            actor3Input.addItem(actor.getString(1));
                        }
                    } catch (SQLException se) {
                        System.out.println("SQLException occurs: " + se);
                    }

                    // Show Director
                    sb2.append("SELECT DISTINCT d.Director_Name FROM Movie m, Direct_Movie d WHERE m.Series_Num = d.Series_Num AND ");
                    sb2.append(yearSelect);

                    for (int i = 0; i < selectedGenre.length; i++) {
                        if (i == 0) {
                            sb2.append(genreSelect);
                        } else {
                            sb2.append(relationSelect);
                            sb2.append(genreSelect);
                        }
                    }

                    sb2.append(" )");

                    if (selectedCountry != null && selectedCountry.length != 0) {
                        sb2.append(" AND ( ");

                        for (int i = 0; i < selectedCountry.length; i++) {
                            if (i == 0) {
                                sb2.append(countrySelect);
                            } else {
                                sb2.append(relationSelect);
                                sb2.append(countrySelect);
                            }
                        }

                        sb2.append(" )");
                    }

                    sb2.append(" ORDER BY d.Director_Name");

                    System.out.println(sb2.toString());

                    try {
                        PreparedStatement statement = connection.prepareStatement(sb2.toString());

                        statement.setInt(1, selectedStartYear);
                        statement.setInt(2, selectedEndYear);

                        for (int i = 0; i < selectedGenre.length; i++) {
                            statement.setString(i + 3, selectedGenre[i]);
                        }

                        if (selectedCountry != null && selectedCountry.length != 0) {
                            for (int i = 0; i < selectedCountry.length; i++) {
                                statement.setString(i + 3 + selectedGenre.length, selectedCountry[i]);
                            }
                        }

                        ResultSet director = statement.executeQuery();

                        while (director.next()) {
                            directorInput.addItem(director.getString(1));
                        }
                    } catch (SQLException se) {
                        System.out.println("SQLException occurs: " + se);
                    }

                    actor1Input.setSelectedItem("");
                    actor2Input.setSelectedItem("");
                    actor3Input.setSelectedItem("");
                    directorInput.setSelectedItem("");
                }
            }
        });
    }

    private static void showTag() {
        tagShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String relationSelect = " " + selectedRelation + " ";
                String tagWeightSelect = "mt.Weight " + selectedRange + " " + selectedWeight + " AND ";
                StringBuilder sb = new StringBuilder();

                if (selectedGenre == null || selectedGenre.length == 0) {
                    JOptionPane.showMessageDialog(null, "Genre must be Selected, Please Select and Continue!", "Genre Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    sb.append("SELECT DISTINCT t.Tag_ID, t.Text FROM Movie m, Tag t, MovieHas_Tag mt WHERE t.Tag_ID = mt.Tag_ID AND m.Series_Num = mt.Series_Num AND ");
                    sb.append(tagWeightSelect);
                    sb.append(yearSelect);

                    for (int i = 0; i < selectedGenre.length; i++) {
                        if (i == 0) {
                            sb.append(genreSelect);
                        } else {
                            sb.append(relationSelect);
                            sb.append(genreSelect);
                        }
                    }

                    sb.append(" )");

                    if (selectedCountry != null && selectedCountry.length != 0) {
                        sb.append(" AND ( ");

                        for (int i = 0; i < selectedCountry.length; i++) {
                            if (i == 0) {
                                sb.append(countrySelect);
                            } else {
                                sb.append(relationSelect);
                                sb.append(countrySelect);
                            }
                        }

                        sb.append(" )");
                    }

                    if ((selectedActor != null && selectedActor.size() != 0) || !selectedDirector.equals("")) {
                        sb.append(" AND ( ");

                        if (selectedActor != null && selectedActor.size() != 0) {
                            for (int i = 0; i < selectedActor.size(); i++) {
                                if (i == 0) {
                                    sb.append(actorSelect);
                                } else {
                                    sb.append(relationSelect);
                                    sb.append(actorSelect);
                                }
                            }

                            if (!selectedDirector.equals("")) {
                                sb.append(relationSelect);
                                sb.append(directorSelect);
                            }
                            sb.append(" )");
                        }
                        else {
                            sb.append(directorSelect);
                            sb.append(" )");
                        }
                    }

                    sb.append(" ORDER BY t.Tag_ID");

                    System.out.println(sb.toString());

                    // Reset UI
                    tagListModel.removeAllElements();
                    movieListModel.removeAllElements();
                    userListModel.removeAllElements();
                    movieQueryContent.setText("");
                    userQueryContent.setText("");

                    // Reset Data
                    selectedTag = null;
                    selectedMovie = null;

                    try {
                        PreparedStatement statement = connection.prepareStatement(sb.toString());

                        statement.setInt(1, selectedStartYear);
                        statement.setInt(2, selectedEndYear);

                        for (int i = 0; i < selectedGenre.length; i++) {
                            statement.setString(i + 3, selectedGenre[i]);
                        }

                        if (selectedCountry != null && selectedCountry.length != 0) {
                            for (int i = 0; i < selectedCountry.length; i++) {
                                statement.setString(i + 3 + selectedGenre.length, selectedCountry[i]);
                            }
                        }

                        if (selectedActor != null && selectedActor.size() != 0) {
                            for (int i = 0; i < selectedActor.size(); i++) {
                                statement.setString(i + 3 + selectedGenre.length + (selectedCountry != null ? selectedCountry.length : 0), selectedActor.get(i));
                            }
                        }

                        if (!selectedDirector.equals("")) {
                            statement.setString(3 + selectedGenre.length + (selectedCountry != null ? selectedCountry.length : 0) + (selectedActor != null ? selectedActor.size() : 0), selectedDirector);
                        }

                        ResultSet tag = statement.executeQuery();

                        while (tag.next()) {
                            tagListModel.addElement(tag.getString(1) + "        " + tag.getString(2));
                        }
                    } catch (SQLException se) {
                        System.out.println("SQLException occurs: " + se);
                    }
                }
            }
        });
    }

    private static void showMovieQuery() {
        movieQueryShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String relationSelect = " " + selectedRelation + " ";
                String tagWeightSelect = "AND mt1.Weight " + selectedRange + " " + selectedWeight + "\n)";
                StringBuilder sb = new StringBuilder();

                if (selectedGenre == null || selectedGenre.length == 0) {
                    JOptionPane.showMessageDialog(null, "Genre must be Selected, Please Select and Continue!", "Selected Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    sb.append("SELECT DISTINCT m.Series_Num, m.Title, g.Genre, m.Release_Year, m.Country, m.Rating, m.NumOfRating\nFROM Movie m, Genres g\nWHERE m.Series_Num = g.Series_Num AND\n");
                    sb.append("m.Release_Year >= ");
                    sb.append(selectedStartYear);
                    sb.append(" AND m.Release_Year <= ");
                    sb.append(selectedEndYear);
                    sb.append(" AND ( ");

                    for (int i = 0; i < selectedGenre.length; i++) {
                        if (i == 0) {
                            sb.append("EXISTS (\nSELECT g1.Series_Num\nFROM Genres g1\nWHERE g1.Series_Num = m.Series_Num AND g1.Genre = \'");
                            sb.append(selectedGenre[i]);
                            sb.append("\'\n)");
                        } else {
                            sb.append(relationSelect);
                            sb.append("EXISTS (\nSELECT g1.Series_Num\nFROM Genres g1\nWHERE g1.Series_Num = m.Series_Num AND g1.Genre = \'");
                            sb.append(selectedGenre[i]);
                            sb.append("\'\n)");
                        }
                    }

                    sb.append(" )");

                    if (selectedCountry != null && selectedCountry.length != 0) {
                        sb.append(" AND ( ");

                        for (int i = 0; i < selectedCountry.length; i++) {
                            if (i == 0) {
                                sb.append("EXISTS (\nSELECT m1.Series_Num\nFROM Movie m1\nWHERE m1.Series_Num = m.Series_Num AND m1.Country = \'");
                                sb.append(selectedCountry[i]);
                                sb.append("\'\n)");
                            } else {
                                sb.append(relationSelect);
                                sb.append("EXISTS (\nSELECT m1.Series_Num\nFROM Movie m1\nWHERE m1.Series_Num = m.Series_Num AND m1.Country = \'");
                                sb.append(selectedCountry[i]);
                                sb.append("\'\n)");
                            }
                        }

                        sb.append(" )");
                    }

                    if ((selectedActor != null && selectedActor.size() != 0) || !selectedDirector.equals("")) {
                        sb.append(" AND ( ");

                        if (selectedActor != null && selectedActor.size() != 0) {
                            for (int i = 0; i < selectedActor.size(); i++) {
                                if (i == 0) {
                                    sb.append("EXISTS (\nSELECT c1.Series_Num\nFROM Cast_Movie c1\nWHERE c1.Series_Num = m.Series_Num AND c1.Actor_Name = \'");
                                    sb.append(selectedActor.get(i));
                                    sb.append("\'\n)");
                                } else {
                                    sb.append(relationSelect);
                                    sb.append("EXISTS (\nSELECT c1.Series_Num\nFROM Cast_Movie c1\nWHERE c1.Series_Num = m.Series_Num AND c1.Actor_Name = \'");
                                    sb.append(selectedActor.get(i));
                                    sb.append("\'\n)");
                                }
                            }

                            if (!selectedDirector.equals("")) {
                                sb.append(relationSelect);
                                sb.append("EXISTS (\nSELECT d1.Series_Num\nFROM Direct_Movie d1\nWHERE d1.Series_Num = m.Series_Num AND d1.Director_Name = \'");
                                sb.append(selectedDirector);
                                sb.append("\'\n)");
                            }

                            sb.append(" )");
                        }
                        else {
                            sb.append("EXISTS (\nSELECT d1.Series_Num\nFROM Direct_Movie d1\nWHERE d1.Series_Num = m.Series_Num AND d1.Director_Name = \'");
                            sb.append(selectedDirector);
                            sb.append("\'\n)");
                            sb.append(" )");
                        }
                    }

                    if (selectedTag != null && selectedTag.length != 0) {
                        sb.append(" AND ( ");

                        for (int i = 0; i < selectedTag.length; i++) {
                            if (i == 0) {
                                sb.append("EXISTS (\nSELECT mt1.Series_Num\nFROM MovieHas_Tag mt1\nWHERE mt1.Series_Num = m.Series_Num AND mt1.Tag_ID = ");
                                sb.append(selectedTag[i]);
                                sb.append(" ");
                                sb.append(tagWeightSelect);
                            } else {
                                sb.append(relationSelect);
                                sb.append("EXISTS (\nSELECT mt1.Series_Num\nFROM MovieHas_Tag mt1\nWHERE mt1.Series_Num = m.Series_Num AND mt1.Tag_ID = ");
                                sb.append(selectedTag[i]);
                                sb.append(" ");
                                sb.append(tagWeightSelect);
                            }
                        }

                        sb.append(" )");
                    }

                    sb.append("\nORDER BY m.Series_Num");

                    // System.out.println(sb.toString());

                    // Reset UI
                    movieQueryContent.setText("");

                    movieQueryContent.append(sb.toString());
                }
            }
        });
    }

    private static void showUserQuery() {
        userQueryShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String relationSelect = " " + selectedRelation + " ";
                StringBuilder sb = new StringBuilder();

                if (selectedTag == null || selectedTag.length == 0) {
                    JOptionPane.showMessageDialog(null, "Tag must be Selected, Please Select and Continue!", "Selected Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (selectedMovie == null || selectedMovie.length == 0) {
                    JOptionPane.showMessageDialog(null, "Movie must be Selected, Please Select and Continue!", "Selected Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    sb.append("SELECT DISTINCT utm.User_ID\nFROM UserTag_Movie utm\nWHERE ( ");

                    for (int i = 0; i < selectedTag.length; i++) {
                        for (int j = 0; j < selectedMovie.length; j++) {
                            if (i == 0 && j == 0) {
                                sb.append("EXISTS (\nSELECT utm1.Series_Num\nFROM UserTag_Movie utm1\nWHERE utm1.Series_Num = utm.Series_Num AND utm1.Tag_ID = utm.Tag_ID AND utm1.Tag_ID = ");
                                sb.append(selectedTag[i]);
                                sb.append(" AND utm1.Series_Num = ");
                                sb.append(selectedMovie[j]);
                                sb.append("\n)");
                            } else {
                                sb.append(relationSelect);
                                sb.append("EXISTS (\nSELECT utm1.Series_Num\nFROM UserTag_Movie utm1\nWHERE utm1.Series_Num = utm.Series_Num AND utm1.Tag_ID = utm.Tag_ID AND utm1.Tag_ID = ");
                                sb.append(selectedTag[i]);
                                sb.append(" AND utm1.Series_Num = ");
                                sb.append(selectedMovie[j]);
                                sb.append("\n)");
                            }
                        }
                    }

                    sb.append(" )");

                    sb.append("\nORDER BY utm.User_ID");

                    // System.out.println(sb.toString());

                    // Reset UI
                    userQueryContent.setText("");

                    userQueryContent.append(sb.toString());
                }
            }
        });
    }

    private static void searchMovie() {
        movieSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String relationSelect = " " + selectedRelation + " ";
                String tagWeightSelect = "AND mt1.Weight " + selectedRange + " " + selectedWeight + " )";
                StringBuilder sb = new StringBuilder();

                if (selectedGenre == null || selectedGenre.length == 0) {
                    JOptionPane.showMessageDialog(null, "Genre must be Selected, Please Select and Continue!", "Selected Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    sb.append("SELECT DISTINCT m.Series_Num, m.Title, g.Genre, m.Release_Year, m.Country, m.Rating, m.NumOfRating FROM Movie m, Genres g WHERE m.Series_Num = g.Series_Num AND ");
                    sb.append(yearSelect);

                    for (int i = 0; i < selectedGenre.length; i++) {
                        if (i == 0) {
                            sb.append(genreSelect);
                        } else {
                            sb.append(relationSelect);
                            sb.append(genreSelect);
                        }
                    }

                    sb.append(" )");

                    if (selectedCountry != null && selectedCountry.length != 0) {
                        sb.append(" AND ( ");

                        for (int i = 0; i < selectedCountry.length; i++) {
                            if (i == 0) {
                                sb.append(countrySelect);
                            } else {
                                sb.append(relationSelect);
                                sb.append(countrySelect);
                            }
                        }

                        sb.append(" )");
                    }

                    if ((selectedActor != null && selectedActor.size() != 0) || !selectedDirector.equals("")) {
                        sb.append(" AND ( ");

                        if (selectedActor != null && selectedActor.size() != 0) {
                            for (int i = 0; i < selectedActor.size(); i++) {
                                if (i == 0) {
                                    sb.append(actorSelect);
                                } else {
                                    sb.append(relationSelect);
                                    sb.append(actorSelect);
                                }
                            }

                            if (!selectedDirector.equals("")) {
                                sb.append(relationSelect);
                                sb.append(directorSelect);
                            }
                            sb.append(" )");
                        }
                        else {
                            sb.append(directorSelect);
                            sb.append(" )");
                        }
                    }

                    if (selectedTag != null && selectedTag.length != 0) {
                        sb.append(" AND ( ");

                        for (int i = 0; i < selectedTag.length; i++) {
                            if (i == 0) {
                                sb.append(tagSelect);
                                sb.append(tagWeightSelect);
                            } else {
                                sb.append(relationSelect);
                                sb.append(tagSelect);
                                sb.append(tagWeightSelect);
                            }
                        }

                        sb.append(" )");
                    }

                    sb.append(" ORDER BY m.Series_Num");

                    System.out.println(sb.toString());

                    // Reset UI
                    movieListModel.removeAllElements();
                    userListModel.removeAllElements();
                    userQueryContent.setText("");

                    // Reset Data
                    selectedMovie = null;

                    try {
                        PreparedStatement statement = connection.prepareStatement(sb.toString());

                        statement.setInt(1, selectedStartYear);
                        statement.setInt(2, selectedEndYear);

                        for (int i = 0; i < selectedGenre.length; i++) {
                            statement.setString(i + 3, selectedGenre[i]);
                        }

                        if (selectedCountry != null && selectedCountry.length != 0) {
                            for (int i = 0; i < selectedCountry.length; i++) {
                                statement.setString(i + 3 + selectedGenre.length, selectedCountry[i]);
                            }
                        }

                        if (selectedActor != null && selectedActor.size() != 0) {
                            for (int i = 0; i < selectedActor.size(); i++) {
                                statement.setString(i + 3 + selectedGenre.length
                                        + (selectedCountry != null ? selectedCountry.length : 0), selectedActor.get(i));
                            }
                        }

                        if (!selectedDirector.equals("")) {
                            statement.setString(3 + selectedGenre.length
                                    + (selectedCountry != null ? selectedCountry.length : 0)
                                    + (selectedActor != null ? selectedActor.size() : 0), selectedDirector);
                        }

                        if (selectedTag != null && selectedTag.length != 0) {
                            for (int i = 0; i < selectedTag.length; i++) {
                                statement.setInt(i + 3 + selectedGenre.length
                                        + (selectedCountry != null ? selectedCountry.length : 0)
                                        + (selectedActor != null ? selectedActor.size() : 0)
                                        + (!selectedDirector.equals("") ? 1 : 0), selectedTag[i]);
                            }
                        }

                        ResultSet movie = statement.executeQuery();

                        while (movie.next()) {
                            movieListModel.addElement(movie.getString(1) + "        " + movie.getString(2)
                                    + "        " + movie.getString(3) + "        " + movie.getString(4)
                                    + "        " + movie.getString(5) + "        " + movie.getString(6)
                                    + "        " + movie.getString(7));
                        }
                    } catch (SQLException se) {
                        System.out.println("SQLException occurs: " + se);
                    }
                }
            }
        });
    }

    private static void searchUser() {
        userSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String relationSelect = " " + selectedRelation + " ";
                StringBuilder sb = new StringBuilder();

                if (selectedTag == null || selectedTag.length == 0) {
                    JOptionPane.showMessageDialog(null, "Tag must be Selected, Please Select and Continue!", "Selected Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (selectedMovie == null || selectedMovie.length == 0) {
                    JOptionPane.showMessageDialog(null, "Movie must be Selected, Please Select and Continue!", "Selected Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    sb.append("SELECT DISTINCT utm.User_ID FROM UserTag_Movie utm WHERE ( ");

                    for (int i = 0; i < selectedTag.length; i++) {
                        for (int j = 0; j < selectedMovie.length; j++) {
                            if (i == 0 && j == 0) {
                                sb.append(userSelect);
                            } else {
                                sb.append(relationSelect);
                                sb.append(userSelect);
                            }
                        }
                    }

                    sb.append(" )");

                    sb.append(" ORDER BY utm.User_ID");

                    System.out.println(sb.toString());

                    // Reset UI
                    userListModel.removeAllElements();

                    try {
                        PreparedStatement statement = connection.prepareStatement(sb.toString());

                        int count = 1;
                        for (int i = 0; i < selectedTag.length; i++) {
                            for (int j = 0; j < selectedMovie.length; j++) {
                                statement.setInt(count, selectedTag[i]);
                                count++;
                                statement.setInt(count, selectedMovie[j]);
                                count++;
                            }
                        }

                        ResultSet user = statement.executeQuery();

                        while (user.next()) {
                            userListModel.addElement(user.getString(1));
                        }
                    } catch (SQLException se) {
                        System.out.println("SQLException occurs: " + se);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        // Connect to Database
        dbConnection();

        // Open Application
        new hw3();

        // Initialize the Application
        initialization();

        // Set Up Listener
        genreListener();
        yearListener();
        relationListener();
        countryListener();
        actorListener();
        directorListener();
        tagListener();
        movieListener();

        // Set Up Button to Show all the Results
        showCountry();
        showCast();
        showTag();
        showMovieQuery();
        showUserQuery();
        searchMovie();
        searchUser();
    }
}