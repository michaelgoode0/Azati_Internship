CREATE TABLE posts_hashtags
(
    post_id    bigserial,
    hashtag_id bigserial,
    primary key (post_id, hashtag_id),
    FOREIGN key (post_id) REFERENCES posts (id),
    FOREIGN key (hashtag_id) REFERENCES hashtags (id)
);