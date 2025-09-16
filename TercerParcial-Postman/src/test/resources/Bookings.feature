Feature: Booking endpoint

  @run
  Scenario Outline: POST con datos erroneos
    Given Realizo un POST
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <checkin> | <checkout> | <additionalneeds> |
    Then Verifico que el estado del codigo es 400
    And Verificamos que el campo "booking.firstname" contenga "<firstname>"

    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Joh@      | Doe/*    | -150       | true        | 2025-09-20 | 2025-09-25 | Breakfast       |

  @run
  Scenario Outline: POST sin nombres
    Given Realizo un POST
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <checkin> | <checkout> | <additionalneeds> |
    Then Verifico que el estado del codigo es 400
    And Verificamos que el campo "booking.firstname" contenga "<firstname>"

    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      |           |          | 150        | true        | 2025-09-20 | 2025-09-25 | Breakfast       |

  @run
  Scenario Outline: Realizamos POST con numeros en los campos de nombre
    Given Realizo un POST
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <checkin> | <checkout> | <additionalneeds> |
    Then Verifico que el estado del codigo es 400
    And Verificamos que el campo "booking.firstname" contenga "<firstname>"

    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | 156948    | 1564     | 150        | true        | 2025-09-20 | 2025-09-25 | Breakfast       |

  @run
  Scenario Outline: PUT actualizando con valores vacios
    Given Generamos un nuevo token
    Then Verifico que el estado del codigo es 200
    And Realizo un PUT con los siguientes datos "290"
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <checkin> | <checkout> | <additionalneeds> |
    Then Verifico que el estado del codigo es 400
    And Verificamos que el campo "firstname" contenga "<firstname>"

    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      |           |          |            | false       | 2025-10-01 | 2025-10-05 | Lunch           |

  @run
  Scenario Outline: DELETE en los registros
    Given Generamos un nuevo token
    And Realizamos un Delete al id "<id>"
    Then Verifico que el estado del codigo es 201
    And Realizo una llamada GET aL ID "<id>"
    Then Verifico que el estado del codigo es 404

    Examples:
      | id |
      | 45  |

