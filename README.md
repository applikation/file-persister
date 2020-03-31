# File Parser and Record Persister

A complete/self sufficient spring boot application is complete with Unit and Integration Tests
which can get a file from the corresponding endpoint as String and then can parse using the 
CSV parsing methodology, discard the malformed rows & finally save it into DB.
Also, endpoints are provided to retrieve and delete the records, based on the primary key/Id 
provided in the URL.

##### - Entry controller Class is DataController. 
##### - Service interfaces are placed as in under com.task.file.persister.service
##### - CSVParserService in com.task.file.persister.serviceimpl can be changed as and when requirement changes

Project Setup/Requirements:
- Java 8
- Maven
- Spring Boot/Data-JPA/Test/Actuators
- H2 DB
- Lombok(Convenience library for Setter, Getter, Constructors etc.) 


