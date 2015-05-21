package core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * Sportsdirect success password recovery page
 */
public class ChangePasswordEndPage {

    BaseFunctions baseFunctions;
    private static final By SUCCESS_TXT = By.id("dnn_ctr54473_PasswordReset_SuccessText");
    private static final By SIGNIN_BTN = By.id("dnn_LOGIN_loginLink");
    private static final Logger LOGGER = Logger.getLogger(ChangePasswordEndPage.class);

    public ChangePasswordEndPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Change password success page is loaded");
    }

    /**
     * Method checks if successful recovery text appears on page
     *
     * @return true or false
     */
    public boolean isPresentSuccessTextBlock() {
        return baseFunctions.isPresentElement(SUCCESS_TXT);
    }

    /**
     * Method clicks on Sign In button
     */
    public void clickSignInButton() {
        baseFunctions.click(SIGNIN_BTN);
        LOGGER.info("User clicked on Sign In button");
    }
}
