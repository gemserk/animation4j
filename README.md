Description
------------

This project provides an easy way to animate values between time intervals.

Features
------------

* Animation of any object you want.
* API based on CSS3 definitions for animations and transitions.
* Optional event listeners for animation and transition state changes.
* Low dependence to animation4j classes by providing black box interaction (optional).

Introduction
------------

To know how to use the project, you should know a bit about the following classes. First of all, the next examples will be using a class named Vector2f defined as:

	Vector2f {

		float x, y;
	
	}

(note: I removed some boilerplate code for the documentation examples)

### Transition<T>

This class provides a way to modify an object from one value to another in a specified time, the API:

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

	}

### TypeConverter

Provides a way to let the framework copy values to your object and vice versa, this is the API:

	TypeConverter<T> {

		// Should returns the quantity of variables used 
		// to convert the object to the float[] and vice versa.
		int variables();

		// copy the values of the object to the specified float array, if null it will create a new float array.
		float[] copyFromObject(T object, float[] x);

		// copy the values of the float array to the specified object.
		T copyToObject(T object, float[] x);

	}

Using the previously defined Vector2f, an implementation of TypeConverter<Vector2f> could be something like this:

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

### Synchronizer

This class provides an easy way to interact with the framework by registering transitions of objects you want to perform and then call the update method to synchronize the interpolated values of the registered transition with your object.

### TransitionBuilder

This class provides an easy way to build a Transition<T> that could be used to be registered on the Synchronizer. The next example shows how to create a Transition<Vector2f>:

	Transitions.transitionBuilder() // This line asks for a TransitionBuilder
		.start(new Vector2f(10, 10))  // This line defines the starting value of the transition
		.end(new Vector2f(50, 50))  // This line defines the ending value of the transition
		.time(0.5f)					// This line defines the duration of the transition to be half a second

How to use it
------------

### Importing the project

First of all, you need to import the required jars of the project, if you are using maven, add:

	<dependency>
		<groupId>com.gemserk.animation4j</groupId>
		<artifactId>animation4j-core</artifactId>
		<version>0.0.14</version>
	</dependency>

(note: 0.0.14 is the latest version uploaded to maven central by the time this document was made)

Otherwise, you can download the jars from the [downloads](https://github.com/gemserk/animation4j/downloads) section.

There are different ways of using the framework, the next sections shows some of them.

### Using the Synchronizer 

The first example shows how to register an object to be synchronized by using the Synchronizer and the TransitionBuilder.

	// this code registers a new TypeConverter for the class Vector2f, to be used by 
	// the framework internally
	Converters.register(Vector2f.class, new Vector2fConverter());
	Vector2f v = new Vector2f(50f, 50f);
	Synchronizer.transition(v, Transitions.transitionBuilder()
				.end(new Vector2f(100f, 100f))
				.time(0.5f));
	// now we call the synchronizer to preform the transitions and synchronize the new value with v
	Synchronizer.synchronize(0.25f);
	// this should print (75,75)
	System.out.println("(" + v.x + "," + v.y ")");


Limitations
------------

* To improve performance of the framework by not generating garbage each time an interpolation is made by the Transition implementation, it is recommended to use mutable objects, then the framework will try to reuse the same internal instance for it.
* Right now, there is no way to stop a registered transition to be made, so if you start a transition using Synchronizer and you want to start another transition over the same object, both transitions will be performed (where only the second should be).
* Having to create by hand the Converter for your own object types, for example, the TypeConverter<Vector2f> of the example. However, it could be reused between multiple transition instances.

TODO List
------------

* Improve API to make it easier to use.
* Improve current documentation and add documentation about animation and time line features.
* Make a way to create transitions using the TransitionBuilder specifying the first and last values of the object without having to create a new object, could be something like .start(50, 50) instead .start(new Vector(50,50)), to avoid generating garbage. Of course, that could be handled by the library user instead.

Contributing
------------

Feel free to add issues, make forks or contact us directly if you have suggestions, bug reports or enhancements.

