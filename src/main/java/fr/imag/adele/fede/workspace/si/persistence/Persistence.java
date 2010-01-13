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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
import fr.imag.adele.cadse.core.enumdef.TWCommitKind;
import fr.imag.adele.cadse.core.enumdef.TWDestEvol;
import fr.imag.adele.cadse.core.enumdef.TWEvol;
import fr.imag.adele.cadse.core.enumdef.TWUpdateKind;
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
import fr.imag.adele.cadse.core.WSModelState;
import fr.imag.adele.cadse.core.attribute.IAttributeType;
import fr.imag.adele.cadse.core.transaction.delta.ItemDelta;
import fr.imag.adele.cadse.core.transaction.delta.LinkDelta;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;
import fr.imag.adele.cadse.core.ui.EPosLabel;
import fr.imag.adele.cadse.util.Assert;
import fr.imag.adele.fede.workspace.as.persistence.IPersistence;

/**
 * @generated
 */
public class Persistence implements IPersistence {


	/**
	 * @generated
	 */
	IPlatformIDE				platformIDE;

	/**
	 * The ws domain.
	 *
	 * @generated
	 *
	 */
	CadseDomain						workspaceCU;

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

	/** The Constant CURENT_VERSION. */
	private static final int		CURENT_VERSION		= VERSION_6;

	// private UUID id;

	/** The model name. */
	private String[]				modelName;

	/** The model version. */
	private int[]					modelVersion;

	/** The lock. */
	boolean							lock				= false;

	// private boolean save;
	/** The enable event. */
	private boolean					enableEvent			= true;

	/**
	 * The enable persistance. By default a the begining the persitance is false
	 * the load process set the enable to true ...
	 */
	boolean							enablePersistance	= false;

	/** The enable import update listner. */
	private boolean					enableImportUpdateListner;

	// private String workspaceTypeName;

	/** The ws location. */
	File							wsLocation;

	/** The save id. */
	private boolean					saveID;

	/** The create item persistance. */
	// public static ICreateItemPersistance createItemPersistance;
	/** The thread save. */
	static WSPersitanceService		threadSave;

	/** The m logger. */
	static Logger					mLogger				= Logger.getLogger("SI.Workspace.Persitance");
	static Logger					model_events_log	= Logger.getLogger("Model.Events");

	/**
	 * Instantiates a new wS persistance.
	 */
	public Persistence() {

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
	
	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#load(java.lang.String)
	 */
	public boolean load(final String location) {
		this.workspaceCU = workspaceCU;
		//workspaceCU.beginOperation("load");
		long start = System.currentTimeMillis();
		try {
			enablePersistance = false;

			File wsLocation = new File(location);
			File location_melusine = new File(wsLocation, Persistence.MELUSINE_DIRECTORY);

			// load the workspace information : cadse name;
			loadID2(location);

			try {
				LogicalWorkspace workspaceLogique = workspaceCU.getLogicalWorkspace();
				IMigrationFormat mig = new MigrationFormat(workspaceLogique, mLogger);
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
			enablePersistance = true;
		//	workspaceCU.endOperation();
			mLogger.finest("Load peristance in " + (System.currentTimeMillis() - start) + " ms.");


		}

		return true;
	}

	/**
	 * Save.
	 *
	 * @see fede.workspace.role.persistance.IPersistance#save()
	 */
	public void save() {
		if (!enablePersistance) {
			return;
		}

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

//	/**
//	 * Migrate.
//	 *
//	 * @param workspaceLogique
//	 *            the workspace logique
//	 * @param items
//	 *            the items
//	 *
//	 * @return true, if successful
//	 */
//	private boolean migrate(LogicalWorkspace workspaceLogique, Map<UUID, ItemDescription> items) {
//		String migfile = System.getProperty("fr.imag.adele.cadseg.migfile");
//		if (migfile != null) {
//			long start = System.currentTimeMillis();
//			MigrationModel migrationModel = new MigrationModel();
//			int count = migrationModel.run(mLogger, workspaceLogique, items, new File(migfile), getCadsesName(),
//					getCadsesVersion());
//			Collection<ItemDescription> deleteItems = migrationModel.getDeleteItems();
//			for (ItemDescription delDesc : deleteItems) {
//				delete(delDesc);
//			}
//			mLogger.finest("Migrate " + count + " actions in " + (System.currentTimeMillis() - start) + " ms.");
//			return count != 0;
//		}
//		return false;
//
//	}

	/**
	 * Delete.
	 *
	 * @param item
	 *            the item
	 */
	public void delete(ItemDescription item) {

		Persistence.mLogger.info("delete " + item.getName());

		// new version...
		delete(fileInWorkspaceV2(item.getId(), false));

		// new version...
		delete(fileInWorkspaceXMLV2(item.getId()));
		Persistence.mLogger.info("delete " + item.getName());

	}

	/**
	 * Delete.
	 *
	 * @param item
	 *            the item
	 */
	public void delete(Item item) {

		Persistence.mLogger.info("delete " + item.getName());
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
				Persistence.mLogger.log(Level.SEVERE, "Cannot delete the file " + itemFile);
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
	void loadItemDescriptionFromRepo(LogicalWorkspaceTransaction copy, IMigrationFormat mig, 
			File location_melusine, IProgressMonitor monitor,
			Map<UUID, ItemDelta> items) throws IOException {
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
	private ItemDelta loadItem(LogicalWorkspaceTransaction copy, IMigrationFormat mig, File fileItem, boolean delete) throws Throwable {
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

		saveInternal(item);
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
	void saveInternal(final Item item) throws IOException, FileNotFoundException, NoSuchAlgorithmException {
		if (item.isStatic()) {
			mLogger.log(Level.WARNING, "Cannot save item {0} : it is static", item.getId());
			return;
		}
		
		// write in byte array
		byte[] data = itemToByteArray(item);

		// write the file in repo
		File itemFile = fileInWorkspaceV2(item);
		write(data, itemFile, true);
		// write xml the file in repo
//		try {
//			writeXML(item, fileInWorkspaceXMLV2(item.getId()), true);
//		} catch (Throwable e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		// byte[] md = MD5.getMD5(data);

		try {
			// write the file in resource
			File resourceFile = fileInResource(item);
			if (resourceFile != null) {
				write(data, resourceFile, true);
			}
			// write xml the file in resource
//			File resourceFileXml = fileInResourceXml(item);
//			if (resourceFileXml != null) {
//				writeXML(item, resourceFileXml, true);
//			}
		} catch (Throwable e) {
			mLogger.log(Level.SEVERE,"", e);
		}
		// save the md5 attribut after all write.
		// try {
		// item.setAttribute(WS_PRIVATE_VERSION, md);
		// } catch (CadseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// log
		mLogger.info("SAVE ITEM " + item.getName() + " : " + item.getId());
		model_events_log.info("SAVE ITEM " + item.getQualifiedName() + " : " + item.getId());
	}



	// create a file
	

	/**
	 * Import item read.
	 *
	 * @param ws
	 *            the ws
	 * @param item
	 *            the item
	 * @param repository
	 *            the repository
	 * @param monitor
	 *            the monitor
	 * @param itemImported
	 *            the item imported
	 * @param mig
	 *            the mig
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	private void importItemRead(LogicalWorkspaceTransaction ws, Item item, File repository, IProgressMonitor monitor,
			List<Item> itemImported, IMigrationFormat mig) throws Throwable {

		File def = new File(repository, ".melusine");
		File itemfile = new File(def, item.getId().toString());
		monitor.subTask("Import " + item.getId());
		monitor.worked(1);
		if (ws.getItem(item.getId()) != null) {
			System.out.println("Allready exits " + item.getId());
			return;
		}
		ItemDelta desc = loadItem(ws, mig, itemfile, false);
		// Item parent = attachedLink == null? null:attachedLink.getSource();
		// LinkType parentLT = attachedLink == null?
		// null:attachedLink.getType();
		if (desc != null) {
			Item readedItem = ws.loadItem(desc);
			itemImported.add(readedItem);
		}

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
		IMigrationFormat mig = new MigrationFormat(ws, mLogger);
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

				ItemDescriptionRef ref = readSerHeader_4_or_5_6(mig, input);

				if (!(df.getName().equals(ref.getId().toString()))) {
					throw new CadseException("Bad id " + df.getName() + "!=" + ref.getId().toString());
				}

				// WorkspaceDomain wd = ws.getWorkspaceDomain();
				ItemType it = ws.getItemType(ref.getType());
				// if (it == null) {
				// it = wd.getItemType(type);
				// }
				if (it == null) {
					throw new CadseException("Item type " + ref.getType() + " is missing.");
				}

				Item item = ws.loadItem(ref);

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

	/** The cache directory. */
	private File	cacheDirectory	= null;

	/** The cache item. */
	private File	cacheItem		= null;

	/**
	 * Inits the.
	 */
	private void init() {
		if (cacheItem == null) {
			File data = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
			cacheItem = new File(data, ".ws-items");
			cacheDirectory = new File(data, ".ws-dir");
			cacheItem.mkdirs();
			cacheDirectory.mkdirs();
		}
	}

	/**
	 * Gets the cache item file.
	 *
	 * @param cl
	 *            the cl
	 *
	 * @return the cache item file
	 */
	private File getCacheItemFile(Link cl) {
		return getCacheItemFile(cl.getDestinationId().toString());
	}

	/**
	 * Gets the cache item file.
	 *
	 * @param cl
	 *            the cl
	 *
	 * @return the cache item file
	 */
	private File getCacheItemFile(Item cl) {
		return getCacheItemFile(cl.getId().toString());
	}

	/**
	 * Gets the cache item file.
	 *
	 * @param id
	 *            the id
	 *
	 * @return the cache item file
	 */
	private File getCacheItemFile(String id) {
		return new File(cacheItem, id + ".ser");
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// fede.workspace.role.persistance.IPersistance#restoreItem(fr.imag.adele.cadse.core.Item,
	// * fr.imag.adele.cadse.core.Link)
	// */
	// synchronized public void restoreItem(final Item item, final Link link)
	// throws Throwable {
	// init();
	// final IProgressMonitor p = Eclipse.getDefaultMonitor();
	//
	// try {
	// enablePersistance = false;
	//
	// final List<Item> itemsRead = new ArrayList<Item>();
	//
	// File itemFile = getCacheItemFile(link);
	// if (!itemFile.exists()) {
	// return;
	// }
	// IWorkspaceLogique model = workspaceCU.getWorkspaceLogique();
	// IMigrationFormat mig = new MigrationFormat(model, mLogger);
	// restoreItems(p, mig, model, item, itemFile, itemsRead);
	//
	// } catch (Exception e) {
	// throw new CadseException(e.getMessage(), e);
	// } finally {
	// enablePersistance = true;
	// }
	//
	// }



	// private void restoreResource(IProgressMonitor p, List<Item> itemsRead)
	// throws FileNotFoundException, IOException, CadseException,
	// ClassNotFoundException, CoreException {
	// for (Item i : itemsRead) {
	// if (!i.getType().hasContent()) continue;
	//
	// IResource r = eclipse.getResourceFromItem(i,false,true);
	// Job j = new ImportOrRestoreJob(i, r, new File(cacheDirectory,i.getId()),
	// false,true);
	// j.setRule(r);
	// j.schedule();
	// }
	// }

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// fede.workspace.role.persistance.IPersistance#hiddenItem(fr.imag.adele.cadse.core.Item)
	// */
	// public void hiddenItem(Item item) {
	// init();
	//
	// try {
	// File itemFile = getCacheItemFile(item);
	//
	// write(item, itemFile, true);
	//
	// if (!item.getType().hasContent()) {
	// return;
	// }
	// IResource r = eclipse.getResourceFromItem(item, false, true);
	// if (r == null) {
	// return;
	// }
	// if (!r.exists()) {
	// return;
	// }
	//
	// try {
	//
	// IProgressMonitor monitor = Eclipse.getDefaultMonitor();
	// Job.getJobManager().beginRule(r, monitor);
	//
	// SaveJob.restoreContenu(item, r, cacheDirectory, monitor);
	// } finally {
	// Job.getJobManager().endRule(r);
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * Write xml.
	 *
	 * @param item
	 *            the item
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
	private void writeXML(Item item, File itemFile, boolean deleteFileIfError) throws FileNotFoundException,
			IOException {
		if (!item.isResolved()) {
			return;
		}
		FileWriter output = null;
		try {
			output = new FileWriter(itemFile);
			writeXML_4(output, item);
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
	 * Write.
	 *
	 * @param item
	 *            the item
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
	private void write(Item item, File itemFile, boolean deleteFileIfError) throws FileNotFoundException, IOException {
		if (!item.isResolved()) {
			return;
		}
		write(itemToByteArray(item), itemFile, deleteFileIfError);
	}

	/**
	 * Byte array to item description.
	 *
	 * @param mig
	 *            the mig
	 * @param data
	 *            the data
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
	private ItemDelta byteArrayToItemDescription(LogicalWorkspaceTransaction copy, IMigrationFormat mig, byte[] data) throws IOException,
			ClassNotFoundException, CadseException {
		boolean oldLock = lock;
		try {
			lock = true;
			setEnableEvent(false);
			// byte[] md5 = MD5.getMD5(data);
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
				return new File(fileResource.getParentFile(), ".cadse-" + fileResource.getName().replace('.', '_'));
			}
		} else {
			return new File(fileResource, ".cadse.ser");
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
				return new File(fileResource.getParentFile(), ".cadse-" + fileResource.getName().replace('.', '_')
						+ ".xml");
			}
		} else {
			return new File(fileResource, ".cadse.xml");
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

	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#isEnablePersistance()
	 */
	public boolean isEnablePersistance() {
		return enablePersistance;
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
	 * @see fede.workspace.role.persistance.IPersistance#startListener()
	 */
	public void startListener() {
		enableImportUpdateListner = true;
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

	public void start() {

		Assert.isTrue(threadSave == null, "Error in persistance, thread allready started.");

		threadSave = new WSPersitanceService(this);
		threadSave.start();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fede.v6.melusine.core.InstanceObject#stop()
	 */
	public void stop() {
		this.enablePersistance = true;
		forceSave();
		this.enablePersistance = false;
		threadSave.stopService();
		threadSave = null;
	}

	/**
	 * Write xm l_4.
	 *
	 * @param output
	 *            the output
	 * @param item
	 *            the item
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static public void writeXML_4(Appendable output, Item item) throws IOException {

		output.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n").append("<item version-file=\"1.6\"");
		XMLPersistance.writeXML(output, "id", item.getId().toString());
		XMLPersistance.writeXML(output, "type", item.getType().getId().toString());
		XMLPersistance.writeXMLOpt(output, "qualified-name", item.getQualifiedName());
		XMLPersistance.writeXMLOpt(output, "name", item.getName());

		// special value
		XMLPersistance.writeXML(output, "readonly", item.isReadOnly());
		XMLPersistance.writeXML(output, "open", true);
		XMLPersistance.writeXML(output, "valid", item.isValid());
		output.append(" >\n");

		// attributs
		IAttributeType<?>[] keys = item.getLocalAllAttributeTypes();
		Arrays.sort(keys);
		for (IAttributeType<?> key : keys) {
			// if (Persistence.WS_PRIVATE_VERSION.equals(key)) {
			// continue;
			// }

			try {
				XMLPersistance.writeXML(output, key.getName(), item.getAttributeOwner(key), "attribute", "  ");
			} catch (IOException e) {
				mLogger.log(Level.SEVERE,
						"Cannot save the key : " + key + " class of value : " + item.getAttribute(key).getClass(), e);
				throw e;
			}
		}

		// outgoing link
		List<? extends Link> outgoing = item.getOutgoingLinks();
		for (Link link : outgoing) {
			if (link.isDerived()) {
				continue; // Filter derivedLink
			}
			output.append("  <link");
			XMLPersistance.writeXML(output, "type", link.getLinkType().getName());
			XMLPersistance.writeXML(output, "destination-id", link.getDestinationId().toString());
			XMLPersistance.writeXMLOpt(output, "destination-qualified-name", link.getDestinationQualifiedName());
			XMLPersistance.writeXMLOpt(output, "destination-name", link.getDestinationName());
			XMLPersistance.writeXML(output, "destination-type", link.getDestinationType().getId().toString());
			externalLinkWrite(output, link);
			output.append(" >\n");

			output.append("  </link>\n");
		}

		output.append("</item>\n");
	}

	/**
	 * External link write.
	 *
	 * @param output
	 *            the output
	 * @param link
	 *            the link
	 */
	private static void externalLinkWrite(Appendable output, Link link) {
		// TODO Auto-generated method stub

	}

	/**
	 * External derived link write.
	 *
	 * @param output
	 *            the output
	 * @param link
	 *            the link
	 */
	private static void externalDerivedLinkWrite(Appendable output, DerivedLink link) {
		// TODO Auto-generated method stub

	}

	/**
	 * External componants write.
	 *
	 * @param output
	 *            the output
	 * @param link
	 *            the link
	 */
	protected static void externalComponantsWrite(Appendable output, Item link) {
		// TODO Auto-generated method stub

	}

	/**
	 * Write ser_6.
	 *
	 * @param output
	 *            the output
	 * @param item
	 *            the item
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static public void writeSer_6(ObjectOutput output, Item item) throws IOException {
		output.writeInt(VERSION_6);
		writeUUID(output, item.getId());
		writeUUID(output, item.getType().getId());
		writeString(output, item.getQualifiedName());
		writeString(output, item.getName());

		// special value
		output.writeBoolean(item.isReadOnly());
		output.writeBoolean(true);// do not remove this line or change the
		// format (old place for isopen)
		output.writeBoolean(item.isValid());
		writeString(output, ""); // do not remove this line or change the
		// format (old place for info)

		// attributs
		IAttributeType<?>[] keys = item.getLocalAllAttributeTypes();
		Arrays.sort(keys);
		for (IAttributeType<?> key : keys) {
			{
				if (key.isTransient()) {
					continue;
				}
				if (key instanceof LinkType) {
					continue;
				}

				Object value = item.getAttributeOwner(key);
				if (value == null) {
					continue;
				}
				writeString(output, key.getId().toString());
				try {
					output.writeObject(value);
				} catch (IOException e) {
					mLogger.log(Level.SEVERE, "Cannot save item " + item.getId() + " error when write the value of the key '" + key
							+ "', class of value is " + item.getAttribute(key).getClass(), e);
					throw e;
				}
			}
		}
		writeString(output, null);

		// outgoing link
		List<? extends Link> outgoing = item.getOutgoingLinks();
		for (Link link : outgoing) {
			if (link.isDerived()) { // Filter derivedLink
				continue;
			}
			/*
			 * link type dest id dest long name dest short name dest type info
			 */
			writeString(output, link.getLinkType().getName());
			writeUUID(output, link.getDestinationId());
			writeString(output, link.getDestinationQualifiedName());
			writeString(output, link.getDestinationName());
			writeUUID(output, link.getDestinationType().getId());
			writeString(output, ""); // do not remove this line or change the
			// format (old place for info)
			output.writeInt(link.getVersion());
		}
		writeString(output, null);

//		// dervivedlink
//		Set<DerivedLink> _derivedLinks = item.getDerivedLinks();
//		for (DerivedLink link : _derivedLinks) {
//			writeString(output, link.getLinkType().getName());
//			writeUUID(output, link.getDestination().getId());
//			writeString(output, link.getDestination().getQualifiedName());
//			writeString(output, link.getDestination().getName());
//			writeUUID(output, link.getDestination().getType().getId());
//			writeString(output, null); // do not remove this line or change the
//			// format (old place for info)
//			output.writeBoolean(link.isAggregation());
//			output.writeBoolean(link.isRequire());
//			writeString(output, link.getDerivedType().getOriginLinkType().getName());
//			writeUUID(output, link.getDerivedType().getOriginLinkType().getSource().getId());
//			writeUUID(output, link.getDerivedType().getOriginLinkType().getDestination().getId());
//			output.writeInt(link.getVersion());
//		}
//		writeString(output, null);
//
//		// Components
//		Set<Item> comps = item.getComponents();
//		output.write(comps.size());
//		for (Item link : comps) {
//			writeUUID(output, link.getId());
//			writeString(output, link.getQualifiedName());
//			writeString(output, link.getName());
//			writeUUID(output, link.getType().getId());
//		}

	}

	/**
	 * UUID UUID String String B B B String { String, Object } String(null) {
	 * String, UUID, String, String, UUID, String, int } String(null).
	 *
	 * @param output
	 *            the output
	 * @param item
	 *            the item
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */

	static public void writeSer_6(ObjectOutput output, ItemDescription item) throws IOException {
		output.writeInt(VERSION_6);
		writeUUID(output, item.getId());
		writeUUID(output, item.getType());

		writeString(output, item.getQualifiedName());
		writeString(output, item.getName());

		// special value
		output.writeBoolean(item.isReadOnly());
		output.writeBoolean(true); // do not remove this line or change the
		// format (old place for 'is open')
		output.writeBoolean(item.isValid());
		writeString(output, ""); // do not remove this line or change the
		// format (old place for info)

		// attributs
		String[] keys = item.getAttributes().keySet().toArray(new String[0]);
		Arrays.sort(keys);
		for (String key : keys) {

			writeString(output, key);
			try {
				output.writeObject(item.getAttributes().get(key));
			} catch (IOException e) {
				mLogger.log(Level.SEVERE, "Cannot save item " + item.getId() + " error when write the value of the key '" + key
						+ "', class of value is " + item.getAttributes().get(key).getClass(), e);
				throw e;
			}
		}
		writeString(output, null);

		// outgoing link
		List<LinkDescription> outgoing = item.getLinks();
		for (LinkDescription link : outgoing) {
			if (link.isDerived()) {
				continue;
			}
			/*
			 * link type dest id dest long name dest short name dest type info
			 */
			writeString(output, link.getType());
			writeUUID(output, link.getDestination().getId());
			writeString(output, link.getDestination().getQualifiedName());
			writeString(output, link.getDestination().getName());
			writeUUID(output, link.getDestination().getType());
			writeString(output, ""); // do not remove this line or change the
			// format (old place for info)
			output.writeInt(link.getVersion());
		}
		writeString(output, null);

//		// dervivedlink
//		Set<DerivedLinkDescription> _derivedLinks = item.getDerived();
//		for (DerivedLinkDescription link : _derivedLinks) {
//			writeString(output, link.getType());
//			writeUUID(output, link.getDestination().getId());
//			writeString(output, link.getDestination().getQualifiedName());
//			writeString(output, link.getDestination().getName());
//			writeUUID(output, link.getDestination().getType());
//			writeString(output, ""); // do not remove this line or change the
//			// format (old place for info)
//			output.writeBoolean(link.isAggregation());
//			output.writeBoolean(link.isRequire());
//			writeString(output, link.getOriginLinkTypeID());
//			writeUUID(output, link.getOriginLinkSourceTypeID());
//			writeUUID(output, link.getOriginLinkDestinationTypeID());
//			output.write(link.getVersion());
//		}
//		writeString(output, null);
//
//		// Components
//		Set<ItemDescriptionRef> comps = item.getComponents();
//		output.write(comps.size());
//		for (ItemDescriptionRef link : comps) {
//			writeUUID(output, link.getId());
//			writeString(output, link.getQualifiedName());
//			writeString(output, link.getName());
//			writeUUID(output, link.getType());
//		}

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
	public static byte[] itemToByteArray(ItemDescription item) throws IOException {
		ObjectOutputStream output = null;
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(256);
		try {
			output = new ObjectOutputStream(byteOut);
			writeSer_6(output, item);
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
	public static byte[] itemToByteArray(Item item) throws IOException {
		ObjectOutputStream output = null;
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(256);
		try {
			output = new ObjectOutputStream(byteOut);
			Persistence.writeSer_6(output, item);
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
	public static void writeSer(ItemDescription desc, File f) throws IOException, FileNotFoundException {
		byte[] data = itemToByteArray(desc);
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
	public static void writeSer(Item item, File f) throws IOException, FileNotFoundException {
		byte[] data = itemToByteArray(item);
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
	public static ItemDescriptionRef readSerHeader_4_or_5_6(IMigrationFormat mig, ObjectInput input)
			throws IOException, ClassNotFoundException {
		// version 4

		int version = input.readInt();
		if (version == 6) {
			return readSerHeader_6(mig, input);
		}

		UUID id = (UUID) input.readObject();
		String type = (String) input.readObject();
		String longname = (String) input.readObject();
		String shortname = (String) input.readObject();
		UUID it = mig.getOrCreateITID(type);
		ItemType itObject = mig.findTypeFrom(it);
		
		ItemDescriptionRef ref = new ItemDescriptionRef(id, itObject, longname, shortname);
		return ref;
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
	public static ItemDescriptionRef readSerHeader_6(IMigrationFormat mig, ObjectInput input) throws IOException, ClassNotFoundException {
		// version 4

		/* int version = */input.readInt(); /* == 6 */

		UUID id = (UUID) input.readObject();
		UUID type = (UUID) input.readObject();
		String longname = (String) input.readObject();
		String shortname = (String) input.readObject();
		ItemType itObject = mig.findTypeFrom(type);
		
		ItemDescriptionRef ref = new ItemDescriptionRef(id, itObject, longname, shortname);
		return ref;
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
	public static ItemDelta readSer_5(LogicalWorkspaceTransaction copy, IMigrationFormat mig,
			ObjectInput input) throws IOException, ClassNotFoundException, CadseException {
		// version 5

		// /*int version = */ input.readInt(); /* == 4 */

		UUID id = (UUID) input.readObject();
		String type = (String) input.readObject();
		String longname = (String) input.readObject();
		String shortname = (String) input.readObject();
		UUID it = mig.getITID(type);
		if (it == null) {
			return null;
		}
		ItemType itObject = mig.findTypeFrom(it);
		
		// special value
		boolean readOnly = input.readBoolean();
		input.readBoolean(); // remove is open flag
		boolean isValid = input.readBoolean();
		input.readObject(); // remove info attribute

		ItemDelta desc = copy.loadItem(id, itObject);
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
				IAttributeType<?> att = mig.findAttributeFrom(itObject, key);
				if (att == null) continue;
				
				desc.loadAttribute(att, value);
			} catch (ClassNotFoundException e) {
				mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
			} catch (IOException e) {
				mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
			}
		}
		// set the md5
		//desc.loadAttribute(WS_PRIVATE_VERSION, md5);

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
			UUID destTypeID = mig.getOrCreateITID(destTypeName);
			
			
			ItemType destType = mig.findTypeFrom(destTypeID);
			if (destType == null) continue;
			
			ItemDelta destItem = copy.loadItem(destId, destTypeID);
			destItem.setQualifiedName(destLongName, true);
			destItem.setName(destShortName, true);
			
			LinkType att = mig.findlinkTypeFrom(itObject, linkType);
			if (att == null) continue;
			
			LinkDelta ldesc = desc.loadLink(att, destItem);
			ldesc.setInfo(link_info);
		}

//		// dervivedlink
//		while (true) {
//			String linkType = (String) input.readObject();
//			if (linkType == null) {
//				break;
//			}
//			UUID destId = (UUID) input.readObject();
//			String destUniqueName = (String) input.readObject();
//			String destShortName = (String) input.readObject();
//			String destTypeName = (String) input.readObject();
//			UUID destTypeID = mig.getOrCreateITID(destTypeName);
//			String link_info = (String) input.readObject();
//
//			boolean isAggregation = input.readBoolean();
//			boolean isRequire = input.readBoolean();
//			String originLinkTypeID = (String) input.readObject();
//			String originLinkSourceTypeID = (String) input.readObject();
//			String originLinkDestinationTypeID = (String) input.readObject();
//			UUID uuidOriginLinkDestinationTypeID = mig.getOrCreateITID(originLinkDestinationTypeID);
//			UUID uuidOriginLinkSourceTypeID = mig.getOrCreateITID(originLinkSourceTypeID);
//			String other = (String) input.readObject();
//			int version = 0;
//			try {
//				version = Integer.parseInt(other);
//			} catch (NumberFormatException e) {
//				version = 0;
//			}
//			ItemDescriptionRef dest = new ItemDescriptionRef(new UUID(destId), destTypeID, destUniqueName,
//					destShortName);
//			DerivedLinkDescription derivedLinkDescription = new DerivedLinkDescription(desc, linkType, dest,
//					isAggregation, isRequire, link_info, originLinkTypeID, uuidOriginLinkSourceTypeID,
//					uuidOriginLinkDestinationTypeID, version);
//			desc.addDerivedLink(derivedLinkDescription);
//		}
//		// Components
//		while (true) {
//			UUID compId = (UUID) input.readObject();
//			if (compId == null) {
//				break;
//			}
//			String compUniqueName = (String) input.readObject();
//			String compShortName = (String) input.readObject();
//			String compTypeName = (String) input.readObject();
//			UUID uuidCompTypeName = mig.getOrCreateITID(compTypeName);
//
//			desc.addComponantsLink(new ItemDescriptionRef(new UUID(compId), uuidCompTypeName, compUniqueName,
//					compShortName));
//		}

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
	public static ItemDelta readSer_6(LogicalWorkspaceTransaction copy, IMigrationFormat mig, ObjectInputStream input) throws IOException,
			ClassNotFoundException, CadseException {
		// version 6

		// /*int version = */ input.readInt(); /* == 6 */

		UUID id = readUUID(input);
		UUID type = readUUID(input);
		ItemType it = mig.findTypeFrom(type);
		String longname = readString(input);
		String shortname = readString(input);

		// special value
		boolean readOnly = input.readBoolean();
		input.readBoolean(); // remove is open flag
		boolean isValid = input.readBoolean();
		readString(input); // remove info attribute

		ItemDelta desc = copy.loadItem(id, it);
		desc.setLoaded(true);
		desc.setValid(isValid, true);
		desc.setReadOnly(readOnly, true);
		desc.setQualifiedName(longname, true);
		desc.setName(shortname, true);

		// attributs
		while (true) {
			String key = readString(input);
			if (key == null) {
				break;
			}
			IAttributeType<?> att = mig.findAttributeFrom(it, key);
			if (att != null && att.getAttributeType() != null) {
				input.addClass(att.getAttributeType());
			}
			try {
				Object value = input.readObject();
				if (att == null) continue;
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
				desc.loadAttribute(att, value);
			} catch (ClassNotFoundException e) {
				mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
			} catch (IOException e) {
				mLogger.log(Level.SEVERE, "Can't read the value for " + key, e);
			}
		}
		// set the md5
		//desc.loadAttribute(WS_PRIVATE_VERSION, md5);

		// outgoing link
		while (true) {
			/*
			 * link type dest id dest long name dest short name dest type info
			 */

			String linkType = readString(input);

			if (linkType == null) {
				break;
			}
			UUID destId = readUUID(input);
			String destQualifiedName = readString(input);

			String destName = readString(input);

			UUID destTypeName = readUUID(input);
			String link_info = readString(input);

			int version = input.readInt();
			
			ItemType destType = mig.findTypeFrom(destTypeName);
			if (destType == null) continue;
			
			LinkType att = mig.findlinkTypeFrom(it, linkType);
			if (att == null) continue;
			
			ItemDelta destItem = copy.loadItem(destId, destTypeName);
			if (!destItem.isStatic()) {
				destItem.setQualifiedName(destQualifiedName, true);
				destItem.setName(destName, true);
			}

			LinkDelta ldesc = desc.loadLink(att, destItem);
			ldesc.setInfo(link_info, true);
			ldesc.setVersion(version, true);
		}

		desc.finishLoad();

		// CadseCore.getWorkspaceDomain().endReadItemDescription(desc, input);
		return desc;
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
		return new UUID(firstLong, firstLong);
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
	public static ItemDelta readSer(LogicalWorkspaceTransaction wl, IMigrationFormat mig, File itemFile) throws Throwable {
		if (!itemFile.exists()) {
			throw new IOException("Can't find the file : " + itemFile.toString());
		}

		byte[] data = MD5.read(itemFile);
		// byte[] md5 = MD5.getMD5(data);

		return readFromByteArray(wl, mig, data);
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
	public static ItemDelta readFromByteArray(LogicalWorkspaceTransaction copy, IMigrationFormat mig, byte[] data)
			throws IOException, ClassNotFoundException, CadseException {
		ObjectInputStream input = null;
		try {

			input = new ObjectInputStream(new ByteArrayInputStream(data));
			int version = input.readInt();
			ItemDelta desc = null;
			if (version == VERSION_6) {
				desc = readSer_6(copy, mig, input);

			} else if (version == VERSION_4) {
				// desc = readSer_4(mig, md5, input);
				// desc.setModified(true);
				// desc.setRecomputeComponantsAndDerivedLink(true);
				// NOTHING
				// OLDER VERSION
				return null;
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
//	private static void moveAttribute(ItemDescription desc, String oldKey, String newKey) {
//		Object value = desc.getAttributes().remove(oldKey);
//		if (value != null) {
//			desc.addAttribute(newKey, value);
//		}
//	}

	/**
	 * Read ser h.
	 *
	 * @param mig
	 *            the mig
	 * @param itemFile
	 *            the item file
	 *
	 * @return the item description ref
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	public static ItemDescriptionRef readSerH(IMigrationFormat mig, File itemFile) throws Throwable {
		if (!itemFile.exists()) {
			throw new IOException("Can't find the file : " + itemFile.toString());
		}
		ObjectInputStream input = null;
		try {
			byte[] data = MD5.read(itemFile);
			input = new ObjectInputStream(new ByteArrayInputStream(data));
			ItemDescriptionRef item = Persistence.readSerHeader_4_or_5_6(mig, input);
			return item;
		} finally {
			if (input != null) {
				input.close();
			}
		}
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
		IMigrationFormat mig = new MigrationFormat(wl, mLogger);
		loadItemDescriptionFromRepo(null, mig, directory, null, items);
		Collection<ItemDelta> values = items.values();
		return (ItemDelta[]) values.toArray(new ItemDelta[values.size()]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fede.workspace.role.persistance.IPersistance#save(fr.imag.adele.cadse.core.Item,
	 *      java.io.File)
	 */
	public void save(Item item, File repository) throws IOException, CadseException {
		if (!repository.exists()) {
			repository.mkdirs();
		}
		if (!repository.isDirectory()) {
			throw new IOException("The directory " + repository.getAbsolutePath() + " is not a directory");
		}
		File itemFile = new File(repository, item.getId() + ".ser");
		writeSer(item, itemFile);
	}

	public CadseDomain getCadseDomain() {
		return this.workspaceCU;
	}

	public IPlatformIDE getPlatformIDE() {
		return platformIDE;
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
		if (!enablePersistance) {
			return;
		}

		try {
		//	workspaceCU.beginOperation("Persistence.save");
			saveModelNameIfNeed();
			// pas avant le saveID sinon on ne sauve pas l'id du
			// currentworkspace...
			enablePersistance = false;
			for (Item item : items) {
				saveInternal(item);
			}

		} finally {
			enablePersistance = true;
		//	workspaceCU.endOperation();
		}

	}

	/**
	 * Save all.
	 *
	 * @throws CadseException
	 *             the melusine exception
	 */
	public void saveAll() {
		if (!isEnablePersistance()) {
			return;
		}
		CadseDomain theCurrentWD = getCadseDomain();
		if (theCurrentWD == null) 
			return;
//		if (theCurrentWD.beginOperation("WSPersistance.saveall", false) == null) {
//			return;
//		}
		try {

			LogicalWorkspace model = theCurrentWD.getLogicalWorkspace();
			if (model == null) {
				return;
			}
			saveModelNameIfNeed();
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
//						Persistence.mLogger.warning(MessageFormat.format("Cannot perform save {0} : it\\'s readonly.", i
//								.getId()));
//						continue;
//					}
					saveInternal(i);

				} catch (Throwable e) {
					Persistence.mLogger.log(Level.SEVERE, "Cannot perform save :" + i.getId(), e);
				}

			}
		} finally {
		//	theCurrentWD.endOperation();
		}
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existPersistence() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CadseRuntime[] getRunningCadses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getRunningCadsesName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRunningCadsesVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item[] load(LogicalWorkspace wl, boolean failthrow)
			throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSer(Item item, File fileSer) throws CadseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item[] loadFromPersistence(LogicalWorkspace lw, List<URL> url)
			throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

}
