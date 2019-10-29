##### Technology stack
- Backend
  - Java 11
  - Spring Boot 2
  - PostgreSQL
- Frontend
  - Typescript 3
  - Angular 8
- Requirements
  - Java
  - Docker
  - Node
  - Npm

##### Run demo locally
- make sure u have a specific ports in dockerfiles and nginx
- `docker-compose up`
- `docker run -p 5432:5432 -e POSTGRES_USER=docker -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=docker postgres`

##### Deploy to Aws using Elastic Beanstalk
1. Backend
    - Create RDS Instance
    - Set up the valid credentials in properties to postgres RDS
    - Set up static PORT in Dockerfile EXPOSE 8080
2. Frontend
    - Set up static PORT in Dockerfile EXPOSE 80(https) or 443(https)
3. Deploy
    - Create Elastic Beanstalk app
    - zip this two folders with Dockerfile on top
    - Upload zip one by one
    - Check on EC2 instances
  
##### Deploy to Heroku (not recommended)
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
    - heroku git:remote -a <\appname>
    - heroku container:login
    - heroku container:push web
    - heroku container:release web
