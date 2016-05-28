package com.example.leonardollobato.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CHEATE-ACTIVITY-";
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.leonardollobato.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_IS_SHOWN =
            "com.example.leonardollobato.geoquiz.answer_shown";



    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private int mAnswerStringId;

    /**
     * Customized Intent, specific for pass data from parent Activity to this one (CheatActivity).
     * @param packageContext
     * @param answerIsTrue
     * @return
     */
    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return i;
    }

    /**
     * Returns true if the answer was cheated or false if it was not.
     * @param result
     * @return
     */
    public static boolean wasAnswerShow(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);



        // Get the value passed from the Quiz Activity about the right answer
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);


        mShowAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // check if the answer received from the QuizActivity is True or False and Set the View
                if(mAnswerIsTrue){
                    mAnswerStringId = R.string.true_button;
                }else{
                    mAnswerStringId = R.string.false_button;
                }

                mAnswerTextView.setText(mAnswerStringId);
                setAnswerShowResult(true);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    int cx = mShowAnswer.getWidth() / 2;
                    int cy = mShowAnswer.getHeight() / 2;
                    float radius = mShowAnswer.getWidth();

                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswer, cx, cy, radius, 0);

                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerTextView.setVisibility(View.VISIBLE);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        } });
                    anim.start();
                }else{
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }

            }
        });

    }



    /**
     * Create a Intent that has the flag that indicates if the user cheated or not
     * then it is sent to the parent activity by the setResult method that receive a general status and the intent with the data
     * @param isAnswerShown
     */
    private void setAnswerShowResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
