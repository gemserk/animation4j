package com.gemserk.animation4j.transitions.sync;

import java.util.ArrayList;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.utils.Pool;
import com.gemserk.animation4j.utils.Pool.PoolObjectFactory;

/**
 * Handles synchronization of a collection of object synchronizers.
 * 
 * @author acoppes
 * 
 */
public class SynchronizedTransitionManager {
	
	private static final PoolObjectFactory<TransitionReflectionObjectSynchronizer> reflectionSynchronizerFactory = new PoolObjectFactory<TransitionReflectionObjectSynchronizer>() {
		@Override
		public TransitionReflectionObjectSynchronizer createObject() {
			return new TransitionReflectionObjectSynchronizer();
		}
	};

	private static final PoolObjectFactory<TransitionDirectObjectSynchronizer> synchronizerFactory = new PoolObjectFactory<TransitionDirectObjectSynchronizer>() {
		@Override
		public TransitionDirectObjectSynchronizer createObject() {
			return new TransitionDirectObjectSynchronizer();
		}
	};

	private Pool<TransitionReflectionObjectSynchronizer> reflectionObjectSynchronizersPool = new Pool<TransitionReflectionObjectSynchronizer>(reflectionSynchronizerFactory, 100);

	private Pool<TransitionDirectObjectSynchronizer> objectSynchronizerPool = new Pool<TransitionDirectObjectSynchronizer>(synchronizerFactory, 100);

	private ArrayList<TransitionObjectSynchronizer> synchronizers;

	private ArrayList<TransitionObjectSynchronizer> removeSynchronizers;

	public SynchronizedTransitionManager() {
		synchronizers = new ArrayList<TransitionObjectSynchronizer>();
		removeSynchronizers = new ArrayList<TransitionObjectSynchronizer>();
	}

	public void objectSynchronizer(Object object, Transition transition) {
		TransitionDirectObjectSynchronizer objectSynchronizer = objectSynchronizerPool.newObject();
		objectSynchronizer.setObject(object);
		objectSynchronizer.setTransition(transition);
		objectSynchronizer.setTypeConverter(Converters.converter(object.getClass()));
		handle(objectSynchronizer);
	}

	public void reflectionObjectSynchronizer(Object object, String field, Transition transition) {
		TransitionReflectionObjectSynchronizer objectSynchronizer = reflectionObjectSynchronizersPool.newObject();
		objectSynchronizer.setObject(object);
		objectSynchronizer.setFieldName(field);
		objectSynchronizer.setTransition(transition);
		handle(objectSynchronizer);
	}

	public void handle(TransitionObjectSynchronizer synchronizer) {
		synchronizers.add(synchronizer);
	}

	public void synchronize() {
		removeSynchronizers.clear();
		for (int i = 0; i < synchronizers.size(); i++) {
			TransitionObjectSynchronizer synchronizer = synchronizers.get(i);
			synchronizer.synchronize();

			if (synchronizer.isFinished()) {
				removeSynchronizers.add(synchronizer);

				// returns objects to their pools
				if (synchronizer instanceof TransitionReflectionObjectSynchronizer)
					reflectionObjectSynchronizersPool.free((TransitionReflectionObjectSynchronizer) synchronizer);
				else if (synchronizer instanceof TransitionDirectObjectSynchronizer)
					objectSynchronizerPool.free((TransitionDirectObjectSynchronizer) synchronizer);
			}
		}
		synchronizers.removeAll(removeSynchronizers);
	}

}