FROM homework18:18.0-SNAPSHOT

FROM openjdk:8-jdk
MAINTAINER Victor Skurikhin

ENV TARGET_JAR=eureka-server.jar
ENV PROJECT_DIR=/opt/project
ENV TARGET_DIR=$PROJECT_DIR/eureka-server/target
WORKDIR $TARGET_DIR

COPY --from=0 $TARGET_DIR/$TARGET_JAR $PROJECT_DIR/

EXPOSE 8761

CMD [ "java", "-jar", "/opt/project/eureka-server.jar" ]
