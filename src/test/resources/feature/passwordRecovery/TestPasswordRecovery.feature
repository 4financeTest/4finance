Feature: Password recovery

  Scenario: Recover password using email
    Given I am on the 'sportsdirect.com' page
    And Mail 'inbox' is empty for email '4financetest@gmail.com' with mail password '4Finance!java'
    When I go to SignIn page
    And I click Forgot Password link
    And I enter '4financetest@gmail.com' in e-mail field
    Then I receive an email for '4financetest@gmail.com' with password '4Finance!java' with link

    When I go to received URL in my 'inbox' folder
    And I enter new password
    Then I try to log in with a new password fot login: '4financetest@gmail.com'
    And I close the browser