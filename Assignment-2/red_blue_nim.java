import java.util.Scanner;

public class red_blue_nim {
    static int redMarbles;
    static int blueMarbles;
    static boolean isComputerTurn;
    static int searchDepth;
    static boolean isMisereVersion;

    public static void main(String[] args) {
        System.out.println("CUSTOM RED-BLUE NIM GAME");
        parseCommandLineArguments(args);
        playRedBlueNim();
    }

    static void parseCommandLineArguments(String[] args) {
        if (args.length < 2) {
            System.out.println(
                    "Usage: java CustomRedBlueNim <red-marbles> <blue-marbles> [<version>] [<first-player>] [<search-depth>]");
            System.exit(1);
        }
        redMarbles = Integer.parseInt(args[0]);
        blueMarbles = Integer.parseInt(args[1]);

        if (args.length > 2) {
            isMisereVersion = args[2].equalsIgnoreCase("misere");
        } else {
            isMisereVersion = false;
        }

        if (args.length > 3) {
            isComputerTurn = args[3].equalsIgnoreCase("computer");
        } else {
            isComputerTurn = true;
        }

        if (args.length > 4) {
            searchDepth = Integer.parseInt(args[4]);
        } else {
            searchDepth = 1;
        }
    }

    static void playRedBlueNim() {
        Scanner scanner = new Scanner(System.in);
        int currentPlayer = (isComputerTurn) ? 1 : 0;

        while (redMarbles >= 1 && blueMarbles >= 1) {
            System.out.printf("\nCurrent State: %d Red & %d Blue Marbles.\n", redMarbles, blueMarbles);
            if (currentPlayer == 1) {
                int[] bestMove = findBestMove(searchDepth, true);
                if (bestMove[0] == 0) {
                    System.out.printf("Computer removes %d red marble(s).\n", bestMove[1]);
                    redMarbles -= bestMove[1];
                } else {
                    System.out.printf("Computer removes %d blue marble(s).\n", bestMove[1]);
                    blueMarbles -= bestMove[1];
                }
                currentPlayer = 0;
            } else {
                boolean validMove = false;
                while (!validMove) {
                    System.out.print("Your turn (format: pile color[red/blue] number[1/2]): ");
                    try {
                        String pileColor = scanner.next();
                        int number = scanner.nextInt();
                        if (("red".equals(pileColor) && number >= 1 && number <= 2 && redMarbles >= number) ||
                                ("blue".equals(pileColor) && number >= 1 && number <= 2 && blueMarbles >= number)) {
                            if ("red".equals(pileColor)) {
                                redMarbles -= number;
                            } else {
                                blueMarbles -= number;
                            }
                            validMove = true;
                        } else {
                            System.out.println("Invalid move. Try again.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid move (e.g., 'red 1').");
                        scanner.nextLine(); // Consume the invalid input
                    }
                }
                currentPlayer = 1;
            }
        }

        int score = 2 * redMarbles + 3 * blueMarbles;
        if (isMisereVersion) {
            if (currentPlayer == 1) {
                System.out.printf("Game Over! Computer wins with %d points.\n", score);
            } else {
                System.out.printf("Game Over! You win with %d points.\n", score);
            }
        } else {
            if (currentPlayer == 1) {
                System.out.printf("Game Over! Computer loses with -%d points.\n", score);
            } else {
                System.out.printf("Game Over! You lose with -%d points.\n", score);
            }
        }
    }

    static int[] findBestMove(int depth, boolean computerTurn) {
        int[] bestMove = new int[3];
        int bestScore = (computerTurn) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if (depth == 0 || redMarbles < 1 || blueMarbles < 1) {
            int scoreMultiplier = (isMisereVersion != computerTurn) ? -1 : 1;
            bestScore = scoreMultiplier * (2 * redMarbles + 3 * blueMarbles);
        } else {
            int i = 1;
            while (i <= 2 && beta > alpha) {
                int pile = 0;
                while (pile < 2 && beta > alpha) {
                    if ((pile == 0 && redMarbles >= i) || (pile == 1 && blueMarbles >= i)) {
                        int tempRed = redMarbles - (pile == 0 ? i : 0);
                        int tempBlue = blueMarbles - (pile == 1 ? i : 0);
                        redMarbles -= (pile == 0 ? i : 0);
                        blueMarbles -= (pile == 1 ? i : 0);
                        int[] result = findBestMove(depth - 1, !computerTurn);
                        redMarbles += (pile == 0 ? i : 0);
                        blueMarbles += (pile == 1 ? i : 0);
                        int tempScore = result[2];
                        if ((computerTurn && tempScore > bestScore) || (!computerTurn && tempScore < bestScore)) {
                            bestScore = tempScore;
                            bestMove[0] = pile;
                            bestMove[1] = i;
                        }
                        if (computerTurn) {
                            alpha = Math.max(alpha, bestScore);
                        } else {
                            beta = Math.min(beta, bestScore);
                        }
                    }
                    pile++;
                }
                i++;
            }
        }
        bestMove[2] = bestScore;
        return bestMove;
    }
}
