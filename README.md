# ScatterPlot-java-library
Implementation of Scatter-Plot-Search in java
Written by Anurag Choudhary
2/19/2016
achdhry3@illinois.edu

----------------

Read this file to know exactly what's going on with the ScatPlotRank Java library.
----------------

Problem statement:
----------------

We have a representation of a Scatter Plot in memory where dataset at least has two continuous floating point columns and one of the column is used to denote class. Imagine the data to be something like this:

Sepal Length,Sepal Width,Petal Length,Petal Width,Class
4.6,3.2,1.4,0.2,Iris-setosa<br>
5.3,3.7,1.5,0.2,Iris-setosa<br>
5,3.3,1.4,0.2,Iris-setosa<br>
7,3.2,4.7,1.4,Iris-versicolor<br>
6.4,3.2,4.5,1.5,Iris-versicolor<br>
.....

Any of the two columns excluding Class can be considered for the x and the y axes while Class becomes the z-axis.

We have to be able to provide users to explore the dataset and gain insight from it by making visual queries to it.

On the backend, we expect to receive coordinates of the different shapes/regions that the user draws on some kind of a representative plot.

We should then calculate the ranks of the classes inside the region based on their frequencies. Of course, there might be other metrics (concentration/spread) but for now, we're limited to rank the classes sheerly by the number of normalized points inside the user's region which we shall now call as the target.

Also, the current implementation only takes in rectangles.

Once we have the ranks, we should be able to plot all the points of the top k ranks of the classes.
----------------------------------------

Algorithm
----------

The algorithm that I have implemented in this library is based on first indexing the dataset and then run queries on the indexed data.

* 	I first make two separate x and y lists ordered in the ascending order 
* 	I make sure that every element of the list point to the corresponding tuple
* 	When I get the user query, I binary search both lists individually for the end points of the rectangle and then intersect both lists.

	So for example: <br>
	If the lists looked like this:<br>
	x, y, class<br>
	1,2,'A'<br>
	5,3,'A'<br>
	3,2,'C'<br>

	My x-list would look like:<br>
	1 -> 1,2,'A'<br>
	3 -> 3,2,'C'<br>
	5 -> 5,3,'A'<br>

	And my y-list would look like:<br>
	2 -> 1,2,'A'<br>
	2 -> 3,2,'C'<br>
	5 -> 5,3,'A'<br>

	Let's say the user queries the rectangle (1,2) -> (5,3)
	My x-set would be:<br>
	1 -> 1,2,'A'<br>
	3 -> 3,2,'C'<br>

	And my y-set would be:<br>
	2 -> 1,2,'A'<br>
	2 -> 3,2,'C'<br>
	5 -> 5,3,'A'

	The intersection would lead to:<br>
	1 -> 1,2,'A'<br>
	3 -> 3,2,'C'<br>

* 	The next step is to count the the number of points in each classes. Here it 	makes sense to favour those classes which have almost all their points in the region. 
	So even before indexing our points, I attach a normalized score to every tuple based on the total number of points in that class.
	Thus, the dataset with the attached scores look like:

	x,y,class,score<br>
	1,2,'A',0.5<br>
	5,3,'A',0.5 <br>
	3,2,'C',1.0<br>

* 	Thus, we count each point for each class by their weights and then add them to a tree map (Category: Total-score)
	So our tree map looks like this:<br>

	A: 0.5<br>
	C: 1.0<br>

* 	We then send back the result and the front-end can do the job of sorting the result classes based on their scores.

----------------------

CODE STRUCTURE
--------------

I have tried my best to make meaningful directory and class names to make it explicit as to what their purposes are. But I'll still glance over them here to clear any confusion:

*	The main directory is the 'core' directory. It has three files:
	a) Controller
	The outer most class is the Controller class. It has self-explanatory functions which call other functions in the library.
	b)  Rectangle
	Used to store user's input information. Also has methods to set the attributes
	c) ScatterPlotRank
	Main logic class. It calls all helper logic functions to deliver the result. It accepts indexed lists (as a part of the DataStore)

*	The 'database' directory is used to save the in-memory-database. It has a few classes of its own:
	a) Tuple
	Used to store the tuples and has overloaded constructors for various scenarios.<br>
	1,2,'A'
	b) ListElement
	Used to store an element of a list which points to a tuple.
	1->1,2,'A'<br>
	c) ScatterPlotIndex
	Has functions to index the points and an arraylist of indexed lists of all columns of the dataset
	d) CategoryPointsCount
	An encapsulation of a function to add the scores to every tuple in the datastore.
	e) Datastore
	As the name suggests, it is used to house all the tuples and lists that will get passed to the main logic function.

* 	The other directories are dataloader and util which both have very self-		explanatory classes and can be used to abstract the way data is loaded/			generated to fill the datastore.

----------------------------------------------------------------------------



