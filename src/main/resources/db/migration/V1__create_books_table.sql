CREATE TABLE IF NOT EXISTS books (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  author VARCHAR(150) NOT NULL,
  isbn VARCHAR(50) NOT NULL,
  published_date DATE,
  CONSTRAINT uk_books_isbn UNIQUE (isbn)
);
