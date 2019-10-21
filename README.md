##### Technology stack
- Backend
  - Java 11 Spring Boot 2 PostgreSQL
- Frontend
  - Typescript 3 Angular 8

##### Run demo locally
- make sure u have a specific ports in dockerfiles and nginx
- `docker-compose up --build`

##### Deploy to heroku
1. Backend
    - Create heroku app
    - Add postgres plugin
    - Change port in properties to dynamic ${PORT}
    - Set up the valid credentials in properties to postgres heroku
2. Frontend
    - Create heroku app
    - Switch between production and develop mode in env
    - Change all ports in nginx and docker to dynamic ${PORT}
    - Make sure u have valid paths in dockerfile
    - Set up valid http urls to backend
3. Deploy
    - heroku login
    - git init
    - heroku git:remote -a <\appname>\
    - heroku container:login
    - heroku container:push web
    - heroku container:release web
