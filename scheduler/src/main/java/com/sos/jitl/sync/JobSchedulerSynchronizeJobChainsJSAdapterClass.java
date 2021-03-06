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
package com.sos.jitl.sync;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import sos.scheduler.job.JobSchedulerJobAdapter;
import sos.spooler.Job_chain;
import sos.spooler.Job_chain_node;
import sos.spooler.Order;
import sos.spooler.Spooler;
import sos.spooler.Variable_set;

import com.sos.JSHelper.Basics.IJSCommands;
import com.sos.JSHelper.Exceptions.JobSchedulerException;

// Super-Class for JobScheduler Java-API-Jobs

/**
 * \class 		JobSchedulerSynchronizeJobChainsJSAdapterClass - JobScheduler Adapter for "Synchronize Job Chains"
 *
 * \brief AdapterClass of JobSchedulerSynchronizeJobChains for the SOSJobScheduler
 *
 * This Class JobSchedulerSynchronizeJobChainsJSAdapterClass works as an adapter-class between the SOS
 * JobScheduler and the worker-class JobSchedulerSynchronizeJobChains.
 *

 *
 *
 * \verbatim ;
 * mechanicaly created by C:\ProgramData\sos-berlin.com\jobscheduler\scheduler_ur\config\JOETemplates\java\xsl\JSJobDoc2JSAdapterClass.xsl from http://www.sos-berlin.com at 20121217120436
 * \endverbatim
 */
public class JobSchedulerSynchronizeJobChainsJSAdapterClass extends JobSchedulerJobAdapter {
	private static final String	conParameterSCHEDULER_SYNC_READY	= "scheduler_sync_ready";
	private static final String	COMMAND_SHOW_JOB					= "<show_job job=\"%s\" max_task_history=\"0\" what=\"job_orders job_chains payload\"/>";
	private static final String	COMMAND_SHOW_JOB_CHAIN_FOLDERS		= "<show_state max_order_history=\"0\" max_orders=\"0\" what=\"job_chains folders\" subsystems=\"folder order\"/>";
	private final String		conClassName						= "JobSchedulerSynchronizeJobChainsJSAdapterClass";																//$NON-NLS-1$
	@SuppressWarnings("hiding")
	private static Logger		logger								= Logger.getLogger(JobSchedulerSynchronizeJobChainsJSAdapterClass.class);

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

	@Override
	public boolean spooler_init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_init"; //$NON-NLS-1$
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_process"; //$NON-NLS-1$

		try {
			super.spooler_process();

			//Ab hier wegen http://www.sos-berlin.com/jira/browse/JS-461
			boolean syncReady = false;
			if (spooler_task.order().params().value(conParameterSCHEDULER_SYNC_READY) != null) {
				syncReady = spooler_task.order().params().value(conParameterSCHEDULER_SYNC_READY).equals("true");
			}
			if (syncReady) {
				spooler_log.info("js-461: Sync skipped");
				Order o = spooler_task.order();
				Variable_set resultParameters = spooler.create_variable_set();
				String[] parameterNames = o.params().names().split(";");
				for (int i = 0; i < parameterNames.length; i++) {
					if (!parameterNames[i].equals(conParameterSCHEDULER_SYNC_READY)) {
						resultParameters.set_var(parameterNames[i], o.params().value(parameterNames[i]));
					}
				}
				o.set_params(resultParameters);
				return signalSuccess();
			}
			//js-461 Ende

			doProcessing();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			throw new JobSchedulerException("--- Fatal Error: " + e.getLocalizedMessage(), e);
		}
		finally {
		} // finally

		return signalSuccess();

	} // spooler_process

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_exit"; //$NON-NLS-1$
		super.spooler_exit();
	}

	private void doProcessing___inactive() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doProcessing";

		JobSchedulerSynchronizeJobChains objR = new JobSchedulerSynchronizeJobChains();
		JobSchedulerSynchronizeJobChainsOptions objO = objR.Options();
		objR.setJSJobUtilites(this);
		objO.CurrentNodeName(this.getCurrentNodeName());

		SchedulerParameters = getSchedulerParameterAsProperties(getJobOrOrderParameters());

		objO.setAllOptions(SchedulerParameters);
		objO.CheckMandatory();

		String jobName = spooler_task.job().name();
		objO.jobpath.Value(jobName);
		objR.setJSJobUtilites(this);

		String answer = spooler.execute_xml(COMMAND_SHOW_JOB_CHAIN_FOLDERS);
		// logger.debug(answer);
		objO.jobchains_answer.Value(answer);
		answer = spooler.execute_xml(String.format(COMMAND_SHOW_JOB, jobName));
		// logger.debug(answer);
		objO.orders_answer.Value(answer);

		IJSCommands objJSCommands = this;
		Object objSp = objJSCommands.getSpoolerObject();
		Spooler objSpooler = (Spooler) objSp;

		objO.jobpath.Value("/" + spooler_task.job().name());

		for (final Map.Entry <String,String> element : SchedulerParameters.entrySet()) {
			final String strMapKey = element.getKey().toString();
			String strTemp = "";
			if (element.getValue() != null) {
				strTemp = element.getValue().toString();
				if (strMapKey.contains("password")) {
					strTemp = "***";
				}
			}
			logger.info("Key = " + strMapKey + " --> " + strTemp);
		}

		objR.setSchedulerParameters(SchedulerParameters);

		objR.Execute();

		if (objR.syncNodeContainer.isReleased()) {
			while (!objR.syncNodeContainer.eof()) {
				SyncNode objSyncNode = objR.syncNodeContainer.getNextSyncNode();
				Job_chain objJobChain = objSpooler.job_chain(objSyncNode.getSyncNodeJobchainPath());
				Job_chain_node objCurrentNode = objJobChain.node(objSyncNode.getSyncNodeState());

				/**
				 * get last node of chain. unfortunately there is no method available. Therefore we have to make a hack
				 * it is not really the last node, it is the node without next_state
				 */
				// TODO move to base class: getLastJobChainNode
				String strLastNodeName = "";
				try {
					Job_chain_node objJCEnd = objJobChain.node("JobChainEnd");
					if (objJCEnd != null) {
						strLastNodeName = objJCEnd.state();
					}
				}
				catch (Exception e) {
//					strLastNodeName = "JobChainEnd";  // just for testing, not serious
				}

				if (strLastNodeName.length() <= 0) {
					Job_chain_node objJCN = objCurrentNode.next_node();
					while (objJCN != null) {
						strLastNodeName = objJCN.state();
						if (strLastNodeName.equalsIgnoreCase("JobChainEnd")) {
							break;
						}
						objJCN = objJCN.next_node();
					}
				}

				List<SyncNodeWaitingOrder> lstWaitingOrders = objSyncNode.getSyncNodeWaitingOrderList();
				for (SyncNodeWaitingOrder objWaitingOrder : lstWaitingOrders) {
					String strEndState = objWaitingOrder.getEndState();
					logger.debug(String.format("Release jobchain=%s order=%s at state %s, endstate=%s", objSyncNode.getSyncNodeJobchainPath(),
							objWaitingOrder.getId(), objSyncNode.getSyncNodeState(), strEndState));

					Job_chain_node next_n = objCurrentNode.next_node();

					String next_state = objCurrentNode.next_state();
					if (objCurrentNode.state().equalsIgnoreCase(strEndState)) {
						next_state = strLastNodeName; // double execution?
					}
					if (strEndState.length() > 0) {
						strEndState = " end_state='" + strEndState + "' ";
					}
					// TODO Why not using the Internal API?
					// TODO Why repeated code?
					String strJSCommand = "";
					if (next_n.job() == null) { //siehe http://www.sos-berlin.com/jira/browse/JS-461
						strJSCommand = "<modify_order job_chain='" + objSyncNode.getSyncNodeJobchainPath() + "' order='" + objWaitingOrder.getId()
								+ "' suspended='no'" + strEndState + ">"
								+ "<params><param name='scheduler_sync_ready' value='true'></param></params>" +
								"</modify_order>";
					}
					else {
						strJSCommand = "<modify_order job_chain='" + objSyncNode.getSyncNodeJobchainPath() + "' order='" + objWaitingOrder.getId()
								+ "' state='" + next_state + "' suspended='no'" + strEndState + "/>";
					}
					logger.debug(strJSCommand);
					answer = objSpooler.execute_xml(strJSCommand);
					logger.debug(answer);
				}
			}
		}
		else {
			if (!spooler_task.order().suspended()) {
				spooler_task.order().set_state(spooler_task.order().state()); //Damit der Suspend auf den sync-Knoten geht und nicht auf den n�chsten.
				spooler_task.order().set_suspended(true);
			}
		}

	} // doProcessing

	private void doProcessing() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doProcessing";

		JobSchedulerSynchronizeJobChains objR = new JobSchedulerSynchronizeJobChains();
		JobSchedulerSynchronizeJobChainsOptions objO = objR.Options();
		objR.setJSJobUtilites(this);
		objO.CurrentNodeName(this.getCurrentNodeName());

		SchedulerParameters = getSchedulerParameterAsProperties(getJobOrOrderParameters());

		objO.setAllOptions(SchedulerParameters);
		objO.CheckMandatory();

		String jobName = spooler_task.job().name();
		objO.jobpath.Value(jobName);
		objR.setJSJobUtilites(this);

		String answer = spooler.execute_xml(COMMAND_SHOW_JOB_CHAIN_FOLDERS);
		// logger.debug(answer);
		objO.jobchains_answer.Value(answer);
		answer = spooler.execute_xml(String.format(COMMAND_SHOW_JOB, jobName));
		// logger.debug(answer);
		objO.orders_answer.Value(answer);

		IJSCommands objJSCommands = this;
		Object objSp = objJSCommands.getSpoolerObject();
		Spooler objSpooler = (Spooler) objSp;

		objO.jobpath.Value("/" + spooler_task.job().name());

		for (final Map.Entry <String,String> element : SchedulerParameters.entrySet()) {
			final String strMapKey = element.getKey().toString();
			String strTemp = "";
			if (element.getValue() != null) {
				strTemp = element.getValue().toString();
				if (strMapKey.contains("password")) {
					strTemp = "***";
				}
			}
			logger.info("Key = " + strMapKey + " --> " + strTemp);
		}

		objR.setSchedulerParameters(SchedulerParameters);

		objR.Execute();

		if (objR.syncNodeContainer.isReleased()) {
			while (!objR.syncNodeContainer.eof()) {
				SyncNode objSyncNode = objR.syncNodeContainer.getNextSyncNode();
				Job_chain objJobChain = objSpooler.job_chain(objSyncNode.getSyncNodeJobchainPath());
				Job_chain_node objCurrentNode = objJobChain.node(objSyncNode.getSyncNodeState());

				 

				List<SyncNodeWaitingOrder> lstWaitingOrders = objSyncNode.getSyncNodeWaitingOrderList();
				for (SyncNodeWaitingOrder objWaitingOrder : lstWaitingOrders) {
					String strEndState = objWaitingOrder.getEndState();
					logger.debug(String.format("Release jobchain=%s order=%s at state %s, endstate=%s", objSyncNode.getSyncNodeJobchainPath(),
							objWaitingOrder.getId(), objSyncNode.getSyncNodeState(), strEndState));

					Job_chain_node next_n = objCurrentNode.next_node();

					//Den Fall behandeln, dass der Syncknoten gleich dem Endknoten des Auftrages ist
					//Wenn das der Fall ist, wird der Job nochmal ausgef�hrt, damit der Auftrag im Sync-Knoten verbleibt.
					//Es wird in dem Fall scheduler_sync_ready=true gesetzt. Dann liefert der Job true und der Auftrag ist beendent.
					String next_state = objCurrentNode.next_state();
					if (objCurrentNode.state().equalsIgnoreCase(strEndState)) {
 						next_state = objCurrentNode.state();
					}

					//TODO
					/*
					 * if (order wurde vom Splitter erzeugt??? Oder wann soll strLastNodeName verwendet werden){
					 *    next_state = strLastNodeName;
					 *    }
					 *
					 */


					if (strEndState.length() > 0) {
					  strEndState = " end_state='" + strEndState + "' ";
					}
					// TODO Why not using the Internal API?
					// Antwort: Weil das nicht geht. http://www.sos-berlin.com/jira/browse/JS-578
					// TODO Why repeated code?
					String strJSCommand = "";
					if (next_n.job() == null || next_state.equals(objCurrentNode.state())) { //siehe http://www.sos-berlin.com/jira/browse/JS-461
						strJSCommand = "<modify_order job_chain='" + objSyncNode.getSyncNodeJobchainPath() + "' order='" + objWaitingOrder.getId()
								+ "' suspended='no'" + strEndState + ">"
								+ "<params><param name='scheduler_sync_ready' value='true'></param></params>" +
								"</modify_order>";
					}
					else {
						strJSCommand = "<modify_order job_chain='" + objSyncNode.getSyncNodeJobchainPath() + "' order='" + objWaitingOrder.getId()
								+ "' state='" + next_state + "' suspended='no'" + strEndState + "/>";
					}
					logger.debug(strJSCommand);
					answer = objSpooler.execute_xml(strJSCommand);
					logger.debug(answer);
				}
			}
		}
		else {
			if (!spooler_task.order().suspended()) {
				spooler_task.order().set_state(spooler_task.order().state()); //Damit der Suspend auf den sync-Knoten geht und nicht auf den n�chsten.
				spooler_task.order().set_suspended(true);
			}
		}

	} // doProcessing

}
