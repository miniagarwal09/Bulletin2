package com.example.android.bulletin;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uibinder.shared.Article;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


class ArticleAdaptor extends RecyclerView.Adapter<ArticleAdaptor.ArticleViewHolder>{

    private int numberItems;
    Tab1 mainActivity;
    ArrayList<Article> articles;

    ArticleAdaptor(int items, Tab1 mainActivity, ArrayList<Article> articles){
        this.articles=articles;
        numberItems=items;
        this.mainActivity=mainActivity;

    }


    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_article_layout,parent,false);
        return new ArticleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView imageView;
        TextView text;
        Button url;



        ArticleViewHolder(View view){
            super(view);
            title=(TextView)view.findViewById(R.id.title);
            imageView=(ImageView)view.findViewById(R.id.article_image);
            url=(Button)view.findViewById(R.id.url);
            text=(TextView)view.findViewById(R.id.text);
        }
        void bind(final int number){
     /*       new Thread(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        URL url = null;
                        try {
                            url = new URL("http://192.168.1.8:8080/bulletin2/"+articles.get(number).getImage());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        Bitmap bmp = null;
                        try {
                            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        imageView.setImageBitmap(bmp);


                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
*/




            if(!articles.get(number).getTitle().equals(""))
            title.setText(articles.get(number).getTitle());
            if(!articles.get(number).getMtext().equals(""))
            text.setText(articles.get(number).getMtext());
            url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url=articles.get(number).getLink();
                    Intent intent=new Intent(Intent.ACTION_VIEW);

                    intent.setData(Uri.parse(url));
                    mainActivity.startActivity(intent);

                }
            });
        }
    }
}
