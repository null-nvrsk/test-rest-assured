import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojos.UserRequest;
import pojos.CreateUserResponse;
import pojos.UserPojoFull;
import steps.UsersSteps;
import utils.RestWrapper;
import utils.UserGenerator;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RestTest {

    private static RestWrapper api;

    @BeforeAll
    public static void prepareClient(){
        api = RestWrapper.loginAs("eve.holt@reqres.in", "cityslicka");
    }

    @Test
    public void getUsers(){
        List<UserPojoFull> users = api.user.getUsers();
        assertThat(users).extracting(UserPojoFull::getEmail).contains("george.bluth@reqres.in");
    }

    @Test
    public void createUser(){
        UserRequest rq = UserGenerator.getSimpleUser();

        UsersSteps userApi = new UsersSteps();
        CreateUserResponse rs = api.user.createUser(rq);

        assertThat(rs)
                .isNotNull()
                .extracting(CreateUserResponse::getName)
                .isEqualTo(rq.getName());
    }

    @Test
    public void getOrders(){
        List<UserPojoFull> users = api.order.getOrders();
        assertThat(users).extracting(UserPojoFull::getEmail).contains("george.bluth@reqres.in");
    }
}
