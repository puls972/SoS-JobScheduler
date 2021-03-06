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
package com.sos.jobnet.waiter;
import com.sos.i18n.I18NBase;
import com.sos.i18n.annotation.I18NResourceBundle;
import com.sos.jobnet.classes.*;
import com.sos.jobnet.db.EventsDBItem;
import com.sos.jobnet.dispatcher.JobNetDispatcher;
import com.sos.jobnet.interfaces.*;
import com.sos.jobnet.options.JobNetOptions;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
* \class JobNetWaiter 
* 
* \brief JobNetWaiter - 
* 
* \details
*
* \section JobNetWaiter.java_intro_sec Introduction
*
* \section JobNetWaiter.java_samples Some Samples
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
* @version $Id: JobNetWaiter.java 21040 2013-09-09 16:51:14Z ss $
* \see reference
*
* Created on 24.02.2012 13:02:19
 */
/**
 * @author oh
 *
 */
@I18NResourceBundle(baseName = "com_sos_jobnet_messages", defaultLocale = "en")
public class JobNetWaiter extends I18NBase implements IJobNetCallback, IJobNetPlanEventListener {

	private final String		conClassName	= "JobNetWaiter";
	@SuppressWarnings("unused")
	private	final String		conSVNVersion	= "$Id: JobNetWaiter.java 21040 2013-09-09 16:51:14Z ss $";
	private static final Logger	logger			= Logger.getLogger(JobNetWaiter.class);
	private IWaitHandler		objWaitHandler	= null;
	private JobNetOptions		objOptions		= null;
    private final Integer taskId;
	
	private static final String	JOBNETW_I_0001		= "JOBNETW_I_0001";			// %s: Check %d of %d
	private static final String	JOBNETW_I_0002		= "JOBNETW_I_0002";			// %s: Check %d
	// private static final String	JOBNETW_I_0003		= "JOBNETW_I_0003";			// %s: %s is called because the %s is skipped
	private static final String	JOBNETW_I_0005		= "JOBNETW_I_0005";			// %s: Max. checks reached
	private static final String	JOBNETW_T_0001		= "JOBNETW_T_0001";			// Check(%d|%d): %s
	private static final String	JOBNETW_T_0002		= "JOBNETW_T_0002";			// Start check for waiting nodes
	private static final String	JOBNETW_T_0003		= "JOBNETW_T_0003";			// DISPATCHER is started, RUNNER is skipped
	private static final String	JOBNETW_T_0004		= "JOBNETW_T_0004";			// RUNNER is started
	private static final String	JOBNETW_T_0005		= "JOBNETW_T_0005";			// RUNNER is 'On Demand'!
	private static final String	JOBNETW_T_0006		= "JOBNETW_T_0006";			// Waiting for
	
	private IJobNetCallback objJNCallback = this;

	public JobNetWaiter(Integer taskId) {
		super(JobNetConstants.strBundleBaseName);
        this.taskId = taskId;
	}
	
	public void setIJobNetCallback(IJobNetCallback pobjJobNetCallback) {
		objJNCallback = pobjJobNetCallback;
	}
	
	public JobNetOptions Options() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options";
		if (objOptions == null) {
			objOptions = new JobNetOptions();
		}
		return objOptions;
	} // public JobNetOptions Options


	public void setOptions(JobNetOptions objOptions) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setOptions";
		this.objOptions = objOptions;
	} // public void setOptions

	public void setOptionsUsingHashMap(HashMap<String, String> objHshMap) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setOptionsUsingHashMap";
		try {
			Options().setAllOptions(objHshMap);
		}
		catch (Exception e) {
			throw new JobNetException(e);
		}
	} // public void setOptionsUsingHashMap

	public void doInit() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doInit";
		objWaitHandler = new JobNetDBWaiter(Options());
		objWaitHandler.setNodeStatusListener(this);
	} // public void doInit
	
	
	public boolean run() {
		final String conMethodName 	= conClassName + "::run";
		boolean ok 					= true;
		int intNoOfChecks 			= 1;
		int numberOfWaitingNodes		= 0;
		boolean bMaxChecksReached 	= false;
		boolean isRunnerOnDemand 	= false;
		IJobNetCollection jobNetCollection = new JobNetCollection();
		EventsCollection missingEvents = new EventsCollection();
		int intMaxNumberOfChecks 	= Options().max_number_of_checks.value();
		int intCheckInterval 		= Options().check_interval.getTimeAsSeconds();
		logger.debug(Messages.getMsg(JobNetConstants.JOBNET_D_0001, conMethodName, "max_number_of_checks", intMaxNumberOfChecks));
		logger.debug(Messages.getMsg(JobNetConstants.JOBNET_D_0001, conMethodName, "check_interval", intCheckInterval));
		
		try {
			objJNCallback.setStateText(Messages.getMsg(JOBNETW_T_0002));
			objWaitHandler.open();
			
			if(objWaitHandler.isJobnetStart()) {
				objJNCallback.jobnetStarted(this);
                objWaitHandler.startHistory();
			}
			
			if (objWaitHandler.isReadyForProcessing() ) {

				objWaitHandler.setNodeStatusToWaiting(taskId);
				objWaitHandler.setWaiterStatusToRunning();
                objWaitHandler.updateHistory();

				do {
					logTrial(conMethodName, intNoOfChecks, intMaxNumberOfChecks);
					isRunnerOnDemand = isRunnerOnDemand();
					missingEvents.clear();
					if (objWaitHandler.isWaiterSkipped() || objWaitHandler.isBootstrap()) {
						numberOfWaitingNodes = 0;
					} else {
						jobNetCollection 	= objWaitHandler.getWaitingNodes();
						numberOfWaitingNodes = jobNetCollection.size();
						missingEvents = objWaitHandler.getMissingEvents();
					}
					
					if (missingEvents.hasElements() || numberOfWaitingNodes > 0 || isRunnerOnDemand) {
						String stateText = getStateText(missingEvents, jobNetCollection, isRunnerOnDemand, intNoOfChecks, intMaxNumberOfChecks);
						objJNCallback.setStateText(stateText);
						if (intMaxNumberOfChecks > 0) {
							if (intMaxNumberOfChecks <= intNoOfChecks) {
								bMaxChecksReached = true;
								String msg = Messages.getMsg(JOBNETW_I_0005, conMethodName);
								objJNCallback.log(Level.INFO,msg);
								break;
							}
						}
						objWaitHandler.close();
	
						Thread.sleep(intCheckInterval*1000);
						objWaitHandler.open();
						// objWaitHandler.refresh();
	 				}
					
					String logText = getLogText(missingEvents, jobNetCollection, isRunnerOnDemand, intNoOfChecks, intMaxNumberOfChecks);
					logger.info(logText);
					objJNCallback.log(Level.INFO,logText);
					intNoOfChecks++;
	
					String msg = "Missing events: " + missingEvents.hasElements();
					logger.debug(msg);
					objJNCallback.log(Level.DEBUG,msg);
					
					msg = "Number of waiting nodes: " + numberOfWaitingNodes;
					logger.debug(msg);
					objJNCallback.log(Level.DEBUG,msg);
	
					msg = "On Demand? " + isRunnerOnDemand;
					logger.debug(msg);
					objJNCallback.log(Level.DEBUG,msg);
	
				}
				while (missingEvents.hasElements()  || numberOfWaitingNodes > 0 || isRunnerOnDemand);

				if (bMaxChecksReached) {
					ok = false;
					objWaitHandler.setWaiterStatusToError();
					objWaitHandler.setNodeStatusToError();
                    objWaitHandler.updateHistoryWithError(900, "Preconditions are not fullfilled after " + intMaxNumberOfChecks + " attempts.");
					Thread.sleep(5*1000); //Damit der letzte StateText in JOC auch zu lesen ist
				}
				else {
					if (objWaitHandler.isRunnerSkipped() || Options().restart_option.isContinueIgnore()) {
						objWaitHandler.setWaiterStatusToFinish();
						objWaitHandler.setNodeStatusToRunning();
						objJNCallback.skipRunner();
						String msg = Messages.getMsg(JOBNETW_T_0003);
						objJNCallback.setStateText(msg);
						logger.info(msg);
					}
					else {
						objWaitHandler.setWaiterStatusToFinish();
						objWaitHandler.setRunnerStatusToRunning();
						objWaitHandler.setStartTime();
						objWaitHandler.setNodeStatusToRunning();
						String msg = Messages.getMsg(JOBNETW_T_0004);
						objJNCallback.setStateText(msg);
						logger.info(msg);
					}
                    objWaitHandler.updateHistory();
				}
			} else {
				String msg = 
						"Order is not in status " + NodeStatus.STARTED.name() +
						" (or " + NodeStatus.NOT_PROCESSED.name() +
						" for bootstrap order) - Waiter will be skipped.";
				logger.error(msg);
				objJNCallback.log(Level.ERROR, msg);
				throw new JobNetException(msg);
			}
		}
		
		catch (Exception e) {
			ok = false;
			try {
				objWaitHandler.setWaiterStatusToError();
				objWaitHandler.setNodeStatusToError();
                objWaitHandler.updateHistoryWithError(901,"Error in Waiter: " + e.getMessage());
			}
			catch (Exception e1) {
				String msg = "Error setting error NodeStatus database.";
				logger.error(msg,e);
				objJNCallback.log(Level.ERROR, msg, e);
				throw new JobNetException(msg,e);
			}
			String msg = "Error processing the waiter.";
			logger.error(msg,e);
			objJNCallback.log(Level.ERROR, msg, e);
			throw new JobNetException(msg,e);
		}
		
		finally {
			objJNCallback.setStateText("Preprocessing finished - order will be processed ...");
			objWaitHandler.close();
		}
		return ok;
	} // public JobNetWaiter run

	
	public void createEvent(String eventId) {
		String uuid = Options().uuid_jobnet_identifier.Value();
		EventsDBItem eventRecord = objJNCallback.createEventRecord(uuid, eventId);
		objWaitHandler.createEvent(eventRecord);
	}
	
	private boolean isRunnerOnDemand() {
		return (!objWaitHandler.isRunnerSkipped()) ? objWaitHandler.isRunnerOnDemand() : false;
	}
	
	private void logTrial(String conMethodName, int intNoOfChecks, int intMaxNumberOfChecks) {
		String msg = (intMaxNumberOfChecks > 0) 
			? Messages.getMsg(JOBNETW_I_0001, conMethodName, intNoOfChecks, intMaxNumberOfChecks)
			: Messages.getMsg(JOBNETW_I_0002, conMethodName, intNoOfChecks);
		objJNCallback.log(Level.INFO, msg);
	}
	
	
	private String getStateText(EventsCollection missingEvents, IJobNetCollection jobNetCollection, boolean bRunnerIsOnDemand, int intNoOfChecks, int intMaxNumberOfChecks) {
		
		StringBuffer msg = new StringBuffer();
		String trials = "";

		if (bRunnerIsOnDemand) {
			msg.append(Messages.getMsg(JOBNETW_T_0005));
		}

		if (jobNetCollection.size() > 0) {
			if(msg.length() > 0)
				msg.append("; ");
			msg.append(Messages.getMsg(JOBNETW_T_0006) + " " + jobNetCollection.getItemsAsText());
		}
		
		if(missingEvents.hasElements()) {
			if(msg.length() > 0)
				msg.append("; ");
			msg.append("Events: " + missingEvents.asString());
		}
		
		if (intMaxNumberOfChecks > -1) {
			trials = String.format("(%d|%d)", intNoOfChecks, intMaxNumberOfChecks);
		}
		else {
			trials = String.format(" %d", intNoOfChecks);
		}
		
		String message = Messages.getMsg(JOBNETW_T_0001, trials, msg.toString());
		return message;
	}
	
	private String getLogText(EventsCollection missingEvents, IJobNetCollection jobNetCollection, boolean bRunnerIsOnDemand, int intNoOfChecks, int intMaxNumberOfChecks) {

		StringBuffer message = new StringBuffer();
		message.append("Trial " + intNoOfChecks);
		if(intMaxNumberOfChecks > 0)
			message.append(" of " + ((intMaxNumberOfChecks>0) ? intMaxNumberOfChecks :"unlimited"));
		message.append(": ");
		
		StringBuffer list = new StringBuffer();
		if (bRunnerIsOnDemand) { 
			if(list.length() > 0)
				list.append(", ");
			list.append("\n   ...'user intervention'" );
		}
		if(jobNetCollection.size() > 0) {
			list.append("\n   ...Predecessors: " + jobNetCollection.getItemsAsText());
		}
		if(missingEvents.hasElements()) {
			list.append("\n   ...Events......: " + missingEvents.asString());
		}
		
		if(list.length() == 0)
			message.append( "All preconditions are fulfilled." );
		else {
			message.append( "Waiting for " );
			message.append( list.toString() );
		}

		return message.toString();
	}

	@Override
	public int getExitCode() {
		// TODO Auto-generated method stub
		return 0;
	}

    @Override
    public void setNextState() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void log(Level level, String message, Throwable error) {
		logger.log(level, message, error);	}

	@Override
	public void log(Level level, String message) {
		logger.log(level, message);
	}

	@Override
	public void setStateText(String stateText) {
	}

	@Override
	public void startNode(IPlanItem planItem, JobNetOptions options) {
	}

	@Override
	public void skipRunner() {
	}

	@Override
	public void logStatus(IPlanItem record, NodeStatus oldStatus) {
        NodeStatus status = NodeStatus.valueOf(record.getStatusNode());
		logger.info("Node status of id " + record.getNodeId() +  " is set from " + oldStatus.name() + " to " + status.name());
	}

	@Override
	public void nodeStatusChanged(IPlanItem node, NodeStatus oldState) {
		objJNCallback.logStatus(node, oldState);
	}

	@Override
	public void waiterStatusChanged(NodeStatus newState, NodeStatus oldState) {
		objJNCallback.logContextStatus("WAITER", oldState, newState);
	}

	@Override
	public void runnerStatusChanged(NodeStatus newState, NodeStatus oldState) {
		objJNCallback.logContextStatus("RUNNER", oldState, newState);
	}

	@Override
	public void dispatcherStatusChanged(NodeStatus newState, NodeStatus oldState) {
		// not implemented
	}

	@Override
	public void logContextStatus(String context, NodeStatus fromStatus, NodeStatus toStatus) {
		String msg = context + " changed from  " + fromStatus.name() +  " to " + toStatus.name();
		logger.info(msg);
	}

	@Override
	public EventsDBItem createEventRecord(String forEventClass,	String forEventId) {
		// has to be implemented in the callback class.
		return null;
	}

	@Override
	public void jobnetStarted(JobNetWaiter waiter) {
		// has to be implemented in the callback class.
	}

	@Override
	public void jobnetEnded(JobNetDispatcher dispatcher) {
		// has to be implemented in the callback class.
	}

}
