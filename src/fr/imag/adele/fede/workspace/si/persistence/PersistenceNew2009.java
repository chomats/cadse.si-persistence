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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import fr.imag.adele.cadse.core.CadseException;
import fr.imag.adele.cadse.core.CompactUUID;
import fr.imag.adele.cadse.core.LogicalWorkspace;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.CadseDomain;
import fr.imag.adele.cadse.core.attribute.IAttributeType;
import fr.imag.adele.cadse.core.delta.ItemDelta;
import fr.imag.adele.cadse.core.delta.LinkDelta;
import fr.imag.adele.cadse.core.enumdef.TWCommitKind;
import fr.imag.adele.cadse.core.enumdef.TWDestEvol;
import fr.imag.adele.cadse.core.enumdef.TWEvol;
import fr.imag.adele.cadse.core.enumdef.TWUpdateKind;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;
import fr.imag.adele.cadse.core.ui.EPosLabel;
import fr.imag.adele.fede.workspace.as.platformeclipse.IPlatformEclipse;

/**
 * @generated
 */
public class PersistenceNew2009 {



	/**
	 * @generated
	 */
	IPlatformEclipse				platformEclipse;

	/**
	 * The ws domain.
	 *
	 * @generated
	 *
	 */
	CadseDomain					workspaceCU;

	/** The Constant key_change_infos. */
	public static final String		key_change_infos	= "WS:private:changeInfos";

	/** The Constant key_readonly. */
	public static final String		key_readonly		= "WS:private:readonly";

	/** The Constant key_open. */
	public static final String		key_open			= "WS:private:open";

	/** The Constant key_valid. */
	public static final String		key_valid			= "WS:private:valid";

	/** The Constant header_file_version. */
	public static final String		header_file_version	= "WS:private:head:version";

	/** The Constant key_info. */
	public static final String		key_info			= "WS:private:info";

	/** The Constant WS_PRIVATE_VERSION. */
	//public static final String		WS_PRIVATE_VERSION	= "##WS:private:version";

	/** The Constant MELUSINE_DIRECTORY. */
	public static final String		MELUSINE_DIRECTORY	= ".melusine";

	// static final String FILE_NAME = "workspace-metadata.ser";
	/** The Constant ID_FILE_NAME. */
	static final String				ID_FILE_NAME		= "workspace-metadata.id";

	/** The Constant DELETE. */
	private static final boolean	DELETE				= false;

	/** The Constant VERSION_4. */
	private static final int		VERSION_4			= 4;

	/** The Constant VERSION_5. */
	private static final int		VERSION_5			= 5;

	/** The Constant VERSION_6. */
	private static final int		VERSION_6			= 6;

	private Persistence				pOld;

	/** The m logger. */
	static Logger						mLogger				= Logger.getLogger("SI.Workspace.Persitance");
	static Logger						model_events_log	= Logger.getLogger("Model.Events");

	/**
	 * Instantiates a new wS persistance.
	 */
	public PersistenceNew2009() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#load(java.lang.String)
	 */
	public boolean load(final String location, CadseDomain workspaceCU, Persistence pOld) {
		this.workspaceCU = workspaceCU;
		this.pOld = pOld;
		//workspaceCU.beginOperation("load");
		long start = System.currentTimeMillis();
		try {
			pOld.enablePersistance = false;

			File wsLocation = new File(location);
			File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);

			// load the workspace information : cadse name;
			pOld.loadID2(location);

			try {
				LogicalWorkspace workspaceLogique = workspaceCU.getLogicalWorkspace();
				IMigrationFormat mig = new MigrationFormat(workspaceLogique, mLogger);
				Map<CompactUUID, ItemDelta> items = new HashMap<CompactUUID, ItemDelta>();

				LogicalWorkspaceTransaction copy = workspaceLogique.createTransaction();
				loadItemDescriptionFromRepo(copy, mig, location_melusine, null, items);

				/* Collection<Item> items2 = */
				copy.commit(false, false, true, null);

			} catch (Throwable e) {
				//workspaceCU.reset();
				throw e;
			}

			// }}, false, true, true, true);
		} catch (Throwable e1) {
			mLogger.log(Level.SEVERE, "load", e1);
			return false;
		} finally {
			pOld.enablePersistance = true;
		//	workspaceCU.endOperation();
			mLogger.finest("Load peristance in " + (System.currentTimeMillis() - start) + " ms.");


		}

		return true;
	}

	/**
	 * Load item description from repo.
	 *
	 * @param mig
	 *            the mig
	 * @param location_melusine
	 *            the location_melusine
	 * @param monitor
	 *            the monitor
	 * @param items
	 *            the items
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void loadItemDescriptionFromRepo(LogicalWorkspaceTransaction copy, IMigrationFormat mig,
			File location_melusine, IProgressMonitor monitor, Map<CompactUUID, ItemDelta> items) throws IOException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		File[] itemsFiles = location_melusine.listFiles();
		if (itemsFiles == null || itemsFiles.length == 0) {
			throw new IOException("No file in " + location_melusine.toString());
		}

		monitor.beginTask("load repository " + location_melusine, itemsFiles.length);
		for (File file : itemsFiles) {
			monitor.worked(1);
			String nameFile = file.getName();
			if (nameFile.equals(ID_FILE_NAME)) {
				continue;
			}
			// if (nameFile.equals(id))
			// continue;
			// if (nameFile.equals(id + ".ser"))
			// continue;
			if (nameFile.equals(".svn")) {
				continue;
			}
			if (nameFile.equals(".project")) {
				continue;
			}
			if (nameFile.endsWith(".xml")) {
				continue;
			}
			if (nameFile.endsWith(".ser")) {
				try {
					ItemDelta itemLoaded = loadItem(copy, mig, file, false);
					if (itemLoaded == null) {
						pOld.delete(file);
						mLogger.info("Delete file " + file.getName());
						continue;
					}
					items.put(itemLoaded.getId(), itemLoaded);
					monitor.subTask(itemLoaded.getQualifiedName());
				} catch (Throwable e) {
					mLogger.log(Level.SEVERE, "load "+nameFile, e);
					// nameFile.delete();
				}
				continue;
			}
		}
	}

	public ItemDelta loadFromPersistence(LogicalWorkspaceTransaction wl, URL url) throws CadseException {
		ObjectInputStream input = null;
		try {
			byte[] data = MD5.read(url);
		//	byte[] md5 = MD5.getMD5(data);
			IMigrationFormat mig = new MigrationFormat(wl, mLogger);
			ItemDelta item = readFromByteArray(wl, mig, data);
			return item;
		} catch (ClassNotFoundException e) {
			throw new CadseException(e.getMessage(),e);
		} catch (IOException e) {
			throw new CadseException(e.getMessage(),e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					mLogger.log(Level.SEVERE, "load", e);
				}
			}
		}
	}

	public ItemDelta loadFromPersistence(LogicalWorkspaceTransaction wl, InputStream stream) throws CadseException {
		ObjectInputStream input = null;
		try {
			byte[] data = MD5.read(stream);
			IMigrationFormat mig = new MigrationFormat(wl, mLogger);
			ItemDelta item = readFromByteArray(wl, mig, data);
			return item;
		} catch (ClassNotFoundException e) {
			throw new CadseException(e.getMessage(),e);
		} catch (IOException e) {
			throw new CadseException(e.getMessage(),e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					mLogger.log(Level.SEVERE, "load", e);
				}
			}
		}
	}
	/**
	 * Load item.
	 *
	 * @param mig
	 *            the mig
	 * @param fileItem
	 *            the file item
	 * @param delete
	 *            the delete
	 *
	 * @return the item description
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	private ItemDelta loadItem(LogicalWorkspaceTransaction copy, IMigrationFormat mig, File fileItem,
			boolean delete) throws Throwable {
		if (!fileItem.exists()) {
			throw new IOException("Can't find the file : " + fileItem.toString());
		}
		try {
			byte[] data = MD5.read(fileItem);
			return byteArrayToItemDescription(copy, mig, data);
		} catch (FileNotFoundException e) {

			throw e;
		} finally {
			if (DELETE && delete && fileItem.exists()) {
				fileItem.delete();
			}
		}
	}

	/**
	 * Byte array to item description.
	 *
	 * @param mig
	 *            the mig
	 * @param data
	 *            the data
	 * @return
	 *
	 * @return the item description
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws CadseException
	 *             the melusine exception
	 * @throws CoreException
	 */
	private ItemDelta byteArrayToItemDescription(LogicalWorkspaceTransaction copy, IMigrationFormat mig,
			byte[] data) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, CadseException {
		boolean oldLock = pOld.lock;
		try {
			pOld.lock = true;
			pOld.setEnableEvent(false);
			return readFromByteArray(copy, mig, data);
		} finally {

			pOld.setEnableEvent(true);
			pOld.lock = oldLock;
		}
	}

	/**
	 * Read ser_5.
	 *
	 * @param mig
	 *            the mig
	 * @param md5
	 *            the md5
	 * @param input
	 *            the input
	 *
	 * @return the item description
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws CadseException
	 * @throws CoreException
	 */
	private static ItemDelta readSer_5(LogicalWorkspaceTransaction copy, IMigrationFormat mig,
			ObjectInput input) throws IOException, ClassNotFoundException, CadseException {
		// version 5

		// /*int version = */ input.readInt(); /* == 4 */

		UUID id = (UUID) input.readObject();
		String type = (String) input.readObject();
		String longname = (String) input.readObject();
		String shortname = (String) input.readObject();
		CompactUUID it = mig.getITID(type);
		if (it == null) {
			return null;
		}

		// special value
		boolean readOnly = input.readBoolean();
		input.readBoolean(); // remove is open flag
		boolean isValid = input.readBoolean();
		input.readObject(); // remove info attribute

		ItemDelta desc = copy.loadItem(new CompactUUID(id), it);
		desc.setValid(isValid, true);
		desc.setReadOnly(readOnly, true);
		desc.setQualifiedName(longname, true);
		desc.setName(shortname, true);

		// attributs
		while (true) {
			String key = (String) input.readObject();
			if (key == null) {
				break;
			}
			try {
				Object value = input.readObject();
				desc.loadAttribute(key, value);
			} catch (ClassNotFoundException e) {
				mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
			} catch (IOException e) {
				mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
			}
		}
		// set the md5
		//desc.loadAttribute(PersistenceNew2009.WS_PRIVATE_VERSION, md5);

		// outgoing link
		while (true) {
			/*
			 * link type dest id dest long name dest short name dest type info
			 */

			String linkType = (String) input.readObject();
			if (linkType == null) {
				break;
			}
			UUID destId = (UUID) input.readObject();
			String destLongName = (String) input.readObject();
			String destShortName = (String) input.readObject();
			String destTypeName = (String) input.readObject();
			String link_info = (String) input.readObject();
			CompactUUID destTypeID = mig.getOrCreateITID(destTypeName);

			ItemDelta destItem = copy.loadItem(new CompactUUID(destId), destTypeID);
			destItem.setUniqueName(destLongName, true);
			destItem.setShortName(destShortName, true);

			LinkDelta ldesc = desc.loadLink(linkType, destItem);
			ldesc.setInfo(link_info);
		}

		// dervivedlink
		while (true) {
			String linkType = (String) input.readObject();
			if (linkType == null) {
				break;
			}
			UUID destId = (UUID) input.readObject();
			String destUniqueName = (String) input.readObject();
			String destShortName = (String) input.readObject();
			String destTypeName = (String) input.readObject();
			CompactUUID destTypeID = mig.getOrCreateITID(destTypeName);
			String link_info = (String) input.readObject();

			boolean isAggregation = input.readBoolean();
			boolean isRequire = input.readBoolean();
			String originLinkTypeID = (String) input.readObject();
			String originLinkSourceTypeID = (String) input.readObject();
			String originLinkDestinationTypeID = (String) input.readObject();
			CompactUUID uuidOriginLinkDestinationTypeID = mig.getOrCreateITID(originLinkDestinationTypeID);
			CompactUUID uuidOriginLinkSourceTypeID = mig.getOrCreateITID(originLinkSourceTypeID);
			String other = (String) input.readObject();
			int version = 0;
			try {
				version = Integer.parseInt(other);
			} catch (NumberFormatException e) {
				version = 0;
			}

			ItemDelta dest = copy.loadItem(new CompactUUID(destId), destTypeID);
			dest.setShortName(destShortName, true);
			dest.setUniqueName(destUniqueName, true);

			desc.loadDerivedLink(linkType, dest, isAggregation, isRequire, link_info, originLinkTypeID,
					uuidOriginLinkSourceTypeID, uuidOriginLinkDestinationTypeID, version);
		}
		// Components
		while (true) {
			UUID compId = (UUID) input.readObject();
			if (compId == null) {
				break;
			}
			String compUniqueName = (String) input.readObject();
			String compShortName = (String) input.readObject();
			String compTypeName = (String) input.readObject();
			CompactUUID uuidCompTypeName = mig.getOrCreateITID(compTypeName);

			ItemDelta componentItem = copy.loadItem(new CompactUUID(compId), uuidCompTypeName);
			componentItem.setShortName(compShortName, true);
			componentItem.setUniqueName(compUniqueName, true);

			// desc.addComponant(componentItem);
		}

		desc.finishLoad();
		// CadseCore.getWorkspaceDomain().endReadItemDescription(desc, input);
		return desc;
	}

	/**
	 * Read ser_6.
	 *
	 * @param md5
	 *            the md5
	 * @param input
	 *            the input
	 *
	 * @return the item description
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws CadseException
	 * @throws CoreException
	 */
	private static ItemDelta readSer_6(LogicalWorkspaceTransaction copy, ObjectInputStream input)
			throws IOException, ClassNotFoundException, CadseException {
		// version 6

		// /*int version = */ input.readInt(); /* == 6 */

		CompactUUID id = readUUID(input);
		CompactUUID type = readUUID(input);
		ItemType it = copy.getItemType(type);
		String longname = readString(input);
		String shortname = readString(input);

		// special value
		boolean readOnly = input.readBoolean();
		input.readBoolean(); // remove is open flag
		boolean isValid = input.readBoolean();
		readString(input); // remove info attribute

		ItemDelta desc = copy.loadItem(id, type);
		desc.setValid(isValid, true);
		desc.setReadOnly(readOnly, true);
		desc.setUniqueName(longname, true);
		desc.setShortName(shortname, true);

		// attributs
		while (true) {
			String key = readString(input);
			if (key == null) {
				break;
			}
			IAttributeType<?> att = it == null ? null : it.getAttributeType(key);
			if (att != null && att.getAttributeType() != null) {
				input.addClass(att.getAttributeType());
			}
			try {
				Object value = input.readObject();
				if (value instanceof fede.workspace.domain.CompactUUID) {
					value = new CompactUUID(((fede.workspace.domain.CompactUUID)value).getMostSignificantBits(),
							((fede.workspace.domain.CompactUUID)value).getLeastSignificantBits());
				}
				else if (value instanceof fede.workspace.domain.root.type.TWCommitKind) {
					value = TWCommitKind.valueOf(value.toString());
				}
				else if (value instanceof fede.workspace.domain.root.type.TWDestEvol) {
					value = TWDestEvol.valueOf(value.toString());
				}
				else if (value instanceof fede.workspace.domain.root.type.TWEvol) {
					value = TWEvol.valueOf(value.toString());
				}
				else if (value instanceof fede.workspace.domain.root.type.TWUpdateKind) {
					value = TWUpdateKind.valueOf(value.toString());
				}
				else if (value instanceof fr.imag.adele.cadseg.model.type.PositionEnum) {
					value = EPosLabel.valueOf(value.toString());
				}
				desc.loadAttribute(key, value);
			} catch (ClassNotFoundException e) {
				mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
			} catch (IOException e) {
				mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
			}
		}
		// set the md5
		//desc.loadAttribute(PersistenceNew2009.WS_PRIVATE_VERSION, md5);

		// outgoing link
		while (true) {
			/*
			 * link type dest id dest long name dest short name dest type info
			 */

			String linkType = readString(input);

			if (linkType == null) {
				break;
			}
			CompactUUID destId = readUUID(input);
			String destLongName = readString(input);

			String destShortName = readString(input);

			CompactUUID destTypeName = readUUID(input);
			String link_info = readString(input);

			int version = input.readInt();

			ItemDelta destItem = copy.loadItem(destId, destTypeName);
			destItem.setUniqueName(destLongName, true);
			destItem.setShortName(destShortName, true);

			LinkDelta ldesc = desc.loadLink(linkType, destItem);
			ldesc.setInfo(link_info, true);
			ldesc.setVersion(version, true);
		}

		// dervivedlink
		while (true) {
			String linkType = readString(input);
			if (linkType == null) {
				break;
			}
			CompactUUID destId = readUUID(input);
			String destUniqueName = readString(input);
			String destShortName = readString(input);
			CompactUUID destTypeName = readUUID(input);
			String link_info = readString(input);

			boolean isAggregation = input.readBoolean();
			boolean isRequire = input.readBoolean();
			String originLinkTypeID = readString(input);
			CompactUUID originLinkSourceTypeID = readUUID(input);
			CompactUUID originLinkDestinationTypeID = readUUID(input);
			int version = input.readInt();

			ItemDelta dest = copy.loadItem(destId, destTypeName);
			dest.setShortName(destShortName, true);
			dest.setUniqueName(destUniqueName, true);

			desc.loadDerivedLink(linkType, dest, isAggregation, isRequire, link_info, originLinkTypeID,
					originLinkSourceTypeID, originLinkDestinationTypeID, version);
		}
		int size = input.read();
		for (int i = 0; i < size; i++) {
			CompactUUID compId = readUUID(input);
			String compUniqueName = readString(input);
			;
			String compShortName = readString(input);
			;
			CompactUUID compTypeName = readUUID(input);

			ItemDelta componentItem = copy.loadItem(compId, compTypeName);
			componentItem.setShortName(compShortName, true);
			componentItem.setUniqueName(compUniqueName, true);

			// desc.addComponant(componentItem);
		}

		desc.finishLoad();

		return desc;
		// CadseCore.getWorkspaceDomain().endReadItemDescription(desc, input);

	}

	/**
	 * Read uuid.
	 *
	 * @param input
	 *            the input
	 *
	 * @return the compact uuid
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static CompactUUID readUUID(ObjectInput input) throws IOException {
		return new CompactUUID(input.readLong(), input.readLong());
	}

	/**
	 * Read string.
	 *
	 * @param input
	 *            the input
	 *
	 * @return the string
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static String readString(ObjectInput input) throws IOException {
		byte b = input.readByte();
		if (b == 0) {
			return null;
		}
		if (b == 2) {
			return Item.NO_VALUE_STRING;
		}
		if (b == 1) {
			return input.readUTF().intern();
		}
		return null;
	}

	/**
	 * Read from byte array.
	 *
	 * @param mig
	 *            the mig
	 * @param data
	 *            the data
	 * @param md5
	 *            the md5
	 *
	 * @return the item description
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws CadseException
	 * @throws CoreException
	 */
	private static ItemDelta readFromByteArray(LogicalWorkspaceTransaction copy, IMigrationFormat mig,
			byte[] data) throws IOException, ClassNotFoundException, CadseException {
		ObjectInputStream input = null;
		try {

			input = new ObjectInputStream(new ByteArrayInputStream(data));
			int version = input.readInt();
			ItemDelta desc = null;
			if (version == VERSION_6) {
				desc = readSer_6(copy, input);

			} else if (version == VERSION_4) {
				// desc = readSer_4(mig, md5, input);
				// desc.setModified(true);
				// desc.setRecomputeComponantsAndDerivedLink(true);
				// NOTHING
				// OLDER VERSION
			} else if (version == VERSION_5) {
				desc = readSer_5(copy, mig, input);
				if (desc != null) {
					desc.setModified(true);
				}
			}
			return desc;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

}
