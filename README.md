# Sudoku Game - Java Desktop Application

Welcome to the **Sudoku Project**. This project provides a fully functional Sudoku game built in Java, featuring both a Console interface and a Graphical User Interface (Swing).

## Technical Documentation (Live UML)

This project strictly follows the **GitFlow** methodology and continuously updates its technical documentation via GitHub Actions.

- [Class Diagram (UML)](CLASS_DIAGRAM.md)
- [Activity / State Diagram](ACTIVITY_DIAGRAM.md)

## Automated Reports (CI/CD)

Every push to `develop` or `main` triggers a GitHub Actions workflow that compiles the code, runs the tests, and generates the following reports automatically:

- [**Javadoc Documentation**](javadoc/index.html) (API Reference)
- [**JaCoCo Coverage Report**](coverage/index.html) (Unit Test Coverage)

*(Note: The links above will work once the GitHub Pages deployment is successful)*

## How to play locally

Requirements: Java 17+, Maven 3.6+

```bash
cd demo
mvn compile
mvn exec:java -Dexec.mainClass="com.example.Main"
```