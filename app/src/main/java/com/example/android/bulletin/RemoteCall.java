package com.example.android.bulletin;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uibinder.client.GreetingService;
import com.uibinder.client.GreetingServiceAsync;
import com.uibinder.shared.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RemoteCall extends AsyncTask<String,Void,String> {

    Tab1 tab1;
    MyFirebaseInstanceIDService myFirebaseInstanceIDService;
    ArrayList<String> category_list=new ArrayList<String>();

    RemoteCall(Tab1 tab1) {
        this.tab1 = tab1;
    }

    RemoteCall(MyFirebaseInstanceIDService myFirebaseInstanceIDService) {
        this.myFirebaseInstanceIDService = myFirebaseInstanceIDService;
    }


    @Override
    protected String doInBackground(String... params) {

        try {

            SyncProxy.setBaseURL(Bulletin.SERVER_URL + "uibinder/");
        }
        catch (Exception e){
            no_network();
        }
        GreetingServiceAsync greetingServiceAsync = SyncProxy.create(GreetingService.class);

        if (myFirebaseInstanceIDService != null) {
            greetingServiceAsync.add_registration_ids(params[0], new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("___token", throwable.getMessage());
                }

                @Override
                public void onSuccess(String s) {

                    Log.d("___token", s);
                }
            });

        } else {
            category_list.add("National");
            category_list.add("International");
            category_list.add("Sports");
            category_list.add("Entertainment");
            category_list.add("Other");

            greetingServiceAsync.fetch_article(new AsyncCallback<ArrayList<Article>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("___", throwable.getMessage());
                }

                @Override
                public void onSuccess(final ArrayList<Article> articles) {
                    Collections.sort(articles, new Comparator<Article>() {

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
                    Bulletin.tinyDB.putListObject("Trending", articles);
                }
            });

            for(final String category:category_list){
                greetingServiceAsync.fetch_article(category, new AsyncCallback<ArrayList<Article>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d("___",throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(ArrayList<Article> articles) {
                        Collections.sort(articles, new Comparator<Article>() {

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
                        Bulletin.tinyDB.putListObject(category, articles);
                    }
                });
            }
            updateUi();
        }
        return "doInBackground Finished";
    }

    void updateUi(){
        tab1.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    tab1.articles = Bulletin.tinyDB.getListObject("Trending", Article.class);
                if(tab1.articles!=null) {
                    tab1.articleAdaptor = new ArticleAdaptor(tab1.articles.size(), tab1, tab1.articles);
                    tab1.recyclerView.setAdapter(tab1.articleAdaptor);
                }
            }
        });
    }

    void no_network(){
        tab1.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(tab1.getActivity(),"Can't connect to server",Toast.LENGTH_SHORT).show();
                tab1.retry.setVisibility(View.VISIBLE);

            }
        });

    }



}


