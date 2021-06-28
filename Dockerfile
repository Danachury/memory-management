FROM maven:3.8.1-openjdk-11-slim

WORKDIR /memorymanagement

memorymanagement-1.0-RC.jar /memorymanagement

CMD ["echo HelloWorld!"]
