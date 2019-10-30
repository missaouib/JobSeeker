## Technology stack
- Backend
  - Java 11
  - Spring Boot 2
  - PostgreSQL
- Frontend
  - Typescript 3
  - Angular 8
- Requirements
  - Java
  - Node
  - Docker

# DEMO 

#### Run locally
- make sure u have a specific ports in dockerfiles and nginx
- `docker-compose up`
- `docker run -p 5432:5432 -e POSTGRES_USER=docker -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=docker postgres`

#### Deploy Docker to Aws using Elastic Beanstalk Single-Container
1. Backend
    - Create RDS Instance
        - Make sure u have checked "public accesibility" to "Yes"
        - Change security group for PSQL TCP 5432 (or for anywhere)
    - Set up the valid credentials in properties to postgres RDS
    - Set up static PORT in Dockerfile EXPOSE 8080
2. Frontend
    - Set up the http url for requesting backend
    - Set up static PORT in Dockerfile EXPOSE 80
3. Deploy
    - Create Elastic Beanstalk app
    - Create two environments with docker platform
    - zip this two folders with Dockerfile on top
    - Upload zip one by one
  
#### Deploy Docker to Aws using Elastic Beanstalk Multi-Container
1. Create RDS Instance like in previous instruction
2. Create ECR repository and push your two images
  - Configure IAM Security with some online guide
  - install AWS CLI
  - login with `aws configure`
  - Follow the instruction commands on ECR repository 
3. Create Dockerrun.aws.json ane push it with the Elastic BeanStalk
  
#### Deploy Docker to Aws using EC2 ECS
#### Deploy Docker to Aws using clean EC2
  
#### Deploy Docker to Heroku
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
