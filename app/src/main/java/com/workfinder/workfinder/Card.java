package com.workfinder.workfinder;

/**
 * Created by Kim-Christian on 2017-08-31.
 */

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Card extends AppCompatActivity {
    private int id;
    private String subtitle;
    private String title;
    private Drawable thumbnail;
    private String thumbnailText;
    private CardView cardView;
    private int position;

    public Card(LayoutInflater vi) {
        View v = vi.inflate(R.layout.view_card, null);
        this.cardView = (CardView) v.findViewById(R.id.cardView);
        createCardView(this.id, 20);
        setAttr(R.id.cardView, "Subtitle", "Title", "Action1", "Action2", 0);
    }

    public Card() {
        this(null);
    }

    public void setAttr(int id, String subtitle, String title, String action1, String action2, int position) {
        setId(id);
        setSubtitle(subtitle);
        setTitle(title);
        setPosition(position);
    }

    public int getId() {
        return this.id;
    }
    public String getSubtitle() {
        return this.subtitle;
    }
    public String get_title() {
        return this.title;
    }           // getTitle cannot override
    public Drawable getThumbnail() {
        return this.thumbnail;
    }
    public String getThumbnailText() { return this.thumbnailText; }
    public CardView getCardView() { return this.cardView; }
    public int getPosition() { return this.position; }

    public void setId(int new_id) {
        this.id = new_id;
        this.cardView.setId(this.id);
    }
    public void setSubtitle(String new_subtitle) {
        this.subtitle = new_subtitle;
        ((TextView) this.cardView.findViewById(R.id.card_subtitle)).setText(this.subtitle);
    }
    public void setTitle(String new_title) {
        this.title = new_title;
        ((TextView) this.cardView.findViewById(R.id.card_title)).setText(this.title);
    }
    public void setThumbnailText(String new_thumbnail_text) {
        this.thumbnailText = new_thumbnail_text;
        TextView thumbnailText = (TextView) this.cardView.findViewById(R.id.card_thumbnail_text);
        thumbnailText.setText(this.thumbnailText);
    }
    public void setPosition(int new_position) {
        this.position = new_position;
    }

    private void createCardView(int id, int margin){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        lp.setMargins(margin, margin, margin, margin);
        this.cardView.setLayoutParams(lp);
    }
}
