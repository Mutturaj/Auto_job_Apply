package customEntities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class allApi {

    @Test(priority = 3)
    public String getOtp() {
        String res = RestAssured.given().baseUri("http://sauron.nixy.stg-drove.phonepe.nb6/api/user/9343550736/getOTP").get().getBody().jsonPath().getString("reply.OTP");
        return res;

    }

    @Test(priority = 1)
    public String downGradeTo1fa() {
        Response res1 = RestAssured.given().baseUri("http://meta-server.stg.sb.az6/v1/utility").contentType(ContentType.JSON).body("").post("/downGradeToOneFa/8310920718");
        return res1.toString();
    }

    @Test(priority = 2)
    public String emailNotVerified() {

        Response res2 = RestAssured.given().baseUri("http://meta-server.stg.sb.az6/v1/utility").contentType(ContentType.JSON).body("").post("/markEmailNotVerified/8310920718");
        return res2.asString();
    }

    @Test(priority = 4)
    public void resetFlow() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .patch("http://useronboarding.stg.sb.az6/housekeeping/workflow/disable/user/8BUWC59FP2EG0WK80O19WT8ROD/SB_USER_ONBOARDING")
                .then()
                .statusCode(200);
}
}