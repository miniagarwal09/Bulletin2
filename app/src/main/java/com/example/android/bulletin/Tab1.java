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

import com.uibinder.shared.Article;

import java.util.ArrayList;


public class Tab1 extends Fragment {
    @Nullable

    ArticleAdaptor articleAdaptor;
    RecyclerView recyclerView;
    String category;
    Tab1 tab1;
    ArrayList<Article> articles;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.article_layout, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.article_view_container);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        Bundle b = getArguments();
        category = (String) b.get("Category");
        recyclerView.setLayoutManager(linearLayoutManager);
        if(Bulletin.tinyDB.getListObject(category,Article.class).size()!=0){
            articles = Bulletin.tinyDB.getListObject(category,Article.class);
            Log.d("___a",category);
            Log.d("___a",String.valueOf(Bulletin.tinyDB.getListObject(category, Article.class).size()));
            articleAdaptor = new ArticleAdaptor(articles.size(), this, articles);
            recyclerView.setAdapter(articleAdaptor);
        }
        else
            {
               // Bulletin.tinyDB = new TinyDB(getContext());
                Log.d("___b",category);
                Log.d("___b",String.valueOf(Bulletin.tinyDB.getListObject(category, Article.class).size()));
                tab1 = this;
                articleAdaptor = new ArticleAdaptor(0, this, articles);
                recyclerView.setAdapter(articleAdaptor);
                RemoteCall remoteCall = new RemoteCall(this, category);
                remoteCall.execute("Making Background Remote Call");
            }



        return rootView;
    }
}
