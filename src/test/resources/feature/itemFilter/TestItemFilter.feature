Feature: Catalog item filter test

Scenario: Catalog item filtration
  Given I am on the 'sportsdirect.com' page
  When I select 'Mens' category from menu
  And I select 'Shoes' subcategory
  And I select 'EUR' as a currency
  And I select 'Karrimor' brand
  And I select price range from '30' to '60'
  Then Sales items appears in a price range from '30' to '60'
  And I close the browser