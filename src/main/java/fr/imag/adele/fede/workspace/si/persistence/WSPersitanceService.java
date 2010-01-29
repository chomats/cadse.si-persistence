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

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import fr.imag.adele.cadse.core.ChangeID;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemState;
import fr.imag.adele.cadse.core.Link;
import fr.imag.adele.cadse.core.WSEvent;
import fr.imag.adele.cadse.core.WorkspaceListener;
import fr.imag.adele.cadse.core.attribute.IAttributeType;
import fr.imag.adele.cadse.core.transaction.delta.ImmutableWorkspaceDelta;

/**
 * The Class WSPersitanceService.
 *
 * @author <a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a>
 */
public class WSPersitanceService extends Thread {

	/** The p. */
	Persistence			p;

	/** The reload files. */
	BlockingQueue<Item>	_toPersistItems	= new LinkedBlockingQueue<Item>();

	private boolean		started			= true;

	/**
	 * Instantiates a new wS persitance service.
	 *
	 * @param p
	 *            the p
	 */
	public WSPersitanceService(Persistence p) {
		super("ws persistance service");
		this.p = p;
		setDaemon(true);
		p.getCadseDomain().getLogicalWorkspace().addListener(
				new PersistenceListener(),
				ChangeID.toFilter(ChangeID.SET_ATTRIBUTE, ChangeID.DELETE_OUTGOING_LINK, ChangeID.CREATE_OUTGOING_LINK,
						ChangeID.CREATE_ITEM, ChangeID.DELETE_ITEM, ChangeID.FORCE_SAVE));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
	//	EclipsePojo.waitIsStarted();

		p.setLocation(p.getPlatformIDE().getLocation(true));
		// p.getWorspaceDomain().addListener(this);
		while (started) {
			Item item;
			try {
				item = _toPersistItems.take();
				if (item == null) {
					continue;
				}
				if (item.getState() == ItemState.DELETED) {
					p.delete(item);
				} else {
					try {
						p.save(item);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (InterruptedException e1) {
				// do nothing
			}

		}
	}

	public class PersistenceListener extends WorkspaceListener {
		@Override
		public void workspaceChanged(ImmutableWorkspaceDelta delta) {
			HashSet<Item> toPersistItems = new HashSet<Item>();
			for (WSEvent wse : delta.getEvents()) {
				Item item = (Item) wse.getOperationArgs()[0];
				if (item.isStatic()) continue;
				switch (wse.getEventTypeId()) {
					case SET_ATTRIBUTE:
						final IAttributeType<?> attDef = (IAttributeType<?>) wse.getOperationArgs()[1];
						if (attDef.isTransient()) continue;
						toPersistItems.add(item);
						break;
					case DELETE_OUTGOING_LINK:
					case CREATE_OUTGOING_LINK:
						toPersistItems.add(((Link) wse.getOperationArgs()[0]).getSource());
						break;
					case CREATE_ITEM:
						toPersistItems.add(item);
						break;

					case DELETE_ITEM:
						toPersistItems.add(item);
						break;
					case FORCE_SAVE:
						toPersistItems.add(item);
						break;
					default:
						break;
				}
			}
			_toPersistItems.addAll(toPersistItems);
		}
	}

	public void stopService() {
		this.started = false;
	}

}
