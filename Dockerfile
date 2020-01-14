FROM java:8
EXPOSE 8080
ADD /target/payment-1.0-SNAPSHOT.jar payment.jar
ENTRYPOINT ["java","-jar","payment.jar"]
