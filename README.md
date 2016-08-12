http://localhost:8080/api/rest/greeting

http://localhost:8080/api/rest/hello

http://localhost:8080/v2/api-docs
http://localhost:8080/swagger-ui.html
http://localhost:8080/swagger-resources
http://localhost:8080/swagger-resources/configuration/ui
http://localhost:8080/swagger-resources/configuration/security

Rest Data:
http://localhost:8080/api/type
http://localhost:8080/api/type/1
http://localhost:8080/api/type?page=1&size=1&sort=name,desc
http://localhost:8080/api/profile/type
http://localhost:8080/api/type/search/findByGrpIgnoringCase?grp=emptype

Cache
http://localhost:8080/api/rest/message?code=a
http://localhost:8080/api/rest/message?code=code1
Metrics:
http://localhost:8082/metrics
"cache.messages.hit.ratio":0.7777777862548828,"cache.messages.miss.ratio":0.2222222328186035

Jersey:
http://localhost:8080/reverse?input=a



curl localhost:8080/oauth/token -d "grant_type=password&scope=read&username=user&password=spring" -u clientapp:123456
{"access_token":"96de54f9-46cf-43dd-9217-5f584a28739f","token_type":"bearer","refresh_token":"f60bd8b9-84b2-4c97-8c7f-042ff8f59fa8"expires_in":43199,"scope":"
read"}