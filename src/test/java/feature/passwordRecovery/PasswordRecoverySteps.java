package feature.passwordRecovery;

import core.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import javax.mail.MessagingException;
import java.io.IOException;

public class PasswordRecoverySteps {

    BaseFunctions baseFunctions = new BaseFunctions();
    HomePage homePage;
    SignInPage signInPage;
    PasswordRecoveryPage recoveryPage;
    MailHelper mailHelper;
    CreateNewPasswordPage createNewPasswordPage;
    private static final String NEW_PASSWORD = "BrandNew!Password" + RandomStringUtils.randomAlphanumeric(8);

    @Given("I am on the '(.+)' page")
    public void openHomePage(final String url) {
        baseFunctions.goToUrl(url);
        homePage = new HomePage(baseFunctions);
    }

    @Given("Mail '(.+)' is empty for email '(.+)' with mail password '(.+)'")
    public void clearMail(final String directory, final String email, final String password) {
        try {
            mailHelper = new MailHelper(email, password);
            mailHelper.clearAllMails(directory);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @When("I go to SignIn page")
    public void goToSignInPage() {
        signInPage = homePage.clickSignInButton();
    }

    @When("I click Forgot Password link")
    public void clickForgotPasswordLink() {
        recoveryPage = signInPage.clickForgotPasswordLink();
    }

    @When("I enter '(.+)' in e-mail field")
    public void enterEmail(final String email) {
        recoveryPage.fillEmailField(email);
        recoveryPage.clickSubmitBtn();
    }

    @Then("I receive an email for '(.+)' with password '(.+)' with link")
    public void getMailMessage(final String email, final String password) {
        try {
            MailHelper mailHelper = new MailHelper(email, password);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @When("I go to received URL in my '(.+)' folder")
    public void goToReceivedURL(final String folder) {
        try {
            baseFunctions.goToUrl(mailHelper.getRecoveryLinkFromMessage(folder));
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        createNewPasswordPage = new CreateNewPasswordPage(baseFunctions);
    }

    @When("I enter new password")
    public void enterNewPassword() {
        createNewPasswordPage.fillNewPassword(NEW_PASSWORD);
        ChangePasswordEndPage endPage = createNewPasswordPage.clickSubmitBtn();
        endPage.clickSignInButton();
    }

    @Then("I try to log in with a new password fot login: '(.+)'")
    public void loginPortal(final String email) {
        homePage = new HomePage(baseFunctions);
        homePage.clickSignInButton();
        signInPage = new SignInPage(baseFunctions);
        signInPage.fillLoginForm(email, NEW_PASSWORD);
        signInPage.clickSignUpBtn();
        homePage = new HomePage(baseFunctions);
        Assert.assertTrue("Not logged in", homePage.isPresentAccountBtn());
    }

    @Then("I close the browser")
    public void closeBrowser() {
        baseFunctions.stopDriver();
    }
}
