package com.Ryan.ToDoMicroservice.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class JsonResponseBody {
    @Setter @Getter
    private int server;
    @Setter @Getter
    private Object object;
    public JsonResponseBody(int server, Object object) {
        this.server = server;
        this.object = object;
    }
}
