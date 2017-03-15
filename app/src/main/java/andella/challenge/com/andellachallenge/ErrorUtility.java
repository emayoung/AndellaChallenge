package andella.challenge.com.andellachallenge;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ErrorUtility extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_utility);

        Button retryBut = (Button) findViewById(R.id.retry_button);
        retryBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                check for network connection before retrying to prevent unneccessary shutter
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()){
//                    lanch mainactivity again and remove error utility from the back stack
                    Intent intent = new Intent(ErrorUtility.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
//
            }
        });
    }



}
