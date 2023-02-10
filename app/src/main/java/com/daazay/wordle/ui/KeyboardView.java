package com.daazay.wordle.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.daazay.wordle.R;

import java.util.HashMap;
import java.util.Map;

public class KeyboardView extends LinearLayout {
    private static final String TAG = "KeyboardView_TAG";
    public static enum Language {
        RUS,
        ENG,
    };
    public Language language = Language.RUS;
    private Map<String, Button> keys;
    public ImageButton eraseButton;
    public ImageButton acceptButton;
    private String[][] locale;

    private LinearLayout firstLine;
    private LinearLayout secondLine;
    private LinearLayout thirdLine;

    public KeyboardView(Context context) {
        super(context);
        setKeysLocale();
        initUI();
        setKeys();
    }

    public KeyboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setKeysLocale();
        initUI();
        setKeys();
    }

    public KeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setKeysLocale();
        initUI();
        setKeys();
    }

    private void initUI() {
        inflate(getContext(), R.layout.keyboard_view, this);
        firstLine = findViewById(R.id.firstLine);
        secondLine = findViewById(R.id.secondLine);
        thirdLine = findViewById(R.id.thirdLine);
    }

    public void setLanguage(Language language) {
        this.language = language;
        setKeysLocale();
        setKeys();
    }

    private void setKeysLocale() {
        switch (language) {
            case RUS: {
                locale = new String[][]{
                        new String[]{"Й", "Ц", "У", "К", "Е", "Н", "Г", "Ш", "Щ", "З", "Х", "Ъ" },
                        new String[]{"Ф", "Ы", "В", "А", "П", "Р", "О", "Л", "Д", "Ж", "Э" },
                        new String[]{"Я", "Ч", "С", "М", "И", "Т", "Ь", "Б", "Ю" },
                };
                break;
            }
            case ENG: {
                locale = new String[][]{
                        new String[]{"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P" },
                        new String[]{"A", "S", "D", "F", "G", "H", "J", "K", "L" },
                        new String[]{"Z", "X", "C", "V", "B", "N", "M" },
                };
                break;
            }
        }
    }

    private void setKeys() {
        keys = new HashMap<String, Button>();
        firstLine.removeAllViews();
        secondLine.removeAllViews();
        thirdLine.removeAllViews();

        for (int i = 0; i < locale[0].length; i++) {
            addKey(firstLine, locale[0][i]);
        }
        for (int i = 0; i < locale[1].length; i++) {
            addKey(secondLine, locale[1][i]);
        }
        // add erase
        eraseButton = new ImageButton(getContext());
        eraseButton.setImageResource(R.drawable.ic_erase);
        eraseButton.setBackground(getResources().getDrawable(R.drawable.yellow_button_drawable));
        setControlKeysSize(eraseButton);
        thirdLine.addView(eraseButton);
        //
        for (int i = 0; i < locale[2].length; i++) {
            addKey(thirdLine, locale[2][i]);
        }
        // add enter
        acceptButton = new ImageButton(getContext());
        acceptButton.setImageResource(R.drawable.ic_accept);
        acceptButton.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
        setControlKeysSize(acceptButton);
        thirdLine.addView(acceptButton);
        //
    }

    private void setControlKeysSize(View view) {
        switch (language) {
            case ENG: {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(toPixels(55), toPixels(40));
                params.setMargins(0,0,0,0);
                view.setPadding(0,0,0,0);
                view.setLayoutParams(params);
                break;
            }
            case RUS: {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(toPixels(60), toPixels(40));
                params.setMargins(0,0,0,0);
                view.setPadding(0,0,0,0);
                view.setLayoutParams(params);
                break;
            }
        }
    }

    private void setKeySize(View view) {
        switch (language) {
            case ENG: {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(toPixels(35), toPixels(45));
                params.setMargins(0,0,0,0);
                view.setPadding(0,0,0,0);
                view.setLayoutParams(params);
                break;
            }
            case RUS: {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(toPixels(30), toPixels(45));
                params.setMargins(0,0,0,0);
                view.setPadding(0,0,0,0);
                view.setLayoutParams(params);
                break;
            }
        }
    }

    private int toPixels(int dps) {
        Resources r = getContext().getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                r.getDisplayMetrics()
        );
    }

    private void addKey(LinearLayout line, String key) {
        Button button = new Button(getContext());
        button.setText(key);
        button.setTextColor(getResources().getColor(R.color.background));
        button.setBackground(getResources().getDrawable(R.drawable.gray_button_drawable));
        setKeySize(button);
        keys.put(key, button);
        line.addView(button);
    }


    public void setOnClickListener(OnClickListener l) {
        for (Map.Entry<String, Button> entry : keys.entrySet()) {
            entry.getValue().setOnClickListener(l);
        }
    }

    public void switchButtonEnable(String key) {
        View b = (Button) keys.get(key);
        if (b == null) return;
        boolean flag = b.isEnabled();
        b.setEnabled(!flag);

        if (flag) {
            b.setBackground(getResources().getDrawable(R.drawable.black_button_drawable));
        } else {
            b.setBackground(getResources().getDrawable(R.drawable.gray_button_drawable));
        }
    }

    public void setEnabled(String key, boolean flag) {
        View b = (Button) keys.get(key);
        if (b == null) return;
        b.setEnabled(flag);
        if (!flag) {
            b.setBackground(getResources().getDrawable(R.drawable.black_button_drawable));
        } else {
            b.setBackground(getResources().getDrawable(R.drawable.gray_button_drawable));
        }
    }

    public void disableOrEnable(boolean flag) {
        for (Map.Entry<String, Button> entry : keys.entrySet()) {
            Button b = entry.getValue();
            b.setEnabled(flag);
            b.setBackground(getResources().getDrawable(R.drawable.gray_button_drawable));
        }
    }
}
