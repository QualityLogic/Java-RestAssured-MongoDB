package org.example.tests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.jasminb.jsonapi.ResourceConverter;
import org.example.models.Planet;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlanetsTests {

    private ResourceConverter converter = new ResourceConverter(Planet.class);

    @Test
    void VerifyTatooine() {
        var response = given()
                .baseUri("http://localhost")
                .port(3000)
        .when()
                .get("/planets/1");

        var tatooine = response.as(Planet.class);

        assertThat(tatooine.name, equalTo("Tatooine"));
        assertThat(tatooine.climate, equalTo("arid"));
    }
}
