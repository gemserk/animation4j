package com.gemserk.animation4j.transitions.sync;

import java.util.ArrayList;

public class SynchronizedTransitionManager {

	private ArrayList<TransitionObjectSynchronizer> synchronizers;

	private ArrayList<TransitionObjectSynchronizer> removeSynchronizers;

	public SynchronizedTransitionManager() {
		synchronizers = new ArrayList<TransitionObjectSynchronizer>();
		removeSynchronizers = new ArrayList<TransitionObjectSynchronizer>();
	}

	public void handle(TransitionObjectSynchronizer synchronizer) {
		synchronizers.add(synchronizer);
	}

	public void synchronize() {
		for (int i = 0; i < synchronizers.size(); i++) {
			TransitionObjectSynchronizer synchronizer = synchronizers.get(i);
			synchronizer.synchronize();

			if (synchronizer.isFinished())
				removeSynchronizers.add(synchronizer);
		}

		synchronizers.removeAll(removeSynchronizers);
	}

}