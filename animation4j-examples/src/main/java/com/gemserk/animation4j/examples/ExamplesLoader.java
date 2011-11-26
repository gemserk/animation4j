package com.gemserk.animation4j.examples;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.gemserk.componentsengine.java2d.Java2dDesktopApplication;
import com.gemserk.componentsengine.java2d.Java2dGame;
import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.render.CurrentGraphicsProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
public class ExamplesLoader {

	static class Option {

		Java2dGame app;

		String name;

		public Option(String name, Java2dGame app) {
			this.name = name;
			this.app = app;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	static Option[] options;

	static Option selectedOption;

	public static void main(String[] arguments) {

		final JFrame mainFrame = new JFrame() {
			{

				setSize(340, 140);
				setLocation(300, 300);

				setLayout(new GridLayout(3, 1));

				add(new JLabel("Select example"));

			}
		};

		mainFrame.setVisible(true);

		options = new Option[] { //
		new Option("Example1 - Basic Animation", new Example1()), //
		 new Option("Example2 - Advanced Animation", new Example2()), //
		 new Option("Example3 - Basic Transition", new Example3()), //
		 new Option("Example4 - Advanced Transition", new Example4()), //
		};

		selectedOption = options[0];

		mainFrame.add(new JComboBox(options) {
			{

				addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent event) {
						if (event.getStateChange() == ItemEvent.SELECTED) {
							selectedOption = (Option) event.getItem();
							System.out.println(event.getItem());
						}
					}
				});

			}
		});

		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mainFrame.add(new JButton() {
			{
				setText("Play");
				addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent event) {

						System.out.println("Starting " + selectedOption.name);

						try {
							final Java2dDesktopApplication application = new Java2dDesktopApplication();

							Injector injector = Guice.createInjector(new AbstractModule() {
								@Override
								protected void configure() {
									bind(CurrentGraphicsProvider.class).in(Singleton.class);
									bind(KeyboardInput.class).in(Singleton.class);
									bind(MouseInput.class).in(Singleton.class);
									bind(Java2dGame.class).toInstance(selectedOption.app);
								}
							});

							injector.injectMembers(application);

							application.init(selectedOption.name, new Dimension(640, 480), false);

							new Thread() {
								public void run() {
									application.start();
								};
							}.start();

						} catch (Exception ex) {
							throw new RuntimeException("failed to start prototype: " + selectedOption.name, ex);
						}

					}
				});

			}
		});

		mainFrame.invalidate();
		mainFrame.validate();

	}

}
