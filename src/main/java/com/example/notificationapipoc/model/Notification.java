package com.example.notificationapipoc.model;

import lombok.Data;

import java.util.List;

@Data
public class Notification {

    private List<Integer> userIds;

    private String text;

}
