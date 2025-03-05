package com.company.svc;


import com.company.api.testsupport.Configuration;
import com.company.api.testsupport.MicroserviceTestBase;
import com.company.svc.test.ScimUserControllerTests;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;


public class CommonTest extends MicroserviceTestBase {

    private static final Logger logger = LoggerFactory.getLogger(CommonTest.class);

    private static String environment;

    @BeforeClass
    public static void setupEnvironment() throws IOException {

        PropertyConfigurator.configure(ScimUserControllerTests.class.getClassLoader().getResource("log4j.properties"));
        Configuration conf = new Configuration();
        String environmentProperty = conf.getProperty("test.env");
        if (environmentProperty != null && !environmentProperty.isEmpty()) {
            environment = environmentProperty;
        } else {
            String configFile = System.getProperty("test.config.file", "dev.properties");
            environment = configFile.split("\\.")[0];
        }

        logger.info("Running tests in environment: " + environment);

    }

    public static boolean isEnvironment(Collection<String> environments) {
        return environments.contains(environment);
    }

    public static boolean isEnvironment(String environment) {
        return CommonTest.environment.equals(environment);
    }

    public static boolean isNotRunningInProd() {
        return !"prod".equals(environment);
    }
}
