package com.MeetUp.meetup.screens.home.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.utils.view.ItemDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupHolder> {

    private List<SingleGroupModel> groupList = new ArrayList<>();
    private PublishSubject<Integer> groupPublishSubject = PublishSubject.create();

    public void setGroupList(List<SingleGroupModel> groupList) {
        this.groupList.clear();
        this.groupList.addAll(groupList);
        notifyDataSetChanged();
    }

    @Override
    public GroupHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_view_container, null);
        return new GroupHolder(v, groupPublishSubject);
    }

    @Override
    public void onBindViewHolder(GroupHolder groupHolder, int position) {
        groupHolder.bind(groupList.get(position));
    }

    public Observable<Integer> onGroupClick() {
        return groupPublishSubject;
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    class GroupHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.group_name)
        TextView groupName;
        @BindView(R.id.recyclerView_cards_host)
        RecyclerView recyclerViewGroup;
        CardListAdapter cardListAdapter;
        private PublishSubject<Integer> groupPublishSubject;

        GroupHolder(View view, PublishSubject<Integer> groupPublishSubject) {
            super(view);
            ButterKnife.bind(this, view);
            this.groupPublishSubject = groupPublishSubject;
            initRecycler();
        }

        private void initRecycler() {
            cardListAdapter = new CardListAdapter(groupPublishSubject);
            recyclerViewGroup.setLayoutManager(new LinearLayoutManager
                    (itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewGroup.setAdapter(cardListAdapter);
            recyclerViewGroup.setHasFixedSize(true);
            recyclerViewGroup.addItemDecoration(new ItemDecorator((int) itemView.getContext().getResources()
                    .getDimension(R.dimen.spacing_18_dp)));
        }

        void bind(SingleGroupModel data) {
            groupName.setText(data.getNameGroup());
            cardListAdapter.swapData(data.getGroups());
        }
    }
}
