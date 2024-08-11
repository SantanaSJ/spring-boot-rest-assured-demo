Feature: Add artists
  As a user I want to attempt to add albums in the database

  Scenario Outline: Add an album with valid request body and non-existing album
    Given I am an authenticated "<user>" with "<role>"
    And CSRF token is valid and present
    And the album "<albumName>" by "<artistName>" does not exist in the database
    When I send a POST request to "<endpoint>" with a valid request body with "<artistName>", "<albumName>", "<albumDescription>"  and CSRF token
    Then the response code should be <code>
    And the location header should be present
    And the response message should be "Album added successfully!"

    Examples:
      | endpoint          | user         | role  | code | artistName         | albumName                | albumDescription                                               |
      | /api/album/add   | TestUser      | USER  | 201  | TestArtist1        | I Sing the Body Electric | Dolorum laborum quod et laboriosam recusandae dolorum placeat. |
      | /api/album/add   | AdminTestUser | ADMIN | 201  | TestArtist2        | Consider the Lilies      | Assumenda et quas.                                             |