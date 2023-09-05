package com.example.notificationapipoc.controller;

import com.example.notificationapipoc.model.Notification;
import com.example.notificationapipoc.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/notifications")
@Slf4j
@CrossOrigin
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("")
    public void sendNotification(@RequestBody Notification notification) {
        notificationService.notifyUsers(notification);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Object>> createChannel(@PathVariable Integer userId) {
        return Flux.create(fluxSink -> notificationService.add(userId, notification -> {
            if (notification.getUserIds().contains(userId)) {
                fluxSink.next(notification.getText());
            }
        })).map(notification -> ServerSentEvent.builder().data(notification).build());
    }

}
