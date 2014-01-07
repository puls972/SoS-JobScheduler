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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.scheduler.model.SchedulerObjectFactory;

/**
* \class JSObjSpooler 
* 
* \brief JSObjSpooler - 
* 
* \details
*
* \section JSObjSpooler.java_intro_sec Introduction
*
* \section JSObjSpooler.java_samples Some Samples
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
* @version $Id: JSObjSpooler.java 20718 2013-07-18 18:16:10Z kb $
* \see reference
*
* Created on 09.02.2011 15:21:07
 */

/**
 * @author oh
 *
 */
public class JSObjSpooler extends Spooler {

	private final String		conClassName	= "JSObjSpooler";
	@SuppressWarnings("unused")
	private static final Logger	logger			= Logger.getLogger(JSObjSpooler.class);

	public JSObjSpooler(final SchedulerObjectFactory schedulerObjectFactory) {
		super();
		objFactory = schedulerObjectFactory;
	}
	
	public JSObjSpooler(final SchedulerObjectFactory schedulerObjectFactory, final ISOSVirtualFile pobjVirtualFile) {
		super();
		objFactory = schedulerObjectFactory;
        final Spooler objSpooler = (Spooler) unMarshal(pobjVirtualFile);
        setObjectFieldsFrom(objSpooler);
        setHotFolderSrc(pobjVirtualFile);
        afterUnmarshal();
    }
	
	private void afterUnmarshal() {
        @SuppressWarnings("unused")
        final String conMethodName = conClassName + "::afterUnmarchal";
        Config objConfig = getConfig().get(0);
        if (objConfig.getSchedulerScript() != null) {
        	for (SchedulerScript objSchedulerScript : objConfig.getSchedulerScript()) {
        		if (objSchedulerScript.getScript() != null) {
                    removeEmptyContentsFrom(objSchedulerScript.getScript().getContent());
                }
            }
        }
        if (objConfig.getScript() != null) {
            removeEmptyContentsFrom(objConfig.getScript().getContent());
        }
    } // public afterUnmarchal
	
	/**
     * 
     * \brief removeEmptyContentsFrom
     * 
     * \details 
     * Some objects contain cdata as well as other objects, so that 
     * unmarshalling creates for every spaces around the other objects 
     * empty cdata nodes. These empty cdata nodes are removed.  
     *
     * \return void
     *
     * @param objList
     */
    private void removeEmptyContentsFrom(final List<Object> objList) {
        @SuppressWarnings("unused")
        final String conMethodName = conClassName + "::removeEmptyContentsFrom";
        final List<String> emptyContents = new ArrayList<String>();
        for (final Object listItem : objList) {
            if (listItem instanceof String && ((String) listItem).trim().length() == 0) {
                emptyContents.add(((String) listItem));
            }
        }
        objList.removeAll(emptyContents);
    } // private removeEmptyContentsFrom
}
