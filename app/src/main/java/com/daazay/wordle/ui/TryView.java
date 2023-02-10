package com.daazay.wordle.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.daazay.wordle.R;

public class TryView extends LinearLayout {
    private static final String TAG = "TRY_VIEW";
    private String word = "FLAME";
    private TextView[] letters;
    private final Context context;

    public TryView(Context context) {
        super(context);
        this.context = context;
        init(null);
        Log.i(TAG, ": constructor");
    }

    public TryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
        Log.i(TAG, ": constructor");
    }

    public TryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
        Log.i(TAG, ": constructor");
    }

    public void setWord(String word) {
        this.word = word;
    }

    private void init(@Nullable AttributeSet attrs) {
        inflate(getContext(), R.layout.try_view, this);
        initUI();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TryView);

        String firstLetter = attributes.getString(R.styleable.TryView_firstLetter);
        letters[0].setText(firstLetter);
        String secondLetter = attributes.getString(R.styleable.TryView_secondLetter);
        letters[1].setText(secondLetter);
        String thirdLetter = attributes.getString(R.styleable.TryView_thirdLetter);
        letters[2].setText(thirdLetter);
        String fourthLetter = attributes.getString(R.styleable.TryView_fourthLetter);
        letters[3].setText(fourthLetter);
        String fifthLetter = attributes.getString(R.styleable.TryView_fifthLetter);
        letters[4].setText(fifthLetter);


        Integer firstLetterStatus = attributes.getInt(R.styleable.TryView_firstLetterStatus, -1);
        setLetterByStatus(letters[0],firstLetterStatus);
        Integer secondLetterStatus = attributes.getInteger(R.styleable.TryView_secondLetterStatus, -1);
        setLetterByStatus(letters[1],secondLetterStatus);
        Integer thirdLetterStatus = attributes.getInteger(R.styleable.TryView_thirdLetterStatus, -1);
        setLetterByStatus(letters[2],thirdLetterStatus);
        Integer fourthLetterStatus = attributes.getInteger(R.styleable.TryView_fourthLetterStatus, -1);
        setLetterByStatus(letters[3],fourthLetterStatus);
        Integer fifthLetterStatus = attributes.getInteger(R.styleable.TryView_fifthLetterStatus, -1);
        setLetterByStatus(letters[4],fifthLetterStatus);

        attributes.recycle();
    }

    private void setLetterByStatus(TextView letter, Integer status) {
        switch (status) {
            case -1: {
                letter.setBackgroundColor(getResources().getColor(R.color.wrongColor));
                break;
            }
            case 0: {
                letter.setBackgroundColor(getResources().getColor(R.color.inPlaceColor));
                break;
            }
            case 1: {
                letter.setBackgroundColor(getResources().getColor(R.color.outOfPlaceColor));
                break;
            }
        }
    }

    private void initUI() {
        letters = new TextView[] {
                findViewById(R.id.firstLetter),
                findViewById(R.id.secondLetter),
                findViewById(R.id.thirdLetter),
                findViewById(R.id.fourthLetter),
                findViewById(R.id.fifthLetter)};

        for (TextView letter : letters) {
            letter.setText("");
        }
    }

    public boolean isDone() {
        for (int i = 0; i < letters.length; i++) {
            if (!getLetter(i).equals(String.valueOf(word.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public void setLetterColors () {
        String input = getInputWord();
        for (int i = 0; i < letters.length; i++) {
            char word_char = word.charAt(i);
            for (int j = 0; j < letters.length; j++) {
                char inputChar = input.charAt(j);

                if (inputChar == word_char) {
                    ColorDrawable colorDrawable = (ColorDrawable) letters[j].getBackground();
                    int letterColor = colorDrawable.getColor();
                    int inPlaceColor = getResources().getColor(R.color.inPlaceColor);
                    int outOfPlaceColor = getResources().getColor(R.color.outOfPlaceColor);

                    if ((letterColor != inPlaceColor && letterColor != outOfPlaceColor)) {
                       if (i == j) {
                           letters[j].setBackgroundColor(inPlaceColor);
                       } else {
                           letters[j].setBackgroundColor(outOfPlaceColor);
                       }
                       break;
                    }
                }
            }
        }
    }

    public String getInvalidLetters() {
        String f_word = getInputWord();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < letters.length; i++) {
            char ch = f_word.charAt(i);

            if (!word.contains(String.valueOf(ch))) {
                res.append(ch);
            }
        }
        return res.toString();
    }

    public void clearLetters() {
        for (TextView letter : letters) {
            letter.setText("");
            letter.setBackgroundColor(getResources().getColor(R.color.wrongColor));
        }
    }

    public void setLetterBgColor(int pos, int Color) {
        letters[pos].setBackgroundColor(Color);
    }

    public void setLetter(int pos, String letter) {
        letters[pos].setText(letter);
    }

    public String getLetter(int pos) {
        return (String) letters[pos].getText();
    }

    public String getInputWord() {
        StringBuilder input = new StringBuilder();
        for (TextView letter : letters) {
            input.append(letter.getText());
        }
        return input.toString();
    }
}
