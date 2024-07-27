Feature: Retrieve artist with authentication
  As an authenticated user I want to attempt to retrieve an artist by its valid id so that I can view the artist's details.

  Background:
    Given artists exist in the DB
      | id | name               | description                                   |
      | 11 | Rick O'Kon         | Id est natus molestias sequi sint illum.      |
      | 12 | Donovan McCullough | Dignissimos libero accusantium sed aut vitae. |

    And the following albums exist in the DB for that artist
      | artist             | album                    | description                                                    |
      | Rick O'Kon         | I Sing the Body Electric | Dolorum laborum quod et laboriosam recusandae dolorum placeat. |
      | Rick O'Kon         | Tiger! Tiger!            | Et aspernatur est aut.                                         |
      | Donovan McCullough | Consider the Lilies      | Assumenda et quas.                                             |
      | Donovan McCullough | Cabbages and Kings       | Omnis blanditiis assumenda laboriosam praesentium quo.         |

  Scenario Outline: Retrieve an artist with valid id

    Given I am an authenticated "<user>" with "<role>"
    When I send GET request to "<endpoint>" using "<artistName>" id
    Then the response code should be <code>
    And content-type should be "application/json"
    And the artist name should be "<artistName>"
    And the artist description should be "<description>"
    And the artist should have "<numberOfAlbums>" albums
    And the first album name should be "<albumName>"
    And the first album description should be "<albumDescription>"

    Examples:

      | endpoint          | user          | role  | code | artistName         | description                                   | numberOfAlbums | albumName                | albumDescription                                               |
      | /api/artist/find/ | TestUser      | USER  | 200  | Rick O'Kon         | Id est natus molestias sequi sint illum.      | 2              | I Sing the Body Electric | Dolorum laborum quod et laboriosam recusandae dolorum placeat. |
      | /api/artist/find/ | AdminTestUser | ADMIN | 200  | Donovan McCullough | Dignissimos libero accusantium sed aut vitae. | 2              | Consider the Lilies      | Assumenda et quas.                                             |


  Scenario Outline: Retrieve an artist with invalid id
    Given I am an authenticated "<user>" with "<role>"
    When I send GET request to "<endpoint>" with invalid id
    Then the response code should be <code>

    Examples:

      | endpoint             | user          | role  | code |
      | /api/artist/find/105 | TestUser      | USER  | 404  |
      | /api/artist/find/106 | AdminTestUser | ADMIN | 404  |
