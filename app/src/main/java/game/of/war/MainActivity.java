package game.of.war;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    View player_card;
    View opponent_card;

    ImageView playerTopLeftSuit;
    ImageView playerTopRightSuit;
    ImageView playerBottomLeftSuit;
    ImageView playerBottomRightSuit;

    ImageView opponentTopLeftSuit;
    ImageView opponentTopRightSuit;
    ImageView opponentBottomLeftSuit;
    ImageView opponentBottomRightSuit;

    ArrayList<Integer> clubArray = new ArrayList<>();
    ArrayList<Integer> spadeArray = new ArrayList<>();
    ArrayList<Integer> diamondArray = new ArrayList<>();
    ArrayList<Integer> heartArray = new ArrayList<>();;

    ArrayList<ArrayList<Integer>> deckArray = new ArrayList<>();

    int CLUBS = 1;
    int SPADES = 2;
    int DIAMONDS = 3;
    int HEARTS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player_card = findViewById(R.id.player_card);
        opponent_card = findViewById(R.id.opponent_card);

        playerTopLeftSuit = findViewById(R.id.playerTopLeftSuit);
        playerTopRightSuit = findViewById(R.id.playerTopRightSuit);
        playerBottomLeftSuit = findViewById(R.id.playerBottomLeftSuit);
        playerBottomRightSuit = findViewById(R.id.playerBottomRightSuit);

        opponentTopLeftSuit = findViewById(R.id.opponentTopLeftSuit);
        opponentTopRightSuit = findViewById(R.id.opponentTopRightSuit);
        opponentBottomLeftSuit = findViewById(R.id.opponentBottomLeftSuit);
        opponentBottomRightSuit = findViewById(R.id.opponentBottomRightSuit);

        populateSuitArrays();
        populateDeckArray();

        player_card.setOnClickListener(v-> {
            Random random = new Random();
            int selected = random.nextInt(4-1 + 1);
            switch (selected) {
                case 1:
                    setPlayerDeckSuitImages(R.drawable.club); break;
                case 2:
                    setPlayerDeckSuitImages(R.drawable.spade); break;
                case 3:
                    setPlayerDeckSuitImages(R.drawable.diamond); break;
                case 4:
                    setPlayerDeckSuitImages(R.drawable.heart); break;
            }
        });
    }

    public void populateSuitArrays() {
        for (int i=0; i<13; i++) {
            clubArray.add(i);
            spadeArray.add(i);
            diamondArray.add(i);
            heartArray.add(i);
        }
    }

    public void populateDeckArray() {
        deckArray.add(clubArray);
        deckArray.add(spadeArray);
        deckArray.add(diamondArray);
        deckArray.add(heartArray);
    }

    public void setPlayerDeckSuitImages(int suitDrawable) {
        playerTopLeftSuit.setBackgroundResource(suitDrawable);
        playerTopRightSuit.setBackgroundResource(suitDrawable);
        playerBottomLeftSuit.setBackgroundResource(suitDrawable);
        playerBottomRightSuit.setBackgroundResource(suitDrawable);
    }

    public void setOpponentDeckSuitImages(int suitDrawable) {
        opponentTopLeftSuit.setBackgroundResource(suitDrawable);
        opponentTopRightSuit.setBackgroundResource(suitDrawable);
        opponentBottomLeftSuit.setBackgroundResource(suitDrawable);
        opponentBottomRightSuit.setBackgroundResource(suitDrawable);
    }
}