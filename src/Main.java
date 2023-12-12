import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of decision alternatives:");
        int numAlternatives = scanner.nextInt();

        System.out.println("Enter the number of states of nature:");
        int numStates = scanner.nextInt();

        // Create a matrix to store payoffs for each decision alternative and state of nature
        int[][] payoffs = new int[numAlternatives][numStates];

        // Enter payoffs for each decision alternative and state of nature
        for (int i = 0; i < numAlternatives; i++) {
            for (int j = 0; j < numStates; j++) {
                System.out.println("Enter payoff for alternative " + (i + 1) +
                        " and state of nature " + (j + 1) + ":");
                payoffs[i][j] = scanner.nextInt();
            }
        }

        // Perform decision-making under uncertainty
        System.out.println("1. Maximax (Optimistic) Decision:");
        maximaxDecision(payoffs);

        System.out.println("\n2. Maximin (Pessimistic) Decision:");
        maximinDecision(payoffs);

        System.out.println("\n3. Criterion of Realism (Hurwicz) Decision:");
        System.out.println("Enter the coefficient (alpha) for the Hurwicz criterion (between 0 and 1):");
        double alpha = scanner.nextDouble();
        hurwiczDecision(payoffs, alpha);

        System.out.println("\n4. Equally Likely (Laplace) Decision:");
        laplaceDecision(payoffs);

        System.out.println("\n5. Minimax Regret Decision:");
        minimaxRegretDecision(payoffs);

        scanner.close();
    }

    // Maximax (Optimistic) Decision
    private static void maximaxDecision(int[][] payoffs) {
        int[] maxPayoffs = new int[payoffs.length];

        for (int i = 0; i < payoffs.length; i++) {
            int max = payoffs[i][0];
            for (int j = 1; j < payoffs[i].length; j++) {
                if (payoffs[i][j] > max) {
                    max = payoffs[i][j];
                }
            }
            maxPayoffs[i] = max;
        }

        int maxMaximax = maxPayoffs[0];
        int bestAlternativeMaximax = 1;

        for (int i = 1; i < maxPayoffs.length; i++) {
            if (maxPayoffs[i] > maxMaximax) {
                maxMaximax = maxPayoffs[i];
                bestAlternativeMaximax = i + 1;
            }
        }

        System.out.println("Best alternative (Maximax): Alternative " + bestAlternativeMaximax +
                " with payoff " + maxMaximax);
    }

    // Maximin (Pessimistic) Decision
    private static void maximinDecision(int[][] payoffs) {
        int[] minPayoffs = new int[payoffs.length];

        for (int i = 0; i < payoffs.length; i++) {
            int min = payoffs[i][0];
            for (int j = 1; j < payoffs[i].length; j++) {
                if (payoffs[i][j] < min) {
                    min = payoffs[i][j];
                }
            }
            minPayoffs[i] = min;
        }

        int minMaximin = minPayoffs[0];
        int bestAlternativeMaximin = 1;

        for (int i = 1; i < minPayoffs.length; i++) {
            if (minPayoffs[i] < minMaximin) {
                minMaximin = minPayoffs[i];
                bestAlternativeMaximin = i + 1;
            }
        }

        System.out.println("Best alternative (Maximin): Alternative " + bestAlternativeMaximin +
                " with payoff " + minMaximin);
    }

    // Criterion of Realism (Hurwicz) Decision
    private static void hurwiczDecision(int[][] payoffs, double alpha) {
        double[] hurwiczPayoffs = new double[payoffs.length];

        for (int i = 0; i < payoffs.length; i++) {
            double max = payoffs[i][0];
            double min = payoffs[i][0];
            for (int j = 1; j < payoffs[i].length; j++) {
                if (payoffs[i][j] > max) {
                    max = payoffs[i][j];
                }
                if (payoffs[i][j] < min) {
                    min = payoffs[i][j];
                }
            }
            hurwiczPayoffs[i] = alpha * max + (1 - alpha) * min;
        }

        double maxHurwicz = hurwiczPayoffs[0];
        int bestAlternativeHurwicz = 1;

        for (int i = 1; i < hurwiczPayoffs.length; i++) {
            if (hurwiczPayoffs[i] > maxHurwicz) {
                maxHurwicz = hurwiczPayoffs[i];
                bestAlternativeHurwicz = i + 1;
            }
        }

        System.out.println("Best alternative (Hurwicz): Alternative " + bestAlternativeHurwicz +
                " with payoff " + maxHurwicz);
    }

    // Equally Likely (Laplace) Decision
    private static void laplaceDecision(int[][] payoffs) {
        double[] averagePayoffs = new double[payoffs.length];

        for (int i = 0; i < payoffs.length; i++) {
            double sum = 0;
            for (int j = 0; j < payoffs[i].length; j++) {
                sum += payoffs[i][j];
            }
            averagePayoffs[i] = sum / payoffs[i].length;
        }

        double maxLaplace = averagePayoffs[0];
        int bestAlternativeLaplace = 1;

        for (int i = 1; i < averagePayoffs.length; i++) {
            if (averagePayoffs[i] > maxLaplace) {
                maxLaplace = averagePayoffs[i];
                bestAlternativeLaplace = i + 1;
            }
        }

        System.out.println("Best alternative (Laplace): Alternative " + bestAlternativeLaplace +
                " with average payoff " + maxLaplace);
    }

    // Minimax Regret Decision
    private static void minimaxRegretDecision(int[][] payoffs) {
        int[] maxPayoffs = new int[payoffs.length];

        for (int i = 0; i < payoffs.length; i++) {
            int max = payoffs[i][0];
            for (int j = 1; j < payoffs[i].length; j++) {
                if (payoffs[i][j] > max) {
                    max = payoffs[i][j];
                }
            }
            maxPayoffs[i] = max;
        }

        int[] regrets = new int[payoffs.length];

        for (int i = 0; i < payoffs.length; i++) {
            int maxRegret = 0;
            for (int j = 0; j < payoffs[i].length; j++) {
                int regret = maxPayoffs[j] - payoffs[i][j];
                if (regret > maxRegret) {
                    maxRegret = regret;
                }
            }
            regrets[i] = maxRegret;
        }

        int minRegret = regrets[0];
        int bestAlternativeRegret = 1;

        for (int i = 1; i < regrets.length; i++) {
            if (regrets[i] < minRegret) {
                minRegret = regrets[i];
                bestAlternativeRegret = i + 1;
            }
        }

        System.out.println("Best alternative (Minimax Regret): Alternative " + bestAlternativeRegret +
                " with regret " + minRegret);
    }
}