# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run full test suite (Chrome + Firefox in parallel)
mvn clean test

# Run a single test class
mvn clean test -Dtest=CheckoutTests

# Run a single test method
mvn clean test -Dtest=CheckoutTests#singleItemCheckoutTest

# Generate and open Allure report (requires tests to have run first)
mvn allure:serve
```

## Project Standards

- **Page Object Model first** — all UI interactions belong in page classes, never in test methods.
- **ThreadLocal for WebDriver** — never store a `WebDriver` instance in a plain field; always use `BaseTest`'s `ThreadLocal` via `getDriver()`.
- **Check `BaseTest` before writing any test** — `src/test/java/base/BaseTest.java` defines the canonical `@BeforeMethod`/`@AfterMethod` lifecycle; new test classes must extend it.
- **Keep tests thin** — if logic is complex (waiting, calculating, navigating), move it to the relevant page class.

## Architecture

This is a Selenium + TestNG UI test automation project for [saucedemo.com](https://www.saucedemo.com), built with the Page Object Model pattern.

### Layer structure

```
src/test/java/
├── pages/      # Page Object classes (one per page)
├── tests/      # TestNG test classes + BaseTest + TestData
src/test/resources/
└── testng.xml  # Suite config: parallel="tests", thread-count="2"
```

### Base classes

**`BasePage`** — abstract parent for all page objects. Holds the `WebDriver` instance and a `WebDriverWait` (10 s). Every page class extends it and never manages the driver itself.

**`BaseTest`** — abstract parent for all test classes. Uses `ThreadLocal<WebDriver>` so tests on different threads each get an isolated browser session. `@BeforeMethod` reads the `browser` parameter from testng.xml to pick Chrome or Firefox; `@AfterMethod` quits and removes the driver. Subclasses call `getDriver()` to get the current thread's driver.

**`TestData`** — final class of static constants (URL, credentials, checkout form values). The single source of truth for shared test data.

### Parallel execution

`testng.xml` runs two `<test>` nodes in parallel — one with `browser=chrome`, one with `browser=firefox` — both executing `CheckoutTests`. The `ThreadLocal` design in `BaseTest` keeps the sessions isolated.

### Synchronisation strategy

- Explicit `WebDriverWait` + `ExpectedConditions` everywhere — no `Thread.sleep`.
- JavaScript executor used for clicks where Selenium's native click raises `ElementClickInterceptedException`.
- URL-change waits confirm navigation has completed before interacting with the next page.

### Allure reporting

AspectJ weaver is configured as a javaagent in the Surefire plugin (see `pom.xml`) so Allure annotations are woven at test runtime. Results land in `target/allure-results/`; `mvn allure:serve` opens the interactive HTML dashboard.
