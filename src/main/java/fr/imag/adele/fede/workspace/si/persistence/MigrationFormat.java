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
import fr.imag.adele.cadse.core.impl.CadseCore;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;
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
	
	/** The new uuid. */
	private Map<UUID, ItemDelta> _items = new HashMap<UUID, ItemDelta>();

	/** The log. */
	private Logger log;

	private LogicalWorkspaceTransaction lwT;

	private Persistence p;

	/**
	 * Instantiates a new migration format.
	 *
	 * @param workspaceLogique
	 *            the workspace logique
	 * @param mLogger
	 *            the m logger
	 */
	public MigrationFormat(Persistence p, LogicalWorkspaceTransaction lwT, LogicalWorkspace workspaceLogique, Logger mLogger) {
		this.workspaceLogique = workspaceLogique;
		this.log = mLogger;
		this.p = p;
		this.lwT = lwT;
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
	public IAttributeType<?> findAttributeFrom(ItemType it, String attName) throws Throwable {
		try {
			UUID uuid = UUID.fromString(attName);
			Item foundItem = _items.get(uuid);
			File fileItem = p.fileSerFromUUID(uuid);
			if (foundItem == null && fileItem.exists()) {
				foundItem = p.loadItem(getTransaction(), this, fileItem, false);
			}
			if (foundItem == null)
				foundItem = it.getLogicalWorkspace().getItem(uuid);
			if (foundItem instanceof ItemDelta) {
				ItemDelta delta = (ItemDelta) foundItem;
				if (delta.getRealItem() != null && delta.getRealItem() instanceof IAttributeType)
					return (IAttributeType<?>) delta.getRealItem();
				if (delta.isModified())
					return delta.getAdapter(IAttributeType.class);
				foundItem = delta.getBaseItem();
			}
			if (foundItem instanceof IAttributeType<?>)
				return (IAttributeType<?>) foundItem;
		} catch (Exception ignored) {
		}
		IAttributeType<?> attributeType = it.getAttributeType(attName);
		if (attributeType == null)
			attributeType = CadseCore.findAttributeFrom(it, attName);
		return attributeType;
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
		IAttributeType<?> attributeType = it.getOutgoingLinkType(linkType);
		if (attributeType == null)
			attributeType = CadseCore.getOldNameMap().get(linkType);
		return (LinkType) attributeType;
	}
	
	@Override
	public void registerItem(ItemDelta item) {
		_items.put(item.getId(), item);
	}
	
	@Override
	public ItemDelta getItem(UUID uuid) {
		return _items.get(uuid);
	}
	
	@Override
	public LogicalWorkspaceTransaction getTransaction() {
		return lwT;
	}

}
