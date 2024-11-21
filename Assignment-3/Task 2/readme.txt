
Name	: Lokeshwar Kodipunjula
email	: lxk5121@mavs.uta.edu
Student ID	: 1002175121
Language used	: Java
Version		: java 21.0.2
Compatibility	: compatible with Omega Environment


Stucture of the code:-
1.Imports: The code starts with importing necessary Java utility classes - ArrayList and List from the java.util package.

2.Class Declaration: The BayesianNetwork class is declared, containing methods for initializing the Bayesian network, calculating probabilities, and setting/getting values for different variables in the network.

3.Constructor and Initialization Method: The class has a constructor public BayesianNetwork(double[] values) for initializing the network with given probabilities. It also has an init() method for setting initial probabilities based on the input values.

4.Probability Calculation Methods: The class contains methods like nextValues() for calculating probabilities recursively, containsNone() and getNoneIndex() for handling cases where values are yet to be assigned, and setValue() for setting probabilities based on conditions.

5.Main Method: The main() method serves as the entry point of the program. It validates the input arguments, processes the given and query values, creates an instance of BayesianNetwork, calculates the probabilities, and prints the result.


Key Components:

_init_ method:
Initializes probability tables for Burglary, Earthquake, Alarm, JohnCalls, and MaryCalls.

computeProbability method:
Calculates joint probabilities for the events based on boolean inputs and conditional dependencies.

fill_missing_values method:
Ensures all necessary event keys are present and adds default values if missing.

aggregate_probabilities method:
Aggregates and normalizes probabilities across scenarios for conditional probability calculations.




How to Impliment:-

1. Prerequisites: Ensure java is installed.
2. Running the Application:
	-> open the terminal, Move to the directory where the file is located.
	
Running the File: java Task2.java <arguments>
	Example commands:
	-java Task2.java
	-java Task2.java Bf At Mt
	-java Task2.java Jt given Et Bf
	-java Task2.java Jf Mt given Et