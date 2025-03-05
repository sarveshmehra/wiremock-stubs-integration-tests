
FROM registry.company.net/bisnode/server-jre:8u111bisnode7

ADD build/libs/kappaweb-mock.jar kappaweb-mock.jar

EXPOSE 8095

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/kappaweb-mock.jar"]
