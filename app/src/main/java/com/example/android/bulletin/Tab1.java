package com.example.android.bulletin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uibinder.shared.Article;

import java.util.ArrayList;


public class Tab1 extends Fragment {
    @Nullable

    ArticleAdaptor articleAdaptor;
    RecyclerView recyclerView;
    String category = "";
    Tab1 tab1;
    ArrayList<Article> articles;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        tab1 = this;
        Bundle b = getArguments();
        category = (String) b.get("Category");
        View rootView = inflater.inflate(R.layout.article_layout, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.article_view_container);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        articleAdaptor = new ArticleAdaptor(0, this, articles);
        recyclerView.setAdapter(articleAdaptor);

            RemoteCall remoteCall = new RemoteCall(this, category);
            remoteCall.execute("Making Background Remote Call");





        return rootView;
    }
}
