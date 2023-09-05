package com.example.notificationapipoc.service;

import com.example.notificationapipoc.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@Slf4j
public class NotificationService {

    private final Map<Integer, Consumer<Notification>> userListeners = new HashMap<>();

    public void notifyUsers(Notification notification) {
        userListeners.forEach(($, listener) -> listener.accept(notification));
        log.info("Notification sent to users");
    }

    public void add(Integer userId, Consumer<Notification> listener) {
        userListeners.put(userId, listener);
    }

}
