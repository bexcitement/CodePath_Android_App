package com.bexcitement.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ToDOActivity extends Activity {
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private int itemPos;
	private final int REQUEST_CODE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(todoAdapter);
		setupListViewListener();
	}

	private void setupListViewListener() {
		// Delete items
		// Setting up event listener 
		// attaches this listener to an item and does a callback
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int pos, long id) {
				// pos is the index that we want to remove from the array list
				todoItems.remove(pos);
				// notifying the adapter that the array data has changed
				todoAdapter.notifyDataSetChanged();
				writeItems(); // whenever you delete an item, write it to the file
				return true;
			}
		});
		
		// Navigating to new page
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View item, int pos,
					long id) {
				// TODO Auto-generated method stub
				itemPos = pos;
				launchEditView();	
			}
		});
		
	}
	
	public void launchEditView() {
		Intent i = new Intent(ToDOActivity.this, EditItemActivity.class);
		i.putExtra("name", todoItems.get(itemPos));   
		startActivityForResult(i, REQUEST_CODE); // brings up the second activity
	}
	
	public void onAddedItem(View v) {
		String itemText = etNewItem.getText().toString(); // getting text from edit text section
		todoAdapter.add(itemText); // adding item to list view
		etNewItem.setText(""); // clearing out edit text section
		writeItems(); // whenever you add a new item, write it to the file
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract name value from result extras
	     String name = data.getExtras().getString("name");
	     todoItems.set(itemPos,name);
	     todoAdapter.notifyDataSetChanged();
	     writeItems();
	  }
	} 
	
	private void readItems() {
		// get access to a file from Android
		// below gives the absolute path for files to be created for Android
		File filesDir = getFilesDir();
		// creating a new file and naming it todo.txt
		File todoFile = new File(filesDir, "todo.txt");
		try {
			// try to load files using this strategy
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch(IOException e) {
			// otherwise create a new array list
			todoItems= new ArrayList<String>();
		}
		
	}
	
	private void writeItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			// writes our array todoItems into our txt file
			FileUtils.writeLines(todoFile, todoItems);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

}
