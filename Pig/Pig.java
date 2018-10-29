import java.util.Scanner;

/*
 * PIG: two players continually roll a 6-sided die. Each roll they get is added to their score for that round.
 * If they roll a 1, their turn ends and they lose all the points for that round. 
 * The player can choose at any time during their turn to bank their points, ending their turn and adding their points to their overall score.
 * First to 100 points or higher wins. 
 */
public class Pig {

	private static final int NUM_SIDES = 6; // 6 sides on the die
	private static final int NUM_PLAYERS = 2;
	private static final int WIN_SCORE = 100;
	private static int[] playerScores;

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		String answer = "y"; // signifies that player wants to keep playing again after a game finishes
		while (answer.equalsIgnoreCase("y")) {
			playerScores = new int[NUM_PLAYERS];
			System.out.println("Score to win = " + WIN_SCORE + ".");
			while (win(playerScores) == -1) { // no player has won yet
				for (int i = 0; i < playerScores.length; i++) {
					System.out.println("\nPlayer " + (i + 1) + "'s turn. ");
					playTurn(i + 1);
					if (playerScores[i] >= WIN_SCORE) {
						break; //keeps it from going through the others players' turns if one of the first players wins
					}
				}
			}
			System.out.println("Player " + win(playerScores) + " wins! ");
			System.out.println("Play again? Yes(y) or No(n)");
			answer = scan.nextLine();
			while (!(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n"))) {
				System.out.println("Error: Please press \"y\" or \"n\".");
				System.out.print("Play again? Yes(y) or No(n) ");
				answer = scan.nextLine();
			}
			if (answer.equalsIgnoreCase("n")) {
				endGame();
			}
		}
	}

	// goes through one player's turn (choose to bank or roll until turn ends through rolling a 1 or banking points)
	private static boolean playTurn(int player) {
		String choice = chooseOption();
		int roll = 0;
		int roundScore = 0;
		while (!choice.equalsIgnoreCase("bank")) {
			roll = rollDie();
			System.out.println("You rolled a " + roll + "!");
			if (roll == 1) {
				System.out.println("Your turn is over. ");
				return false;
			} else {
				roundScore += roll;
				choice = chooseOption();
			}
		}
		playerScores[player - 1] += roundScore;
		System.out.println("Player " + player + " has chosen to bank their points for this round. Total score: "
				+ playerScores[player - 1]);
		return true;
	}

	//prompts user to input their move; can bank points they've gotten that round or choose to roll again
	private static String chooseOption() {
		String choice;
		Scanner scan = new Scanner(System.in);
		System.out.print("Please type \"roll\" to roll the die, or \"bank\" to bank your points for this round. ");
		choice = scan.nextLine().trim();
		while (!isValidChoice(choice)) {
			System.out.println("Error: Please type \"roll\" or \"bank\".");
			System.out.print("Please type \"roll\" to roll the die, or \"bank\" to bank your points for this round. ");
			choice = scan.nextLine().trim();
		}

		return choice;
	}
	//ends the game if user types quit; otherwise, checks if user input equals roll or bank (returns false if invalid input)
	private static boolean isValidChoice(String choice) {
		if(choice.equalsIgnoreCase("quit")) {
			endGame();
		}
		return choice.equalsIgnoreCase("roll") || choice.equalsIgnoreCase("bank");
	}

	private static void endGame() {
		System.out.println("Goodbye.");
		System.exit(0);
		
	}

	//checks if any other players have reached the score required to win the game and returns the player's number; otherwise, if no one has won, returns -1
	private static int win(int[] playerScores) {
		for (int i = 0; i < playerScores.length; i++) {
			if (playerScores[i] >= WIN_SCORE) {
				return i;
			}
		}
		return -1;
	}
	
	//randomly rolls a die with NUM_SIDES number of sides and returns the result 
	private static int rollDie() {
		return (int) (1 + Math.random() * NUM_SIDES);
	}
}
