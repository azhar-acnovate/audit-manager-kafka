package com.acnovate.kafka.consumer.AuditManager.rest;

import com.acnovate.kafka.consumer.AuditManager.config.security.RestAPIConfigurator;
import com.acnovate.kafka.consumer.AuditManager.domain.dto.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Optional;

@Service
@Slf4j
public class QiwkApiClient {

    private final String baseURL;

    private final String username;

    private final String password;

    private final RestTemplate restTemplate;

    @Autowired
    public QiwkApiClient(RestAPIConfigurator restAPIConfigurator, RestTemplate restTemplate) {
        this.baseURL = restAPIConfigurator.getBaseURL();
        this.username = restAPIConfigurator.getUsername();
        this.password = restAPIConfigurator.getPassword();
        this.restTemplate = restTemplate;
    }

    /**
     * Helper method to generate HttpHeaders with Basic Authentication.
     *
     * @return HttpHeaders containing Basic Authentication header.
     */
    private HttpHeaders getBasicAuthHeader() {
        // Create the Basic Auth header
        String credentials = username + ":" + password;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);

        return headers;
    }

    /**
     * Retrieves an authentication token by performing a GET request with Basic Authentication.
     *
     * @return The authentication token if successful, or null if an error occurs.
     */
    @Cacheable("authToken")
    public String getAuthToken() {
        final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(getBasicAuthHeader());

        try {
            ResponseEntity<TokenResponse> response = restTemplate.exchange(baseURL + "/security/csrf", HttpMethod.GET, entity, TokenResponse.class);

            return Optional.ofNullable(response.getBody())
                    .map(TokenResponse::getItems)
                    .flatMap(items -> items.stream().findFirst())
                    .map(TokenResponse.Item::getAttributes)
                    .map(TokenResponse.Attributes::getNonce_key)
                    .orElseThrow(() -> new IllegalStateException("Unable to retrieve nonce key from the response."));
        } catch (RestClientException e) {
            log.error("An error occurred while invoking the client. Retrying the event processing.", e);
        }

        return null;
    }


    @CacheEvict({"authToken"})
    public void invalidateAuthToken() {
        // nothing to do here, everything is handled via annotation
    }

    public <T> T getQiwkData(String objectName, String id, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = getBasicAuthHeader();
        headers.add("CSRF_NONCE", getAuthToken());
        final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    baseURL + "/qiwkWebService/getQiwkJSONDataById/" + objectName + "/" + id,
                    HttpMethod.GET,
                    entity,
                    responseType
            );
            return response.getBody();
        } catch (RestClientException e) {
            log.error("An error occurred while invoking the client. Retrying the event processing.", e);
        }

        return null;
    }

}
