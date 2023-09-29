# Portfolio service of vest tech

This handles the portfolio of each user



## Getting Started

- This service relies on Postgres as primary source of truth.
- For the messaging queues, this service depends on RabbitMq



### Prerequisites

- Postgres has to be installed and running
- RabbitMq has to be running either as a container or in device
- Make sure to rename your application-dummy.properties to application.properties
- Add the properties and run it!

### To run
- Make sure you have the investtech-commons repo in the same project directory
- Move the docker file to the parent folder and docker build
  - First it builds the commons and stores in the container /.m2
  - Deletes the target file and other jar files
  - Then the portfolio service is built.
```
Directory: 
project
|
|_ investtech-commons
|
|_ investtech-portfolio
|
|_ Dockerfile


$ docker build --no-cache -t portfolio:live .


```