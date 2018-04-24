package com.example.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {

    private EditText etEditText;
//    private TextView etTvLabel;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit Item");
//        etTvLabel = (TextView) findViewById(R.id.etTvLabel);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        String item = intent.getStringExtra("item");

        etEditText = (EditText) findViewById(R.id.etEditText);
        etEditText.setText(item, TextView.BufferType.EDITABLE);
        //put user's cursor at the end of Text
        etEditText.setSelection(etEditText.getText().length());
    }


    public void onEdit(View view) {
        etEditText = (EditText) findViewById(R.id.etEditText);
        Intent data = new Intent();
        data.putExtra("item", etEditText.getText().toString());
        data.putExtra("position", position);

        setResult(RESULT_OK, data);
        finish();
    }
}
