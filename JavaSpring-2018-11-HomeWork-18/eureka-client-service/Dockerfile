FROM homework18:18.0-SNAPSHOT

FROM openjdk:8-jdk
MAINTAINER Victor Skurikhin

ENV TARGET_JAR=eureka-client-service.jar
ENV PROJECT_DIR=/opt/project
ENV TARGET_DIR=$PROJECT_DIR/eureka-client-service/target
WORKDIR $TARGET_DIR

COPY --from=0 $TARGET_DIR/$TARGET_JAR $PROJECT_DIR/

EXPOSE 8080

CMD [ "java", "-jar", "/opt/project/eureka-client-service.jar" ]
