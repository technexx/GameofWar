package game.of.war;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random random = new Random();

    ImageView crossedSwords;

    TextView totalCardsLeftTextView;
    View playerCard;
    View opponentCard;
    Button resetGame;

    TextView playerNumber;
    TextView playerScoreText;
    TextView playerWinText;
    TextView opponentNumber;
    TextView opponentScoreText;
    TextView opponentWinText;
    TextView drawRoundText;

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

    final int CLUBS = 1;
    final int SPADES = 2;
    final int DIAMONDS = 3;
    final int HEARTS = 4;

    final int PLAYER_CARD_WINS = 1;
    final int OPPONENT_CARD_WINS =2;
    final int CARDS_DRAW = 3;

    int totalCardsLeft = 52;
    int totalPlayerScore;
    int totalOpponentScore;
    boolean gameHasBegun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crossedSwords = findViewById(R.id.crossed_swords);
        drawRoundText = findViewById(R.id.draw_round_text);
        totalCardsLeftTextView = findViewById(R.id.total_cards_left_textView);

        resetGame = findViewById(R.id.reset_game);
        resetGame.setText("Reset");
        resetGame.setVisibility(View.INVISIBLE);

        playerCard = findViewById(R.id.playerCard);
        playerNumber = findViewById(R.id.playerNumber);
        playerScoreText = findViewById(R.id.player_score_text);
        playerWinText = findViewById(R.id.player_win_round_text);
        playerTopLeftSuit = findViewById(R.id.playerTopLeftSuit);
        playerTopRightSuit = findViewById(R.id.playerTopRightSuit);
        playerBottomLeftSuit = findViewById(R.id.playerBottomLeftSuit);
        playerBottomRightSuit = findViewById(R.id.playerBottomRightSuit);

        opponentWinText = findViewById(R.id.opponent_win_round_text);
        opponentCard = findViewById(R.id.opponentCard);
        opponentNumber = findViewById(R.id.opponentNumber);
        opponentScoreText = findViewById(R.id.opponent_score_text);
        opponentTopLeftSuit = findViewById(R.id.opponentTopLeftSuit);
        opponentTopRightSuit = findViewById(R.id.opponentTopRightSuit);
        opponentBottomLeftSuit = findViewById(R.id.opponentBottomLeftSuit);
        opponentBottomRightSuit = findViewById(R.id.opponentBottomRightSuit);

        populateSuitArrays();

        crossedSwords.setOnClickListener(v-> {
            logArrays();
            if (selectArrayOfNumbersFromSuit()==null) {
                return;
            }
            selectAndCompareAndDisplayResultsOfCardRound();
        });

        resetGame.setOnClickListener(v-> {
            resetGameViewsAndVars();
        });
    }

    public void selectAndCompareAndDisplayResultsOfCardRound() {
        ArrayList<Integer> chosenSuitArrayForPlayer = selectArrayOfNumbersFromSuit();
        while (chosenSuitArrayForPlayer.size()==0) {
            chosenSuitArrayForPlayer = selectArrayOfNumbersFromSuit();
        }

        int playerCardSelected = selectCardNumberFromArray(chosenSuitArrayForPlayer);
        displaySuitDrawableForPlayer(chosenSuitArrayForPlayer);
        playerNumber.setText(convertCardValueToString(playerCardSelected));
        removeCardFromDeck(chosenSuitArrayForPlayer, playerCardSelected);

        ArrayList<Integer> chosenSuitArrayForOpponent = selectArrayOfNumbersFromSuit();
        while (chosenSuitArrayForOpponent.size()==0) {
            chosenSuitArrayForOpponent = selectArrayOfNumbersFromSuit();
        }

        int opponentCardSelected = selectCardNumberFromArray(chosenSuitArrayForOpponent);
        displaySuitDrawableForOpponent(chosenSuitArrayForOpponent);
        opponentNumber.setText(convertCardValueToString(opponentCardSelected));
        removeCardFromDeck(chosenSuitArrayForOpponent, opponentCardSelected);

        int drawResult = flipResult(playerCardSelected, opponentCardSelected);
        setCardRoundResultText(drawResult);
        setGameScore(drawResult);

        removeCardBackGrounds();
    }

    public ArrayList<Integer> selectArrayOfNumbersFromSuit() {
        if (arrayOfNumbersForClubs.size()==0 && arrayOfNumbersForSpades.size()==0 && arrayOfNumbersForDiamonds.size()==0 && arrayOfNumbersForHearts.size()==0) {
            return null;
        }

        int selected = random.nextInt(5-1) + 1;
        ArrayList<Integer> result = new ArrayList<>();

        if (selected==CLUBS) {
            result = arrayOfNumbersForClubs;
        }
        else if (selected==SPADES) {
            result = arrayOfNumbersForSpades;
        }
        else if (selected==DIAMONDS) {
            result = arrayOfNumbersForDiamonds;
        }
        else if (selected==HEARTS) {
            result = arrayOfNumbersForHearts;
        }

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

    public void removeCardFromDeck(ArrayList<Integer> cardArraySelected, int cardSelected) {
        cardArraySelected.remove((Object) cardSelected);
    }

    public void setGameScore(int winner) {
        if (winner==PLAYER_CARD_WINS) {
            totalPlayerScore++;
            playerScoreText.setText(String.valueOf(totalPlayerScore));
        }
        else if (winner==OPPONENT_CARD_WINS) {
            totalOpponentScore++;
            opponentScoreText.setText(String.valueOf(totalOpponentScore));
        }
        totalCardsLeft -= 2;
        totalCardsLeftTextView.setText(String.valueOf(totalCardsLeft));

        if (totalCardsLeft==0) {
            declareWinnerOfGame();
            resetGame.setVisibility(View.VISIBLE);
        }
    }

    public void declareWinnerOfGame() {
        if (totalPlayerScore > totalOpponentScore) {
            playerNumber.setText("\u2713");
            opponentNumber.setText("X");
        } else {
            playerNumber.setText("X");
            opponentNumber.setText("\u2713");
        }
    }

    public void setCardRoundResultText(int winner) {
        if (winner==PLAYER_CARD_WINS) {
            playerWinText.setText("Win!");
            opponentWinText.setText("");
            drawRoundText.setText("");
        } else if (winner==OPPONENT_CARD_WINS) {
            playerWinText.setText("");
            opponentWinText.setText("WIN!");
            drawRoundText.setText("");
        } else {
            playerWinText.setText("");
            opponentWinText.setText("");
            drawRoundText.setText("DRAW!");
        }
    }

    public int flipResult(int playerCard, int opponentCard) {
        if (playerCard > opponentCard) return PLAYER_CARD_WINS;
        else if (opponentCard > playerCard) return OPPONENT_CARD_WINS;
        else return CARDS_DRAW;
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
        for (int i=2; i<15; i++) {
            arrayOfNumbersForClubs.add(i);
            arrayOfNumbersForSpades.add(i);
            arrayOfNumbersForDiamonds.add(i);
            arrayOfNumbersForHearts.add(i);
        }
    }

    public void removeCardBackGrounds() {
        if (!gameHasBegun) {
            playerCard.setBackgroundResource(R.drawable.card_border);
            opponentCard.setBackgroundResource(R.drawable.card_border);
            gameHasBegun = true;
        }
    }

    public void resetGameViewsAndVars() {
        populateSuitArrays();

        gameHasBegun = false;
        playerScoreText.setText("0");
        opponentScoreText.setText("0");
        totalCardsLeft = 52;
        totalCardsLeftTextView.setText("52");
        playerCard.setBackgroundResource(R.drawable.card_logo);
        opponentCard.setBackgroundResource(R.drawable.card_logo);
        resetGame.setVisibility(View.INVISIBLE);
    }

    public void logArrays() {
        Log.i("testnum", "club array SIZE is " + arrayOfNumbersForClubs.size());
        Log.i("testnum", "spade array SIZE is " + arrayOfNumbersForSpades.size());
        Log.i("testnum", "diamond array SIZE is " + arrayOfNumbersForDiamonds.size());
        Log.i("testnum", "heart array SIZE is " + arrayOfNumbersForHearts.size());

        Log.i("testnum", "club array is " + arrayOfNumbersForClubs);
        Log.i("testnum", "spade array is " + arrayOfNumbersForSpades);
        Log.i("testnum", "diamond array is " + arrayOfNumbersForDiamonds);
        Log.i("testnum", "heart array is " + arrayOfNumbersForHearts);
    }
}