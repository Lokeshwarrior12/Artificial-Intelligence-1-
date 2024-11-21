import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class task1 {

    private static double[] priorProb = { 0.1, 0.2, 0.4, 0.2, 0.1 };
    private static double[] cherryProbs = { 1.0, 0.75, 0.50, 0.25, 0.0 };
    private static double[] limeProbs = { 0.0, 0.25, 0.50, 0.75, 1.0 };

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Task1 <observations>");
            System.exit(1);
        }

        String observations = args[0];
        String output = computePosterior(observations);

        writeToFile(output);
    }

    private static String computePosterior(String observations) {
        StringBuilder output = new StringBuilder();
        int observationsLength = observations.length();
        output.append("Observation sequence Q: ").append(observations).append("\nLength of Q: ")
                .append(observationsLength);

        double[] bagProbs = Arrays.copyOf(priorProb, priorProb.length);
        double tempSumCherry = 0.0, tempSumLime = 0.0;

        for (int j = 0; j < 5; j++) {
            tempSumCherry += bagProbs[j] * cherryProbs[j];
            tempSumLime += bagProbs[j] * limeProbs[j];
        }

        for (int i = 1; i <= observationsLength; i++) {
            output.append("\n\nAfter Observation ").append(i).append(" = ").append(observations.charAt(i - 1))
                    .append("\n");

            if (observations.charAt(i - 1) == 'C') {
                updateProbabilities(bagProbs, cherryProbs, tempSumCherry, output);
            } else {
                updateProbabilities(bagProbs, limeProbs, tempSumLime, output);
            }

            tempSumCherry = 0.0;
            tempSumLime = 0.0;

            for (int j = 0; j < 5; j++) {
                tempSumCherry += bagProbs[j] * cherryProbs[j];
                tempSumLime += bagProbs[j] * limeProbs[j];
            }

            output.append("\n").append("\nProbability that the next candy we pick will be C, given Q: ")
                    .append(formatOutput(tempSumCherry));
            output.append("\nProbability that the next candy we pick will be L, given Q: ")
                    .append(formatOutput(tempSumLime));
        }

        return output.toString();
    }

    private static void updateProbabilities(double[] bagProbs, double[] flavorProbs, double tempSum,
            StringBuilder output) {
        for (int j = 0; j < 5; j++) {
            double temp = (bagProbs[j] * flavorProbs[j]) / tempSum;
            bagProbs[j] = temp;
            output.append("\nP(h").append(j + 1).append(" | Q) = ").append(formatOutput(temp));
        }
    }

    private static String formatOutput(double value) {
        if (Math.abs(value) < 1e-12) {
            return "0.0";
        } else if (Math.abs(value) < 0.0001 || Math.abs(value) > 1000.0) {
            return String.format("%12.12e", value);
        } else {
            String formatted = String.format("%.12f", value);
            if (!formatted.equals("0.0")) {
                formatted = formatted.replaceAll("(\\.\\d*?)0+$", "$1");
            }
            return formatted;
        }
    }

    private static void writeToFile(String output) {
        try {
            FileWriter fileWriter = new FileWriter("result.txt");
            fileWriter.write(output);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}