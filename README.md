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

