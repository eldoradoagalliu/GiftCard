# Gift Card

Gift Card is a REST backend application for creating gift cards. It can be used by clients and businesses interested
in having the option of issuing coupons in their business activity. The application was build with Java 17, Spring Boot
framework, Spring Security with JWT Authentication, Firestore database and Docker for deployment.

---

Application features
-

- Client/Business login and registration
- Roles setup of Administrator and Client
- Administrator functions - Handle new and existing clients, cover any gift card coupon issue
- Client/Business functions - Create and validate gift card coupons
- Email service for retrieving and validating gift card coupons

Backend features
-

- Spring Security implemented with custom JWT authentication
- REST APIs exposed for users and gift cards actions
- Role authority configuration implemented for using certain APIs
- Firebase client is initialized and used for storing data
- Used Dockerfile for application deployment in Render platform

To run locally the application you need to configure the environments variables (similar to .env.example) and add
the secret private key (a json file) for Firebase database.