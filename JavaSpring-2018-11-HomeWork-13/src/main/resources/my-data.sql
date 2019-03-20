INSERT INTO genre (genre_id,value)
SELECT 1, 'Information Technology'
  FROM dual
 WHERE NOT exists(SELECT * FROM genre WHERE genre_id = 1);

INSERT INTO book (book_id, isbn, title, edition_number, copyright, year, genre_id)
SELECT 1, '0130895601', 'Advanced Java 2 Platform How to Program', 1, '2002', 2002, 1
  FROM dual
 WHERE NOT exists(SELECT * FROM book WHERE isbn = '0130895601');
INSERT INTO book (book_id, isbn, title, edition_number, copyright, year, genre_id)
SELECT 2, '0130829293', 'XML How to Program', 1, '2001', 2001, 1
  FROM dual
 WHERE NOT exists(SELECT * FROM book WHERE isbn = '0130829293');
INSERT INTO book (book_id, isbn, title, edition_number, copyright, year, genre_id)
SELECT 3, '0130895520', 'The Complete Perl Training Course', 1, '2001', 2001, 1
  FROM dual
 WHERE NOT exists(SELECT * FROM book WHERE isbn = '0130895520');

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
VALUES (1, SELECT book_id FROM book WHERE isbn = '0130895601'),
       (2, SELECT book_id FROM book WHERE isbn = '0130895601'),
       (1, SELECT book_id FROM book WHERE isbn = '0130829293'),
       (2, SELECT book_id FROM book WHERE isbn = '0130829293'),
       (3, SELECT book_id FROM book WHERE isbn = '0130829293'),
       (3, SELECT book_id FROM book WHERE isbn = '0130895520');

INSERT INTO book_review (review, book_id)
VALUES ('Review for Advanced Java 2 Platform How to Program', SELECT book_id FROM book WHERE isbn = '0130895601'),
       ('Advanced Review for Advanced Java 2 Platform How to Program', SELECT book_id FROM book WHERE isbn = '0130895601'),
       ('The Complete Perl Training Course review 1', SELECT book_id FROM book WHERE isbn = '0130895520'),
       ('The Complete Perl Training Course review 2', SELECT book_id FROM book WHERE isbn = '0130895520'),
       ('The Complete Perl Training Course review 3', SELECT book_id FROM book WHERE isbn = '0130895520');

INSERT INTO user (login, password, expired, locked) VALUES ('user', '$2a$04$I5btmUIjAQhu2TRCQdKueOSajNkadQ2izCM.Lmbkwpy.i3lItZym6', FALSE, FALSE);
INSERT INTO user (login, password, expired, locked) VALUES ('archivist', '$2a$04$I5btmUIjAQhu2TRCQdKueOSajNkadQ2izCM.Lmbkwpy.i3lItZym6', FALSE, FALSE);

INSERT INTO acl_sid (id, principal, sid) VALUES (1, 0, 'ROLE_USERS');
INSERT INTO acl_sid (id, principal, sid) VALUES (2, 1, 'user');
INSERT INTO acl_sid (id, principal, sid) VALUES (3, 1, 'archivist');

INSERT INTO acl_class (id, class) VALUES (1, 'ru.otus.homework.models.Book');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES (
  1,
  SELECT id FROM acl_class WHERE class = 'ru.otus.homework.models.Book',
  SELECT book_id FROM book WHERE isbn = '0130895520',
  NULL, 
  SELECT id FROM acl_sid WHERE sid = 'archivist',
  0
);

INSERT INTO acl_entry(id, acl_object_identity, ace_order, sid,                                             mask, granting, audit_success, audit_failure)
VALUES               ( 1,                   1,         1, SELECT id FROM acl_sid WHERE sid = 'archivist',     1,        1,             1,             1),
                     ( 2,                   1,         2, SELECT id FROM acl_sid WHERE sid = 'ROLE_USERS',    1,        0,             1,             1);
