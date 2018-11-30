package com.alphexng.android.tic_tac_toe_game;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    //This is the Internal State of the Game.
    private BoardActivity mGame;

    //The buttons which make up the board.
    private Button mBoardButtons[];

    //The various texts displayed in the game for the Game stats.
    private TextView mInfoTextView;
    private TextView mScoreHuman;
    private TextView mScoreTie;
    private TextView mScoreComputer;
    private TextView mPlayer1Text;
    private TextView mPlayer2Text;

    //The counters for the win or ties
    private int mPlayer1Counter = 0;
    private int mTieCounter = 0;
    private int mPlayer2Counter = 0;

    //The variables to check if who plays first,
    // to check if it's computer or human mode
    // to check if game is over.
    private boolean mPlayer1First = true;
    private boolean mIsSinglePlayer = false;
    private boolean mIsPlayer1Turn = true;
    private boolean mGameOver = false;


    //Method called when this GameActivity is first created or launched
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);

        boolean mGameType = getIntent().getExtras().getBoolean("gameType");

        //Initialize the Buttons
        mBoardButtons = new Button[BoardActivity.getBOARD_SIZE()];
        mBoardButtons[0] = findViewById(R.id.btn00);
        mBoardButtons[1] = findViewById(R.id.btn01);
        mBoardButtons[2] = findViewById(R.id.btn02);
        mBoardButtons[3] = findViewById(R.id.btn10);
        mBoardButtons[4] = findViewById(R.id.btn11);
        mBoardButtons[5] = findViewById(R.id.btn12);
        mBoardButtons[6] = findViewById(R.id.btn20);
        mBoardButtons[7] = findViewById(R.id.btn21);
        mBoardButtons[8] = findViewById(R.id.btn22);

        //Setup the TextViews
        mInfoTextView = findViewById(R.id.game_stats);
        mScoreHuman = findViewById(R.id.score_human);
        mScoreTie = findViewById(R.id.score_ties);
        mScoreComputer = findViewById(R.id.score_computer);
        mPlayer1Text = findViewById(R.id.player1);
        mPlayer2Text = findViewById(R.id.player2);

        //Sets initial counter display values
        mScoreHuman.setText(Integer.toString(mPlayer1Counter));
        mScoreTie.setText(Integer.toString(mTieCounter));
        mScoreComputer.setText(Integer.toString(mPlayer2Counter));

        //create a new game object
        mGame = new BoardActivity();

        //starts a new game
        StartNewGame(mGameType);
    }

    //Take the user back to previous page
    public void back(View view) {
        GameActivity.this.finish();
    }

    //Calls the start new game method
    public void replay(View view) {
        StartNewGame(mIsSinglePlayer);
    }

    //Method called to start a new game
    //clears the board and resets all buttons / text
    //sets game over to false
    @SuppressLint("SetTextI18n")
    private void StartNewGame(boolean choose_turn) {

        //Call the choose_turn variable and assigns it the value of the mIsSinglePlayer
        //from the selection to determine if it is against human or computer.
        this.mIsSinglePlayer = choose_turn;

        mGame.clearBoard();
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        if (mIsSinglePlayer) {

            mPlayer1Text.setText("Human:");
            mPlayer2Text.setText("Computer:");

            if (mPlayer1First) {
                mInfoTextView.setText(R.string.first_human);
                mPlayer1First = false;
            } else {
                mInfoTextView.setText(R.string.turn_computer);
                int move = mGame.getComputerMove();
                setMove(BoardActivity.PLAYER_2, move);
                mPlayer1First = true;
            }
        } else {
            mPlayer1Text.setText("Player 1:");
            mPlayer2Text.setText("Player 2:");

            if (mPlayer1First) {
                mInfoTextView.setText(R.string.turn_player1);
                mPlayer1First = false;
            } else {
                mInfoTextView.setText(R.string.turn_player2);
                mPlayer1First = true;
            }
        }

        mGameOver = false;
    }

    //Sets move for the current player
    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == BoardActivity.PLAYER_1)
            //To set the display colour of X to green and O to Red.
            mBoardButtons[location].setTextColor(Color.GREEN);
        else mBoardButtons[location].setTextColor(Color.RED);
    }

    private class ButtonClickListener implements View.OnClickListener {
        final int location;

        ButtonClickListener(int location) {
            this.location = location;
        }

        @SuppressLint("SetTextI18n")
        public void onClick(View view) {
            if (!mGameOver) {
                if (mBoardButtons[location].isEnabled()) {
                    //This plays the single player or computer method.
                    if (mIsSinglePlayer) {
                        setMove(BoardActivity.PLAYER_1, location);

                        int winner = mGame.checkForWinner();

                        if (winner == 0) {
                            mInfoTextView.setText(R.string.turn_computer);
                            int move = mGame.getComputerMove();
                            setMove(BoardActivity.PLAYER_2, move);
                            winner = mGame.checkForWinner();
                        }

                        switch (winner) {
                            case 0:
                                mInfoTextView.setText(R.string.turn_human);
                                break;
                            case 1:
                                mInfoTextView.setText(R.string.result_tie);
                                mTieCounter++;
                                mScoreTie.setText(Integer.toString(mTieCounter));
                                mGameOver = true;
                                break;
                            case 2:
                                mInfoTextView.setText(R.string.result_human_wins);
                                mPlayer1Counter++;
                                mScoreHuman.setText(Integer.toString(mPlayer1Counter));
                                mGameOver = true;
                                break;
                            default:
                                mInfoTextView.setText(R.string.result_computer_wins);
                                mPlayer2Counter++;
                                mScoreComputer.setText(Integer.toString(mPlayer2Counter));
                                mGameOver = true;
                                break;
                        }
                    }

                    //This plays the Multi player or Human Method
                    else {
                        if (mIsPlayer1Turn) {
                            setMove(BoardActivity.PLAYER_1, location);
                        } else
                            setMove(BoardActivity.PLAYER_2, location);

                        int winner = mGame.checkForWinner();

                        switch (winner) {
                            case 0:
                                if (mIsPlayer1Turn) {
                                    mInfoTextView.setText(R.string.turn_player2);
                                    mIsPlayer1Turn = false;
                                } else {
                                    mInfoTextView.setText(R.string.turn_player1);
                                    mIsPlayer1Turn = true;
                                }
                                break;
                            case 1:
                                mInfoTextView.setText(R.string.result_tie);
                                mTieCounter++;
                                mScoreTie.setText(Integer.toString(mTieCounter));
                                mGameOver = true;
                                break;
                            case 2:
                                mInfoTextView.setText(R.string.result_player1_wins);
                                mPlayer1Counter++;
                                mScoreHuman.setText(Integer.toString(mPlayer1Counter));
                                mGameOver = true;
                                break;
                            default:
                                mInfoTextView.setText(R.string.result_player2_wins);
                                mPlayer2Counter++;
                                mScoreComputer.setText(Integer.toString(mPlayer2Counter));
                                mGameOver = true;
                                break;
                        }

                    }
                }

            }
        }
    }


}

