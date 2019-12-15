# Partner B

## Features and Functional Requirements

This document contains all of the functional requirements of the system.

---

### Admin API

##### Port

This service runs on port 8282.

##### API

The Admin API is a BFF edge service that contains all the endpoints for CRUD operations of the following:

* Customers
* Products
* Inventory
* Orders
* Level Up Points

This API is not documented and is only partially implemented in the existing code. You must pull out the parts of the API that are implemented in the existing code, model them in Swagger, and reimplement them in the new Admin API service. Use the existing API endpoints as a model for the missing endpoints.

##### Security Rules

The security rules for the Admin API Service are:

* All Admin API endpoints require authentication.
* Admin Role
  * Can access all endpoints.
* Manager Role
  * Can Create, Read, and Update all items in the system.
* Team Lead Role
  * Can Read and Update all items in the system.
  * Can Create Customers in the system.
* Employee Role
  * Can read all items in the system.
  * Can Update Inventory in the system.

---

### Spring Security

The Admin API must be protected by Spring Security (roles and access rules are found in the Functional Requirements above).

* You must use the default Spring Security schema.
* All passwords must be hashed with BCrypt
---

### Customer Service

##### Port

This service runs on port 7005.

##### Description

This is a microservice that contains all the CRUD functionality for Customers. This service has not been designed. you must design and document the API and implement the entire service.

---

### Product Service

##### Port

This service runs on port 7004.

##### Description

This is a microservice that contains all the CRUD functionality for Products. This service contains information on all the Products that the company has sold in the past and may sell in the future. This service does not contain information about current inventory levels. This service has not been designed. you must design and document the API and implement the entire service.

---

© 2019 Trilogy Education Services
