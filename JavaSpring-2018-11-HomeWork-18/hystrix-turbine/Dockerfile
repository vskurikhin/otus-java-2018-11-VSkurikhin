FROM maven:3.6.1-jdk-8

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mvn -B dependency:resolve

ADD ./src/ $PROJECT_DIR/src
RUN mvn install

FROM openjdk:8-jdk

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY --from=0 $PROJECT_DIR/target/hystrix-turbine.jar $PROJECT_DIR/

EXPOSE 8080

CMD [ "java", "-jar", "/opt/project/hystrix-turbine.jar" ]
