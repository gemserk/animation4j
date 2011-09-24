Description
------------

This project provides an easy way to animate values between time intervals.

Features
------------

* Animation of any object you want.
* API based on CSS3 definitions for animations and transitions.
* Optional event listeners for animation and transition state changes.

Introduction
------------

To know how to use the project, you should know a bit about the following classes. First of all, the next examples will be using a class named Vector2f defined as:

	public class Vector2f {

		public float x, y;
	
		public Vector2f(float x, float y) {
			this.x = x;
			this.y = y;
		}

	}

### Transition 

This class provides a way to modify an object from one value to another in a specified time.

### TypeConverter

Provides a way to let the framework copy values to your object and vice versa, this is the API:

	/**
	 * Provides a way to convert an object in a float[] array and vice versa, for interpolation purposes.
	 * 
	 * @param <T>
	 *            The type to convert.
	 * @author acoppes
	 */
	public interface TypeConverter<T> {

		/**
		 * Returns the quantity of variables are used to convert the object to the float[] and vice versa.
		 * 
		 * @return the quantity of variables used.
		 */
		int variables();

		/**
		 * Copy the values of the object to the specified float array, if null it will create a new float array.
		 * 
		 * @param object
		 *            The object from where to get the values to fulfill the float array.
		 * @param x
		 *            The float array to copy the values of the object. If null it will create a new float array.
		 * @return The float array with the values of the object.
		 */
		float[] copyFromObject(T object, float[] x);

		/**
		 * Copy the values of the float array to the specified object.
		 * 
		 * @param object
		 *            The object which the float array values will be copied to. If null or object immutable, it will create a new object.
		 * @param x
		 *            The float array to get the values to fulfill the object.
		 * @return An object with the values of the float array.
		 */
		T copyToObject(T object, float[] x);

	}

Using the previously defined Vector2f, an implementation of TypeConverter<Vector2f> could be something like this:

	public class Vector2fConverter implements TypeConverter<Vector2f> {
	
		public static int arrayInstances = 0;

		@Override
		public float[] copyFromObject(Vector2f v, float[] x) {
			if (x == null) {
				x = new float[variables()];
				arrayInstances++;
			}
			x[0] = v.x;
			x[1] = v.y;
			return x;
		}

		@Override
		public Vector2f copyToObject(Vector2f v, float[] x) {
			if (v == null)
				v = new Vector2f(0, 0);
			v.x = x[0];
			v.y = x[1];
			return v;
		}

		@Override
		public int variables() {
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

### Animation and Animation package...

TODO

How to use it
------------

The first example shows how to register an object to be synchronized 

	Transition<

Limitations
------------

* To improve performance of the framework by not generating garbage each time an interpolation is made by the Transition implementation, it is recommended to use mutable objects, then the framework will try to reuse the same internal instance for it.
* Right now, there is no way to stop a registered transition to be made, so if you start a transition using Synchronizer and you want to start another transition over the same object, both transitions will be performed (where only the second should be).
* Having to create by hand the Converter for your own object types, for example, the TypeConverter<Vector2f> of the example. However, it could be reused between multiple transition instances.

TODO List
------------

* Improve API to make it easier to use.
* More examples.
* Make a way to create transitions using the TransitionBuilder specifying the first and last values of the object without having to create a new object, could be something like .start(50, 50) instead .start(new Vector(50,50)), to avoid generating garbage. Of course, that could be handled by the library user instead.

Contributing
------------

Feel free to add issues, make forks or contact us directly if you have suggestions, bug reports or enhancements.

