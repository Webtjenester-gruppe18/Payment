FROM java:8
ADD /target/paymentRequest-1.0-SNAPSHOT.jar paymentRequest.jar
ENTRYPOINT ["java","-jar","paymentRequest.jar"]
