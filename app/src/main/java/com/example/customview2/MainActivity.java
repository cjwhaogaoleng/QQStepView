package com.example.customview2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QQStepView qqStepView = findViewById(R.id.step_view);
        qqStepView.setMaxStep(8000);

        //属性动画
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 7000);
        animator.setDuration(3000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
//                animation.getValues();
                float currentStep = (float) animation.getAnimatedValue();
                qqStepView.setCurrentStep((int) currentStep);
            }
        });

        animator.start();

    }
}