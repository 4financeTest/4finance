package tests;

import core.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.mail.*;
import java.io.IOException;

/**
 * This test restores password via E-mail using gmail and checks if password is restored
 */
public class TestAccountRestore{

    private static final String LOGIN = "4financetest@gmail.com";
    private static final String PASSWORD = "4Finance!java";
    private static final String WORKIN_DIRECTORY = "inbox";
    private static final String WEB_SITE_URL = "sportsdirect.com";
    private static final String NEW_PASSWORD = "BrandNew!Password" + RandomStringUtils.randomAlphanumeric(8);
    private BaseFunctions baseFunctions = new BaseFunctions();
    private static final Logger LOGGER = Logger.getLogger(TestAccountRestore.class);

    /**
     * method creates mail session and cleans working directory
     *
     * @throws MessagingException
     */
    @Before
    public void startDriver() throws MessagingException {
        LOGGER.info("Connecting to mail server as " + LOGIN);
        MailHelper mailHelper = new MailHelper(LOGIN, PASSWORD);

        LOGGER.info("Clean up " + WORKIN_DIRECTORY + " directory");
        mailHelper.clearAllMails(WORKIN_DIRECTORY);
    }

    @Test
    public void test() throws MessagingException, IOException {
        LOGGER.info("This test restores password via E-mail using gmail and checks if password is restored");
        baseFunctions.goToUrl(WEB_SITE_URL);
        HomePage homePage = new HomePage(baseFunctions);

        LOGGER.info("User goes to SignIn page");
        SignInPage signInPage = homePage.clickSignInButton();

        LOGGER.info("User clocks to Forgot Password link");
        PasswordRecoveryPage recoveryPage = signInPage.clickForgotPasswordLink();

        LOGGER.info("Filling E-mail fields with: " + LOGIN + " and submitting form");
        recoveryPage.fillEmailField(LOGIN);
        recoveryPage.clickSubmitBtn();

        LOGGER.info("Checking mail for recovery link");
        MailHelper mailHelper = new MailHelper(LOGIN, PASSWORD);

        LOGGER.info("User goes to received URL");
        baseFunctions.goToUrl(mailHelper.getRecoveryLinkFromMessage(WORKIN_DIRECTORY));
        CreateNewPasswordPage createNewPasswordPage = new CreateNewPasswordPage(baseFunctions);

        LOGGER.info("Filling fields with a new Password " + NEW_PASSWORD + " and submitting the form");
        createNewPasswordPage.fillNewPassword(NEW_PASSWORD);
        ChangePasswordEndPage endPage = createNewPasswordPage.clickSubmitBtn();

        Assert.assertTrue("No Success text block", endPage.isPresentSuccessTextBlock());
        LOGGER.info("Password changed successfully, logging off");
        endPage.clickSignInButton();

        LOGGER.info("User try to Sugn In with a new password");
        homePage = login(LOGIN, NEW_PASSWORD);

        Assert.assertTrue("Not logged in", homePage.isPresentAccountBtn());
        LOGGER.info("Log In with a new password success");
    }

    /**
     * Stopping webDriver
     */
    @After
    public void closeDriver() {
        baseFunctions.stopDriver();
    }

    /**
     * This method signing in user to sportsdirect.com
     *
     * @param login - user login
     * @param password - user password
     * @return - home page
     */
    private HomePage login(String login, String password) {
        baseFunctions.goToUrl(WEB_SITE_URL);
        HomePage homePage = new HomePage(baseFunctions);
        homePage.clickSignInButton();
        SignInPage signInPage = new SignInPage(baseFunctions);
        signInPage.fillLoginForm(login, password);
        signInPage.clickSignUpBtn();
        return new HomePage(baseFunctions);
    }
}