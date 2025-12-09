# Coupon Management System

A REST-ful API to manage e-commerce coupons and determine the best applicable coupon for a specific user and cart configuration.

## Project Overview
[cite_start]This service provides an in-memory coupon management system[cite: 12]. It allows administrators to create coupons with complex eligibility rules and allows clients to query the "best" coupon available for a transaction.

## Tech Stack
* **Language:** Java 17
* **Framework:** Spring Boot 3.2
* **Build Tool:** Maven
* **Data Storage:** In-Memory (ConcurrentHashMap)

## How to Run
1.  **Prerequisites:** Java 17+, Maven.
2.  **Clone:** `git clone <repo-url>`
3.  **Run:** `mvn spring-boot:run`
4.  **Test:** The server starts at `http://localhost:8080` (or the PORT defined by your cloud provider).

## API Endpoints
* `POST /coupons`: Create a new coupon.
* `POST /applicable-coupons`: Get the best coupon for a cart/user.
* `POST /login`: Reviewer login endpoint.

## AI Usage Note
I utilized AI tools to assist in generating the standard Spring Boot controller structure and ensuring the JSON models aligned with the assignment requirements.