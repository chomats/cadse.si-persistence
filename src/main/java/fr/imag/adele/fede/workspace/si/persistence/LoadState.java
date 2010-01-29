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

import fr.imag.adele.cadse.core.CadseException;
import java.util.UUID;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.LinkType;
import fr.imag.adele.cadse.core.attribute.IAttributeType;
import fr.imag.adele.cadse.core.transaction.delta.ItemDelta;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;

/**
 * The Interface IMigrationFormat.
 * 
 * @author <a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a>
 */
public interface LoadState {

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

	public IAttributeType<?> getAttribute(ItemType it, String key);

	ItemType getItemType(UUID itID);
	
	public LinkType getLinkType(ItemType it, String key);

	LogicalWorkspaceTransaction getTransaction();
	
	public void addToWriteHeader(Item item);
	
	public void visiteItem(Item item);
	
	public void finishRead() throws CadseException;
	
	public void finishWrite();

	ItemDelta loadHeader(UUID attSourceType);

}