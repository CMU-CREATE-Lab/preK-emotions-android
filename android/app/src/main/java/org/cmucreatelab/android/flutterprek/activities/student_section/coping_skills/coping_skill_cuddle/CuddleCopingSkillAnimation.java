package org.cmucreatelab.android.flutterprek.activities.student_section.coping_skills.coping_skill_cuddle;

import org.cmucreatelab.android.flutterprek.R;
import android.graphics.Point;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.Random;

public class CuddleCopingSkillAnimation {

    private CuddleCopingSkillActivity cuddleCopingSkillActivity;
    private static final long FADE_DURATION = 500;
    private static final long TRANS_DURATION = 1000;
    private Display display;
    private ImageView heartImageView;
    private ImageView sheepImageView;
    private LinearLayout sheepLayout;
    private Animation fadeIn;
    private GestureDetector gdt;
    private int right;
    private float xPos;
    private boolean animating = false;

    public void startAnimation() {
        // Only animate the heart if it's not being animated already
        if (!animating) {
            animating = true;
            Log.e("Animation", "Starting animation");

            // Set side of the screen
            Random rnd = new Random();
            right = rnd.nextInt(2);

            // Set the position of the heart on screen
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            if (right == 0) {
                xPos = (float) (0.29 * (float) width);
            } else {
                xPos = (float) (0.599 * (float) width);
            }
            float yPos = (float) (0.447 * (float) height);
            heartImageView.setX(xPos);
            heartImageView.setY(yPos);

            // Set the size of the heart
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) heartImageView.getLayoutParams();
            params.width = rnd.nextInt(100) + 100;
            heartImageView.setLayoutParams(params);

            // Set the view to be visible
            heartImageView.setVisibility(View.VISIBLE);

            // Start the fade in animation
            fadeIn = AnimationUtils.loadAnimation(cuddleCopingSkillActivity.getApplicationContext(), R.anim.heart_fade_in);
            heartImageView.startAnimation(fadeIn);

            // Start a countdown timer to move the heart after it fades in
            new CountDownTimer(FADE_DURATION, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    // Move the heart after it has faded in
                    moveHeart();
                }
            }.start();
        }
    }

    public void moveHeart(){
        // Make sure to stop the animation if it's still going
        stopAnimation(heartImageView);

        // Create an animation set for the movement of the heart
        AnimationSet heartMov = new AnimationSet(true);

        //TODO add a rotation?

        // Set up the translation animation
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0, 0.0f, -200f);
        animation.setDuration(TRANS_DURATION);
        animation.setRepeatCount(0);
        heartMov.addAnimation(animation);
        heartMov.setStartOffset(0);

        // Start the animation
        heartImageView.startAnimation(heartMov);

        // Start a countdown timer to fade the heart out once it's done moving
        new CountDownTimer(TRANS_DURATION, 500) {

            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                // Fade heart out
                fadeHeartOut();
            }
        }.start();
    }

    public void fadeHeartOut() {
        // Stop animation if it's not done aready
        stopAnimation(heartImageView);

        // Set the position of the heart to be where it should be after translation
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        float yPos = (float) (0.447 * (float) height) - 200;
        heartImageView.setX(xPos);
        heartImageView.setY(yPos);

        // Find and start the fade out animation
        Animation fadeOut = AnimationUtils.loadAnimation(cuddleCopingSkillActivity.getApplicationContext(),R.anim.heart_fade_out);
        heartImageView.startAnimation(fadeOut);

        // Start a countdown timer to set the heart invisible and release 'animating'
        new CountDownTimer(FADE_DURATION, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                heartImageView.setVisibility(View.INVISIBLE);
                animating = false;
            }
        }.start();
    }

    public void stopAnimation(View v) {
        v.clearAnimation();
        if (canCancelAnimation()) {
            v.animate().cancel();
        }
    }

    public static boolean canCancelAnimation() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public void initAnimation() {
        display = cuddleCopingSkillActivity.getWindowManager().getDefaultDisplay();
        heartImageView = cuddleCopingSkillActivity.findViewById(R.id.heart1);
    }

    private void initSheep(){
        sheepImageView = cuddleCopingSkillActivity.findViewById(R.id.sheep);
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        float xPos = (float) (0.394 * (float) width);
        float yPos = (float) (0.64 * (float) height);

        sheepImageView.setX(xPos);
        sheepImageView.setY(yPos);

        sheepImageView.setVisibility(View.VISIBLE);

        sheepLayout = cuddleCopingSkillActivity.findViewById(R.id.sheepLayout);
    }

    private void initTouch(){
        gdt = new GestureDetector(new GestureListener(cuddleCopingSkillActivity, this));
        sheepLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            } });
    }

    public CuddleCopingSkillAnimation(final CuddleCopingSkillActivity cuddleCopingSkillActivity) {
        this.cuddleCopingSkillActivity = cuddleCopingSkillActivity;

        initAnimation();
        initSheep();
        initTouch();
    }
}


