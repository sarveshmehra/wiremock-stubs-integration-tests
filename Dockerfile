
FROM registry.company.net/company/server-jre:8u111company7

ADD build/libs/kappaweb-mock.jar kappaweb-mock.jar

EXPOSE 8095

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/kappaweb-mock.jar"]
