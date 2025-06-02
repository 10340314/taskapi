CREATE TABLE IF NOT EXISTS task (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    due_date DATE,
    priority VARCHAR(10),
    status VARCHAR(20)
);