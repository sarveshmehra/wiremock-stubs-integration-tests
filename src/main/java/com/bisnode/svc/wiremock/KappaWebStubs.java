package com.bisnode.svc.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.WireMockServer;


public class KappaWebStubs {

    private final WireMockServer mockServer;

    public KappaWebStubs(WireMockServer mockServer) {
        this.mockServer = mockServer;
    }

    public void initialize() {
        this.stubAuthenticationFail();
        this.stubAuthenticationSuccess();
        this.stubGetUserInfo();
        this.stubGetUserNotFound();
        this.stubHealth();
        this.stubResetPasswordSuccess();
        this.stubUnauthorized();
    }

    private void stubAuthenticationSuccess() {

        mockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo("/kappa-web/rest/v3/users/authenticate"))
                .withRequestBody(WireMock.equalToJson("{\"password\":\"Password1\",\"userCode\":\"987654\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "  \"data\": {\n" +
                                "    \"code\": \"SUCCESS\"\n" +
                                "  }\n" +
                                "}")));
    }

    private void stubAuthenticationFail() {

        mockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo("/kappa-web/rest/v3/users/authenticate"))
                .withRequestBody(WireMock.equalToJson("{\"password\":\"Password1\",\"userCode\":\"987653\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(401)
                        .withBody("{\n" +
                                "  \"data\": {\n" +
                                "    \"code\": \"INVALID_CREDENTIALS\"\n" +
                                "  }\n" +
                                "}")));
    }

    private void stubGetUserInfo() {

        mockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/kappa-web/rest/v3/users/987611"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "  \"data\": [\n" +
                                "    {\n" +
                                "      \"userCode\": \"987611\",\n" +
                                "      \"firstName\": \"Mock\",\n" +
                                "      \"lastName\": \"User\",\n" +
                                "      \"email\": \"mock.user@bisnode.com\",\n" +
                                "      \"loginName\": \"987611\",\n" +
                                "      \"passwordExpireDate\": \"2025-01-31\",\n" +
                                "      \"expandable\": [\n" +
                                "        \"customers\"\n" +
                                "      ]\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}")));
    }

    private void stubGetUserNotFound() {

        mockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/kappa-web/rest/v3/users/987612"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                ));
    }

    private void stubResetPasswordSuccess() {

        mockServer.stubFor(WireMock.patch(WireMock.urlPathEqualTo("/kappa-web/rest/v3/users/password"))
                .withRequestBody(WireMock.equalToJson("{\"password\":\"password\",\"userCode\":\"987652\"}"))
                .willReturn(WireMock.aResponse().withStatus(200)
                        .withBody("{\n" +
                                "  \"data\": {\n" +
                                "    \"responseCode\": \"SUCCESS\",\n" +
                                "    \"userCode\": \"987652\"\n" +
                                "  }\n" +
                                "}")));
    }

    private void stubUnauthorized() {

        mockServer.stubFor(WireMock.patch(WireMock.urlPathEqualTo("/kappa-web/rest/v3/users/password"))
                .withRequestBody(WireMock.equalToJson("{\"password\":\"password\",\"userCode\":\"3\"}"))
                .willReturn(WireMock.aResponse().withStatus(401)
                        .withBody("{\n" +
                                "  \"data\": {\n" +
                                "    \"responseCode\": \"UNAUTHORIZED\"\n" +
                                "  }\n" +
                                "}")));
    }

    private void stubHealth() {

        mockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/health"))
                .willReturn(WireMock.aResponse().withStatus(200)
                        .withBody("{\n" +
                                "    \"status\": \"RUNNING\"\n" +
                                "}")));
    }
}
