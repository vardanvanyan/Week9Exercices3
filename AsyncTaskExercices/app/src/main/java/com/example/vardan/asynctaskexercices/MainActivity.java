package com.example.vardan.asynctaskexercices;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String KAY = "key";
    public static final String KAY_FR = "key_fr";
    private EditText editText;
    private TextView sleeped_for;
    private TextView textView2;
    private TextView text_seconds;
    private AsyncDialogFragment dialogFragment;
    private FragmentManager fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_text);
        sleeped_for = findViewById(R.id.sleeped);
        textView2 = findViewById(R.id.num);
        text_seconds = findViewById(R.id.text_seconds);
        dialogFragment = new AsyncDialogFragment();
        fragment = this.getFragmentManager();
        final Button button = findViewById(R.id.button);
        enterProgress(button);
    }

    private void enterProgress(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressAsyncTask myTask = new ProgressAsyncTask();
                if (!editText.getText().toString().isEmpty()) {
                    myTask.execute();
                } else {
                    Toast.makeText(MainActivity.this, "type number", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    class ProgressAsyncTask extends AsyncTask<Void, Void, Void> {
        private String second = editText.getText().toString();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                TimeUnit.SECONDS.sleep(Integer.valueOf(second));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            final Bundle bundle = new Bundle();
            bundle.putString(KAY_FR, second);
            dialogFragment.setArguments(bundle);
            dialogFragment.show(fragment, KAY);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            sleeped_for.setVisibility(View.VISIBLE);
            text_seconds.setVisibility(View.VISIBLE);
            textView2.setText(second);
            dialogFragment.dismiss();
        }
    }
}
