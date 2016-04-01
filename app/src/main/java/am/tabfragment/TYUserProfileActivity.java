package am.tabfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TYUserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TYUser user = getIntent().getParcelableExtra("user");

        ImageView imageView = (ImageView) findViewById(R.id.item_icon);
        imageView.setImageResource(user.getImageID());

        //Setting the name of the User
        TextView nameText = (TextView) findViewById(R.id.userName);
        nameText.setText(user.getUsername());

        TextView emailText = (TextView) findViewById(R.id.userEmail);
        emailText.setText(user.getEmail());

        TextView phoneText = (TextView) findViewById(R.id.userPhoneNumber);
        phoneText.setText(user.getPhoneNumber());

    }


}
