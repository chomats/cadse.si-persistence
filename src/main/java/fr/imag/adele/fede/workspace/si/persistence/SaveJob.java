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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import adele.util.io.FileUtil;

import fr.imag.adele.cadse.core.Item;

/**
 * The Class SaveJob.
 *
 * @author <a href="mailto:stephane.chomat@imag.fr">Stephane Chomat</a>
 */
class SaveJob {

	/**
	 * Restore contenu.
	 *
	 * @param item
	 *            the item
	 * @param r
	 *            the r
	 * @param cacheDirectory
	 *            the cache directory
	 * @param monitor
	 *            the monitor
	 *
	 * @throws CoreException
	 *             the core exception
	 */
	static void restoreContenu(Item item, IResource r, File cacheDirectory,
			IProgressMonitor monitor) throws CoreException {
		if (r == null)
			return;
		if (!r.exists())
			return;
		File ie_dir = r.getLocation().toFile();

		if (r.getType() == IResource.PROJECT) {
			// IProject p = (IProject) r;
			// p.delete(false, true, monitor);
			deleteToAndRename(item, ie_dir, cacheDirectory);
		} else if (r.getType() == IResource.FOLDER) {
			deleteToAndRename(item, ie_dir, cacheDirectory);
			// IFolder f = (IFolder) r;
			// f.delete(true, true, monitor);
		} else if (r.getType() == IResource.FOLDER) {
			copyfile(item, r, cacheDirectory);
			// IFile f = (IFile) r;
			// f.delete(true, true, monitor);
		}

	}

	/**
	 * Copyfile.
	 *
	 * @param item
	 *            the item
	 * @param r
	 *            the r
	 * @param cacheDirectory
	 *            the cache directory
	 */
	private static void copyfile(Item item, IResource r, File cacheDirectory) {
		File ie_file = r.getLocation().toFile();
		File i_dir = new File(cacheDirectory, item.getId().toString());
		FileUtil.deleteDir(i_dir);
		ie_file.renameTo(new File(i_dir, ie_file.getName()));
	}

	/**
	 * Delete to and rename.
	 *
	 * @param item
	 *            the item
	 * @param ie_dir
	 *            the ie_dir
	 * @param cacheDirectory
	 *            the cache directory
	 */
	private static void deleteToAndRename(Item item, File ie_dir,
			File cacheDirectory) {
		File i_dir = new File(cacheDirectory, item.getId().toString());
		FileUtil.deleteDir(i_dir);
		ie_dir.renameTo(i_dir);
	}
}