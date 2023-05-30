package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.models.*;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlanetsTests {
    final static String protocol = "http";
    final static String host = "localhost";
    final static int port = 3000;

    private static List<Planet> createdPlanets = new ArrayList<>();
    private static List<Person> createdPeople = new ArrayList<>();
    private static List<Film> createdFilms = new ArrayList<>();
    private static List<Species> createdSpecies = new ArrayList<>();
    private static List<Vehicle> createdVehicles = new ArrayList<>();

    // Hooks and Utilities

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = protocol + "://" + host;
        RestAssured.port = port;
    }

    @AfterEach
    void teardown() {
        // TODO: Remove DELETE calls directly to the API
        if (!createdPeople.isEmpty())
            deleteNewPeople();

        if (!createdPlanets.isEmpty())
            deleteNewPlanets();

        if (!createdFilms.isEmpty())
            deleteNewFilms();

        if (!createdSpecies.isEmpty())
            deleteNewSpecies();

        if (!createdVehicles.isEmpty())
            deleteNewVehicles();
    }

    private static void deleteNewPlanets() {
        for (var planet : createdPlanets) {
            var response = given()
                    .when()
                    .delete("planets/" + planet.id);

            assertThat(response.statusCode(), equalTo(200));
        }

        createdPlanets = new ArrayList<>();
    }

    private static void deleteNewPeople() {
        for (var person : createdPeople) {
            var response = given()
                    .when()
                    .delete("people/" + person.id);

            assertThat(response.statusCode(), equalTo(200));
        }

        createdPeople = new ArrayList<>();
    }

    private static void deleteNewFilms() {
        for (var film : createdFilms) {
            var response = given()
                    .when()
                    .delete("films/" + film.id);

            assertThat(response.statusCode(), equalTo(200));
        }

        createdFilms = new ArrayList<>();
    }

    private static void deleteNewSpecies() {
        for (var species : createdSpecies) {
            var response = given()
                    .when()
                    .delete("species/" + species.id);

            assertThat(response.statusCode(), equalTo(200));
        }

        createdSpecies = new ArrayList<>();
    }

    private static void deleteNewVehicles() {
        for (var vehicle : createdVehicles) {
            var response = given()
                    .when()
                    .delete("vehicles/" + vehicle.id);

            assertThat(response.statusCode(), equalTo(200));
        }

        createdVehicles = new ArrayList<>();
    }

    private Integer getNumberOfPlanets() {
        return getNumberOfItemsFromResponse(given().when().get("/planets"));
    }

    private Integer getNumberOfPeople() {
        return getNumberOfItemsFromResponse(given().when().get("/people"));
    }

    private Integer getNumberOfSpecies() {
        return getNumberOfItemsFromResponse(given().when().get("/species"));
    }

    private Integer getNumberOfFilms() {
        return getNumberOfItemsFromResponse(given().when().get("/films"));

    }

    private Integer getNumberOfVehicles() {
        return getNumberOfItemsFromResponse(given().when().get("/vehicles"));
    }

    private Integer getNumberOfItemsFromResponse(Response response) {
        var ids = response.getBody().jsonPath().getList("id");
        return Integer.parseInt(ids.get(ids.size() - 1).toString());
    }

    private Boolean isGETAllResponseSuccessful(Response response) {
        try {
            assertThat(response.statusCode(), equalTo(200));
            var body = response.getBody().jsonPath().getList("id");
            assertThat(body.isEmpty(), equalTo(false));
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return  false;
        }

        return true;
    }

    // Tests

    @Test
    void GetPeople() {
        var response = given()
                .when()
                .get("/people/");

        assertThat(isGETAllResponseSuccessful(response), equalTo(true));
    }

    @Test
    void GetFilms() {
        var response = given()
                .when()
                .get("/films/");

        assertThat(isGETAllResponseSuccessful(response), equalTo(true));
    }

    @Test
    void GetPlanets() {
        var response = given()
                .when()
                .get("/planets/");

        assertThat(isGETAllResponseSuccessful(response), equalTo(true));
    }

    @Test
    void GetSpecies() {
        var response = given()
                .when()
                .get("/species/");

        assertThat(isGETAllResponseSuccessful(response), equalTo(true));
    }

    @Test
    void GetVehicles() {
        var response = given()
                .when()
                .get("/vehicles/");

        assertThat(isGETAllResponseSuccessful(response), equalTo(true));
    }

    @Test
    void GetStarships() {
        var response = given()
                .when()
                .get("/starships/");

        assertThat(isGETAllResponseSuccessful(response), equalTo(true));
    }

    @Test
    void VerifyHuman() {
        var response = given()
                .when()
                .get("/species/1");

        assertThat(response, notNullValue());

        var human = response.as(Species.class);

        assertThat(human.name, equalTo("Human"));
        assertThat(human.classification, equalTo("mammal"));
        assertThat(human.designation, equalTo("sentient"));
        assertThat(human.average_height, equalTo("180"));
        assertThat(human.skin_colors, equalTo("caucasian, black, asian, hispanic"));
        assertThat(human.hair_colors, equalTo("blonde, brown, black, red"));
        assertThat(human.eye_colors, equalTo("brown, blue, green, hazel, grey, amber"));
        assertThat(human.average_lifespan, equalTo("120"));
        assertThat(human.language, equalTo("Galactic Basic"));
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
    void VerifyAtAt() {
        var vehicleResponse = given()
                .when()
                .get("/vehicles/7");

        assertThat(vehicleResponse, notNullValue());
        var atAt = vehicleResponse.as(Vehicle.class);

        assertThat(atAt.name, equalTo("AT-AT"));
        assertThat(atAt.model, equalTo("All Terrain Armored Transport"));
        assertThat(atAt.manufacturer, equalTo("Kuat Drive Yards, Imperial Department of Military Research"));
        assertThat(atAt.cost_in_credits, equalTo("unknown"));
        assertThat(atAt.length, equalTo("20"));
        assertThat(atAt.max_atmosphering_speed, equalTo("60"));
        assertThat(atAt.crew, equalTo("5"));
        assertThat(atAt.passengers, equalTo("40"));
        assertThat(atAt.cargo_capacity, equalTo("1000"));
        assertThat(atAt.consumables, equalTo("unknown"));
        assertThat(atAt.vehicle_class, equalTo("assault walker"));
        assertThat(atAt.pilots.isEmpty(), equalTo(true));
        assertThat(atAt.films.isEmpty(), equalTo(false));
    }

    @Test
    void VerifyPlanetCreation() {
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
                .put("edited", Instant.now().toString());

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
    void VerifyPersonCreation() {
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
                .put("edited", Instant.now().toString());

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

    @Test
    void VerifyFilmCreation() {
        var newId = getNumberOfFilms() + 1;

        var body = new JSONObject()
                .put("id", newId)
                .put("title", "Tester_Film_" + newId)
                .put("episode_id", newId)
                .put("opening_crawl", "In a galaxy far away or perhaps not...")
                .put("director", "Quality Logic")
                .put("producer", "BC")
                .put("release_date", Instant.now())
                .put("characters", new ArrayList<>())
                .put("planets", new ArrayList<>())
                .put("starships", new ArrayList<>())
                .put("vehicles", new ArrayList<>())
                .put("species", new ArrayList<>())
                .put("created", Instant.now())
                .put("edited", Instant.now());

        var newFilmResponse = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post("/films");

        assertThat(newFilmResponse.statusCode(), equalTo(201));

        var filmResponse = given()
                .when()
                .get("/films/" + newId);

        assertThat(filmResponse, notNullValue());
        var film = filmResponse.as(Film.class);
        createdFilms.add(film);

        var bodyMap = body.toMap();
        assertThat(film.title, equalTo(bodyMap.get("title")));
        assertThat(film.episode_id, equalTo(bodyMap.get("episode_id").toString()));
        assertThat(film.opening_crawl, equalTo(bodyMap.get("opening_crawl")));
        assertThat(film.director, equalTo(bodyMap.get("director")));
        assertThat(film.producer, equalTo(bodyMap.get("producer")));
        assertThat(film.release_date, equalTo(bodyMap.get("release_date").toString()));
        assertThat(film.characters.isEmpty(), equalTo(true));
        assertThat(film.planets.isEmpty(), equalTo(true));
        assertThat(film.vehicles.isEmpty(), equalTo(true));
        assertThat(film.starships.isEmpty(), equalTo(true));
        assertThat(film.species.isEmpty(), equalTo(true));
    }

    @Test
    void VerifySpeciesCreation() {
        var newId = getNumberOfSpecies() + 1;

        var body = new JSONObject()
                .put("id", newId)
                .put("name", "Tester_Species_" + newId)
                .put("classification", "mammel")
                .put("designation", "sentient")
                .put("average_height", "170")
                .put("skin_colors", "green, gray")
                .put("hair_colors", "black")
                .put("eye_colors", "black")
                .put("average_lifespan", "200")
                .put("homeworld", "")
                .put("language", "Graylien")
                .put("people", new ArrayList<>())
                .put("films", new ArrayList<>())
                .put("created", Instant.now())
                .put("edited", Instant.now());

        var newSpeciesResponse = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post("/species");

        assertThat(newSpeciesResponse.statusCode(), equalTo(201));

        var speciesResponse = given()
                .when()
                .get("/species/" + newId);

        assertThat(speciesResponse, notNullValue());
        var species = speciesResponse.as(Species.class);
        createdSpecies.add(species);

        var bodyMap = body.toMap();
        assertThat(species.name, equalTo(bodyMap.get("name")));
        assertThat(species.classification, equalTo(bodyMap.get("classification")));
        assertThat(species.designation, equalTo(bodyMap.get("designation")));
        assertThat(species.average_height, equalTo(bodyMap.get("average_height")));
        assertThat(species.skin_colors, equalTo(bodyMap.get("skin_colors")));
        assertThat(species.hair_colors, equalTo(bodyMap.get("hair_colors")));
        assertThat(species.eye_colors, equalTo(bodyMap.get("eye_colors")));
        assertThat(species.average_lifespan, equalTo(bodyMap.get("average_lifespan")));
        assertThat(species.language, equalTo(bodyMap.get("language")));
        assertThat(species.people.isEmpty(), equalTo(true));
        assertThat(species.films.isEmpty(), equalTo(true));
    }

    @Test
    void VerifyVehicleCreation() {
        var newId = getNumberOfVehicles() + 1;

        var body = new JSONObject()
                .put("id", newId)
                .put("name", "Vehicle_Tester_" + newId)
                .put("model", "Testing Assault Vehicle")
                .put("manufacturer", "Quality Logic")
                .put("cost_in_credits", "10000")
                .put("length", "20")
                .put("max_atmosphering_speed", "60")
                .put("crew", "5")
                .put("passengers", "5")
                .put("cargo_capacity", "1000")
                .put("consumables", "unknown")
                .put("vehicle_class", "space fighter")
                .put("pilots", new ArrayList<String>())
                .put("films", new ArrayList<String>())
                .put("created", Instant.now().toString())
                .put("edited", Instant.now().toString());

        var postNewVehicleResponse = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post("/vehicles/");

        assertThat(postNewVehicleResponse.statusCode(), equalTo(201));

        var getNewVehicleResponse = given()
                .when()
                .get("/vehicles/" + newId);

        assertThat(getNewVehicleResponse, notNullValue());
        var newVehicle = getNewVehicleResponse.as(Vehicle.class);
        createdVehicles.add(newVehicle);

        var bodyMap = body.toMap();
        assertThat(newVehicle.name, equalTo(bodyMap.get("name")));
        assertThat(newVehicle.model, equalTo(bodyMap.get("model")));
        assertThat(newVehicle.manufacturer, equalTo(bodyMap.get("manufacturer")));
        assertThat(newVehicle.cost_in_credits, equalTo(bodyMap.get("cost_in_credits")));
        assertThat(newVehicle.length, equalTo(bodyMap.get("length")));
        assertThat(newVehicle.max_atmosphering_speed, equalTo(bodyMap.get("max_atmosphering_speed")));
        assertThat(newVehicle.crew, equalTo(bodyMap.get("crew")));
        assertThat(newVehicle.passengers, equalTo(bodyMap.get("passengers")));
        assertThat(newVehicle.cargo_capacity, equalTo(bodyMap.get("cargo_capacity")));
        assertThat(newVehicle.consumables, equalTo(bodyMap.get("consumables")));
        assertThat(newVehicle.vehicle_class, equalTo(bodyMap.get("vehicle_class")));
        assertThat(newVehicle.pilots.isEmpty(), equalTo(true));
        assertThat(newVehicle.films.isEmpty(), equalTo(true));
    }

    @Test
    void VerifyANewHope() {
        var response = given()
                .when()
                .get("/films/1");

        var aNewHope = response.as(Film.class);

        var openingCrawl = "It is a period of civil war.\r\nRebel spaceships, striking\r\nfrom a hidden base, have won\r\ntheir first victory against\r\nthe evil Galactic Empire.\r\n\r\nDuring the battle, Rebel\r\nspies managed to steal secret\r\nplans to the Empire's\r\nultimate weapon, the DEATH\r\nSTAR, an armored space\r\nstation with enough power\r\nto destroy an entire planet.\r\n\r\nPursued by the Empire's\r\nsinister agents, Princess\r\nLeia races home aboard her\r\nstarship, custodian of the\r\nstolen plans that can save her\r\npeople and restore\r\nfreedom to the galaxy....";

        assertThat(aNewHope.title, equalTo("A New Hope"));
        assertThat(aNewHope.episode_id, equalTo("4"));
        assertThat(aNewHope.opening_crawl, equalTo(openingCrawl));
        assertThat(aNewHope.director, equalTo("George Lucas"));
        assertThat(aNewHope.producer, equalTo("Gary Kurtz, Rick McCallum"));
        assertThat(aNewHope.characters.isEmpty(), equalTo(false));
        assertThat(aNewHope.planets.isEmpty(), equalTo(false));
        assertThat(aNewHope.starships.isEmpty(), equalTo(false));
        assertThat(aNewHope.vehicles.isEmpty(), equalTo(false));
        assertThat(aNewHope.species.isEmpty(), equalTo(false));
    }

    @Test
    void VerifyPatchedPerson() {
        VerifyPersonCreation();

        var person = createdPeople.get(0);
        var body = new JSONObject()
                .put("id", person.id)
                .put("name", "Bryce_Tester_Patch" + person.id)
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
                .put("edited", Instant.now().toString());

        var request = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .patch("/people/" + person.id);

        assertThat(request.statusCode(), equalTo(200));

        var patchedPerson = request.as(Person.class);
        assertThat(patchedPerson.name, containsString("Patch"));

        var getRequest = given()
                .when()
                .get("/people/" + patchedPerson.id);

        var getPerson = getRequest.as(Person.class);
        assertThat(getPerson.name, containsString("Patch"));
    }

    @Test
    void VerifyPatchedPlanet() {
        VerifyPlanetCreation();

        var planet = createdPlanets.get(0);
        var body = new JSONObject()
                .put("id", planet.id)
                .put("name", "Planet_Tester_Patch" + planet.id)
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
                .put("edited", Instant.now().toString());

        var request = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .patch("/planets/" + planet.id);

        assertThat(request.statusCode(), equalTo(200));

        var patchedPlanet = request.as(Planet.class);
        assertThat(patchedPlanet.name, containsString("Patch"));

        var getRequest = given()
                .when()
                .get("/planets/" + patchedPlanet.id);

        var getPlanet = getRequest.as(Planet.class);
        assertThat(getPlanet.name, containsString("Patch"));
    }

    @Test
    void VerifyPatchedSpecies() {
        VerifySpeciesCreation();

        var species = createdSpecies.get(0);

        var body = new JSONObject()
                .put("id", species.id)
                .put("name", "Tester_Species_Patch_" + species.id)
                .put("classification", "mammel")
                .put("designation", "sentient")
                .put("average_height", "170")
                .put("skin_colors", "green, gray")
                .put("hair_colors", "black")
                .put("eye_colors", "black")
                .put("average_lifespan", "200")
                .put("homeworld", "")
                .put("language", "Graylien")
                .put("people", new ArrayList<>())
                .put("films", new ArrayList<>())
                .put("created", Instant.now())
                .put("edited", Instant.now());

        var request = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .patch("/species/" + species.id);

        assertThat(request.statusCode(), equalTo(200));

        var patchedSpecies = request.as(Species.class);
        assertThat(patchedSpecies.name, containsString("Patch"));

        var getRequest = given()
                .when()
                .get("/species/" + patchedSpecies.id);

        var getSpecies = getRequest.as(Species.class);
        assertThat(getSpecies.name, containsString("Patch"));
    }

    @Test
    void VerifyPatchedVehicle() {
        VerifyVehicleCreation();

        var vehicle = createdVehicles.get(0);

        var body = new JSONObject()
                .put("id", vehicle.id)
                .put("name", "Vehicle_Tester_Patch" + vehicle.id)
                .put("model", "Testing Assault Vehicle")
                .put("manufacturer", "Quality Logic")
                .put("cost_in_credits", "10000")
                .put("length", "20")
                .put("max_atmosphering_speed", "60")
                .put("crew", "5")
                .put("passengers", "5")
                .put("cargo_capacity", "1000")
                .put("consumables", "unknown")
                .put("vehicle_class", "space fighter")
                .put("pilots", new ArrayList<String>())
                .put("films", new ArrayList<String>())
                .put("created", Instant.now().toString())
                .put("edited", Instant.now().toString());

        var request = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .patch("/vehicles/" + vehicle.id);

        assertThat(request.statusCode(), equalTo(200));

        var patchedVehicle = request.as(Vehicle.class);
        assertThat(patchedVehicle.name, containsString("Patch"));

        var getRequest = given()
                .when()
                .get("/vehicles/" + patchedVehicle.id);

        var getVehicle = getRequest.as(Vehicle.class);
        assertThat(getVehicle.name, containsString("Patch"));
    }

    @Test
    void VerifyPlanetNotFound() {
        var response = given()
                .when()
                .get("/planets/-1");

        assertThat(response.statusCode(), equalTo(404));
    }

    @Test
    void VerifyPersonNotFound() {
        var response = given()
                .when()
                .get("/people/-1");

        assertThat(response.statusCode(), equalTo(404));
    }

    @Test
    void VerifyFilmNotFound() {
        var response = given()
                .when()
                .get("/films/-1");

        assertThat(response.statusCode(), equalTo(404));
    }

    @Test
    void VerifySpeciesNotFound() {
        var response = given()
                .when()
                .get("/species/-1");

        assertThat(response.statusCode(), equalTo(404));
    }

    @Test
    void VerifyVehicleNotFound() {
        var response = given()
                .when()
                .get("/vehicles/-1");

        assertThat(response.statusCode(), equalTo(404));
    }

    @Test
    void VerifyCrossLinkedEndpointData() {
        var luke = given()
                .when()
                .get("/people/1")
                .as(Person.class);

        var tatooine = given()
                .when()
                .get("/planets/1")
                .as(Planet.class);

        var resident = given()
                .when()
                .get(tatooine.residents.get(0))
                .as(Person.class);

        assertThat(luke.id, equalTo(resident.id));
        assertThat(luke.name, equalTo(resident.name));
        assertThat(luke.height, equalTo(resident.height));
        assertThat(luke.mass, equalTo(resident.mass));
        assertThat(luke.hair_color, equalTo(resident.hair_color));
        assertThat(luke.skin_color, equalTo(resident.skin_color));
        assertThat(luke.eye_color, equalTo(resident.eye_color));
        assertThat(luke.birth_year, equalTo(resident.birth_year));
        assertThat(luke.gender, equalTo(resident.gender));
        assertThat(luke.homeworld, equalTo(resident.homeworld));
        assertThat(luke.films, equalTo(resident.films));
        assertThat(luke.species, equalTo(resident.species));
        assertThat(luke.vehicles, equalTo(resident.vehicles));
        assertThat(luke.starships, equalTo(resident.starships));

        var homeworld = given()
                .when()
                .get(resident.homeworld)
                .as(Planet.class);

        assertThat(homeworld.id, equalTo(tatooine.id));
        assertThat(homeworld.name, equalTo(tatooine.name));
        assertThat(homeworld.rotation_period, equalTo(tatooine.rotation_period));
        assertThat(homeworld.orbital_period, equalTo(tatooine.orbital_period));
        assertThat(homeworld.diameter, equalTo(tatooine.diameter));
        assertThat(homeworld.climate, equalTo(tatooine.climate));
        assertThat(homeworld.gravity, equalTo(tatooine.gravity));
        assertThat(homeworld.terrain, equalTo(tatooine.terrain));
        assertThat(homeworld.surface_water, equalTo(tatooine.surface_water));
        assertThat(homeworld.population, equalTo(tatooine.population));
        assertThat(homeworld.residents, equalTo(tatooine.residents));
        assertThat(homeworld.films, equalTo(tatooine.films));
    }
}
