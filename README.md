# Reactive REST API Using Spring Boot and RxJava

### Things todo list

1. Clean this repository: `git clone https://github.com/hendisantika/springboot-reactive-web-api-rxjava.git`
2. Navigate to the folder: `cd springboot-reactive-web-api-rxjava`
3. Run the application: `mvn clean spring-boot:run`
4. Open Swagger UI: http://localhost:8081/swagger-ui/index.html

### Reactive REST API

The reactive REST API to be built is just a simple CRUD about authors and books. Here are the endpoints :

* [POST] /api/authors → add an author
* [POST] /api/books → add a book
* [PUT] /api/books/{bookId} → update a book
* [GET] /api/books?limit={limit}&page={page} → get list of books
* [GET] /api/book/{bookId} → get a book’s detail
* [DELETE] /api/book/{bookId} → delete a book

