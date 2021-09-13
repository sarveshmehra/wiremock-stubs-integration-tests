package com.bisnode.svc.test;

import com.bisnode.svc.CommonTest;
import com.bisnode.test.ApiException;
import com.bisnode.test.client.ScimUserControllerApi;
import com.bisnode.test.model.FilterRequestBody;
import com.bisnode.test.model.ScimResultWrapper;
import com.bisnode.test.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScimUserControllerTests extends CommonTest {

    ScimUserControllerApi api = new ScimUserControllerApi();
    String filterString = "";

    @Before
    public void setUp() {
        api.getApiClient().setBasePath(getApiUrl());
    }

    @After
    public void easeOff() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Test
    public void shouldReturnUserInfoWithGet() throws ApiException{

        filterString = "userName eq \"987611\"";
        ScimResultWrapper response = api.filterUsingGET(filterString);
        assertEquals("987611", response.getResources().get(0).getUserName());
        assertEquals("mock.user@bisnode.com", response.getResources().get(0).getEmails().get(0).getValue());
        assertEquals("1", response.getTotalResults().toString());
    }

    @Test
    public void shouldNotReturnUserInfoWithGet() throws ApiException {

        filterString = "userName eq \"987612\"";
        ScimResultWrapper response = api.filterUsingGET(filterString);
        assertEquals("0", response.getTotalResults().toString());
    }

    @Test
    public void shouldReturnBadRequestOnGet() {

        try {
            filterString = "userName  \"987611\"";
            api.filterUsingGET(filterString);
        } catch (ApiException apiException) {
            assertEquals(400, apiException.getCode());
        }
    }

    @Test
    public void shouldReturnUserInfoWithPost() throws ApiException {

        filterString = "userName eq \"987611\"";
        ScimResultWrapper response = api.filterPostUsingPOST(getFilterRequestBody(filterString));
        assertEquals("987611", response.getResources().get(0).getUserName());
        assertEquals("mock.user@bisnode.com", response.getResources().get(0).getEmails().get(0).getValue());
        assertEquals("1", response.getTotalResults().toString());
    }

    @Test
    public void shouldNotReturnUserInfoWithPost() throws ApiException {

        filterString = "userName eq \"987612\"";
        ScimResultWrapper response = api.filterPostUsingPOST(getFilterRequestBody(filterString));
        assertEquals("0", response.getTotalResults().toString());
    }

    @Test
    public void shouldReturnBadRequestOnPost() {

        try {
            filterString = "userName e \"987611\"";
            api.filterPostUsingPOST(getFilterRequestBody(filterString));
        } catch (ApiException apiException) {
            assertEquals(400, apiException.getCode());
        }
    }

    @Test
    public void shouldAuthenticateUserWithGet() throws ApiException {

        filterString = "userName eq \"987654\" and password eq \"Password1\" and active eq true";
        ScimResultWrapper response = api.filterUsingGET(filterString);
        assertEquals("987654", response.getResources().get(0).getUserName());
        assertEquals("1", response.getTotalResults().toString());
    }

    @Test
    public void shouldNotAuthenticateUserWithGet() throws ApiException {

        filterString = "userName eq \"987653\" and password eq \"Password1\" and active eq true";
        ScimResultWrapper response = api.filterUsingGET(filterString);
        assertEquals("0", response.getTotalResults().toString());
    }

    @Test
    public void shouldAuthenticateUserWithPost() throws ApiException {

        filterString = "userName eq \"987654\" and password eq \"Password1\" and active eq true";
        ScimResultWrapper response = api.filterPostUsingPOST(getFilterRequestBody(filterString));
        assertEquals("987654", response.getResources().get(0).getUserName());
        assertEquals("1", response.getTotalResults().toString());
    }

    @Test
    public void shouldNotAuthenticateUserWithPost() throws ApiException {

        filterString = "userName eq \"987653\" and password eq \"Password1\" and active eq true";
        ScimResultWrapper response = api.filterPostUsingPOST(getFilterRequestBody(filterString));
        assertEquals("0", response.getTotalResults().toString());
    }

    @Test
    public void shouldChangePassword() throws ApiException {

        User response = api.saveUserUsingPUT("Basic YWRtaW46cGFzc3dvcmQ=", "987652", getUser("987652"));
        assertEquals("987652", response.getUserName());
    }

    @Test
    public void changePasswordShouldReturnInternalError() {

        try {
            api.saveUserUsingPUT("Basic YWRtaW46cGFzc3dvcmQ=", "1", getUser("2"));
        } catch (ApiException apiException) {
            assertEquals(500, apiException.getCode());
        }
    }

    @Test
    public void changePasswordShouldReturnUnauthorized() {

        try {
            api.saveUserUsingPUT("Basic YWRtaW46cGFzc3dvcmQ=", "3", getUser("3"));
        } catch (ApiException apiException) {
            assertEquals(401, apiException.getCode());
        }
    }

    public User getUser(String id) {
        User user = new User();
        user.setId(id);
        user.setPassword("password");
        return user;
    }

    public FilterRequestBody getFilterRequestBody(String filterQuery) {
        FilterRequestBody filterRequestBody = new FilterRequestBody();
        List<String> schemas = Arrays.asList("urn:ietf:params:scim:api:messages:2.0:SearchRequest");
        filterRequestBody.setSchemas(schemas);
        filterRequestBody.setFilter(filterQuery);
        return filterRequestBody;
    }

}
