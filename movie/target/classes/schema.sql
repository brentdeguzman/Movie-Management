-- Creating the movie table with UUID as the primary key
CREATE TABLE MOVIE (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
