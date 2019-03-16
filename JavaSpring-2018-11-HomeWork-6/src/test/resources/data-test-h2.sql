DELETE FROM author_isbn;
DELETE FROM author;
DELETE FROM book_review;
DELETE FROM book;
DELETE FROM publisher;
DELETE FROM genre;

INSERT INTO publisher (publisher_id, publisher_name)
VALUES (1, 'test_publisher_name_1');

INSERT INTO genre (genre_id, genre)
VALUES (2, 'test_genre_2');

INSERT INTO book (book_id, isbn, title, edition_number, copyright, publisher_id, genre_id)
VALUES (3, '9999999993', 'test_title_3', 3, '2003', 1, 2),
       (4, '9999999994', 'test_title_4', 4, '2004', 1, 2),
       (5, '9999999995', 'test_title_5', 5, '2005', 1, 2);


INSERT INTO author (author_id, first_name, last_name)
VALUES (6, 'test_first_name_6', 'test_last_name_6'),
       (7, 'test_first_name_7', 'test_last_name_7'),
       (8, 'test_first_name_8', 'test_last_name_8');


INSERT INTO author_isbn (author_id, book_id)
VALUES (6, 3),
       (6, 4),
       (7, 3),
       (7, 4),
       (8, 3),
       (8, 4);

INSERT INTO book_review (review_id, review, book_id)
VALUES (31, 'test_review_31', 3),
       (32, 'test_review_32', 3),
       (41, 'test_review_41', 4);
