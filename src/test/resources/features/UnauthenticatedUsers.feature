Feature: Retrieve artist without authentication
  As an unauthenticated user I wan to attempt to retrieve an artist and I should be shown error message

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

  Scenario Outline: Retrieve an artist with valid id and no authentication
    When I as an unauthenticated user send GET request to "<endpoint>" using "<artistName>" id
    Then the response code should be <code>

    Examples:
      | endpoint          | code | artistName |
      | /api/artist/find/ | 401  | Rick O'Kon |

  Scenario Outline: Retrieve an artist with invalid id and no authentication
    When I as an unauthenticated user send GET request to "<endpoint>" with invalid id
    Then the response code should be <code>

    Examples:
      | endpoint          | code |
      | /api/artist/find/ | 401  |
