CREATE TABLE dual AS ( VALUES (true) );

CREATE TABLE IF NOT EXISTS user_profile (
  id                SERIAL,
  login             VARCHAR(255),
  password          VARCHAR(255),
  expired           BOOLEAN      NOT NULL,
  locked            BOOLEAN      NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS author (
  author_id         SERIAL,
  first_name        VARCHAR(60) NOT NULL,
  last_name         VARCHAR(40) NOT NULL,
  UNIQUE (first_name, last_name),
  PRIMARY KEY (author_id)
);

CREATE TABLE IF NOT EXISTS genre (
  genre_id          SERIAL,
  value             VARCHAR(90) NOT NULL,
  UNIQUE (value),
  PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS book (
  book_id           SERIAL,
  isbn              VARCHAR(20) NOT NULL,
  title             VARCHAR(90) NOT NULL,
  edition_number    INT,
  copyright         VARCHAR(40) NOT NULL,
  year              INT,
  genre_id          BIGINT REFERENCES genre (genre_id),
  UNIQUE (isbn),
  PRIMARY KEY (book_id)
);

CREATE TABLE IF NOT EXISTS author_isbn (
  author_id         BIGINT NOT NULL REFERENCES author (author_id),
  book_id           BIGINT NOT NULL REFERENCES book (book_id)
);

CREATE TABLE IF NOT EXISTS book_review (
  review_id         SERIAL,
  review            VARCHAR(255) NOT NULL,
  book_id           BIGINT REFERENCES book (book_id),
  PRIMARY KEY (review_id)
);
