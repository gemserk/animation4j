Description
------------

This project provides an easy way to animate values between time intervals.

Features
------------

* Animation and transitions of any object you want.
* API based on CSS3 definitions for animations and transitions.
* Optional event listeners for animation and transition state changes.
* Low dependency to animation4j classes by providing black box interaction (optional).

Introduction
------------

To know how to use the project, you should know a bit about the following classes. First of all, the next examples will be using a class named Vector2f defined as:

	Vector2f {

		float x, y;
	
	}

(note: I removed some boilerplate code for the documentation examples, like class and fields visibility declaration for example)

### Transition<T>

This class provides a way to perform a transition of an object from an starting value to an ending value in a specified time. The API looks like this:

	Transition<T> {

		// returns current value of the transition
		T get(); 

		// sets the current value of the transition	
		set(T t); 

		// starts a transition to the specified value in the specified time
		set(T t, float time); 

		// returns true whenever the transition was started, false otherwise
		boolean isStarted();

		// returns true whenever the transition was finished, false otherwise.
		boolean isFinished();

		// updates the transition the specified time.
		update(float delta)

	}

### TransitionBuilder

Provides an easy way to build Transitions of objects. It is based on the fact that Transitions work over mutable objects. To create a new transition you specify the object to be modified, the type converter to use to modify it, the starting and ending values and the duration of the transition.

The next example shows how to create a Transition<Vector2f>:

	Vector2f myVector = new Vector2f();
	Vector2fConverter vector2fConverter = new Vector2fConverter();
	Transitions.transition(myVector, vector2fConverter) // This line asks for a TransitionBuilder
		.start(new Vector2f(10, 10))  // This line defines the starting value of the transition
		.end(5f, new Vector2f(50, 50));  // This line defines the ending value and the duration of the transition.

TransitionBuilder allows also to specify the values in float arrays instead having to create new objects, for example:

	Transitions.transition(myVector, vector2fConverter) // This line asks for a TransitionBuilder
		.start(10f, 10f)  // This line defines the starting value of the transition
		.end(5f, 50f, 50f);  // This line defines the ending value and the duration of the transition.

### TypeConverter

Provides a way to let the framework copy float values to your object and vice versa. This is the API:

	TypeConverter<T> {

		// Should returns the quantity of variables used 
		// to convert the object to the float[] and vice versa.
		int variables();

		// copy the values of the object to the specified float array, if null it will create a new float array.
		float[] copyFromObject(T object, float[] x);

		// copy the values of the float array to the specified object.
		T copyToObject(T object, float[] x);

	}

Using the previously defined Vector2f, an example implementation of TypeConverter<Vector2f> looks like this:

	Vector2fConverter implements TypeConverter<Vector2f> {
	
		float[] copyFromObject(Vector2f v, float[] x) {
			if (x == null) 
				x = new float[variables()];
			x[0] = v.x;
			x[1] = v.y;
			return x;
		}

		Vector2f copyToObject(Vector2f v, float[] x) {
			if (v == null)
				v = new Vector2f(0, 0);
			v.x = x[0];
			v.y = x[1];
			return v;
		}

		int variables() {
			return 2;
		}
	}



### Synchronizer (deprecated)

Initially, it was a class to perform synchronization of values between a transition and an object but now that transitions were modified to work only over mutable objects, this class lost its value. For now, it can be used to register transitions and call the update(time) method only once.

How to use it
------------

### Importing the project

First of all, you need to import the required jars of the project, if you are using Maven, add:

	<dependency>
		<groupId>com.gemserk.animation4j</groupId>
		<artifactId>animation4j-core</artifactId>
		<version>0.2.0</version>
	</dependency>

(note: 0.2.0 is the latest version uploaded to Maven central by the time this document was made)

Otherwise, you can download the jars from the [downloads](https://github.com/gemserk/animation4j/downloads) section.

There are different ways of using the framework, the next sections shows some of them.

### Example: Using the Transition API 

Start by creating a new transition:

	Vector2f myVector = new Vector2f();
	Vector2fConverter vector2fConverter = new Vector2fConverter();
	Transition<Vector2f> transition = Transitions.transition(myVector, vector2fConverter)
		.start(0f, 0f)
		.end(5f, 50f, 50f);

Now, in some part of the code (probably the update method if you are making a game) you have to update the transition in order to let it interpolate the values of your object instance:
	
	transition.update(1f);
	// this will show (10, 10) in the standard output
	System.out.println("(" + myVector.x + "," + myVector.y ")");

Limitations
------------

* To improve performance of the framework by not generating garbage each time an interpolation is made by the Transition implementation only mutable objects can be used. However, you can create mutable classes to help in perform transitions of immutable objects (TODO: example of this).

* You have to create the TypeConverter manually for your own object types as the TypeConverter<Vector2f> of the previous example, Animation4j can't create it by you automatically (at least for now). The good part is you have to do that only once and you can reuse the TypeConverter instance whenever you want since it is stateless.

TODO List
------------

* Improve API to make it easier to use.
* Add documentation about Animation API and Timeline features.

Contributing
------------

Feel free to add issues, make forks or contact us directly if you have suggestions, bug reports or enhancements.

