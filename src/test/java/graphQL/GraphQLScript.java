package graphQL;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class GraphQLScript {

    public static void main(String[] args) {

        //Query
        int character = 2984;
        String response = given().log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"query\": \"query($characterId: Int!, $episodeId: Int!) {\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n    type\\n    id\\n  }\\n  location(locationId: 3603) {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    episode\\n  }\\n}\",\n" +
                        "  \"variables\": {\n" +
                        "    \"characterId\": " + character + ",\n" +
                        "    \"episodeId\": 2010\n" +
                        "  }\n" +
                        "}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String characterName = js.getString("data.character.name");
        Assert.assertEquals(characterName, "Robin");

        //Mutation

        String newCharacter = "Baskin Robin";
        String mutationResponse = given().log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"query\": \"mutation ($locationName: String!, $characterName: String!, $episodeName: String!){\\n  createLocation(location: {name: $locationName, type: \\\"Southzone\\\", dimension: \\\"234\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: $characterName, type: \\\"Macho\\\", status: \\\"dead\\\", species: \\\"fantasy\\\", gender: \\\"male\\\", image: \\\"png\\\", originId: 3599, locationId: 3599}) {\\n    id\\n  }\\n  createEpisode(episode: {air_date: \\\"31.05.2023\\\", name: $episodeName, episode: \\\"Bubu\\\"}) {\\n    id\\n  }\\n  deleteLocations(locationIds: [3603]) {\\n    locationsDeleted\\n  }\\n}\\n\",\n" +
                        "  \"variables\": {\n" +
                        "    \"locationName\": \"Newzealand\",\n" +
                        "    \"characterName\": \"" + newCharacter + "\",\n" +
                        "    \"episodeName\": \"Manifest\"\n" +
                        "  }\n" +
                        "}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println(mutationResponse);
    }
}
