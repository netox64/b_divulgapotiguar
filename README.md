Anuncia_potiguar

<div align="center">
  <img src="https://github.com/yt-neto/milkapi/blob/main/docs/img/img1.png" width="250" height="250" />
  <img src="https://github.com/yt-neto/milkapi/blob/main/docs/img/img2.png" width="250" height="250" />
</div>

<h4 align="center">This is a project to promote the sale of properties in the Potiguar region </h4>

<p align="center">
<img src="https://sonarcloud.io/api/project_badges/measure?project=netox64_b_divulgapotiguar&metric=alert_status">
<img src="https://sonarcloud.io/api/project_badges/measure?project=netox64_b_divulgapotiguar&metric=coverage">
<img src="https://sonarcloud.io/api/project_badges/measure?project=netox64_b_divulgapotiguar&metric=duplicated_lines_density">
<img src="https://sonarcloud.io/api/project_badges/measure?project=netox64_b_divulgapotiguar&metric=security_rating">
<img src="https://sonarcloud.io/api/project_badges/measure?project=netox64_b_divulgapotiguar&metric=sqale_index">
</p>

<p align="center">
    <a href="#Technologies_Used">Technologies Used</a> •
    <a href="#Api_resources">Api resources</a> •
    <a href="#Folder_Architecture">Folder Architecture FrontEnd</a> •
    <a href="#Folder_Architecture">Folder Architecture BackEnd</a> •
    <a href="#Running_Application">Running application</a> •
    <a href="#About_the_Author">About the Author</a> •
    <a href="https://github.com/neto-works/estocaria_ponto_net/blob/main/LICENSE">Licensing</a>
</p>

## Technologies_Used

- The following technologies were used in this project:
    - Java as a programming language.
    - springboot application.
    - And the docker containerization tool.
    - Next.js to build the frontend.

## Designer_basis_for_application

- [figma](https://www.figma.com/design/2flw1NCYRfBob1jQdt15Ea/anunciar_potiguar?node-id=0-1&t=4548e2NpDK2J3CeQ-1)

## Folder_Architecture
```
api.src
  ├  └─  main.java.ofc.api
  ├                    └──────────  Config : all configs, springSecurity, Swagger ...
  ├                    └──────────  Controllers : all controllers, interfaces, Dtos ...
  ├                    └──────────  Entities  : models for aplication.
  ├                    └──────────  Repositories : Repositories, JPa base , generics ...
  ├                    └──────────  Services : all Services, generics and interfaces ...
  ├  
  └───────  main.resources   
  ├                    └──────────  aplication.properties
  ├  
  └───────  test
  ├──  docher-compose.yml
  └──  pom.xml : all dependecies
```

## Bank_Model

<img src="https://github.com/yt-neto/milkapi/blob/main/docs/diagramas/banco.png" />

## Api_resources v1

### Auth 

- open to everyone
 ```http
  POST /api/auth/login
 ```
 ```http
  POST /api/auth/register
 ```
 ```http
  POST /api/auth/refresh-token
 ```
- Authorize Policy _"QuemPuderGerenciar"(Gerente)_

 ```http
  POST /api/auth/revoke/{email}
 ```
   ```http
  POST api/auth/createRole
 ```
   ```http
  POST /api/auth/addUserToRole
 ```

## Prerequisites

- SDKMAN for manager version jdk
- JDK 17
- Docker and Docke-compose plugin

## Running_Application

- create the bank:
 ```
    cd api && docker compose up
 ```

- install dependencies:
 ```
    mvn install

 ```

- to execute:
```
  java -jar target/nome_projeto-1.0-SNAPSHOT.jar
```

- http://localhost:8080/swagger-ui/index.html?urls.primaryName=public

## Test the application using postman

- open your postman desktop
- click import
- import from z_Postman_Endpoints folder

> [!IMPORTANT]
> When opening the endpoints, check the "base_url" addressing variable if it is correct.

> [!WARNING]
> When making requests, pay attention to the tokens and the app's authorization policies. Errors may also be found as it is still under development


## About_the_Author
- Clodoaldo Neto :call_me_hand:
