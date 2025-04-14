package ru.rsue.android.droidquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;


        if (mIsDeceiterArray[mCurrentIndex]) {
            messageResId = R.string.judgement_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mDeceitButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_android, true),
            new Question(R.string.question_linear, false),
            new Question(R.string.question_service, false),
            new Question(R.string.question_res, true),
            new Question(R.string.question_manifest, true),
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, true),
            new Question(R.string.question_3, false),
            new Question(R.string.question_4, true),
            new Question(R.string.question_5, false)
    };
    private int mCurrentIndex = 0;
    private boolean[] mIsDeceiterArray = new boolean[mQuestionBank.length];
    private static final int REQUEST_CODE_DECEIT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) вызван");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsDeceiterArray = savedInstanceState.getBooleanArray("deceiter_array");
            if (mIsDeceiterArray == null) {
                mIsDeceiterArray = new boolean[mQuestionBank.length];
            }
        }
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        mDeceitButton = (Button)findViewById(R.id.deceit_button);
        mDeceitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = DeceitActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_DECEIT);
            }
        });
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mPrevButton = (ImageButton)findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){

                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        updateQuestion();
        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        updateQuestion();
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CODE_DECEIT) {
            if (data == null) return;
            mIsDeceiterArray[mCurrentIndex] = DeceitActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBooleanArray("deceiter_array", mIsDeceiterArray);
    }

        @Override
        public void onStart(){
            super.onStart();
            Log.d(TAG, "onStart() вызван");
        }
        @Override
        public void onPause(){
            super.onPause();
            Log.d(TAG, "onPause() вызван");
        }
        @Override
        public void onResume(){
            super.onResume();
            Log.d(TAG, "onResume() вызван");
        }
        @Override
        public void onStop(){
            super.onStop();
            Log.d(TAG, "onStop() вызван");
        }
        @Override
        public void onDestroy(){
            super.onDestroy();
            Log.d(TAG, "onDestroy вызван");
        }
    }

