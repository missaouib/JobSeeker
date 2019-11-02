## Technology stack
- Backend
  - Java 11
  - Spring Boot 2
  - PostgreSQL
- Frontend
  - Typescript 3
  - Angular 8

# DEMO 

#### Run locally
- make sure u have a specific ports in dockerfiles and nginx
- `docker-compose up`
- `docker run -p 5432:5432 -e POSTGRES_USER=docker -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=docker postgres`

#### Configure AWS CLI
1. Create admin user in IAM with all permissions
2. Install `apt install awscli`
3. Login with admin credentials `aws configure`
4. Connect to EC2 
   - Open port 22 in Security Group for EC2
   - Create KeyPair for EC2
   - Set up only read rights `chmod 400 key.pem`
   - `ssh -i <key.pem> <ec2 dns>`
5. Create Postgres RDS Instance
   - Make sure u have checked "public accesibility" to "Yes"
   - Change security group for PSQL TCP 5432 (or for anywhere)

#### Deploy Docker to Aws using EC2 ECS (recomendded)
1. Set up the valid credentials in properties for RDS
2. Create ECR repository and push your two images
3. Create ECS Cluster
4. Create ECS Task definitions (select 80 and 8080 ports)
5. Run tasks
  
#### Deploy Docker to Aws using Elastic Beanstalk Multi-Container
1. Set up the valid credentials in properties for RDS
2. Create ECR repository and push your two images
    - Go to IAM -> Policies
    - Attach `AmazonEC2ContainerRegistryReadOnly` for Elastic Beanstalk roles
    - Follow the instruction commands on ECR repository in Aws Cli
3. Create Elastic Beanstalk app with multi-containers docker platform
4. Set up the http url for requesting backend 
5. Create and upload Dockerrun.aws.json
  
#### Deploy Docker to Aws using Elastic Beanstalk Single-Container
1. Set up the valid credentials in properties to postgres RDS
2. Set up static PORT in Dockerfile EXPOSE 8080
3. Set up the http url for requesting backend
4. Set up static PORT in Dockerfile EXPOSE 80
5. Create Elastic Beanstalk app with docker platform environment
6. Zip this two folders with Dockerfile on top and upload one by one
  
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
