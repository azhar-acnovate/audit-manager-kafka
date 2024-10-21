package com.acnovate.kafka.consumer.AuditManager.domain.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TokenResponse {
    private List<Item> items;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Getter
    @Setter
    public static class Item {
        private String id;
        private Attributes attributes;

    }

    @Getter
    @Setter
    public static class Attributes {
        private String nonce_key;
        private String nonce;

    }
}

