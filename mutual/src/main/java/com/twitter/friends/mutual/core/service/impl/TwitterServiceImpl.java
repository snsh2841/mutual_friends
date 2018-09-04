package com.twitter.friends.mutual.core.service.impl;

import com.twitter.friends.mutual.core.service.TwitterService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.AccessToken;

@Service
public class TwitterServiceImpl implements TwitterService {

    @Value("${twitter.consumer.key}")
    String twitterConsumerkey;

    @Value("${twitter.consumer.secret}")
    String twitterConsumerSecret;

    @Value("${twitter.token.key}")
    String twitterTokenKey;

    @Value("${twitter.token.secret}")
    String twitterTokenSecret;

    public List<String> getFriendsList(){

        Twitter twitter = new TwitterFactory().getInstance();
        // Twitter Consumer key & Consumer Secret
        twitter.setOAuthConsumer(twitterConsumerkey, twitterConsumerSecret);
        // Twitter Access token & Access token Secret
        twitter.setOAuthAccessToken(new AccessToken(twitterTokenKey,
                twitterTokenSecret));
        try {

            User u1 = null ;
            long cursor = -1;
            PagableResponseList<User> response;
            System.out.println("Listing followers's ids.");
            List users = new ArrayList(0);

            do {
                 response = twitter.getFriendsList("snsh2841", cursor, 100);

                response.forEach(user -> users.add(user) );

            } while ((cursor = response.getNextCursor()) != 0);

            System.out.println("Total friends "+users.size());
    } catch (TwitterException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(0);
    }

    public static void main(String args[]){

            new TwitterServiceImpl().getFriendsList();
    }
}