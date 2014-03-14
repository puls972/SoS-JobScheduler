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
package com.sos.jobnet.db;

import com.sos.hibernate.classes.DbItem;
import com.sos.hibernate.classes.SOSHibernateFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
/**
* \class JobnetPlanFilter 
* 
* \brief JobnetPlanFilter - 
* 
* \details
*
* \section JobnetPlanFilter.java_intro_sec Introduction
*
* \section JobnetPlanFilter.java_samples Some Samples
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
* \author Uwe Risse
* \version 14.12.2011
* \see reference
*
* Created on 14.12.2011 13:53:37
 */

public class JobNetPlanFilter extends SOSHibernateFilter implements com.sos.hibernate.interfaces.ISOSHibernateFilter {
//public class JobNetPlanFilter extends SOSHibernateFilter implements com.sos.hibernate.interfaces.ISOSHibernateFilter {


	@SuppressWarnings("unused")
	private final String	conClassName	= "JobnetPlanFilter";
	public  final String	conSVNVersion	= "$Id: JobNetPlanFilter.java 21003 2013-09-06 08:13:52Z ss $";
	private String dateFormat         = "yyyy-MM-dd HH:mm:ss";
	private String uuid;
    private Long  planId;
	private Long nodeId;
	private Long subnetId;
	private Long connectorId;
	private Date plannedStartTimeFrom;
	private Date plannedStartTimeTo;
	private Date startTimeFrom;
	private Date startTimeTo;
    private String node;
    private String nodeType;
    private Long taskId;
 	private String jobnet;
	private Integer status;
	private Integer statusWaiter;
	private Integer statusRunner;
	private Integer statusDispatcher;
	private Boolean isWaiterSkipped;
	private Boolean isRunnerSkipped;
    private Boolean isDispatcherSkipped;
    private Boolean isRunnerOnDemand;
	
	private Boolean bootstrap;
	private String schedulerId="";
	private String searchField;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getNode() {
		return node;
	}

	

	public Long getNodeId() {
		return nodeId;
	}


	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Long getSubnetId() {
		return subnetId;
	}


	public void setSubnetId(Long subnetId) {
		this.subnetId = subnetId;
	}

	public Long getConnectorId() {
		return connectorId;
	}


	public void setConnectorId(Long connectorId) {
		this.connectorId = connectorId;
	}


	public Boolean getIsWaiterSkipped() {
		return isWaiterSkipped;
	}


	public void setIsWaiterSkipped(Boolean isWaiterSkipped) {
		this.isWaiterSkipped = isWaiterSkipped;
	}


	public Boolean getIsRunnerSkipped() {
		return isRunnerSkipped;
	}


	public void setIsRunnerSkipped(Boolean isRunnerSkipped) {
		this.isRunnerSkipped = isRunnerSkipped;
	}


	public Boolean getIsDispatcherSkipped() {
		return isDispatcherSkipped;
	}
	
	public void setIsDispatcherSkipped(Boolean isDispatcherSkipped) {
        this.isDispatcherSkipped = isDispatcherSkipped;
    }
	
	public Boolean getIsRunnerOnDemand() {
        return isRunnerOnDemand;
    }

	public void setIsRunnerOnDemand(Boolean isRunnerOnDemand) {
		this.isRunnerOnDemand = isRunnerOnDemand;
	}
	
	public void setNode(String node) {
		this.node = node;
	}

	public String getJobnet() {
		return jobnet;
	}

	public void setJobnet(String jobnet) {
		this.jobnet = jobnet;
	}

	public Integer getStatusWaiter() {
		return statusWaiter;
	}

	public void setStatusWaiter(Integer statusWaiter) {
		this.statusWaiter = statusWaiter;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatusRunner() {
		return statusRunner;
	}

	public void setStatusRunner(Integer statusRunner) {
		this.statusRunner = statusRunner;
	}

	public Integer getStatusDispatcher() {
		return statusDispatcher;
	}

	public void setStatusDispatcher(Integer statusDispatcher) {
		this.statusDispatcher = statusDispatcher;
	}

	public Boolean getBootstrap() {
		return bootstrap;
	}

	public void setBootstrap(Boolean bootstrap) {
		this.bootstrap = bootstrap;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getUuid() {
		return uuid;
	}


	public String getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(String schedulerId) {
		this.schedulerId = schedulerId;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public Date getPlannedStartTimeFrom() {
		return plannedStartTimeFrom;
	}

	public Date getPlannedStartTimeTo() {
		return plannedStartTimeTo;
	}

	public Date getStartTimeFrom() {
		return startTimeFrom;
	}

	public Date getStartTimeTo() {
		return startTimeTo;
	}

	public void setPlannedStartTimeFrom(Date plannedStartTimeFrom) {
		this.plannedStartTimeFrom = plannedStartTimeFrom;
	}

	public void setPlannedStartTimeFrom(String plannedStartTimeFrom) throws ParseException {
		if (plannedStartTimeFrom.equals("")) {
			this.plannedStartTimeFrom = null;
		}
		else {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			Date d = formatter.parse(plannedStartTimeFrom);
			setPlannedStartTimeFrom(d);
		  }
		}

	public void setPlannedFrom(String plannedStartTimeFrom, String dateFormat) throws ParseException {
		this.dateFormat = dateFormat;
		setPlannedStartTimeFrom(plannedStartTimeFrom);
		}
	
	public void setPlannedStartTimeTo(Date plannedStartTimeTo) {
		this.plannedStartTimeTo  = plannedStartTimeTo;
	}

	public void setPlannedStartTimeTo(String plannedStartTimeTo) throws ParseException {
		if (plannedStartTimeTo.equals("")) {
			this.plannedStartTimeTo = null;
		}
		else {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			Date d = formatter.parse(plannedStartTimeTo);
			setPlannedStartTimeFrom(d);
		  }
		}

	public void setPlannedStartTimeTo(String plannedStartTimeTo, String dateFormat) throws ParseException {
		this.dateFormat = dateFormat;
		setPlannedStartTimeTo(plannedStartTimeTo);
		}


	public void setStartTimeTo(Date startTimeTo) {
		this.startTimeTo  = startTimeTo;
	}

	public void setStartTimeTo(String startTimeTo) throws ParseException {
		if (startTimeTo.equals("")) {
			this.startTimeTo = null;
		}
		else {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			Date d = formatter.parse(startTimeTo);
			setPlannedStartTimeFrom(d);
		  }
		}

	public void setstartTimeTo(String startTimeTo, String dateFormat) throws ParseException {
		this.dateFormat = dateFormat;
		setStartTimeTo(startTimeTo);
		}

	
	public void setStartTimeFrom(Date startTimeFrom) {
		this.startTimeFrom  = startTimeFrom;
	}

	public void setStartTimeFrom(String startTimeFrom) throws ParseException {
		if (startTimeFrom.equals("")) {
			this.startTimeFrom = null;
		}
		else {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			Date d = formatter.parse(startTimeFrom);
			setPlannedStartTimeFrom(d);
		  }
		}

	public void setstartTimeFrom(String startTimeFrom, String dateFormat) throws ParseException {
		this.dateFormat = dateFormat;
		setStartTimeFrom(startTimeFrom);
		}

	 

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchFiled(String searchField) {
		this.searchField = searchField;
	}
	

 

	 

	@Override
	public String getTitle() {
 		return "";
	}

	@Override
	public boolean isFiltered(DbItem h) {
		return false;
 	}



    public String getNodeType() {
        return nodeType;
    }



    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }



    public Long getTaskId() {
        return taskId;
    }



    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

	 
	
	
}