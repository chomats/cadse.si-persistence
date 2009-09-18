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
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import adele.util.io.FileUtil;
import fr.imag.adele.cadse.core.Item;

/**
 * The Class ImportOrRestoreJob.
 * 
 * @author <a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a>
 */
class ImportOrRestoreJob extends Job {

	/** The item. */
	private final Item item;

	/** The item file. */
	private final File itemFile;

	/** The copy. */
	private final boolean copy;

	/** The refresh. */
	private boolean refresh;

	/** The r. */
	private IResource r;

	/**
	 * Instantiates a new import or restore job.
	 * 
	 * @param item
	 *            the item
	 * @param r
	 *            the r
	 * @param itemFile
	 *            the item file
	 * @param copy
	 *            the copy
	 * @param refresh
	 *            the refresh
	 */
	public ImportOrRestoreJob(Item item, IResource r, File itemFile,
			boolean copy, boolean refresh) {
		super("import content from " + itemFile.getAbsolutePath() + " to "
				+ item.getId());
		this.item = item;
		this.itemFile = itemFile;
		this.copy = copy;
		this.refresh = refresh;
		this.r = r;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public IStatus run(IProgressMonitor monitor) {

		try {
			restore(monitor);
		} catch (CoreException e) {
			return e.getStatus();
		}
		if (refresh)
			item.refresh();
		return Status.OK_STATUS;
	}

	/**
	 * Restore.
	 * 
	 * @param monitor
	 *            the monitor
	 * 
	 * @throws CoreException
	 *             the core exception
	 */
	public void restore(IProgressMonitor monitor) throws CoreException {
		if (!itemFile.exists() || r == null)
			return;

		File e_dir = r.getLocation().toFile();
		restore(monitor, itemFile, e_dir, copy);
	}

	/**
	 * Restore.
	 * 
	 * @param monitor
	 *            the monitor
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @param copy
	 *            the copy
	 */
	static void restore(IProgressMonitor monitor, File source,
			File destination, boolean copy) {
		File[] files = source.listFiles();
		monitor.beginTask("Restore file :", files.length);
		for (File sourceFile : source.listFiles()) {
			monitor.worked(1);
			monitor.subTask(sourceFile.getName());
			File destinationFile = new File(destination, sourceFile.getName());
			if (destinationFile.exists()) {
				if (sourceFile.isDirectory() && destinationFile.isDirectory())
					// add restored content to existing directory
					restore(monitor, sourceFile, destinationFile, copy);
				else {
					// replace existing file by restored file
					destinationFile.delete();
					if (copy) {
						try {
							monitor.subTask("Copy file : " + source.getName());
							FileUtil.copy(sourceFile, destinationFile, true);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						sourceFile.renameTo(destinationFile);
					}
				}
			} else {
				destinationFile.delete();
				if (copy) {
					try {
						FileUtil.copy(sourceFile, destinationFile, true);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					sourceFile.renameTo(destinationFile);
				}
			}
		}
		if (!copy) {
			source.delete();
		}
	}

}