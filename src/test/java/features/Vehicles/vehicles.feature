Feature: Testing los endpoints de los vehiculos

  Background:
    * url 'http://localhost:8080/api/vehicles'
    * configure headers = { 'Accept': 'application/json' }

  Scenario: 1. Obtener la lista de los vehiculos con paginacion por defecto
    Given path '/'
    When method GET
    Then status 200
    And match response == { content: '#array', totalElements: '#number', totalPages: '#number', number: 0, size: 10, first: true, last: '#boolean' }

    * def firstUid = response.content[0].uid

  Scenario: 2. Obtener un vehiculo por UID (usando el UID guardado)
    Given path '/', 44
    When method GET
    Then status 200
    And match response == { uid: '#string', description: '#string', properties: '#object' }
    And match response.uid == 44

  Scenario: 3. Obtener la lista de los vehiculos con paginacion personalizada
    Given path '/'
    And param page = 1
    And param size = 5
    When method GET
    Then status 200
    And match response.number == 1
    And match response.size == 5

  Scenario: 4. Intentar obtener un vehiculo con UID que no existe (Not Found)
    Given path '/999999999'
    When method GET
    Then status 404