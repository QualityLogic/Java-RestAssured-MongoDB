# Java-Rest-Assured
A demonstration of API testing using the Java API automation framework [Rest Assured](https://rest-assured.io/). This testing framework allows for much flexibility, allowing one to introduce abstraction and complexity as needed.

## Server
To not depend on a third-party resource to serve the API endpoints, this repository utilizes [Json-Server](https://github.com/typicode/json-server). The endpoints resemble the ones found in [Swapi](https://swapi.dev/), a Stars Wars based API test service.

To run the server, follow these instructions:

1. Ensure you have the correct version of Node installed (check the `.node-version` file). You can use `nvm`, `asdf`, or whatever Node version management tool you like.
2. Change to the `server` directory:
```
cd server
```
3. Install the dependencies:
```
npm install
```
4. Run the server:
```
npm run serve
```

## Testing
The Java portion of this repository uses **Maven** as its dependency manager. The required packages: **Rest Assured** and **JUnit** can be managed through the `pom.xml` file or any given Maven management system, such as the one in Intellij Idea.

**Rest Assured** uses the standard Gherkin language methods, i.e., the _Given_, _When_, and _Then_ statements.

For example:
```
given()
    .baseUri("http://localhost")
    .port(3000)
.when()
    .get("/planets/1");
.then()
    .body("planet.name", equalTo("Tatooine")
// ... 
```
The example above can be read as: _Given_ we have a base URI representing our localhost, on port 3000, _When_ we make a GET request for the data of a specific planet, _Then_ we expect the planet's name to equal "Tatooine".

The _Then_ portion of a given test should contain nearly all assertions. Rest Assured provides a `body()` method, which allows one to access a given property of the response body and assert against it. However, one can also define the expected response body as a **class**. This allows you to cast the response body to a class instance with defined properties for that response.

An example of a response model class:
```
public class Planet {

    public String id;
    public String name;
    public String rotation_period;
    public String orbital_period;
    public String diameter;
// ...
```

If we take the above _Given_, _When_, and _Then_ statements and combine it with a class instance, we get the following:
```
var response = given()
    .baseUri("http://localhost")
    .port(3000)
    .when()
        .get("/planets/1");

    var tatooine = response.as(Planet.class);

    assertThat(tatooine.name, equalTo("Tatooine"));
```

Where we can use JUnit to assert against the properties of the class instance.

The above test would be wrapped in a [JUnit](https://junit.org/junit5/) `@Test` annotated method. 
