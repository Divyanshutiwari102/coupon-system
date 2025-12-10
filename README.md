# Coupon Management System

A REST-ful API to manage e-commerce coupons and determine the best applicable coupon for a specific user and cart configuration.

## Live Demo
**Base URL:** `https://coupon-system-divyanshu.onrender.com`
*(Note: Root URL returns 404 by design. Please use the endpoints below.)*

## Project Overview
This service provides an in-memory coupon management system. It allows administrators to create coupons with complex eligibility rules and allows clients to query the "best" coupon available for a transaction based on user and cart attributes.

## Tech Stack
* **Language:** Java 17
* **Framework:** Spring Boot 3.2.3
* **Build Tool:** Maven
* **Deployment:** Docker & Render
* **Data Storage:** In-Memory (ConcurrentHashMap)

## How to Run Locally
1.  **Prerequisites:** Java 17+, Maven installed.
2.  **Clone:** `git clone https://github.com/Divyanshutiwari102/coupon-system.git`
3.  **Run:** `mvn spring-boot:run`
4.  **Test:** The server starts at `http://localhost:8080`.

## API Endpoints

### 1. Create Coupon
* **Endpoint:** `POST /coupons`
* **Description:** Create a new coupon.

### 2. Get Best Coupon
* **Endpoint:** `POST /applicable-coupons`
* **Description:** Get the best coupon for a specific cart and user.

### 3. List Coupons
* **Endpoint:** `GET /coupons`
* **Description:** Fetch all available coupons.

### 4. Reviewer Login
* **Endpoint:** `POST /login`
* **Credentials:**
    * **Email:** `hire-me@anshumat.org`
    * **Password:** `HireMe@2025!`

## Author
**Divyanshu Tiwari**
* **Email:** divyanshutiwari337@gmail.com
* **GitHub:** [Divyanshutiwari102](https://github.com/Divyanshutiwari102)
* **LinkedIn:** [Divyanshu](https://www.linkedin.com/in/divyanshu-tiwari-42b156289/)
