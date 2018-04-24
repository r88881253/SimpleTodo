package com.example.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//import com.example.simpletodo.settings.webview.WebView;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> aToDoAdapter;
    private ListView lvItems;
    private EditText etAddText;
    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 1;
//    ArrayList<String> items;
//    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Simple Todo");
        populateArrayItems();

        // Find reference to those views
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etAddText = (EditText) findViewById(R.id.etAddText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("position", position); // pass arbitrary data to launched activity
                i.putExtra("item", todoItems.get(position).toString());

//                startActivity(i);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }



    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        if(!file.exists()){
            writeItems();
        }
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }catch (IOException e){
            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try{
            FileUtils.writeLines(file, todoItems);
        }catch (IOException e){

        }
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etAddText.getText().toString());
        etAddText.setText("");
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data==null){
            return;
        }
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            int position = data.getIntExtra("position", -1);
            String item = data.getStringExtra("item");

            if(position != -1) {
                todoItems.set(position, item);
                writeItems();
                // Find reference to those views
                lvItems = (ListView) findViewById(R.id.lvItems);
                lvItems.setAdapter(aToDoAdapter);
                etAddText = (EditText) findViewById(R.id.etAddText);
            }

        }
    }


}
