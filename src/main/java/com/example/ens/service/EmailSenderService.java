package com.example.ens.service;

public interface EmailSenderService {
    void send(String to, String subject, String body);
}
