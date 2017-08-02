package com.example.android.bulletin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.uibinder.shared.Article;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;



public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            buildNotification(remoteMessage);
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            ArrayList<Article> old_trending,old_category;
            Bulletin.tinyDB=new TinyDB(getApplicationContext());
            Article article=create_article(remoteMessage);
            old_trending =Bulletin.tinyDB.getListObject("Trending",Article.class);
            old_category=Bulletin.tinyDB.getListObject(article.getCategory(),Article.class);
            old_trending.add(article);
            old_category.add(article);
            sort_articles(old_trending);
            sort_articles(old_category);
            Bulletin.tinyDB.putListObject("Trending",old_trending);
            Bulletin.tinyDB.putListObject(article.getCategory(),old_category);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }






    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, Bulletin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    ArrayList<String> stringToList(String tags){
        tags=tags.replace("[","");
        tags=tags.replace("]","");
        return new ArrayList<String>(Arrays.asList(tags.split("\\s*,\\s*")));
    }
    void buildNotification(RemoteMessage remoteMessage){
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("text"))
                .setContentIntent(PendingIntent.getActivity(this,0,new Intent(this,Bulletin.class),0))
                .setSmallIcon(R.drawable.bulls)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(123, notification);
    }
    ArrayList<Article> sort_articles(ArrayList<Article> articles){
        Collections.sort(articles, new Comparator<Article>() {
            //TODO complete this
            @Override
            public int compare(Article o1, Article o2) {
                // TODO Auto-generated method stub
                if (Long.parseLong(o1.getTime()) > Long.parseLong(o2.getTime())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return articles;

    }
    Article create_article(RemoteMessage remoteMessage){
        Article article=new Article();
        article.setTitle(remoteMessage.getData().get("title"));
        article.setImage(remoteMessage.getData().get("image"));
        article.setCategory(remoteMessage.getData().get("category"));
        article.setMtext(remoteMessage.getData().get("text"));
        article.setTime(remoteMessage.getData().get("time"));
        String tags=remoteMessage.getData().get("tags");
        article.setTags(stringToList(tags));
        article.setLink(remoteMessage.getData().get("link"));
        return article;

    }
}