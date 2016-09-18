[![build status](https://secure.travis-ci.org/survivejs/react.png)](http://travis-ci.org/survivejs/react) [![Join the chat at https://gitter.im/survivejs/react](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/survivejs/react)

# Dashboard Spring Boot Sample

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


curl -H "Content-Type: application/json" -H "Authorization: bearer 256992ef-1b0d-4541-8677-05dfaf751b5a"  http://localhost:8080/api/rest/employee
// By Principal => get Roles also
curl -H "Content-Type: application/json" -H "Authorization: bearer 256992ef-1b0d-4541-8677-05dfaf751b5a"  http://localhost:8080/api/rest/employee1
// By Request => get UserName only
curl -H "Content-Type: application/json" -H "Authorization: bearer 256992ef-1b0d-4541-8677-05dfaf751b5a"  http://localhost:8080/api/rest/employee2
// By ThreadLocal => get Roles also
curl -H "Content-Type: application/json" -H "Authorization: bearer 256992ef-1b0d-4541-8677-05dfaf751b5a"  http://localhost:8080/api/rest/employee3

curl -H "Content-Type: application/json" -H "Authorization: bearer 60445446-c66a-4d20-bbd0-07230a4002f6"  http://localhost:8080/userinfo
// Test HasRole Annotation
curl localhost:8080/oauth/token -d "grant_type=password&scope=read&username=user&password=spring" -u clientapp:123456
=> {"code":500,"message":"Internal Server Error","error":null}
=> CustomExceptionHandler       : {"path":"/users","from":"0:0:0:0:0:0:0:1","message":"Access is denied"}
curl localhost:8080/oauth/token -d "grant_type=password&scope=read&username=admin&password=spring" -u clientapp:123456
curl -H "Content-Type: application/json" -H "Authorization: bearer baf81d69-3b02-4cd3-a466-48bee52dcd17"  http://localhost:8080/users

#curl -H "Content-Type: application/json" -H "Authorization: bearer e08ab2b7-f7e7-42cb-a2a3-541b064b27d7" --data '{}' http://localhost:8080/api/users
curl -H "Content-Type: application/json" -H "Authorization: bearer 453ab3fd-6df6-49a4-ba01-fb5b93430a1f"  http://localhost:8080/api/users

curl -H "Content-Type: application/json" -H "Authorization: bearer 453ab3fd-6df6-49a4-ba01-fb5b93430a1f"  http://localhost:8080/api/rest/employee
curl -H "Content-Type: application/json" -H "Authorization: bearer 453ab3fd-6df6-49a4-ba01-fb5b93430a1f" -X POST -d "{"""name""":"""Jonathan1""","""empType""":null,"""empGrade""":null}" http://localhost:8080/api/rest/employee
curl -H "Content-Type: application/json" -H "Authorization: bearer 91af5423-82b0-456f-a4be-dc4a34858bd3"  http://localhost:8080/api/rest/employee/page?page.size=1"&"page=4

Monitor:
http://localhost:8082/metrics
http://localhost:8082/health
http://localhost:8082/trace
http://localhost:8082/dump
http://localhost:8082/beans
http://localhost:8082/configprops
http://localhost:8082/info
http://localhost:8082/autoconfig
http://localhost:8082/env


curl localhost:9080/oauth/token -d "grant_type=password&scope=read&username=john&password=123" -u fooClientIdPassword:secret