FROM maven:3.6.1-jdk-8
MAINTAINER Victor Skurikhin

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
ADD ./eureka-server/         $PROJECT_DIR/eureka-server
ADD ./hystrix-monitor/       $PROJECT_DIR/hystrix-monitor
ADD ./hystrix-turbine/       $PROJECT_DIR/hystrix-turbine
ADD ./eureka-client-service/ $PROJECT_DIR/eureka-client-service
RUN mvn -B install

CMD [ "sh" ]
