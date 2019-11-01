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

### Config AWS CLI
1. install `apt install awscli`
2. configure IAM
3. aws configure login
4. debug ec2 via console
  - open port 22
  - create key pair
  - ssh -i file dns

#### Deploy Docker to Aws using EC2 ECS (recomendded)
1. Create RDS Instance like in previous instruction
   - Make sure u have checked "public accesibility" to "Yes"
   - Change security group for PSQL TCP 5432 (or for anywhere)
   - Set up the valid credentials in properties to postgres RDS
2. Create ECR repository and push your two images
    - Configure IAM Security with some online guide
      - Go to IAM -> Policies
      - Attach `AmazonEC2ContainerRegistryReadOnly` for Elastic Beanstalk roles
    - install AWS CLI
    - login with `aws configure`
    - Follow the instruction commands on ECR repository 
3. Create ECS Cluster
4. Create ECS Task definitions 
5. Run task

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
   - Make sure u have checked "public accesibility" to "Yes"
   - Change security group for PSQL TCP 5432 (or for anywhere)
   - Set up the valid credentials in properties to postgres RDS
2. Create ECR repository and push your two images
    - Configure IAM Security with some online guide
      - Go to IAM -> Policies
      - Attach `AmazonEC2ContainerRegistryReadOnly` for Elastic Beanstalk roles
    - install AWS CLI
    - login with `aws configure`
    - Follow the instruction commands on ECR repository 
3. Create Elastic Beanstalk app with multi-containers docker platform
4. Set up the http url for requesting backend 
4. Create and upload Dockerrun.aws.json
  
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
