FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILD_STAGE

#We are copying all the files inside of this directory into the container directory
COPY ./ ./

#Packaging up our code into a jar file (Without postgresql)
# "-DskipTests" this allows us to package without a db being attached
RUN mvn clean package -DskipTests

FROM openjdk:8-jre-alpine

#We're just copying over the jar file into our new image
COPY --from=MAVEN_BUILD_STAGE /target/reimbursement-api-0.0.1-SNAPSHOT.jar /reimbursement-api.jar

#Is the final part of the image, will tell the container how to start executing
CMD ["java", "-jar", "reimbursement-api.jar"]