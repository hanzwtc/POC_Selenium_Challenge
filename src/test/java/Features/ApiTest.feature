Feature: PRUEBA API - PRUEBITA

  @SendRequestApiTest
  Scenario Outline: User consultation of countries codes

    Given The Method configuration "GET"
    And   The Endpoint configuration "http://api.countrylayer.com/v2/alpha/"
    And   Endpoint is parameterized "<Data>"
    When  Api services execute
    Then  Verify responde code "<StatusCode>"
    And   User evidence of the petition

    Examples:
      |Data                                                 |StatusCode|
      |US?access_key=1175fea6ba50ad8eac7a31857b37deaa       |200       |
      |DE?access_key=1175fea6ba50ad8eac7a31857b37deaa       |200       |
      |GB?access_key=1175fea6ba50ad8eac7a31857b37deaa       |200       |
      |PERU?access_key=1175fea6ba50ad8eac7a31857b37deaa     |404       |
