package core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * Sportsdirect create new password page
 */
public class CreateNewPasswordPage {

    BaseFunctions baseFunctions;
    private static final By NEW_PASSWORD_FIELD = By.id("dnn_ctr54473_PasswordReset_txtNewPassword");
    private static final By RETYPE_PASSWORD_FIELD = By.id("dnn_ctr54473_PasswordReset_txtConfirmNewPassword");
    private static final By SUBMIT_BTN = By.id("dnn_ctr54473_PasswordReset_lnkbtnChangePassword");
    private static final Logger LOGGER = Logger.getLogger(CreateNewPasswordPage.class);

    public CreateNewPasswordPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Create New Password page is opened");
    }

    /**
     * Method fills in firlds with a new password
     *
     * @param password new password to enter
     */
    public void fillNewPassword(String password) {
        baseFunctions.waitForElement(NEW_PASSWORD_FIELD, 500);
        baseFunctions.fillInput(NEW_PASSWORD_FIELD, password);
        LOGGER.info("User typed new password");
        baseFunctions.waitForElement(RETYPE_PASSWORD_FIELD, 500);
        baseFunctions.fillInput(RETYPE_PASSWORD_FIELD,password);
        LOGGER.info("User confirmed new password");
    }

    /**
     * Method submits form with new password
     *
     * @return password recovery last page
     */
    public ChangePasswordEndPage clickSubmitBtn() {
        baseFunctions.click(SUBMIT_BTN);
        LOGGER.info("User clicks submit button");
        return new ChangePasswordEndPage(baseFunctions);
    }
}