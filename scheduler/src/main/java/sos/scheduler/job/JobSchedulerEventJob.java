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
package sos.scheduler.job;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xalan.xslt.EnvironmentCheck;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import sos.connection.SOSConnection;
import sos.connection.SOSPgSQLConnection;
import sos.scheduler.command.SOSSchedulerCommand;
import sos.scheduler.consoleviews.events.SOSActions;
import sos.scheduler.consoleviews.events.SOSEvaluateEvents;
import sos.scheduler.misc.ParameterSubstitutor;
import sos.spooler.Spooler;
import sos.spooler.Variable_set;
import sos.util.SOSDate;
import sos.util.SOSFile;
import sos.util.SOSLogger;
import sos.xml.SOSXMLTransformer;
import sos.xml.SOSXMLXPath;

import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
 *
 * @author andreas.pueschel@sos-berlin.com
 *
 *    This job is used to process events by a EventService instance
 *
 */
@SuppressWarnings("deprecation")
public class JobSchedulerEventJob extends JobSchedulerJob {
	/** event action */
	private String				eventAction							= "";
	/** event JobScheduler id */
	private String				eventSchedulerId					= "";
	/** event remote JobScheduler host */
	private String				eventRemoteSchedulerHost			= "";
	/** event remote JobScheduler port */
	private String				eventRemoteSchedulerPort			= "";
	/** job chain that caused the event */
	private String				eventJobChainName					= "";
	/** order identification that caused the event */
	private String				eventOrderId						= "";
	/** job that caused the event */
	private String				eventJobName						= "";
	/** event class name */
	private String				eventClass							= "";
	/** event identification */
	private String				eventId								= "";
	/** event exit code */
	private String				eventExitCode						= "";
	/** event expiration date */
	private String				eventExpires						= "";
	/** event creation date */
	private String				eventCreated						= "";
	/** event parameters */
	private Element				eventParameters						= null;
	/** expiration period */
	private String				expirationPeriod					= "";
	/** expiration cycle */
	private String				expirationCycle						= "";
	/** calculated expiration date */
	private Calendar			expirationDate						= null;
	/** event handler file path */
	private String				eventHandlerFilepath				= "";
	/** event handler file specification */
	private String				eventHandlerFilespec				= "";
	/** job and order parameters */
	private Variable_set		parameters							= null;
	/** table name */
	private static String		tableEvents							= "SCHEDULER_EVENTS";
	/** local DOM objects of events */
	private Document			events								= null;
	/** local list of event handlers */
	private Collection<File>	eventHandlerFileList				= new LinkedHashSet<File>();
	/** iterator for local list of event handlers */
	private Iterator<File>		eventHandlerFileListIterator		= null;
	/** local list of transformed files */
	private Collection<Object>	eventHandlerResultFileList			= new Vector<Object>();
	/** iterator for local list of transformed files */
	private Iterator<Object>	eventHandlerResultFileListIterator	= null;
	/** Timeout for Socket communication default=5 **/
	private int					socket_timeout						= 5;
	private ParameterSubstitutor parameterSubstitutor;

	@Override
	public boolean spooler_init() {
		boolean rc = super.spooler_init();
		EnvironmentCheck ec = new EnvironmentCheck();
		StringWriter sWri = new StringWriter();
		PrintWriter pWri = new PrintWriter(sWri);
		ec.checkEnvironment(pWri);
		pWri.close();
		try {
			getLogger().debug3("Checking Xalan environment...");
			getLogger().debug3(sWri.toString());
		}
		catch (Exception ex) {
		}
		return rc;
	}

	@Override
	public boolean spooler_process() {
		boolean rc = true;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document eventDocument = docBuilder.newDocument();
			eventDocument.appendChild(eventDocument.createElement("events"));
			this.setEvents(eventDocument);
			// fetch events from global JobScheduler variable
			this.getSchedulerEvents();
			this.setParameters(spooler.create_variable_set());
			HashSet<String> jobParameterNames = new HashSet<String>();
			try {
				if (spooler_task.params() != null)
					this.getParameters().merge(spooler_task.params());
				if (spooler_job.order_queue() != null)
					this.getParameters().merge(spooler_task.order().params());
				/* event processing parameters */
				if (this.getParameters().value("socket_timeout") != null && this.getParameters().value("socket_timeout").length() > 0) {
					this.setSocket_timeout(this.getParameters().value("socket_timeout"));
					spooler_log.debug1(".. parameter [socket_timeout]: " + socket_timeout);
				}
				else {
					this.setSocket_timeout("5");
				}
				if (this.getParameters().value("action") != null && this.getParameters().value("action").length() > 0) {
					this.setEventAction(this.getParameters().value("action"));
					spooler_log.debug1(".. parameter [action]: " + this.getEventAction());
				}
				else {
					this.setEventAction("add");
				}
				jobParameterNames.add("action");
				if (this.getParameters().value("scheduler_id") != null && this.getParameters().value("scheduler_id").length() > 0) {
					this.setEventSchedulerId(this.getParameters().value("scheduler_id"));
					spooler_log.debug1(".. parameter [scheduler_id]: " + this.getEventSchedulerId());
				}
				else {
					this.setEventSchedulerId(spooler.id());
				}
				jobParameterNames.add("scheduler_id");
				if (this.getParameters().value("spooler_id") != null && this.getParameters().value("spooler_id").length() > 0) {
					this.setEventSchedulerId(this.getParameters().value("spooler_id"));
					spooler_log.debug1(".. parameter [spooler_id]: " + this.getEventSchedulerId());
				}
				jobParameterNames.add("spooler_id");
				/* standard parameters */
				if (this.getParameters().value("remote_scheduler_host") != null && this.getParameters().value("remote_scheduler_host").length() > 0) {
					this.setEventRemoteSchedulerHost(this.getParameters().value("remote_scheduler_host"));
					spooler_log.debug1(".. parameter [remote_scheduler_host]: " + this.getEventRemoteSchedulerHost());
				}
				else {
					this.setEventRemoteSchedulerHost("");
				}
				jobParameterNames.add("remote_scheduler_host");
				if (this.getParameters().value("remote_scheduler_port") != null && this.getParameters().value("remote_scheduler_port").length() > 0) {
					this.setEventRemoteSchedulerPort(this.getParameters().value("remote_scheduler_port"));
					spooler_log.debug1(".. parameter [remote_scheduler_port]: " + this.getEventRemoteSchedulerPort());
				}
				else {
					this.setEventRemoteSchedulerPort("0");
				}
				jobParameterNames.add("remote_scheduler_port");
				if (this.getParameters().value("job_chain") != null && this.getParameters().value("job_chain").length() > 0) {
					this.setEventJobChainName(this.getParameters().value("job_chain"));
					spooler_log.debug1(".. parameter [job_chain]: " + this.getEventJobChainName());
				}
				else {
					this.setEventJobChainName("");
				}
				jobParameterNames.add("job_chain");
				if (this.getParameters().value("order_id") != null && this.getParameters().value("order_id").length() > 0) {
					this.setEventOrderId(this.getParameters().value("order_id"));
					spooler_log.debug1(".. parameter [order_id]: " + this.getEventOrderId());
				}
				else {
					this.setEventOrderId("");
				}
				jobParameterNames.add("order_id");
				if (this.getParameters().value("job_name") != null && this.getParameters().value("job_name").length() > 0) {
					this.setEventJobName(this.getParameters().value("job_name"));
					spooler_log.debug1(".. parameter [job_name]: " + this.getEventJobName());
				}
				else {
					this.setEventJobName("");
				}
				jobParameterNames.add("job_name");
				if (this.getParameters().value("event_class") != null && this.getParameters().value("event_class").length() > 0) {
					this.setEventClass(this.getParameters().value("event_class"));
					spooler_log.debug1(".. parameter [event_class]: " + this.getEventClass());
				}
				else {
					this.setEventClass("");
				}
				jobParameterNames.add("event_class");
				if (this.getParameters().value("event_id") != null && this.getParameters().value("event_id").length() > 0) {
					this.setEventId(this.getParameters().value("event_id"));
					spooler_log.debug1(".. parameter [event_id]: " + this.getEventId());
				}
				else {
					this.setEventId("");
				}
				jobParameterNames.add("event_id");
				if (this.getParameters().value("exit_code") != null && this.getParameters().value("exit_code").length() > 0) {
					this.setEventExitCode(this.getParameters().value("exit_code"));
					spooler_log.debug1(".. parameter [exit_code]: " + this.getEventExitCode());
				}
				else {
					this.setEventExitCode("");
				}
				jobParameterNames.add("exit_code");
				if (this.getParameters().value("expires") != null && this.getParameters().value("expires").length() > 0) {
					this.setEventExpires(this.getParameters().value("expires"));
					spooler_log.debug1(".. parameter [expires]: " + this.getEventExpires());
				}
				else {
					this.setEventExpires("");
				}
				jobParameterNames.add("expires");
				if (this.getParameters().value("created") != null && this.getParameters().value("created").length() > 0) {
					this.setEventCreated(this.getParameters().value("created"));
					spooler_log.debug1(".. parameter [created]: " + this.getEventCreated());
				}
				else {
					this.setEventCreated(SOSDate.getCurrentTimeAsString());
				}
				jobParameterNames.add("created");
				/* recommended job parameters */
				if (this.getParameters().value("expiration_period") != null && this.getParameters().value("expiration_period").length() > 0) {
					this.setExpirationPeriod(this.getParameters().value("expiration_period"));
					spooler_log.debug1(".. parameter [expiration_period]: " + this.getExpirationPeriod());
				}
				else {
					// by default events are removed after 24 hours
					this.setExpirationPeriod("24:00:00");
				}
				jobParameterNames.add("expiration_period");
				if (this.getParameters().value("expiration_cycle") != null && this.getParameters().value("expiration_cycle").length() > 0) {
					this.setExpirationCycle(this.getParameters().value("expiration_cycle"));
					spooler_log.debug1(".. parameter [expiration_cycle]: " + this.getExpirationCycle());
				}
				else {
					this.setExpirationCycle("");
				}
				jobParameterNames.add("expiration_cycle");
				if (this.getParameters().value("event_handler_filepath") != null && this.getParameters().value("event_handler_filepath").length() > 0) {
					this.setEventHandlerFilepath(this.getParameters().value("event_handler_filepath"));
					spooler_log.debug1(".. parameter [event_handler_filepath]: " + this.getEventHandlerFilepath());
				}
				else {
					this.setEventHandlerFilepath("./config/events");
				}
				jobParameterNames.add("event_handler_filepath");
				if (this.getParameters().value("event_handler_filespec") != null && this.getParameters().value("event_handler_filespec").length() > 0) {
					this.setEventHandlerFilespec(this.getParameters().value("event_handler_filespec"));
					spooler_log.debug1(".. parameter [event_handler_filespec]: " + this.getEventHandlerFilespec());
				}
				else {
					this.setEventHandlerFilespec("\\.sos.scheduler.xsl$");
				}
				jobParameterNames.add("event_handler_filespec");
				/* any additional parameters */
				this.setEventParameters(getEvents().createElement("params"));
				String[] parameterNames = this.getParameters().names().split(";");
				for (int i = 0; i < parameterNames.length; i++) {
					if (!jobParameterNames.contains(parameterNames[i])) {
						Element param = getEvents().createElement("param");
						param.setAttribute("name", parameterNames[i]);
						param.setAttribute("value", this.getParameters().value(parameterNames[i]));
						getLogger().debug3("Event parameter [" + parameterNames[i] + "]: " + this.getParameters().value(parameterNames[i]));
						this.getEventParameters().appendChild(param);
					}
				}
			}
			catch (Exception e) {
				throw new Exception("error occurred processing parameters: " + e.getMessage());
			}
			this.setExpirationDate(calculateExpirationDate(expirationCycle, expirationPeriod));
			try { // to process events
					// moved to the beginning of spooler_process()
					// fetch events from global JobScheduler variable
					// this.getSchedulerEvents();
				// perform event action
				if (this.getEventAction().equalsIgnoreCase("add")) {
					this.getLogger().info("adding event: " + this.getEventClass() + " " + this.getEventId());
					this.addEvent();
				}
				else
					if (this.getEventAction().equalsIgnoreCase("remove")) {
						this.getLogger().info("removing event: " + this.getEventClass() + " " + this.getEventId());
						this.removeEvent();
					}
					else {
						this.getLogger().info("processing events");
					}
				// process events, launch dependent jobs and orders
				this.processSchedulerEvents();
				// update events in global JobScheduler variable
				this.putSchedulerEvents();
			}
			catch (Exception e) {
				throw new Exception("error occurred processing event: " + e.getMessage());
			}
			return spooler_job.order_queue() != null ? rc : false;
		}
		catch (Exception e) {
			spooler_log.warn("error occurred processing event [" + this.getEventClass() + " " + this.getEventId() + "]: " + e.getMessage());
			return false;
		}
	}

	public static Calendar calculateExpirationDate(final String expirationCycle, final String expirationPeriod) throws Exception {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(SOSDate.getCurrentTime());
			if (expirationCycle.indexOf(":") > -1) {
				String[] timeArray = expirationCycle.split(":");
				int hours = Integer.parseInt(timeArray[0]);
				int minutes = Integer.parseInt(timeArray[1]);
				int seconds = 0;
				if (timeArray.length > 2) {
					seconds = Integer.parseInt(timeArray[2]);
				}
				cal.set(Calendar.HOUR_OF_DAY, hours);
				cal.set(Calendar.MINUTE, minutes);
				cal.set(Calendar.SECOND, seconds);
				// add one day if the current timestamp is after the calculated cycle
				// TODO: Was ist im Dezember?
				if (cal.after(SOSDate.getCurrentTime()))
					cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			else
				if (expirationPeriod.indexOf(":") > -1) {
					String[] timeArray = expirationPeriod.split(":");
					int hours = Integer.parseInt(timeArray[0]);
					int minutes = Integer.parseInt(timeArray[1]);
					int seconds = 0;
					if (timeArray.length > 2) {
						seconds = Integer.parseInt(timeArray[2]);
					}
					// add the interval from the given period to the expiration date
					if (hours > 0)
						cal.add(Calendar.HOUR_OF_DAY, hours);
					if (minutes > 0)
						cal.add(Calendar.MINUTE, minutes);
					if (seconds > 0)
						cal.add(Calendar.SECOND, seconds);
				}
				else
					if (expirationPeriod.length() > 0) {
						cal.add(Calendar.SECOND, Integer.parseInt(expirationPeriod));
					}
		}
		catch (Exception e) {
			throw new Exception("Error calculating expiration date: " + e, e);
		}
		return cal;
	}

	/**
	 * get events from JobScheduler global variables
	 */
	public void getSchedulerEvents() throws Exception {
		try {
			String eventSet = spooler.var(JobSchedulerConstants.eventVariableName);
			if (this.getConnection() != null && (eventSet == null || eventSet.length() == 0)) {
				// ??
				/*if (this.getExpirationPeriod().length() > 0) {
				    this.getConnection().executeUpdate("DELETE FROM " + this.getTableEvents() + " WHERE \"EXPIRES\"<=%timestamp_iso('" + SOSDate.getTimeAsString(this.getExpirationDate().getTime()) + "') AND (\"SPOOLER_ID\" IS NULL OR \"SPOOLER_ID\"='' OR \"SPOOLER_ID\"='" + spooler.id() + "')");
				    this.getConnection().commit();
				}*/
				readEventsFromDB(getConnection(), spooler, getEvents(), getLogger());
			}
			else {
				eventSet = eventSet.replaceAll(String.valueOf((char) 254), "<").replaceAll(String.valueOf((char) 255), ">");
				this.getLogger().debug6("current event set: " + eventSet);
				if (eventSet.length() == 0)
					return;
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document eventDocument = docBuilder.parse(new InputSource(new StringReader(eventSet)));
				this.setEvents(eventDocument);
				// check for expired events
				// Sun JRE 1.5
				// XPath xpath = XPathFactory.newInstance().newXPath();
				// NodeList nodes = (NodeList) xpath.evaluate("//events/*", eventDocument, XPathConstants.NODESET);
				this.getLogger().debug9("looking for //events/event");
				NodeList nodes = org.apache.xpath.XPathAPI.selectNodeList(this.getEvents(), "//events/event");
				this.getLogger().debug9("nodes.getLength(): " + nodes.getLength());
				int activeNodeCount = 0;
				int expiredNodeCount = 0;
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					if (node == null || node.getNodeType() != Node.ELEMENT_NODE)
						continue;
					Node curEventExpires = node.getAttributes().getNamedItem("expires");
					if (curEventExpires == null || curEventExpires.getNodeValue() == null || curEventExpires.getNodeValue().length() == 0) {
						activeNodeCount++;
						continue;
					}
					// remove this event should the expiration date be overdue
					Calendar expiresDate = GregorianCalendar.getInstance();
					Calendar now = GregorianCalendar.getInstance();
					expiresDate.setTime(SOSDate.getTime(curEventExpires.getNodeValue()));
					if (expiresDate.before(now)) {
						this.getLogger().debug9("Found expired event");
						this.getEvents().getFirstChild().removeChild(node);
						this.getLogger().debug9("event removed");
						expiredNodeCount++;
					}
					else {
						activeNodeCount++;
					}
				}
				this.getLogger().info(activeNodeCount + " events are active, " + expiredNodeCount + " events have expired");
			}
		}
		catch (Exception e) { // ignore subsequent transactional errors
			if (this.getConnection() != null) {
				try {
					this.getConnection().rollback();
				}
				catch (Exception ex) {
				} // gracefully ignore this error
			}
			throw new Exception("events fetched with errors: " + e.getMessage());
		}
	}

	public static void readEventsFromDB(final SOSConnection conn, final Spooler spooler, final Document eventsDoc, final SOSLogger log) throws Exception {
		try {
			conn.executeUpdate("DELETE FROM " + getTableEvents()
					+ " WHERE \"EXPIRES\"<=%now AND (\"SPOOLER_ID\" IS NULL OR \"SPOOLER_ID\"='' OR \"SPOOLER_ID\"='" + spooler.id() + "')");
			conn.commit();
			Vector<?> vEvents = conn.getArrayAsVector("SELECT \"SPOOLER_ID\", \"REMOTE_SCHEDULER_HOST\", \"REMOTE_SCHEDULER_PORT\", \"JOB_CHAIN\", \"ORDER_ID\", \"JOB_NAME\", \"EVENT_CLASS\", \"EVENT_ID\", \"EXIT_CODE\", \"CREATED\", \"EXPIRES\", \"PARAMETERS\" FROM "
					+ getTableEvents() + " WHERE (\"SPOOLER_ID\" IS NULL OR \"SPOOLER_ID\"='' OR \"SPOOLER_ID\"='" + spooler.id() + "') ORDER BY \"ID\" ASC");
			Iterator<?> vIterator = vEvents.iterator();
			int vCount = 0;
			while (vIterator.hasNext()) {
				HashMap<?, ?> record = (HashMap<?, ?>) vIterator.next();
				Element event = eventsDoc.createElement("event");
				event.setAttribute("scheduler_id", record.get("spooler_id").toString());
				event.setAttribute("remote_scheduler_host", record.get("remote_scheduler_host").toString());
				event.setAttribute("remote_scheduler_port", record.get("remote_scheduler_port").toString());
				event.setAttribute("job_chain", record.get("job_chain").toString());
				event.setAttribute("order_id", record.get("order_id").toString());
				event.setAttribute("job_name", record.get("job_name").toString());
				event.setAttribute("event_class", record.get("event_class").toString());
				event.setAttribute("event_id", record.get("event_id").toString());
				event.setAttribute("exit_code", record.get("exit_code").toString());
				event.setAttribute("expires", record.get("expires").toString());
				event.setAttribute("created", record.get("created").toString());
				if (record.get("parameters").toString().length() > 0) {
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document eventParameters = docBuilder.parse(new InputSource(new StringReader(record.get("parameters").toString())));
					log.debug9("Importing params node...");
					Node impParameters = eventsDoc.importNode(eventParameters.getDocumentElement(), true);
					log.debug9("appending params child...");
					event.appendChild(impParameters);
				}
				eventsDoc.getLastChild().appendChild(event);
				vCount++;
			}
			log.info(vCount + " events restored from database");
		}
		catch (Exception e) {
			throw new JobSchedulerException("Failed to read events from database: " + e, e);
		}
	}

	/**
	 * update events in JobScheduler global variables
	 */
	public void putSchedulerEvents() throws Exception {
		try {
			String eventsString = this.xmlDocumentToString(this.getEvents());
			this.getLogger().debug9("Updating events: " + eventsString);
			spooler.set_var(JobSchedulerConstants.eventVariableName,
					eventsString.replaceAll("<", String.valueOf((char) 254)).replaceAll(">", String.valueOf((char) 255)));
		}
		catch (Exception e) {
			throw new JobSchedulerException("events updated with errors: " + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean analyseMonitorEventHandler(final String fileSpec, final String fileSpecLog) throws Exception {
		boolean erg = false;
		this.getLogger().debug6(".. looking for special event handler for: " + fileSpecLog + " " + fileSpec);
		Vector<File> specialFiles = SOSFile.getFilelist(this.getEventHandlerFilepath(), fileSpec, 0);
		Iterator<File> iter = specialFiles.iterator();
		while (iter.hasNext()) {
			File actionEventHandler = iter.next();
			if (actionEventHandler.exists() && actionEventHandler.canRead()) {
				erg = true;
				this.getLogger().debug1(".. analysing action event handler: " + actionEventHandler.getCanonicalPath());
				SOSEvaluateEvents eval = new SOSEvaluateEvents(spooler.hostname(), spooler.tcp_port());
				try {
					eval.setActiveEvents(this.getEvents());
					eval.readConfigurationFile(actionEventHandler);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				// Logging
				Iterator<SOSActions> iActions = eval.getListOfActions().iterator();
				while (iActions.hasNext()) {
					SOSActions a = iActions.next();
					this.getLogger().debug1(".... checking action " + a.getName());
					if (a.isActive(eval.getListOfActiveEvents())) {
						this.getLogger().debug1(".... added action:" + a.getName());
						this.getEventHandlerResultFileList().add(a.getCommands());
						NodeList commands = XPathAPI.selectNodeList(a.getCommands(), "command | remove_event | add_event");
						for (int i = 0; i < commands.getLength(); i++) {
							Node n = commands.item(i);
							if (n.getNodeName().equals("command") || n.getNodeName().equals("remove_event") || n.getNodeName().equals("add_event")) {
								this.getLogger().debug1(".. " + n.getNodeName() + " was added");
								NamedNodeMap attr = n.getAttributes();
								if (attr != null) {
									for (int ii = 0; ii < attr.getLength(); ii++) {
										this.getLogger().debug1("...." + attr.item(ii).getNodeName() + "=" + attr.item(ii).getNodeValue());
									}
								}
							}
						}
					}
				}
				// Logging Ende
			}
		}
		return erg;
	}
	
	
	private String getText(final Node n) {
		if (n != null) {
			return n.getNodeValue();
		}
		else {
			return "";
		}
	}

	private void getParametersFromEvents() throws Exception  {
		getLogger().debug5("executing getParametersFromEvents...."); 
		
			Document doc =  this.getEvents();
			NodeList events = doc.getElementsByTagName("event");
			getLogger().debug5("events length: " + events.getLength()); 
			for (int i = 0; i < events.getLength(); i++) {
				Node event = events.item(i);
				NamedNodeMap attr = event.getAttributes();
					
				String event_class = getText(attr.getNamedItem("event_class"));
				String event_id = getText(attr.getNamedItem("event_id"));
				getLogger().debug5("event_class:"+ event_class); 
				getLogger().debug5("event_id:"+ event_id); 
				 
				NodeList parameters = XPathAPI.selectNodeList(event, "params/param");
				getLogger().debug5("parameter length: " + parameters.getLength()); 
				if (parameters != null && parameters.getLength() > 0) {
				
					for (int ii = 0; ii < parameters.getLength(); ii++) {
						Node eventParam = parameters.item(ii);
 						

						NamedNodeMap paramAttr = eventParam.getAttributes();
					 
						String param_name = getText(paramAttr.getNamedItem("name"));
						String param_value = getText(paramAttr.getNamedItem("value"));
						parameterSubstitutor.addKey(event_class+"."+event_id+"."+param_name, param_value);
						parameterSubstitutor.addKey(event_class+".*."+param_name, param_value);
						parameterSubstitutor.addKey(event_id+"."+param_name, param_value);
						parameterSubstitutor.addKey(param_name, param_value);

						getLogger().debug5(event_class+"."+event_id+"."+param_name+"="+param_value);
					}
  				}
			}
		}
		 
	 

 
	private void getMonitorEventHandler() throws IOException, Exception {
		boolean fileFound = false;
		File eventHandlerFile = new File(this.getEventHandlerFilepath());
		if (eventHandlerFile.isDirectory()) {
			if (!eventHandlerFile.canRead()) {
				throw new Exception("event handler directory is not accessible: " + eventHandlerFile.getCanonicalPath());
			}
			this.getLogger().debug6("retrieving event handlers from directory: " + this.getEventHandlerFilepath() + " for file specification: Action");
			String fileSpec = "";
			String fileSpecLog = "";
			if (this.getEventJobChainName().length() > 0) {
				fileSpec = this.getEventJobChainName() + "(\\..*)?\\.job_chain\\.actions.xml$";
				fileSpecLog = "job_chain";
				fileFound = analyseMonitorEventHandler(fileSpec, fileSpecLog);
			}
			if (!fileFound && this.getEventJobName().length() > 0) {
				fileSpec = this.getEventJobName() + "(\\..*)?\\.job\\.actions.xml$";
				fileSpecLog = "job";
				fileFound = analyseMonitorEventHandler(fileSpec, fileSpecLog);
			}
			if (!fileFound && this.getEventClass().length() > 0) {
				fileSpec = this.getEventClass() + "(\\..*)?\\.event_class\\.actions.xml$";
				fileSpecLog = "event_class";
				fileFound = analyseMonitorEventHandler(fileSpec, fileSpecLog);
			}
			if (!fileFound) {
				fileSpec = "(\\..*)?\\.actions\\.xml$";
				fileSpecLog = "";
				fileFound = analyseMonitorEventHandler(fileSpec, fileSpecLog);
			}
		}
	}

	/**
	 * process events from individual event handlers
	 */
	public void processSchedulerEvents() throws Exception {
		File eventHandler = null;
		
	    parameterSubstitutor = new ParameterSubstitutor();
		
		parameterSubstitutor.addKey("current_date", SOSDate.getCurrentTimeAsString());
 		//Todo: Hier die Parameter des Jobs und die Parameter der Events hinzuf�gen
		getParametersFromEvents();
		
		try {
			HashMap<File, File> eventHandlerResultReference = new HashMap<File, File>();
			try { // to process event handlers for events
				this.getLogger().debug3(".. current events for event handler processing:");
				this.getLogger().debug3(this.xmlDocumentToString(this.getEvents()));
				this.setEventHandlerResultFileList(new LinkedHashSet<Object>());
				this.setEventHandlerFileList(new LinkedHashSet<File>());
				// add Resultfile for scheduler_action.xml
				this.getMonitorEventHandler();
				File eventHandlerFile = new File(this.getEventHandlerFilepath());
				if (eventHandlerFile.isDirectory()) {
					if (!eventHandlerFile.canRead())
						throw new Exception("event handler directory is not accessible: " + eventHandlerFile.getCanonicalPath());
					this.getLogger().debug6(
							"retrieving event handlers from directory: " + this.getEventHandlerFilepath() + " for file specification: "
									+ this.getEventHandlerFilespec());
					if (this.getEventJobChainName().length() > 0) {
						String fileSpec = this.getEventJobChainName() + "(\\..*)?\\.job_chain\\.sos.scheduler.xsl$";
						this.getLogger().debug6(".. looking for special event handler for job chain: " + fileSpec);
						Vector<?> specialFiles = SOSFile.getFilelist(this.getEventHandlerFilepath(), fileSpec, 0);
						Iterator<?> iter = specialFiles.iterator();
						while (iter.hasNext()) {
							File specialEventHandler = (File) iter.next();
							if (specialEventHandler.exists() && specialEventHandler.canRead()) {
								this.getEventHandlerFileList().add(specialEventHandler);
								this.getLogger().debug1(".. using special event handler for job chain: " + specialEventHandler.getCanonicalPath());
							}
						}
					}
					if (this.getEventJobName().length() > 0) {
						String fileSpec = this.getEventJobName() + "(\\..*)?\\.job\\.sos.scheduler.xsl$";
						this.getLogger().debug6(".. looking for special event handler for job: " + fileSpec);
						Vector<?> specialFiles = SOSFile.getFilelist(this.getEventHandlerFilepath(), fileSpec, 0);
						Iterator<?> iter = specialFiles.iterator();
						while (iter.hasNext()) {
							File specialEventHandler = (File) iter.next();
							if (specialEventHandler.exists() && specialEventHandler.canRead()) {
								this.getEventHandlerFileList().add(specialEventHandler);
								this.getLogger().debug1(".. using special event handler for job: " + specialEventHandler.getCanonicalPath());
							}
						}
					}
					if (this.getEventClass().length() > 0) {
						String fileSpec = this.getEventClass() + "(\\..*)?\\.event_class\\.sos.scheduler.xsl$";
						this.getLogger().debug6(".. looking for special event handlers for event class: " + fileSpec);
						Vector<?> specialFiles = SOSFile.getFilelist(this.getEventHandlerFilepath(), fileSpec, 0);
						Iterator<?> iter = specialFiles.iterator();
						while (iter.hasNext()) {
							File specialEventHandler = (File) iter.next();
							if (specialEventHandler.exists() && specialEventHandler.canRead()) {
								this.getEventHandlerFileList().add(specialEventHandler);
								this.getLogger().debug1(".. using special event handler for event class: " + specialEventHandler.getCanonicalPath());
							}
						}
					}
					// add default event handlers
					this.getEventHandlerFileList().addAll(SOSFile.getFilelist(this.getEventHandlerFilepath(), this.getEventHandlerFilespec(), 0));
					this.getLogger().debug1(
							".. adding list of default event handlers: " + this.getEventHandlerFilepath() + "/" + this.getEventHandlerFilespec());
				}
				else {
					if (!eventHandlerFile.canRead()) {
						throw new Exception("event handler file is not accessible: " + eventHandlerFile.getCanonicalPath());
					}
					this.getEventHandlerFileList().add(eventHandlerFile);
				}
				HashMap<String, String> stylesheetParameters = new HashMap<String, String>();
				stylesheetParameters.put("current_date", SOSDate.getCurrentTimeAsString());
				stylesheetParameters.put("expiration_date", SOSDate.getTimeAsString(this.getExpirationDate().getTime()));
				// old Xalan doesn't work with parameters
				getEvents().getDocumentElement().setAttribute("current_date", SOSDate.getCurrentTimeAsString());
				getEvents().getDocumentElement().setAttribute("expiration_date", SOSDate.getTimeAsString(this.getExpirationDate().getTime()));
				this.setEventHandlerFileListIterator(this.getEventHandlerFileList().iterator());
				while (this.getEventHandlerFileListIterator().hasNext()) {
					eventHandler = this.getEventHandlerFileListIterator().next();
					if (eventHandler == null)
						continue;
					File stylesheetResultFile = File.createTempFile("sos", ".xml");
					stylesheetResultFile.deleteOnExit();
					this.getEventHandlerResultFileList().add(stylesheetResultFile);
					eventHandlerResultReference.put(stylesheetResultFile, eventHandler);
					this.getLogger().debug1(".. processing events with stylesheet: " + eventHandler.getCanonicalPath());
					SOSXMLTransformer.transform(this.xmlDocumentToString(this.getEvents()), eventHandler, stylesheetResultFile, stylesheetParameters);
				}
			}
			catch (Exception e) {
				throw new Exception("error occurred processing event handler" + (eventHandler != null ? " [" + eventHandler.getCanonicalPath() + "]" : "")
						+ ": " + e.getMessage());
			}
			try { // to execute commands returned by event handlers
				this.setEventHandlerResultFileListIterator(this.getEventHandlerResultFileList().iterator());
				while (this.getEventHandlerResultFileListIterator().hasNext()) {
					NodeList commands = null;
					Object result = this.getEventHandlerResultFileListIterator().next();
					if (result instanceof File) {
						File resultFile = (File) result;
						if (resultFile == null)
							continue;
						File eventHandlerFile = eventHandlerResultReference.get(resultFile);
						this.getLogger().debug3(".. content of result file for event handler: " + eventHandlerFile.getCanonicalPath());
						this.getLogger().debug3(this.getFileContent(resultFile).toString());
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
						Document eventDocument = docBuilder.parse(new InputSource(new StringReader(this.getFileContent(resultFile).toString())));
						Element eCommands = eventDocument.getDocumentElement();
						if (eCommands != null) {
							String debug = eCommands.getAttribute("debug");
							if (debug.equalsIgnoreCase("true"))
								logTransformation(resultFile, eventHandlerFile);
						}
						// XPath xpath = XPathFactory.newInstance().newXPath();
						// NodeList commands = (NodeList) xpath.evaluate("//command", eventDocument, XPathConstants.NODESET);
						commands = XPathAPI.selectNodeList(eventDocument, "//command");
					}
					else {
						commands = XPathAPI.selectNodeList((Node) result, "command");
					}
					for (int i = 0; i < commands.getLength(); i++) {
						Node command = commands.item(i);
						NamedNodeMap commandAttributes = command.getAttributes();
						String commandHost = spooler.hostname();
						String commandPort = Integer.toString(spooler.tcp_port());
						String commandProtocol = "tcp";
						for (int j = 0; j < commandAttributes.getLength(); j++) {
							if (commandAttributes.item(j).getNodeName().equals("scheduler_host") && commandAttributes.item(j).getNodeValue().length() > 0) {
								this.getLogger().debug7("using host from command: " + commandAttributes.item(j).getNodeValue());
								commandHost = commandAttributes.item(j).getNodeValue();
							}
							if (commandAttributes.item(j).getNodeName().equals("scheduler_port") && commandAttributes.item(j).getNodeValue().length() > 0)
								commandPort = commandAttributes.item(j).getNodeValue();
							if (commandAttributes.item(j).getNodeName().equals("protocol") && commandAttributes.item(j).getNodeValue().length() > 0)
								commandProtocol = commandAttributes.item(j).getNodeValue();
						}
						SOSSchedulerCommand schedulerCommand = new SOSSchedulerCommand();
						schedulerCommand.setTimeout(socket_timeout);

						if (commandHost.length() > 0) {
							schedulerCommand.setHost(commandHost);
							if (commandPort.length() > 0) {
								schedulerCommand.setPort(Integer.parseInt(commandPort));
							}
							else {
								throw new Exception("empty port has been specified by event handler response for commands");
							}
						}
						else {
							throw new Exception("empty JobScheduler ID or host and port have been specified by event handler response for commands");
						}
						if (commandProtocol.length() > 0)
							schedulerCommand.setProtocol(commandProtocol);
						try {
							this.getLogger().debug1(".. connecting to JobScheduler " + schedulerCommand.getHost() + ":" + schedulerCommand.getPort());
							schedulerCommand.connect();
							NodeList commandElements = command.getChildNodes();
							for (int k = 0; k < commandElements.getLength(); k++) {
								if (commandElements.item(k).getNodeType() != Node.ELEMENT_NODE)
									continue;
								String commandRequest = this.xmlNodeToString(commandElements.item(k));
								
								commandRequest = parameterSubstitutor.replace(commandRequest);
								commandRequest = parameterSubstitutor.replaceEnvVars(commandRequest);
								commandRequest = parameterSubstitutor.replaceSystemProperties(commandRequest);
								
								this.getLogger().info(".. sending command to remote JobScheduler [" + commandHost + ":" + commandPort + "]: " + commandRequest);
								schedulerCommand.sendRequest(commandRequest);

								SOSXMLXPath answer = new SOSXMLXPath(new StringBuffer(schedulerCommand.getResponse()));
								String errorText = answer.selectSingleNodeValue("//ERROR/@text");
								if (errorText != null && errorText.length() > 0) {
									throw new Exception("could not send command to remote JobScheduler [" + commandHost + ":" + commandPort + "]: " + errorText);
								}
							}
						}
						catch (Exception e) {
							throw new Exception("Error contacting remote JobScheduler: " + e, e);
						}
						finally {
							try {
								schedulerCommand.disconnect();
							}
							catch (Exception ex) {
							}
						}
					}
				}
			}
			catch (Exception e) {
				throw new Exception("could not execute command: " + e.getMessage());
			}
			try { // to remove events that have been dismissed by stylsheets
				this.setEventHandlerResultFileListIterator(this.getEventHandlerResultFileList().iterator());
				while (this.getEventHandlerResultFileListIterator().hasNext()) {
					Object result = this.getEventHandlerResultFileListIterator().next();
					NodeList commands = null;
					if (result instanceof File) {
						File resultFile = (File) result;
						if (resultFile == null)
							continue;
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
						Document eventDocument = docBuilder.parse(new InputSource(new StringReader(this.getFileContent(resultFile).toString())));
						// XPath xpath = XPathFactory.newInstance().newXPath();
						// NodeList commands = (NodeList) xpath.evaluate("//remove_event", eventDocument, XPathConstants.NODESET);
						commands = XPathAPI.selectNodeList(eventDocument, "//remove_event");
					}
					else {
						commands = XPathAPI.selectNodeList((Node) result, "remove_event");
					}
					this.getLogger().debug3("-->" + commands.getLength() + " events should be deleted");
					for (int i = 0; i < commands.getLength(); i++) {
						if (commands.item(i) == null)
							continue;
						if (commands.item(i).getNodeType() == Node.ELEMENT_NODE) {
							this.removeEvents(commands.item(i).getChildNodes());
						}
					}
				}
			}
			catch (Exception e) {
				throw new Exception("could not remove event caused by event handler: " + e.getMessage());
			}
			try { // to add events that have been created by event handlers
				this.setEventHandlerResultFileListIterator(this.getEventHandlerResultFileList().iterator());
				while (this.getEventHandlerResultFileListIterator().hasNext()) {
					Object result = this.getEventHandlerResultFileListIterator().next();
					NodeList commands = null;
					if (result instanceof File) {
						File resultFile = (File) result;
						if (resultFile == null)
							continue;
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
						Document eventDocument = docBuilder.parse(new InputSource(new StringReader(this.getFileContent(resultFile).toString())));
						// XPath xpath = XPathFactory.newInstance().newXPath();
						// NodeList commands = (NodeList) xpath.evaluate("//add_event/*", eventDocument, XPathConstants.NODESET);
						commands = XPathAPI.selectNodeList(eventDocument, "//add_event/event");
					}
					else {
						commands = XPathAPI.selectNodeList((Node) result, "add_event/event");
					}
					for (int i = 0; i < commands.getLength(); i++) {
						if (commands.item(i) == null)
							continue;
						if (commands.item(i).getNodeType() == Node.ELEMENT_NODE) {
							NamedNodeMap attributes = commands.item(i).getAttributes();
							if (attributes == null)
								continue;
							Element event = this.getEvents().createElement("event");
							for (int j = 0; j < attributes.getLength(); j++) {
								if (attributes.item(j).getNodeName().equalsIgnoreCase("event_name"))
									continue;
								event.setAttribute(attributes.item(j).getNodeName(), attributes.item(j).getNodeValue());
							}
							this.addEvent(event);
						}
					}
				}
			}
			catch (Exception e) {
				throw new Exception("could not add event created by event handler: " + e.getMessage());
			}
		}
		catch (Exception e) {
			throw new Exception("events processed with errors: " + e.getMessage());
		}
		finally {
			try { // to remove temporary files
				this.setEventHandlerResultFileListIterator(this.getEventHandlerResultFileList().iterator());
				while (this.getEventHandlerResultFileListIterator().hasNext()) {
					Object result = this.getEventHandlerResultFileListIterator().next();
					if (result instanceof File) {
						File resultFile = (File) result;
						if (resultFile != null) {
							if (!resultFile.delete()) {
								//							this.getLogger().info(".. temporary file could not be removed: " + resultFile.getCanonicalPath());
								resultFile.deleteOnExit();
							}
						}
					}
				}
			}
			catch (Exception e) {
				this.getLogger().warn("could not delete temporary file: " + e.getMessage());
			}
		}
	}

	private void logTransformation(final File resultFile, final File stylesheetFile) throws Exception {
		try {
			File logDir = new File(spooler.log_dir());
			File eventLogDir = new File(logDir, "events");
			if (!eventLogDir.exists()) {
				getLogger().info("creating event log dir: " + eventLogDir.getAbsolutePath());
				if (!eventLogDir.mkdir()) {
					getLogger().warn("directory [" + eventLogDir.getAbsolutePath() + "] could not be created.");
					return;
				}
			}
			File stylesheetLogDir = new File(eventLogDir, stylesheetFile.getName());
			if (!stylesheetLogDir.exists()) {
				getLogger().info("creating stylesheet log dir: " + stylesheetLogDir.getAbsolutePath());
				if (!stylesheetLogDir.mkdir()) {
					getLogger().warn("directory [" + stylesheetLogDir.getAbsolutePath() + "] could not be created.");
					return;
				}
			}
			String timeStamp = SOSDate.getCurrentDateAsString("yyyy-MM-dd_HHmmss_SSS");
			File resultLogFile = new File(stylesheetLogDir, "events_" + timeStamp + "_result.xml");
			SOSFile.copyFile(resultFile, resultLogFile);
			File eventFile = new File(stylesheetLogDir, "events_" + timeStamp + ".xml");
			// write current events to eventFile
			OutputStream fout = new FileOutputStream(eventFile, false);
			OutputStreamWriter out = new OutputStreamWriter(fout, "UTF-8");
			OutputFormat format = new OutputFormat(getEvents());
			format.setEncoding("UTF-8");
			format.setIndenting(true);
			format.setIndent(2);
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(getEvents());
			out.close();
			getLogger().debug("current events logged to: " + eventFile.getAbsolutePath());
			getLogger().debug("transformation result logged to: " + resultLogFile.getAbsolutePath());
		}
		catch (Exception e) {
			throw new Exception("Error logging Transformation result: " + e, e);
		}
	}

	/**
	 * add an event
	 */
	public void addEvent() throws Exception {
		try {
			this.getLogger().debug9(
					".. constructing event: schedulerId=" + this.getEventSchedulerId() + ", eventClass=" + this.getEventClass() + ", eventId="
							+ this.getEventId());
			Element event = this.getEvents().createElement("event");
			event.setAttribute("scheduler_id", this.getEventSchedulerId());
			event.setAttribute("remote_scheduler_host", this.getEventRemoteSchedulerHost());
			event.setAttribute("remote_scheduler_port", this.getEventRemoteSchedulerPort());
			event.setAttribute("job_chain", this.getEventJobChainName());
			event.setAttribute("order_id", this.getEventOrderId());
			event.setAttribute("job_name", this.getEventJobName());
			event.setAttribute("event_class", this.getEventClass());
			event.setAttribute("event_id", this.getEventId());
			event.setAttribute("exit_code", this.getEventExitCode());
			event.setAttribute("created", this.getEventCreated());
			if (getEventExpires().length() == 0 || getEventExpires().equalsIgnoreCase("default")) {
				event.setAttribute("expires", SOSDate.getTimeAsString(this.getExpirationDate().getTime()));
			}
			else
				if (getEventExpires().equalsIgnoreCase("never")) {
					getLogger().debug7("event expires: never");
				}
				else {
					event.setAttribute("expires", this.getEventExpires());
				}
			if (this.getEventParameters() != null && this.getEventParameters().getChildNodes().getLength() > 0) {
				event.appendChild(this.getEventParameters());
			}
			this.addEvent(event);
		}
		catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * add an event
	 */
	public void addEvent(final Element event) throws Exception {
		addEvent(event, true);
	}

	/**
	 * add an event
	 */
	public void addEvent(final Element event, final boolean replace) throws Exception {
		if (event.getAttribute("scheduler_id").length() == 0) {
			event.setAttribute("scheduler_id", spooler.id());
		}
		if (replace) {
			Element dummyParent = getEvents().createElement("events");
			Element remEv = (Element) event.cloneNode(true);
			dummyParent.appendChild(remEv);
			getLogger().debug9("remEv: " + remEv);
			getLogger().debug9("remEv.getParentNode(): " + remEv.getParentNode());
			remEv.removeAttribute("created");
			remEv.removeAttribute("expires");
			removeEvents(remEv.getParentNode().getChildNodes());
		}
		try {
			String curEventSchedulerId = this.getAttributeValue(event, "scheduler_id");
			String curEventRemoteSchedulerHost = this.getAttributeValue(event, "remote_scheduler_host");
			String curEventRemoteSchedulerPort = this.getAttributeValue(event, "remote_scheduler_port");
			String curEventClass = this.getAttributeValue(event, "event_class");
			String curEventId = this.getAttributeValue(event, "event_id");
			String curEventExitCode = this.getAttributeValue(event, "exit_code");
			String curEventJobChainName = this.getAttributeValue(event, "job_chain");
			String curEventOrderId = this.getAttributeValue(event, "order_id");
			String curEventJobName = this.getAttributeValue(event, "job_name");
			String curEventCreated = this.getAttributeValue(event, "created");
			String curExpiration_period = this.getAttributeValue(event, "expiration_period");
			String curExpiration_cycle = this.getAttributeValue(event, "expiration_cycle");
			this.getLogger().debug3(".. --> curExpiration_period:" + curExpiration_period);
			this.getLogger().debug3(".. --> curExpiration_cycle:" + curExpiration_cycle);
			// Im AddEvent ist kein ExpirationPeriod angegeben. Wir nehmen den Wert des Jobs (Default = "24:00:00")
			if (curExpiration_period == null || curExpiration_period.length() <= 0) {
				curExpiration_period = this.getExpirationPeriod();
			}
			// Im AddEvent ist kein ExpirationCycle angegeben. Wir nehmen den Wert des Jobs (Default="")
			if (curExpiration_cycle == null || curExpiration_cycle.length() <= 0) {
				curExpiration_cycle = this.getExpirationCycle();
			}
			String curEventExpires = this.getAttributeValue(event, "expires");
			this.getLogger().debug3(".. --> curEventExpires:" + curEventExpires);
			// Wenn kein Expires explizit angegeben ist, wird es aus expirationPeriod (Dauer) bzw. expirationCycle(Zeitpunkt) errechnet.
			// Default ist das eine Lebensdauer von 24 Stunden.
			if (curEventExpires == null || curEventExpires.length() <= 0) {
				Calendar cal = calculateExpirationDate(curExpiration_cycle, curExpiration_period);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					curEventExpires = format.format(cal.getTime());
					this.getLogger().debug3(".. --> curEventExpires:" + curEventExpires);
				}
				catch (Exception pe) {
					this.getLogger().warn(cal.getTime().toString() + " is not a valid Date. Expires will be set to default");
				}
			}

			this.getLogger().info(
					".. adding event ...: scheduler id=" + curEventSchedulerId + ", event class=" + curEventClass + ", event id=" + curEventId + ", exit code="
							+ curEventExitCode + ", job chain=" + curEventJobChainName + ", order id=" + curEventOrderId + ", job=" + curEventJobName);

			if (curEventId.length() == 0)
				throw new Exception("Empty event_id is not allowed.");
			this.getEvents().getLastChild().appendChild(event);
			if (this.getConnection() == null) {
				// spooler_log.debug1("no database in use - events are not stored persistently");
			}
			else {
				String stmt = "INSERT INTO "
						+ JobSchedulerEventJob.getTableEvents()
						+ " (\"SPOOLER_ID\", \"REMOTE_SCHEDULER_HOST\", \"REMOTE_SCHEDULER_PORT\", \"JOB_CHAIN\", \"ORDER_ID\", \"JOB_NAME\", \"EVENT_CLASS\", \"EVENT_ID\", \"EXIT_CODE\", \"CREATED\", \"EXPIRES\")"
						+ " VALUES ('" + curEventSchedulerId + "', '" + curEventRemoteSchedulerHost + "', "
						+ (curEventRemoteSchedulerPort.length() == 0 ? "0" : curEventRemoteSchedulerPort) + ", '" + curEventJobChainName + "', '"
						+ curEventOrderId + "', '" + curEventJobName + "', '" + curEventClass + "', '" + curEventId + "', '" + curEventExitCode + "', "
						+ (curEventCreated.length() > 0 ? "%timestamp_iso('" + curEventCreated + "')" : "%now") + ", "
						+ (curEventExpires.length() > 0 ? "%timestamp_iso('" + curEventExpires + "')" : "NULL") + ")";
				this.getConnection().executeUpdate(stmt);
				NodeList nodes = XPathAPI.selectNodeList(event, "params");
				if (nodes != null && nodes.getLength() > 0) {
					getLogger().debug5("Event has parameters. Storing parameters...");
					Element params = (Element) nodes.item(0);
					String paramsString = xmlElementToString(params);
					getLogger().debug5(paramsString);
					String sequenceName = "SCHEDULER_EVENTS_ID_SEQ";
					if (getConnection() instanceof SOSPgSQLConnection)
						sequenceName = "\"scheduler_events_ID_seq\"";
					String eventDbID = getConnection().getLastSequenceValue(sequenceName);
					getConnection().updateClob(getTableEvents(), "PARAMETERS", paramsString, "\"ID\"=" + eventDbID);
				}
				this.getConnection().commit();
			}
		}
		catch (Exception e) {
			if (this.getConnection() != null) {
				try {
					this.getConnection().rollback();
				}
				catch (Exception ex) {
				} // gracefully ignore this error
			}
			throw new Exception(e);
		}
	}

	/**
	 * remove multiple events with matching attributes
	 */
	public void removeEvents(final NodeList events1) throws Exception {
		try {
			for (int i = 0; i < events1.getLength(); i++) {
				if (events1.item(i) == null || events1.item(i).getNodeType() != Node.ELEMENT_NODE)
					continue;
				String xquery = "//event[";
				String and = "";
				Element event = (Element) events1.item(i);
				String curEventClass = event.getAttribute("event_class");
				if (curEventClass != null && curEventClass.equalsIgnoreCase(JobSchedulerConstants.EVENT_CLASS_ALL_EVENTS)) {
					removeAllEvents();
					return;
				}
				boolean hasAttributes = false;
				NamedNodeMap eventAttributeList = event.getAttributes();
				for (int j = 0; j < eventAttributeList.getLength(); j++) {
					Node eventAttribute = eventAttributeList.item(j);
					if (eventAttribute == null)
						continue;
					if (eventAttribute.getNodeName().equalsIgnoreCase("event_name"))
						continue;
					String value = eventAttribute.getNodeValue();
					if (!eventAttribute.getNodeName().equals("event_title") && value != null && value.length() > 0) {
						hasAttributes = true;
						xquery += and + "@" + eventAttribute.getNodeName() + "='" + eventAttribute.getNodeValue() + "'";
						and = " and ";
					}
				}
				xquery += "]";
				if (!hasAttributes) {
					getLogger().warn("current event has no attributes. Removal (of all elements) will not be performed.");
					continue;
				}
				// XPath xpath = XPathFactory.newInstance().newXPath();
				// NodeList nodes = (NodeList) xpath.evaluate(xquery, this.getEvents(), XPathConstants.NODESET);
				getLogger().debug7("xquery to remove events: " + xquery);
				NodeList nodes = XPathAPI.selectNodeList(this.getEvents(), xquery);
				for (int j = 0; j < nodes.getLength(); j++) {
					if (nodes.item(j) == null)
						continue;
					if (nodes.item(j).getNodeType() == Node.ELEMENT_NODE)
						this.removeEvent(nodes.item(j));
				}
			}
		}
		catch (Exception e) {
			throw new Exception(e);
		}
	}

	public void removeAllEvents() throws Exception {
		try {
			getLogger().info("event class is: " + JobSchedulerConstants.EVENT_CLASS_ALL_EVENTS + ". Removing all events.");
			Document eventDocument = getEvents();
			eventDocument.removeChild(eventDocument.getFirstChild());
			eventDocument.appendChild(eventDocument.createElement("events"));
			if (this.getConnection() != null) {
				String stmt = "DELETE FROM " + JobSchedulerEventJob.getTableEvents() + " WHERE 1>0";
				this.getConnection().executeUpdate(stmt);
				this.getConnection().commit();
			}
			this.setEventClass("");
		}
		catch (Exception e) {
			throw new Exception("Error removing all events: " + e, e);
		}
	}

	/**
	 * remove an event
	 */
	public void removeEvent() throws Exception {
		try {
			this.getLogger().debug9(".. constructing event: eventClass=" + this.getEventClass() + ", eventId=" + this.getEventId());
			Element event = this.getEvents().createElement("event");
			event.setAttribute("event_class", this.getEventClass());
			event.setAttribute("event_id", this.getEventId());
			event.setAttribute("scheduler_id", this.getEventSchedulerId());
			event.setAttribute("remote_scheduler_host", this.getEventRemoteSchedulerHost());
			if (!this.getEventRemoteSchedulerPort().equalsIgnoreCase("0"))
				event.setAttribute("remote_scheduler_port", this.getEventRemoteSchedulerPort());
			event.setAttribute("job_chain", this.getEventJobChainName());
			event.setAttribute("order_id", this.getEventOrderId());
			event.setAttribute("job_name", this.getEventJobName());
			if (this.getEventExitCode().length() > 0) {
				event.setAttribute("exit_code", this.getEventExitCode());
			}
			// event.setAttribute("expires", this.getEventExpires());
			// event.setAttribute("created", this.getEventCreated());
			// TODO: sollen Parameter beim Remove ausgewertet werden?
			// event.setAttribute("parameters", this.getEventParameters());
			Element dummyParent = getEvents().createElement("events");
			dummyParent.appendChild(event);
			this.removeEvents(event.getParentNode().getChildNodes());
		}
		catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * remove an event
	 */
	public void removeEvent(final Node event) throws Exception {
		try {
			String curEventSchedulerId = this.getAttributeValue(event, "scheduler_id");
			String curEventClass = this.getAttributeValue(event, "event_class");
			String curEventId = this.getAttributeValue(event, "event_id");
			String curEventExitCode = this.getAttributeValue(event, "exit_code");
			String curEventJobChainName = this.getAttributeValue(event, "job_chain");
			String curEventOrderId = this.getAttributeValue(event, "order_id");
			String curEventJobName = this.getAttributeValue(event, "job_name");
			this.getLogger().info(
					".. removing event ...: scheduler id=" + curEventSchedulerId + ", event class=" + curEventClass + ", event id=" + curEventId
							+ ", exit code=" + curEventExitCode + ", job chain=" + curEventJobChainName + ", order id=" + curEventOrderId + ", job="
							+ curEventJobName);
			Node nEvents = this.getEvents().getFirstChild();
			this.getLogger().debug7("Events Name: " + nEvents.getLocalName());
			this.getLogger().debug7("Events size: " + nEvents.getChildNodes().getLength());
			this.getEvents().getFirstChild().removeChild(event);
			this.getLogger().debug7("Events size: " + nEvents.getChildNodes().getLength());
			if (this.getConnection() == null) {
				// this.getLogger().debug1("no database in use - events are not stored persistently");
			}
			else {
				String stmt = "DELETE FROM " + JobSchedulerEventJob.getTableEvents() + " WHERE ";
				String and = "";
				if (curEventSchedulerId.length() > 0) {
					stmt += and + "\"SPOOLER_ID\"='" + curEventSchedulerId + "'";
					and = " AND ";
				}
				if (curEventJobChainName.length() > 0) {
					stmt += and + "\"JOB_CHAIN\"='" + curEventJobChainName + "'";
					and = " AND ";
				}
				if (curEventOrderId.length() > 0) {
					stmt += and + "\"ORDER_ID\"='" + curEventOrderId + "'";
					and = " AND ";
				}
				if (curEventJobName.length() > 0) {
					stmt += and + "\"JOB_NAME\"='" + curEventJobName + "'";
					and = " AND ";
				}
				if (curEventClass.length() > 0) {
					stmt += and + "\"EVENT_CLASS\"='" + curEventClass + "'";
					and = " AND ";
				}
				if (curEventId.length() > 0) {
					stmt += and + "\"EVENT_ID\"='" + curEventId + "'";
					and = " AND ";
				}
				if (curEventExitCode.length() > 0) {
					stmt += and + "\"EXIT_CODE\"='" + curEventExitCode + "'";
					and = " AND ";
				}
				this.getConnection().executeUpdate(stmt);
				this.getConnection().commit();
			}
		}
		catch (Exception e) {
			if (this.getConnection() != null) {
				try {
					this.getConnection().rollback();
				}
				catch (Exception ex) {
				} // gracefully ignore this error
			}
			throw new Exception(e);
		}
	}

	/**
	 * return XML String
	 */
	public String getAttributeValue(final Node node, final String namedItem) throws Exception {
		try {
			if (node.getAttributes() == null || node.getAttributes().getLength() == 0)
				return "";
			if (node.getAttributes().getNamedItem(namedItem) == null)
				return "";
			return node.getAttributes().getNamedItem(namedItem).getNodeValue();
		}
		catch (Exception e) {
			throw new Exception("error occurred reading attribute value: " + e.getMessage());
		}
	}

	/**
	 * return XML String
	 */
	public String xmlNodeToString(final Node node) throws Exception {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			document.appendChild(document.importNode(node, true));
			StringWriter out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, new OutputFormat(document));
			serializer.serialize(document);
			return out.toString();
		}
		catch (Exception e) {
			throw new Exception("error occurred transforming node: " + e.getMessage());
		}
	}

	/**
	 * return XML String
	 */
	public String xmlDocumentToString(final Document document) throws Exception {
		try {
			StringWriter out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, new OutputFormat(document));
			serializer.serialize(document);
			return out.toString();
		}
		catch (Exception e) {
			throw new Exception("error occurred transforming document: " + e.getMessage());
		}
	}

	/**
	 * return XML String
	 */
	public String xmlElementToString(final Element element) throws Exception {
		try {
			StringWriter out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, new OutputFormat());
			serializer.serialize(element);
			return out.toString();
		}
		catch (Exception e) {
			throw new Exception("error occurred transforming document: " + e.getMessage());
		}
	}

	/**
	 * read content of a file to a string buffer
	 */
	private StringBuffer getFileContent(final File file) throws Exception {
		BufferedInputStream in = null;
		StringBuffer content = new StringBuffer();
		if (file == null)
			throw new Exception("no valid file object found");
		if (!file.canRead())
			throw new Exception("file not accessible: " + file.getCanonicalPath());
		try {
			FileInputStream fis = new FileInputStream(file);
			in = new BufferedInputStream(fis);
			byte buffer[] = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				content.append(new String(buffer, 0, bytesRead));
			}
			fis.close();
			fis = null;
			in.close();
			in = null;
			return content;
		}
		catch (Exception e) {
			throw new Exception("error occurred reading content of file [" + file.getCanonicalPath() + "]: " + e.getMessage());
		}
		finally {
			if (in != null) {
				in.close();
				in = null;
			}
		}
	}

	/**
	 * @return the parameters
	 */
	public Variable_set getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(final Variable_set parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the eventClass
	 */
	public String getEventClass() {
		return eventClass;
	}

	/**
	 * @param eventClass the eventClass to set
	 */
	public void setEventClass(final String eventClass) {
		this.eventClass = eventClass;
	}

	/**
	 * @return the eventExpires
	 */
	public String getEventExpires() {
		return eventExpires;
	}

	/**
	 * @param eventExpires the eventExpires to set
	 */
	public void setEventExpires(final String eventExpires) {
		this.eventExpires = eventExpires;
	}

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(final String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the eventParameters
	 */
	public Element getEventParameters() {
		return eventParameters;
	}

	/**
	 * @param eventParameters the eventParameters to set
	 */
	public void setEventParameters(final Element eventParameters) {
		this.eventParameters = eventParameters;
	}

	/**
	 * @return the eventAction
	 */
	public String getEventAction() {
		return eventAction;
	}

	/**
	 * @param eventAction the eventAction to set
	 */
	public void setEventAction(final String eventAction) throws Exception {
		if (!eventAction.equalsIgnoreCase("add") && !eventAction.equalsIgnoreCase("remove") && !eventAction.equalsIgnoreCase("process")) {
			throw new Exception("invalid action specified [add, remove, process]: " + eventAction);
		}
		this.eventAction = eventAction;
	}

	/**
	 * @return the tableEvents
	 */
	public static String getTableEvents() {
		return JobSchedulerEventJob.tableEvents;
	}

	/**
	 * @param tableEvents the tableEvents to set
	 */
	public static void setTableEvents(final String tableEvents) {
		JobSchedulerEventJob.tableEvents = tableEvents;
	}

	/**
	 * @return the events
	 */
	public Document getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(final Document events) {
		this.events = events;
	}

	/**
	 * @return the eventSchedulerId
	 */
	public String getEventSchedulerId() {
		return eventSchedulerId;
	}

	/**
	 * @param eventSchedulerId the eventSchedulerId to set
	 */
	public void setEventSchedulerId(final String eventSchedulerId) {
		this.eventSchedulerId = eventSchedulerId;
	}

	/**
	 * @return the expirationPeriod
	 */
	public String getExpirationPeriod() {
		return expirationPeriod;
	}

	/**
	 * @param expirationPeriod the expirationPeriod to set
	 */
	public void setExpirationPeriod(final String expirationPeriod) {
		this.expirationPeriod = expirationPeriod;
	}

	/**
	 * @return the eventJobChainName
	 */
	public String getEventJobChainName() {
		return eventJobChainName;
	}

	/**
	 * @param eventJobChainName the eventJobChainName to set
	 */
	public void setEventJobChainName(final String eventJobChainName) {
		this.eventJobChainName = eventJobChainName;
	}

	/**
	 * @return the eventJobName
	 */
	public String getEventJobName() {
		return eventJobName;
	}

	/**
	 * @param eventJobName the eventJobName to set
	 */
	public void setEventJobName(final String eventJobName) {
		this.eventJobName = eventJobName;
	}

	/**
	 * @return the event handler Filepath
	 */
	public String getEventHandlerFilepath() {
		return eventHandlerFilepath;
	}

	/**
	 * @param eventHandlerFilepath the event handler Filepath to set
	 */
	public void setEventHandlerFilepath(final String eventHandlerFilepath) {
		this.eventHandlerFilepath = eventHandlerFilepath;
	}

	/**
	 * @return the stylesheetFilespec
	 */
	public String getEventHandlerFilespec() {
		return eventHandlerFilespec;
	}

	/**
	 * @param eventHandlerFilespec the stylesheetFilespec to set
	 */
	public void setEventHandlerFilespec(final String eventHandlerFilespec) {
		this.eventHandlerFilespec = eventHandlerFilespec;
	}

	/**
	 * @return the stylesheetFileList
	 */
	public Collection<File> getEventHandlerFileList() {
		return eventHandlerFileList;
	}

	/**
	 * @param eventHandlerFileList the stylesheetFileList to set
	 */
	public void setEventHandlerFileList(final Collection<File> eventHandlerFileList) {
		this.eventHandlerFileList = eventHandlerFileList;
	}

	/**
	 * @return the stylesheetFileListIterator
	 */
	public Iterator<File> getEventHandlerFileListIterator() {
		return eventHandlerFileListIterator;
	}

	/**
	 * @param eventHandlerFileListIterator the stylesheetFileListIterator to set
	 */
	public void setEventHandlerFileListIterator(final Iterator<File> eventHandlerFileListIterator) {
		this.eventHandlerFileListIterator = eventHandlerFileListIterator;
	}

	/**
	 * @return the stylesheetResultFileList
	 */
	public Collection<Object> getEventHandlerResultFileList() {
		return eventHandlerResultFileList;
	}

	/**
	 * @param eventHandlerResultFileList the stylesheetResultFileList to set
	 */
	public void setEventHandlerResultFileList(final Collection<Object> eventHandlerResultFileList) {
		this.eventHandlerResultFileList = eventHandlerResultFileList;
	}

	/**
	 * @return the expirationDate
	 */
	public Calendar getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(final Calendar expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the stylesheetResultFileListIterator
	 */
	public Iterator<Object> getEventHandlerResultFileListIterator() {
		return eventHandlerResultFileListIterator;
	}

	/**
	 * @param eventHandlerResultFileListIterator the stylesheetResultFileListIterator to set
	 */
	public void setEventHandlerResultFileListIterator(final Iterator<Object> eventHandlerResultFileListIterator) {
		this.eventHandlerResultFileListIterator = eventHandlerResultFileListIterator;
	}

	/**
	 * @return the eventRemoteSchedulerHost
	 */
	public String getEventRemoteSchedulerHost() {
		return eventRemoteSchedulerHost;
	}

	/**
	 * @param eventRemoteSchedulerHost the eventRemoteSchedulerHost to set
	 */
	public void setEventRemoteSchedulerHost(final String eventRemoteSchedulerHost) {
		this.eventRemoteSchedulerHost = eventRemoteSchedulerHost;
	}

	/**
	 * @return the eventRemoteSchedulerPort
	 */
	public String getEventRemoteSchedulerPort() {
		return eventRemoteSchedulerPort;
	}

	/**
	 * @param eventRemoteSchedulerPort the eventRemoteSchedulerPort to set
	 */
	public void setEventRemoteSchedulerPort(final String eventRemoteSchedulerPort) {
		this.eventRemoteSchedulerPort = eventRemoteSchedulerPort;
	}

	/**
	 * @return the eventExitCode
	 */
	public String getEventExitCode() {
		return eventExitCode;
	}

	/**
	 * @param eventExitCode the eventExitCode to set
	 */
	public void setEventExitCode(final String eventExitCode) {
		this.eventExitCode = eventExitCode;
	}

	/**
	 * @return the eventOrderId
	 */
	public String getEventOrderId() {
		return eventOrderId;
	}

	/**
	 * @param eventOrderId the eventOrderId to set
	 */
	public void setEventOrderId(final String eventOrderId) {
		this.eventOrderId = eventOrderId;
	}

	/**
	 * @return the eventCreated
	 */
	public String getEventCreated() {
		return eventCreated;
	}

	/**
	 * @param eventCreated the eventCreated to set
	 */
	public void setEventCreated(final String eventCreated) {
		this.eventCreated = eventCreated;
	}

	/**
	 * @return the expirationCycle
	 */
	public String getExpirationCycle() {
		return expirationCycle;
	}

	/**
	 * @param expirationCycle the expirationCycle to set
	 */
	public void setExpirationCycle(final String expirationCycle) {
		this.expirationCycle = expirationCycle;
	}

	//	private int getSocket_timeout() {
	//		return socket_timeout;
	//	}
	//
	private void setSocket_timeout(final String s) {
		try {
			socket_timeout = Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
			spooler_log.warn("Illegal value for parameter socket_timeout:" + s + ". Integer expected. Using default=5");
			socket_timeout = 5;
		}
	}
}
