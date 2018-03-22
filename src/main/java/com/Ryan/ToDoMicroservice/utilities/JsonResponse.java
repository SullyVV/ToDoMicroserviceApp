package com.Ryan.ToDoMicroservice.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class JsonResponse {
    @Setter @Getter
    private int server;
    @Setter @Getter
    private Object object;
}
