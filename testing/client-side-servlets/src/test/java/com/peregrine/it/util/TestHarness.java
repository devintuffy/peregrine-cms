package com.peregrine.it.util;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.sling.testing.clients.ClientException;
import org.apache.sling.testing.clients.SlingClient;
import org.apache.sling.testing.clients.SlingHttpResponse;
import org.apache.sling.testing.clients.util.FormEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by schaefa on 6/28/17.
 */
public class TestHarness {

    public static final String ADMIN_PREFIX_URL = "/api/admin/";

    private static final Logger logger = LoggerFactory.getLogger(TestHarness.class.getName());

    public static SlingHttpResponse deleteFolder(SlingClient client, String path, int expectedStatus) throws ClientException, IOException {
        String url = ADMIN_PREFIX_URL + "deleteNode.json" + path;
        logger.info("Delete Folder with URL: '{}' and Name: '{}'", url);
        HttpEntity formEntry = FormEntityBuilder.create().build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse deleteNode(SlingClient client, String path, int expectedStatus) throws ClientException, IOException {
//        String url = ADMIN_PREFIX_URL + "deleteNode.json" + path;
//        logger.info("Delete Node with URL: '{}' and Name: '{}'", url);
//        HttpEntity formEntry = FormEntityBuilder.create().build();
//        return client.doPost(url, formEntry, expectedStatus);
        return deleteNode(client, path, null, expectedStatus);
    }

    public static SlingHttpResponse deleteNode(SlingClient client, String path, String type, int expectedStatus) throws ClientException, IOException {
        String url = ADMIN_PREFIX_URL + "deleteNode.json" + path;
        logger.info("Delete Node with URL: '{}' and Name: '{}'", url);
        HttpEntity formEntry = null;
        if(type == null) {
            formEntry = FormEntityBuilder.create().build();
        } else {
            formEntry = FormEntityBuilder.create().addParameter("type", type).build();
        }
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse deletePage(SlingClient client, String path, int expectedStatus) throws ClientException, IOException {
        String url = ADMIN_PREFIX_URL + "deletePage.json" + path;
        logger.info("Delete Page with URL: '{}' and Name: '{}'", url);
        HttpEntity formEntry = FormEntityBuilder.create().build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse createPage(SlingClient client, String path, String name, String templatePath, int expectedStatus) throws ClientException {
        String url = ADMIN_PREFIX_URL + "createPage.json" + path;
        HttpEntity formEntry = FormEntityBuilder.create().addParameter("name", name).addParameter("templatePath", templatePath).build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse createObject(SlingClient client, String path, String name, String templatePath, int expectedStatus) throws ClientException {
        String url = ADMIN_PREFIX_URL + "createObject.json" + path;
        HttpEntity formEntry = FormEntityBuilder.create().addParameter("name", name).addParameter("templatePath", templatePath).build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse createTemplate(SlingClient client, String path, String name, String component, int expectedStatus) throws ClientException {
        String url = ADMIN_PREFIX_URL + "createTemplate.json" + path;
        HttpEntity formEntry = FormEntityBuilder.create().addParameter("name", name).addParameter("component", component).build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse moveResource(SlingClient client, String from, String to, String type, int expectedStatus) throws ClientException {
        String url = ADMIN_PREFIX_URL + "move.json" + from;
        HttpEntity formEntry = FormEntityBuilder.create().addParameter("to", to).addParameter("type", type).build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse renameResource(SlingClient client, String from, String to, int expectedStatus) throws ClientException {
        String url = ADMIN_PREFIX_URL + "rename.json" + from;
        HttpEntity formEntry = FormEntityBuilder.create().addParameter("to", to).build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse moveNodeToResource(SlingClient client, String from, String to, String type, int expectedStatus) throws ClientException {
        String url = ADMIN_PREFIX_URL + "moveNodeTo.json" + to;
        HttpEntity formEntry = FormEntityBuilder.create().addParameter("component", from).addParameter("type", type).build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse insertNodeAtAsComponent(SlingClient client, String path, String component, String type, int expectedStatus) throws ClientException, IOException {
        String url = ADMIN_PREFIX_URL + "insertNodeAt.json" + path;
        HttpEntity formEntry = FormEntityBuilder.create().addParameter("component", component).addParameter("type", type).build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse insertNodeAtAsContent(SlingClient client, String path, String content, String type, int expectedStatus) throws ClientException, IOException {
        String url = ADMIN_PREFIX_URL + "insertNodeAt.json" + path;
        HttpEntity formEntry = FormEntityBuilder.create().addParameter("content", content).addParameter("type", type).build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse executeReplication(SlingClient client, String path, String name, int expectedStatus) throws ClientException, IOException {
        String url = ADMIN_PREFIX_URL + "repl.json" + path;
        HttpEntity formEntry = FormEntityBuilder.create().addParameter("name", name).build();
        return client.doPost(url, formEntry, expectedStatus);
    }

    public static SlingHttpResponse uploadFile(SlingClient client, String path, String name, byte[] content, int expectedStatus) throws ClientException, IOException {
        String url = ADMIN_PREFIX_URL + "uploadFiles.json" + path;
//        HttpEntity formEntry = FormEntityBuilder.create().addParameter("name", name).build();
        HttpEntity entity = MultipartEntityBuilder.create()
            .addBinaryBody(name, content, ContentType.create("application/octet-stream"), name)
            .build();
        // return the sling response
        return client.doPost(url, entity, expectedStatus);
    }

    public static SlingHttpResponse updateResource(SlingClient client, String path, String content, int expectedStatus) throws ClientException, IOException {
        String url = ADMIN_PREFIX_URL + "updateResource.json" + path;
        HttpEntity formEntry = FormEntityBuilder.create().addParameter("content", content).build();
        return client.doPost(url, formEntry, expectedStatus);
    }
}
