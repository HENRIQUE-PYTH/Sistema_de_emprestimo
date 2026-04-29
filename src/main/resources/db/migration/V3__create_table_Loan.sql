CREATE TABLE loan (
    id BIGINT NOT NULL AUTO_INCREMENT,

    user_id BIGINT NOT NULL ,
    book_id BIGINT NOT NULL,

    loan_date DATE,
    due_date DATE,
    return_date DATE,

    fine_amount DECIMAL(10,2) NOT NULL,
    days_late INTEGER,

    PRIMARY KEY (id),

    CONSTRAINT fk_loan_user
                  FOREIGN KEY (user_id)
                  REFERENCES user(id),

    CONSTRAINT fk_loan_book
                  FOREIGN KEY (book_id)
                  REFERENCES book(id)

);