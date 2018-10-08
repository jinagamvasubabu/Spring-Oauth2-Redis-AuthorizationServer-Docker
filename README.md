## Authorization Server:
Implemented Authorization server using Spring boot and Oauth2 AutoConfigure. 

## Features:
* A Beautiful UI to create the clients with the details including `ClientId, Client Secret(Bcrypt Password), Access token, Refresh token, ResorcesIds, Scopes and Roles`.
* `Tokens` will be stored in `Redis DB`.
* `MYSQL` will be used to store the `client credentials`
* `Docker compatable`, just execute startup.sh to start the APP. (Simplified Start)
* Bonus Endpoint to update the ClientSecret or Password 

## How to run?
* Execute the startup.sh file in [ResourceServer](https://github.com/jinagamvasubabu/Spring-Oauth2-Redis-ResourceServer-Docker) which inturn creates a `spring-oauth2-redis-resourceserver-docker_resource-server:latest` image
* Execute the startup.sh file in [AuthorizationServer](https://github.com/jinagamvasubabu/Spring-Oauth2-Redis-AuthorizationServer-Docker)

## Sample Curl Requests:

### Auth token generation (grant-type: client_credentials):
```
curl -X POST \
  http://localhost:8082/secure/oauth/token \
  -H 'Authorization: Basic UmVzb3VyY2VTZXJ2ZXI6c2VjcmV0' \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: 55026a54-ba64-46ec-8ec4-730c28d525d2' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -F grant_type=client_credentials
  ```
 ### CheckToken 
 ```
 curl -X GET \
  'http://localhost:8082/secure/oauth/check_token?token=57db17ad-469f-4f33-90a4-c4237c0f1bbb' \
  -H 'Authorization: Basic UmVzb3VyY2VTZXJ2ZXI6c2VjcmV0' \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: 41605116-57fd-4f89-a431-9b97960e90b2'
 ```
 
 ### Update ClientSecret:
 ```
 curl -X POST \
  http://localhost:8082/secure/secrets/reset \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: d1b5030f-af9e-4623-a495-e4bd05cffa21' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -F clientId=ResourceServer \
  -F resetType=CLIENT_CREDENTIALS \
  -F existingClientSecret=secret \
  -F newClientSecret=secret
  ```
  
  ### Update Password
  ```
  curl -X POST \
  http://localhost:8082/secure/secrets/reset \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: d5f42841-d527-4c90-94f6-27e7ec0795a9' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -F clientId=test \
  -F resetType=PASSWORD \
  -F userName=oauth_admin \
  -F existingPassword=user \
  -F newPassword=user
  ```
  
  







