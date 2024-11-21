Student Name: Lokeshwar Kodipunjula
UTA ID: 1002175121

##Programming Language: Python 3.1

##Running the Code:
1. Make sure you have Python 3.1 installed on your system.

2. Clone the repository:

3. Navigate to the project directory:

4. Run the main script `find_route.py` with the following command:
    How to run the code:
    Open a terminal and navigate to the directory

    ```
    python find_route.py <input_filename> <origin_city> <destination_city> [heuristic_filename]

    Run the main script `find_route.py` with the following command:
    ##Uninformed Search:
   
    python find_route.py input1.txt Bremen Kassel   OR
    python find_route.py input1.txt London Kassel

    ##Informed Search:
  
    python find_route.py input1.txt Bremen Kassel h_kassel.txt

    ```

5. View the output which includes the nodes popped, expanded, and generated during the search, along with the total distance and route between the source and destination cities.

Note: Compilation is not needed for this task as Python is an interpreted language. Make sure you have the required input files (`input1.txt` and `h_kassel.txt` if using A* search) in the project directory before running the code.


## Features

- **Uninformed Search (UCS)**: Finds the shortest path between two cities without using any additional information about the problem other than the graph topology and edge costs.
- **Informed Search (A* Search)**: Utilizes additional heuristic information to guide the search towards the goal more efficiently.
- **Input**: The system accepts a text file containing the graph representation with edge costs and optionally a heuristic file for A* search.
- **Output**: It outputs the nodes popped, expanded, and generated during the search process, along with the total distance and route between the source and destination cities.

## Code Structure
1. Classes:

    i.Vertex: Represents a city vertex in the graph. It stores information about the city, its parent vertex, the cost to reach it, and its heuristic value.
  ii.Map: Represents the graph/map containing city connections and distances between them.

2. Functions:

load_graph_names(filename): Reads the input file containing city connections and loads them into the map.
load_heuristic_values(filename): Reads the heuristic file containing heuristic values for cities and loads them into a dictionary.
ucs(graph, start_city, goal_city): Performs Uniform Cost Search (UCS) to find the shortest path between the start and goal cities.
a_star(graph, start_city, goal_city, heuristic_data): Performs A* Search to find the shortest path between the start and goal cities using the provided heuristic data.
main(): Main function to execute the route finding system. It parses command-line arguments, loads the graph and heuristic data, performs the search algorithm, and prints the results.