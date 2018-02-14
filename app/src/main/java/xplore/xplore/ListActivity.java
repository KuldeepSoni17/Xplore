package xplore.xplore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    TextView logout, user;
    Animation slide_in_left, slide_out_right;
    Animation slide_in_right, slide_out_left;
    int[] images = {R.drawable.offer, R.drawable.offer1, R.drawable.offer2};

    ListView lv;
    int[] logo = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    String[] names = {"brand", "brand1", "brand2", "brand3"};
    String[] offers = {"offer", "offer1", "offer2", "offer3"};
    AAdapter aAdapter;
    ArrayList<Mainlist> mainlists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().hide();

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        logout = (TextView) findViewById(R.id.logout);
        user = (TextView) findViewById(R.id.user);


        SharedPreferences preferences = getApplication().getSharedPreferences("login", MODE_PRIVATE);
        String name = preferences.getString("username", "");

        user.setText(name);

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(images[i]);
            viewFlipper.addView(imageView);
        }

        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        //Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        viewFlipper.setInAnimation(in);
        //viewFlipper.setOutAnimation(out);

        viewFlipper.setFlipInterval(10000);
        viewFlipper.setAutoStart(true);

        slide_in_left = AnimationUtils.loadAnimation(this,
                R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this,
                R.anim.slide_out_right);

        slide_in_right = AnimationUtils.loadAnimation(this,
                R.anim.slide_in_right);
        slide_out_left = AnimationUtils.loadAnimation(this,
                R.anim.slide_out_left);

        lv = (ListView) findViewById(R.id.list);
        mainlists = new ArrayList<>();

        for (int i = 0; i < logo.length; i++) {
            Mainlist mainlist = new Mainlist();
            mainlist.setImages(String.valueOf(logo[i]));
            mainlist.setNames(names[i]);
            mainlist.setOffers(offers[i]);
            mainlists.add(mainlist);

            aAdapter = new AAdapter(ListActivity.this, mainlists);
            lv.setAdapter(aAdapter);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getApplication().getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("username");
                editor.commit();
                startActivity(new Intent(ListActivity.this, MainActivity.class));
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, GameActivity.class);
                String b_name = names[position];
                String b_logo = String.valueOf(logo[position]);
                intent.putExtra("brand", b_name);
                intent.putExtra("logo", logo[position]);
                startActivity(intent);
            }
        });
    }

    private void SwipeRight() {
        viewFlipper.setInAnimation(slide_in_right);
        viewFlipper.setOutAnimation(slide_out_right);
        viewFlipper.showPrevious();
    }

    private void SwipeLeft() {
        viewFlipper.setInAnimation(slide_in_left);
        viewFlipper.setOutAnimation(slide_out_left);
        viewFlipper.showNext();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return gestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener
            = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            float sensitvity = 50;
            if ((e1.getX() - e2.getX()) > sensitvity) {
                SwipeLeft();
            } else if ((e2.getX() - e1.getX()) > sensitvity) {
                SwipeRight();
            }

            return true;
        }

    };

    GestureDetector gestureDetector = new GestureDetector(simpleOnGestureListener);


}
