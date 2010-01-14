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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


import java.util.UUID;

import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.LinkType;
import fr.imag.adele.cadse.core.LogicalWorkspace;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.attribute.IAttributeType;
import fr.imag.adele.cadse.core.transaction.delta.ItemDelta;

/**
 * The Class MigrationFormat.
 *
 * @author <a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a>
 */
public class MigrationFormat implements IMigrationFormat {

	/** The workspace logique. */
	LogicalWorkspace workspaceLogique;

	/** The init. */
	boolean init = false;

	/** The name to uuid. */
	Map<String, UUID> nameToUUID;

	/** The new uuid. */
	private Map<UUID, String> newUUID;

	/** The log. */
	private Logger log;

	/**
	 * Instantiates a new migration format.
	 *
	 * @param workspaceLogique
	 *            the workspace logique
	 * @param mLogger
	 *            the m logger
	 */
	public MigrationFormat(LogicalWorkspace workspaceLogique, Logger mLogger) {
		this.workspaceLogique = workspaceLogique;
		this.log = mLogger;
	}

	/* (non-Javadoc)
	 * @see fede.workspace.tool.persistance.IMigrationFormat#getOrCreateITID(java.lang.String)
	 */
	public UUID getOrCreateITID(String type) {
		if (!init)
			init();
		UUID ret = nameToUUID.get(type);
		if (ret == null) {
			ret  = UUID.randomUUID();
			nameToUUID.put(type, ret);
			if (newUUID == null)
				newUUID = new HashMap<UUID, String>();
			newUUID.put(ret,type);
			log.warning("Cannot find the type "+type+" create a fictif id "+ret);
		}

		return ret;
	}

	/**
	 * Inits the.
	 */
	private void init() {
		nameToUUID = new HashMap<String, UUID>();
		Collection<ItemType> its = workspaceLogique.getItemTypes();
		for (ItemType it : its) {
			nameToUUID.put(it.getName(), it.getId());
		}
		init = true;
	}

	/* (non-Javadoc)
	 * @see fede.workspace.tool.persistance.IMigrationFormat#getUnresolvedType()
	 */
	public Map<UUID, String> getUnresolvedType() {
		return newUUID;
	}

	/* (non-Javadoc)
	 * @see fede.workspace.tool.persistance.IMigrationFormat#getITID(java.lang.String)
	 */
	public UUID getITID(String type) {
		if (!init)
			init();
		UUID ret = nameToUUID.get(type);
		return ret;
	}

	@Override
	public IAttributeType<?> findAttributeFrom(ItemType it, String attName) {
		try {
			UUID uuid = UUID.fromString(attName);
			Item foundItem = it.getLogicalWorkspace().getItem(uuid);
			if (foundItem instanceof ItemDelta) {
				ItemDelta delta = (ItemDelta) foundItem;
				if (delta.isModified())
					return delta.getAdapter(IAttributeType.class);
				foundItem = delta.getBaseItem();
			}
			if (foundItem instanceof IAttributeType<?>)
				return (IAttributeType<?>) foundItem;
		} catch (Exception ignored) {
		}
		return it.getAttributeType(attName);
	}

	@Override
	public ItemType findTypeFrom(UUID id) {
		return workspaceLogique.getItemType(id);
	}

	@Override
	public LinkType findlinkTypeFrom(ItemType it, String linkType) {
		try {
			UUID uuid = UUID.fromString(linkType);
			Item foundItem = it.getLogicalWorkspace().getItem(uuid);
			if (foundItem instanceof LinkType)
				return (LinkType) foundItem;
		} catch (Exception ignored) {
		}
		return it.getOutgoingLinkType(linkType);
	}

}
