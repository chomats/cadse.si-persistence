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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;



import fr.imag.adele.cadse.core.CadseException;
import java.util.UUID;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.LinkType;
import fr.imag.adele.cadse.core.LogicalWorkspace;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.attribute.IAttributeType;
import fr.imag.adele.cadse.core.transaction.delta.ItemDelta;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;
import fr.imag.adele.cadse.core.impl.CadseCore;

/**
 * The Class MigrationFormat.
 *
 * @author <a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a>
 */
public class LoadStateImpl implements LoadState {

	/** The workspace logique. */
	LogicalWorkspace workspaceLogique;
	ReadItemType _read;
	/** The init. */
	boolean init = false;

	/** The name to uuid. */
	Map<String, UUID> nameToUUID;

	/** The new uuid. */
	private Map<UUID, String> newUUID;

	/** The new uuid. */
	private Map<UUID, ItemType> _itemTypeByID = null;
	
	private Map<UUID, IAttributeType<?>> _attDefByID = null;
	
	private Map<UUID, LinkType> _linkTypeByID = null;
	
	private Set<UUID> _visitedID = null;
	

	/** The log. */
	private Logger log;
	private LogicalWorkspaceTransaction _transaction;
	private ArrayList<Item> _itemToWriteHeader;

	/**
	 * Instantiates a new migration format.
	 *
	 * @param workspaceLogique
	 *            the workspace logique
	 * @param mLogger
	 *            the m logger
	 */
	public LoadStateImpl(ReadItemType read, LogicalWorkspace workspaceLogique, Logger mLogger) {
		this.workspaceLogique = workspaceLogique;
		this.log = mLogger;
		_read = read;
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
	public IAttributeType<?> getAttribute(ItemType it, String key) {
		IAttributeType<?> attributeType = it.getAttributeType(key);
		if (attributeType == null)
			attributeType = CadseCore.getOldNameMap().get(key);
		return attributeType;
	}

	@Override
	public ItemType getItemType(UUID itID) {
		ItemType it = workspaceLogique.getItemType(itID);
		if (it != null)
			return it;
		if (_visitedID == null)
			_visitedID = new  HashSet<UUID>();
		if (_visitedID.contains(itID))
			return null;
		if (_itemTypeByID == null)
			_itemTypeByID = new HashMap<UUID, ItemType>();
		it = _itemTypeByID.get(itID);
		if (it != null)
			return it;
		_visitedID.add(itID);
		ItemDelta delta = _read.readItemType(itID);
		if (delta == null) return null;
		it = _read.getItemTypeFromDelta(delta);
		if (it == null) return null;
		_itemTypeByID.put(itID, it);
		return it;
	}
	
	@Override
	public LinkType getLinkType(ItemType it, String key) {
		return it.getOutgoingLinkType(key);
	}

	@Override
	public LogicalWorkspaceTransaction getTransaction() {
		if (_transaction == null)
			_transaction = workspaceLogique.createTransaction();
		return _transaction;
	}

	@Override
	public void addToWriteHeader(Item item) {
		if (_itemToWriteHeader  == null)
			_itemToWriteHeader = new ArrayList<Item>();
		_itemToWriteHeader.add(item);

	}

	@Override
	public void finishRead() throws CadseException {
		if (_transaction != null)
			_transaction.commit();
	}

	@Override
	public void finishWrite() {
		if (_itemToWriteHeader != null)
			for (Item item : _itemToWriteHeader) {
				try {
					_read.writeHeaderIfNead(item);
				} catch (FileNotFoundException e) {
					log.log(Level.SEVERE, "cannot write item header", e);
				} catch (IOException e) {
					log.log(Level.SEVERE, "cannot write item header", e);
				}
			}
		
	}

	@Override
	public void visiteItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemDelta loadHeader(UUID attSourceType) {
		ItemDelta ret = _transaction.getItem(attSourceType);
		if (ret == null)
			return ret;
		
		//PersistenceNew2009.readSerHeader_7(mig, input);
		return _read.readItemType(attSourceType);
	}
	


}
