package org.example.tests;

import org.example.models.Person;
import org.example.models.Planet;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlanetsTests {

    @Test
    void VerifyTatooine() {
        var response = given()
                .baseUri("http://localhost")
                .port(3000)
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
        var response = given()
                .baseUri("http://localhost")
                .port(3000)
                .when()
                .get("/people/1");

        assertThat(response, notNullValue());
        var lukeSkywalker = response.as(Person.class);

        assertThat(lukeSkywalker.name, equalTo("Luke Skywalker"));
    }
}
