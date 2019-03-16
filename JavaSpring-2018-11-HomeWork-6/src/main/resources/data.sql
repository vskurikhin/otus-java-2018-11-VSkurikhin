
CREATE TABLE IF NOT EXISTS author (
  author_id  BIGINT      NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(60) NOT NULL,
  last_name  VARCHAR(40) NOT NULL,
  UNIQUE (first_name, last_name),
  PRIMARY KEY (author_id)
);

CREATE TABLE IF NOT EXISTS genre (
  genre_id BIGINT      NOT NULL AUTO_INCREMENT,
  genre    VARCHAR(90) NOT NULL,
  PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS publisher (
  publisher_id   BIGINT      NOT NULL AUTO_INCREMENT,
  publisher_name VARCHAR(90) NOT NULL,
  PRIMARY KEY (publisher_id)
);

CREATE TABLE IF NOT EXISTS book (
  book_id        BIGINT      NOT NULL AUTO_INCREMENT,
  isbn           VARCHAR(20) NOT NULL,
  title          VARCHAR(90) NOT NULL,
  edition_number INT,
  copyright      VARCHAR(40) NOT NULL,
  publisher_id   BIGINT REFERENCES publisher (publisher_id),
  genre_id       BIGINT REFERENCES genre (genre_id),
  UNIQUE (isbn),
  PRIMARY KEY (book_id)
);

CREATE TABLE IF NOT EXISTS author_isbn (
  author_id BIGINT NOT NULL REFERENCES author (author_id),
  book_id   BIGINT NOT NULL REFERENCES book (book_id)
);

CREATE TABLE IF NOT EXISTS book_review (
  review_id BIGINT       NOT NULL AUTO_INCREMENT,
  review    VARCHAR(255) NOT NULL,
  book_id   BIGINT REFERENCES book (book_id),
  PRIMARY KEY (review_id)
);

INSERT INTO genre (genre_id,genre)
SELECT 1, 'Information Technology'
  FROM dual
 WHERE NOT exists(SELECT * FROM genre WHERE genre_id = 1);

INSERT INTO publisher (publisher_id, publisher_name)
SELECT 1, 'Prentice Hall PTR Upper Saddle River, NJ, USA'
  FROM dual
 WHERE NOT exists(SELECT * FROM publisher WHERE publisher_id = 1);

INSERT INTO book (book_id, isbn, title, edition_number, copyright, publisher_id, genre_id)
SELECT 1, '0130895601', 'Advanced Java 2 Platform How to Program', 1, '2002', 1, 1
  FROM dual
 WHERE NOT exists(SELECT * FROM book WHERE book_id = 1);
INSERT INTO book (book_id, isbn, title, edition_number, copyright, publisher_id, genre_id)
SELECT 2, '0130829293', 'XML How to Program', 1, '2001', 1, 1
  FROM dual
 WHERE NOT exists(SELECT * FROM book WHERE book_id = 2);
INSERT INTO book (book_id, isbn, title, edition_number, copyright, publisher_id, genre_id)
SELECT 3, '0130895520', 'The Complete Perl Training Course', 1, '2001', 1, 1
  FROM dual
 WHERE NOT exists(SELECT * FROM book WHERE book_id = 3);

INSERT INTO author (author_id, first_name, last_name)
SELECT 1, 'Harvey', 'Deitel'
  FROM dual
 WHERE NOT exists(SELECT * FROM author WHERE author_id = 1);
INSERT INTO author (author_id, first_name, last_name)
SELECT 2, 'Paul', 'Deitel'
  FROM dual
 WHERE NOT exists(SELECT * FROM author WHERE author_id = 2);
INSERT INTO author (author_id, first_name, last_name)
SELECT 3, 'Tem', 'Nieto'
  FROM dual
 WHERE NOT exists(SELECT * FROM author WHERE author_id = 3);

INSERT INTO author_isbn (author_id, book_id)
VALUES (1, 1),
       (2, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (3, 3);

INSERT INTO book_review (review_id, review, book_id)
VALUES (11, 'test_review_11', 1),
       (12, 'test_review_12', 1),
       (21, 'test_review_21', 2);
