package todo.todoapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yoshikinakamura on 12/5/14.
 */
public class AddActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViewById(R.id.btn_add_todo).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_add_todo:
                EditText editText = (EditText)findViewById(R.id.newTodo);

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
//                HashSet<String> stringHashSet = new HashSet<String>();
                Set<String> stringSet = pref.getStringSet("stringSet",new HashSet<String>());
                HashSet<String> stringHashSet = new HashSet<String>(stringSet.size());
                for(String str : stringSet){
                    stringHashSet.add(str);
                }
                stringHashSet.add(editText.getText().toString());
                editor.putStringSet("stringSet", stringHashSet);
                editor.commit();

                Intent data = new Intent();
                data.putExtra("todo", editText.getText().toString());
                setResult(RESULT_OK, data);

                finish();
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
