package com.gemserk.animation4j.examples;

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

@SuppressWarnings("serial")
public class ExamplesLoader {

	static class Option {

		Java2dDesktopApplication app;

		String name;

		public Option(String name, Java2dDesktopApplication app) {
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
				new Option("Example1", new Example1()), //
				new Option("Example2", new Example2()), //
				new Option("Example3", new Example3()), //
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
							mainFrame.setVisible(false);

							final Java2dDesktopApplication exampleInstance = selectedOption.app;
							
							exampleInstance.init();
							
							new Thread() { 
								public void run() {
									exampleInstance.start();
								};
							}.start();

						} catch (Exception ex) {
							throw new RuntimeException("failed to start prototype: " + selectedOption.name, ex);
						}

					}
				});

			}
		});
		
		mainFrame.repaint();
		
	}

}
