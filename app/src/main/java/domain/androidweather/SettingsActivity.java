package domain.androidweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class SettingsActivity extends ActionBarActivity {

    private SharedPreferences preferences;
    private EditText textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getPreferences(MODE_PRIVATE);

        textField = (EditText)findViewById(R.id.settings_town);

        if(preferences.contains("Town")) {
            textField.setText(preferences.getString("Town", null));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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


    public void onSaveButtonClick(View v) {
        String townValue = textField.getText().toString();
        Intent intent = new Intent(SettingsActivity.this, TestActivity.class);
        intent.putExtra("Town", townValue);
        startActivity(intent);
        saveSettings(new Pair<>("Town", townValue));
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
    }

    public void saveSettings(Pair<String, String> settings) {
        preferences.edit().putString(settings.first, settings.second).apply();
    }



}
