package am.tabfragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {
    SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        manager = new SessionManager(Splash.this);
        Thread background = new Thread(){
            public void run(){
                try{
                    sleep(3000);

                    Boolean loginStatus = manager.checkLoginStatus();
                    if(loginStatus){
                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(Splash.this, RGLoginActivity.class);
                        startActivity(intent);
                    }

                    finish();
                }catch (Exception e){

                }
            }
        };

        background.start();
    }
}
