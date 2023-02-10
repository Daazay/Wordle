package com.daazay.wordle.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daazay.wordle.R;
import com.daazay.wordle.ui.KeyboardView;
import com.daazay.wordle.ui.TryView;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private KeyboardView keyboardView;
    private Button restartButton;
    private Button languageButton;
    private TryView[] tries;
    private String word;
    private Integer currentLetterIndex = 0;
    private Integer currentTry = 0;

    private String[] words;
    private String winText;
    private String loseText;
    private boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

    }

    private void initWords() {
        switch (keyboardView.language) {
            case RUS: {
                words = new String[]{
                        "АГЕНТ", "АКТЁР", "БАГАЖ", "БОМБА", "ВЬЮГА", "ВОЙНА",
                        "ДОРОГА", "ДОЖДЬ", "ЗАПАД", "ЗНАМЯ", "КАРМА", "КАНАТ",
                        "ЛЕНТА", "МОРЯК", "МЕТЛА", "НОСОК", "ДОСКА", "ТКАНЬ",
                        "РОБОТ", "УКУС", "ХВОСТ", "ЦЕНТР", "ЩЕНОК", "ЩЕПКА", "ЯКОРЬ",};
                winText = "Ты победил!";
                loseText = "Ты проиграл! Загаданное слово было - ";
                languageButton.setText("Язык");
                restartButton.setText("Рестарт");
                break;
            }
            case ENG: {
                words = new String[]{
                        "AGENT", "ASSET", "OFFER", "VALUE",
                        "TRUTH", "BOARD", "DEBUG", "CHAIR",
                        "TABLE", "CLASS", "ARROW", "DRIVE",
                        "ANGRY", "BEACH", "TODAY", "DIRTY",
                        "CRAWL", "DEPTH", "INPUT", "EIGHT",
                        "FIRST", "CRIME", "FLAME", "BRICK",
                        "CYNIC", "CLONE", "CLOUD", "COMIC",
                        "ROUGH", "DUMMY", "DISCO", "FLUFF",
                        "CURVE", "ROVER", "ROYAL", "RUSTY",
                        "ZEBRA", "PIZZA", "PRIZE", "CRAZY",
                        "ENJOY", "NINJA", "HABIT" };
                winText = "You win!";
                loseText = "You lose. The correct word was - ";
                languageButton.setText("Lang");
                restartButton.setText("Restart");
                break;
            }
        }
    }

    private void newGame() {
        initWords();
        int index = ThreadLocalRandom.current().nextInt(0, words.length);
        word = words[index % words.length];
        currentLetterIndex = 0;
        currentTry = 0;

        for (TryView tryView : tries) {
            tryView.setWord(word);
            tryView.clearLetters();
        }

        keyboardView.disableOrEnable(true);

        tries[0].setLetterBgColor(0, getResources().getColor(R.color.availableColor));
        gameOver = false;
    }

    private final View.OnClickListener acceptButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (gameOver)
                return;
            String w = tries[currentTry].getInputWord();
            if (w.length() < 5) return;

            String chs = tries[currentTry].getInvalidLetters();

            for (int i = 0; i < chs.length(); i++) {
                String ch = String.valueOf(chs.charAt(i));
                keyboardView.setEnabled(ch, false);
            }

            tries[currentTry].setLetterColors();
            if (tries[currentTry].isDone()) {
                Toast.makeText(getBaseContext(), winText, Toast.LENGTH_SHORT).show();
                keyboardView.disableOrEnable(false);
                gameOver = true;
                return;
            }

            currentTry++;

            if (currentTry >= tries.length) {
                Toast.makeText(getBaseContext(), loseText + word + ".", Toast.LENGTH_SHORT).show();
                keyboardView.disableOrEnable(false);
                gameOver = true;
                return;
            }

            currentLetterIndex = 0;
            tries[currentTry].setLetterBgColor(0, getResources().getColor(R.color.availableColor));
        }
    };

    private final View.OnClickListener eraseButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (gameOver) return;
            clearLettersInTryView(tries[currentTry]);
        }
    };

    private void initUI() {
        languageButton = findViewById(R.id.buttonLanguage);
        restartButton = (Button) findViewById(R.id.buttonRestart);
        keyboardView = (KeyboardView) findViewById(R.id.keyboard);

        tries = new TryView[]{
                (TryView) findViewById(R.id.firstTry),
                (TryView) findViewById(R.id.secondTry),
                (TryView) findViewById(R.id.thirdTry),
                (TryView) findViewById(R.id.fourthTry),
                (TryView) findViewById(R.id.fifthTry),
                (TryView) findViewById(R.id.sixthTry),
        };

        ///////////////////////////////////////////////////////////////////////

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (keyboardView.language) {
                    case ENG: {
                        keyboardView.setLanguage(KeyboardView.Language.RUS);
                        break;
                    }
                    case RUS: {
                        keyboardView.setLanguage(KeyboardView.Language.ENG);
                        break;
                    }
                }
                keyboardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button b = (Button) view;
                        String key = b.getText().toString();

                        if (currentLetterIndex != 5) {
                            tries[currentTry].setLetter(currentLetterIndex, key);
                            tries[currentTry].setLetterBgColor(currentLetterIndex++, getResources().getColor(R.color.availableColor));
                        }
                    }
                });

                keyboardView.acceptButton.setOnClickListener(acceptButtonOnClickListener);

                keyboardView.eraseButton.setOnClickListener(eraseButtonOnClickListener);
                newGame();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
            }
        });

        keyboardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String key = b.getText().toString();

                if (currentLetterIndex != 5) {
                    tries[currentTry].setLetter(currentLetterIndex, key);
                    tries[currentTry].setLetterBgColor(currentLetterIndex++, getResources().getColor(R.color.availableColor));
                }
            }
        });

        keyboardView.acceptButton.setOnClickListener(acceptButtonOnClickListener);

        keyboardView.eraseButton.setOnClickListener(eraseButtonOnClickListener);

        newGame();
    }

    private void clearLettersInTryView(TryView tryView) {
        for (int i = 0; i < 5; i++) {
            tryView.setLetter(i, "");
            tryView.setLetterBgColor(i, getResources().getColor(R.color.wrongColor));
        }
        tryView.setLetterBgColor(0, getResources().getColor(R.color.availableColor));

        currentLetterIndex = 0;
    }
}