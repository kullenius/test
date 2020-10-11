# How to setup urlshortener
### Make sure to have :
- Maven
- Java
- Postgres
- Postman (or similar tool to make Json HTTP request)

### Your request should look like
#### For POST 
url: http://localhost:8081/add
```json
{
  "url":"http://myurl.com"
}
```
#### For GET
url: http://localhost:8081/myID

psst.. You'll get your ID from the POST-request
