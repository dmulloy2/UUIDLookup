/**
 * (c) 2014 dmulloy2
 */
package net.dmulloy2.uuidlookup.types;

/**
 * @author dmulloy2
 */

public class Constants {
	public static String NAME = "UUIDLookup";
	public static String VERSION = "2.0";
	public static String AUTHOR = "dmulloy2";

	public static String getFullName() {
		return String.format("%s v%s by %s", NAME, VERSION, AUTHOR);
	}
}