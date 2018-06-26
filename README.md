# narratiwe-backend
Narratiwe backend pre-Firebase development.
It's the codebase to implement the Narratiwe (www.narratiwe.com) backend, alternative and indipendent to Firebase. 
It's not a REST service and all the request have POST method.

## Specifications

Java servlet powered by Maven.
Database NoSQL MongoDB.

## Build

Import the project in Eclipse then with right click on the project run `maven clean` and `maven install` to add all dependencies.

Download and install Mongodb on your system and create a narratiwe db scheme.

## Run

Run the project as web application using TomCat.

## Examples

USER SERVLET http://localhost:8080/Narratiwe/UserServlet

CREATE
{"command":"create","email":"danilo@gmail.com","name":"danilo", "surname":"dugo", "nickname":"dugo2", "description":"ciao", "password": "ciao", "city":"Parma", "nation":"italy", "address":"via Milano", "birthday":"01/03/1991", "image":"", "status":0}

LOGIN
{"command":"login","email":"danilo@gmail.com","password":"ciao"}

GET
{"command":"get","email":"danilo@gmail.com","token":"ggjvtnofod9envv0nvq7mka4r9"}

UPDATE
{"command":"update", "token":"8ur1pmkvvnm15cmj418a5maocn", "email":"","name":"", "surname":"", "nickname":"", "description":"", "password": "", "city":"", "nation":"", "address":"", "birthday":"28/02/1991", "image":"", "status": 0}

DELETE
{"command":"get", "token":"ggjvtnofod9envv0nvq7mka4r9"}


---------------------------------------------------------------------------------------------------------------------------------------------------
STORY SERVLET http://localhost:8080/Narratiwe/StoryServlet


CREATE
{"command":"create","title":"La mia prima storia", "description":"La mia prima descrizione", "category":1, "token":"4jp4om3ko3kkemirl62sl2cgbk", "date":"09/07/2017, 17:51", "language":"it"}

GET
{"command":"get", "id":"596258023bf80d2f1bf7b241", "token":"4jp4om3ko3kkemirl62sl2cgbk"}

UPDATE
{"command":"update","id":"596258023bf80d2f1bf7b241", "token":"4jp4om3ko3kkemirl62sl2cgbk","title":"FirstBook!", "description":"", "date":"02/07/2017, 18:45", "category":1, "language":""}

DELETE
{"command":"delete", "token":"4jp4om3ko3kkemirl62sl2cgbk", id:"596251203bf80d2f1ba3cd9f"}

---------------------------------------------------------------------------------------------------------------------------------------------------
STORYFRAME SERVLET http://localhost:8080/Narratiwe/StoryFrameServlet

CREATE
{"command":"create","content":"Questa è la prima paginaaa", "story":"596258023bf80d2f1bf7b241", "token":"4jp4om3ko3kkemirl62sl2cgbk", "date":"09/07/2017, 17:51"}

GET
{"command":"get", "id":"59625f983bf80d2f1b289833", "token":"4jp4om3ko3kkemirl62sl2cgbk"}

UPDATE
{"command":"update","id":"59625f983bf80d2f1b289833", "token":"4jp4om3ko3kkemirl62sl2cgbk","content":"22222222", "date":"02/07/2017, 18:45", "status":1}


DELETE
{"command":"delete", "token":"4jp4om3ko3kkemirl62sl2cgbk", id:"59625f983bf80d2f1b289833"}


