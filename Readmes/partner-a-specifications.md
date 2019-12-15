# Partner A

## Features and Functional Requirements

This document contains all of the functional requirements of the system.

---

### Retail API Service

##### Port

This service runs on port 8181.

##### API

The Retail API is a BFF edge service that contains all API endpoints for searching inventory and purchasing products. This API does not require authentication. This API is not documented. You must pull all of the inventory search and product purchasing endpoint from the existing code, document them in Swagger, and reimplement them in the new Retail API Service.

##### Backing Services

The Retail API Service communicates with the following backing services:

* Level Up
* Order
* Inventory
* Product
* Customer

##### Business Rules

* 10 Level Up points are awarded for each $50 purchased.
* These points are not pro-rated.
  * For example:
    * A $49 order gets zero Level Up points.
    * A $99 order gets 10 Level Up points.
    * A $110 order gets 20 Level Up points.
* Level Up points are submitted when the order is submitted.
* Level Up points totals are returned as part of the completed invoice.
* Order quantity must be greater than zero and less than or equal to the number of items in inventory.
* Orders must contain valid products.
* An order must contain a valid customer.

##### Architecture Requirements

* Level Up points must be submitted to the Level Up service via a queue.
* Level Up total inquiries must be run through a circuit breaker.
* See diagram above.

---

### Level Up Service

##### Port

This service runs on port 7001.

##### Description

This is a microservice that contains all CRUD functionality for the Level Up! rewards system. This service has not been designed. You must design and document the API and implement the entire service.

The Read endpoint of the service must have a circuit breaker.

---

### Invoice Service

##### Port

This service runs on port 7002.

##### Description

This is a microservice that contains all CRUD functionality for Invoices. This service has not been designed, but a shell implementation of the CRUD endoints for Invoices is in the existing code. Find these endpoints, document the endpoints in Swagger and reimplement them in the new Invoice microservice.

---

### Inventory Service

##### Port

This service runs on port 7003.

##### Description

This is a microservice that contains all CRUD functionality for inventory. This service has not been designed. You must design and document the API and implement the entire service.


---

© 2019 Trilogy Education Services
