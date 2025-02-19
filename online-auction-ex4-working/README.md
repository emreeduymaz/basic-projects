# Online Auction System

This project aims to create a robust online auction system using REST APIs, with full CRUD (Create, Read, Update, Delete) functionality for users and auctions. The online auction system will allow users to list and bid on items in various categories, with all data stored permanently either in XML or a Postgres database.

## Technologies Used

- Java 19
- Spring Boot
- JPA
- Lombok
- Maven
- Vertabelo for database design
- Postgres as the database
- Postman for API testing
- PayPal Sandbox for simulating payment operations

## Features

### User Management (via REST API)

- **Adding a User:** Create a new user profile to participate in the online auction system.
- **Editing a User:** Update details of an existing user profile.
- **Deleting a User:** Remove an existing user profile from the system.
- **Retrieving User Information:** View details of a user profile.

### Auction Management (via REST API)

- **Adding an Item to an Auction:** List a new item for auction.
- **Editing an Item for Bidding:** Modify the details of a listed item.
- **Removing an Item from the Auction:** Unlist a previously listed item.
- **Downloading Information about the Item at the Auction:** Fetch details of a listed item.

### Payment (via REST API)

- **Making Payment for the Auctioned Item:** Use the PayPal Sandbox to simulate the payment transaction for the auctioned item.

### Auction Interface

- **Conduct Auctions:** The application provides an interface to manage and participate in auctions.

## Installation and Setup

Add instructions on how to clone and run your project locally, such as any environment variables, system properties, or profiles that need to be set.

## How to Use

Provide a brief explanation on how to use the application, navigate through its features, and what each feature does.

## API Documentation

Include a link to your Postman collection or any other API documentation.

## Database Schema

Include a link or image of your database schema designed with Vertabelo.

## Tests

Add information about how to run and interpret the results of your tests, including any libraries or frameworks used for testing.

## Contact

Add your contact information or any additional instructions for users to report issues or suggest enhancements for the project.

This README.md file is just a template. Remember to replace the placeholders and the text inside the `[]` with the actual information.

Please note that you might need to adjust some details according to your project requirements and structure. It's always a good idea to include clear instructions on how to use the application, including how to set it up locally, how to run tests, and how to report issues.

