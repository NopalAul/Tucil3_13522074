import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the start word:");
        String startWord = scanner.nextLine().toLowerCase();
        System.out.println("Enter the end word:");
        String endWord = scanner.nextLine().toLowerCase();
        System.out.println("Choose the algorithm:");
        System.out.println("1. UCS");
        System.out.println("2. Greedy Best First Search");
        System.out.println("3. A*");
        int algorithmChoice = scanner.nextInt();

        Dictionary dictionary = new Dictionary("src/util/dictionary.txt");

        WordLadderSolver solver;
        switch (algorithmChoice) {
            case 1:
                // solver = new UCSSolver(dictionary, startWord, endWord);
                // print the printPriorityQueue method from UCSSolver
                UCSSolver ucsSolver = new UCSSolver(dictionary, startWord, endWord);
                // Print the priority queue before solving
                // ucsSolver.printPriorityQueue();
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
                scanner.close(); // Close the scanner
                return;
        }

        long startTime = System.nanoTime();
        List<String> path = solver.solve();
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000; // Convert to milliseconds

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

        scanner.close(); // Close the scanner
    }
}
