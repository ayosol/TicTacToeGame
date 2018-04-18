package com.example.android.tic_tac_toegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        findViewById(R.id.single_player).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "Single Player Button pressed!");
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("gameType", true);
                startActivity(intent);
            }
        });

        findViewById(R.id.two_player).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "Two Player Button pressed!");
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("gameType", false);
                startActivity(intent);
            }
        });

//        findViewById(R.id.about).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public boolean choose_turn(View view) {
        //Is the button now checked?
        boolean checked_button = ((RadioButton) view).isChecked();

        //Check for which radiobutton was checked
        switch (view.getId()) {
            case R.id.player1:
                if (checked_button)
                    return true;
                break;
            case R.id.player2:
                if (checked_button)
                    return false;
                break;
        }
        return checked_button;
    }


}

