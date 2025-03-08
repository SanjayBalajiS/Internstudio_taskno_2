import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Number extends JFrame {
    private int secretNumber;
    private int attempts;
    private int bestScore = Integer.MAX_VALUE;
    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JLabel bestScoreLabel;
    private JButton guessButton;
    private JButton restartButton;
    private JComboBox<String> difficultyBox;
    private int maxRange = 100; // Default difficulty: Medium

    public Number() {
        setTitle("🎮 Advanced Number Guessing Game");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));
        getContentPane().setBackground(new Color(44, 62, 80)); // Dark theme

        // Difficulty selection
        String[] difficulties = {"Easy (1-50)", "Medium (1-100)", "Hard (1-200)"};
        difficultyBox = new JComboBox<>(difficulties);
        difficultyBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDifficulty();
            }
        });
        add(difficultyBox);

        // Message label
        messageLabel = new JLabel("Guess a number between 1 and " + maxRange + "!", SwingConstants.CENTER);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(messageLabel);

        // Input field
        guessField = new JTextField();
        add(guessField);

        // Guess button
        guessButton = new JButton("Guess");
        guessButton.setBackground(new Color(52, 152, 219));
        guessButton.setForeground(Color.WHITE);
        add(guessButton);

        // Labels for attempts & best score
        attemptsLabel = new JLabel("Attempts: 0", SwingConstants.CENTER);
        attemptsLabel.setForeground(Color.WHITE);
        add(attemptsLabel);

        bestScoreLabel = new JLabel("Best Score: --", SwingConstants.CENTER);
        bestScoreLabel.setForeground(Color.YELLOW);
        add(bestScoreLabel);

        // Restart button (hidden initially)
        restartButton = new JButton("Play Again");
        restartButton.setBackground(new Color(231, 76, 60));
        restartButton.setForeground(Color.WHITE);
        restartButton.setVisible(false);
        add(restartButton);

        // Generate the random number
        resetGame();

        // Action listener for Guess button
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        // Action listener for Restart button
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        setVisible(true);
    }

    private void resetGame() {
        Random random = new Random();
        secretNumber = random.nextInt(maxRange) + 1;
        attempts = 0;
        messageLabel.setText("Guess a number between 1 and " + maxRange + "!");
        guessField.setText("");
        guessField.setEditable(true);
        guessButton.setEnabled(true);
        restartButton.setVisible(false);
        attemptsLabel.setText("Attempts: 0");
    }

    private void handleGuess() {
        try {
            int userGuess = Integer.parseInt(guessField.getText());
            attempts++;

            if (userGuess < 1 || userGuess > maxRange) {
                messageLabel.setText("🚫 Enter a number between 1 and " + maxRange + "!");
                return;
            }

            if (userGuess < secretNumber) {
                messageLabel.setText("📉 Too low! Try again.");
            } else if (userGuess > secretNumber) {
                messageLabel.setText("📈 Too high! Try again.");
            } else {
                messageLabel.setText("🎉 Correct! You guessed it in " + attempts + " attempts.");
                guessField.setEditable(false);
                guessButton.setEnabled(false);
                restartButton.setVisible(true);

                // Update best score
                if (attempts < bestScore) {
                    bestScore = attempts;
                    bestScoreLabel.setText("🏆 Best Score: " + bestScore);
                }
            }

            attemptsLabel.setText("Attempts: " + attempts);
        } catch (NumberFormatException e) {
            messageLabel.setText("❌ Enter a valid number!");
        }
    }

    private void setDifficulty() {
        String difficulty = (String) difficultyBox.getSelectedItem();
        switch (difficulty) {
            case "Easy (1-50)":
                maxRange = 50;
                break;
            case "Medium (1-100)":
                maxRange = 100;
                break;
            case "Hard (1-200)":
                maxRange = 200;
                break;
        }
        resetGame();
    }

    public static void main(String[] args) {
        new Number();
    }
}
