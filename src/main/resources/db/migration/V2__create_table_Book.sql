CREATE TABLE book (

    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(150) NOT NULL,
    available BOOLEAN NOT NULL,
    isbn VARCHAR (100) UNIQUE NOT NULL,
    genre VARCHAR(70) NOT NULL,
    publication_year YEAR NOT NULL,
    active TINYINT NOT NULL,

    PRIMARY KEY (id)
);