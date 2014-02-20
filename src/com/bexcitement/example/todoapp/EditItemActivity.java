package com.bexcitement.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends Activity {
	private String name;
	private EditText editItem;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		editItem = (EditText) findViewById(R.id.editItem);
		name = getIntent().getStringExtra("name");
		editItem.setText(name, TextView.BufferType.EDITABLE);
		editItem.setSelection(editItem.getText().length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}
	
	public void onSubmit(View v) {
		  // closes the activity and returns to first screen
		  EditText newItem = (EditText) findViewById(R.id.editItem);
		  Intent data = new Intent();
		  data.putExtra("name", newItem.getText().toString());
		  setResult(RESULT_OK, data);
		  this.finish(); 
	}

}
