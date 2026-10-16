-- Full curriculum: 8 lessons per month for Java Essentials (4 months) and Java Advanced (9 months).
-- The first lessons of month 1 are free previews. Edit everything later in Admin -> Courses.

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 1), 10, 'Introduction to Java & the JVM',
        '### What you will learn
- What Java is used for: backend, fintech, Android, big data
- JDK vs JRE vs JVM, bytecode and "write once, run anywhere"
- Installing JDK 21 and IntelliJ IDEA
- Anatomy of a Java program: class, main method, statements
- Compiling and running from IDE and terminal

### Example
```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, Java!");
    }
}
```

### Practice
Install the JDK and IntelliJ, then write a program that prints your name, age and why you want to learn Java.',
        TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 1), 20, 'Variables & Primitive Types',
        '### What you will learn
- Declaring and initializing variables
- The 8 primitive types: int, long, double, boolean, char...
- Type casting: widening and narrowing
- `var` and type inference
- Naming conventions and constants with `final`

### Example
```java
int quantity = 3;
double price = 12.5;
final double DISCOUNT = 0.1;
double total = quantity * price * (1 - DISCOUNT);
System.out.println("Total: " + total);
```

### Practice
Write a program that stores a product name, price, quantity and discount, then prints the total price.',
        TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 1), 30, 'Operators & Expressions',
        '### What you will learn
- Arithmetic operators and integer division
- Comparison and logical operators
- Increment/decrement and compound assignment
- Operator precedence
- Useful `Math` methods

### Example
```java
int year = 2028;
boolean leap = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
System.out.println(year + " is leap: " + leap);
```

### Practice
Build a BMI calculator and a program that checks whether a year is a leap year.',
        TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 1), 40, 'Strings & Console Input',
        '### What you will learn
- Creating strings and immutability
- Common methods: length, substring, indexOf, equals
- Reading input with `Scanner`
- Formatting output with `printf` and `String.format`
- Text blocks

### Practice
Ask the user for their full name and print it in upper case, its length and initials.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 1), 50, 'Conditionals',
        '### What you will learn
- `if` / `else if` / `else`
- Classic `switch` and modern arrow `switch`
- Switch expressions with `yield`
- The ternary operator
- Writing readable conditions

### Practice
Write a grade converter (score to A/B/C/D/F) and a simple menu-driven program.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 1), 60, 'Loops',
        '### What you will learn
- `for`, `while` and `do-while`
- `break` and `continue`
- Nested loops
- Common loop patterns: sum, count, min/max, search

### Practice
Print a multiplication table, find prime numbers up to N and build a number-guessing game.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 1), 70, 'Methods',
        '### What you will learn
- Defining methods: parameters and return values
- Method overloading
- Variable scope
- Pass-by-value in Java
- Introduction to recursion

### Practice
Refactor your previous programs into small methods; write a recursive factorial and Fibonacci.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 1), 80, 'Arrays & Month 1 Project',
        '### What you will learn
- One- and two-dimensional arrays
- Iterating with `for` and for-each
- The `Arrays` utility class: sort, fill, toString
- Month 1 review

### Practice
Project: a console Grade Book that stores students'' scores, calculates averages and prints the top student.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 2), 10, 'Classes & Objects',
        '### What you will learn
- Thinking in objects: state and behaviour
- Fields, methods and constructors
- The `this` keyword
- Creating and using objects

### Practice
Model a `BankAccount` class with deposit, withdraw and balance.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 2), 20, 'Encapsulation',
        '### What you will learn
- Access modifiers: private, default, protected, public
- Getters, setters and validation
- Immutable objects
- Information hiding

### Practice
Make `BankAccount` safe: no negative balances, immutable account number.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 2), 30, 'Static Members & Packages',
        '### What you will learn
- `static` fields and methods
- `final` variables, methods and classes
- Packages and imports
- Organizing a project structure

### Practice
Create a `utils` package with a static `MathUtils` class and use it from another package.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 2), 40, 'Inheritance',
        '### What you will learn
- `extends` and the is-a relationship
- `super` and constructor chaining
- Method overriding and `@Override`
- The `Object` class: `toString`, `equals`, `hashCode`

### Practice
Build an `Employee` hierarchy: `Manager` and `Developer` with different salary rules.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 2), 50, 'Polymorphism & Abstract Classes',
        '### What you will learn
- Runtime polymorphism and dynamic dispatch
- Upcasting and downcasting, `instanceof` pattern matching
- Abstract classes and methods
- When to use abstraction

### Practice
Create an abstract `Shape` with `Circle`, `Rectangle`, `Triangle` and calculate total area of a list of shapes.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 2), 60, 'Interfaces',
        '### What you will learn
- Defining and implementing interfaces
- Default and static methods
- Multiple interfaces
- Composition over inheritance

### Practice
Design `Payable` and `Notifiable` interfaces for a payment system.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 2), 70, 'Records, Enums & Wrapper Classes',
        '### What you will learn
- Records for immutable data
- Enums with fields and methods
- Wrapper classes, autoboxing and unboxing
- Comparing wrappers safely

### Practice
Model an order system with an `OrderStatus` enum and `OrderItem` record.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 2), 80, 'OOP Project: Library System',
        '### What you will learn
- Class design and simple UML diagrams
- Applying encapsulation, inheritance and interfaces together
- Month 2 review

### Practice
Project: a console Library Management app with books, members, borrowing and returning.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 3), 10, 'Exceptions',
        '### What you will learn
- What happens when things go wrong
- Checked vs unchecked exceptions
- `try` / `catch` / `finally`
- `try-with-resources`

### Practice
Make your Library app handle invalid input and missing books without crashing.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 3), 20, 'Custom Exceptions & Best Practices',
        '### What you will learn
- Creating custom exception classes
- Throwing and rethrowing
- Exception chaining
- What to catch and what to let fail

### Practice
Add `InsufficientFundsException` to `BankAccount` and handle it in the UI layer.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 3), 30, 'Generics',
        '### What you will learn
- Why generics: type safety
- Generic classes and methods
- Bounded type parameters
- Wildcards: `? extends` and `? super`

### Practice
Write a generic `Box<T>` and a generic `Pair<K, V>` plus a method that finds the max element of any list.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 3), 40, 'Lists',
        '### What you will learn
- The Collections Framework overview
- `ArrayList` vs `LinkedList`
- Iterating, adding, removing safely
- Big-O basics for collections

### Practice
Implement a to-do list manager using `List`.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 3), 50, 'Sets & Maps',
        '### What you will learn
- `HashSet`, `LinkedHashSet`, `TreeSet`
- `HashMap`, `LinkedHashMap`, `TreeMap`
- The `equals`/`hashCode` contract
- Counting and grouping with maps

### Practice
Count word frequencies in a text and print the top 10 words.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 3), 60, 'Queues, Sorting & Comparators',
        '### What you will learn
- `Queue`, `Deque`, `PriorityQueue`
- `Comparable` vs `Comparator`
- Sorting with `Comparator.comparing` and chaining
- Choosing the right collection

### Practice
Simulate a bank queue and sort employees by department, then salary.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 3), 70, 'Strings Deep Dive & Date/Time',
        '### What you will learn
- `StringBuilder` and performance
- Regular expressions basics
- `java.time`: LocalDate, LocalDateTime, Duration
- Formatting and parsing dates

### Practice
Validate e-mails and phone numbers with regex; calculate a person''s age from a birth date.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 3), 80, 'Git & GitHub + Mini Project',
        '### What you will learn
- Git basics: init, add, commit, branch, merge
- GitHub: push, pull requests, README
- Month 3 review

### Practice
Project: a Contact Book app with search and sorting, published on your GitHub.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 4), 10, 'Lambdas & Functional Interfaces',
        '### What you will learn
- Lambda syntax
- `Function`, `Predicate`, `Supplier`, `Consumer`, `BiFunction`
- Method references
- Writing your own functional interface

### Practice
Replace anonymous classes in your projects with lambdas and method references.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 4), 20, 'Stream API I',
        '### What you will learn
- Creating streams from collections and arrays
- `filter`, `map`, `sorted`, `distinct`, `limit`
- Terminal operations: `collect`, `forEach`, `count`
- Lazy evaluation

### Practice
Given a list of products, find the 5 most expensive in-stock products.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 4), 30, 'Stream API II',
        '### What you will learn
- `reduce` and numeric streams
- `flatMap`
- `Collectors.groupingBy`, `partitioningBy`, `joining`
- Streams vs loops: readability and performance

### Practice
Build a sales report: revenue per category, best-selling product, average order value.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 4), 40, 'Optional & Null Safety',
        '### What you will learn
- The billion-dollar mistake: `null`
- Creating and using `Optional`
- `map`, `orElse`, `orElseThrow`
- Where not to use `Optional`

### Practice
Refactor a repository class to return `Optional` instead of `null`.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 4), 50, 'Files & I/O',
        '### What you will learn
- `Path` and `Files` (NIO)
- Reading and writing text files
- Working with CSV data
- Handling I/O exceptions

### Practice
Import transactions from a CSV file and export a summary report.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 4), 60, 'Unit Testing with JUnit 5',
        '### What you will learn
- Why we test
- Test structure: Arrange-Act-Assert
- Assertions and testing exceptions
- Introduction to TDD

### Practice
Write unit tests for `BankAccount` and your Grade Book calculations.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 4), 70, 'Build Tools & Clean Code',
        '### What you will learn
- Maven and Gradle: dependencies and builds
- Standard project layout
- Clean code: naming, small methods, SOLID intro
- Code review basics

### Practice
Convert one of your projects to Gradle and refactor it using clean-code rules.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-essentials' AND m.month_number = 4), 80, 'Final Project & Next Steps',
        '### What you will learn
- Requirements and design
- Implementation with collections, streams, files and tests
- Presentation and code review
- Roadmap to Java Advanced

### Practice
Final project: a Personal Expense Tracker (categories, monthly reports, CSV import/export, unit tests) on GitHub.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 1), 10, 'JVM Architecture',
        '### What you will learn
- Class loading and class loaders
- Runtime memory areas: heap, stack, metaspace
- Bytecode and the JIT compiler
- Reading `javap` output

### Practice
Inspect bytecode of a simple class and explain what the JVM does step by step.',
        TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 1), 20, 'Garbage Collection & Memory',
        '### What you will learn
- How GC works: generations, G1, ZGC
- Memory leaks in Java
- Profiling with VisualVM and Java Flight Recorder
- Basic JVM tuning flags

### Practice
Create a memory leak on purpose, find it with a profiler and fix it.',
        TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 1), 30, 'Threads Basics',
        '### What you will learn
- Processes vs threads
- `Thread` and `Runnable`
- Thread lifecycle
- Race conditions

### Practice
Write a program that shows a race condition with a shared counter.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 1), 40, 'Synchronization',
        '### What you will learn
- `synchronized` blocks and methods
- `volatile` and visibility
- `ReentrantLock`, `ReadWriteLock`
- Deadlocks and how to avoid them

### Practice
Fix the race condition three different ways and compare them.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 1), 50, 'Executors & Thread Pools',
        '### What you will learn
- `ExecutorService` and thread pools
- `Callable` and `Future`
- Shutting down executors correctly
- Sizing thread pools

### Practice
Download/process 100 files in parallel with a fixed thread pool.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 1), 60, 'CompletableFuture',
        '### What you will learn
- Asynchronous programming
- Chaining: `thenApply`, `thenCompose`, `thenCombine`
- Error handling in async code
- Timeouts

### Practice
Call three slow services in parallel and combine their results.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 1), 70, 'Concurrent Collections & Virtual Threads',
        '### What you will learn
- `ConcurrentHashMap`, `CopyOnWriteArrayList`, `BlockingQueue`
- Atomic variables
- Virtual threads in Java 21
- Producer-consumer pattern

### Practice
Build a producer-consumer pipeline, then run 10,000 tasks on virtual threads.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 1), 80, 'Modern Java (17–21)',
        '### What you will learn
- Sealed classes and interfaces
- Pattern matching for `instanceof` and `switch`
- Record patterns
- Month 1 review

### Practice
Project: a multithreaded log analyzer using modern Java features.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 2), 10, 'Relational Databases & PostgreSQL',
        '### What you will learn
- Relational model: tables, rows, keys
- Running PostgreSQL with Docker
- Tools: psql and DBeaver
- Creating databases and users

### Practice
Run PostgreSQL in Docker and connect with DBeaver.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 2), 20, 'DDL & Constraints',
        '### What you will learn
- Data types in PostgreSQL
- CREATE / ALTER / DROP
- Primary keys, foreign keys, UNIQUE, CHECK, NOT NULL
- Sequences and identity columns

### Practice
Create the schema for a library database with proper constraints.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 2), 30, 'Querying Data',
        '### What you will learn
- INSERT, UPDATE, DELETE
- SELECT, WHERE, ORDER BY, LIMIT/OFFSET
- LIKE, IN, BETWEEN, NULL handling
- Aliases and expressions

### Practice
Write 15 queries against a sample dataset.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 2), 40, 'Joins & Relationships',
        '### What you will learn
- One-to-one, one-to-many, many-to-many
- INNER, LEFT, RIGHT, FULL joins
- Self joins
- Join tables

### Practice
Answer business questions that require joining 3+ tables.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 2), 50, 'Aggregation & Advanced SQL',
        '### What you will learn
- GROUP BY and HAVING
- Subqueries
- Common Table Expressions (WITH)
- Window functions: ROW_NUMBER, RANK, SUM OVER

### Practice
Build monthly revenue and top-customer reports.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 2), 60, 'Schema Design & Normalization',
        '### What you will learn
- Entity-Relationship diagrams
- Normal forms 1NF–3NF
- When to denormalize
- Naming conventions

### Practice
Design an ERD for an online shop.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 2), 70, 'Indexes & Performance',
        '### What you will learn
- How B-tree indexes work
- Creating the right indexes
- `EXPLAIN ANALYZE`
- Common performance mistakes

### Practice
Speed up slow queries on a table with 1M rows.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 2), 80, 'Transactions & Concurrency',
        '### What you will learn
- ACID
- Isolation levels and anomalies
- Row locking and `SELECT ... FOR UPDATE`
- Month 2 review

### Practice
Project: implement and query the online-shop database (orders, payments, stock).',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 3), 10, 'JDBC',
        '### What you will learn
- Connecting Java to a database
- `PreparedStatement` and SQL injection
- Mapping `ResultSet` to objects
- Connection pools with HikariCP

### Practice
Write a DAO layer for the shop database with plain JDBC.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 3), 20, 'ORM & JPA Basics',
        '### What you will learn
- Object-relational mismatch
- JPA vs Hibernate
- Entities and `EntityManager`
- Persistence context and entity states

### Practice
Replace one JDBC DAO with JPA.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 3), 30, 'Entity Mapping',
        '### What you will learn
- Ids and generation strategies
- Column mapping, enums, dates
- Embeddables
- Inheritance mapping strategies

### Practice
Map the shop domain model with JPA annotations.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 3), 40, 'Relationships',
        '### What you will learn
- `@ManyToOne`, `@OneToMany`, `@ManyToMany`
- Owning side and `mappedBy`
- Cascade types and orphan removal
- Bidirectional helpers

### Practice
Map orders, order items and products with correct relationships.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 3), 50, 'Fetching & the N+1 Problem',
        '### What you will learn
- Lazy vs eager loading
- The N+1 problem and how to detect it
- JOIN FETCH and entity graphs
- Batch fetching

### Practice
Find and fix N+1 queries in a sample project using SQL logs.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 3), 60, 'Queries: JPQL, Criteria & Native',
        '### What you will learn
- JPQL queries and parameters
- Projections and DTO queries
- Criteria API for dynamic queries
- Native SQL when needed

### Practice
Build a dynamic product search with optional filters.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 3), 70, 'Transactions & Locking',
        '### What you will learn
- Transaction boundaries
- Optimistic locking with `@Version`
- Pessimistic locking
- Auditing: created/updated timestamps

### Practice
Prevent double stock reservation with optimistic locking.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 3), 80, 'Database Migrations',
        '### What you will learn
- Why migrations
- Flyway: versioned scripts
- Liquibase: changelogs
- Month 3 review

### Practice
Project: the shop persistence layer with Flyway migrations and integration with PostgreSQL.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 4), 10, 'Inversion of Control & DI',
        '### What you will learn
- Tight vs loose coupling
- Dependency injection patterns
- The Spring `ApplicationContext`
- Constructor injection

### Practice
Wire a small app manually, then with Spring.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 4), 20, 'Beans & Configuration',
        '### What you will learn
- `@Component`, `@Service`, `@Repository`
- `@Configuration` and `@Bean`
- Bean scopes and lifecycle
- Qualifiers and primary beans

### Practice
Create two payment providers and switch between them with configuration.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 4), 30, 'Spring Boot Fundamentals',
        '### What you will learn
- What Spring Boot adds
- Starters and auto-configuration
- Project structure
- Running and packaging

### Practice
Generate a project with Spring Initializr and explore auto-configuration reports.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 4), 40, 'Configuration & Profiles',
        '### What you will learn
- `application.yml`
- Profiles for dev/test/prod
- `@ConfigurationProperties`
- Externalized configuration and environment variables

### Practice
Add dev and prod profiles with different databases.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 4), 50, 'Spring AOP & Transactions',
        '### What you will learn
- Proxies and aspects
- Writing a logging aspect
- How `@Transactional` really works
- Common transactional pitfalls

### Practice
Write an aspect that measures method execution time.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 4), 60, 'Spring Data JPA',
        '### What you will learn
- Repositories and derived queries
- `@Query`
- Paging and sorting
- Specifications

### Practice
Rewrite the shop DAO layer with Spring Data JPA.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 4), 70, 'Logging & Monitoring',
        '### What you will learn
- SLF4J and Logback
- Log levels and structured logs
- Spring Boot Actuator
- Health checks and metrics

### Practice
Add meaningful logs and Actuator endpoints to your app.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 4), 80, 'Layered Application Project',
        '### What you will learn
- Controller–service–repository architecture
- DTOs vs entities
- Month 4 review

### Practice
Project: a layered Spring Boot backend for the online shop.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 5), 10, 'REST & HTTP',
        '### What you will learn
- REST principles and resources
- HTTP methods and idempotency
- Status codes
- Designing clean URLs

### Practice
Design the REST API (endpoints, methods, status codes) for the shop.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 5), 20, 'Controllers & DTOs',
        '### What you will learn
- `@RestController`
- Path variables, query params, request bodies
- Request/response DTOs
- Testing with Postman

### Practice
Implement CRUD endpoints for products and categories.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 5), 30, 'Validation',
        '### What you will learn
- Bean Validation annotations
- Validating nested objects
- Custom validators
- Validation groups

### Practice
Validate order creation requests with clear error messages.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 5), 40, 'Error Handling',
        '### What you will learn
- `@ControllerAdvice` and `@ExceptionHandler`
- `ProblemDetail` (RFC 9457)
- Mapping domain exceptions to HTTP
- Consistent error format

### Practice
Add global error handling to the shop API.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 5), 50, 'Mapping, Pagination & Versioning',
        '### What you will learn
- MapStruct for DTO mapping
- Pagination and sorting in APIs
- Filtering
- API versioning strategies

### Practice
Add paginated, filterable product search.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 5), 60, 'API Documentation',
        '### What you will learn
- OpenAPI specification
- springdoc and Swagger UI
- Documenting models and errors
- API-first approach

### Practice
Publish interactive API docs for the shop.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 5), 70, 'Calling Other Services',
        '### What you will learn
- `RestClient` and `WebClient`
- OpenFeign
- Timeouts and retries
- Handling remote errors

### Practice
Integrate a currency-exchange API into price calculation.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 5), 80, 'REST API Project',
        '### What you will learn
- End-to-end API design and implementation
- Month 5 review

### Practice
Project: a complete Booking or Shop REST API with validation, errors, docs and pagination.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 6), 10, 'Security Fundamentals',
        '### What you will learn
- Authentication vs authorization
- OWASP Top 10
- Hashing vs encryption
- Threat thinking for backend developers

### Practice
Review the shop API and list its security risks.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 6), 20, 'Spring Security Architecture',
        '### What you will learn
- The security filter chain
- `SecurityContext` and `Authentication`
- `SecurityFilterChain` configuration
- Debugging security

### Practice
Trace a request through the filter chain with debug logs.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 6), 30, 'Users, Login & Passwords',
        '### What you will learn
- `UserDetailsService` from the database
- Password encoding with BCrypt
- Form login and HTTP Basic
- Account locking

### Practice
Store users in PostgreSQL and enable login.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 6), 40, 'Authorization',
        '### What you will learn
- Roles and authorities
- URL-based rules
- Method security: `@PreAuthorize`
- Ownership checks

### Practice
Let customers see only their own orders; admins see all.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 6), 50, 'JWT Authentication',
        '### What you will learn
- Stateless authentication
- Access and refresh tokens
- Signing and validating JWTs
- Token revocation strategies

### Practice
Secure the shop API with JWT.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 6), 60, 'OAuth2 & OpenID Connect',
        '### What you will learn
- OAuth2 flows
- OpenID Connect
- Resource server configuration
- Keycloak / Google login

### Practice
Add "Login with Google" or Keycloak to a demo app.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 6), 70, 'Web Security Details',
        '### What you will learn
- CORS
- CSRF
- Security headers
- Managing secrets safely

### Practice
Configure CORS for a frontend and move secrets to environment variables.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 6), 80, 'Security Project',
        '### What you will learn
- Hardening a real API
- Month 6 review

### Practice
Project: fully secured shop API with roles, JWT and security tests.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 7), 10, 'Testing Strategy',
        '### What you will learn
- The test pyramid
- Unit vs integration vs end-to-end
- What is worth testing
- Test naming and structure

### Practice
Write a test plan for the shop application.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 7), 20, 'JUnit 5 in Depth',
        '### What you will learn
- Lifecycle and extensions
- Parameterized tests
- AssertJ fluent assertions
- Testing time and randomness

### Practice
Cover the pricing and discount logic with parameterized tests.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 7), 30, 'Mockito',
        '### What you will learn
- Mocks, stubs and spies
- Verifying interactions
- Argument captors
- When not to mock

### Practice
Unit-test services in isolation from repositories and external APIs.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 7), 40, 'Spring Boot Test Slices',
        '### What you will learn
- `@SpringBootTest`
- `@WebMvcTest` with MockMvc
- `@DataJpaTest`
- Test configuration and profiles

### Practice
Test controllers and repositories with slices.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 7), 50, 'Testcontainers',
        '### What you will learn
- Why real dependencies in tests
- PostgreSQL in Testcontainers
- Kafka and Redis containers
- Speeding up container tests

### Practice
Run repository tests against real PostgreSQL.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 7), 60, 'API Testing',
        '### What you will learn
- Testing REST endpoints end-to-end
- RestAssured
- Contract testing introduction
- Testing security rules

### Practice
Write end-to-end tests for the order flow.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 7), 70, 'Code Quality',
        '### What you will learn
- Coverage with JaCoCo
- SonarQube and static analysis
- Code review checklists
- Refactoring safely with tests

### Practice
Reach 80% meaningful coverage and fix Sonar issues.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 7), 80, 'Testing Project',
        '### What you will learn
- Building a reliable test suite
- Month 7 review

### Practice
Project: a complete automated test suite for the course project.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 8), 10, 'From Monolith to Microservices',
        '### What you will learn
- Monolith vs microservices: trade-offs
- Domain-Driven Design and bounded contexts
- Service boundaries
- Real migration story: ECOM onboarding

### Practice
Split the shop domain into services on paper.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 8), 20, 'Docker in Depth',
        '### What you will learn
- Images, containers, layers
- Writing efficient Dockerfiles
- Docker Compose for multi-service apps
- Networking and volumes

### Practice
Containerize the shop and run it with Compose.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 8), 30, 'Spring Cloud',
        '### What you will learn
- Config server
- Service discovery
- API gateway
- Inter-service communication

### Practice
Build order and payment services behind an API gateway.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 8), 40, 'Resilience',
        '### What you will learn
- Failures in distributed systems
- Circuit breaker with Resilience4j
- Retries, timeouts, bulkheads
- Rate limiting

### Practice
Make the order service survive a failing payment service.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 8), 50, 'Apache Kafka',
        '### What you will learn
- Topics, partitions, offsets
- Producers and consumers in Spring
- Consumer groups
- Delivery guarantees

### Practice
Publish `OrderCreated` events and consume them in a notification service.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 8), 60, 'Event-Driven Architecture',
        '### What you will learn
- Events vs commands
- Outbox pattern
- Sagas for distributed transactions
- Idempotent consumers

### Practice
Implement an order saga with the outbox pattern.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 8), 70, 'Caching & Other Brokers',
        '### What you will learn
- Caching with Redis and Spring Cache
- Cache invalidation strategies
- RabbitMQ overview
- Choosing Kafka vs RabbitMQ

### Practice
Cache the product catalog in Redis.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 8), 80, 'Observability',
        '### What you will learn
- Centralized logging
- Distributed tracing
- Metrics with Prometheus and Grafana
- Month 8 review

### Practice
Project: microservice shop with Kafka events, tracing and dashboards.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 9), 10, 'CI/CD Pipelines',
        '### What you will learn
- Continuous integration and delivery
- GitHub Actions workflows
- Build, test and Docker image stages
- Quality gates

### Practice
Create a pipeline that tests and builds your project on every push.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 9), 20, 'Kubernetes Basics',
        '### What you will learn
- Pods, deployments, services
- ConfigMaps and Secrets
- Health probes
- Scaling

### Practice
Deploy the shop services to a local Kubernetes cluster.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 9), 30, 'Cloud Deployment',
        '### What you will learn
- Cloud basics with GCP: Cloud Run, Cloud SQL
- Deploying to Railway
- Environment configuration
- Costs and monitoring

### Practice
Deploy your application to the cloud with a managed database.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 9), 40, 'System Design Fundamentals',
        '### What you will learn
- Scalability and load balancing
- Caching layers and CDNs
- CAP theorem and consistency
- Designing a payment system

### Practice
Design a URL shortener and a payment-link system (like Click to Birpay).',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 9), 50, 'Final Project: Architecture',
        '### What you will learn
- Choosing the project
- Requirements and user stories
- Architecture and ERD
- Planning sprints

### Practice
Submit the architecture document for your final project.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 9), 60, 'Final Project: Implementation',
        '### What you will learn
- Building core features
- Code reviews
- Testing and fixing
- Mentor checkpoints

### Practice
Implement the core of your final project with tests.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 9), 70, 'Final Project: Deployment & Demo',
        '### What you will learn
- CI/CD for the final project
- Production deployment
- Demo and presentation
- Feedback

### Practice
Deploy and present your final project.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO lesson (month_id, sort_order, title, content, preview, published, created_at, updated_at)
VALUES ((SELECT m.id FROM course_month m JOIN course c ON c.id = m.course_id WHERE c.slug = 'java-advanced' AND m.month_number = 9), 80, 'Career: CV & Interviews',
        '### What you will learn
- Writing a strong backend developer CV
- GitHub portfolio
- Java, Spring and SQL interview questions
- System design interviews

### Practice
Prepare your CV and do a mock technical interview.',
        FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
