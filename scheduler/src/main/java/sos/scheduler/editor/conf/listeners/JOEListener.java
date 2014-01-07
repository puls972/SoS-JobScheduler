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
package sos.scheduler.editor.conf.listeners;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.program.Program;
import org.jdom.Element;
import org.jdom.JDOMException;

import sos.scheduler.editor.app.ErrorLog;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.ResourceManager;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;

import com.sos.JSHelper.Basics.JSToolBox;

/**
* \class JOEListener 
* 
* \brief JOEListener - 
* 
* \details
*
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
* \version $Id$
* \see reference
*
* Created on 16.11.2011 15:25:39
 */

/**
 * @author KB
 *
 */
public class JOEListener extends JSToolBox {

	@SuppressWarnings("unused")
	private final String		conClassName	= "JOEListener";
	private static final String	conSVNVersion	= "$Id$";
	private static final Logger	logger			= Logger.getLogger(JOEListener.class);

	protected SchedulerDom		_dom			= null;

	protected ISchedulerUpdate	_main			= null;
	protected Element			_job			= null;
	protected Element			_parent			= null;
	protected Element			objElement		= null;

	public final static int		NONE			= -1;

	public JOEListener() {
		//
	}

	public int getLanguage() {
		return NONE;
	}

	public String getLanguageAsString(int language) {
		return "";
	}

	public String getLanguage(int language) {
		return "";
	}

	public String getComment() {
		return "";
	}

	public String getDescription() {
		return "";
	}

	public String getSource() {
		return "";
	}

	public String getPrePostProcessingScriptSource () {
		String strT = "";
		return strT;
	}

	public void setSource (final String pstrS) {
		
	}

	public void setComment (final String pstrS) {
		
	}

	public void setDescription (final String pstrD) {
		
	}
	
	public void setLanguage(final String pstrLanguage) {
	}

	public String getJobName() {
		return "???";
	}

	public boolean isDisabled() {
		return false;
	}


	public SchedulerDom get_dom() {
 		return _dom;
	}

	public Image getImage(final String pstrImageFileName) {
		return ResourceManager.getImageFromResource("/sos/scheduler/editor/" + pstrImageFileName);
	}

	// "http://www.sos-berlin.com/doc/en/scheduler.doc/xml/job.xml"

	public void openXMLDoc(final String pstrTagName) {

		String lang = Options.getLanguage();
		String strHelpUrl = "http://www.sos-berlin.com/doc/" + lang + "/scheduler.doc/xml/" + pstrTagName + ".xml";
		openHelp(strHelpUrl);
	}

	// http://www.sos-berlin.com/doc/en/scheduler.doc/xml/job.xml#attribute_stop_on_error

	public void openXMLAttributeDoc(final String pstrTagName, final String pstrAttributeName) {

		String lang = Options.getLanguage();
		String strHelpUrl = "http://www.sos-berlin.com/doc/" + lang + "/scheduler.doc/xml/" + pstrTagName + ".xml#attribute_" + pstrAttributeName;
		openHelp(strHelpUrl);

	}

	public boolean Check4HelpKey(final int pintKeyCode, final String pstrTagName, final String pstrAttribute) {
		if (isHelpKey(pintKeyCode)) {
			openXMLAttributeDoc(pstrTagName, pstrAttribute);
			return true;
		}
		if (isGlobalHelpKey(pintKeyCode)) {
			openXMLDoc(pstrTagName);
			return true;
		}

		return false;

	}

	public boolean isHelpKey(final int pintKeyCode) {
		boolean flgRet = (pintKeyCode == SWT.F1);
		return flgRet;
	}

	public boolean isGlobalHelpKey(final int pintKeyCode) {
		boolean flgRet = (pintKeyCode == SWT.F10);
		return flgRet;
	}

	public void openHelp(String helpKey) {
		String lang = Options.getLanguage();
		String url = helpKey;
		try {
			// TODO: �berpr�fen, ob Datei wirklich existiert
			if (url.contains("http:")) {
			}
			else {
				url = new File(url).toURL().toString();
			}
			Program prog = Program.findProgram("html");
			if (prog != null)
				prog.execute(url);
			else {
				Runtime.getRuntime().exec(Options.getBrowserExec(url, lang));
			}
		}
		catch (Exception e) {
			try {
				new ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() + "; "  
						+ sos.scheduler.editor.app.Messages.getString("MainListener.cannot_open_help", new String[] { url, lang, e.getMessage() }), e);
			}
			catch (Exception ee) {
				// tu nichts
			}
			e.printStackTrace();
			MainWindow.message(sos.scheduler.editor.app.Messages.getString("MainListener.cannot_open_help", new String[] { url, lang, e.getMessage() }),
					SWT.ICON_ERROR | SWT.OK);
		}
	}

	public String getXML() {

		String strXmlText = "";
		if (objElement != null) {
			strXmlText = getXML(objElement);

			if (strXmlText != null) {

				// newXML ist null, wenn �nderungen nicht �bernommen werden sollen
				// if (newXML != null)
				// applyXMLChange(newXML);

			}
		}
		return strXmlText;
	}

	private String getXML(Element element) {

		String xml = "";
		if (element != null) {
			try {
				if (_dom instanceof SchedulerDom && ((SchedulerDom) _dom).isDirectory()) {

					xml = _dom.getXML(Utils.getHotFolderParentElement(element));
				}
				else {
					xml = _dom.getXML(element);
				}

			}
			catch (JDOMException ex) {
				try {
					new ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName(), ex);
				}
				catch (Exception ee) {
					// tu nichts
				}
				// message("Error: " + ex.getMessage(), SWT.ICON_ERROR | SWT.OK);
				return null;
			}

		}
		return xml;
	}

}
