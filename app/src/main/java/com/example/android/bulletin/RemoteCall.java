package com.example.android.bulletin;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uibinder.client.GreetingService;
import com.uibinder.client.GreetingServiceAsync;
import com.uibinder.shared.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class RemoteCall extends AsyncTask<String,Void,ArrayList<Article>> {


    private Tab1 mainActivity;
    ArrayList<Article> articleArrayList=new ArrayList<Article>();
    boolean onSuccessComplete=false;
    String category="";
    GreetingServiceAsync greetingServiceAsync=Bulletin.greetingServiceAsync;

    public boolean isOnSuccessComplete() {
        return onSuccessComplete;
    }

    public void setOnSuccessComplete(boolean onSuccessComplete) {
        this.onSuccessComplete = onSuccessComplete;
    }

    public ArrayList<Article> getArticleArrayList() {
        return articleArrayList;
    }

    public void setArticleArrayList(ArrayList<Article> articleArrayList) {
        this.articleArrayList = articleArrayList;
    }

    RemoteCall(Tab1 mainActivity,String category) {
        this.mainActivity = mainActivity;
        this.category=category;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<Article> doInBackground(final String... params) {
        Log.d("Remote Call", "Called");
        SyncProxy.setBaseURL("http://192.168.1.8:8080/bulletin2/uibinder/");
        greetingServiceAsync = SyncProxy.create(GreetingService.class);
                if (category.equals("Trending")) {
                    fetch_android(true, greetingServiceAsync);
                } else
                    fetch_android(false, greetingServiceAsync);
                return getArticleArrayList();


    }

    private void fetch_android(final boolean all ,final GreetingServiceAsync greetingServiceAsync) {
        if(all) {
            greetingServiceAsync.fetch_article(new AsyncCallback<ArrayList<Article>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Log.getStackTraceString(throwable);
                }

                @Override
                public void onSuccess(ArrayList<Article> articles) {
                    Log.d("Remote Call Add Article", "Article Fetched");
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
                    setArticleArrayList(articles);
                    setOnSuccessComplete(true);
                    //Log.d("__1",getArticleArrayList().get(0).toString());
                }
            });
        }
        else {
            greetingServiceAsync.fetch_article(category,new AsyncCallback<ArrayList<Article>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Log.getStackTraceString(throwable);
                }

                @Override
                public void onSuccess(ArrayList<Article> articles) {
                    Log.d("Remote Call Add Article", "Article Fetched");
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
                    setArticleArrayList(articles);
                    setOnSuccessComplete(true);
                    //Log.d("__1",getArticleArrayList().get(0).toString());
                }
            });
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        super.onPostExecute(articles);
        while(true) {
            if(isOnSuccessComplete()) {

                Bulletin.tinyDB.putListObject(category, getArticleArrayList());
                mainActivity.articleAdaptor = new ArticleAdaptor(getArticleArrayList().size(), mainActivity, getArticleArrayList());
                mainActivity.recyclerView.setAdapter(mainActivity.articleAdaptor);
                break;
            }
        }
    }
    private void updateUi(){


    }
}