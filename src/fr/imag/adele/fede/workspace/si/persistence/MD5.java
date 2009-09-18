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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Class MD5.
 *
 * @author <a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a>
 */
public class MD5 {

	/**
	 * Equals.
	 *
	 * @param md1
	 *            the md1
	 * @param md2
	 *            the md2
	 *
	 * @return true, if successful
	 */
	static public boolean equals(byte[] md1, byte[] md2) {
		if (md1.length != md2.length)
			return false;
		for (int i = 0; i < md1.length; i++) {
			if (md1[i] != md2[i])
				return false;
		}
		return true;
	}

//	/**
//	 * Gets the m d5.
//	 *
//	 * @param itemFile
//	 *            the item file
//	 *
//	 * @return the m d5
//	 *
//	 * @throws NoSuchAlgorithmException
//	 *             the no such algorithm exception
//	 * @throws IOException
//	 *             Signals that an I/O exception has occurred.
//	 */
//	static public byte[] getMD5(File itemFile) throws NoSuchAlgorithmException,
//			IOException {
//		return getMD5(read(itemFile));
//	}
//
//	/**
//	 * Gets the m d5.
//	 *
//	 * @param data
//	 *            the data
//	 *
//	 * @return the m d5
//	 *
//	 * @throws NoSuchAlgorithmException
//	 *             the no such algorithm exception
//	 */
//	static public byte[] getMD5(byte[] data) throws NoSuchAlgorithmException {
//		MessageDigest md = MessageDigest.getInstance("SHA");
//		md.update(data);
//		return md.digest();
//	}

	/**
	 * Read.
	 *
	 * @param itemFile
	 *            the item file
	 *
	 * @return the byte[]
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static public byte[] read(InputStream input) throws IOException {
		int length = input.available();
		byte[] result = new byte[length];
		int off = 0;
		while (true) {
			int readLength = input.read(result, off, length - off);
			off += readLength;
			if (off == length)
				break;
		}
		return result;
	}

	/**
	 * Read.
	 *
	 * @param itemFile
	 *            the item file
	 *
	 * @return the byte[]
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static public byte[] read(File itemFile) throws IOException {
		FileInputStream input = null;
		try {
			input = new FileInputStream(itemFile);
			int length = input.available();
			byte[] result = new byte[length];
			int off = 0;
			while (true) {
				int readLength = input.read(result, off, length - off);
				off += readLength;
				if (off == length)
					break;
			}
			return result;
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (input != null)
				input.close();
		}

	}

	/**
	 * Read.
	 *
	 * @param itemFile
	 *            the item file
	 *
	 * @return the byte[]
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static public byte[] read(URL itemFile) throws IOException {
		InputStream input = null;
		try {
			input = itemFile.openStream();
			int length = input.available();
			byte[] result = new byte[length];
			int off = 0;
			while (true) {
				int readLength = input.read(result, off, length - off);
				off += readLength;
				if (off == length)
					break;
			}
			return result;
		} catch (IOException e) {
			throw e;
		} finally {
			if (input != null)
				input.close();
		}

	}

	/** The Constant digits. */
	final public static char[] digits = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

//	/**
//	 * Gets the m d5 str.
//	 *
//	 * @param itemFile
//	 *            the item file
//	 *
//	 * @return the m d5 str
//	 *
//	 * @throws NoSuchAlgorithmException
//	 *             the no such algorithm exception
//	 * @throws IOException
//	 *             Signals that an I/O exception has occurred.
//	 */
//	static public String getMD5Str(File itemFile)
//			throws NoSuchAlgorithmException, IOException {
//		return toString(getMD5(itemFile));
//	}

	/**
	 * To string.
	 *
	 * @param md
	 *            the md
	 *
	 * @return the string
	 *
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static public String toString(byte[] md) throws NoSuchAlgorithmException,
			IOException {
		char[] buf = new char[md.length * 2];
		for (int i = 0, j = 0; i < md.length; i++) {
			int v = md[i];
			buf[j++] = digits[(v >> 4) & 15];
			buf[j++] = digits[(v) & 15];
		}
		return new String(buf);
	}

//	/**
//	 * The main method.
//	 *
//	 * @param args
//	 *            the arguments
//	 */
//	public static void main(String[] args) {
//		try {
//			System.out
//					.println(toString(getMD5(new File(
//							"/home/chomats/Fede/demo-runtime2/Tool.Product.ProductServer/.melusine"))));
//			System.out
//					.println(toString(getMD5(new File(
//							"/home/chomats/Fede/demo-runtime2/.melusine/Tool.Product.ProductServer"))));
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
