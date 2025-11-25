# Bank demo

Star Wars Api - API para Conexa


# Descripción

Esta API cumple la función de listar las peliculas, las personas, los vehiculos y las naves con una conexión a la API de Star Wars, además del listado, se agrego un filtro por id que tienen ambos, para que puedan seleccionar con el especificado.
Para que tenga una estricta seguridad se creó un login seguro con sus roles y autorizaciones para que todos aquellos que puedan listar y/o filtrar el contenido deba hacerlo con la sesion activa.

## Tecnologias

Esta API esta hecha con Java 21, donde el contenido esta realizado con Spring e Hibernate para el resguardo de datos del usuario. Para el resguardo de datos del usuario se utilizaron base de datos en memoria y MySQL.
El login se realizo con JWT token y Spring Security.
Se realizaron test tanto de aceptación como unitarios para la verificación del correcto funcionamiento de los servicios. 

## Clases

A continuación se detallan las clases.

## Films

### Films
 Contiene los datos de la API
 - title : titulo de la película. (String)
 - episode_id : episodio de la película. (String)
 - opening_crawl: párrafos iniciales al comienzo de la película. (String)
 - director: director de la película. (String)
 - producer: nombre del productor o productores de la película. (String)
 - release_date: formato de fecha ISO 8601 de estreno de una película en el país del creador original. (Date)
 - species: URL de recursos de especies que se encuentran en la película. (Listado de String)
 - starships: URL de recursos de naves espaciales que se encuentran en la película. (Listado de String)
 - vehicles: URL de recursos de vehículos que se encuentran en la película. (Listado de String)
 - characters: URL de recursos de personas que se encuentran en la película. (Listado de String)
 - planets: URL de recursos del planeta que se encuentran en la película. (Listado de String)
 - url: URL hipermedia del recurso. (String)
 - created: formato de fecha ISO 8601 del momento en que se creó del recurso. (String)
 - edited: formato de fecha ISO 8601 del momento en que se editó del recurso. (String)

### FilmsResult
Datos que contienen cada colección de películas.

- uid: Identificación de la colección. (String)
- description: Descripción de la película. (String)
- properties: Película obtenida. (Films)

### FilmApiResponse
Datos que conlleva un listado de colección de películas.

- message : mensaje de la API. (String)
- result: Listado de la colección de películas. (Listado de Films)

### FilmApiResponseFilter
Datos que conlleva la obtención del filtrado.
- message: mensaje de la API. (String)
- result: Película obtenida. (Films)

## People

 Contiene los datos de la API
 
 - name : Nombre de la persona. (String)
 - birth_year : fecha de nacimiento. (String)
 - eye_color: color de ojos. (String)
 - gender: genero. (String)
 - hair_color: color de pelo. (String)
 - height: altura. (Date)
 - mass: masa corporal. (String)
 - skin_color: color de piel. (String)
 - homeworld: URL de un recurso planetario, un planeta en el que esta persona nació o habita. (Listado de String)
 - films: URL de recursos cinematográficos en los que ha estado la persona. (Listado de String)
 - species: URL de recursos de especies que se encuentra la persona. (Listado de String)
 - starships: URL de recursos de naves que se encuentra la persona. (Listado de String)
 - vehicles: URL de recursos de vehiculos que se encuentra la persona. (Listado de String)
 - url: URL hipermedia del recurso. (String)
 - created: formato de fecha ISO 8601 del momento en que se creó del recurso. (String)
 - edited: formato de fecha ISO 8601 del momento en que se editó del recurso. (String)

### PeopleResult
Datos que contienen cada colección de las personas.

- uid: Identificación de la colección. (String)
- description: Descripción de la persona. (String)
- properties: Película obtenida. (People)

### PeopleApiResponse
Datos que conlleva un listado de colección de las personas.

- message : mensaje de la API. (String)
- result: Listado de la colección de las personas. (Listado de People)

### PeopleApiResponseFilter
Datos que conlleva la obtención del filtrado.
- message: mensaje de la API. (String)
- result: Persona obtenida. (People)

## Starships

 Contiene los datos de la API
 
 - name : Nombre de la nave. (String)
 - model : modelo de la nave. (String)
 - starship_class: clase de nave. (String)
 - manufacturer: fabricante de la nave espacial. (String)
 - cost_in_credits: costo de la nueva nave espacial, en créditos galácticos. (String)
 - length: longitud de la nave espacial en metros. (String)
 - crew: cantidad de personal necesaria para operar o pilotear la nave espacial. (String)
 - passengers: pasajeros. (String)
 - max_atmosphering_speed: velocidad máxima de la nave espacial en la atmósfera. "N/A" si esta nave espacial no puede realizar vuelos atmosféricos. (String)
 - hyperdrive_rating: clase de hiperimpulso de la nave espacial. (String)
 - MGLT: número máximo de Megaluces que la nave espacial puede viajar en una hora estándar. (String)
 - cargo_capacity: número máximo de kilogramos que la nave espacial puede transportar. (String)
 - consumables: tiempo máximo que la nave espacial puede proporcionar consumibles para toda su tripulación sin tener que reabastecerse. (String)
 - films: URL de recursos de películas que se encuentran las naves. (Listado de String)
 - pilots: URL de recursos de pilotos que se encuentran las naves. (Listado de String)
 - url: URL hipermedia del recurso. (String)
 - created: formato de fecha ISO 8601 del momento en que se creó del recurso. (String)
 - edited: formato de fecha ISO 8601 del momento en que se editó del recurso. (String)

### StarshipResult
Datos que contienen cada colección de las naves.

- uid: Identificación de la colección. (String)
- description: Descripción de la nave. (String)
- properties: Película obtenida. (Starships)

### StarshipApiResponse
Datos que conlleva un listado de colección de las naves.

- message : mensaje de la API. (String)
- result: Listado de la colección de las naves. (Listado de Starships)

### StarshipApiResponseFilter
Datos que conlleva la obtención del filtrado.
- message: mensaje de la API. (String)
- result: nave obtenida. (Starships)

## Vehicles

 Contiene los datos de la API
 
 - name : Nombre del vehiculo. (String)
 - model : modelo del vehiculo. (String)
 - vehicle_class: clase del vehiculo. (String)
 - manufacturer: fabricante del vehiculo. (String)
 - cost_in_credits: costo del vehiculo, en créditos galácticos. (String)
 - length: longitud del vehiculo en metros. (String)
 - crew: cantidad de personal necesaria para operar o pilotear el vehiculo. (String)
 - passengers: pasajeros. (String)
 - max_atmosphering_speed: velocidad máxima del vehiculo en la atmósfera. (String)
 - cargo_capacity: número máximo de kilogramos que el vehiculo puede transportar. (String)
 - consumables: tiempo máximo que el vehiculo puede proporcionar consumibles para toda su tripulación sin tener que reabastecerse. (String)
 - films: URL de recursos de películas que se encuentran los vehiculos. (Listado de String)
 - pilots: URL de recursos de pilotos que se encuentran los vehiculos. (Listado de String)
 - url: URL hipermedia del recurso. (String)
 - created: formato de fecha ISO 8601 del momento en que se creó del recurso. (String)
 - edited: formato de fecha ISO 8601 del momento en que se editó del recurso. (String)

### VehicleResult
Datos que contienen cada colección de los vehiculos.

- uid: Identificación de la colección. (String)
- description: Descripción del vehiculo. (String)
- properties: Vehiculo obtenida. (Vehicles)

### VehicleApiResponse
Datos que conlleva un listado de colección de los vehiculos.

- message : mensaje de la API. (String)
- result: Listado de la colección de los vehiculos. (Listado de Vehicles)

### VehicleApiResponseFilter
Datos que conlleva la obtención del filtrado.
- message: mensaje de la API. (String)
- result: vehiculo obtenida. (Vehicles)

## Users

### User

Datos del usuario.  

- Id: Identificador. (Long)
- dni: DNI del usuario (Integer)  
- username: Nombre de usuario. (String)  
- password: Contraseña. (String) 
- isAuthorized: Semaforo para saber si esta autorizado. (Boolean)

## Endpoints

La API cuenta con endpoints para el filtrado y listado de peliculas, personas, naves y vehiculos, como así tambien el login necesario.

## Films

### Obtener Listado
En este mismo se obtiene un listado de peliculas por paginación.

### Filtrado por ID
Se filtra por id de la colección.

## People

### Obtener Listado
En este mismo se obtiene un listado de personas por paginación.

### Filtrado por ID
Se filtra por id de la colección.

## Starships

### Obtener Listado
En este mismo se obtiene un listado de naves por paginación.

### Filtrado por ID
Se filtra por id de la colección.

## Vehicles

### Obtener Listado
En este mismo se obtiene un listado de vehiculos por paginación.

### Filtrado por ID
Se filtra por id de la colección.

## Users

### Añadir un usuario
Se agrega un usuario con su rol a la base de datos.

Request:

- id: Identificación. (Long)
- dni: DNI del usuario. (Integer)
- username: Nombre de usuario. (String)
- password: Contraseña. (String)
- isAuthorized: Boolean para saber esta autorizado. (Boolean)

### Login de la API
Se inicia sesión el usuario.

Request:
- username: Nombre de usuario. (String)
- password: Contraseña. (String)



