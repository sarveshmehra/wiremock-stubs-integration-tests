package com.company.svc;

import com.company.svc.wiremock.KappaWebStubs;
import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class Application {

    public static void main(String... args) {
        int port = Integer.parseInt(System.getProperty("port", "8095"));
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(port));
        KappaWebStubs stubs = new KappaWebStubs(wireMockServer);
        stubs.initialize();
        wireMockServer.start();
    }


}
