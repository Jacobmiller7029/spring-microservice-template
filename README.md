# Description
An application that allows searching of a carriers' list of saved shipping lanes and contact details


# Dependencies
- Postgresql 9.5
    - Geolocations data
    - The following plugins are required for the application to run:
        - cube
        - earthdistance
        
Although the above plugins are required to run it locally, they are taken care of in the docker container.     
        
- Lombok; This spring application is configured to use Project Lombok.


# Scripts

- Currently, all scripts should be ran from the top most project directory. Scripts will fail otherwise. 
- Be sure to be logged in to the docker account before attempting to run scripts that use docker.


### Local
To run application, run the `start-local.sh -c` script. 
"-c" is optional. This has docker remove generated containers and images. 
If you make changes to the java application, you'll need the -c command.

### Remote
(WIP)

## Additional Notes:
Google api key will need to be provided at runtime.

## Liquibase - 
Application uses Liquibase for database migrations. 

To create a changelog file, run `sudo mvn compile liquibase:diff` This will create a changelog file at `src/main/resources/db/changelog`
The naming scheme for changelog files is {YYYYMMDD}+current time in military time up to the minute.

The above command will generate a change log after comparing the changes in the entity files to whats in the actual database.

For some reason when running the above command, liquibase can't use Spring context values so the literal user creds & url values need to be used.

#TODO:
### MVP
- [ X ] Create for Carrier / Lane / Contact
- [ X ] Display Carrier / Lane / Contact
- [ X ] Remove for Carrier / Lane / Contact
- [ X ] Edit for Carrier / Lane / Contact
- [ X ] Mirror Updates / Deletes in search result list
- [ X ] Separate Carrier details from Lane in search. 
- [ X ] Soft Delete
- [ X ] Separate New Carrier section from Carrier Search section
- [ X ] Default value handling.(I.E, search radius needs to be more user friendly)

- [ X ] UI form submission more user friendly. (I.E, pressing enter on search should dispatch search)
- [ X ] Modal form validation
- [ X ] Login page / Credentials / Spring Security. Single user / password.

- [ X ] AWS Postgres instance setup.
- [ X ] Pre-deployment cleanup & manual deployment.
- [ X ] Setup Liquibase
- [  ] Spreadsheet upload
- [  ] Equipment Types CRUD
- [  ] Manual QA of Deployed project.
- [  ] Demo.
- [  ] Deployment script configuration & auto-deployment. 

### POST MVP RELEASE:
- [ ] Move frontend to s3 bucket.
- [ ] API call validation. (Frontend, with error modals explaining whats missing in input)?
- [ ] Setup encrypted Postgres instance on AWS. (Part of being PII compliant)
- [ ] Setup HTTPS domain (Part of becoming PII compliant)
- [ ] Harden backend (Improved error handling, responses, ect)
- [ ] Harden frontend up (Add toasts, other notifications, loading animations, ect)
- [ ] Fully Unit tested Frontend / Backend.
- [ ] AWS / Jenkins Pipeline setup
- [ ] Code cleanup / demonstration worthy refactor / Architecture refactor
- [ ] UI overhaul

### Nice to have improvements
- Color Scheme update
- Multi-client
- Multi-user
- Admin functionality
    - Section for manually creating users / passwords
    - Section for manually deleting users
- Setup application to user Mongo instead of Sql
- Automated password reset.
- Integration tested Backend
- E2E testing
- More robust local startup script.(Should include things like multiple flags in any order, perhaps start up the frontend as well.)
- Monitoring and error reporting applications. Could be self made(Like weekly emails or something).

# Project AWS overview
This application uses 2 AWS EC2 instances. One for frontend and backend. 

The project contains the Dockerfiles that are on each instance. Each docker 
file is configured to build its application and provide the needed environment variables.

There are a deployment script that acts as a local pipeline for deploying. Testing will be added to those eventually.

### Manually connect to EC2 instance
Username is: **ec2-user**  
Verify keyPem exists locally. 
- Frontend deployment uses `MacKeyPair.pem` file.

To login, run this command: `ssh -i /path_to_key/my_key.pem user_name@public_dns_name`

For example, `ssh -i MacKeyPair.pem ec2-user@ec2-54-191-4-35.us-...`

### EC2 instance setup
To get any new EC2 instances up and running, run these commands after connecting to the instance
- `sudo yum update -y`
- `sudo yum install -y docker`
- `sudo service docker start`

This will start docker.

### Manual deploy
Once logged on:
- To start the frontend, run: `sudo docker run -d -p 80:3000 --name frontend jacobmiller7029/lanetracker-frontend:latest`
- to start the backend, run: `sudo docker run -d -p 80:8090 -e PROFILE=prod -e DB_PASSWORD=.. -e DB_USER=.. -e GOOGLE_API_KEY=.. -e SECRET=.. --name backend jacobmiller7029/lanetracker:latest`

### Automatic deployment (WIP)
The idea will be 3 scripts. One to deploy the frontend, one to deploy the backend. And one that calls both scripts. 
The individual deploy scripts build the application and push to docker hub. They will then login into each EC2 instance 
and pull and run the latest image. 
(WIP)