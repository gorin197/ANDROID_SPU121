package step.learning.android_spu121;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class OnSwipeListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    public void onSwipeBottom() {

    }

    public void onSwipeLeft() {
    }

    public void onSwipeRight() {
    }

    public void onSwipeTop() {
    }

    public OnSwipeListener(Context context) {
        this.gestureDetector = new GestureDetector(context, new SwipeGestureListener());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.gestureDetector.onTouchEvent(motionEvent);
    }

    private final class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int MIN_SWIPE_DISTANCE = 50;
        private static final int MIN_SWIPE_VELOCITY = 50;

        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            float dx = e2.getX() - e1.getX();
            float dy = e2.getY() - e1.getY();
            if (Math.abs(dx) > Math.abs(dy)) {
                if (Math.abs(dx) >= MIN_SWIPE_DISTANCE && Math.abs(velocityX)  >= MIN_SWIPE_VELOCITY) {
                    if (dx < 0) {
                        onSwipeLeft();
                    } else {
                        onSwipeRight();
                    }
                    result = true;
                }
            } else {
                if (Math.abs(dy) >= MIN_SWIPE_DISTANCE && Math.abs(velocityY)  >= MIN_SWIPE_VELOCITY) {
                    if (dy < 0) {
                        onSwipeTop();
                    } else {
                        onSwipeBottom();
                    }
                    result = true;
                }
            }


            return result;

        }

        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            return true;
        }


    }
}
