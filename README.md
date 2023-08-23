# QQStepView
模拟qq计步器做出来的view，环形进度条
 ## 效果展示
https://github.com/cjwhaogaoleng/QQStepView/assets/117556474/4928ed73-5f8e-4a7d-93c3-90547dd26ca9

 ## 引入依赖
暂未打包，后面可以导成依赖供考核使用
 ## 源码位置
/app/src/main/java/com/example/qqstepview/QQStepView.java

/app/src/main/java/com/example/qqstepview/QQStepViewK.kt
 ## 代码讲解
  ### JAVA
```
qqStepView.setMaxStep(8000);
        //设置属性动画，使其动起来
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 7000);
        animator.setDuration(3000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                float currentStep = (float) animation.getAnimatedValue();
                qqStepView.setCurrentStep((int) currentStep);
            }
        });

        animator.start();
```
  ### KOTLIN
```
qqStepView.setMaxStep(8000)
        //属性动画
        val animator = ObjectAnimator.ofFloat(0f, 7000f)
        animator.duration = 3000
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation -> 
            val currentStep = animation.animatedValue as Float
            qqStepView.setCurrentStep(currentStep.toInt())
        }
        animator.start()
```
 ## 实现方法
 ```
 //设置最大步数（满环的步数）
 fun setMaxStep(maxStep: Int);
 //设置动态当前步数
 fun setCurrentStep(currentStep: Int);
```
 ## 待完成
 - [x] 自定义view
   - [x] onMeasure 源码和写法基本了解
   - [x] onDraw 源码和写法基本了解
   - [ ] onTouch 触碰事件正在学习
 - [ ] compose 已经接触，还没有另一种熟练
 - [ ] KOTLIN语言正在转变中
