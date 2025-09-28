package steps;

import io.cucumber.java.Before;
import org.example.page.LoginPage;
import org.example.util.TestContext;

public class Hooks {

    private final LoginPage loginPage;
    private final TestContext context;

    public Hooks(TestContext context, LoginPage loginPage) {
        this.context = context;
        this.loginPage = loginPage;
    }

    @Before
    public void clearLoginFields() {
        loginPage.clearUserName().clearPassword();
    }

}
