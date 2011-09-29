package com.gemserk.componentsengine.java2d.input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Copied from http://www.gamedev.net/reference/programming/features/javainput/page3.asp
 */
public class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final int BUTTON_COUNT = 3;
	// Polled position of the mouse cursor
	private Point mousePos = null;
	// Current position of the mouse cursor
	private Point currentPos = null;
	// Current state of mouse buttons
	private boolean[] state = null;
	// Polled mouse buttons
	private MouseInput.MouseState[] poll = null;
	
	private float wheelRotationState;
	
	private float wheelRotationPolled;

	private enum MouseState {
		RELEASED, // Not down
		PRESSED, // Down, but not the first time
		ONCE
		// Down for the first time
	}

	public MouseInput() {
		// Create default mouse positions
		mousePos = new Point(0, 0);
		currentPos = new Point(0, 0);
		// Setup initial button states
		state = new boolean[BUTTON_COUNT];
		poll = new MouseInput.MouseState[BUTTON_COUNT];
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			poll[i] = MouseState.RELEASED;
		}
	}

	public synchronized void poll() {
		// Save the current location
		mousePos = new Point(currentPos);
		// Check each mouse button
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			// If the button is down for the first
			// time, it is ONCE, otherwise it is
			// PRESSED.
			if (state[i]) {
				if (poll[i] == MouseState.RELEASED)
					poll[i] = MouseState.ONCE;
				else
					poll[i] = MouseState.PRESSED;
			} else {
				// button is not down
				poll[i] = MouseState.RELEASED;
			}
		}
		
		wheelRotationPolled = wheelRotationState;
		wheelRotationState = 0f;
	}

	public Point getPosition() {
		return mousePos;
	}
	
	public boolean buttonDownOnce(int button) {
		return poll[button - 1] == MouseState.ONCE;
	}

	/**
	 * Return if mouse button is down, based on MouseEvent.getButton().
	 * @param button
	 * @return
	 */
	public boolean buttonDown(int button) {
		return poll[button - 1] == MouseState.ONCE || poll[button - 1] == MouseState.PRESSED;
	}

	public synchronized void mousePressed(MouseEvent e) {
		state[e.getButton() - 1] = true;
	}

	public synchronized void mouseReleased(MouseEvent e) {
		state[e.getButton() - 1] = false;
	}

	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseMoved(MouseEvent e) {
		currentPos = e.getPoint();
	}

	public void mouseClicked(MouseEvent e) {
		// Not needed
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		wheelRotationState = e.getWheelRotation();
	}

	public float getWheelRotation() {
		return wheelRotationPolled;
	}

}