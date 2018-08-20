package com.datahipster.app.web.rest.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Query {

    private QueryChunk chunk;

    public QueryChunk getChunk() {
        return chunk;
    }

    public void setChunk(QueryChunk chunk) {
        this.chunk = chunk;
    }
}
