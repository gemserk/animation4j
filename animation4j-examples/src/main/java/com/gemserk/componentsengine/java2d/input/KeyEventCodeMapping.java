package com.gemserk.componentsengine.java2d.input;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class KeyEventCodeMapping {
	
	Map<String, Integer> keyCodesMap = new HashMap<String, Integer>();
	
	public KeyEventCodeMapping() {
		buildKeyCodesMapForKeyEvent();
	}
	
	private void buildKeyCodesMapForKeyEvent() {
		
		Field[] fields = KeyEvent.class.getFields();
		try {
			for ( Field field : fields ) {
				if ( Modifier.isStatic(field.getModifiers())
				     && Modifier.isPublic(field.getModifiers())
				     && Modifier.isFinal(field.getModifiers())
				     && field.getType().equals(int.class)
				     && field.getName().startsWith("VK_") ) {
					int key = field.getInt(null);
					String name = field.getName().substring(3);
					keyCodesMap.put(name, key);
				}

			}
		} catch (Exception e) {
		}
		
	}

	public int getKeyCode(String button) {
		return keyCodesMap.get(button.toUpperCase());
	}
	
}