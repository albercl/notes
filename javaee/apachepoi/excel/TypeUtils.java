package es.um.atica.casiopeaadmin.util;

import java.util.Date;

public class TypeUtils {

	private TypeUtils() {
	}

	public static boolean isNumeric( Class<?> clazz ) {
		return clazz == Integer.class || clazz == Long.class || clazz == Double.class || clazz == Float.class
				|| clazz == Short.class || clazz == Byte.class || clazz == int.class || clazz == long.class
				|| clazz == double.class;
	}

	public static boolean isInteger( Class<?> clazz ) {
		return clazz == Integer.class || clazz == int.class || clazz == Long.class || clazz == long.class
				|| clazz == Short.class || clazz == short.class;
	}

	public static boolean isFloat( Class<?> clazz ) {
		return clazz == Float.class || clazz == float.class || clazz == Double.class || clazz == double.class;
	}

	public static boolean isBoolean( Class<?> clazz ) {
		return clazz == Boolean.class || clazz == boolean.class;
	}

	public static boolean isDate( Class<?> clazz ) {
		return clazz == Date.class;
	}
}
