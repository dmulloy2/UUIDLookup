package net.dmulloy2.uuidlookup.util;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import net.dmulloy2.uuidlookup.types.StringJoiner;

/**
 * Base Util class
 *
 * @author dmulloy2
 */

public class Util {
	private Util() {
	}

	/**
	 * Returns a useful Stack Trace for debugging purpouses
	 *
	 * @param ex Underlying {@link Throwable}
	 * @param circumstance Circumstance in which the Exception occured
	 */
	public static String getUsefulStack(Throwable ex, String circumstance) {
		StringJoiner joiner = new StringJoiner("\n");
		joiner.append("Encountered an exception" + (circumstance != null ? " while " + circumstance : "") + ": " + ex.toString());
		joiner.append("Affected classes:");

		for (StackTraceElement ste : ex.getStackTrace()) {
			if (ste.getClassName().contains("dmulloy2")) {
				StringBuilder line = new StringBuilder();
				line.append("\t" + ste.getClassName() + "." + ste.getMethodName() + " (Line " + ste.getLineNumber() + ")");
				line.append(" [" + getWorkingJarName() + "]");
			}
		}

		while (ex.getCause() != null) {
			ex = ex.getCause();
			joiner.append("Caused by: " + ex.toString());
			joiner.append("Affected classes:");
			for (StackTraceElement ste : ex.getStackTrace()) {
				if (ste.getClassName().contains("dmulloy2")) {
					StringBuilder line = new StringBuilder();
					line.append("\t" + ste.getClassName() + "." + ste.getMethodName() + " (Line " + ste.getLineNumber() + ")");
					line.append(" [" + getWorkingJarName() + "]");

				}
			}
		}

		return joiner.toString();
	}

	/**
	 * Gets the current working jar's name
	 *
	 * @return The name, or "Unknown" if it cannot be found
	 */
	public static final String getWorkingJarName() {
		try {
			String path = Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			path = URLDecoder.decode(path, "UTF-8");
			return path.substring(path.lastIndexOf("/") + 1);
		} catch (Throwable ex) {
		}
		return "Unknown";
	}

	/**
	 * Constructs a new list from an existing {@link List}
	 * <p>
	 * This fixes concurrency for some reason
	 * <p>
	 * Should not be used to edit the base List
	 *
	 * @param list Base {@link List}
	 * @return a new list from the given list
	 */
	public static <T> List<T> newList(List<T> list) {
		return new ArrayList<T>(list);
	}

	/**
	 * Constructs a new {@link List} paramaterized with <code>T</code>
	 *
	 * @param objects Array of <code>T</code> to create the list with
	 * @return a new {@link List} from the given objects
	 */
	@SafeVarargs
	public static <T> List<T> toList(T... objects) {
		List<T> ret = new ArrayList<T>();

		for (T t : objects) {
			ret.add(t);
		}

		return ret;
	}
}
