# Best Buy - API Playground

This project was created for testing some functionalities of RESTful API in Best Buy API playground.

The technology stack that is used in the testing framework:

 * JAVA
 * Maven   
 * RestAssured
 * Extent Reports
 
 ### Prerequisites
 
 In order to use these tests you need to install following software:
 
 
 * [Maven](https://maven.apache.org/)
 * [Java Development Kit](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) (JDK)
 
 Besides, you need to install and start Best Buy application itself, according to [guide](https://github.com/bestbuy/api-playground/#getting-started) 
 Please pay attention to the fact that this application can face an error during installation on systems with Windows10 and the latest version of NodeJS.
 To make the application be successfully installed I had to roll back NodeJS to the version 9.1.1.
  
### Installation

After cloning the project from Githab, you can either build it in your IDE (IntelliJ IDEA or Eclipse) or just go to the project folder and type in command line "mvn clean install".

The framework set to default configuration of Best Buy application. If you installed Best Buy to other port, you should change the constant PORT in ApiTest.java file.

### How to Run Tests

You can execute tests either in IDE or just go to the project folder and type in command line "mvn test"  
  
### Results and Reports

After every execution a folder named with timestamp is created in the Reports folder where you can find HTML reports along with statistics and logs. 

### Tests Descriptions

######versionTest()

This test checks request and response to /version endpoint. 

Checkings:
* Response code 200
* Version number 

######healthCheckTest()

This test checks request and response to /healthcheck endpoint.
 
Checkings:
* Response code 200
* Presense of required fields in responses jsons. 


######getAllProductsTest()

This test checks request and response to /healthcheck endpoint. 

Checkings:
* Response code 200
* Presense of required fields in responses jsons. 


######getAllCategoriesTest()

This test checks request and response to /categories endpoint. 

Checkings:
* Response code 200
* Presense of required fields in responses jsons. 



######getAllServicesTest()

This test checks request and response to /services endpoint. 

Checkings:
* Response code 200
* Presense of required fields in responses jsons. 


######getAllStoresTest()

This test checks request and response to /stores endpoint. 

Checkings:
* Response code 200
* Presense of required fields in responses json. 

######addGetUpdateDeleteProductTest()

This test checks POST request for adding a new product, GET request for single product, PATCH request for update product and DELETE request for product.
GET, PATCH and DELETE requests are used with the recently created product by POST request.  

Checkings:
* Response code 201 for POST request.
* Presense of required fields in responses json. 
* Response code 200 for GET product request.
* Presense of required fields in responses json. 
* Fields of response to GET product request equal to fields from POST request.
* Response code 200 for PATCH product request
* Presense of required fields in responses json.
* "name" field of product has been successfully updated.
* Response code 200 for DELETE product request.
* Response code 404 for GET request for deleted product. 
* Field "message" in json equals to "NotFound" in response to GET request for deleted product.
   
######limitProductsTest()

This test checks request and response to /products endpoint when number of results is limited by 1 only. 

Checkings:
* Response code 200.
* Presense of required fields in responses json.
* Only one product is returned. 

######getProductsFromMostExpensiveDescTest()

This test checks request and response to /products endpoint when results are sorted by price descended. 

Checkings:
* Response code 200.
* Presense of required fields in responses json.
* Products in response are sorted by price from the highest.

###Summary 
Due to the lack of time not all possible tests have been implemented. To maximize coverage all endpoints are involved in testing with GET all items requests.
POST, PATCH, GET one item and DELETE requests are tested for Products. Also some tricks for GET requests are tested for Products. 
Other entities can be tested similarly.
There are plenty of possible tests for the rich functionality of Best Buy application. 

### Author
 **Alexander Ognev**  <avogneff@gmail.com>

  