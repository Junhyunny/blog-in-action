INSERT INTO todos (title, description, completed, created_at, updated_at)
SELECT
    'TODO ' || number,
    'Sample TODO item number ' || number,
    number % 3 = 0,
    CURRENT_TIMESTAMP - ((number % 365) * INTERVAL '1 day'),
    CURRENT_TIMESTAMP - ((number % 365) * INTERVAL '1 day')
FROM generate_series(1, 1000000) AS numbers(number);

ANALYZE todos;
