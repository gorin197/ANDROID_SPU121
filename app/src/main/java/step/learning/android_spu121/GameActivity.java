package step.learning.android_spu121;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TableLayout gameField = findViewById(R.id.game_field);
        gameField.post(()->{
            int windowWidth = this.getWindow().getDecorView().getWidth();
            int margin=
                    ((TableLayout.LayoutParams)gameField.getLayoutParams()).leftMargin;
            //int fieldSize=windowWidth-2*

        });

        findViewById(R.id.game_layout).setOnTouchListener(
        new OnSwipeListener(GameActivity.this){
            @Override
            public void onSwipeBottom() {
                Toast.makeText(GameActivity.this, "onSwipeBottom", Toast.LENGTH_SHORT).show();
                
            }

            @Override
            public void onSwipeLeft() {
                Toast.makeText(GameActivity.this, "onSwipeLeft", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight() {
                Toast.makeText(GameActivity.this, "onSwipeRight", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeTop() {
                Toast.makeText(GameActivity.this, "onSwipeTop", Toast.LENGTH_SHORT).show();
            }
        }

        );
    }
}