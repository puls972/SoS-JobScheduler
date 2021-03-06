/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/
package com.sos.VirtualFileSystem.local;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.apache.log4j.Logger;

import sos.util.SOSFile;
import sos.util.SOSFilelistFilter;

import com.sos.JSHelper.Basics.JSJobUtilities;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.Options.SOSOptionTransferMode;
import com.sos.JSHelper.io.Files.JSFile;
import com.sos.VirtualFileSystem.DataElements.SOSFileList;
import com.sos.VirtualFileSystem.DataElements.SOSFolderName;
import com.sos.VirtualFileSystem.Interfaces.ISOSAuthenticationOptions;
import com.sos.VirtualFileSystem.Interfaces.ISOSConnection;
import com.sos.VirtualFileSystem.Interfaces.ISOSConnectionOptions;
import com.sos.VirtualFileSystem.Interfaces.ISOSSession;
import com.sos.VirtualFileSystem.Interfaces.ISOSShellOptions;
import com.sos.VirtualFileSystem.Interfaces.ISOSVFSHandler;
import com.sos.VirtualFileSystem.Interfaces.ISOSVfsFileTransfer;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFileSystem;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFolder;
import com.sos.VirtualFileSystem.Options.SOSConnection2OptionsAlternate;
import com.sos.VirtualFileSystem.Options.SOSConnection2OptionsSuperClass;
import com.sos.VirtualFileSystem.common.SOSVfsBaseClass;
import com.sos.VirtualFileSystem.shell.cmdShell;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
* \class SOSVfsLocal
*
* \brief SOSVfsLocal -
*
* \details
*
* \section SOSVfsLocal.java_intro_sec Introduction
*
* \section SOSVfsLocal.java_samples Some Samples
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author KB
* @version $Id: SOSVfsLocal.java 19908 2013-04-18 14:37:53Z oh $
* \see reference
*
* Created on 23.08.2010 17:53:03
 */

/**
 * @author KB
 *
 */
@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSVfsLocal extends SOSVfsBaseClass implements ISOSVfsFileTransfer, ISOSVFSHandler, ISOSVirtualFileSystem, ISOSConnection {

	@SuppressWarnings("unused")
	private final String		conClassName		= "SOSVfsLocal";

	private final Logger		logger				= Logger.getLogger(SOSVfsLocal.class);
	private final InputStream	objInputStream		= null;
	private final OutputStream	objOutputStream		= null;

	private String				strReplyString		= "";
	@SuppressWarnings("unused")
	private File				objWorkingDirectory	= null;

	public SOSVfsLocal() {
		//
	}

	/**
	 * \brief TransferMode
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pobjFileTransferMode
	 * @return
	 */
	@Override
	public ISOSVirtualFile TransferMode(final SOSOptionTransferMode pobjFileTransferMode) {
		return null;
	}

	/**
	 * \brief appendFile
	 *
	 * \details
	 * Appends a File, which pathname is given as a String-Parameter, to another
	 * file, with name is given as string-parameter.
	 *
	 * \return the size of the file after append-operation
	 *
	 * @param localFile
	 * @param remoteFile
	 * @return
	 */
	// TODO appendFile with ISOSVirtualFile
	@Override
	public long appendFile(final String strSourceFileName, final String strTargetFileName) {
		JSFile objTargetFile = new JSFile(strTargetFileName);
		long lngFileSize = 0;
		try {
			lngFileSize = objTargetFile.AppendFile(strSourceFileName);
		}
		catch (Exception e) {
			String strM = SOSVfs_E_134.params("appendFile()");
			logger.error(strM, e);
			throw new JobSchedulerException(strM, e);
		}
		return lngFileSize;
	}

	/**
	 * \brief ascii
	 *
	 * \details
	 *
	 * \return
	 *
	 */
	@Override
	public void ascii() {
		// nothing to do

	}

	/**
	 * \brief binary
	 *
	 * \details
	 *
	 * \return
	 *
	 */
	@Override
	public void binary() {
		// nothing to do

	}

	/**
	 * \brief changeWorkingDirectory
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pathname
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean changeWorkingDirectory(final String pstrPathName) {
		boolean flgResult = true;
		// TODO use this directory on any file-operation
		File fleFile = new File(pstrPathName);
		if (fleFile.exists()) {
			if (fleFile.isDirectory()) {
				objWorkingDirectory = new File(pstrPathName);
			}
			else {
				flgResult = false;
			}
		}
		else {
			flgResult = false;
		}
		return flgResult;
	}

	/**
	 * \brief delete
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pathname
	 * @throws IOException
	 */
	@Override
	public void delete(final String pathname) throws IOException {
		File objF = new File(getRealFileName(pathname));
		objF.delete();
	}

	private String getRealFileName(final String pstrPathname) {
		// TODO use objWorkingDirectory if it is not null to determine the Directory
		return pstrPathname;
	}

	/**
	 * \brief disconnect
	 *
	 * \details
	 *
	 * \return
	 *
	 * @throws IOException
	 */
	@Override
	public void disconnect() throws IOException {
		// nothing to do at all
	}

	/**
	 * \brief getFile
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param remoteFile
	 * @param localFile
	 * @param append
	 * @return
	 * @throws Exception
	 */
	@Override
	public long getFile(final String pstrSourceFileName, final String pstrTargetFileName, final boolean append) throws Exception {

		long lngFileSize = 0;

		if (append == false) {
			JSFile objF = new JSFile(pstrSourceFileName);
			lngFileSize = objF.length();
			objF.copy(pstrTargetFileName);
		}
		else {
			lngFileSize = this.appendFile(pstrSourceFileName, pstrTargetFileName);
		}
		return lngFileSize;
	}

	/**
	 * \brief getFile
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param remoteFile
	 * @param localFile
	 * @return
	 * @throws Exception
	 */
	@Override
	public long getFile(final String remoteFile, final String localFile) throws Exception {
		return 0;
	}

	/**
	 * \brief getHandler
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public ISOSVFSHandler getHandler() {
		return this;
	}

	/**
	 * \brief getReplyString
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public String getReplyString() {
		return strReplyString;
	}

	/**
	 * \brief isConnected
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public boolean isConnected() {
		return true;
	}

	/**
	 * \brief listNames
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pathname
	 * @return
	 * @throws IOException
	 */
	@Override
	public String[] listNames(final String pathname) throws IOException {

		File objF = new File(pathname);
		File[] objA = objF.listFiles();
		String[] strT = new String[objA.length];
		int i = 0;
		for (File file : objA) {
			strT[i++] = file.getAbsolutePath();
		}
		return strT;
	}

	/**
	 * \brief login
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param strUserName
	 * @param strPassword
	 */
	@Override
	public void login(final String strUserName, final String strPassword) {
	}

	/**
	 * \brief logout
	 *
	 * \details
	 *
	 * \return
	 *
	 * @throws IOException
	 */
	@Override
	public void logout() throws IOException {
	}

	/**
	 * \brief mkdir
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pathname
	 * @throws IOException
	 */
	@Override
	public void mkdir(final String pathname) throws IOException {
		File objF = new File(pathname);
		if (objF.exists() == false) {
			@SuppressWarnings("unused")
			boolean flgR = objF.mkdirs();
		}
		else {
			if (objF.isDirectory() == false) {
				throw new JobSchedulerException(String.format("can not create directory '%1$s', because it is a file", pathname));
			}
		}
	}

	/**
	 * \brief nList
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pathname
	 * @return
	 */
	@Override
	public Vector<String> nList(final String pathname) {
		notImplemented();
		return null;
	}

	/**
	 * \brief nList
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pathname
	 * @param flgRecurseSubFolder
	 * @return
	 */
	@Override
	public Vector<String> nList(final String pathname, final boolean flgRecurseSubFolder) {
		notImplemented();
		return null;
	}

	/**
	 * \brief nList
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param recursive
	 * @return
	 * @throws Exception
	 */
	@Override
	public Vector<String> nList(final boolean recursive) throws Exception {
		notImplemented();
		return null;
	}

	/**
	 * \brief nList
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public Vector<String> nList() throws Exception {
		notImplemented();
		return null;
	}

	/**
	 * \brief passive
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public int passive() {
		return 0;
	}

	/**
	 * \brief put
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param localFile
	 * @param remoteFile
	 */
	@Override
	public void put(final String localFile, final String remoteFile) {
	}

	/**
	 * \brief putFile
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param localFile
	 * @param out
	 * @return
	 */
	@Override
	public long putFile(final String localFile, final OutputStream out) {
		return 0;
	}

	/**
	 * \brief putFile
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param localFile
	 * @param remoteFile
	 * @return
	 * @throws Exception
	 */
	@Override
	public long putFile(final String localFile, final String remoteFile) throws Exception {
		return 0;
	}

	/**
	 * \brief getConnection
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public ISOSConnection getConnection() {
		return null;
	}

	/**
	 * \brief getSession
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public ISOSSession getSession() {
		return null;
	}

	/**
	 * \brief mkdir
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pobjFolderName
	 * @return
	 * @throws IOException
	 */
	@Override
	public ISOSVirtualFolder mkdir(final SOSFolderName pobjFolderName) throws IOException {
		new File(pobjFolderName.Value()).mkdir();
		return null;
	}

	/**
	 * \brief rmdir
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pobjFolderName
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean rmdir(final SOSFolderName pobjFolderName) throws IOException {
		new File(pobjFolderName.Value()).delete();
		return true;
	}

	/**
	 * \brief dir
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pobjFolderName
	 * @return
	 */
	@Override
	public SOSFileList dir(final SOSFolderName pobjFolderName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * \brief dir
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pathname
	 * @param flag
	 * @return
	 */
	@Override
	public SOSFileList dir(final String pathname, final int flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJSJobUtilites(final JSJobUtilities pobjJSJobUtilities) {
	}

	private cmdShell	objCmdShell	= null;

	@Override
	public void ExecuteCommand(final String strCmd) throws Exception {
		if (objCmdShell == null) {
			objCmdShell = new cmdShell();
		}
		String strT = strCmd;
		if (objCmdShell.isWindows() == true) {
			strT = strT.replaceAll("/", "\\\\");
		}
		objCmdShell.executeCommand(strT);
	}

	@Override
	public String createScriptFile(final String pstrContent) throws Exception {
		return EMPTY_STRING;
	}

	@Override
	public Integer getExitCode() {
		return 0;
	}

	@Override
	public String getExitSignal() {
		return EMPTY_STRING;
	}

	@Override
	public StringBuffer getStdErr() throws Exception {
		return new StringBuffer("");
	}

	@Override
	public StringBuffer getStdOut() throws Exception {
		return new StringBuffer("");
	}

	@Override
	public boolean remoteIsWindowsShell() {
		return false;
	}

	@Override
	public ISOSConnection Authenticate(final ISOSAuthenticationOptions pobjAO) throws Exception {
		strReplyString = "230 Login successful.";
		return this;
	}

	@Override
	public void CloseConnection() throws Exception {
		strReplyString = "ok";
	}

	@Override
	public ISOSConnection Connect() throws Exception {
		strReplyString = "ok";
		return this;
	}

	@Override
	public ISOSConnection Connect(final ISOSConnectionOptions pobjConnectionOptions) throws Exception {
		this.Connect();
		return this;
	}

	@Override
	public ISOSConnection Connect(final String pstrHostName, final int pintPortNumber) throws Exception {
		return null;
	}

	@Override
	public void CloseSession() throws Exception {
		strReplyString = "221 Goodbye.";
	}

	@Override
	public ISOSSession OpenSession(final ISOSShellOptions pobjShellOptions) throws Exception {
		return null;
	}

	@Override
	public ISOSVirtualFile getFileHandle(final String pstrFileName) {
		SOSVfsLocalFile objF = new SOSVfsLocalFile(pstrFileName);
		objF.setHandler(this);
		return objF;
	}

	@Override
	public boolean isNegativeCommandCompletion() {
		return false;
	}

	@Override
	public String[] getFilelist(final String folder, final String regexp, final int flag, final boolean pflgRecurseSubFolder) {
		String[] strS = null;
		try {
			//			Vector<File> objA = getFilelistVector(folder, regexp, flag, pflgRecurseSubFolder);
			Vector<File> objA = SOSFile.getFolderlist(folder, regexp, flag, pflgRecurseSubFolder);
			Vector<String> objV = new Vector<String>(objA.size());
			for (File objF : objA) {
				if (objF.isDirectory() == false) {
					objV.add(objF.getAbsolutePath());
				}
			}
			strS = objV.toArray(new String[objV.size()]);
			//			strS = (String[]) objA.toArray(new String[objA.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return strS;
	}

	@Override
	public String[] getFolderlist(final String folder, final String regexp, final int flag, final boolean pflgRecurseSubFolder) {
		String[] strS = null;
		try {
			//			Vector<File> objA = getFilelistVector(folder, regexp, flag, pflgRecurseSubFolder);
			Vector<File> objA = SOSFile.getFolderlist(folder, regexp, flag, pflgRecurseSubFolder);
			Vector<String> objV = new Vector<String>(objA.size());
			for (File objF : objA) {
				if (objF.isDirectory() == true) {
					objV.add(objF.getAbsolutePath());
				}
			}
			strS = objV.toArray(new String[objV.size()]);
			//			strS = (String[]) objA.toArray(new String[objA.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return strS;
	}

	private Vector<File> getFilelistVector(final String folder, final String regexp, final int RegExpFlag) throws Exception {

		Vector<File> filelist = new Vector<File>();

		if (folder == null || folder.length() == 0) {
			throw new FileNotFoundException("empty directory not allowed!!");
		}

		File f = new File(folder);
		if (!f.exists()) {
			throw new FileNotFoundException("directory does not exist: " + folder);
		}

		filelist = new Vector<File>();
		File[] files = f.listFiles(new SOSFilelistFilter(regexp, RegExpFlag));
		for (File file : files) {
			if (file.isDirectory()) {
			}
			else
				if (file.isFile()) {
					filelist.add(file);
				}
				else {
					// unknown
				}
		}

		return filelist;
	}

	/**
	 *
	 * liefert Dateiliste des eingegebenen Verzeichnis zurück.
	 * @return Vector Dateiliste
	 * @param regexp ein regul&auml;er Ausdruck
	 * @param flag ein Integer-Wert: CASE_INSENSITIVE, MULTILINE, DOTALL, UNICODE_CASE, and CANON_EQ
	 * @param withSubFolder
	 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/constant-values.html#java.util.regex.Pattern.UNIX_LINES">Constant Field Values</a>
	 */
	private Vector<File> getFilelistVector(final String folder, final String regexp, final int flag, final boolean withSubFolder) throws Exception {
		Vector<File> filelist = new Vector<File>();
		File file = null;
		File[] subDir = null;

		file = new File(folder);
		subDir = file.listFiles();
		filelist.addAll(getFilelistVector(folder, regexp, flag));
		if (withSubFolder) {
			for (File element : subDir) {
				if (element.isDirectory()) {
					filelist.addAll(getFilelistVector(element.getPath(), regexp, flag, true));
				}
			}
		}
		return filelist;
	}

	@Override
	public void CompletePendingCommand() {
		// nothing to do

	}

	@Override
	public ISOSConnection Connect(final SOSConnection2OptionsAlternate pobjConnectionOptions) throws Exception {
		// nothing to do
		return null;
	}

	@Override
	public String DoPWD() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream getAppendFileStream(final String strFileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getFileSize(final String strFileName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public InputStream getInputStream(final String strFileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModificationTime(final String strFileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream getOutputStream(final String strFileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDirectory(final String strFileName) {
		boolean flgR = new File(strFileName).isDirectory();
		// TODO Auto-generated method stub
		return flgR;
	}

	@Override
	public void rename(final String strFileName, final String pstrNewFileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeOutput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public int read(final byte[] bteBuffer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int read(final byte[] bteBuffer, final int intOffset, final int intLength) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void write(final byte[] bteBuffer, final int intOffset, final int intLength) {
		// TODO Auto-generated method stub
		notImplemented();

	}

	@Override
	public void write(final byte[] bteBuffer) {
		// TODO Auto-generated method stub
		notImplemented();

	}

	@Override
	public void openInputFile(final String pstrFileName) {
		// TODO Auto-generated method stub
		notImplemented();

	}

	@Override
	public void openOutputFile(final String pstrFileName) {
		// TODO Auto-generated method stub
		notImplemented();
	}

	@Override
	public Vector<ISOSVirtualFile> getFiles(final String string) {
		// TODO Auto-generated method stub
		notImplemented();
		return null;
	}

	@Override
	public Vector<ISOSVirtualFile> getFiles() {
		// TODO Auto-generated method stub
		notImplemented();
		return null;
	}

	@Override
	public void putFile(final ISOSVirtualFile objVirtualFile) {

		String strName = objVirtualFile.getName();
		strName = new File(strName).getAbsolutePath();
		if (strName.startsWith("c:") == true) {
			strName = strName.substring(3);
		}
		@SuppressWarnings("unused")
		ISOSVirtualFile objVF = this.getFileHandle(strName);
		OutputStream objOS = objVF.getFileOutputStream();

		InputStream objFI = objVirtualFile.getFileInputStream();

		int lngBufferSize = 1024;
		byte[] buffer = new byte[lngBufferSize];
		int intBytesTransferred;
		long totalBytes = 0;
		try {
			synchronized (this) {
				while ((intBytesTransferred = objFI.read(buffer)) != -1) {
					objOS.write(buffer, 0, intBytesTransferred);
					totalBytes += intBytesTransferred;
				}
				objFI.close();
				objOS.flush();
				objOS.close();
			}
		}
		catch (Exception e) {
			throw new JobSchedulerException(SOSVfs_E_134.params("putFile()"), e);
		}
		finally {
		}

	}

	@Override
	public void ControlEncoding(final String pstrControlEncoding) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rmdir(final String pstrFolderName) throws IOException {
		new File(pstrFolderName).delete();
	}

	@Override
	public void doPostLoginOperations() {

	}

	@Override
	public ISOSConnection Connect(final SOSConnection2OptionsSuperClass pobjConnectionOptions) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream getFileOutputStream() {
		return objOutputStream;
	}

	@Override
	public OutputStream getOutputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getInputStream() {
		return objInputStream;
	}
}
