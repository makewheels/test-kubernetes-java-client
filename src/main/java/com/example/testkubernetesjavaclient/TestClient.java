package com.example.testkubernetesjavaclient;

import com.alibaba.fastjson.JSON;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Yaml;

import java.io.File;
import java.util.HashMap;

public class TestClient {
    private static void listPods() throws ApiException {
        CoreV1Api api = new CoreV1Api();
        V1PodList list = api.listPodForAllNamespaces(
                null, null,
                null, null, null,
                null, null,
                null, null, null);
        for (V1Pod item : list.getItems()) {
            V1Container v1Container = item.getSpec().getContainers().get(0);
            System.out.println(JSON.toJSONString(v1Container));
        }
    }

    public static void main(String[] args) throws Exception {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        listPods();

        // Example yaml file can be found in $REPO_DIR/test-svc.yaml
        File file = new File("D:\\workSpace\\intellijidea\\test-kubernetes-java-client\\src\\main\\resources\\a.yaml");
        V1Deployment deployment = (V1Deployment) Yaml.load(file);

        // Deployment and StatefulSet is defined in apps/v1, so you should use AppsV1Api instead of
        // CoreV1API
        AppsV1Api appsV1Api = new AppsV1Api();
        appsV1Api.createNamespacedDeployment("default", deployment,
                null, null, null);

        listPods();
    }
}
