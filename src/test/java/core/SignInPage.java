package core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * Sportsdirect SignIn page
 */
public class SignInPage {

    BaseFunctions baseFunctions;
    private static final By FORGOT_PASSWORD_LINK = By.id("dnn_ctr12859_Login_Login_AuthenticationProvider_registerLogin_cmdForgottenPassword");
    private static final By LOGIN_FIELD = By.id("dnn_ctr12859_Login_Login_AuthenticationProvider_registerLogin_txtExistingCustomerEmailAddress");
    private static final By PASSWORD_FIELD = By.id("dnn_ctr12859_Login_Login_AuthenticationProvider_registerLogin_txtPassword");
    private static final By SIGN_IN_BTN = By.id("dnn_ctr12859_Login_Login_AuthenticationProvider_registerLogin_btnRegisteredCustomer");
    private static final Logger LOGGER = Logger.getLogger(SignInPage.class);

    public SignInPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Sign In page opened");
    }

    /**
     * Method clicks on Forgot Password link
     *
     * @return password recovery page
     */
    public PasswordRecoveryPage clickForgotPasswordLink() {
        baseFunctions.click(FORGOT_PASSWORD_LINK);
        LOGGER.info("User click on Forgot Password link");
        return new PasswordRecoveryPage(baseFunctions);
    }

    /**
     * Method to fill login form
     *
     * @param login user login
     * @param password user password
     */
    public void fillLoginForm(String login, String password) {
        baseFunctions.waitForElement(LOGIN_FIELD, 500);
        baseFunctions.fillInput(LOGIN_FIELD, login);
        LOGGER.info("User is filling login field");
        baseFunctions.waitForElement(PASSWORD_FIELD, 500);
        baseFunctions.fillInput(PASSWORD_FIELD, password);
        LOGGER.info("User is filling password field");
    }

    /**
     * Method submits login form
     */
    public void clickSignUpBtn() {
        baseFunctions.click(SIGN_IN_BTN);
        LOGGER.info("User clicks on submit button");
    }
}