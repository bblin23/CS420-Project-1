Rough Description:

8 Puzzle solver. 

http://www.aiai.ed.ac.uk/~gwickler/images/8-puzzle-states.png

Given 8 puzzles, this program uses either the Hamming or Manhattan distance heuristics to solve. The user may enter string representations where "012345678" is

0 1 2
3 4 5
6 7 8

or use the multiple file inputs, as well as solve random inputs. 8 puzzles have unsolvable configurations when the number of inversions are odd. See https://www.geeksforgeeks.org/check-instance-8-puzzle-solvable/ for more details.

----
Instructions:

1. Run Eclipse
	Specific version - Version: Neon.1a Release (4.6.1)

2. Import > General > Projects from Folder or Archive > Archive... > BoltonLin_420p1

3. Run CS420_Project1/src/def/Driver.java

4. 3 Types of input, 1 for String Input (enter as a line e.g. 120345678), 2 for file input (include extension), and 3 for random solve.

5. Enter heuristic, Hamming refers to h1 in prompt, and Manhattan Distance refers to h2 in prompt.

6. Retry if desired by entering Y, otherwise N.
