Feature: Retrieve a list of artists and albums
  As a user I want to retrieve all available albums and artists and be able to view the content

  Scenario: Get all artists
    Given there are artists in the database
    When I send a GET request to "/api/artist/all"
    Then the response code should be 200
    And the response body should contain a list of artists
    And content-type should be "application/json"

  Scenario: No artists in the database
  Given there are no artists in the database
  When I send a GET request to "/api/artist/all"
  Then the response code should be 200
  And the response body should be empty