# inside-test 0.0.1-SNAPSHOT

Хотелось бы конечно исполльзовать Spring Security + строить апи по ресту но решил делать по заданию

Мой вариант построения рест апи:

> POST http://localhost:1024/v1/user - create user 
> 
> POST http://localhost:1024/v1/user/{userId}/auth - get token 
> 
> POST http://localhost:1024/v1/user/{userId}/message - create message 
> 
> GET http://localhost:1024/v1/user/{userId}/message - get all message 
> 
> GET http://localhost:1024/v1/user/{userId}/message/{messageId} - get message by id 

Далее примеры методов  из проекта

## Api example methods

1) Create user 

>curl --location --request POST 'http://localhost:1024/user' \
>--header 'Content-Type: application/json' \
>--data-raw '{
>    "name" : "Johnny",
>    "secret" : "admin"
>}'

2) Get token

>curl --location --request POST 'http://localhost:1024/auth' \
>--header 'Content-Type: application/json' \
>--data-raw '{
>    "login": "Johnny",
>    "secret": "admin"    
>}'

3) Create message

>curl --location --request POST 'http://localhost:1024/message' \
>--header 'Authorization: Bearer >eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiSm9yaWsiLCJ0b2tlbl9leHBpcmF0aW9uX2RhdGUiOjE2NjkzMTExODI4NTMsInVzZXJJZCI6IjIiLCJ0b2tlbl9jcmVhdGVfZGF0ZSI6MTY2OTMwMTE4Mjg>1M30.aAoPc9Tc1dEX0hVj30xrmP6Vf7rB-uutAP9z5F4Ycuoam3gUHc64bbMt2h_QTLV1T69WI8siWvVlHA-K9ajTdA' \
>--header 'Content-Type: application/json' \
>--data-raw '{
>    "name" : "Johnny",
>    "message" : "test message"
>}'

4) Get history

>curl --location --request POST 'http://localhost:1024/message' \
>--header 'Authorization: Bearer >eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiSm9yaWsiLCJ0b2tlbl9leHBpcmF0aW9uX2RhdGUiOjE2NjkzMTExODI4NTMsInVzZXJJZCI6IjIiLCJ0b2tlbl9jcmVhdGVfZGF0ZSI6MTY2OTMwMTE4Mjg>1M30.aAoPc9Tc1dEX0hVj30xrmP6Vf7rB-uutAP9z5F4Ycuoam3gUHc64bbMt2h_QTLV1T69WI8siWvVlHA-K9ajTdA' \
>--header 'Content-Type: application/json' \
>--data-raw '{
>    "name" : "Johnny",
>    "message" : "history"
>}'


## Deployment:
>Deployment process as easy as possible:
>
>#### Required software:
>- terminal for running shell scripts
>- docker
>- docker-compose
>#### to deploy application:
>#### 1) Switch into needed branch and run shell script with db auth and jwt secret params: 
>zsh example: `sh start.sh admin qwerty123 asdb123asdasdasdasdasdjajkfdlkt564frvb1r5vc4vb2c4r28vc5rcv4b258cvrv42358vcr425v4cr2vc452v3485cv235r4v2c5rv425vc4r28cr4v235v8cr4235vc4br2836cvr4b28c35v6c4r2v83cb
`

## Technology stack:
>- Maven 
>- Java 18
>- Spring boot \ Spring data | Spring MVC
>- PostgreSQL \ Liquibase \ Hibernate
>- Docker \ Docker compose
>- JUnit 5 \ Mockito \ lombok
>- Io.jsonwebtoken
>- Mapstruct

p.s покрыть абсолютно все сервисы тестами и сделать человеческий возврат ошибок к сожалению не позволило время
