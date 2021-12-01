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

    ArrayList<Integer> arrayOfNumbersForClubs = new ArrayList<>();
    ArrayList<Integer> arrayOfNumbersForSpades = new ArrayList<>();
    ArrayList<Integer> arrayOfNumbersForDiamonds = new ArrayList<>();
    ArrayList<Integer> arrayOfNumbersForHearts = new ArrayList<>();;

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

        crossedSwords.setOnClickListener(v-> {
            //Todo: This is not referencing the actual global array we're using, but simply creating a new one identical to it, thus we're not actually removing it.
            //Todo: Removing indices won't work for values because list will not correspond after deletions.
            ArrayList<Integer> chosenSuitArrayForPlayer = selectArrayOfNumbersFromSuit();
            ArrayList<Integer> chosenSuitArrayForOpponent = selectArrayOfNumbersFromSuit();

            Log.i("testnum", "chosen player array is " + chosenSuitArrayForPlayer);
            Log.i("testnum", "chose opponent array is " + chosenSuitArrayForOpponent);

            int playerCardSelected = selectCardNumberFromArray(chosenSuitArrayForPlayer);
            displaySuitDrawableForPlayer(chosenSuitArrayForPlayer);
            playerNumber.setText(convertCardValueToString(playerCardSelected));

            int opponentCardSelected = selectCardNumberFromArray(chosenSuitArrayForOpponent);
            displaySuitDrawableForOpponent(chosenSuitArrayForOpponent);
            opponentNumber.setText(convertCardValueToString(opponentCardSelected));

            removeCardFromDeck(chosenSuitArrayForPlayer, playerCardSelected);
            removeCardFromDeck(chosenSuitArrayForOpponent, opponentCardSelected);

            Log.i("testnum", "club array is " + arrayOfNumbersForClubs);
            Log.i("testnum", "spade array is " + arrayOfNumbersForSpades);
            Log.i("testnum", "diamond array is " + arrayOfNumbersForDiamonds);
            Log.i("testnum", "heart array is " + arrayOfNumbersForHearts);
        });
    }

    public void removeCardFromDeck(ArrayList<Integer> cardArraySelected, int cardSelected) {
        cardArraySelected.remove((Object) cardSelected);
    }

    public ArrayList<Integer> selectArrayOfNumbersFromSuit() {
        int selected = random.nextInt(5-1) + 1;
        ArrayList<Integer> result = new ArrayList<>();

        if (selected==CLUBS && arrayOfNumbersForClubs.size()>0) result = arrayOfNumbersForClubs;
        if (selected==SPADES && arrayOfNumbersForSpades.size()>0) result = arrayOfNumbersForSpades;
        if (selected==DIAMONDS && arrayOfNumbersForDiamonds.size()>0) result = arrayOfNumbersForDiamonds;
        if (selected==HEARTS && arrayOfNumbersForHearts.size()>0) result = arrayOfNumbersForHearts;

        return result;
    }

    public int selectCardNumberFromArray(ArrayList<Integer> numberArray) {
        if (numberArray.size()>0) {
            int numberIndex = random.nextInt(numberArray.size());
            return numberArray.get(numberIndex);
        } else {
            return 0;
        }
    }

    public void displaySuitDrawableForPlayer(ArrayList<Integer> selectedSuitArray) {
        if (selectedSuitArray==arrayOfNumbersForClubs) setPlayerDeckSuitImages(R.drawable.club);
        if (selectedSuitArray==arrayOfNumbersForSpades) setPlayerDeckSuitImages(R.drawable.spade);
        if (selectedSuitArray==arrayOfNumbersForDiamonds) setPlayerDeckSuitImages(R.drawable.diamond);
        if (selectedSuitArray==arrayOfNumbersForHearts) setPlayerDeckSuitImages(R.drawable.heart);
    }

    public void displaySuitDrawableForOpponent(ArrayList<Integer> selectedSuitArray) {
        if (selectedSuitArray==arrayOfNumbersForClubs) setOpponentDeckSuitImages(R.drawable.club);
        if (selectedSuitArray==arrayOfNumbersForSpades) setOpponentDeckSuitImages(R.drawable.spade);
        if (selectedSuitArray==arrayOfNumbersForDiamonds) setOpponentDeckSuitImages(R.drawable.diamond);
        if (selectedSuitArray==arrayOfNumbersForHearts) setOpponentDeckSuitImages(R.drawable.heart);
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

    public String convertCardValueToString(int value) {
        if (value<=10) {
            return String.valueOf(value);
        }
        else if (value==11) return "J";
        else if (value==12) return "Q";
        else if (value==13) return "K";
        else if (value==14) return "A";
        else return "0";
    }

    public void populateSuitArrays() {
        for (int i=1; i<15; i++) {
            arrayOfNumbersForClubs.add(i);
            arrayOfNumbersForSpades.add(i);
            arrayOfNumbersForDiamonds.add(i);
            arrayOfNumbersForHearts.add(i);
        }
    }
}