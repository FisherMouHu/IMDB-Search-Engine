CREATE TABLE IMDB_User(
    User_ID     NUMBER,
    First_Name  VARCHAR(20),
    Last_Name   VARCHAR(20),
    Gender      VARCHAR(5),
    Email       VARCHAR(30),
    Birth_Date  DATE,
    PRIMARY KEY(User_ID)
);

CREATE TABLE Person(
    ID          VARCHAR(100),
    Name	   VARCHAR(100),
    Gender      VARCHAR(5),
    Birth_Date  DATE,
    Nation      VARCHAR(20),
    State       VARCHAR(20),
    Province    VARCHAR(20),
    Town        VARCHAR(20),
    PRIMARY KEY(ID)
);

CREATE TABLE Critic(
    ID          VARCHAR(100),
    Name	   VARCHAR(100),
    Gender      VARCHAR(5),
    Birth_Date  DATE,
    Nation      VARCHAR(20),
    State       VARCHAR(20),
    Province    VARCHAR(20),
    Town        VARCHAR(20),
    PRIMARY KEY(ID)
);

CREATE TABLE Producer(
    ID          VARCHAR(100),
    Name	   VARCHAR(100),
    Gender      VARCHAR(5),
    Birth_Date  DATE,
    Nation      VARCHAR(20),
    State       VARCHAR(20),
    Province    VARCHAR(20),
    Town        VARCHAR(20),
    PRIMARY KEY(ID)
);

CREATE TABLE Director(
    ID          VARCHAR(100),
    Name	   VARCHAR(100),
    Gender      VARCHAR(5),
    Birth_Date  DATE,
    Nation      VARCHAR(20),
    State       VARCHAR(20),
    Province    VARCHAR(20),
    Town        VARCHAR(20),
    PRIMARY KEY(ID)
);

CREATE TABLE Actor(
    ID          VARCHAR(100),
    Name	   VARCHAR(100),
    Gender      VARCHAR(5),
    Birth_Date  DATE,
    Nation      VARCHAR(20),
    State       VARCHAR(20),
    Province    VARCHAR(20),
    Town        VARCHAR(20),
    PRIMARY KEY(ID)
);

CREATE TABLE Movie(
    Series_Num   NUMBER,
    Title        VARCHAR(200),
    Country      VARCHAR(40),
    Release_Year NUMBER,
    Language     VARCHAR(10),
    Product_Cost NUMBER,
    Rating	    NUMBER,
    NumOfRating  NUMBER,
    PRIMARY KEY(Series_Num)
);

CREATE TABLE Genres(
    Series_Num  NUMBER	  NOT NULL,
    Genre       VARCHAR(20),
    FOREIGN KEY(Series_Num) REFERENCES Movie
    ON DELETE CASCADE
);

CREATE TABLE Location(
    Series_Num  NUMBER	  NOT NULL,
    Country	   VARCHAR(100),
    State	   VARCHAR(100),
    City	   VARCHAR(100),
    Street	   VARCHAR(150),
    FOREIGN KEY(Series_Num) REFERENCES Movie
    ON DELETE CASCADE
);

CREATE TABLE Scene(
    Series_Num  NUMBER	  NOT NULL,
    Num         NUMBER,
    Location    VARCHAR(30),
    FOREIGN KEY(Series_Num) REFERENCES Movie
    ON DELETE CASCADE
);

CREATE TABLE TV_Show(
    Series_Num  VARCHAR(10),
    TV_Network  VARCHAR(10),
    Name        VARCHAR(30),
    PRIMARY KEY(Series_Num)
);

CREATE TABLE Reality_Show(
    Series_Num  VARCHAR(10),
    TV_Network  VARCHAR(10),
    Name        VARCHAR(30),
    PRIMARY KEY(Series_Num)
);

CREATE TABLE Season(
    Series_Num  VARCHAR(10)     NOT NULL,
    Season_Num  NUMBER,
    Start_Date  DATE,
    End_Date    DATE,
    PRIMARY KEY(Series_Num,Season_Num),
    FOREIGN KEY(Series_Num) REFERENCES TV_Show
    ON DELETE CASCADE
);

CREATE TABLE Episode(
    Series_Num  VARCHAR(10)     NOT NULL,
    Season_Num  NUMBER,
    Episode_Num NUMBER,
    Title       VARCHAR(30),
    Length      NUMBER,
    PRIMARY KEY(Series_Num,Season_Num,Episode_Num),
    FOREIGN KEY(Series_Num,Season_Num) REFERENCES Season
    ON DELETE CASCADE
);

CREATE TABLE Award(
    Event       VARCHAR(30)     NOT NULL,
    Category    VARCHAR(30)     NOT NULL,
    PRIMARY KEY(Event, Category)
);

CREATE TABLE Review(
    Review_ID    VARCHAR(10),
    Content      VARCHAR(1000),
    Publish_Date DATE,
    PRIMARY KEY(Review_ID)
);

CREATE TABLE Critic_Review(
    Review_ID    VARCHAR(10),
    Content      VARCHAR(1000),
    Publish_Date DATE,
    Vote_Num     NUMBER,
    Star_Num     NUMBER,
    PRIMARY KEY(Review_ID)
);

CREATE TABLE User_Review(
    Review_ID    VARCHAR(10),
    Content      VARCHAR(1000),
    Publish_Date DATE,
    PRIMARY KEY(Review_ID)
);

CREATE TABLE Recommended_Review(
    Review_ID    VARCHAR(10),
    Content      VARCHAR(1000),
    Publish_Date DATE,
    PRIMARY KEY(Review_ID)
);

CREATE TABLE Vote(
    Review_ID   VARCHAR(10)     NOT NULL,
    Vote_Type   VARCHAR(10),
    FOREIGN KEY(Review_ID) REFERENCES Review ON DELETE CASCADE,
    CHECK(Vote_Type='Cool' OR Vote_Type='Funny' OR Vote_Type='Useful')
);

CREATE TABLE Favor_Movie(
    User_ID     NUMBER		NOT NULL,
    Series_Num  NUMBER		NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User
    ON DELETE CASCADE
);

CREATE TABLE ToWatch_Movie(
    User_ID     NUMBER		NOT NULL,
    Series_Num  NUMBER		NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User
    ON DELETE CASCADE
);

CREATE TABLE UserReviewed_Movie(
    User_ID     NUMBER		NOT NULL,
    Series_Num  NUMBER		NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User
    ON DELETE CASCADE
);

CREATE TABLE CriticReviewed_Movie(
    ID          VARCHAR(100)	NOT NULL,
    Series_Num  NUMBER		NOT NULL,
    FOREIGN KEY(ID) REFERENCES Critic
    ON DELETE CASCADE
);

CREATE TABLE Favor_Show(
    User_ID     NUMBER		NOT NULL,
    Series_Num  VARCHAR(10)     NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User
    ON DELETE CASCADE
);

CREATE TABLE ToWatch_Show(
    User_ID     NUMBER		NOT NULL,
    Series_Num  VARCHAR(10)	NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User
    ON DELETE CASCADE
);

CREATE TABLE UserReviewed_Show(
    User_ID     NUMBER		NOT NULL,
    Series_Num  VARCHAR(10)	NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User
    ON DELETE CASCADE
);

CREATE TABLE CriticReviewed_Show(
    ID          VARCHAR(100)	NOT NULL,
    Series_Num  VARCHAR(10)	NOT NULL,
    FOREIGN KEY(ID) REFERENCES Critic
    ON DELETE CASCADE
);

CREATE TABLE User_Write(
    User_ID     NUMBER		NOT NULL,
    Review_ID   VARCHAR(10)     NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User ON DELETE CASCADE,
    FOREIGN KEY(Review_ID) REFERENCES User_Review ON DELETE CASCADE
);

CREATE TABLE User_View(
    User_ID     NUMBER		NOT NULL,
    Review_ID   VARCHAR(10)     NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User ON DELETE CASCADE,
    FOREIGN KEY(Review_ID) REFERENCES Recommended_Review ON DELETE CASCADE
);

CREATE TABLE User_Vote(
    User_ID     NUMBER		NOT NULL,
    Review_ID   VARCHAR(10)     NOT NULL,
    Vote_Type   VARCHAR(10)     NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User ON DELETE CASCADE,
    FOREIGN KEY(Review_ID) REFERENCES Critic_Review ON DELETE CASCADE,
    CHECK(Vote_Type='Cool' OR Vote_Type='Funny' OR Vote_Type='Useful')
);

CREATE TABLE UserRate_Movie(
    User_ID     NUMBER		NOT NULL,
    Series_Num  NUMBER		NOT NULL,
    Point       NUMBER          NOT NULL,
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE,
    CHECK(Point >= 0 AND Point <= 5)
);

CREATE TABLE UserRate_Show(
    User_ID     NUMBER		NOT NULL,
    Series_Num  VARCHAR(10)     NOT NULL,
    Point       NUMBER          NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES TV_Show ON DELETE CASCADE,
    CHECK(Point >= 0 AND Point <= 5)
);

CREATE TABLE Like_Movie(
    User_ID     NUMBER		NOT NULL,
    Series_Num  NUMBER		NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE
);

CREATE TABLE Like_Show(
    User_ID     NUMBER		NOT NULL,
    Series_Num  VARCHAR(10)     NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES TV_Show ON DELETE CASCADE
);

CREATE TABLE WantToWatch_Movie(
    User_ID     NUMBER		NOT NULL,
    Series_Num  NUMBER		NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE
);

CREATE TABLE WantToWatch_Show(
    User_ID     NUMBER		NOT NULL,
    Series_Num  VARCHAR(10)     NOT NULL,
    FOREIGN KEY(User_ID) REFERENCES IMDB_User ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES TV_Show ON DELETE CASCADE
);

CREATE TABLE Critic_Write(
    ID          VARCHAR(100)     NOT NULL,
    Review_ID   VARCHAR(10)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Critic ON DELETE CASCADE,
    FOREIGN KEY(Review_ID) REFERENCES Critic_Review ON DELETE CASCADE
);

CREATE TABLE Critic_View(
    ID          VARCHAR(100)     NOT NULL,
    Review_ID   VARCHAR(10)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Critic ON DELETE CASCADE,
    FOREIGN KEY(Review_ID) REFERENCES Review ON DELETE CASCADE
);

CREATE TABLE Recommend(
    ID          VARCHAR(100)     NOT NULL,
    Review_ID   VARCHAR(10)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Critic ON DELETE CASCADE,
    FOREIGN KEY(Review_ID) REFERENCES Review ON DELETE CASCADE
);

CREATE TABLE CriticRate_Movie(
    ID          VARCHAR(100)     NOT NULL,
    Series_Num  NUMBER		NOT NULL,
    Point       NUMBER          NOT NULL,
    FOREIGN KEY(ID) REFERENCES Critic ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE,
    CHECK(Point >= 0 AND Point <= 5)
);

CREATE TABLE CriticRate_Show(
    ID          VARCHAR(100)     NOT NULL,
    Series_Num  VARCHAR(10)     NOT NULL,
    Point       NUMBER          NOT NULL,
    FOREIGN KEY(ID) REFERENCES Critic ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES TV_Show ON DELETE CASCADE,
    CHECK(Point >= 0 AND Point <= 5)
);

CREATE TABLE Movie_Has(
    Series_Num  NUMBER		NOT NULL,
    Review_ID   VARCHAR(10)     NOT NULL,
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE,
    FOREIGN KEY(Review_ID) REFERENCES Review ON DELETE CASCADE
);

CREATE TABLE Show_Has(
    Series_Num  VARCHAR(10)     NOT NULL,
    Review_ID   VARCHAR(10)     NOT NULL,
    FOREIGN KEY(Series_Num) REFERENCES TV_Show ON DELETE CASCADE,
    FOREIGN KEY(Review_ID) REFERENCES Review ON DELETE CASCADE
);

CREATE TABLE Produce_Movie(
    ID          VARCHAR(100)     NOT NULL,
    Series_Num  NUMBER		NOT NULL,
    FOREIGN KEY(ID) REFERENCES Producer ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE
);

CREATE TABLE Produce_Show(
    ID          VARCHAR(100)     NOT NULL,
    Series_Num  VARCHAR(10)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Producer ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES TV_Show ON DELETE CASCADE
);

CREATE TABLE Direct_Movie(
    ID               VARCHAR(100)     NOT NULL,
    Series_Num       NUMBER	     NOT NULL,
    Director_Name    VARCHAR(100)£¬
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE
);

CREATE TABLE Direct_Show(
    ID          VARCHAR(100)	NOT NULL,
    Series_Num  VARCHAR(10)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Director ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES TV_Show ON DELETE CASCADE
);

CREATE TABLE Cast_Movie(
    ID          VARCHAR(100)     NOT NULL,
    Series_Num  NUMBER		NOT NULL,
    Actor_Name  VARCHAR(100),
    Role        VARCHAR(20),
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE
);

CREATE TABLE Cast_Episode(
    ID          VARCHAR(100)     NOT NULL,
    Series_Num  VARCHAR(10)     NOT NULL,
    Season_Num  NUMBER          NOT NULL,
    Episode_Num NUMBER          NOT NULL,
    Role        VARCHAR(20)     NOT NULL,
    Cast_Type   VARCHAR(10)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Actor ON DELETE CASCADE,
    FOREIGN KEY(Series_Num,Season_Num,Episode_Num) REFERENCES Episode
    ON DELETE CASCADE,
    CHECK(Cast_Type = 'Regular' OR Cast_Type = 'Guest Star')
);

CREATE TABLE Cast_RealityShow(
    ID          VARCHAR(100)     NOT NULL,
    Series_Num  VARCHAR(10)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Person ON DELETE CASCADE,
    FOREIGN KEY(Series_Num) REFERENCES Reality_Show ON DELETE CASCADE
);

CREATE TABLE Movie_Nominate(
    Series_Num  NUMBER		NOT NULL,
    Event       VARCHAR(30)     NOT NULL,
    Category    VARCHAR(30)     NOT NULL,
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE,
    FOREIGN KEY(Event,Category) REFERENCES Award ON DELETE CASCADE
);

CREATE TABLE Movie_Win(
    Series_Num  NUMBER		NOT NULL,
    Event       VARCHAR(30)     NOT NULL,
    Category    VARCHAR(30)     NOT NULL,
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE,
    FOREIGN KEY(Event,Category) REFERENCES Award ON DELETE CASCADE
);

CREATE TABLE Director_Nominate(
    ID          VARCHAR(100)     NOT NULL,
    Event       VARCHAR(30)     NOT NULL,
    Category    VARCHAR(30)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Director ON DELETE CASCADE,
    FOREIGN KEY(Event,Category) REFERENCES Award ON DELETE CASCADE
);

CREATE TABLE Director_Win(
    ID          VARCHAR(100)     NOT NULL,
    Event       VARCHAR(30)     NOT NULL,
    Category    VARCHAR(30)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Director ON DELETE CASCADE,
    FOREIGN KEY(Event,Category) REFERENCES Award ON DELETE CASCADE
);

CREATE TABLE Actor_Nominate(
    ID          VARCHAR(100)     NOT NULL,
    Event       VARCHAR(30)     NOT NULL,
    Category    VARCHAR(30)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Actor ON DELETE CASCADE,
    FOREIGN KEY(Event,Category) REFERENCES Award ON DELETE CASCADE
);

CREATE TABLE Actor_Win(
    ID          VARCHAR(100)     NOT NULL,
    Event       VARCHAR(30)     NOT NULL,
    Category    VARCHAR(30)     NOT NULL,
    FOREIGN KEY(ID) REFERENCES Actor ON DELETE CASCADE,
    FOREIGN KEY(Event,Category) REFERENCES Award ON DELETE CASCADE
);

CREATE TABLE Tag(
    Tag_ID	   NUMBER£¬
    Text	   VARCHAR(100),
    PRIMARY KEY(Tag_ID)
);

CREATE TABLE MovieHas_Tag(
    Series_Num  NUMBER,
    Tag_ID      NUMBER,
    Weight      NUMBER,
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE,
    FOREIGN KEY(Tag_ID) REFERENCES Tag ON DELETE CASCADE
);

CREATE TABLE UserTag_Movie(
    User_ID     NUMBER,
    Series_Num  NUMBER,
    Tag_ID      NUMBER,
    FOREIGN KEY(Series_Num) REFERENCES Movie ON DELETE CASCADE,
    FOREIGN KEY(Tag_ID) REFERENCES Tag ON DELETE CASCADE
);