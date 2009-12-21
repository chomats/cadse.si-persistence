/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package fr.imag.adele.fede.workspace.si.persistence;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import java.util.UUID;

/**
 * The Class XMLPersistance.
 *
 * @author <a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a>
 */
public class XMLPersistance {

	/**
	 * Simple type for.
	 *
	 * @param wrapper
	 *            the wrapper
	 *
	 * @return the class
	 */
	public static Class simpleTypeFor(Class wrapper) {
		if (wrapper == Boolean.class)
			return Boolean.TYPE;
		if (wrapper == Byte.class)
			return Byte.TYPE;
		if (wrapper == Character.class)
			return Character.TYPE;
		if (wrapper == Short.class)
			return Short.TYPE;
		if (wrapper == Integer.class)
			return Integer.TYPE;
		if (wrapper == Long.class)
			return Long.TYPE;
		if (wrapper == Float.class)
			return Float.TYPE;
		if (wrapper == Double.class)
			return Double.TYPE;
		if (wrapper == Void.class)
			return Void.TYPE;
		if (wrapper == String.class)
			return wrapper;
		if (wrapper == Date.class)
			return wrapper;
		return null;
	}

	/**
	 * Write xml.
	 *
	 * @param output
	 *            the output
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static final void writeXML(Appendable output, String name,
			String value) throws IOException {
		output.append(" ").append(name).append(" =\"").append(
				quoteCharacters(value)).append("\"");
	}

	/**
	 * Write xml opt.
	 *
	 * @param output
	 *            the output
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static final void writeXMLOpt(Appendable output, String name,
			String value) throws IOException {
		if (value == null)
			return;
		output.append(" ").append(name).append(" =\"").append(
				quoteCharacters(value)).append("\"");
	}

	/**
	 * Write xml.
	 *
	 * @param output
	 *            the output
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static final void writeXML(Appendable output, String name,
			int value) throws IOException {
		output.append(" ").append(name).append(" =\"").append(Integer.toString(value)).append("\"");
	}

	/**
	 * Write xml.
	 *
	 * @param output
	 *            the output
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static final void writeXML(Appendable output, String name,
			boolean value) throws IOException {
		output.append(" ").append(name).append(" =\"").append(
				value ? "true" : "false").append("\"");
	}

	/**
	 * Write xml.
	 *
	 * @param output
	 *            the output
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @param tag
	 *            the tag
	 * @param tab
	 *            the tab
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static final void writeXML(Appendable output, String key,
			Object value, String tag, String tab) throws IOException {
		if (value == null)
			return;

		output.append(tab).append("<").append(tag).append(" key=\"")
				.append(key).append("\" type=\"");

		Class primitiveType = simpleTypeFor(value.getClass());
		if (primitiveType != null) {
			String primitiveTypeName = primitiveType.getName();
			// Make sure that character types are quoted correctly.
			if (primitiveType == Character.TYPE) {
				value = quoteCharacters(((Character) value).toString());
			}
			if (primitiveType == String.class) {
				value = quoteCharacters((String) value);
			}
			output.append(primitiveTypeName).append("\" value=\"").append(
					value.toString()).append("\"/>\n");
			return;
		}
		/*
		 * if (value instanceof Boolean) { output.append("Boolean\"
		 * value=\"").append(value.toString()).append("\"/>\n"); } else if
		 * (value instanceof Integer) { output.append("Integer\"
		 * value=\"").append(value.toString()).append("\"/>\n"); } else if
		 * (value instanceof Float) { output.append("Float\"
		 * value=\"").append(value.toString()).append("\"/>\n"); } else if
		 * (value instanceof Double) { output.append("Double\"
		 * value=\"").append(value.toString()).append("\"/>\n"); } else if
		 * (value instanceof String) { output.append("String\"
		 * value=\"").append(quoteCharacters(value.toString())).append("\"/>\n"); }
		 */
		if (value instanceof UUID) {
			output.append("UUID\" value=\"");
			output.append(value.toString());
			output.append("\"/>\n");
			return;
		}
		if (value instanceof UUID) {
			output.append("UUID\" value=\"");
			output.append(value.toString());
			output.append("\"/>\n");
			return;
		}

		if (value instanceof Map) {
			output.append("Map\">\n");
			Map<String, Object> m = (Map<String, Object>) value;
			for (Map.Entry<String, Object> e : m.entrySet()) {
				writeXML(output, e.getKey(), e.getValue(), "element", tab
						+ "  ");
			}
			output.append(tab).append("</").append(tag).append(">\n");
			return;
		}
		if (value instanceof List) {
			output.append("List\">\n");
			List m = (List) value;
			int i = 0;
			for (Object e : m) {
				writeXML(output, Integer.toString(i++), e, "element", tab
						+ "  ");
			}
			output.append(tab).append("</").append(tag).append(">\n");
			return;
		}
		if (value instanceof Enum) {
			output.append(value.getClass().getCanonicalName());
			output.append("\" value=\"");
			output.append(((Enum)value).name());
			output.append("\"/>\n");
			return;
		}
		if (value.getClass().isArray()) {
			Class<?> wrapper = value.getClass().getComponentType();
			if (wrapper == Boolean.TYPE) {
				output.append("Array of boolean\">\n");
				boolean[] array = (boolean[]) value;
				int i = 0;
				for (boolean b : array) {
					writeXML(output, Integer.toString(i++), b, "element", tab
							+ "  ");
				}
				return;
			}
			if (wrapper == Byte.TYPE) {
				output.append("Array of byte\">\n");
				byte[] array = (byte[]) value;
				int i = 0;
				for (byte b : array) {
					writeXML(output, Integer.toString(i++), b, "element", tab
							+ "  ");
				}
				return;
			}
			if (wrapper == Character.TYPE) {
				output.append("Array of char\">\n");
				char[] array = (char[]) value;
				int i = 0;
				for (char b : array) {
					writeXML(output, Integer.toString(i++), b, "element", tab
							+ "  ");
				}
				return;
			}
			if (wrapper == Short.TYPE) {
				output.append("Array of short\">\n");
				short[] array = (short[]) value;
				int i = 0;
				for (short b : array) {
					writeXML(output, Integer.toString(i++), b, "element", tab
							+ "  ");
				}
				return;
			} else if (wrapper == Integer.TYPE) {
				output.append("Array of int\">\n");
				int[] array = (int[]) value;
				int i = 0;
				for (int b : array) {
					writeXML(output, Integer.toString(i++), b, "element", tab
							+ "  ");
				}
				return;
			}
			if (wrapper == Long.TYPE) {
				output.append("Array of long\">\n");
				long[] array = (long[]) value;
				int i = 0;
				for (long b : array) {
					writeXML(output, Integer.toString(i++), b, "element", tab
							+ "  ");
				}
				return;
			}
			if (wrapper == Float.TYPE) {
				output.append("Array of float\">\n");
				float[] array = (float[]) value;
				int i = 0;
				for (float b : array) {
					writeXML(output, Integer.toString(i++), b, "element", tab
							+ "  ");
				}
				return;
			}
			if (wrapper == Double.TYPE) {
				output.append("Array of boolean\">\n");
				double[] array = (double[]) value;
				int i = 0;
				for (double b : array) {
					writeXML(output, Integer.toString(i++), b, "element", tab
							+ "  ");
				}
				return;
			}
			if (wrapper == String.class) {
				output.append("Array of String\">\n");
				String[] array = (String[]) value;
				int i = 0;
				for (String b : array) {
					writeXML(output, Integer.toString(i++), b, "element", tab
							+ "  ");
				}
				return;
			}
		}
		System.err.println("Cannot write the key : " + key + " = " + value
				+ " class of value : " + value.getClass());
	}

	/**
	 * Quote characters.
	 *
	 * @param s
	 *            the s
	 *
	 * @return the string
	 */
	private static String quoteCharacters(String s) {
		StringBuilder result = null;
		for (int i = 0, max = s.length(), delta = 0; i < max; i++) {
			char c = s.charAt(i);
			String replacement = null;

			if (c == '&') {
				replacement = "&amp;";
			} else if (c == '<') {
				replacement = "&lt;";
			} else if (c == '\r') {
				replacement = "&#13;";
			} else if (c == '>') {
				replacement = "&gt;";
			} else if (c == '"') {
				replacement = "&quot;";
			} else if (c == '\'') {
				replacement = "&apos;";
			}

			if (replacement != null) {
				if (result == null) {
					result = new StringBuilder(s);
				}
				result.replace(i + delta, i + delta + 1, replacement);
				delta += (replacement.length() - 1);
			}
		}
		if (result == null) {
			return s;
		}
		return result.toString();
	}
}
