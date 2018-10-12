package com.honeywelltest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    public Context context;
    public List<Item> itemList;
    public UsersListFragment activityContext;
    public ListemItemsListener listemItemsListener;

    public ItemsAdapter(Context context, UsersListFragment activityContext,ListemItemsListener listemItemsListener, List<Item> itemList) {
        this.context = context;
        this.activityContext = activityContext;
        this.itemList = itemList;
        this.listemItemsListener = listemItemsListener;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_row, viewGroup, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int i) {
        Picasso.get().load(itemList.get(i).getAvatar()).into(itemsViewHolder.getPhotoId());
        itemsViewHolder.getfName().setText(itemList.get(i).getfName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView fName;
        public ImageView photoId;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            fName = itemView.findViewById(R.id.fName);
            photoId = itemView.findViewById(R.id.userImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent descriptionIntent = new Intent(activityContext.getActivity(), DescriptionActivity.class);
//                    descriptionIntent.putExtra("position", getAdapterPosition());
//                    activityContext.startActivity(descriptionIntent);

                    DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(getAdapterPosition());
                    descriptionFragment.setListUpdateListener(listemItemsListener);

                    activityContext.getActivity().getFragmentManager()
                            .beginTransaction()
                            .add(R.id.container, descriptionFragment, null)
                            .addToBackStack(null)
                            .commit();

                }
            });
        }

        public TextView getfName() {
            return fName;
        }

        public void setfName(TextView fName) {
            this.fName = fName;
        }

        public ImageView getPhotoId() {
            return photoId;
        }

        public void setPhotoId(ImageView photoId) {
            this.photoId = photoId;
        }
    }
}
