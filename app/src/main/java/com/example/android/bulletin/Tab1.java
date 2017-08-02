package com.example.android.bulletin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.uibinder.shared.Article;

import java.util.ArrayList;


public class Tab1 extends Fragment {
    @Nullable

    ArrayList<Article> articles;
    ArticleAdaptor articleAdaptor;
    RecyclerView recyclerView;
    ImageButton retry;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.article_layout, container, false);
        retry=(ImageButton)getActivity().findViewById(R.id.retry);
        Bundle bundle=getArguments();
        String category=bundle.getString("Category");
        recyclerView = (RecyclerView) rootView.findViewById(R.id.article_view_container);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        if(category.equals("Trending")&&Bulletin.tinyDB.getListObject("Trending",Article.class).size()==0){
            Tab1 tab1 = this;
            articleAdaptor = new ArticleAdaptor(0, this, articles);
            recyclerView.setAdapter(articleAdaptor);
            RemoteCall remoteCall=new RemoteCall(this);
            remoteCall.execute();
        }
        else{
            articles = Bulletin.tinyDB.getListObject(category,Article.class);
            articleAdaptor = new ArticleAdaptor(articles.size(), this, articles);
            recyclerView.setAdapter(articleAdaptor);
        }
        return rootView;
    }
}
