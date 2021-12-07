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

    TextView playerCardNumber;
    TextView playerPointDeclarationTextView;

    TextView playerGamesWonHeader;
    TextView playerGamesWonTextView;

    TextView opponentCardNumber;
    TextView opponentPointDeclarationTextView;

    TextView opponentGamesWonHeader;
    TextView opponentGamesWonTextView;

    TextView drawRoundText;
    TextView warText;

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
    int numberOfplayerGamesWonTextView;
    int numberOfopponentGamesWonTextView;

    boolean gameHasBegun;
    boolean itIsWar;
    int warCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crossedSwords = findViewById(R.id.crossed_swords);
        crossedSwords.setBackgroundResource(R.drawable.crossed_swords);
        drawRoundText = findViewById(R.id.draw_round_text);
        totalCardsLeftTextView = findViewById(R.id.total_cards_left_textView);
        warText = findViewById(R.id.war_textView);
        warText.setVisibility(View.INVISIBLE);

        resetGame = findViewById(R.id.reset_game);
        resetGame.setText("Reset");
        resetGame.setVisibility(View.INVISIBLE);

        playerCard = findViewById(R.id.playerCard);
        playerCardNumber = findViewById(R.id.playerCardNumber);
        playerGamesWonHeader = findViewById(R.id.player_score_text);
        playerPointDeclarationTextView = findViewById(R.id.player_win_round_text);
        playerGamesWonTextView = findViewById(R.id.player_games_won);
        playerTopLeftSuit = findViewById(R.id.playerTopLeftSuit);
        playerTopRightSuit = findViewById(R.id.playerTopRightSuit);
        playerBottomLeftSuit = findViewById(R.id.playerBottomLeftSuit);
        playerBottomRightSuit = findViewById(R.id.playerBottomRightSuit);

        opponentPointDeclarationTextView = findViewById(R.id.opponent_win_round_text);
        opponentCard = findViewById(R.id.opponentCard);
        opponentCardNumber = findViewById(R.id.opponentCardNumber);
        opponentGamesWonHeader = findViewById(R.id.opponent_score_text);
        opponentGamesWonTextView = findViewById(R.id.opponent_games_won);
        opponentTopLeftSuit = findViewById(R.id.opponentTopLeftSuit);
        opponentTopRightSuit = findViewById(R.id.opponentTopRightSuit);
        opponentBottomLeftSuit = findViewById(R.id.opponentBottomLeftSuit);
        opponentBottomRightSuit = findViewById(R.id.opponentBottomRightSuit);

        resetGameViewsAndVars();
        populateSuitArrays();

//        testScore();

        //Todo: Tie game check marks player.
        //Todo: War gun background is displayed on top of swords.
        crossedSwords.setOnClickListener(v-> {
            if (selectArrayOfNumbersFromSuit()==null) {
                return;
            }
            selectAndCompareAndDisplayResultsOfCardRound();
        });

        resetGame.setOnClickListener(v-> {
            populateSuitArrays();
            resetGameViewsAndVars();
        });
    }

    public void selectAndCompareAndDisplayResultsOfCardRound() {
        if (!gameHasBegun) {
            removeCardBackGrounds();
            gameHasBegun = true;
        }

        ArrayList<Integer> chosenSuitArrayForPlayer = selectArrayOfNumbersFromSuit();
        while (chosenSuitArrayForPlayer.size()==0) {
            chosenSuitArrayForPlayer = selectArrayOfNumbersFromSuit();
        }

        int playerCardSelected = selectCardNumberFromArray(chosenSuitArrayForPlayer);
        displaySuitDrawableForPlayer(chosenSuitArrayForPlayer);
        playerCardNumber.setText(convertCardValueToString(playerCardSelected));
        removeCardFromDeck(chosenSuitArrayForPlayer, playerCardSelected);

        ArrayList<Integer> chosenSuitArrayForOpponent = selectArrayOfNumbersFromSuit();
        while (chosenSuitArrayForOpponent.size()==0) {
            chosenSuitArrayForOpponent = selectArrayOfNumbersFromSuit();
        }

        int opponentCardSelected = selectCardNumberFromArray(chosenSuitArrayForOpponent);
        displaySuitDrawableForOpponent(chosenSuitArrayForOpponent);
        opponentCardNumber.setText(convertCardValueToString(opponentCardSelected));
        removeCardFromDeck(chosenSuitArrayForOpponent, opponentCardSelected);

        int drawResult = flipResult(playerCardSelected, opponentCardSelected);

        countDownCardsInDeck();

        if (drawResult==CARDS_DRAW && !itIsWar) {
            itIsWar = true;
            setWarTextAndImageViews(true);
            return;
        }

        if (itIsWar) {
            HEREWEGOITSWARAGAIN(drawResult);
        } else {
            setCardRoundResultText(drawResult);
            setGameScore(drawResult);
            if (warText.getVisibility()==View.VISIBLE) warText.setVisibility(View.INVISIBLE);
        }
    }

    public void HEREWEGOITSWARAGAIN(int drawResult) {
        warCount++;
        setWarCountForLowDeck();
        setWarDeclarationTextView(warCount);

        if (warCount==4) {
            warCount = 0;

            if (drawResult!=CARDS_DRAW || totalCardsLeft == 0) {
                setCardRoundResultText(drawResult);
                setGameScore(drawResult);
                setWarTextAndImageViews(false);
                itIsWar = false;
            } else {
                drawRoundText.setText(R.string.war_again);
            }
        }
    }

    public void setWarDeclarationTextView(int warCount) {
        switch (warCount) {
            case 1: warText.setText("I"); break;
            case 2: warText.setText("I de-"); break;
            case 3: warText.setText("I de-clare"); break;
            case 4: warText.setText("I de-clare WAR!"); break;
        }
    }

    public void setWarCountForLowDeck() {
        if (totalCardsLeft==2) {
            warCount = 3;
            Log.i("testWar", "true at 2 cards!");
        }
        if (totalCardsLeft==4) {
            warCount = 2;
            Log.i("testWar", "true at 4 cards!");
        }
        if (totalCardsLeft==6) {
            warCount = 1;
            Log.i("testWar", "true at 6 cards!");
        }
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
            if (!itIsWar) totalPlayerScore++; else totalPlayerScore +=5;
            playerGamesWonHeader.setText(String.valueOf(totalPlayerScore));
        }
        else if (winner==OPPONENT_CARD_WINS) {
            if (!itIsWar) totalOpponentScore++; else totalOpponentScore +=5;;
            opponentGamesWonHeader.setText(String.valueOf(totalOpponentScore));
        }
    }

    public void countDownCardsInDeck() {
        totalCardsLeft -= 2;
        totalCardsLeftTextView.setText(String.valueOf(totalCardsLeft));

        if (totalCardsLeft==0) {
            declareWinnerOfGame();
            resetGame.setVisibility(View.VISIBLE);
        }
    }

    public void setCardRoundResultText(int winner) {
        if (winner==PLAYER_CARD_WINS) {
            playerPointDeclarationTextView.setText("Score!");
            opponentPointDeclarationTextView.setText("");
            drawRoundText.setText("");
        } else if (winner==OPPONENT_CARD_WINS) {
            playerPointDeclarationTextView.setText("");
            opponentPointDeclarationTextView.setText("Score!");
            drawRoundText.setText("");
        } else {
            playerPointDeclarationTextView.setText("");
            opponentPointDeclarationTextView.setText("");
            drawRoundText.setText("WAR!!!");
        }
    }

    public void declareWinnerOfGame() {
        if (totalPlayerScore > totalOpponentScore) {
            playerCardNumber.setText("\u2713");
            opponentCardNumber.setText("X");
            playerPointDeclarationTextView.setText("Winner!");
            opponentPointDeclarationTextView.setText("Loser!");

            numberOfplayerGamesWonTextView++;
            playerGamesWonTextView.setText(String.valueOf(numberOfplayerGamesWonTextView));
        } else if (totalOpponentScore > totalPlayerScore){
            playerCardNumber.setText("X");
            opponentCardNumber.setText("\u2713");
            playerPointDeclarationTextView.setText("Loser!");
            opponentPointDeclarationTextView.setText("Winner!");

            numberOfopponentGamesWonTextView++;
            opponentGamesWonTextView.setText(String.valueOf(numberOfopponentGamesWonTextView));
        } else {
            playerCardNumber.setText("X");
            opponentCardNumber.setText("X");
            playerPointDeclarationTextView.setText("Stalemate!");
            opponentPointDeclarationTextView.setText("Stalemate!");
        }
    }

    public int flipResult(int playerCard, int opponentCard) {
        if (playerCard > opponentCard) return PLAYER_CARD_WINS;
        else if (opponentCard > playerCard) return OPPONENT_CARD_WINS;
        else return CARDS_DRAW;
    }

    public void setWarTextAndImageViews(boolean atWar) {
        if (!atWar) {
            playerPointDeclarationTextView.setVisibility(View.VISIBLE);
            opponentPointDeclarationTextView.setVisibility(View.VISIBLE);
            crossedSwords.setBackgroundResource(R.drawable.crossed_swords);

            playerCardNumber.setBackgroundResource(0);
            opponentCardNumber.setBackgroundResource(0);
        } else {
            playerPointDeclarationTextView.setVisibility(View.INVISIBLE);
            opponentPointDeclarationTextView.setVisibility(View.INVISIBLE);
            warText.setVisibility(View.VISIBLE);
            crossedSwords.setBackgroundResource(R.drawable.shotguns);
            drawRoundText.setText("WAR!");

            playerCardNumber.setBackgroundResource(R.drawable.blue_oval);
            opponentCardNumber.setBackgroundResource(R.drawable.red_oval);
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
        for (int i=2; i<15; i++) {
            arrayOfNumbersForClubs.add(i);
            arrayOfNumbersForSpades.add(i);
            arrayOfNumbersForDiamonds.add(i);
            arrayOfNumbersForHearts.add(i);
        }
    }

    public void removeCardBackGrounds() {
        playerCard.setBackgroundResource(R.drawable.card_border);
        opponentCard.setBackgroundResource(R.drawable.card_border);
    }

    public void setCardBackGroundsToImageViews() {
        playerCard.setBackgroundResource(R.drawable.card_logo);
        opponentCard.setBackgroundResource(R.drawable.card_logo);
    }

    public void resetGameViewsAndVars() {
        gameHasBegun = false;

        warCount = 0;
        totalPlayerScore = 0;
        totalOpponentScore = 0;
        totalCardsLeft = 52;

        playerGamesWonHeader.setText("0");
        opponentGamesWonHeader.setText("0");
        totalCardsLeftTextView.setText("52");

        setCardBackGroundsToImageViews();
        resetGame.setVisibility(View.INVISIBLE);

        playerCardNumber.setText("");
        opponentCardNumber.setText("");
        playerPointDeclarationTextView.setText("");
        opponentPointDeclarationTextView.setText("");
    }

    public void testScore() {
        playerCard.setOnClickListener(v-> {
            countDownCardsInDeck();
            setGameScore(PLAYER_CARD_WINS);
        });

        opponentCard.setOnClickListener(v-> {
            countDownCardsInDeck();
            setGameScore(OPPONENT_CARD_WINS);
        });
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