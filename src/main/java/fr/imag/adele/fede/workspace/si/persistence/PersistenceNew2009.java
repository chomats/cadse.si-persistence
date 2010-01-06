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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;


import adele.util.io.FileUtil;

import fr.imag.adele.cadse.as.platformide.IPlatformIDE;
import fr.imag.adele.cadse.core.CadseDomain;
import fr.imag.adele.cadse.core.CadseException;
import fr.imag.adele.cadse.core.CadseRuntime;
import java.util.UUID;
import fr.imag.adele.cadse.core.content.ContentItem;
import fr.imag.adele.cadse.core.DerivedLink;
import fr.imag.adele.cadse.core.DerivedLinkDescription;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemDescription;
import fr.imag.adele.cadse.core.ItemDescriptionRef;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.Link;
import fr.imag.adele.cadse.core.LinkDescription;
import fr.imag.adele.cadse.core.LinkType;
import fr.imag.adele.cadse.core.LogicalWorkspace;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.CadseDomain;
import fr.imag.adele.cadse.core.TypeDefinition;
import fr.imag.adele.cadse.core.attribute.IAttributeType;
import fr.imag.adele.cadse.core.transaction.delta.ItemDelta;
import fr.imag.adele.cadse.core.transaction.delta.LinkDelta;
import fr.imag.adele.cadse.core.enumdef.TWCommitKind;
import fr.imag.adele.cadse.core.enumdef.TWDestEvol;
import fr.imag.adele.cadse.core.enumdef.TWEvol;
import fr.imag.adele.cadse.core.enumdef.TWUpdateKind;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;
import fr.imag.adele.cadse.core.ui.EPosLabel;
import fr.imag.adele.cadse.util.Assert;
import fr.imag.adele.fede.workspace.as.persistence.IPersistence;
import fr.imag.adele.teamwork.db.ModelVersionDBException;
import fr.imag.adele.teamwork.db.ModelVersionDBService2;
import fr.imag.adele.teamwork.db.TransactionException;

/**
 * @generated
 */
public class PersistenceNew2009 implements ReadItemType {



	/**
	 * @generated
	 */
	IPlatformIDE				platformEclipse;

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
	// public static final String WS_PRIVATE_VERSION = "##WS:private:version";
	/** The Constant MELUSINE_DIRECTORY. */
	public static final String		MELUSINE_DIRECTORY	= ".cadse";

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
	/** The Constant VERSION_6. */
	private static final int		VERSION_7			= 7;

	/** The Constant CURENT_VERSION. */
	private static final int		CURENT_VERSION		= VERSION_6;

	public static int ITEM_at_ID = -1;
	public static final int INSTANCE_OF = -1;
	public static int PARENT_OF = -1;

	// private UUID id;

	/** The model name. */
	private String[]				modelName;

	/** The model version. */
	private int[]					modelVersion;

	/** The lock. */
	boolean							lock				= false;

	/** The enable event. */
	private boolean					enableEvent			= true;


	/** The m logger. */
	static Logger						mLogger				= Logger.getLogger("SI.Workspace.Persitance");
	static Logger						model_events_log	= Logger.getLogger("Model.Events");

	/** The ws location. */
	File							wsLocation;

	/** The save id. */
	private boolean					saveID;

	/** The create item persistance. */
	// public static ICreateItemPersistance createItemPersistance;
	/** The thread save. */
	static WSPersitanceService		threadSave;
	
	/**
	 * Instantiates a new wS persistance.
	 */
	public PersistenceNew2009() {

	}

	/**
	 * (non-Javadoc).
	 *
	 * @return true, if exist persistance
	 *
	 * @see fede.workspace.role.persistance.IPersistance#existPersistance()
	 */
	public boolean existPersistance() {
		return existPersistance(wsLocation.getAbsolutePath());
	}

	/**
	 * (non-Javadoc).
	 *
	 * @return true, if load
	 *
	 * @see fede.workspace.role.persistance.IPersistance#load()
	 */
	public boolean load() {
		if (wsLocation == null) {
			return false;
		}

		try {
			lock = true;
		//	workspaceCU.beginOperation("WSPersistance.load");
			return load(wsLocation.getAbsolutePath());
		} catch (Throwable e) {
			mLogger.log(Level.SEVERE, "", e);
			return false;
		} finally {
			lock = false;
		//	workspaceCU.endOperation();
		}
	}

	/**
	 * Save.
	 *
	 * @see fede.workspace.role.persistance.IPersistance#save()
	 */
	public void save() {
		if (lock) {
			// save = true;
			return;
		}
		// save = false;
		if (wsLocation == null) {
			return;
		}
		if (workspaceCU.getLogicalWorkspace() == null) {
			return;
		}
		// File location_melusine = new File(wsLocation,MELUSINE_DIRECTORY);
		delete();

		for (Item item : workspaceCU.getLogicalWorkspace().getItems()) {
			try {
				save(item);
			} catch (IOException ex) {
				mLogger.log(Level.SEVERE, "Save workspace : Cannot perform output.", ex);
			} catch (Throwable ex) {
				mLogger.log(Level.SEVERE, "Save workspace : Cannot perform output.", ex);
			}
		}
	}

	public static class ItemHeader {
		public UUID id;
		public UUID typeid;
		public UUID cadseid;
		public String name;
		public String qname;
		public int version;
	}
	/**
	 * The Class IDHeader.
	 *
	 * @author <a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a>
	 */
	public static class IDHeader {

		/** The headerversion. */
		int			headerversion;

		/** The cadses name. */
		String[]	cadsesName;

		/** The version. */
		int[]		version;

		/**
		 * Gets the header version.
		 *
		 * @return the header version
		 */
		public int getHeaderVersion() {
			return headerversion;
		}

		/**
		 * Gets the cadses name.
		 *
		 * @return the cadses name
		 */
		public String[] getCadsesName() {
			return cadsesName;
		}
	}
	
	/**
	 * Load i d_6.
	 *
	 * @param location
	 *            the location
	 *
	 * @return the iD header
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static IDHeader loadID_6(String location) throws IOException, ClassNotFoundException {
		File wsLocation = new File(location);
		File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);

		File f = new File(location_melusine, ID_FILE_NAME);
		if (!f.exists()) {
			throw new IOException("file not found " + f.toString());
		}

		// declared here only to ensure visibilty in finally clause
		ObjectInputStream input = null;
		try {
			// use buffering
			InputStream file = new FileInputStream(f);
			InputStream buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);
			int version = input.readInt();
			List<String> modelName = new ArrayList<String>();
			if (version == VERSION_6) {
				IDHeader h = new IDHeader();
				h.headerversion = version;
				h.cadsesName = (String[]) input.readObject();
				h.version = (int[]) input.readObject();
				return h;
			}
			if (version == VERSION_4) {
				/* UUID id = (UUID) */input.readObject();
				modelName.add(input.readUTF());
				/* String workspaceTypeName = */input.readUTF();
			} else if (version == VERSION_5) {
				modelName.add(input.readUTF());
			}
			IDHeader h = new IDHeader();
			h.headerversion = version;
			h.cadsesName = modelName.toArray(new String[modelName.size()]);
			h.version = new int[h.cadsesName.length];
			return h;
		} finally {
			try {
				if (input != null) {
					// close "input" and its underlying streams
					input.close();
				}
			} catch (IOException ex) {
				mLogger.log(Level.SEVERE, "Cannot close input stream.", ex);
			}
		}
	}
	
	/**
	 * Load i d2.
	 *
	 * @param location
	 *            the location
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	void loadID2(String location) throws IOException, ClassNotFoundException {
		IDHeader h = loadID_6(location);
		modelName = h.cadsesName;
		modelVersion = h.version;
		saveID = h.headerversion != CURENT_VERSION;
	}
	
	/**
	 * save the workspace id and the type. File format is < version, array of
	 * string >
	 *
	 * @param name
	 *            the name
	 * @param versions
	 *            the versions
	 */
	private void saveID_6(String[] name, int[] versions) {
		if (wsLocation == null) {
			return;
		}
		File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);

		if (name == null) {
			return;
		}
		if (name.length == 0) {
			return;
		}
		// declared here only to ensure visibilty in finally clause
		mLogger.info("SAVE ID " + Arrays.asList(name).toString());
		writeIDFileV6(location_melusine, name, versions);
		this.modelName = name;
		this.modelVersion = versions;
	}
	
	/**
	 * Write id file v6.
	 *
	 * @param location_melusine
	 *            the location_melusine
	 * @param modelName
	 *            the model name
	 * @param versions
	 *            the versions
	 */
	static public void writeIDFileV6(File location_melusine, String[] modelName, int[] versions) {
		ObjectOutput output = null;
		File f = null;
		try {
			if (!location_melusine.exists()) {
				location_melusine.mkdirs();
			}

			// use buffering
			f = new File(location_melusine, ID_FILE_NAME);
			if (!f.exists()) {
				// f.getParentFile().mkdirs();
				f.createNewFile();
			}
			OutputStream file = new FileOutputStream(f);
			OutputStream buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			output.writeInt(VERSION_6);
			output.writeObject(modelName);
			output.writeObject(versions);
			output.flush();
		} catch (IOException ex) {
			mLogger.log(Level.SEVERE, "Save workspace : Cannot perform output.", ex);
			if (f != null) {
				f.delete();
			}
		} catch (Throwable ex) {
			mLogger.log(Level.SEVERE, "Save workspace : Cannot perform output.", ex);
			if (f != null) {
				f.delete();
			}
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					mLogger.log(Level.SEVERE, "Save workspace : Cannot perform close.", e);
				}
			}
		}
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#load(java.lang.String)
	 */
	public boolean load(final String location, CadseDomain workspaceCU) {
		this.workspaceCU = workspaceCU;
		//workspaceCU.beginOperation("load");
		long start = System.currentTimeMillis();
		try {
			
			File wsLocation = new File(location);
			File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);

			// load the workspace information : cadse name;
			loadID2(location);

			try {
				LogicalWorkspace workspaceLogique = workspaceCU.getLogicalWorkspace();
				LoadState mig = new LoadStateImpl(this, workspaceLogique, mLogger);
				Map<UUID, ItemDelta> items = new HashMap<UUID, ItemDelta>();

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
		//	workspaceCU.endOperation();
			mLogger.finest("Load peristance in " + (System.currentTimeMillis() - start) + " ms.");
		}

		return true;
	}
	
	/**
	 * Delete.
	 *
	 * @see fede.workspace.role.persistance.IPersistance#delete()
	 */
	public void delete() {

		if (wsLocation == null) {
			return;
		}

		File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);
		if (location_melusine.exists()) {
			FileUtil.deleteDir(location_melusine);
		}
	}

	/**
	 * Exist persistance.
	 *
	 * @param location
	 *            the location
	 *
	 * @return true, if exist persistance
	 *
	 * @see fede.workspace.role.persistance.IPersistance#existPersistance(java.lang.String)
	 */
	public boolean existPersistance(String location) {
		File wsLocation = new File(location);
		File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);

		File f = new File(location_melusine, ID_FILE_NAME);
		return f.exists();
	}

	public boolean load(String location) {
		return load(location, workspaceCU);
	}

	/**
	 * Delete.
	 *
	 * @param item
	 *            the item
	 */
	public void delete(ItemDescription item) {

		mLogger.info("delete " + item.getName());

		// new version...
		delete(fileInWorkspaceV2(item.getId(), false));

		// new version...
		delete(fileInWorkspaceXMLV2(item.getId()));
		mLogger.info("delete " + item.getName());

	}

	/**
	 * Delete.
	 *
	 * @param item
	 *            the item
	 */
	public void delete(Item item) {

		mLogger.info("delete " + item.getName());
		model_events_log.info("DELETE " + item.getQualifiedName() + " (" + item.getId() + ")");
		// old version ...
		delete(fileInWorkspace(item));

		// new version...
		delete(fileInWorkspaceV2(item.getId(), false));

		// new version...
		delete(fileInWorkspaceXMLV2(item.getId()));

	}

	/**
	 * Delete.
	 *
	 * @param itemFile
	 *            the item file
	 */
	void delete(File itemFile) {
		if (itemFile != null && itemFile.exists()) {
			if (!itemFile.delete()) {
				itemFile.deleteOnExit();
				mLogger.log(Level.SEVERE, "Cannot delete the file " + itemFile);
			}
		}
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
	private void loadItemDescriptionFromRepo(LogicalWorkspaceTransaction copy, LoadState mig,
			File location_melusine, IProgressMonitor monitor, Map<UUID, ItemDelta> items) throws IOException {
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
						delete(file);
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
			LoadState mig = new LoadStateImpl(this, wl, mLogger);
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
			LoadState mig = new LoadStateImpl(this, wl, mLogger);
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
	private ItemDelta loadItem(LogicalWorkspaceTransaction copy, LoadState mig, File fileItem,
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
	 * sauvegarde un item, si l'item est le workspace current, je saugarde l'id.
	 *
	 * @param item
	 *            the item
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	public void save(final Item item) throws Throwable {
		
		saveModelNameIfNeed();
		// pas avant le saveID sinon on ne sauve pas l'id du
		// currentworkspace...
		LoadState loadState = new LoadStateImpl(this, this.workspaceCU.getLogicalWorkspace(), mLogger);
		saveInternal(loadState, item);
		loadState.finishWrite();
	}

	/**
	 * Save internal.
	 *
	 * @param item
	 *            the item
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	void saveInternal(LoadState loadState, final Item item) throws IOException, FileNotFoundException, NoSuchAlgorithmException {
		if (item.isStatic()) {
			mLogger.log(Level.WARNING, "Cannot save item {0} : it is static", item.getId());
			return;
		}
		
		// write in byte array
		byte[] data = itemToByteArray(loadState, item);

		// write the file in repo
		File itemFile = fileInWorkspaceV2(item);
		write(data, itemFile, true);

		try {
			// write the file in resource
			File resourceFile = fileInResource(item);
			if (resourceFile != null) {
				write(data, resourceFile, true);
			}
		} catch (Throwable e) {
			mLogger.log(Level.SEVERE,"", e);
		}

		// log
		mLogger.info("SAVE ITEM " + item.getName() + " : " + item.getId());
		model_events_log.info("SAVE ITEM " + item.getQualifiedName() + " : " + item.getId());
	}
	

	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#readOrphanRepository(fr.imag.adele.cadse.core.IWorkspaceLogique,
	 *      java.io.File)
	 */
	public Item[] readOrphanRepository(LogicalWorkspace ws, File repository) {
		File def = new File(repository, ".melusine");

		File[] defFiles = def.listFiles();
		List<Item> ret = new ArrayList<Item>();
		if (defFiles == null) {
			return new Item[0];
		}
		LoadState mig = new LoadStateImpl(this, ws, mLogger);
		// TODO compute mapIt
		for (File df : defFiles) {
			ObjectInputStream input = null;
			try {
				if (!df.exists()) {
					continue;
				}
				if (df.isDirectory()) {
					continue;
				}

				input = new ObjectInputStream(new FileInputStream(df));

				ItemHeader ref = readSerHeader_4_or_5_6(mig, input);

				if (!(df.getName().equals(ref.id.toString()))) {
					throw new CadseException("Bad id " + df.getName() + "!=" + ref.id.toString());
				}

				// WorkspaceDomain wd = ws.getWorkspaceDomain();
				ItemType it = ws.getItemType(ref.typeid);
				// if (it == null) {
				// it = wd.getItemType(type);
				// }
				if (it == null) {
					throw new CadseException("Item type " + ref.typeid + " is missing.");
				}

				Item item = ws.loadItem(new ItemDescriptionRef(ref.id, it, ref.qname, ref.name));

				ret.add(item);
				input.close();
			} catch (Throwable e) {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e1) {
					}
				}
				e.printStackTrace();
				// MessageDialog.openError(shell,Messages.getString("action.run.2")+item.getItem().getId(),e.getMessage());
				// //$NON-NLS-1$

			} finally {

			}
		}
		return ret.toArray(new Item[ret.size()]);
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
	private ItemDelta byteArrayToItemDescription(LogicalWorkspaceTransaction copy, LoadState mig,
			byte[] data) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, CadseException {
		boolean oldLock = lock;
		try {
			lock = true;
			setEnableEvent(false);
			return readFromByteArray(copy, mig, data);
		} finally {

			setEnableEvent(true);
			lock = oldLock;
		}
	}
	
	/**
	 * File in resource.
	 *
	 * @param item
	 *            the item
	 *
	 * @return the file
	 *
	 * @throws CoreException
	 *             the core exception
	 */
	File fileInResource(Item item) throws CoreException {
		IResource r = getResourceFromItem(item);
		if (r == null) {
			return null;
		}
		IPath path = r.getLocation();
		if (path == null) {
			return null;
		}
		File fileResource = path.toFile();

		if (fileResource.equals(wsLocation)) {
			return null;
		}

		if (fileResource.isFile() || !fileResource.exists()) {
			if (true) {
				return null;
			} else {
				return new File(fileResource.getParentFile(), ".melusine-" + fileResource.getName().replace('.', '_'));
			}
		} else {
			return new File(fileResource, ".melusine.ser");
		}

	}

	public IResource getResourceFromItem(Item item)
			throws CoreException {

		if (item == null || !item.isResolved()) {
			return null;
		}

		return item.getMainMappingContent(IResource.class);
	}

	/**
	 * File in resource xml.
	 *
	 * @param item
	 *            the item
	 *
	 * @return the file
	 *
	 * @throws CoreException
	 *             the core exception
	 */
	File fileInResourceXml(Item item) throws CoreException {
		IResource r = getResourceFromItem(item);
		if (r == null) {
			return null;
		}
		IPath path = r.getLocation();
		if (path == null) {
			return null;
		}
		File fileResource = path.toFile();

		if (fileResource.equals(wsLocation)) {
			return null;
		}

		if (fileResource.isFile() || !fileResource.exists()) {
			if (true) {
				return null;
			} else {
				return new File(fileResource.getParentFile(), ".melusine-" + fileResource.getName().replace('.', '_')
						+ ".xml");
			}
		} else {
			return new File(fileResource, ".melusine.xml");
		}

	}

	/**
	 * File in workspace v2.
	 *
	 * @param id2
	 *            the id2
	 * @param mustExists
	 *            the must exists
	 *
	 * @return the file
	 */
	File fileInWorkspaceV2(UUID id2, boolean mustExists) {
		File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);
		if (!location_melusine.exists()) {
			location_melusine.mkdirs();
		}
		File itemFile = new File(location_melusine, id2 + ".ser");
		if (!mustExists || itemFile.exists()) {
			return itemFile;
		}
		itemFile = new File(location_melusine, id2.toString());
		if (!mustExists || itemFile.exists()) {
			return itemFile;
		}
		return itemFile;
	}

	/**
	 * File in workspace xml v2.
	 *
	 * @param id2
	 *            the id2
	 *
	 * @return the file
	 */
	File fileInWorkspaceXMLV2(UUID id2) {
		File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);
		if (!location_melusine.exists()) {
			location_melusine.mkdirs();
		}
		File itemFile = new File(location_melusine, id2 + ".xml");
		return itemFile;
	}

	/**
	 * File in workspace.
	 *
	 * @param item
	 *            the item
	 *
	 * @return the file
	 */
	File fileInWorkspace(Item item) {
		File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);
		if (!location_melusine.exists()) {
			location_melusine.mkdirs();
		}
		File itemFile = new File(location_melusine, item.getId().toString());
		return itemFile;
	}

	/**
	 * File in workspace v2.
	 *
	 * @param item
	 *            the item
	 *
	 * @return the file
	 */
	File fileInWorkspaceV2(Item item) {
		return fileInWorkspaceV2(item.getId(), false);
	}


	/**
	 * Read item i d_6.
	 *
	 * @param input
	 *            the input
	 *
	 * @return the compact uuid
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static UUID readItemID_6(ObjectInput input) throws IOException, ClassNotFoundException {
		int version = input.readInt();
		if (version == 6) {
			return readUUID(input);
		}

		UUID id = (UUID) input.readObject();
		return id;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#getCadsesName()
	 */
	public String[] getCadsesName() {
		if (modelName == null) {
			File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);
			File f = new File(location_melusine, ID_FILE_NAME);

			if (!f.exists()) {
				return null;
			}
			try {
				loadID2(wsLocation.getAbsolutePath());
			} catch (IOException e) {
				mLogger.log(Level.SEVERE, "", e);
			} catch (ClassNotFoundException e) {
				mLogger.log(Level.SEVERE, "", e);
			}
		}
		return modelName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#getCadsesVersion()
	 */
	public int[] getCadsesVersion() {
		getCadsesName(); // force load ...
		return modelVersion;
	}

	public void setEnableEvent(boolean enableEvent) {
		this.enableEvent = enableEvent;
	}

	public boolean isEnableEvent() {
		return enableEvent;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#setModelname(java.lang.String[],
	 *      int[])
	 */
	public void setModelname(String[] wsModel, int[] versions) {
		this.modelName = wsModel;
		this.modelVersion = versions;
		saveID = true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#forceSave()
	 */
	public void forceSave() {
		saveID = true;
		saveAll();
	}


	/**
	 * Read ser header_6.
	 *
	 * @param input
	 *            the input
	 *
	 * @return the item description ref
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static ItemHeader readSerHeader_7(LoadState mig, ObjectInput input) throws IOException, ClassNotFoundException {
		// version 6
		ItemHeader ret = new ItemHeader();
		ret.id = readUUID(input);
		ret.typeid = readUUID(input);;
		ret.cadseid = readUUID(input);;
		ret.qname = readString(input);
		ret.name = readString(input);
		ret.version = input.readInt();
		return ret;
	}
	
	/*
	 * 
	 */
	static public void writeSer_7_HEADER(LoadState loadState, ObjectOutput output, Item item, boolean headerOnly) throws IOException {
		
		/*
		 * HEADER
		 * Int version
		 * UUID id item
		 * UUID id type
		 * UUID id cadse
		 * String qualified name
		 * String name
		 * int tw-version
		 * boolean header only ? true or false
		 */
		output.writeInt(VERSION_7);
		writeUUID(output, item.getId());
		writeUUID(output, item.getType().getId()); //7 -- type ID + cadse ID
		if (!headerOnly)
			loadState.addToWriteHeader(item.getType());
		CadseRuntime cadse = item.getCadse();
		if (cadse == null) {
			writeUUID(output, null);
		}
		else {
			writeUUID(output, cadse.getId());
			if (!headerOnly)
				loadState.addToWriteHeader(cadse);
		}
		writeString(output, item.getQualifiedName());
		writeString(output, item.getName());
		output.writeInt(item.getVersion());
		
		output.writeBoolean(headerOnly);
	}
	
	
	/**
	 * Write ser_7.
	 *
	 * @param output
	 *            the output
	 * @param item
	 *            the item
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static public void writeSer_7(LoadState loadState, ObjectOutput output, Item item) throws IOException {
		
		/*
		 *  HEADER
		 * Int version
		 * UUID id item
		 * UUID id type
		 * UUID id cadse
		 * String qualified name
		 * String name
		 * int tw-version
		 * boolean header only ? true or false
		 * 
		 * bool
		 * bool
		 * 
		 * (
		 * UUID att def id (header)
		 * UUID id of type definition where att def is defined (header)
		 * Object value of this attribue
		 * ) null terminate
		 * 
		 * 
		 * link type dest id dest long name dest short name dest type info
		 * (
		 * UUID type id (header)
		 * UUID source type id (header)
		 * UUID dest type id (header)
		 * UUID dest id (header)
				 * (
				 * UUID att def id (header)
				 * UUID id of type definition where att def is defined (header)
				 * Object value of this attribue
				 * ) null terminate
		 * ) null terminate
		 */
		
		writeSer_7_HEADER(loadState, output, item, false);

		// special value
		output.writeBoolean(item.isReadOnly());
		output.writeBoolean(item.isValid());
		
		
		
		// attributs
		IAttributeType<?>[] keys = item.getLocalAllAttributeTypes();
		Arrays.sort(keys);
		for (IAttributeType<?> attDef : keys) {
			if (attDef.isTransient()) {
				continue;
			}
			Object value = item.getAttributeOwner(attDef);
			if (value == null) {
				continue;
			}
			if (attDef instanceof LinkType) {
				continue;
			}
			/*
			 * UUID att def id (header)
			 * UUID id of type definition where att def is defined (header)
			 * Object value of this attribue
			 * 
			 */
			
			writeUUID(output, attDef.getId());
			loadState.addToWriteHeader(attDef);
			writeUUID(output, attDef.getSource().getId());
			loadState.addToWriteHeader(attDef.getSource());
				try {
					output.writeObject(value);
				} catch (IOException e) {
					mLogger.log(Level.SEVERE, "Cannot save item " + item.getId() + " error when write the value of the key '" + attDef.getName()
							+ "', class of value is " + item.getAttribute(attDef).getClass(), e);
					throw e;
				}
			
		}
		writeUUID(output, null);

		// outgoing link
		List<? extends Link> outgoing = item.getOutgoingLinks();
		for (Link link : outgoing) {
			if (link.isDerived()) { // Filter derivedLink
				continue;
			}
			/*
			 * link type dest id dest long name dest short name dest type info
			 * 
			 * UUID type id (header)
			 * UUID source type id (header)
			 * UUID dest id (header)
			 * 
			 */
			LinkType linkType = link.getLinkType();
			writeUUID(output, linkType.getId());
			loadState.addToWriteHeader(linkType);
			writeUUID(output, linkType.getSource().getId());
			loadState.addToWriteHeader(linkType.getSource());
			writeUUID(output, linkType.getDestination().getId());
			loadState.addToWriteHeader(linkType.getDestination());
			writeUUID(output, link.getDestinationId());
			loadState.addToWriteHeader(link.getDestination());
			
			IAttributeType<?>[] linkAttribute = linkType.getLinkTypeAttributeTypes();
			Arrays.sort(linkAttribute);
			for (IAttributeType<?> attDef : linkAttribute) {
				if (attDef.isTransient()) {
					continue;
				}
				Object value = link.getLinkAttributeOwner(attDef);
				if (value == null) {
					continue;
				}
				if (attDef instanceof LinkType) {
					continue;
				}
				/*
				 * UUID att def id (header)
				 * UUID id of type definition where att def is defined (header)
				 * Object value of this attribue
				 * 
				 */
				
				writeUUID(output, attDef.getId());
				loadState.addToWriteHeader(attDef);
				writeUUID(output, attDef.getSource().getId());
				loadState.addToWriteHeader(attDef.getSource());
					try {
						output.writeObject(value);
					} catch (IOException e) {
						mLogger.log(Level.SEVERE, "Cannot save item " + item.getId() + " error when write the value of the key '" + attDef.getName()
								+ "', class of value is " + item.getAttribute(attDef).getClass(), e);
						throw e;
					}
				
			}
			writeUUID(output, null);
		}
		writeUUID(output, null);
	}
	
	/**
	 * Item to byte array.
	 *
	 * @param item
	 *            the item
	 *
	 * @return the byte[]
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static byte[] itemToByteArray(LoadState loadState, Item item) throws IOException {
		ObjectOutputStream output = null;
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(256);
		try {
			output = new ObjectOutputStream(byteOut);
			writeSer_7(loadState, output, item);
			output.flush();
		} finally {
			if (output != null) {
				output.close();
			}
		}
		return byteOut.toByteArray();
	}
	
	/**
	 * Item to byte array.
	 *
	 * @param item
	 *            the item
	 *
	 * @return the byte[]
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static byte[] itemToByteArrayHeader(Item item) throws IOException {
		ObjectOutputStream output = null;
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(256);
		try {
			output = new ObjectOutputStream(byteOut);
			writeSer_7_HEADER(null, output, item, true);
			output.flush();
		} finally {
			if (output != null) {
				output.close();
			}
		}
		return byteOut.toByteArray();
	}

	/**
	 * Write.
	 *
	 * @param data
	 *            the data
	 * @param itemFile
	 *            the item file
	 * @param deleteFileIfError
	 *            the delete file if error
	 *
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void write(byte[] data, File itemFile, boolean deleteFileIfError) throws FileNotFoundException,
			IOException {
		mkdirs(itemFile.getParentFile());
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(itemFile);
			output.write(data, 0, data.length);
			output.flush();
			output.close();
		} catch (IOException e) {
			if (output != null) {
				output.close();
			}
			if (deleteFileIfError && itemFile.exists()) {
				itemFile.delete();
			}
			throw e;
		}
	}

	/**
	 * Mkdirs.
	 *
	 * @param dir
	 *            the dir
	 */
	private static void mkdirs(File dir) {
		dir.mkdirs();
	}

	/**
	 * Write ser.
	 *
	 * @param desc
	 *            the desc
	 * @param f
	 *            the f
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	public static void writeSer(LoadState loadState, ItemDelta desc, File f) throws IOException, FileNotFoundException {
		byte[] data = itemToByteArray(loadState, desc);
		write(data, f, true);
	}

	/**
	 * Write ser.
	 *
	 * @param item
	 *            the item
	 * @param f
	 *            the f
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	public static void writeSer(LoadState loadState, Item item, File f) throws IOException, FileNotFoundException {
		byte[] data = itemToByteArray(loadState, item);
		write(data, f, true);
	}

	/**
	 * Read ser header_4_or_5_6.
	 *
	 * @param mig
	 *            the mig
	 * @param input
	 *            the input
	 *
	 * @return the item description ref
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static ItemHeader readSerHeader_4_or_5_6(LoadState mig, ObjectInput input)
			throws IOException, ClassNotFoundException {
		// version 4

		int version = input.readInt();
		if (version == 6) {
			return readSerHeader_6(mig, input);
		}
		if (version == 7) {
			return readSerHeader_7(mig, input);
		}

		ItemHeader ret = new ItemHeader();
		UUID id = (UUID) input.readObject();
		String type = (String) input.readObject();
		ret.id = id;
		ret.qname = (String) input.readObject();
		ret.name = (String) input.readObject();
		ret.typeid = mig.getOrCreateITID(type);
		return ret;
	}

	/**
	 * Read ser header_6.
	 *
	 * @param input
	 *            the input
	 *
	 * @return the item description ref
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static ItemHeader readSerHeader_6(LoadState mig, ObjectInput input) throws IOException, ClassNotFoundException {
		// version 6

		ItemHeader ret = new ItemHeader();
		ret.id = readUUID(input);
		ret.typeid =readUUID(input);
		ret.qname = readString(input);
		ret.name =  readString(input);
		
		return ret;
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
	private static ItemDelta readSer_5(LogicalWorkspaceTransaction copy, LoadState loadState,
			ObjectInput input) throws IOException, ClassNotFoundException, CadseException {
		// version 5

		// /*int version = */ input.readInt(); /* == 4 */

		UUID id = (UUID) input.readObject();
		String type = (String) input.readObject();
		String qualifiedName = (String) input.readObject();
		String name = (String) input.readObject();
		UUID itID = loadState.getITID(type);
		if (itID == null) {
			return null;
		}

		ItemType itemType = loadState.getItemType(itID);
		// special value
		boolean readOnly = input.readBoolean();
		input.readBoolean(); // remove is open flag
		boolean isValid = input.readBoolean();
		input.readObject(); // remove info attribute

		ItemDelta desc = copy.loadItem(id, itemType);
		desc.setValid(isValid, true);
		desc.setReadOnly(readOnly, true);
		desc.setQualifiedName(qualifiedName, true);
		desc.setName(name, true);

		// attributs
		while (true) {
			String key = (String) input.readObject();
			if (key == null) {
				break;
			}
			
			try {
				Object value = input.readObject();
				IAttributeType<?> att = loadState.getAttribute(itemType, key);
				if (att == null) continue;
				desc.loadAttribute(att, value);
			} catch (ClassNotFoundException e) {
				mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
			} catch (IOException e) {
				mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
			}
		}

		// outgoing link
		while (true) {
			/*
			 * link type dest id dest long name dest short name dest type info
			 */

			String linkTypeID = (String) input.readObject();
			if (linkTypeID == null) {
				break;
			}
			UUID destId = (UUID) input.readObject();
			String destQualifiedName = (String) input.readObject();
			String destName = (String) input.readObject();
			String destTypeName = (String) input.readObject();
			String link_info = (String) input.readObject();
			UUID destTypeID = loadState.getOrCreateITID(destTypeName);
			ItemType destType = loadState.getItemType(destTypeID);

			ItemDelta destItem = copy.loadItem(destId, destType);
			destItem.setQualifiedName(destQualifiedName, true);
			destItem.setName(destName, true);

			LinkType linkType = loadState.getLinkType(itemType, linkTypeID);
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
			UUID destTypeID = loadState.getOrCreateITID(destTypeName);
			ItemType destType = loadState.getItemType(destTypeID);
			String link_info = (String) input.readObject();

			boolean isAggregation = input.readBoolean();
			boolean isRequire = input.readBoolean();
			String originLinkTypeID = (String) input.readObject();
			String originLinkSourceTypeID = (String) input.readObject();
			String originLinkDestinationTypeID = (String) input.readObject();
			UUID uuidOriginLinkDestinationTypeID = loadState.getOrCreateITID(originLinkDestinationTypeID);
			UUID uuidOriginLinkSourceTypeID = loadState.getOrCreateITID(originLinkSourceTypeID);
			String other = (String) input.readObject();
			int version = 0;
			try {
				version = Integer.parseInt(other);
			} catch (NumberFormatException e) {
				version = 0;
			}

			ItemDelta dest = copy.loadItem((destId), destTypeID);
			dest.setName(destShortName, true);
			dest.setQualifiedName(destUniqueName, true);

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
			UUID uuidCompTypeName = loadState.getOrCreateITID(compTypeName);

			ItemDelta componentItem = copy.loadItem((compId), uuidCompTypeName);
			componentItem.setName(compShortName, true);
			componentItem.setQualifiedName(compUniqueName, true);

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
	private static ItemDelta readSer_6(LogicalWorkspaceTransaction copy, LoadState loadState, ObjectInputStream input)
			throws IOException, ClassNotFoundException, CadseException {
		// version 6

		// /*int version = */ input.readInt(); /* == 6 */

		UUID id = readUUID(input);
		UUID type = readUUID(input);
		ItemType it = copy.getItemType(type);
		if (it == null) return null;
		String qualifiedName = readString(input);
		String name = readString(input);

		// special value
		boolean readOnly = input.readBoolean();
		input.readBoolean(); // remove is open flag
		boolean isValid = input.readBoolean();
		readString(input); // remove info attribute

		ItemDelta desc = copy.loadItem(id, type);
		desc.setLoaded(true);
		desc.setValid(isValid, true);
		desc.setReadOnly(readOnly, true);
		desc.setQualifiedName(qualifiedName, true);
		desc.setName(name, true);

		// attributs
		while (true) {
			String key = readString(input);
			if (key == null) {
				break;
			}
			
			IAttributeType<?> att = loadState.getAttribute(it,key);
			if (att != null && att.getAttributeType() != null) {
				input.addClass(att.getAttributeType());
			}
			try {
				Object value = input.readObject();
				if (value instanceof fede.workspace.domain.CompactUUID) {
					value = new UUID(((fede.workspace.domain.CompactUUID)value).getMostSignificantBits(),
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
				if (att == null) continue;
				desc.loadAttribute(att, value);
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

			String linkTypeID = readString(input);

			if (linkTypeID == null) {
				break;
			}
			UUID destId = readUUID(input);
			String destQualifiedName = readString(input);

			String destName = readString(input);

			UUID destTypeName = readUUID(input);
			ItemType destType = loadState.getItemType(destTypeName);
			String link_info = readString(input);

			int version = input.readInt();

			ItemDelta destItem = copy.loadItem(destId, destType);
			if (!destItem.isStatic()) {
				destItem.setQualifiedName(destQualifiedName, true);
				destItem.setName(destName, true);
			}
			
			LinkType linkType = loadState.getLinkType(it, linkTypeID);
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
			UUID destId = readUUID(input);
			String destUniqueName = readString(input);
			String destShortName = readString(input);
			UUID destTypeName = readUUID(input);
			ItemType destItemType = loadState.getItemType(destTypeName);
			String link_info = readString(input);

			boolean isAggregation = input.readBoolean();
			boolean isRequire = input.readBoolean();
			String originLinkTypeID = readString(input);
			UUID originLinkSourceTypeID = readUUID(input);
			UUID originLinkDestinationTypeID = readUUID(input);
			int version = input.readInt();

			ItemDelta dest = copy.loadItem(destId, destItemType);
			dest.setName(destShortName, true);
			dest.setQualifiedName(destUniqueName, true);

			desc.loadDerivedLink(linkType, dest, isAggregation, isRequire, link_info, originLinkTypeID,
					originLinkSourceTypeID, originLinkDestinationTypeID, version);
		}
		int size = input.read();
		for (int i = 0; i < size; i++) {
			UUID compId = readUUID(input);
			String compUniqueName = readString(input);
			String compShortName = readString(input);
			UUID compTypeName = readUUID(input);
			ItemType compType = loadState.getItemType(compTypeName);
			
			ItemDelta componentItem = copy.loadItem(compId, compType);
			componentItem.setName(compShortName, true);
			componentItem.setQualifiedName(compUniqueName, true);

			// desc.addComponant(componentItem);
		}

		desc.finishLoad();

		return desc;
		// CadseCore.getWorkspaceDomain().endReadItemDescription(desc, input);

	}
	
	static public void  importInDB(ModelVersionDBService2 db,MigrationDB mig,
			File location_melusine) throws IOException {
		File[] itemsFiles = location_melusine.listFiles();
		if (itemsFiles == null || itemsFiles.length == 0) {
			throw new IOException("No file in " + location_melusine.toString());
		}
		int i = 0;
		int l = itemsFiles.length;

		for (File file : itemsFiles) {
			i++;
			mLogger.log(Level.WARNING, "Processing "+i+"/"+l);
			String nameFile = file.getName();
			if (nameFile.equals(ID_FILE_NAME)) {
				continue;
			}
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
					byte[] data = MD5.read(file);
					ObjectInputStream input = null;
					ByteArrayInputStream inputArray  = null;
					try {

						inputArray = new ByteArrayInputStream(data);
						input = new ObjectInputStream(inputArray);
						int version = input.readInt();
						if (version == VERSION_6) {
							readSer_6(db, mig, input);
						} 
					} finally {
						if (input != null) {
							input.close();
						}
					}
				} catch (Throwable e) {
					mLogger.log(Level.SEVERE, "load "+nameFile, e);
				}
				continue;
			}
		}
	}
	
	public static class MigrationDB {
		public Set<Integer> transientAttributes = new HashSet<Integer>();
		public Map<String, Integer> mapAttribute = new HashMap<String, Integer>();
		public Set<String> ignoreAttribute = new HashSet<String>();
		
		public boolean ignoreAttribute(int typeId, String attributeName){
			return ignoreAttribute.contains("{"+typeId+"}"+attributeName);
		}
		
		public int mapAttribute(String key) {
			Integer ret = mapAttribute.get(key);
			if (ret == null) return -1;
			return ret;
		}
		
		public void addIgnoreAttribute(int typeId, String attributeName) {
			ignoreAttribute.add("{"+typeId+"}"+attributeName);
		}
		
		public void addMapAttribute(String attributeName, int attributeId) {
			mapAttribute.put(attributeName, attributeId);
		}
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
	 * @throws ModelVersionDBException 
	 * @throws TransactionException 
	 * @throws CoreException
	 */
	private static int readSer_6(ModelVersionDBService2 db, MigrationDB mig, ObjectInputStream input)
			throws IOException, ClassNotFoundException, CadseException, ModelVersionDBException, TransactionException {
		// version 6

		// /*int version = */ input.readInt(); /* == 6 */
		UUID id = readUUID(input);
		UUID type = readUUID(input);
		int typeId = db.checkLocalIdentifier(type);
		if (typeId == -1)  {
			mLogger.log(Level.SEVERE, "Cannot found type "+type);
			return -1;
		}
		if (id.equals(type)){
			mLogger.log(Level.SEVERE, "Meta type? "+type);
		}
		String qualifiedName = readString(input);
		String name = readString(input);
		if ("<no value>".equals(name))
			name = null;

		// special value
		boolean readOnly = input.readBoolean();
		input.readBoolean(); // remove is open flag
		boolean isValid = input.readBoolean();
		readString(input); // remove info attribute
		try {
			db.beginTransaction(); 
		
			
			int objectId = db.getOrCreateLocalIdentifier(id);
			db.saveObject(objectId , ModelVersionDBService2.OBJECT_STATE_NORMAL, ids(typeId), -1, qualifiedName, name, null);
			if (id.equals(type)){
				mLogger.log(Level.SEVERE, "Meta type? "+objectId);
			}
			db.setObjectIsValid(objectId, isValid);
			db.setObjectIsReadOnly(objectId, readOnly);
			
			// attributs
			while (true) {
				String key = readString(input);
				if (key == null) {
					break;
				}
				int attId = mig.mapAttribute(key);
				if (attId == -1) {
					attId = db.findAttributeWithName(objectId, key);
				}
				Object value = null;
				try {
					value = input.readObject();
					if (value instanceof fede.workspace.domain.CompactUUID) {
						value = new UUID(((fede.workspace.domain.CompactUUID)value).getMostSignificantBits(),
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
					if (mig.ignoreAttribute(typeId, key) || mig.transientAttributes.contains(attId))
						continue;
					if (attId == -1) {
						mLogger.log(Level.SEVERE, "Cannot found attribute "+
								key+" for objecId "+objectId+" for type of object "+typeId+
								" value class "+(value == null ? "?":value.getClass()));
						continue;
					}
					if (attId == ITEM_at_ID) continue;
					
					db.setObjectValue(objectId, attId, value);
				} catch (ClassNotFoundException e) {
					mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
				} catch (IOException e) {
					mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
				} catch (ModelVersionDBException e) {
					mLogger.log(Level.SEVERE, "Can't write the value for " + key+","+
							value+"("+value == null ? "?": value.getClass()+")", e);
				}
			}
			// set the md5
			//desc.loadAttribute(PersistenceNew2009.WS_PRIVATE_VERSION, md5);
	
			// outgoing link
			while (true) {
				/*
				 * link type dest id dest long name dest short name dest type info
				 */
	
				String linkTypeID = readString(input);
	
				if (linkTypeID == null) {
					break;
				}
				
				UUID destId = readUUID(input);
				String destQualifiedName = readString(input);
				String destName = readString(input);
				UUID destTypeName = readUUID(input);
				
				readString(input); // not used
	
				int version = input.readInt();
	
				int attId = mig.mapAttribute(linkTypeID);
				if (attId == -1) {
					attId = db.findAttributeWithName(objectId, linkTypeID);
				}
				
				if (attId == -1)  {
					mLogger.log(Level.SEVERE, "Cannot found link type "+linkTypeID+" for objecId "+objectId+" for type of object "+typeId);
					continue;
				};
				int localDestTypeId = db.checkLocalIdentifier(destTypeName);
				int localDestId = db.checkLocalIdentifier(destId);
				if (localDestTypeId == -1) {
					mLogger.log(Level.SEVERE, "Can't not found dest type " +destTypeName+
							" for the link for " + linkTypeID+" ("+attId+") - >"+localDestTypeId+" for objecId "+objectId+" for type of object "+typeId);
					continue;
				}
				if (localDestId == -1) {
					if ("<no value>".equals(destName))
						destName = null;
					localDestId = db.getOrCreateLocalIdentifier(destId);
					db.saveObject(localDestId, ModelVersionDBService2.OBJECT_STATE_UNRESOLVED, ids(localDestTypeId), -1, destQualifiedName, destName, null);
					db.setObjectVersion(localDestId, version);
				}
				try {
					db.createLinkIfNeed(attId, objectId, localDestId);
					if (attId == PARENT_OF){
						db.saveObjectPart(objectId, localDestId);
					}
				} catch (Exception e) {
					mLogger.log(Level.SEVERE, "Can't write the link for " + linkTypeID+" ("+attId+") - >"+localDestTypeId, e);
				}
			}
			db.finishImport(objectId);
			db.commitTransaction();
			return objectId;
		} catch (Throwable e) {
			db.rollbackTransaction();
		}
		return -1;
		
	}
	
	
	

	private static int[] ids(int... typeId) {
		return typeId;
	}

	/**
	 * Read ser_7
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
	private static ItemDelta readSer_7(LogicalWorkspaceTransaction copy, 
			LoadState state, ObjectInputStream input)
			throws IOException, ClassNotFoundException, CadseException {
		// version 7
		/*
		 *  HEADER
		 * Int version
		 * UUID id item
		 * UUID id type
		 * UUID id cadse
		 * String qualified name
		 * String name
		 * int tw-version
		 * boolean header only ? true or false
		 * 
		 * bool
		 * bool
		 * 
		 * (
		 * UUID att def id (header)
		 * UUID id of type definition where att def is defined (header)
		 * Object value of this attribue
		 * ) null terminate
		 * 
		 * 
		 * link  
		 * (
		 * UUID type id (header)
		 * UUID source type id (header)
		 * UUID dest type id (header)
		 * UUID dest id (header)
				 * (
				 * UUID att def id (header)
				 * UUID id of type definition where att def is defined (header)
				 * Object value of this attribue
				 * ) null terminate
		 * ) null terminate
		 */
		// /*int version = */ input.readInt(); /* ==7 */

		UUID id = readUUID(input);
		UUID typeID = readUUID(input);
		UUID cadsetype = readUUID(input);
		String qualifiedname = readString(input);
		String name = readString(input);
		int version = input.readInt();
		boolean header = input.readBoolean();
		
		ItemType type = (ItemType) copy.findTypeDefinition(typeID, cadsetype, true);
		ItemDelta desc = copy.loadItem(id, type);
		desc.setLoaded(true);
		desc.setQualifiedName(qualifiedname, true);
		desc.setName(name, true);
		desc.setVersion(version);
		
		if (header) return desc;
		
		
		// special value
		boolean readOnly = input.readBoolean();
		boolean isValid = input.readBoolean();

		desc.setValid(isValid, true);
		desc.setReadOnly(readOnly, true);
		

		// attributs
		while (true) {
			UUID key = readUUID(input);
			if (key == null) {
				break;
			}
			
			/*(
					 * UUID att def id (header)
					 * UUID id of type definition where att def is defined (header)
					 * Object value of this attribue
					 * ) null terminate
					 * */
			
			//Source Type definition  de l'att def
			UUID attSourceType = readUUID(input);
			ItemDelta attHeader = state.loadHeader(key);
			ItemDelta attSourceHeader = state.loadHeader(attSourceType);
			
			IAttributeType<?> att = copy.findAttribute(attHeader, attSourceHeader);
			if (att != null && att.getAttributeType() != null) {
				input.addClass(att.getAttributeType());
			}
			try {
				Object value = input.readObject();
				if (value instanceof fede.workspace.domain.CompactUUID) {
					value = new UUID(((fede.workspace.domain.CompactUUID)value).getMostSignificantBits(),
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
				if (att != null)
					desc.loadAttribute(att, value);
				else
					desc.setModified(true);
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
			 * link 
			
		 * (
		 * UUID type id (header)
		 * UUID source type id (header)
		 * UUID dest id (header)
				 * (
				 * UUID att def id (header)
				 * UUID id of type definition where att def is defined (header)
				 * Object value of this attribue
				 * ) null terminate
		 * ) null terminate
		 */

			UUID linkTypeID = readUUID(input);

			if (linkTypeID == null) {
				break;
			}
			//Type source de l'att def
			UUID LTSourceType = readUUID(input);
			UUID ltDestTypeID = readUUID(input);
			UUID destId = readUUID(input);
			
			ItemDelta ltHeader = state.loadHeader(linkTypeID);
			ItemDelta ltSourceHeader = state.loadHeader(LTSourceType);
			ItemDelta destHeader = state.loadHeader(destId);
			ItemDelta ltDestTypeHeader = state.loadHeader(ltDestTypeID);
			
			LinkType linkType = copy.findLinkType(ltHeader, ltSourceHeader, ltDestTypeHeader);
			desc.loadLink(linkType, destHeader);
			
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
	public static UUID readUUID(ObjectInput input) throws IOException {
		long firstLong = input.readLong();
		if (firstLong == -1)
			return null;
		return new UUID(firstLong, input.readLong());
	}

	/**
	 * Write uuid.
	 *
	 * @param output
	 *            the output
	 * @param uuid
	 *            the uuid
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void writeUUID(ObjectOutput output, UUID uuid) throws IOException {
		if (uuid == null)
			output.writeLong(-1);
		output.writeLong(uuid.getMostSignificantBits());
		output.writeLong(uuid.getLeastSignificantBits());
	}
	
	public static void writeType(ObjectOutput output, TypeDefinition type) throws IOException {
		writeUUID(output, type.getId());
		writeUUID(output, type.getCadse().getId());
	}


	/**
	 * Write string.
	 *
	 * @param output
	 *            the output
	 * @param value
	 *            the value
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void writeString(ObjectOutput output, String value) throws IOException {
		if (value == null) {
			output.write(0);
			return;
		}
		if (value == Item.NO_VALUE_STRING) {
			output.write(2);
			return;
		}
		output.writeByte(1);
		output.writeUTF(value);
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
	 * Read model type_ item type.
	 *
	 * @param itemTypeFile
	 *            the item type file
	 *
	 * @return the string[]
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	public static String[] readModelType_ItemType(File itemTypeFile) throws Throwable {
		if (!itemTypeFile.exists()) {
			throw new IOException("Can't find the file : " + itemTypeFile.toString());
		}
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(new FileInputStream(itemTypeFile));
			String mt = input.readUTF();
			String it = input.readUTF();
			return new String[] { mt, it };
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	/**
	 * Read ser.
	 *
	 * @param mig
	 *            the mig
	 * @param itemFile
	 *            the item file
	 *
	 * @return the item description
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	public static ItemDelta readSer(LogicalWorkspace wl, LoadState loadState, File itemFile) throws Throwable {
		if (!itemFile.exists()) {
			throw new IOException("Can't find the file : " + itemFile.toString());
		}

		byte[] data = MD5.read(itemFile);
		// byte[] md5 = MD5.getMD5(data);

		return readFromByteArray(loadState.getTransaction(), loadState, data);
	}
	
//	/**
//	 * Read ser.
//	 *
//	 * @param mig
//	 *            the mig
//	 * @param itemFile
//	 *            the item file
//	 *
//	 * @return the item description
//	 *
//	 * @throws Throwable
//	 *             the throwable
//	 */
//	public static int readSer(ModelVersionDBService2 db, File itemFile) throws Throwable {
//		if (!itemFile.exists()) {
//			throw new IOException("Can't find the file : " + itemFile.toString());
//		}
//
//		byte[] data = MD5.read(itemFile);
//		// byte[] md5 = MD5.getMD5(data);
//
//		return readFromByteArray(db, data);
//	}

	/**
	 * Read from byte array.
	 *
	 * @param loadState
	 *            the loadState
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
	public static ItemDelta readFromByteArray(LogicalWorkspaceTransaction copy, LoadState loadState,
			byte[] data) throws IOException, ClassNotFoundException, CadseException {
		ObjectInputStream input = null;
		try {

			input = new ObjectInputStream(new ByteArrayInputStream(data));
			int version = input.readInt();
			ItemDelta desc = null;
			if (version == VERSION_7) {
				desc = readSer_7(copy, loadState, input);
			} else if (version == VERSION_6) {
				desc = readSer_6(copy, loadState, input);

			} else if (version == VERSION_4) {
				//desc = readSer_4(loadState, null, input);
				//desc.setModified(true);
			} else if (version == VERSION_5) {
				desc = readSer_5(copy, loadState, input);
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
	
//	public static int readFromByteArray(ModelVersionDBService2 db,
//			byte[] data) throws IOException, ClassNotFoundException, CadseException, ModelVersionDBException {
//		ObjectInputStream input = null;
//		try {
//
//			input = new ObjectInputStream(new ByteArrayInputStream(data));
//			int version = input.readInt();
//			int desc = -1;
//			if (version == VERSION_7) {
//			} else if (version == VERSION_6) {
//				desc = readSer_6(db, input);
//			} else if (version == VERSION_4) {
//			} else if (version == VERSION_5) {
//			}
//			return desc;
//		} finally {
//			if (input != null) {
//				input.close();
//			}
//		}
//	}
	
	private static void moveAttribute(ItemDescription desc, IAttributeType<?> oldKey, IAttributeType<?> newKey) {
		Object value = desc.getAttributes().remove(oldKey);
		if (value != null) {
			desc.addAttribute(newKey, value);
		}
	}

	@Override
	public ItemType getItemTypeFromDelta(ItemDelta delta) {
		// TODO Auto-generated method stub
		return null;
	}

	

	/**
	 * Save model name if need.
	 */
	public void saveModelNameIfNeed() {
		if (workspaceCU == null)
			return;
		if (workspaceCU.getLogicalWorkspace() == null)
			return;

		CadseRuntime[] cadseRuntime = workspaceCU.getLogicalWorkspace().getCadseRuntime();
		if (cadseRuntime == null) {
			return;
		}
		// No cadse loaded
		int count = 0;
		for (int i = 0; i < cadseRuntime.length; i++) {
			if (cadseRuntime[i].isExecuted()) {
				count++;
			}
		}

		// calcul tout les cadse qui sont executer, on sauvegade le nom et la
		// version
		String[] name = new String[count];
		int[] versions = new int[count];
		int jCadseExecuted = 0;
		for (int i = 0; i < cadseRuntime.length; i++) {
			if (cadseRuntime[i].isExecuted()) {
				name[jCadseExecuted] = cadseRuntime[i].getName();
				versions[jCadseExecuted] = cadseRuntime[i].getVersion();
				jCadseExecuted++;
			}
		}
		saveID = !equals(name, this.modelName) || !equals(modelVersion, versions);

		if (saveID) {
			saveID_6(name, versions);
		}
		saveID = false;
	}

	/**
	 * Equals.
	 *
	 * @param v1
	 *            the v1
	 * @param v2
	 *            the v2
	 *
	 * @return true, if successful
	 */
	private boolean equals(int[] v1, int[] v2) {
		if (v1 == null) {
			return false;
		}
		if (v2 == null) {
			return false;
		}
		if (v1.length != v2.length) {
			return false;
		}
		for (int i = 0; i < v1.length; i++) {
			if (v1[i] != v2[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Equals.
	 *
	 * @param cadseName
	 *            the cadse name
	 * @param modelName2
	 *            the model name2
	 *
	 * @return true, if successful
	 */
	private boolean equals(String[] cadseName, String[] modelName2) {
		if (cadseName == null) {
			return false;
		}
		if (modelName2 == null) {
			return false;
		}
		if (cadseName.length != modelName2.length) {
			return false;
		}
		for (int i = 0; i < modelName2.length; i++) {
			if (!cadseName[i].equals(modelName2[i])) {
				return false;
			}
		}
		return true;
	}

	
	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#load(fr.imag.adele.cadse.core.IWorkspaceLogique,
	 *      java.io.File, boolean)
	 */
	public ItemDelta[] load(LogicalWorkspace wl, File directory, boolean failthrow) throws IOException,
			CadseException {
		Map<UUID, ItemDelta> items = new HashMap<UUID, ItemDelta>();
		LoadState loadState = new LoadStateImpl(this, wl, mLogger);
		loadItemDescriptionFromRepo(loadState.getTransaction(), loadState, directory, null, items);
		Collection<ItemDelta> values = items.values();
		return (ItemDelta[]) values.toArray(new ItemDelta[values.size()]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#save(fr.imag.adele.cadse.core.Item,
	 *      java.io.File)
	 */
	public void save(LoadState loadstate, Item item, File repository) throws IOException, CadseException {
		if (!repository.exists()) {
			repository.mkdirs();
		}
		if (!repository.isDirectory()) {
			throw new IOException("The directory " + repository.getAbsolutePath() + " is not a directory");
		}
		File itemFile = new File(repository, item.getId() + ".ser");
		writeSer(loadstate, item, itemFile);
	}

	public CadseDomain getCadseDomain() {
		return this.workspaceCU;
	}

	public IPlatformIDE getPlatformEclipse() {
		return platformEclipse;
	}

	public void setLocation(File location) {
		wsLocation = location;
	}

	public File getLocation() {
		File location_melusine = new File(wsLocation, MELUSINE_DIRECTORY);
		return location_melusine;
	}

	public File getLocationSer(UUID id) {
		return fileInWorkspaceV2(id, false);
	}

	public void save(Item[] items) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		try {
			saveModelNameIfNeed();
			LoadState loadState = new LoadStateImpl(this, this.workspaceCU.getLogicalWorkspace(), mLogger);
			// pas avant le saveID sinon on ne sauve pas l'id du
			// currentworkspace...
			for (Item item : items) {
				saveInternal(loadState, item);
			}
			loadState.finishWrite();

		} finally {
		}
	}

	/**
	 * Save all.
	 *
	 * @throws CadseException
	 *             the melusine exception
	 */
	public void saveAll() {
		CadseDomain theCurrentWD = getCadseDomain();
//		if (theCurrentWD.beginOperation("WSPersistance.saveall", false) == null) {
//			return;
//		}
		try {

			LogicalWorkspace model = theCurrentWD.getLogicalWorkspace();
			if (model == null) {
				return;
			}
			saveModelNameIfNeed();
			LoadState loadState = new LoadStateImpl(this, model, mLogger);
			List<Item> items = new ArrayList<Item>(model.getItems());
			for (Item i : items) {
				if (i.isStatic()) {
					continue;
				}
				if (i instanceof ContentItem)
					continue;
				try {
//					TODO an item readonly must be saved !!!					
//					if (i.isReadOnly()) {
//						mLogger.warning(MessageFormat.format("Cannot perform save {0} : it\\'s readonly.", i
//								.getId()));
//						continue;
//					}
					saveInternal(loadState, i);

				} catch (Throwable e) {
					mLogger.log(Level.SEVERE, "Cannot perform save :" + i.getId(), e);
				}

			}
			loadState.finishWrite();

		} finally {
		//	theCurrentWD.endOperation();
		}
	}

	@Override
	public ItemDelta readItemType(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeHeaderIfNead(Item item) throws FileNotFoundException, IOException {
		if (item.isStatic() || !item.isResolved()) {
			write(itemToByteArrayHeader(item), fileInWorkspaceV2(item), true);
		}
	}

	

}
