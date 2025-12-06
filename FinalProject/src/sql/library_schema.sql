CREATE DATABASE library;

USE library;

CREATE TABLE employee (
	username varchar(30),
    password varchar(30),
    first_name varchar(30),
    last_name varchar(30),
    access_level ENUM( 'VIEW', 'EDIT', 'ADD'),
    PRIMARY KEY(username)
);

CREATE TABLE patron (
	username varchar(30),
    password varchar(30),
    first_name varchar(30),
    last_name varchar(30),
    card_number integer,
    date_joined date,
    address varchar(70),
    email varchar(30),
    phone varchar(16),
    has_stop boolean,
    home_location enum('MAIN', 'NORTH', 'SOUTH', 'EAST', 'WEST'),
    PRIMARY KEY(username)
);

-- information about an item the library may or may not have copies of
-- groups together copies of the same item
CREATE TABLE record (
	record_number char(9),
    title varchar(50),
    call_number varchar(30),
    image_path varchar(50),
    circulating boolean,
    section enum('JUVENILE_FICTION', 'JUVENILE_NON_FICTION','JUVENILE_PICTURE','JUVENILE_DVD',
		'JUVENILE_CD','FICTION','NON_FICTION','HIGH_SCHOOL_FICTION','HIGH_SCHOOL_NON_FICTION',
        'YOUNG_PERSON_FICTION','YOUNG_PERSON_NON_FICTION','DVD','CD','PERIODICAL',
        'SPANISH_NON_FICTION','SPANISH_FICTION','TOY'),
    PRIMARY KEY(record_number)
);

-- an actual, physical copy the library has
CREATE TABLE item (
	barcode bigint,
    location enum('MAIN', 'NORTH', 'SOUTH', 'EAST', 'WEST'),
    record_number char(9),
    circulations int,
    status enum('AVAILABLE', 'CHECKED_OUT', 'ON_HOLDSHELF', 'IN_EVAL', 'IN_MENDING', 'IN_TRANSIT'),
    PRIMARY KEY(barcode),
    FOREIGN KEY(record_number) REFERENCES record(record_number)
);


CREATE TABLE checkout (
	patron varchar(30),
    barcode bigint,
    due_date date,
    FOREIGN KEY(patron) REFERENCES patron(username),
    FOREIGN KEY(barcode) REFERENCES item(barcode)
);

CREATE TABLE fine (
    patron varchar(30),
    amount decimal(5, 2),
    date_began date,
    item bigint,
    PRIMARY KEY(patron, item),
    FOREIGN KEY(patron) REFERENCES patron(username),
    FOREIGN KEY(item) REFERENCES item(barcode)
);

-- date expires will be two weeks after date placed
CREATE TABLE hold (
	patron varchar(30),
	item bigint,
    date_placed date,
    date_expires date,
    pickup_location enum('MAIN', 'NORTH', 'SOUTH', 'EAST', 'WEST'),
    PRIMARY KEY(patron, item, date_placed),
    FOREIGN KEY(patron) REFERENCES patron(username),
    FOREIGN KEY(item) REFERENCES item(barcode)
);



-- represent a book the library may or may not have copies of
CREATE TABLE book_record (
	record_number char(9),
    author_first_name varchar(30),
    author_last_name varchar(30),
    publication_date date,
    edition varchar(30),
    isbn bigint,
    genre varchar(30),
    PRIMARY KEY(record_number),
    FOREIGN KEY(record_number) REFERENCES record(record_number)
);

-- represent a DVD or CD that the library may or may not have copies of
CREATE TABLE av_record (
	record_number char(9),
    publication_year year,
    disc_count integer,
    av_type enum('CD','DVD'),
    volume integer,
    PRIMARY KEY(record_number),
    FOREIGN KEY(record_number) REFERENCES record(record_number)
);

-- represent a periodical (newspaper or magazine) the library may or may not have copies of
CREATE TABLE periodical_record (
	record_number char(9),
    edition varchar(30),
    volume integer,
    publication_date date,
    PRIMARY KEY(record_number),
    FOREIGN KEY(record_number) REFERENCES record(record_number)
);

-- represent an educational toy the library may or may not have copies of (puzzles, games, figures, etc.)
CREATE TABLE toy_record (
	record_number char(9),
    pieces integer,
    PRIMARY KEY(record_number),
    FOREIGN KEY(record_number) REFERENCES record(record_number)
);

INSERT INTO record VALUES ('b12345678', 'Harry Potter and the Sorcerer''s Stone', 'JF Rowling', 'images/default_cover.jpg', TRUE, 'JUVENILE_FICTION');
INSERT INTO book_record VALUES ('b12345678', 'J.K.', 'Rowling', '1997-06-27', 'First Edition', 9780747532699, 'Fantasy');
INSERT INTO item VALUES(123456789, 'MAIN', FALSE, 'b12345678', 55);
INSERT INTO item VALUES(539166743, 'NORTH', TRUE, 'b12345678', 32);
-- Items that are checked out should have an entry in checkout table

INSERT INTO record VALUES('b3290397', 'Avengers: Endgame', 'DVD 791.4372 A892345 2019', 'images/default_cover.jpg', TRUE, 'DVD');
INSERT INTO av_record(record_number, publication_year, disc_count, av_type) VALUES('b3290397', 2019, 1, 'DVD');
INSERT INTO item VALUES(673946102, 'WEST', FALSE, 'b3290397', 15);

INSERT INTO record VALUES('b87392016', 'The Salt Lake Tribune', 'PER', 'images/default_cover.jpg', FALSE, 'PERIODICAL');
INSERT INTO periodical_record VALUES('b87392016', 'October 2025', 1, '2025-10-01');
INSERT INTO item VALUES(753297674, 'EAST', FALSE, 'b87392016', 0);
-- this item is non-circulating so it will always have FALSE for checked_out and 0 for circulations

INSERT INTO record VALUES('b45196834', 'Learning Shapes Puzzle', 'TOY 513.25 L', 'images/default_cover.jpg', TRUE, 'TOY');
INSERT INTO toy_record VALUES('b45196834', 15);
INSERT INTO item VALUES(65983201, 'SOUTH', TRUE, 'b45196834', 3);


INSERT INTO patron VALUES('jdoe12', 'kx2Dbr8m1?', 'Jane', 'Doe', 987654321, '2024-07-01', '123 Example St., Salt Lake City, UT, 84000', 'jdoe12@example.com', '(123) 555-5678', FALSE, 'EAST');
INSERT INTO checkout VALUES('jdoe12', 123456789, '2025-12-05');
