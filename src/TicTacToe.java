import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener{
    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel text_field = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;
    boolean hasWinner = false;
    boolean isDraw = false;
    String winner;
    String playerX = "X";
    String playerO = "O";
    String playerTurnO = "Player O turn";
    String playerTurnX = "Player X turn";
    String playerWinnerX = "Player X Wins!";
    String playerWinnerO = "Player O Wins!";


    TicTacToe() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        text_field.setBackground(new Color(25,25,25));
        text_field.setForeground(new Color(25,255,0));
        text_field.setFont(new Font("Ink Free", Font.BOLD,75));
        text_field.setHorizontalAlignment(JLabel.CENTER);
        setGameTextField();

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0,0,800,100);

        button_panel.setLayout(new GridLayout(3,3));
        button_panel.setBackground(new Color(150,150,150));

        initializedButtons();

        title_panel.add(text_field);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);

        firstTurn(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if(e.getSource() == buttons[i]) {
                if(player1_turn) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(255,0,0));
                        buttons[i].setText(playerX);
                        buttons[i].setBackground(Color.ORANGE);
                        text_field.setText(playerTurnX);
                        player1_turn = false;

                        check();
                    }
                }
                else {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(0,0,255));
                        buttons[i].setText(playerO);
                        buttons[i].setBackground(Color.CYAN);
                        text_field.setText(playerTurnO);
                        player1_turn = true;

                        check();
                    }
                }
            }
        }
    }

    public void displayWinner() {
        JOptionPane pane = new JOptionPane();
        int dialogResult;
        if (hasWinner) {
            dialogResult = JOptionPane.showConfirmDialog(
                    pane,
                    winner + " Play again?", "GAME OVER",
                    JOptionPane.YES_NO_OPTION
            );

            if (dialogResult == JOptionPane.YES_OPTION) resetGame();
            else System.exit(0);
        }
        else if (isDraw) {
            dialogResult = JOptionPane.showConfirmDialog(
                    pane,
                    "DRAW! Play again?", "GAME OVER",
                    JOptionPane.YES_NO_OPTION
            );

            if (dialogResult == JOptionPane.YES_OPTION) resetGame();
            else System.exit(0);
        }
    }

    public void initializedButtons() {
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }
    }

    public void firstTurn(boolean replay) {
        if (!replay) {
            try {
                enableFields(false);
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (random.nextInt(2) == 0) {
            enableFields(true);
            player1_turn = true;
            text_field.setText(playerTurnX);
        }
        else {
            enableFields(true);
            player1_turn = false;
            text_field.setText(playerTurnO);
        }
    }

    public void checkWinningConditions(String player, int[] indices) {
        if (player.equals("X")) {
            if (
                    (buttons[indices[0]].getText().equals(player)) &&
                    (buttons[indices[1]].getText().equals(player)) &&
                    (buttons[indices[2]].getText().equals(player))
            ) {
                xWins(indices[0],indices[1],indices[2]);
            }
        }
        else {
            if (
                    (buttons[indices[0]].getText().equals(player)) &&
                    (buttons[indices[1]].getText().equals(player)) &&
                    (buttons[indices[2]].getText().equals(player))
            ) {
                oWins(indices[0],indices[1],indices[2]);
            }
        }
    }

    public void check() {

        // ROW 0 1 2
        int[] indices = {0, 1, 2};
        checkWinningConditions(playerX, indices);
        checkWinningConditions(playerO, indices);

        // ROW 3 4 5
        indices = new int[]{3, 4, 5};
        checkWinningConditions(playerX, indices);
        checkWinningConditions(playerO, indices);

        // ROW 6 7 8
        indices = new int[]{6, 7 ,8};
        checkWinningConditions(playerX, indices);
        checkWinningConditions(playerO, indices);

        // COLUMN 0 3 6
        indices = new int[]{0, 3, 6};
        checkWinningConditions(playerX, indices);
        checkWinningConditions(playerO, indices);

        // COLUMN 6 7 8
        indices = new int[]{6, 7 ,8};
        checkWinningConditions(playerX, indices);
        checkWinningConditions(playerO, indices);

        // COLUMN 1 4 7
        indices = new int[]{1,4,7};
        checkWinningConditions(playerX, indices);
        checkWinningConditions(playerO, indices);

        // COLUMN 2 5 8
        indices = new int[]{2,5,8};
        checkWinningConditions(playerX, indices);
        checkWinningConditions(playerO, indices);

        // DIAGONAL 0 4 8
        indices = new int[]{0,4,8};
        checkWinningConditions(playerX, indices);
        checkWinningConditions(playerO, indices);

        // DIAGONAL 6 4 2
        indices = new int[]{6,4,2};
        checkWinningConditions(playerX, indices);
        checkWinningConditions(playerO, indices);

        checkDraw();
        displayWinner();

    }

    public void xWins(int a, int b, int c) {
        enableFields(false);
        hasWinner = true;

        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        winner = playerWinnerX;
        text_field.setText(winner);
    }

    public void oWins(int a, int b, int c) {
        hasWinner = true;
        enableFields(false);

        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        winner = playerWinnerO;
        text_field.setText(winner);
    }

    public void setGameTextField() {
        text_field.setText("Tic-Tac-Toe Game");
        text_field.setOpaque(true);
    }

    public void resetGame() {
        hasWinner = false;
        isDraw = false;
        resetFields();
        setGameTextField();
        firstTurn(true);
    }

    public void resetFields() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(Color.WHITE);
        }
    }

    public void checkDraw() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().equals(playerX) || buttons[i].getText().equals(playerO)) {
                count++;
            }
        }

        if (count == 9) {
            isDraw = true;
        }
    }

    public void enableFields(boolean set) {
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(set);
            buttons[i].setBackground(Color.WHITE);
        }
    }
}
