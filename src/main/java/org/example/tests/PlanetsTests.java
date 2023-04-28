package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.models.Person;
import org.example.models.Planet;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlanetsTests {
    final static String host = "http://localhost";
    final static int port = 3000;

    private final static List<Planet> createdPlanets = new ArrayList<>();
    private final static List<Person> createdPeople = new ArrayList<>();

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = host;
        RestAssured.port = port;
    }

    @AfterAll
    static void teardown() {
        for (var planet : createdPlanets) {
            var response = given()
                    .when()
                    .delete("planets/" + planet.id);

            assertThat(response.statusCode(), equalTo(200));
        }

        for (var person : createdPeople) {
            var response = given()
                    .when()
                    .delete("people/" + person.id);

            assertThat(response.statusCode(), equalTo(200));
        }
    }

    @Test
    void VerifyTatooine() {
        var response = given()
                .when()
                .get("/planets/1");

        assertThat(response, notNullValue());
        var tatooine = response.as(Planet.class);

        assertThat(tatooine.name, equalTo("Tatooine"));
        assertThat(tatooine.rotation_period, equalTo("23"));
        assertThat(tatooine.orbital_period, equalTo("304"));
        assertThat(tatooine.diameter, equalTo("10465"));
        assertThat(tatooine.climate, equalTo("arid"));
        assertThat(tatooine.gravity, equalTo("1 standard"));
        assertThat(tatooine.terrain, equalTo("desert"));
        assertThat(tatooine.surface_water, equalTo("1"));
        assertThat(tatooine.population, equalTo("200000"));
        assertThat(tatooine.residents.isEmpty(), equalTo(false));
        assertThat(tatooine.films.isEmpty(), equalTo(false));
    }

    @Test
    void VerifyLukeSkywalker() {
        var planetResponse = given()
                .when()
                .get("/planets/1");
        assertThat(planetResponse, notNullValue());
        var tatooine = planetResponse.as(Planet.class);

        var personResponse = given()
                .when()
                .get("/people/1");

        assertThat(personResponse, notNullValue());
        var lukeSkywalker = personResponse.as(Person.class);

        assertThat(tatooine.residents, hasItem(containsString(lukeSkywalker.id)));

        assertThat(lukeSkywalker.name, equalTo("Luke Skywalker"));
        assertThat(lukeSkywalker.height, equalTo("172"));
        assertThat(lukeSkywalker.mass, equalTo("77"));
        assertThat(lukeSkywalker.hair_color, equalTo("blond"));
        assertThat(lukeSkywalker.skin_color, equalTo("fair"));
        assertThat(lukeSkywalker.eye_color, equalTo("blue"));
        assertThat(lukeSkywalker.birth_year, equalTo("19BBY"));
        assertThat(lukeSkywalker.gender, equalTo("male"));
        assertThat(lukeSkywalker.homeworld, containsString(tatooine.id));
        assertThat(lukeSkywalker.films.isEmpty(), equalTo(false));
        assertThat(lukeSkywalker.species.isEmpty(), equalTo(true));
        assertThat(lukeSkywalker.vehicles.isEmpty(), equalTo(false));
        assertThat(lukeSkywalker.starships.isEmpty(), equalTo(false));
    }

    @Test
    void CreatePlanet() {
        var newId = getNumberOfPlanets() + 1;

        var body = new JSONObject()
                .put("id", newId)
                .put("name", "Planet_Tester_" + newId)
                .put("rotation_period", "10")
                .put("orbital_period", "83")
                .put("diameter", "10000")
                .put("climate", "arid")
                .put("gravity", "1 standard")
                .put("terrain", "desert")
                .put("surface_water", "1")
                .put("population", "300000")
                .put("residents", new ArrayList<String>())
                .put("films", new ArrayList<String>())
                .put("created", Instant.now().toString())
                .put("edited", Instant.now().toString())
                .put("url", host + "/:" + port + "planets/" + newId);

        var postNewPlanetResponse = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post("/planets/");

        assertThat(postNewPlanetResponse.statusCode(), equalTo(201));

        var getNewPlanetResponse = given()
                .when()
                .get("/planets/" + newId);

        assertThat(getNewPlanetResponse, notNullValue());
        var newPlanet = getNewPlanetResponse.as(Planet.class);
        createdPlanets.add(newPlanet);

        var bodyMap = body.toMap();
        assertThat(newPlanet.name, equalTo(bodyMap.get("name")));
        assertThat(newPlanet.rotation_period, equalTo(bodyMap.get("rotation_period")));
        assertThat(newPlanet.orbital_period, equalTo(bodyMap.get("orbital_period")));
        assertThat(newPlanet.diameter, equalTo(bodyMap.get("diameter")));
        assertThat(newPlanet.climate, equalTo(bodyMap.get("climate")));
        assertThat(newPlanet.gravity, equalTo(bodyMap.get("gravity")));
        assertThat(newPlanet.terrain, equalTo(bodyMap.get("terrain")));
        assertThat(newPlanet.surface_water, equalTo(bodyMap.get("surface_water")));
        assertThat(newPlanet.population, equalTo(bodyMap.get("population")));
        assertThat(newPlanet.residents.isEmpty(), equalTo(true));
        assertThat(newPlanet.films.isEmpty(), equalTo(true));
    }

    @Test
    void CreatePerson() {
        var newId = getNumberOfPeople() + 1;

        var body = new JSONObject()
                .put("id", newId)
                .put("name", "Bryce_Tester_" + newId)
                .put("height", "175")
                .put("mass", "83")
                .put("hair_color", "brown")
                .put("skin_color", "fair")
                .put("eye_color", "hazel")
                .put("birth_year", "19BBY")
                .put("gender", "male")
                .put("homeworld", "")
                .put("films", new ArrayList<String>())
                .put("species", new ArrayList<String>())
                .put("vehicles", new ArrayList<String>())
                .put("starships", new ArrayList<String>())
                .put("created", Instant.now().toString())
                .put("edited", Instant.now().toString())
                .put("url", host + "/:" + port + "people/" + newId);

        var newPersonResponse = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post("/people/");

        assertThat(newPersonResponse.statusCode(), equalTo(201));

        var personResponse = given()
                .when()
                .get("/people/" + newId);

        assertThat(personResponse, notNullValue());
        var tester = personResponse.as(Person.class);
        createdPeople.add(tester);

        var bodyMap = body.toMap();
        assertThat(tester.name, equalTo(bodyMap.get("name")));
        assertThat(tester.height, equalTo(bodyMap.get("height")));
        assertThat(tester.mass, equalTo(bodyMap.get("mass")));
        assertThat(tester.hair_color, equalTo(bodyMap.get("hair_color")));
        assertThat(tester.skin_color, equalTo(bodyMap.get("skin_color")));
        assertThat(tester.eye_color, equalTo(bodyMap.get("eye_color")));
        assertThat(tester.birth_year, equalTo(bodyMap.get("birth_year")));
        assertThat(tester.gender, equalTo(bodyMap.get("gender")));
        assertThat(tester.films.isEmpty(), equalTo(true));
        assertThat(tester.species.isEmpty(), equalTo(true));
        assertThat(tester.vehicles.isEmpty(), equalTo(true));
        assertThat(tester.starships.isEmpty(), equalTo(true));
    }

    private Integer getNumberOfPlanets() {
        var planetResponse = given()
                .when()
                .get("/planets");

        var ids = planetResponse.getBody().jsonPath().getList("id");
        return Integer.parseInt(ids.get(ids.size() - 1).toString());
    }

    private Integer getNumberOfPeople() {
        var peopleResponse = given()
                .when()
                .get("/people");

        var ids = peopleResponse.getBody().jsonPath().getList("id");
        return Integer.parseInt(ids.get(ids.size() - 1).toString());
    }
}
