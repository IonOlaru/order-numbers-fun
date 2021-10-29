package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

    private final static Logger logger = LoggerFactory.getLogger(TransactionsService.class);
    private final JdbcTemplate jdbcTemplate;

    short retries = 1000;

    public TransactionsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertNewRecord(String orderNumberPrefix) {
        short attempt = 0;
        while (++attempt < retries) {
            try {
                jdbcTemplate.update("INSERT INTO people (first_name, last_name, order_number_prefix) values (?, ?, ?)",
                        "John",
                        "Doe",
                        orderNumberPrefix);
                return;
            } catch (DataAccessException e) {
                if (attempt % 100 == 0)
                    System.out.println(Thread.currentThread().getName() + " " + orderNumberPrefix + " attempt " + attempt);
            }
        }
    }

}
