package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
    private final int port;

    public InfoService(@Value("${server.port}") int port) {
        this.port = port;
    }

    public int getPort(){
        return port;
    }
}
