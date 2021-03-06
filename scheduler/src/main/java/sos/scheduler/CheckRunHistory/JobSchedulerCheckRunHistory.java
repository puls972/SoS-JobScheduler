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
package sos.scheduler.CheckRunHistory;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import sos.spooler.Mail;
import sos.spooler.Spooler;

import com.sos.JSHelper.Basics.IJSCommands;
import com.sos.JSHelper.Basics.JSJobUtilities;
import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.i18n.annotation.I18NResourceBundle;
import com.sos.localization.Messages;
import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.answers.Answer;
import com.sos.scheduler.model.answers.ERROR;
import com.sos.scheduler.model.answers.HistoryEntry;
import com.sos.scheduler.model.commands.JSCmdShowHistory;
import com.sos.scheduler.model.commands.ShowHistory;
import com.sos.scheduler.model.exceptions.JSCommandErrorException;

/**
 * \class 		JobSchedulerCheckRunHistory - Workerclass for "Check the last job run"
 *
 * \brief AdapterClass of JobSchedulerCheckRunHistory for the SOSJobScheduler
 *
 * This Class JobSchedulerCheckRunHistory is the worker-class.
 *

 *
 * see \see J:\E\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JobSchedulerCheckRunHistory.xml for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\sos.scheduler.xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20110224143604 
 * \endverbatim
 */
@I18NResourceBundle(baseName = "com_sos_scheduler_messages", defaultLocale = "en")
public class JobSchedulerCheckRunHistory extends JSToolBox implements JSJobUtilities, IJSCommands {
	private final String							conSVNVersion		= "$Id: JobSchedulerCheckRunHistory.java 21033 2013-09-09 09:33:58Z oh $";
	private final String							conClassName		= "JobSchedulerCheckRunHistory";						//$NON-NLS-1$
	private static Logger							logger				= Logger.getLogger(JobSchedulerCheckRunHistory.class);
	protected JobSchedulerCheckRunHistoryOptions	objOptions			= null;
	private JSJobUtilities							objJSJobUtilities	= this;
	private IJSCommands								objJSCommands		= this;

	/**
	 * 
	 * \brief JobSchedulerCheckRunHistory
	 *
	 * \details
	 *
	 */
	public JobSchedulerCheckRunHistory() {
		super();
		Messages = new Messages("com_sos_scheduler_messages", Locale.getDefault());
	}

	/**
	 * 
	 * \brief Options - returns the JobSchedulerCheckRunHistoryOptionClass
	 * 
	 * \details
	 * The JobSchedulerCheckRunHistoryOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *  
	 * \return JobSchedulerCheckRunHistoryOptions
	 *
	 */
	public JobSchedulerCheckRunHistoryOptions Options() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$
		if (objOptions == null) {
			objOptions = new JobSchedulerCheckRunHistoryOptions();
		}
		return objOptions;
	}

	/**
	 * 
	 * \brief Options - set the JobSchedulerCheckRunHistoryOptionClass
	 * 
	 * \details
	 * The JobSchedulerCheckRunHistoryOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *  
	 * \return JobSchedulerCheckRunHistoryOptions
	 *
	 */
	public JobSchedulerCheckRunHistoryOptions Options(final JobSchedulerCheckRunHistoryOptions pobjOptions) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$
		objOptions = pobjOptions;
		return objOptions;
	}

	/**
	 * 
	 * \brief Execute - Start the Execution of JobSchedulerCheckRunHistory
	 * 
	 * \details
	 * 
	 * For more details see
	 * 
	 * \see JobSchedulerAdapterClass 
	 * \see JobSchedulerCheckRunHistoryMain
	 * 
	 * \return JobSchedulerCheckRunHistory
	 *
	 * @return
	 */
	public JobSchedulerCheckRunHistory Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute"; //$NON-NLS-1$
		logger.info(conSVNVersion);
		logger.debug(String.format(Messages.getMsg("JSJ-I-110"), conMethodName));
		try {
			Object objSp = objJSCommands.getSpoolerObject();
			boolean flgRunAsSchedulerAPIJob = objSp != null;
			
			@SuppressWarnings("unused")
			String strOperation = Options().operation.Value();
			String strJobName = Options().JobName.Value();
			String strMessage = Options().message.Value();
			String strMailTo  = Options().mail_to.Value();
			String strMailCc  = Options().mail_cc.Value();
			String strMailBcc = Options().mail_bcc.Value();
			
			strMessage = Messages.getMsg("JCH_T_0001", strJobName, myReplaceAll(strMessage,"\\[?JOB_NAME\\]?", strJobName));
			Date objDateStartTime = Options().start_time.getDateObject();
			
			if(flgRunAsSchedulerAPIJob) {
				Spooler objSpooler = (Spooler) objSp;
				Mail objMail = objSpooler.log().mail();
				if(isNotEmpty(strMailTo)) {
					objMail.set_to(strMailTo);
				}
				if(isNotEmpty(strMailCc)) {
					objMail.set_cc(strMailCc);
				}
				if(isNotEmpty(strMailBcc)) {
					objMail.set_bcc(strMailBcc);
				}
				if(isNotEmpty(strMessage)) {
					objMail.set_subject(strMessage);
				}
				if(Options().SchedulerHostName.isDirty() == false) {
					Options().SchedulerHostName.Value(objSpooler.hostname());
				}
				if(Options().scheduler_port.isDirty() == false) {
					Options().scheduler_port.value(objSpooler.tcp_port());
				}
			}
			
//			if(!flgRunAsSchedulerAPIJob) {
//				Options().SchedulerHostName.isMandatory(true);
//				Options().scheduler_port.isMandatory(true);
//			}
			
			Options().CheckMandatory();
			logger.debug(Options().toString());
			
			
			
			SchedulerObjectFactory objJSFactory = new SchedulerObjectFactory();
			objJSFactory.initMarshaller(ShowHistory.class);
			JSCmdShowHistory objShowHistory = objJSFactory.createShowHistory();
			objShowHistory.setJob(strJobName);
			objShowHistory.setPrev(BigInteger.valueOf(1));
			Answer objAnswer = null;
			
//			if(flgRunAsSchedulerAPIJob) {
//				String strShowHistoryXML = objShowHistory.toXMLString();
//				String answerXML = objJSCommands.executeXML(strShowHistoryXML);
//				objAnswer = objShowHistory.getAnswer(answerXML);
//			}
//			else {
				objJSFactory.Options().ServerName.Value(Options().SchedulerHostName.Value());
				objJSFactory.Options().PortNumber.value(Options().scheduler_port.value());
				objShowHistory.run();
				objAnswer = objShowHistory.getAnswer();
//			}
			
			if(objAnswer != null) {
				ERROR objError = objAnswer.getERROR();
				if(objError != null) {
					throw new JSCommandErrorException(objError.getText());
				}
				List<HistoryEntry> objHistoryEntries = objAnswer.getHistory().getHistoryEntry();
				if(objHistoryEntries.size() == 0) {
					logger.error(Messages.getMsg("JCH_E_0001", strJobName));
					throw new JobSchedulerException(Messages.getMsg("JCH_E_0001", strJobName));
				}
				else {
					HistoryEntry objHistoryEntry = objHistoryEntries.get(0);
					String strErrorText = objHistoryEntry.getErrorText();
					String strEndTime = objHistoryEntry.getEndTime();
					DateTimeFormatter dateTimeFormatter  =  DateTimeFormat.forPattern("yyyy-MM-dd'T'H:mm:ss.SSSZ");
					DateTime objEndDateTime = dateTimeFormatter.parseDateTime(strEndTime.replaceFirst("Z", "+00:00"));
					DateTime objStartDateTime = new DateTime(objDateStartTime);
					//Date objDateEndTime = SOSDate.getDate(strEndTime, SOSDate.dateTimeFormat);
					//boolean flgRunTooLate = objDateEndTime.before(objDateStartTime);
					boolean flgRunSuccessful = isEmpty(strErrorText);
					boolean flgRunTooLate = objEndDateTime.toLocalDateTime().isBefore(objStartDateTime.toLocalDateTime());
					
					if(flgRunTooLate || !flgRunSuccessful ) {
						//logger.info(Messages.getMsg("JCH_I_0001", strJobName, strEndTime));
						logger.info(Messages.getMsg("JCH_I_0001", strJobName, objEndDateTime.toString()));
						if(!flgRunSuccessful) {
							logger.info(Messages.getMsg("JCH_I_0002", strErrorText));
						}
						logger.error(strMessage);
						throw new JobSchedulerException(strMessage);
					}
					else {
						//logger.info(Messages.getMsg("JCH_I_0003", strJobName, strEndTime));
						logger.info(Messages.getMsg("JCH_I_0003", strJobName, objEndDateTime.toString()));
					}
				}
			}
			else {
				throw new JobSchedulerException(Messages.getMsg("JSJ_E_0140",Options().SchedulerHostName.Value(),Options().scheduler_port.value()));
			}
		}
		catch (Exception e) {
//			e.printStackTrace(System.err);
			logger.error(Messages.getMsg("JSJ-F-107", conMethodName), e);
			throw e;
		}
		return this;
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

	@Override
	public String myReplaceAll(final String pstrSourceString, final String pstrReplaceWhat, final String pstrReplaceWith) {
		String newReplacement = pstrReplaceWith.replaceAll("\\$", "\\\\\\$");
		return pstrSourceString.replaceAll("(?m)" + pstrReplaceWhat, newReplacement);
	}

	/**
	 * 
	 * \brief replaceSchedulerVars
	 * 
	 * \details
	 * Dummy-Method to make sure, that there is always a valid Instance for the JSJobUtilities.
	 * \return 
	 *
	 * @param isWindows
	 * @param pstrString2Modify
	 * @return
	 */
	@Override
	public String replaceSchedulerVars(final boolean isWindows, final String pstrString2Modify) {
		logger.debug("replaceSchedulerVars as Dummy-call executed. No Instance of JobUtilites specified.");
		return pstrString2Modify;
	}

	/**
	 * 
	 * \brief setJSParam
	 * 
	 * \details
	 * Dummy-Method to make shure, that there is always a valid Instance for the JSJobUtilities.
	 * \return 
	 *
	 * @param pstrKey
	 * @param pstrValue
	 */
	@Override
	public void setJSParam(final String pstrKey, final String pstrValue) {
	}

	@Override
	public void setJSParam(final String pstrKey, final StringBuffer pstrValue) {
	}

	/**
	 * 
	 * \brief setJSJobUtilites
	 * 
	 * \details
	 * The JobUtilities are a set of methods used by the SSH-Job or can be used be other, similar, job-
	 * implementations.
	 * 
	 * \return void
	 *
	 * @param pobjJSJobUtilities
	 */
	@Override
	public void setJSJobUtilites(final JSJobUtilities pobjJSJobUtilities) {
		if (pobjJSJobUtilities == null) {
			objJSJobUtilities = this;
		}
		else {
			objJSJobUtilities = pobjJSJobUtilities;
		}
		logger.debug("objJSJobUtilities = " + objJSJobUtilities.getClass().getName());
	}

	/**
	 * 
	 * \brief setJSCommands
	 * 
	 * \details
	 *
	 * \return void
	 *
	 * @param pobjJSCommands
	 */
	@SuppressWarnings("null")
	public void setJSCommands(final IJSCommands pobjJSCommands) {
		if (pobjJSCommands == null) {
			objJSCommands = this;
		}
		else {
			objJSCommands = pobjJSCommands;
		}
		logger.debug("pobjJSCommands = " + pobjJSCommands.getClass().getName());
	}

	@Override
	public String getCurrentNodeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSpoolerObject() {
		return null;
	}

	@Override
	public String executeXML(final String pstrJSXmlCommand) {
		return null;
	}

	@Override
	public void setStateText(final String pstrStateText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCC(final int pintCC) {
		// TODO Auto-generated method stub
		
	}


} // class JobSchedulerCheckRunHistory
