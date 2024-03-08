package com.example.noughtsandcrossess;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int NOUGHT = 0;
    public static final int CROSS = 1;
    public static final int EMPTY = 2;
    public static int COUNT_TURN = 0;

    public static int DIFFICULTY_LEVEL = 1;
    public static int ANYBODYWIN = 0;

    int[][] gameBoard;
    int i, j, k = 0;
    Button[][] squares;

    Button easy;

    Button medium;

    Button hard;
    TextView textMessage;
    TextView dificulty;
    ComputerPlayer mComputerPlayer;

    // Called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        easy = findViewById(R.id.Easy);
        medium = findViewById(R.id.Medium);
        hard = findViewById(R.id.Hard);
        setBoard();

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIFFICULTY_LEVEL = 1;
                dificulty.setText("Difficulty Level - Easy");
                Toast.makeText(MainActivity.this, "Difficulty Level Changed to Easy", Toast.LENGTH_SHORT).show();
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIFFICULTY_LEVEL = 2;
                dificulty.setText("Difficulty Level - Medium");
                Toast.makeText(MainActivity.this, "Difficulty Level Changed to Medium", Toast.LENGTH_SHORT).show();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIFFICULTY_LEVEL = 3;
                dificulty.setText("Difficulty Level - Hard");
                Toast.makeText(MainActivity.this, "Difficulty Level Changed to Hard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.add("Start New Game");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        setBoard();
        return true;
    }


    // Set up the game board.
    private void setBoard() {
        COUNT_TURN = 0;
        mComputerPlayer = new ComputerPlayer();
        squares = new Button[4][4];
        gameBoard = new int[4][4];
        textMessage = findViewById(R.id.dialogue);
        dificulty = findViewById(R.id.difficult);
        squares[1][3] = findViewById(R.id.one);
        squares[1][2] = findViewById(R.id.two);
        squares[1][1] = findViewById(R.id.three);
        squares[2][3] = findViewById(R.id.four);
        squares[2][2] = findViewById(R.id.five);
        squares[2][1] = findViewById(R.id.six);
        squares[3][3] = findViewById(R.id.seven);
        squares[3][2] = findViewById(R.id.eight);
        squares[3][1] = findViewById(R.id.nine);

        for (i = 1; i <= 3; i++) {
            for (j = 1; j <= 3; j++)
                gameBoard[i][j] = EMPTY;  //clear the board to empty
        }
        textMessage.setText("Click a square to start. \uD83D\uDC46");
        if(DIFFICULTY_LEVEL==1){
            dificulty.setText("Difficulty Level - Easy");
        }else if(DIFFICULTY_LEVEL==1){
            dificulty.setText("Difficulty Level - Medium");
        }else{
            dificulty.setText("Difficulty Level - Hard");
        }
        // add the click listeners for each button
        for (i = 1; i <= 3; i++) {
            for (j = 1; j <= 3; j++) {
                squares[i][j].setOnClickListener(new MyClickListener(i, j));
                squares[i][j].setText(" ");
                squares[i][j].setEnabled(true);
            }
        }
    }

    // check the board to see if someone has won
    private boolean checkBoard() {
        boolean gameOver = false;
        //Check for a line of noughts
        if ((gameBoard[1][1] == NOUGHT && gameBoard[2][2] == NOUGHT && gameBoard[3][3] == NOUGHT)
                || (gameBoard[1][3] == NOUGHT && gameBoard[2][2] == NOUGHT && gameBoard[3][1] == NOUGHT)
                || (gameBoard[1][2] == NOUGHT && gameBoard[2][2] == NOUGHT && gameBoard[3][2] == NOUGHT)
                || (gameBoard[1][3] == NOUGHT && gameBoard[2][3] == NOUGHT && gameBoard[3][3] == NOUGHT)
                || (gameBoard[1][1] == NOUGHT && gameBoard[1][2] == NOUGHT && gameBoard[1][3] == NOUGHT)
                || (gameBoard[2][1] == NOUGHT && gameBoard[2][2] == NOUGHT && gameBoard[2][3] == NOUGHT)
                || (gameBoard[3][1] == NOUGHT && gameBoard[3][2] == NOUGHT && gameBoard[3][3] == NOUGHT)
                || (gameBoard[1][1] == NOUGHT && gameBoard[2][1] == NOUGHT && gameBoard[3][1] == NOUGHT)) {
            textMessage.setText("Congratulations!, You win! \uD83D\uDE0D");
            gameOver = true;
        }
        // Check for a line of crosses
        else if ((gameBoard[1][1] == CROSS && gameBoard[2][2] == CROSS && gameBoard[3][3] == CROSS)
                || (gameBoard[1][3] == CROSS && gameBoard[2][2] == CROSS && gameBoard[3][1] == CROSS)
                || (gameBoard[1][2] == CROSS && gameBoard[2][2] == CROSS && gameBoard[3][2] == CROSS)
                || (gameBoard[1][3] == CROSS && gameBoard[2][3] == CROSS && gameBoard[3][3] == CROSS)
                || (gameBoard[1][1] == CROSS && gameBoard[1][2] == CROSS && gameBoard[1][3] == CROSS)
                || (gameBoard[2][1] == CROSS && gameBoard[2][2] == CROSS && gameBoard[2][3] == CROSS)
                || (gameBoard[3][1] == CROSS && gameBoard[3][2] == CROSS && gameBoard[3][3] == CROSS)
                || (gameBoard[1][1] == CROSS && gameBoard[2][1] == CROSS && gameBoard[3][1] == CROSS)) {
            textMessage.setText("You lose!, Try again. \uD83D\uDE2D");
            gameOver = true;
        } else {
            boolean empty = false;
            for (i = 1; i <= 3; i++) {
                for (j = 1; j <= 3; j++) {
                    if (gameBoard[i][j] == EMPTY) {
                        empty = true;
                        break;
                    }
                }
            }
            if (!empty) {
                gameOver = true;
                textMessage.setText("It's a draw!, No winners. \uD83E\uDD2D");
            }
        }
        return gameOver;
    }

    class MyClickListener implements View.OnClickListener {
        int x;
        int y;

        public MyClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            if (squares[x][y].isEnabled()) {
                squares[x][y].setEnabled(false);
                squares[x][y].setText("O");
                gameBoard[x][y] = 0;
                textMessage.setText("");
                if (!checkBoard()) {
                    mComputerPlayer.takeTurn();
                }
            }
        }
    }

    private class ComputerPlayer {
        public void takeTurn() {
            if (DIFFICULTY_LEVEL == 1) {
                if (blockPlayerWin()) {
                    return;
                } else {
                    // Otherwise just pick a random square
                    Random rand = new Random();

                    int a = 1 + rand.nextInt(3);
                    int b = 1 + rand.nextInt(3);

                    while (gameBoard[a][b] != EMPTY) {
                        a = 1 + rand.nextInt(3);
                        b = 1 + rand.nextInt(3);
                    }
                    markSquare(a, b);

                }
            }

            if (DIFFICULTY_LEVEL == 2) {
                if (checkComputerWin()) {
                    return;
                } else if (blockPlayerWin()) {
                    return;
                } else {
                    emptyRandomMark();
                }
            }
            if (DIFFICULTY_LEVEL == 3) {
                if (checkComputerWin()) {
                    return;
                } else if (blockPlayerWin()) {
                    return;
                } else {
                    Hard();
                }
            }
        }

        // Checking every square for a possible human player win and block it
        private boolean blockPlayerWin() {
            if (gameBoard[1][1] == EMPTY &&
                    ((gameBoard[1][2] == NOUGHT && gameBoard[1][3] == NOUGHT) ||
                            (gameBoard[2][2] == NOUGHT && gameBoard[3][3] == NOUGHT) ||
                            (gameBoard[2][1] == NOUGHT && gameBoard[3][1] == NOUGHT))) {
                markSquare(1, 1);
            } else if (gameBoard[1][2] == EMPTY &&
                    ((gameBoard[2][2] == NOUGHT && gameBoard[3][2] == NOUGHT) ||
                            (gameBoard[1][1] == NOUGHT && gameBoard[1][3] == NOUGHT))) {
                markSquare(1, 2);
            } else if (gameBoard[1][3] == EMPTY &&
                    ((gameBoard[1][1] == NOUGHT && gameBoard[1][2] == NOUGHT) ||
                            (gameBoard[3][1] == NOUGHT && gameBoard[2][2] == NOUGHT) ||
                            (gameBoard[2][3] == NOUGHT && gameBoard[3][3] == NOUGHT))) {
                markSquare(1, 3);
            } else if (gameBoard[2][3] == EMPTY && DIFFICULTY_LEVEL == 3 &&
                    ((gameBoard[1][3] == NOUGHT && gameBoard[3][3] == NOUGHT) ||
                            (gameBoard[2][2] == NOUGHT && gameBoard[2][1] == NOUGHT))) {
                markSquare(2, 3);
            } else if (gameBoard[3][2] == EMPTY && DIFFICULTY_LEVEL == 3 &&
                    ((gameBoard[3][1] == NOUGHT && gameBoard[3][3] == NOUGHT) ||
                            (gameBoard[2][2] == NOUGHT && gameBoard[1][2] == NOUGHT))) {
                markSquare(3, 2);
            } else if (gameBoard[2][1] == EMPTY && DIFFICULTY_LEVEL == 3 &&
                    ((gameBoard[3][1] == NOUGHT && gameBoard[1][1] == NOUGHT) ||
                            (gameBoard[2][2] == NOUGHT && gameBoard[2][3] == NOUGHT))) {
                markSquare(2, 1);
            } else if (gameBoard[3][3] == EMPTY && DIFFICULTY_LEVEL == 3 &&
                    ((gameBoard[1][3] == NOUGHT && gameBoard[2][3] == NOUGHT) ||
                            (gameBoard[3][2] == NOUGHT && gameBoard[3][1] == NOUGHT) ||
                            (gameBoard[2][2] == NOUGHT && gameBoard[1][1] == NOUGHT))) {
                markSquare(3, 3);
            } else if (gameBoard[3][1] == EMPTY && DIFFICULTY_LEVEL == 3 &&
                    ((gameBoard[3][3] == NOUGHT && gameBoard[3][2] == NOUGHT) ||
                            (gameBoard[1][1] == NOUGHT && gameBoard[2][1] == NOUGHT) ||
                            (gameBoard[2][2] == NOUGHT && gameBoard[1][3] == NOUGHT))) {
                markSquare(3, 1);
            } else if (gameBoard[2][2] == EMPTY && DIFFICULTY_LEVEL == 3 &&
                    ((gameBoard[3][3] == NOUGHT && gameBoard[1][1] == NOUGHT) ||
                            (gameBoard[1][3] == NOUGHT && gameBoard[3][1] == NOUGHT) ||
                            (gameBoard[2][3] == NOUGHT && gameBoard[2][1] == NOUGHT) ||
                            (gameBoard[1][2] == NOUGHT && gameBoard[3][1] == NOUGHT))) {
                markSquare(2, 2);
            } else
                return false;

            return true;
        }

        private boolean checkComputerWin() {

            if (gameBoard[1][1] == EMPTY &&
                    ((gameBoard[1][2] == CROSS && gameBoard[1][3] == CROSS) ||
                            (gameBoard[2][2] == CROSS && gameBoard[3][3] == CROSS) ||
                            (gameBoard[2][1] == CROSS && gameBoard[3][1] == CROSS))) {
                markSquare(1, 1);
            } else if (gameBoard[1][2] == EMPTY &&
                    ((gameBoard[2][2] == CROSS && gameBoard[3][2] == CROSS) ||
                            (gameBoard[1][1] == CROSS && gameBoard[1][3] == CROSS))) {
                markSquare(1, 2);
            } else if (gameBoard[1][3] == EMPTY &&
                    ((gameBoard[1][1] == CROSS && gameBoard[1][2] == CROSS) ||
                            (gameBoard[3][1] == CROSS && gameBoard[2][2] == CROSS) ||
                            (gameBoard[2][3] == CROSS && gameBoard[3][3] == CROSS))) {
                markSquare(1, 3);

            } else if (gameBoard[2][3] == EMPTY &&
                    ((gameBoard[1][3] == CROSS && gameBoard[3][3] == CROSS) ||
                            (gameBoard[2][2] == CROSS && gameBoard[2][1] == CROSS))) {
                markSquare(2, 3);

            } else if (gameBoard[3][3] == EMPTY &&
                    ((gameBoard[2][3] == CROSS && gameBoard[1][3] == CROSS) ||
                            (gameBoard[3][2] == CROSS && gameBoard[3][1] == CROSS) ||
                            (gameBoard[2][2] == CROSS && gameBoard[1][1] == CROSS))) {
                markSquare(3, 3);

            } else if (gameBoard[3][2] == EMPTY &&
                    ((gameBoard[2][2] == CROSS && gameBoard[1][2] == CROSS) ||
                            (gameBoard[3][3] == CROSS && gameBoard[3][1] == CROSS))) {
                markSquare(3, 2);

            } else if (gameBoard[3][1] == EMPTY &&
                    ((gameBoard[1][1] == CROSS && gameBoard[2][1] == CROSS) ||
                            (gameBoard[2][2] == CROSS && gameBoard[1][3] == CROSS) ||
                            (gameBoard[3][2] == CROSS && gameBoard[3][3] == CROSS))) {
                markSquare(3, 1);

            } else if (gameBoard[2][1] == EMPTY &&
                    ((gameBoard[1][1] == CROSS && gameBoard[3][1] == CROSS) ||
                            (gameBoard[2][2] == CROSS && gameBoard[2][3] == CROSS))) {
                markSquare(2, 1);

            } else if (gameBoard[2][2] == EMPTY &&
                    (       (gameBoard[1][3] == CROSS && gameBoard[3][1] == CROSS) ||
                            (gameBoard[1][2] == CROSS && gameBoard[3][2] == CROSS)||
                            (gameBoard[1][1] == CROSS && gameBoard[3][3] == CROSS)||
                            (gameBoard[2][1] == CROSS && gameBoard[2][3] == CROSS))) {
                markSquare(2, 2);

            }else
                return false;

            return true;
        }

        private void markSquare(int x, int y) {
            squares[x][y].setEnabled(false);
            squares[x][y].setText("X");
            gameBoard[x][y] = CROSS;
            checkBoard();
        }

        private void emptyRandomMark() {
            // Otherwise just pick a random square
            Random rand = new Random();

            int a = 1 + rand.nextInt(3);
            int b = 1 + rand.nextInt(3);
            while (gameBoard[a][b] != EMPTY) {
                a = 1 + rand.nextInt(3);
                b = 1 + rand.nextInt(3);
            }
            markSquare(a, b);
        }

        private boolean Hard() {
            COUNT_TURN++;
            if (COUNT_TURN == 1 && (gameBoard[2][2] == EMPTY || gameBoard[2][2] == CROSS)) {
                if (gameBoard[2][2] == EMPTY && (gameBoard[1][3] != NOUGHT && gameBoard[1][1] != NOUGHT && gameBoard[3][1] != NOUGHT && gameBoard[3][3] != NOUGHT )) {
                    markSquare(2, 2);
                    Toast.makeText(MainActivity.this, "2,2", Toast.LENGTH_SHORT).show();
                } else if ((gameBoard[1][3] != CROSS || gameBoard[1][1] != CROSS || gameBoard[3][3] != CROSS
                        || gameBoard[3][1] != CROSS)
                        && (gameBoard[1][3] != NOUGHT || gameBoard[1][1] != NOUGHT || gameBoard[3][3] != NOUGHT
                        || gameBoard[3][1] != NOUGHT)) {


                    if (gameBoard[2][3] == NOUGHT && gameBoard[1][3] == EMPTY) {
                        markSquare(1, 3);
                        return true;
                    }
                    if (gameBoard[1][2] == NOUGHT && gameBoard[1][1] == EMPTY) {
                        markSquare(1, 1);
                        return true;
                    }
                    if (gameBoard[2][1] == NOUGHT && gameBoard[3][1] == EMPTY) {
                        markSquare(3, 1);
                        return true;
                    }
                    if (gameBoard[3][2] == NOUGHT && gameBoard[3][3] == EMPTY) {
                        markSquare(3, 3);
                        return true;
                    }
                    if (gameBoard[3][3] == NOUGHT && gameBoard[1][1] == EMPTY) {
                        markSquare(1, 1);
                        return true;
                    }
                    if (gameBoard[3][1] == NOUGHT && gameBoard[1][3] == EMPTY) {
                        markSquare(1, 3);
                        return true;
                    }
                    if (gameBoard[1][1] == NOUGHT && gameBoard[3][3] == EMPTY) {
                        markSquare(3, 3);
                        return true;
                    }
                    if (gameBoard[1][3] == NOUGHT && gameBoard[3][1] == EMPTY) {
                        markSquare(3, 1);
                        return true;
                    }

                } else if (gameBoard[1][3] == CROSS) {
                    if (gameBoard[2][3] == EMPTY) {
                        markSquare(2, 3);
                        return true;
                    } else {
                        markSquare(1, 2);
                        return true;
                    }
                } else if (gameBoard[1][1] == CROSS) {
                    if (gameBoard[1][2] == EMPTY) {
                        markSquare(1, 2);
                        return true;
                    } else {
                        markSquare(2, 1);
                        return true;
                    }
                } else if (gameBoard[3][1] == CROSS) {
                    if (gameBoard[2][1] == EMPTY) {
                        markSquare(2, 1);
                        return true;
                    } else {
                        if(gameBoard[1][3]==EMPTY) {
                            markSquare(1, 3);
                            return true;
                        }else{
                            markSquare(3, 2);
                        }
                    }
                } else if (gameBoard[3][3] == CROSS) {
                    if (gameBoard[2][3] == EMPTY) {
                        markSquare(2, 3);
                        return true;
                    } else {
                        markSquare(3, 2);
                        return true;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "hit3", Toast.LENGTH_SHORT).show();
                    emptyRandomMark();
                }
            } else {
                if (COUNT_TURN == 1) {


                    Random rand = new Random();
                    int randomNumber = 1 + rand.nextInt(100); // Generate a random number between 1 and 100

                    int remainder = randomNumber % 4; // Find the remainder when the number is divided by 4

                    switch (remainder) {
                        case 0: // If the number is divisible by 4
                            markSquare(1, 3);
                            break;
                        case 1: // If the remainder is 1
                            markSquare(1, 1);
                            break;
                        case 2: // If the remainder is 2
                            markSquare(3, 1);
                            break;
                        case 3: // If the remainder is 3
                            markSquare(3, 3);
                            break;
                    }

                } else {
                        //Toast.makeText(MainActivity.this, "count 1", Toast.LENGTH_SHORT).show();
                        if(gameBoard[1][1]==EMPTY && gameBoard[1][3]==NOUGHT && gameBoard[3][1]==CROSS) {
                            markSquare(1, 1);
                        }else if(gameBoard[1][1]==EMPTY && gameBoard[3][1]==NOUGHT && gameBoard[1][3]==CROSS) {
                            markSquare(1, 1);
                        }else if(gameBoard[3][3]==EMPTY && gameBoard[1][3]==NOUGHT && gameBoard[3][1]==CROSS) {
                            markSquare(3, 3);
                        }else if(gameBoard[3][3]==EMPTY && gameBoard[3][1]==NOUGHT && gameBoard[1][3]==CROSS) {
                            markSquare(3, 3);
                        }else if(gameBoard[1][3]==EMPTY && gameBoard[3][3]==NOUGHT && gameBoard[1][1]==CROSS) {
                            markSquare(1, 3);
                        }else if(gameBoard[1][3]==EMPTY && gameBoard[1][1]==NOUGHT && gameBoard[3][3]==CROSS) {
                            markSquare(1, 3);
                        }else if(gameBoard[3][3]==EMPTY && gameBoard[3][3]==NOUGHT && gameBoard[1][1]==CROSS) {
                            markSquare(3, 3);
                        }else if(gameBoard[3][3]==EMPTY && gameBoard[3][3]==NOUGHT && gameBoard[1][31]==CROSS) {
                            markSquare(3, 3);
                        } else {
                            if(gameBoard[1][1]==EMPTY && gameBoard[3][2]== NOUGHT && gameBoard[2][1]== NOUGHT){
                                markSquare(1, 1);
                            }else if(gameBoard[1][3]==EMPTY && gameBoard[2][1]== NOUGHT && gameBoard[1][2]== NOUGHT){
                                markSquare(1, 3);
                            }else if(gameBoard[3][3]==EMPTY && gameBoard[2][1]== NOUGHT && gameBoard[2][3]== NOUGHT){
                                markSquare(3, 3);
                            }else {
                                if(gameBoard[3][3]==EMPTY && gameBoard[3][3]== NOUGHT){
                                    markSquare(3, 1);
                                }else if(gameBoard[2][1]==EMPTY && gameBoard[3][1]== NOUGHT){
                                    markSquare(3, 1);
                                }else if(gameBoard[2][1]==EMPTY && gameBoard[1][1]== NOUGHT){
                                    markSquare(2, 1);
                                }else{
                                    if(gameBoard[2][3]==EMPTY ) {
                                        markSquare(2, 3);
                                    }else{
                                        markSquare(2, 2);
                                    }
                                }
                            }
                        }
                    return true;
                }
            }
            return true;
        }
    }
}
