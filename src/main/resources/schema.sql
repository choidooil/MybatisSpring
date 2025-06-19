CREATE TABLE IF NOT EXISTS users (
    userid BIGINT  PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    useraddress VARCHAR(200) NOT NULL
);