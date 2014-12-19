/**
 * UUIDLookup
 * Copyright (C) 2014 dmulloy2
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.dmulloy2.uuidlookup;

import java.awt.GraphicsEnvironment;
import java.util.UUID;
import java.util.logging.Logger;

import net.dmulloy2.uuidlookup.types.NameFetcher;
import net.dmulloy2.uuidlookup.types.UUIDFetcher;
import net.dmulloy2.uuidlookup.util.Util;

/**
 * @author dmulloy2
 */

public class UUIDLookup {
	public static final Logger LOG = Logger.getLogger("UUIDLookup");

	public static void main(String[] args) {
		new UUIDLookup(args);
	}

	public UUIDLookup(String[] args) {
		if (args.length == 0 && ! GraphicsEnvironment.isHeadless()) {
			new UUIDLookupGUI();
			return;
		}

		if (args.length == 0) {
			LOG.severe("You must specify a name or UUID.");
			System.exit(1);
			return;
		}

		String input = args[0];
		LOG.info("Received input: " + input);

		String output = lookup(input);
		LOG.info("Output: " + output);
	}

	private final String lookup(String input) {
		try {
			if (input.length() == 36) {
				UUID uuid = UUID.fromString(input);
				return new NameFetcher(Util.toList(uuid)).call().get(uuid);
			} else {
				return new UUIDFetcher(Util.toList(input)).call().get(input).toString();
			}
		} catch (Throwable ex) {
			return "Failed to lookup \"" + input + "\": " + ex;
		}
	}
}
