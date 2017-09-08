package bodor.com.stopwatchmillseconds;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button start,pause,lap;
    private TextView timer;
    Handler customHandler = new Handler();
    LinearLayout container;
     long startTime = 0L; long timeInMillSeconds = 0L; long timeSwapBuff = 0L; long upDateTime = 0L;

    Runnable upDateTimeThread = new Runnable() {
        @Override
        public void run() {
            timeInMillSeconds = SystemClock.uptimeMillis() - startTime;
            upDateTime = timeSwapBuff + timeInMillSeconds;
            int secs = (int)(upDateTime/1000);
            int mins = secs/60;
            secs  = secs%60;
            int millsecond =(int)(upDateTime%1000);
            timer.setText(mins+":"+String.format("%02d",secs) +":"+String.format("%03d",millsecond));
            customHandler.postDelayed(this,0);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        pause = (Button) findViewById(R.id.pause);
        lap = (Button) findViewById(R.id.lap);
        timer = (TextView) findViewById(R.id.timer);
        container = (LinearLayout) findViewById(R.id.container);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();

                customHandler.postDelayed(upDateTimeThread,0);
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff += timeInMillSeconds;
                customHandler.removeCallbacks(upDateTimeThread);
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row = layoutInflater.inflate(R.layout.row,null);
                TextView textView = row.findViewById(R.id.lap_text);
                textView.setText(timer.getText());
                container.addView(row);
            }
        });



    }



}
