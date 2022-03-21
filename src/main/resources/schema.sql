DROP VIEW IF EXISTS guess_summary CASCADE;

CREATE VIEW guess_summary AS
SELECT game_id,
       DATEDIFF('SECOND', MIN(created), MAX(created)) AS duration,
       COUNT(*)                                       AS guess_count,
       MAX(exact_matches)                             AS max_match_count
FROM guess
GROUP BY game_id;

CREATE VIEW game_performance AS
SELECT g.game_id,
       g.user_id,
       g.created,
       g.pool_size,
       g.length,
       s.duration,
       s.guess_count
FROM game AS g
         JOIN guess_summary AS s
              ON s.game_id = g.game_id AND s.max_match_count = g.length;

CREATE VIEW user_performance AS
SELECT user_id,
       pool_size,
       length,
       COUNT(*)                                   AS games_completed,
       AVG(CAST(duration AS DOUBLE PRECISION))    AS average_duration,
       AVG(CAST(guess_count AS DOUBLE PRECISION)) AS average_guess_count
FROM game_performance
GROUP BY pool_size, length, user_id;
