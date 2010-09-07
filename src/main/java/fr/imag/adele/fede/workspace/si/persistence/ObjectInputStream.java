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
 *
 * Copyright (C) 2006-2010 Adele Team/LIG/Grenoble University, France
 */
package fr.imag.adele.fede.workspace.si.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamClass;
import java.util.HashMap;
import java.util.Map;


public class ObjectInputStream extends java.io.ObjectInputStream {

	Map<String, Class<?>> classes;
	
	protected ObjectInputStream() throws IOException, SecurityException {
		super();
	}

	public ObjectInputStream(InputStream in) throws IOException {
		super(in);
	}
	
	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
			ClassNotFoundException {
		try {
		    return super.resolveClass(desc);
		} catch (ClassNotFoundException ex) {
			String name = desc.getName();
			if (classes != null) {
				Class<?> ret = classes.get(name);
				if (ret != null) {
					return ret;
				}
			}
		    throw ex;
		}
	}
	
	public void addClass(Class<?> clazz) {
		if (classes == null) classes = new HashMap<String, Class<?>>();
		classes.put(clazz.getName(), clazz);
	}

}
