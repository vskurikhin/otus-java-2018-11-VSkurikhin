
CREATE TABLE IF NOT EXISTS author (
    author_id BIGINT NOT NULL AUTO_INCREMENT
  , first_name VARCHAR(60) NOT NULL
  , last_name VARCHAR(40) NOT NULL
  , UNIQUE (first_name, last_name)
  , PRIMARY KEY (author_id)
);

CREATE TABLE IF NOT EXISTS genre (
    genre_id BIGINT NOT NULL AUTO_INCREMENT
  , genre  VARCHAR(90) NOT NULL
  , PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS publisher (
    publisher_id BIGINT NOT NULL AUTO_INCREMENT
  , publisher_name  VARCHAR(90) NOT NULL
  , PRIMARY KEY (publisher_id)
);

CREATE TABLE IF NOT EXISTS book (
    book_id BIGINT NOT NULL AUTO_INCREMENT
  , isbn VARCHAR(20) NOT NULL
  , title VARCHAR(90) NOT NULL
  , edition_number INT
  , copyright VARCHAR(40) NOT NULL
  , publisher_id BIGINT REFERENCES publisher(publisher_id)
  , genre_id BIGINT REFERENCES genre(genre_id)
  , UNIQUE (isbn)
  , PRIMARY KEY (book_id)
);

CREATE TABLE IF NOT EXISTS author_isbn (
    author_id BIGINT NOT NULL REFERENCES author(author_id)
  , book_id BIGINT NOT NULL REFERENCES book(book_id)
);
