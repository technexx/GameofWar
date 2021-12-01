package game.of.war;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView crossedSwords;

    View playerCard;
    View opponentCard;

    TextView playerNumber;
    TextView opponentNumber;

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

    Random random = new Random();

    final int CLUBS = 1;
    final int SPADES = 2;
    final int DIAMONDS = 3;
    final int HEARTS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crossedSwords = findViewById(R.id.crossed_swords);

        playerCard = findViewById(R.id.playerCard);
        opponentCard = findViewById(R.id.opponentCard);

        playerNumber = findViewById(R.id.playerNumber);
        opponentNumber = findViewById(R.id.opponentNumber);

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

        crossedSwords.setOnClickListener(v-> {
            int playerCardSelected = selectNumberFromSuitArray(selectSuitArray());
            displaySuitDrawableForPlayer(selectSuitArray());

            int opponentCardSelected = selectNumberFromSuitArray(selectSuitArray());
            displaySuitDrawableForOpponent(selectSuitArray());

            playerNumber.setText(String.valueOf(playerCardSelected));
            opponentNumber.setText(String.valueOf(opponentCardSelected));
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

    public ArrayList<Integer> selectSuitArray() {
        int selected = random.nextInt(4-1 + 1);
        ArrayList<Integer> result = new ArrayList<>();

        if (selected==CLUBS && clubArray.size()>0) result = clubArray;
        if (selected==SPADES && spadeArray.size()>0) result = spadeArray;
        if (selected==DIAMONDS && diamondArray.size()>0) result = diamondArray;
        if (selected==HEARTS && heartArray.size()>0) result = heartArray;

        return result;
    }

    public int selectNumberFromSuitArray(ArrayList<Integer> suitArray) {
        if (suitArray.size()>0) {
            int numberIndex = random.nextInt(suitArray.size()-1);
            return suitArray.get(numberIndex);
        } else {
            return 0;
        }
    }

    public void displaySuitDrawableForPlayer(ArrayList<Integer> selectedSuitArray) {
        if (selectedSuitArray==clubArray) setPlayerDeckSuitImages(R.drawable.club);
        if (selectedSuitArray==spadeArray) setPlayerDeckSuitImages(R.drawable.spade);
        if (selectedSuitArray==diamondArray) setPlayerDeckSuitImages(R.drawable.diamond);
        if (selectedSuitArray==heartArray) setPlayerDeckSuitImages(R.drawable.heart);
    }

    public void displaySuitDrawableForOpponent(ArrayList<Integer> selectedSuitArray) {
        if (selectedSuitArray==clubArray) setOpponentDeckSuitImages(R.drawable.club);
        if (selectedSuitArray==spadeArray) setOpponentDeckSuitImages(R.drawable.spade);
        if (selectedSuitArray==diamondArray) setOpponentDeckSuitImages(R.drawable.diamond);
        if (selectedSuitArray==heartArray) setOpponentDeckSuitImages(R.drawable.heart);
    }


    public void setPlayerDeckSuitImages(int suitDrawable) {
        playerTopLeftSuit.setBackgroundResource(suitDrawable);
        playerTopRightSuit.setBackgroundResource(suitDrawable);
        playerBottomLeftSuit.setBackgroundResource(suitDrawable);
        playerBottomRightSuit.setBackgroundResource(suitDrawable);
    }

    public void setOpponentDeckSuitImages(int suitDrawable){
        opponentTopLeftSuit.setBackgroundResource(suitDrawable);
        opponentTopRightSuit.setBackgroundResource(suitDrawable);
        opponentBottomLeftSuit.setBackgroundResource(suitDrawable);
        opponentBottomRightSuit.setBackgroundResource(suitDrawable);
    }
}