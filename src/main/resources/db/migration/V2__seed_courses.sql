INSERT INTO course (slug, title, duration_months, sort_order) VALUES ('java-essentials', 'Java Essentials', 4, 1);
INSERT INTO course (slug, title, duration_months, sort_order, includes_course_id)
VALUES ('java-advanced', 'Java Advanced', 9, 2, (SELECT id FROM course WHERE slug = 'java-essentials'));

INSERT INTO course_month (course_id, month_number, title, description)
SELECT c.id, m.n, m.title, m.descr
FROM course c,
     (VALUES (1, 'Java Foundations', 'JDK & IDE setup, syntax, variables, types, operators, control flow, methods, arrays.'),
             (2, 'Object-Oriented Programming', 'Classes, objects, encapsulation, inheritance, polymorphism, interfaces, records, enums.'),
             (3, 'Collections, Generics & Exceptions', 'List, Set, Map, generics, exception handling, String API, Git basics.'),
             (4, 'Functional Java & Final Project', 'Lambdas, Stream API, Optional, file I/O, unit testing intro, final project.')
     ) AS m(n, title, descr)
WHERE c.slug = 'java-essentials';

INSERT INTO course_month (course_id, month_number, title, description)
SELECT c.id, m.n, m.title, m.descr
FROM course c,
     (VALUES (1, 'Advanced Core Java', 'JVM internals, memory, concurrency, multithreading, CompletableFuture, virtual threads.'),
             (2, 'Databases & SQL', 'Relational modelling, SQL, joins, indexes, transactions, PostgreSQL.'),
             (3, 'JDBC, JPA & Hibernate', 'JDBC, ORM concepts, entity mapping, relations, N+1 problem, Flyway.'),
             (4, 'Spring Core & Spring Boot', 'IoC, dependency injection, beans, configuration, profiles, Spring Boot.'),
             (5, 'REST APIs with Spring', 'REST design, controllers, validation, error handling, OpenAPI, Spring Data.'),
             (6, 'Spring Security', 'Authentication, authorization, JWT, OAuth2 basics, securing APIs.'),
             (7, 'Testing', 'JUnit 5, Mockito, integration tests, Testcontainers, test strategy.'),
             (8, 'Microservices & Messaging', 'Docker, microservice patterns, Kafka/RabbitMQ, caching with Redis.'),
             (9, 'Cloud, CI/CD & Final Project', 'CI/CD pipelines, cloud deployment, monitoring, final project & interview prep.')
     ) AS m(n, title, descr)
WHERE c.slug = 'java-advanced';
