CREATE TABLE task (
    id INT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    due_date DATE,
    priority VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL
);