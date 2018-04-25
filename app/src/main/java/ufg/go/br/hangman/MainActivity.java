package ufg.go.br.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView mWord;
    private final int LIMIT_OF_MISTAKES = 7;
    private String wordToBeGuessed = "Danillo".toUpperCase();
    private int mistakes = 0;
    private char[] guess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWord = findViewById(R.id.mWord);
        guess = getWordMasked();
        mWord.setText(String.valueOf(guess));
    }

    public void letterPressed(View v) {
        final int id = v.getId();
        Button letterButton = findViewById(id);
        letterButton.setEnabled(false);
        String letter = letterButton.getText().toString();
        if (wordToBeGuessed.contains(letter)) {
            replaceCorrectLetter(letter.toCharArray()[0]);
            if (wordToBeGuessed.equals(String.valueOf(guess))) {
                showMessage(R.id.you_won);
            }
        } else {
            mistakes++;
        }

        if (mistakes >= LIMIT_OF_MISTAKES) {
            showMessage(R.id.you_lose);
            mWord.setText("");
        }
    }

    private char[] getWordMasked() {
        char[] word = new char[wordToBeGuessed.length()];
        for (int i = 0; i < wordToBeGuessed.length(); i++) {
            word[i] = '―';
        }

        return word;
    }

    private void replaceCorrectLetter(char letter) {
        List<Integer> lettersFound = new ArrayList<>();
        for (int i = 0; i < wordToBeGuessed.length(); i++) {
            if (wordToBeGuessed.toCharArray()[i] == letter) {
                lettersFound.add(i);
            }
        }

        for (Integer index : lettersFound) {
            guess[index] = letter;
        }

        mWord.setText(String.valueOf(guess));
    }

    private void showMessage(int idStringDescription) {
        new MaterialDialog.Builder(this).title(R.string.title_message)
                .content(idStringDescription)
                .positiveText(R.string.label_ok)
                .show();
    }
}
