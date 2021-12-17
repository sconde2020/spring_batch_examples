DROP TABLE student IF EXISTS;

CREATE TABLE student  (
    student_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(20),
    emailAddress VARCHAR(30),
    purchasedPackage VARCHAR(30)
);