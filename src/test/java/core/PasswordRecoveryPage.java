package core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * Sportsdirect password recovery page
 */
public class PasswordRecoveryPage {

    BaseFunctions baseFunctions;
    private static final By RECOVERY_EMAIL_FIELD = By.id("dnn_ctr54473_PasswordReset_UserName");
    private static final By SUBMIT_BTN = By.id("dnn_ctr54473_PasswordReset_cmdSendPassword");
    private static final Logger LOGGER = Logger.getLogger(PasswordRecoveryPage.class);

    public PasswordRecoveryPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Password recovery page is opened");
    }

    /**
     * Method fils text input with eMail account to restore
     *
     * @param email email address
     */
    public void fillEmailField(String email) {
        baseFunctions.waitForElement(RECOVERY_EMAIL_FIELD, 500);
        baseFunctions.fillInput(RECOVERY_EMAIL_FIELD, email);
        LOGGER.info("User types in the E-mail field");
    }

    /**
     * Method click on submit button
     */
    public void clickSubmitBtn() {
        baseFunctions.click(SUBMIT_BTN);
        baseFunctions.pause(1000);
        LOGGER.info("User clicks on submit button");
    }
}