package xplore.xplore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {
    ImageView brand_logo;
    EditText brand_name, brand_location, game_name, game_description;
    Button game_similar, play_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        brand_logo = (ImageView) findViewById(R.id.brand_logo);
        brand_name = (EditText) findViewById(R.id.brand_name);
        brand_location = (EditText) findViewById(R.id.brand_location);
        game_name = (EditText) findViewById(R.id.game_name);
        game_description = (EditText) findViewById(R.id.game_description);
        game_similar = (Button) findViewById(R.id.game_similar);
        play_game = (Button) findViewById(R.id.game_play);


        String brand = getIntent().getStringExtra("brand");
        brand_name.setText(brand);
        int logo = getIntent().getIntExtra("logo",0);
        brand_logo.setImageResource(logo);
    }
}
