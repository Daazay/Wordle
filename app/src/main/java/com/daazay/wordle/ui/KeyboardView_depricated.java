package com.daazay.wordle.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.daazay.wordle.R;

import java.util.HashMap;
import java.util.Map;

public class KeyboardView_depricated extends LinearLayout {
    private static final String TAG = "KEYBOARD_VIEW";
    private Map<String, Button> keys;

    public KeyboardView_depricated(Context context) {
        super(context);
        init(null);
        Log.i(TAG, ": Constructor");
    }

    public KeyboardView_depricated(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        Log.i(TAG, ": Constructor");
    }

    public KeyboardView_depricated(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        Log.i(TAG, ": Constructor");
    }

    private void init(@Nullable AttributeSet attrs) {
        inflate(getContext(), R.layout.keyboard_view_depricated, this);
        initUI();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
    }

    private void initUI() {
        keys = new HashMap<String, Button>();
        keys.put("Q",(Button) findViewById(R.id.buttonQ));
        keys.put("W",(Button) findViewById(R.id.buttonW));
        keys.put("E",(Button) findViewById(R.id.buttonE));
        keys.put("R",(Button) findViewById(R.id.buttonR));
        keys.put("T",(Button) findViewById(R.id.buttonT));
        keys.put("Y",(Button) findViewById(R.id.buttonY));
        keys.put("U",(Button) findViewById(R.id.buttonU));
        keys.put("I",(Button) findViewById(R.id.buttonI));
        keys.put("O",(Button) findViewById(R.id.buttonO));
        keys.put("P",(Button) findViewById(R.id.buttonP));
        keys.put("A",(Button) findViewById(R.id.buttonA));
        keys.put("S",(Button) findViewById(R.id.buttonS));
        keys.put("D",(Button) findViewById(R.id.buttonD));
        keys.put("F",(Button) findViewById(R.id.buttonF));
        keys.put("G",(Button) findViewById(R.id.buttonG));
        keys.put("H",(Button) findViewById(R.id.buttonH));
        keys.put("J",(Button) findViewById(R.id.buttonJ));
        keys.put("K",(Button) findViewById(R.id.buttonK));
        keys.put("L",(Button) findViewById(R.id.buttonL));
        keys.put("Z",(Button) findViewById(R.id.buttonZ));
        keys.put("X",(Button) findViewById(R.id.buttonX));
        keys.put("C",(Button) findViewById(R.id.buttonC));
        keys.put("V",(Button) findViewById(R.id.buttonV));
        keys.put("B",(Button) findViewById(R.id.buttonB));
        keys.put("N",(Button) findViewById(R.id.buttonN));
        keys.put("M",(Button) findViewById(R.id.buttonM));
        keys.put("Enter",(Button) findViewById(R.id.buttonEnter));
        keys.put("Erase",(Button) findViewById(R.id.buttonErase));
    }

    public void setOnClickListener(OnClickListener l) {
        for (Map.Entry<String, Button> entry : keys.entrySet()) {
            entry.getValue().setOnClickListener(l);
        }
    }

    public void switchButtonEnable(String key) {
        Button b = keys.get(key);
        if (b == null) return;
        b.setEnabled(!b.isEnabled());
    }

    public void disableOrEnable(boolean flag) {
        for (Map.Entry<String, Button> entry : keys.entrySet()) {
            entry.getValue().setEnabled(flag);
        }
    }
}
