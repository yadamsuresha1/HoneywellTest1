package com.honeywelltest;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DescriptionFragment extends Fragment implements View.OnClickListener {

    private ImageView userImage;
    private TextView fName, lName;
    private ImageView fNameEdit, lNameEdit;
    private int position;
    private Item item;
    private List<Item> itemsList;

    public static DescriptionFragment newInstance(int position) {
        DescriptionFragment descriptionFragment = new DescriptionFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        descriptionFragment.setArguments(args);

        return descriptionFragment;
    }

    public void updateList(List<Item> itemList) {
        this.itemsList = itemList;
    }


    public ListemItemsListener listemItemsListener;

    public void setListUpdateListener(ListemItemsListener listUpdateListener) {
        this.listemItemsListener = listUpdateListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.description_layout, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
            Log.d("suresh", "position: " + position);
            item = HoneywellUtils.getInstance().getItemsList().get(position);
        }

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        userImage = view.findViewById(R.id.userImage);

        fNameEdit = view.findViewById(R.id.editFName);
        lNameEdit = view.findViewById(R.id.editLName);

        fNameEdit.setOnClickListener(this);
        lNameEdit.setOnClickListener(this);

        fName = view.findViewById(R.id.fNameDes);
        lName = view.findViewById(R.id.lNameDes);

        Picasso.get().load(item.getAvatar()).into(userImage);

        fName.setText(item.getfName());
        lName.setText(item.getlName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editFName:
                openEditDialog(0, item.getfName());
                break;
            case R.id.editLName:
                openEditDialog(1, item.getlName());
                break;
        }
    }


    private void openEditDialog(final int whichData, String editContent) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.edit_dialog);
        dialog.setCanceledOnTouchOutside(false);

        final EditText editData = dialog.findViewById(R.id.editingData);
        editData.setText(editContent);

        Button save = dialog.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editData.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter data", Toast.LENGTH_SHORT).show();
                } else {

                    String updatedName = editData.getText().toString();

                    if (whichData == 0) {
                        //first name
                        item.setfName(updatedName);
                        fName.setText(updatedName);
                    } else if (whichData == 1) {
                        String updatedLName = editData.getText().toString();
                        item.setlName(updatedName);
                        lName.setText(updatedName);
                    }

                    Toast.makeText(getActivity(), "Data updated!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}
