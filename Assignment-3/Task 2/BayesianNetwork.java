import java.util.ArrayList;
import java.util.List;

public class BayesianNetwork {

    private double[] values;

    public BayesianNetwork(double[] values) {
        this.values = values;
    }

    public double init(double[] values) {
        return (setValue("B", values[0], -1, -1) *
                setValue("E", values[1], -1, -1) *
                setValue("A|B,E", values[2], values[0], values[1]) *
                setValue("J|A", values[3], values[2], -1) *
                setValue("M|A", values[4], values[2], -1));
    }

    public double nextValues(double[] values) {
        if (containsNone(values)) {
            int noneIndex = getNoneIndex(values);
            double[] nextItems1 = values.clone();
            nextItems1[noneIndex] = 1;
            double val1 = nextValues(nextItems1);
            double[] nextItems2 = values.clone();
            nextItems2[noneIndex] = 0;
            double val2 = nextValues(nextItems2);
            return val1 + val2;
        } else {
            return init(values);
        }
    }

    private boolean containsNone(double[] values) {
        for (double value : values) {
            if (value == -1) {
                return true;
            }
        }
        return false;
    }

    private int getNoneIndex(double[] values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    public double setValue(String val, double val1, double val2, double val3) {
        double temp;
        if (val.equals("B")) {
            if (val1 == 1) {
                return 0.001;
            } else {
                return 0.999;
            }
        }
        if (val.equals("E")) {
            if (val1 == 1) {
                return 0.002;
            } else {
                return 0.998;
            }
        }
        if (val.equals("J|A")) {
            if (val2 == 1) {
                temp = 0.9;
            } else {
                temp = 0.05;
            }
            if (val1 == 1) {
                return temp;
            } else {
                return 1 - temp;
            }
        }
        if (val.equals("M|A")) {
            if (val2 == 1) {
                temp = 0.7;
            } else {
                temp = 0.01;
            }
            if (val1 == 1) {
                return temp;
            } else {
                return 1 - temp;
            }
        }
        if (val.equals("A|B,E")) {
            if (val2 == 1 && val3 == 1) {
                temp = 0.95;
            } else if (val2 == 1 && val3 == 0) {
                temp = 0.94;
            } else if (val2 == 0 && val3 == 1) {
                temp = 0.29;
            } else {
                temp = 0.001;
            }
            if (val1 == 1) {
                return temp;
            } else {
                return 1 - temp;
            }
        }
        return -1;
    }

    public double[] getValue(String value) {
        double[] result = new double[5];
        if (value.contains("Bt")) {
            result[0] = 1;
        } else if (value.contains("Bf")) {
            result[0] = 0;
        } else {
            result[0] = -1;
        }
        if (value.contains("Et")) {
            result[1] = 1;
        } else if (value.contains("Ef")) {
            result[1] = 0;
        } else {
            result[1] = -1;
        }
        if (value.contains("At")) {
            result[2] = 1;
        } else if (value.contains("Af")) {
            result[2] = 0;
        } else {
            result[2] = -1;
        }
        if (value.contains("Jt")) {
            result[3] = 1;
        } else if (value.contains("Jf")) {
            result[3] = 0;
        } else {
            result[3] = -1;
        }
        if (value.contains("Mt")) {
            result[4] = 1;
        } else if (value.contains("Mf")) {
            result[4] = 0;
        } else {
            result[4] = -1;
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length == 0 || args.length > 6) {
            System.out.println("Arguments must lie between 1 and 6.");
            return; // Terminate the program
        }

        boolean givenValue = false;
        List<String> observations = new ArrayList<>();
        List<String> query = new ArrayList<>();

        for (String arg : args) {
            if (arg.equals("given")) {
                givenValue = true;
            }
            query.add(arg);
            if (givenValue) {
                observations.add(arg);
            }
        }

        BayesianNetwork bnet = new BayesianNetwork(new double[] { 0, 0, 0, 0, 0 });

        if (!query.isEmpty()) {
            double numerator = bnet.nextValues(bnet.getValue(String.join(",", query)));
            double denominator = 1;
            if (!observations.isEmpty()) {
                denominator = bnet.nextValues(bnet.getValue(String.join(",", observations)));
            }
            System.out.printf("Probability = %.10f%n", numerator / denominator);
        } else {
            System.out.println("Invalid query string");
        }
    }
}