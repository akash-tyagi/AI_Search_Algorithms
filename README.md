<h1>AI_Search_Algorithms</h1>
Goal is to compare the performance of Breadth-First-Search, Depth-first-Search and Greedy Best-First-Search on a given navigation problem i.e path finding.

For GBFS, the heuristic used for implementing the priority queue is Straight Line Distance (Euclidean Distance).

The model used for testing purposes is given in file ATM.graph.txt which looks like the figure given in file NavigationMap.png

In general, any type of txt file can be given, only thing to make sure is that the format is same as given in ATM.graph.txt
ex:
vertices: \<total_vertices\>
\<vertex ID\> \<X coordinate\> \<Y coordinate\>
...
edges: \<total_edges\>
\<edge id\> \<Vertex Id\> \<Vertex ID\>
...

the graph considered here is undirected. ex:
vertices: 275
0 1 1
1 1 2
2 1 3
...
edges: 641
0 17 18
1 37 38
2 56 57
...

<h1>How to run the Program:</h1>
To run the program provide the following arguments<br/>
AbsolutePath FrontierNumber StartCoordinateX StartCoordinateY GoalCoordinateX GoalCoordinateY DebugFlag
Ex:/home/akash/Downloads/ATM.graph.txt 3 1 20 20 1 0<br/>

<p>Frontier Types Number</p>
<ol>
<li>Queue: For BFS</li>
<li>Stack: For DFS</li>
<li>Priority Queue: For GBFS</li>
</ol>

<p>Debug Flag</p>
<ol>
<li value="0">Debugging Off</li>
<li>Debugging On: will print the whole trace of the frontier working</li>
</ol>
