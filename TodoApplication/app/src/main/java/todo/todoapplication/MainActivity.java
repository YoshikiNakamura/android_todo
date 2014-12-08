package todo.todoapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import static android.widget.Toast.LENGTH_SHORT;


public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"#onCreate called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        Log.v(TAG,"#onPause called.");
        super.onPause();
    }

    @Override
    protected void onStart() {
        Log.v(TAG,"#onStart called.");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG,"#onDestroy called.");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.v(TAG,"#onStop called.");
        super.onStop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.v(TAG,"#onWindowFocusChanged called.");
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onResume() {
        Log.v(TAG,"#onResume called.");
        super.onResume();
        ListView listview = (ListView)findViewById(R.id.listView);

           SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Set<String> stringSet = pref.getStringSet("stringSet",new HashSet<String>());
        HashSet<String> stringHashSet = new HashSet<String>(stringSet.size());
        for(String str : stringSet){
            stringHashSet.add(str);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for (String str:stringHashSet){
            adapter.add(str);
        }
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>parent, View view, int position, long id){
                ListView listView = (ListView)parent;
                String item = (String)listView.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, item + "を消したよ", Toast.LENGTH_LONG).show();
                adapter.remove(item);
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                Set<String> stringSet = pref.getStringSet("stringSet",new HashSet<String>());
                HashSet<String> stringHashSet = new HashSet<String>(stringSet.size());
                for(String str : stringSet){
                    stringHashSet.add(str);
                }
                stringHashSet.remove(item);
                editor.putStringSet("stringSet", stringHashSet);
                editor.commit();


            }
        });
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
        switch (id){
            case R.id.action_settings:
                return true;
            case R.id.action_add_todo:
                Intent intent = new Intent(this, AddActivity.class);
                int requestCode = 123;
                startActivityForResult(intent, requestCode);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){return;}
        Bundle bundle = data.getExtras();

        switch (requestCode){
            case 123:
                if(resultCode == RESULT_OK){
                    Log.v(TAG, bundle.getString("todo"));
                    Toast.makeText(this, bundle.getString("todo") + "を追加したよ", LENGTH_SHORT).show();

                }else if(resultCode == RESULT_CANCELED){
                    Log.v(TAG,"#123NG");

                }
            break;

        }
    }
}
