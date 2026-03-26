# 🛒 SauceDemo UI Automation Framework

This repository contains a robust, scalable automated UI testing framework for the [SauceDemo](https://www.saucedemo.com/) web application. It demonstrates best practices in test automation, designed to handle dynamic web elements and ensure reliable test execution.

## 🚀 Technologies & Tools
* **Language:** Java 17
* **Testing Framework:** TestNG
* **UI Automation:** Selenium WebDriver (v4)
* **Build Tool:** Apache Maven
* **Reporting:** Allure Report
* **Design Pattern:** Page Object Model (POM)

## ⚙️ Key Features & Architecture
* **Strict Page Object Model (POM):** Clear separation between page locators/methods and test execution logic for maximum maintainability.
* **Parallel Execution:** Fully configured `testng.xml` to run test suites simultaneously across Google Chrome and Mozilla Firefox.
* **Thread Safety:** Implemented `ThreadLocal` for WebDriver management to prevent session collisions during parallel runs.
* **Smart Synchronization:** Replaced unreliable generic waits with `Explicit Waits` and utilized `JavaScript Executor` to completely eliminate flaky tests caused by fast browser rendering (e.g., race conditions on checkout).
* **Price Validation Logic:** Automated complex UI validations, including dynamic calculation formulas to verify if the sum of individual cart items matches the total displayed order price.

## 🧪 Test Scenarios Covered (Use Cases)
1. **UC-1: Single Item Checkout Flow**
    * Perform login -> Add a specific item to the cart -> Validate cart contents -> Fill out checkout information -> Complete purchase -> Validate final success UI message.
2. **UC-2: Multiple Items Checkout with Dynamic Price Validation**
    * Perform login -> Add multiple distinct items -> Validate all items in the cart -> Fill out checkout information -> **Extract item prices, calculate the expected subtotal, and assert against the actual displayed subtotal** -> Complete purchase -> Validate final success UI message.

## 💻 How to Run Locally

### Prerequisites
* Java JDK 17+
* Apache Maven
* Google Chrome & Mozilla Firefox installed

### Execute Tests
To run the full test suite in parallel via the command line, navigate to the project root directory and execute:
```bash
mvn clean test
View Allure Report
After the test execution is successfully finished, generate and launch the Allure dashboard by running: mvn allure:serve
### Execute Tests

**Option 2: Via Command Line (Requires global Maven installation)**
To run the test suite from the terminal, navigate to the project root directory and execute:
```bash
mvn clean test
Project Structure
src/test/java/
 ├── pages/           # Page Object classes (encapsulated locators and actions)
 └── tests/           # TestNG classes with business logic and assertions
src/test/resources/
 └── testng.xml       # TestNG configuration for parallel cross-browser execution