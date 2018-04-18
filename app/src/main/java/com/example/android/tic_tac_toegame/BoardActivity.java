package com.example.android.tic_tac_toegame;

import android.support.v7.app.AppCompatActivity;

import java.util.Random;

public class BoardActivity extends AppCompatActivity {

    public static final char PLAYER_1 = 'X'; // sets the first player as X
    public static final char PLAYER_2 = 'O'; // sets the second player as O
    private final static int BOARD_SIZE = 9;
    private static final char EMPTY_SPACE = ' ';
    private final char[] mBoard;
    private final Random mRand;

    public BoardActivity() {
        mBoard = new char[BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++)
            mBoard[i] = EMPTY_SPACE;

        //Declares a random movement on the board.
        mRand = new Random();
    }

    public static int getBOARD_SIZE() {
        //Return the size of the board
        return BOARD_SIZE;
    }

    //Clears the Board of all X's and O's
    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = EMPTY_SPACE;
        }
    }

    //Sets the given player and the given location on the game board
    //the location must be available or the board will not be changed.
    public void setMove(char player, int location) {
        mBoard[location] = player;
    }

    //Return the best move for the computer to make. The SetMove method should be called to
    //actually make the computer move to that location.
    public int getComputerMove() {
        int move;

        //First check if there is a move O can make to make a win
        for (int i = 0; i < getBOARD_SIZE(); i++) {
            if (mBoard[i] != PLAYER_1 && mBoard[i] != PLAYER_2) {
                char curr = mBoard[i];
                mBoard[i] = PLAYER_2;
                if (checkForWinner() == 3) {
                    setMove(PLAYER_2, i);
                    return i;
                } else mBoard[i] = curr;
            }
        }

        // See if there is a move O can make to block X from winning
        for (int i = 0; i < getBOARD_SIZE(); i++) {
            if (mBoard[i] != PLAYER_1 && mBoard[i] != PLAYER_2) {
                char curr = mBoard[i];
                mBoard[i] = PLAYER_1;
                if (checkForWinner() == 2) {
                    setMove(PLAYER_2, i);
                    return i;
                } else mBoard[i] = curr;
            }
        }

        //Generate Random move
        do {
            move = mRand.nextInt(getBOARD_SIZE());
        } while (mBoard[move] == PLAYER_1 || mBoard[move] == PLAYER_2);
        setMove(PLAYER_2, move);
        return move;
    }

    /*
    Checks for a winner and return status value indicating who has won.
    Return 0 if no winner or tie yet, 1 if it is a tie, 2 if X won
    and 3 if O won.
     */
    public int checkForWinner() {
        //Checks for horizontal wins
        for (int i = 0; i <= 6; i += 3) {
            if (mBoard[i] == PLAYER_1 &&
                    mBoard[i + 1] == PLAYER_1 &&
                    mBoard[i + 2] == PLAYER_1)
                return 2;
            if (mBoard[i] == PLAYER_2 &&
                    mBoard[i + 1] == PLAYER_2 &&
                    mBoard[i + 2] == PLAYER_2)
                return 3;
        }

        //Checks for vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == PLAYER_1 &&
                    mBoard[i + 3] == PLAYER_1 &&
                    mBoard[i + 6] == PLAYER_1)
                return 2;
            if (mBoard[i] == PLAYER_2 &&
                    mBoard[i + 3] == PLAYER_2 &&
                    mBoard[i + 6] == PLAYER_2)
                return 3;
        }

        //Checks for diagonal wins
        if (mBoard[0] == PLAYER_1 &&
                mBoard[4] == PLAYER_1 &&
                mBoard[8] == PLAYER_1 ||
                mBoard[2] == PLAYER_1 &&
                        mBoard[4] == PLAYER_1 &&
                        mBoard[6] == PLAYER_1)
            return 2;

        if (mBoard[0] == PLAYER_2 &&
                mBoard[4] == PLAYER_2 &&
                mBoard[8] == PLAYER_2 ||
                mBoard[2] == PLAYER_2 &&
                        mBoard[4] == PLAYER_2 &&
                        mBoard[6] == PLAYER_2)
            return 3;

        //Checks for a tie
        for (int i = 0; i < getBOARD_SIZE(); i++) {
            //if we find a number then no one has won yet
            if (mBoard[i] != PLAYER_1 && mBoard[i] != PLAYER_2)
                return 0;
        }
        //If we make it through the previous loops ana all places are taken, it's a tie
        return 1;
    }


}