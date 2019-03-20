CREATE TABLE IF NOT EXISTS acl_sid (
  id                BIGINT       NOT NULL AUTO_INCREMENT,
  principal         TINYINT(1)   NOT NULL,
  sid               VARCHAR(100) NOT NULL,
  UNIQUE(sid,principal),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS acl_class (
  id                BIGINT       NOT NULL AUTO_INCREMENT,
  class             VARCHAR(100) NOT NULL,
  UNIQUE(class),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
  id                 BIGINT       NOT NULL AUTO_INCREMENT,
  object_id_class    BIGINT       NOT NULL REFERENCES acl_class (id),
  object_id_identity BIGINT       NOT NULL,
  parent_object      BIGINT              REFERENCES acl_object_identity (id),
  owner_sid          BIGINT              REFERENCES acl_sid (id),
  entries_inheriting TINYINT(1) NOT NULL,
  UNIQUE(object_id_class, object_id_identity),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS acl_entry (
  id                  BIGINT       NOT NULL AUTO_INCREMENT,
  acl_object_identity BIGINT       NOT NULL REFERENCES acl_object_identity (id),
  ace_order           INT          NOT NULL,
  sid                 BIGINT       NOT NULL REFERENCES acl_sid (id),
  mask                INTEGER      NOT NULL,
  granting            TINYINT(1)   NOT NULL,
  audit_success       TINYINT(1)   NOT NULL,
  audit_failure       TINYINT(1)   NOT NULL,
  UNIQUE(acl_object_identity, ace_order),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user (
  id                BIGINT       NOT NULL AUTO_INCREMENT,
  login             VARCHAR(255),
  password          VARCHAR(255),
  expired           TINYINT(1)   NOT NULL,
  locked            TINYINT(1)   NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS author (
  author_id         BIGINT      NOT NULL AUTO_INCREMENT,
  first_name        VARCHAR(60) NOT NULL,
  last_name         VARCHAR(40) NOT NULL,
  UNIQUE (first_name, last_name),
  PRIMARY KEY (author_id)
);

CREATE TABLE IF NOT EXISTS genre (
  genre_id          BIGINT      NOT NULL AUTO_INCREMENT,
  value             VARCHAR(90) NOT NULL,
  UNIQUE (value),
  PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS book (
  book_id           BIGINT      NOT NULL AUTO_INCREMENT,
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
  review_id         BIGINT       NOT NULL AUTO_INCREMENT,
  review            VARCHAR(255) NOT NULL,
  book_id           BIGINT REFERENCES book (book_id),
  PRIMARY KEY (review_id)
);
