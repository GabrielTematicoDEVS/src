package org.inventivetalent.reflection.util;

import java.lang.reflect.*;

/**
 * Helper class to set fields, methods &amp; constructors accessible
 */
public abstract class AccessUtil {

	public static final boolean VERBOSE = System.getProperties().containsKey("org_inventivetalent_reflection_verbose");

	/**
	 * Sets the field accessible and removes final modifiers
	 *
	 * @param field Field to set accessible
	 * @return the Field
	 * @throws ReflectiveOperationException (usually never)
	 */

	public static Field setAccessible(Field field) throws ReflectiveOperationException {
		return setAccessible(field, false);
	}

	public static Field setAccessible(Field field, boolean readOnly) throws ReflectiveOperationException {
		return setAccessible(field, readOnly, false);
	}

	private static Field setAccessible(Field field, boolean readOnly, boolean privileged)
			throws ReflectiveOperationException {
		try {
			field.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (readOnly) {
			return field;
		}
		removeFinal(field, privileged);
		return field;
	}

	private static void removeFinal(Field field, boolean privileged) throws ReflectiveOperationException {
		int modifiers = field.getModifiers();
		if (Modifier.isFinal(modifiers)) {
			try {
				removeFinalSimple(field);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void removeFinalSimple(Field field) throws ReflectiveOperationException {
		int modifiers = field.getModifiers();
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
	}

	/**
	 * Sets the method accessible
	 *
	 * @param method Method to set accessible
	 * @return the Method
	 * @throws ReflectiveOperationException (usually never)
	 */
	public static Method setAccessible(Method method) throws ReflectiveOperationException {
		method.setAccessible(true);
		return method;
	}

	/**
	 * Sets the constructor accessible
	 *
	 * @param constructor Constructor to set accessible
	 * @return the Constructor
	 * @throws ReflectiveOperationException (usually never)
	 */
	public static Constructor setAccessible(Constructor constructor) throws ReflectiveOperationException {
		constructor.setAccessible(true);
		return constructor;
	}
}
