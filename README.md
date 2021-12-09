# Newt

An app for meeting people and having conversations.

## Database Setup

[Setup Walkthrough Video](https://www.youtube.com/watch?v=0o1EFIUH57c)

1. Set up PostgreSQL on your machine.
2. Create a new database called `newtapp`.
3. Create `users`, `conversations` and `messages` tables in the database (see SQL commands below).

You can complete steps 2 and 3 by running the following in `psql`:
```
CREATE DATABASE newtapp;

\c newtapp

CREATE TABLE conversations (
    id serial PRIMARY KEY,
    title text,
    topics text[],
    location text,
    location_radius int,
    min_rating int,
    max_size int,
    is_open boolean,
    messages int[],
    users int[],
    author_id int
);

CREATE TABLE users (
    id serial PRIMARY KEY,
    username text NOT NULL UNIQUE,
    password text NOT NULL,
    location text,
    interests text[],
    total_rating int,
    num_ratings int,
    following int[],
    followers int[],
    blocked_users int[],
    conversations int[],
    authorities text[],
    rated_users int[]
);

CREATE TABLE messages (
    id serial PRIMARY KEY,
    body text,
    author integer,
    written_at text,
    last_updated_at text,
    conversation_id int
);
```

## Start up the Back-end API Server

1. Navigate to the project directory.
2. `mvn clean compile exec:java`

## Start up the Front-end Web Server

1. Install an up-to-date version of Node.js if you do not already have it.
2. Navigate to `src/front-end/` from the project directory.
3. `npm ci` to install dependencies (don't be alarmed by the warnings, they only concern the dev environment, you can confirm this with `npm audit -production`).
4. `npm start` to run the web server.


