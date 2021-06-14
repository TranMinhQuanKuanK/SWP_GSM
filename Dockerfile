FROM tomcat:8.5.66-jdk8-adoptopenjdk-hotspot

LABEL maintainer="hnduong0103@gmail.com"

ADD /GroceryStoreManagement/dist/GroceryStoreManagement.war /usr/local/tomcat/webapps/


EXPOSE 8080
CMD ["catalina.sh", "run"]