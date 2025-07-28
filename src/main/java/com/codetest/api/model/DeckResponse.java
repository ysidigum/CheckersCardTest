package com.codetest.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeckResponse {
    public String deck_id;
    public boolean success;
}