package com.MeetUp.meetup.screens.home.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.data.db.room.entities.Group;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.SingleCardHolder> {

    private List<Group> cardList = new ArrayList<>();
    private final PublishSubject<Integer> groupPublishSubject;

    CardListAdapter(PublishSubject<Integer> groupPublishSubject) {
        this.groupPublishSubject = groupPublishSubject;
    }


    public void swapData(List<Group> cardList) {
        this.cardList.clear();
        this.cardList.addAll(cardList);
        notifyDataSetChanged();
    }

    @Override
    public SingleCardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group, null);
        return new SingleCardHolder(v);

    }

    @Override
    public void onBindViewHolder(SingleCardHolder singleCardHolder, int position) {
        singleCardHolder.bind(cardList.get(position));
        singleCardHolder.itemView.setOnClickListener(cardPosition ->
                groupPublishSubject.onNext(cardList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    class SingleCardHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardView_name)
        TextView cardName;
        @BindView(R.id.group_image)
        ImageView cardImageId;
        @BindView(R.id.cardView_date)
        TextView cardDate;

        SingleCardHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(Group data) {
            cardName.setText(data.getName());
            cardDate.setText(data.getTimezone());
            if (data.getKeyPhoto() != null) {
                Picasso.get().load(data.getKeyPhoto().getPhotoLink()).into(cardImageId);
            } else {
                cardImageId.setImageResource(R.drawable.ic_group_no_key_photo);
            }
        }
    }
}

