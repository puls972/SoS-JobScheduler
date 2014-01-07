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
package com.sos.scheduler.model.objects;

import java.io.File;
import org.apache.log4j.Logger;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.tools.PathResolver;

/**
* \class JSObjInclude 
* 
* \brief JSObjInclude - 
* 
* \details
*
* \section JSObjInclude.java_intro_sec Introduction
*
* \section JSObjInclude.java_samples Some Samples
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
* \author oh
* @version $Id: JSObjInclude.java 20718 2013-07-18 18:16:10Z kb $
* \see reference
*
* Created on 09.02.2011 14:36:27
 */

/**
 * @author oh
 *
 */
public class JSObjInclude extends Include {

	@SuppressWarnings("unused")
	private final String		conClassName	= "JSObjInclude";
	@SuppressWarnings("unused")
	private static final Logger	logger			= Logger.getLogger(JSObjInclude.class);
	
	public JSObjInclude (SchedulerObjectFactory schedulerObjectFactory) {
		super();
		objFactory = schedulerObjectFactory;
	}

	@Override
	public void setHotFolderSrc(ISOSVirtualFile pobjVirtualFile) {
		if (pobjVirtualFile != null) {
			if (getFile() != null) {	// the file attribute
				String fileName = replaceEnvVariables(getFile());
				if ( !PathResolver.isAbsolutePath(fileName) )
					throw new JobSchedulerException("only an absolute path for the attribute 'file' is allowed (path is '" + fileName + "')");
				ISOSVirtualFile vfInclude = objFactory.getFileHandleOrNull(fileName);
				super.setHotFolderSrc( vfInclude );
			} else {					// the live_file attribute
				File f = new File(pobjVirtualFile.getName());
				String baseFolder = f.getAbsolutePath().replace(f.getName(), "");
				String liveFolder = baseFolder;
				if (objFactory.getLiveFolderOrNull() != null) {
					liveFolder = objFactory.getLiveFolderOrNull().getHotFolderSrc().getName();
					String fileName = PathResolver.getAbsolutePath(liveFolder, baseFolder, getLiveFileResolved(getLiveFile()) );
					ISOSVirtualFile vfInclude = objFactory.getFileHandleOrNull(fileName);
					super.setHotFolderSrc( vfInclude );
				}
			}
		}
	}
	
	private String getLiveFileResolved(String liveFile) {
		String result = PathResolver.normalizePath(replaceEnvVariables(liveFile));
		if ( PathResolver.isAbsoluteWindowsPath(liveFile) )
			throw new JobSchedulerException("an absolute path for the attribute 'live_file' is not allowed (path is '" + result + "')");
		return result;
	}
	
	private String replaceEnvVariables(String rawName) {
		String result = rawName;
		for(String key : System.getenv().keySet()) {
			// logger.info(key + ": " + System.getenv(key));
			String searchFor = getRegExp("${" + key + "}");
			result = result.replaceAll(searchFor, getRegExp(System.getenv(key)) );
		}
		logger.info("after replacing: " + result);
		return result;
	}
	
	private String getRegExp(String rawString) {
		StringBuilder b = new StringBuilder();
		for(int i=0; i<rawString.length(); i++) {
			char ch = rawString.charAt(i);
			if ("\\.^$|?*+[]{}()".indexOf(ch) != -1)
				b.append("\\").append(ch);
			else
				b.append(ch);
		}
		return b.toString();
	}
	
	public String getContent() {
		return getHotFolderSrc().File2String();
	}

}
