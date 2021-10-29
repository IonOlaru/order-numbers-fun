# Getting Started

### Create `people` table in `test` database
```
CREATE TABLE test.people
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NULL,
    last_name VARCHAR(50) NULL,
    order_number BIGINT NULL,
    order_number_prefix VARCHAR(2) NULL,
    CONSTRAINT unq_order_number UNIQUE (order_number, order_number_prefix)
);

DELIMITER //
CREATE TRIGGER test.before_insert_autoincrement_order_number
BEFORE INSERT ON test.people FOR EACH ROW
BEGIN
SELECT MAX(order_number) INTO @max_order_number FROM test.people where order_number_prefix = NEW.order_number_prefix;
IF @max_order_number is NULL
	THEN SET NEW.order_number = FLOOR((RAND() * 90000) + 10000);
	ELSE SET NEW.order_number = @max_order_number + 1;
END IF;
END //
```

### Change `application.properties`
Change the next 3 lines to point to your database
```
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/test
spring.datasource.username=user
spring.datasource.password=password
```

### Run the app
App is configured to run with `3` threads and insert `10` records for `1` prefix.
Change class `AppRunner.java` to adjust these values as needed.

```
    private static final int THREAD_POOL_SIZE = 3;
    private static final int N_RECORDS = 10;
    private static final String[] prefixes = new String[] {"AB"};
```


### Verify data (4 prefixes, 20 records)
```
mysql> SELECT * FROM test2.people ORDER BY order_number_prefix, order_number ASC;
+----+------------+-----------+--------------+---------------------+
| id | first_name | last_name | order_number | order_number_prefix |
+----+------------+-----------+--------------+---------------------+
|  1 | John       | Doe       |        94962 | AB                  |
|  2 | John       | Doe       |        94963 | AB                  |
|  3 | John       | Doe       |        94964 | AB                  |
|  4 | John       | Doe       |        94965 | AB                  |
|  5 | John       | Doe       |        94966 | AB                  |
|  6 | John       | Doe       |        94967 | AB                  |
|  8 | John       | Doe       |        94968 | AB                  |
|  9 | John       | Doe       |        94969 | AB                  |
| 10 | John       | Doe       |        94970 | AB                  |
| 11 | John       | Doe       |        94971 | AB                  |
| 13 | John       | Doe       |        10301 | CD                  |
| 19 | John       | Doe       |        10302 | CD                  |
| 20 | John       | Doe       |        10303 | CD                  |
| 14 | John       | Doe       |        84898 | EF                  |
| 12 | John       | Doe       |        93880 | FG                  |
| 15 | John       | Doe       |        93881 | FG                  |
| 16 | John       | Doe       |        93882 | FG                  |
| 17 | John       | Doe       |        93883 | FG                  |
| 18 | John       | Doe       |        93884 | FG                  |
| 21 | John       | Doe       |        93885 | FG                  |
+----+------------+-----------+--------------+---------------------+
20 rows in set (0.00 sec)
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.6/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.6/gradle-plugin/reference/html/#build-image)
* [JDBC API](https://docs.spring.io/spring-boot/docs/2.5.6/reference/htmlsingle/#boot-features-sql)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

