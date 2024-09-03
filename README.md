This project is a Spring Framework-based Customer Support System implemented in Java, designed to facilitate real-time communication between customers and support agents via a REST API. Customers can open support tickets for specific products, and the system employs predefined strategies to match them with the most suitable available agent. Real-time chat is enabled through the MQTT protocol, ensuring prompt assistance. The system is secured with JWT authentication, enforcing role-based access control to ensure that different users (customers, agents) have access only to specific functionalities. After each support session, customers can rate their experience, providing valuable feedback. The project also includes a registration feature for new customers and utilizes Flyway for database schema versioning and migration, ensuring consistent schema management across environments. Integration and unit tests, implemented using JUnit and Spring Test, guarantee the system's reliability and correctness. This documentation provides a concise overview of a secure and scalable customer support solution, detailing its key features and technologies.
