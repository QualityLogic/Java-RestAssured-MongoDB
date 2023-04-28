package org.example.tests;

import io.restassured.http.ContentType;
import org.example.models.Person;
import org.example.models.Planet;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlanetsTests {
    final String host = "http://localhost";
    final int port = 3000;

    @Test
    void VerifyTatooine() {
        var response = given()
                .baseUri(host)
                .port(port)
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
                .baseUri(host)
                .port(port)
        .when()
                .get("/planets/1");
        assertThat(planetResponse, notNullValue());
        var tatooine = planetResponse.as(Planet.class);

        var personResponse = given()
                .baseUri(host)
                .port(port)
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
    void CreatePerson() {
        var peopleResponse = given()
                .baseUri(host)
                .port(port)
        .when()
                .get("/people");

        var ids = peopleResponse.getBody().jsonPath().getList("id");
        var lastId = ids.get(ids.size() - 1).toString();
        var newId = Integer.parseInt(lastId) + 1;
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
                .baseUri(host)
                .port(port)
                .contentType(ContentType.JSON)
                .body(body.toString())
        .when()
                .post("/people/");

        assertThat(newPersonResponse.statusCode(), equalTo(201));

        var personResponse = given()
                .baseUri(host)
                .port(port)
                .when()
                .get("/people/" + newId);

        assertThat(personResponse, notNullValue());
        var tester = personResponse.as(Person.class);

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
}
