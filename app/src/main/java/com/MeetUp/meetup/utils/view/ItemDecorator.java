package com.MeetUp.meetup.utils.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecorator extends RecyclerView.ItemDecoration {

    private final int spacing;

    public ItemDecorator(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildViewHolder(view).getAdapterPosition();
        int itemCount = state.getItemCount();

        outRect.bottom = spacing;
        outRect.top = spacing;
        outRect.right = position == itemCount - 1 ? spacing : 0;
        outRect.left = spacing;

    }
}
