package andella.challenge.com.andellachallenge;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

//parse the position that was recieved in the intent to correctly fetch user details
        final int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();
        final String[] user = ListContentFragment.usernameList;
        String name  = user[postion];
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        collapsingToolbar.setTitle(name);

        final String[] profileUrl = ListContentFragment.userProfilePhotoList;
        final TextView profileUrlDetails = (TextView) findViewById(R.id.profile_url_details);
        profileUrlDetails.setText(profileUrl[postion]);

        TextView locationTextView = (TextView) findViewById(R.id.place_location);
        locationTextView.setText(getResources().getString(R.string.detail_location));

        ImageView profilePhoto = (ImageView) findViewById(R.id.image);

        Glide.with(this)
                .load(ListContentFragment.userProfilePhotoList[postion])
                .placeholder(R.drawable.ic_gentest_image)
                .centerCrop()
                .into(profilePhoto);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_detail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(DetailActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);

                String userName =  "@" +  user[postion] ;
                SpannableString coloredName= new SpannableString(userName);
                coloredName.setSpan(new ForegroundColorSpan(Color.GREEN), 0, userName.length(), 0);

                String checkout =  "Check out " ;
                SpannableString checkout1= new SpannableString(checkout);
                checkout1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, checkout.length(), 0);

                String link =  " on github.com" ;
                SpannableString link1= new SpannableString(link);
                link1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, link.length(), 0);

                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(checkout1);
                builder.append(coloredName);
                builder.append(link1);

                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText(builder, TextView.BufferType.SPANNABLE);

                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.mipmap.ic_launcher);

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // launch an implicit intent to view profile in browser
                        String url = ListContentFragment.userProfileUrl[postion];
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

                dialog.show();

            }
        });



    }

}
