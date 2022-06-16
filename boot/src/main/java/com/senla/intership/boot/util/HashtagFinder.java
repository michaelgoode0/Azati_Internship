package com.senla.intership.boot.util;

import com.senla.intership.boot.entity.Hashtag;

import java.util.ArrayList;
import java.util.List;

public class HashtagFinder {

    public static List<Hashtag> findHashtag(String postText) {
        String[] items = postText.split("/n");
        List<Hashtag> hashtags = new ArrayList<>();
        for (String item : items) {
            if (!item.equals("")) {
                String[] values = item.split(" ");
                for (String value : values) {
                    if (value.startsWith("#")) {
                        Hashtag hashtag = new Hashtag();
                        hashtag.setValue(value);
                        hashtags.add(hashtag);
                    }
                }
            }
        }
        return hashtags;
    }
}
