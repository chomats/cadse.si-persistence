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

import java.util.Map;

import java.util.UUID;

import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.LinkType;
import fr.imag.adele.cadse.core.attribute.IAttributeType;

/**
 * The Interface IMigrationFormat.
 * 
 * @author <a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a>
 */
public interface IMigrationFormat {

	/**
	 * Gets the or create itid.
	 * 
	 * @param type
	 *            the type
	 * 
	 * @return the or create itid
	 */
	UUID getOrCreateITID(String type);

	/**
	 * Gets the iTID.
	 * 
	 * @param type
	 *            the type
	 * 
	 * @return the iTID
	 */
	UUID getITID(String type);

	/**
	 * Gets the unresolved type.
	 * 
	 * @return the unresolved type
	 */
	Map<UUID, String> getUnresolvedType();
	
	ItemType findTypeFrom(UUID id);

	IAttributeType<?> findAttributeFrom(ItemType it, String attName);

	LinkType findlinkTypeFrom(ItemType itObject, String linkType);

}
