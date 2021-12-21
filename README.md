# Newt

An app for meeting people and having conversations.

In Newt, logged-in users are able to create and join Conversations. Conversations are the main content of the app and are defined by a prompt and some relevant topic tags. Within a conversation, users who have joined can discuss the prompt in a group-chat like format. Users can find new Conversations to join by scrolling through their feeds, the Browse feed shows them Conversations that they may be interested in (based on the user's interests) and the Friends feed shows them Conversations that the users they follow are currently a part of.

## Database Setup

[Setup Walkthrough Video](https://www.youtube.com/watch?v=0o1EFIUH57c)

1. Set up PostgreSQL on your machine.
2. Create a user called `optimisticnewt` with password `newtdb`.
3. Create a new database called `newtapp`.
4. Create `users`, `conversations` and `messages` tables in the database.
5. Grant privileges to user `optimisticnewt` for these tables and their sequences.

You can complete steps 2 and 3 by running the following in `psql`:
```
CREATE USER optimisticnewt WITH PASSWORD 'newtdb';

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

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public 
TO optimisticnewt;

GRANT USAGE, SELECT 
ON ALL SEQUENCES IN SCHEMA public 
TO optimisticnewt;
```

## Start up the Back-end API Server

1. Navigate to the project directory.
2. `mvn clean compile exec:java`

## Start up the Front-end Web Server

1. Install an up-to-date version of Node.js if you do not already have it.
2. Navigate to `src/front-end/` from the project directory.
3. `npm ci` to install dependencies (don't be alarmed by the warnings, they only concern the dev environment, you can confirm this with `npm audit -production`).
4. `npm start` to run the web server.

## Old Course Project Repository 

[Old Repo](https://github.com/CSC207-UofT/course-project-newtapp)