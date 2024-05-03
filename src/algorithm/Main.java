package algorithm;
import java.util.List;
import java.util.Scanner;

import util.Dictionary;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the start word:");
        String startWord = scanner.nextLine().toUpperCase();
        System.out.println("Enter the end word:");
        String endWord = scanner.nextLine().toUpperCase();
        System.out.println("Choose the algorithm:");
        System.out.println("1. UCS");
        System.out.println("2. Greedy Best First Search");
        System.out.println("3. A*");
        int algorithmChoice = scanner.nextInt();

        Dictionary dictionary = new Dictionary("util/Collins_Scrabble_Words_2019.txt");

        WordLadderSolver solver;
        switch (algorithmChoice) {
            case 1:
                UCSSolver ucsSolver = new UCSSolver(dictionary, startWord, endWord);
                solver = ucsSolver;
                break;
            case 2:
                solver = new GreedyBestFirstSolver(dictionary, startWord, endWord);
                break;
            case 3:
                solver = new AStarSolver(dictionary, startWord, endWord);
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
                scanner.close(); 
                return;
        }

        long startTime = System.nanoTime();
        List<String> path = solver.solve();
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000; // Convert ke milliseconds

        if (path != null) {
            System.out.println("Path:");
            for (String word : path) {
                System.out.println(word);
            }
            System.out.println();
            System.out.println("Number of step: " + (path.size() - 1));
            System.out.println("Number of visited nodes: " + solver.getVisitedNodes());
            System.out.println("Execution time: " + executionTime + " milliseconds");
        } else {
            System.out.println("No path found.");
        }

        scanner.close(); 
    }
}
