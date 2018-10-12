package com.honeywelltest;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DescriptionActivity extends Activity implements View.OnClickListener {


    private ImageView userImage;
    private TextView fName, lName;
    private ImageView fNameEdit, lNameEdit;
    private int position;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_layout);


        position = getIntent().getIntExtra("position", 0);

        item = HoneywellUtils.getInstance().getItemsList().get(position);

        Log.d("suresh", "position: " + position);

        initViews();


    }

    private void initViews() {
        userImage = findViewById(R.id.userImage);

        fNameEdit = findViewById(R.id.editFName);
        lNameEdit = findViewById(R.id.editLName);

        fNameEdit.setOnClickListener(this);
        lNameEdit.setOnClickListener(this);

        fName = findViewById(R.id.fNameDes);
        lName = findViewById(R.id.lNameDes);

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

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_dialog);
        dialog.setCanceledOnTouchOutside(false);

        final EditText editData = dialog.findViewById(R.id.editingData);
        editData.setText(editContent);

        Button save = dialog.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editData.getText().toString().length() == 0) {
                    Toast.makeText(DescriptionActivity.this, "Please enter data", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(DescriptionActivity.this, "Data updated!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

}
