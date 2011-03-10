package com.gemserk.animation4j.examples;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.gemserk.componentsengine.java2d.Java2dDesktopApplication;

public class ExamplesLoader  {
	
	static class Option {
		
		Class classToLoad;
		
		String name;
		
		public Option(String name, Class classToLoad) {
			this.name = name;
			this.classToLoad = classToLoad;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
	}
	
	static Option[] options = new Option[]{ //
		new Option("Example1", Example1.class), //
		new Option("Example2", Example2.class), //
	};
	
	static Option selectedOption = options[0]; 
	
	public static void main(String[] arguments) {
		
		new JFrame() {{
			
			addWindowListener(new WindowAdapter() {
				
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
				
			});
			
			setSize(340, 140);
			setLocation(300, 300);
			
			setLayout(new GridLayout(3, 1));
			
			add(new JLabel("Select prototype"));
			
			add(new JComboBox(options){{
				
				addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent event) {
						if (event.getStateChange() == ItemEvent.SELECTED) {
							selectedOption = (Option) event.getItem();
							System.out.println(event.getItem());
						}
					}
				});
				
			}});
			
			add(new JButton() {{ 
				
				setText("Play");
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent event) {
						
						System.out.println("Starting " + selectedOption.name);
						
						try {
							
							Class<? extends Java2dDesktopApplication> prototypeClass = selectedOption.classToLoad;
							Constructor<? extends Java2dDesktopApplication> constructor = prototypeClass.getConstructor();
							
							Java2dDesktopApplication exampleInstance = constructor.newInstance();
							
							exampleInstance.init();
							exampleInstance.start();
							
						} catch (Exception ex) {
							throw new RuntimeException("failed to start prototype: " + selectedOption.name, ex);
						}
						
					}
				});
				
			}});
			
		}}.setVisible(true);
		
	}

}
