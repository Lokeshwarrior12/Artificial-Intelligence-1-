Name: Lokeshwar Kodipunjula
Student ID: 1002175121

# Red-Blue Num Game

## Overview
This project implements a command-line version of the red_blue_num game in Java. Red-Blue Nim is a variant of the classic game of Nim, where players take turns removing marbles from two different piles - one red and one blue. The game has two versions: standard and misère. In the standard version, the player who removes the last marble loses the game, while in the misère version, the player who removes the last marble wins.

## Features
- Command-line interface for easy interaction.
- Support for both standard and misère versions of the game.
- Option to choose the starting player.
- Adjustable search depth for the computer player's moves.

Program with the following command-line arguments:
- `<red-marbles>`: Number of red marbles at the beginning of the game.
- `<blue-marbles>`: Number of blue marbles at the beginning of the game.
- `<version>`: (Optional) Game version (`standard` or `misere`). Default is `standard`.
- `<first-player>`: (Optional) Starting player (`computer` or `human`). Default is `computer`.
- `<search-depth>`: (Optional) Depth of search for computer player's moves. Default is `1`.


## How to Run

#Requirements:
To compile the Java code, you must have the Java Development Kit (JDK) installed on your system. If you haven't installed it yet, you can download and install it from the official Oracle website or use OpenJDK, which is an open-source alternative.

Once the JDK is installed, you can compile the Java source code using the javac command. Here's how to do it:

1. Open a terminal or command prompt.

2. Navigate to the directory containing your Java source file (red_blue_num.java in this case).

3. Run the following command to compile the Java source file:

"javac RedBlueNim.java"

#This command will compile the red_blue_num.java file and generate the corresponding bytecode file (red_blue_num.class).

==>After successful compilation, you can run the Java program using the java command followed by the class name. For example:

"java RedBlueNim 5 7 standard computer 10"

