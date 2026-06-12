package api;

import api.dto.AuthResponseDto;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.TestData;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Authentication API")
public class AuthApiTest extends BaseApiTest {

    private static final String AUTH_ENDPOINT =
            "/basic-auth/" + TestData.USERNAME + "/" + TestData.PASSWORD;

    private String validAuthHeader;
    private String invalidAuthHeader;

    @BeforeMethod
    public void prepareCredentials() {
        validAuthHeader   = "Basic " + encode(TestData.USERNAME, TestData.PASSWORD);
        invalidAuthHeader = "Basic " + encode("wrong_user", "wrong_pass");
    }

    @Test
    @Story("Valid credentials")
    @Description("GET /basic-auth with valid Basic Auth credentials should return 200 and authenticated=true")
    public void shouldReturn200WhenCredentialsAreValid() {
        AuthResponseDto body = given()
                .spec(requestSpec)
                .log().ifValidationFails()
                .header("Authorization", validAuthHeader)
                .when()
                .get(AUTH_ENDPOINT)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().as(AuthResponseDto.class);

        assertThat(body.isAuthenticated()).isTrue();
        assertThat(body.getUser()).isEqualTo(TestData.USERNAME);
    }

    @Test
    @Story("Invalid credentials")
    @Description("GET /basic-auth with wrong credentials should return 401 Unauthorized")
    public void shouldReturn401WhenCredentialsAreInvalid() {
        given()
                .spec(requestSpec)
                .log().ifValidationFails()
                .header("Authorization", invalidAuthHeader)
                .when()
                .get(AUTH_ENDPOINT)
                .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    @Story("Missing credentials")
    @Description("GET /basic-auth without Authorization header should return 401 Unauthorized")
    public void shouldReturn401WhenNoAuthorizationHeaderProvided() {
        given()
                .spec(requestSpec)
                .log().ifValidationFails()
                .when()
                .get(AUTH_ENDPOINT)
                .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    private static String encode(String username, String password) {
        return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }
}
