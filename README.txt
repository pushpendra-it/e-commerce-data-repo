Order Management System
------------------------
This Spring Boot application manages orders and order items through a REST API.
It integrates with a MySQL database using Spring Data JPA.

Prerequisites
--------------
Ensure you have the following installed:
Java         : JDK (versin 8+)
DataBase     :MySQL (version 8.0)
Container    : Docker (version version 24.0.6, build ed223bc )
Building tool :Maven (version 3.0.5)
IDE          : Eclipse
Open API     : Swagger

Setup
-----
Clone the repository:
bash
Copy code
git clone <https://github.com/pushpendra-it/e-commerce--orderApi.git>
cd order-management-system


Database Configuration:
----------------------
Create a MySQL database named order_management_db(according to you).
Modify the application.properties file in src/main/resources to configure the database connection:
properties
Copy code
spring.datasource.url=jdbc:postgresql://localhost:5432/order_management_db
spring.datasource.username = username
spring.datasource.password=password



Building the Application:
--------------------------
Open the project in your IDE.
Resolve Dependencies: Right-click on the project -> Maven -> Update Project... -> Check Force Update of Snapshots/Releases -> OK.


Running with Docker
---------------------
Docker Configuration:
Provide a Dockerfile and a docker-compose.yml file.
Dockerfile might look like:
Dockerfile
Copy code
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/order-management-system.jar /app
EXPOSE 9093
CMD ["java", "-jar", "order-management-system.jar"]
Create a docker-compose.yml file to set up the application service and the database service (if required).

Running the Application:
Build and run the Docker containers:
bash
Copy code
docker-compose up


REST API Documentation
=======================
The REST API is defined using OpenAPI 3.0.
You can access the API documentation at http://localhost:9093/swagger-ui.html.

API Endpoints
Order API
GET /api/orders - Get a list of all orders
GET /api/orders/{id} - Get an order by ID
POST /api/orders - Create a new order
PUT /api/orders/{id} - Update an existing order
DELETE /api/orders/{id} - Delete an order by ID

OrderItem API
GET /api/order-items - Get a list of all order items
GET /api/order-items/{id} - Get an order item by ID
POST /api/order-items - Create a new order item
PUT /api/order-items/{id} - Update an existing order item
DELETE /api/order-items/{id} - Delete an order item by ID

Error Handling
==============
The application handles errors gracefully and returns appropriate HTTP status codes.