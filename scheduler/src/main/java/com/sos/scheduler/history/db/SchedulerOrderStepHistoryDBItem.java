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
package com.sos.scheduler.history.db;

//com.sos.scheduler.history

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

import com.sos.hibernate.classes.DbItem;

/**
 * \class SchedulerOrderStepHistoryDBItem
 * 
 * \brief SchedulerOrderStepHistoryDBItem -
 * 
 * \details
 * 
 * \section SchedulerOrderStepHistoryDBItem.java_intro_sec Introduction
 * 
 * \section SchedulerOrderStepHistoryDBItem.java_samples Some Samples
 * 
 * \code .... code goes here ... \endcode
 * 
 * <p style="text-align:center">
 * <br />
 * --------------------------------------------------------------------------- <br />
 * APL/Software GmbH - Berlin <br />
 * ##### generated by ClaviusXPress (http://www.sos-berlin.com) ######### <br />
 * ---------------------------------------------------------------------------
 * </p>
 * \author Uwe Risse \version 05.10.2011 \see reference
 * 
 * Created on 05.10.2011 11:42:58
 */

@Entity
@Table(name = "SCHEDULER_ORDER_STEP_HISTORY")
public class SchedulerOrderStepHistoryDBItem extends DbItem implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SchedulerOrderStepHistoryCompoundKey id;

 
	private Long taskId;
	private String state;
	private Date startTime;
	private Date endTime;
	private Boolean error;
	private String errorText;
	private String errorCode;
	private SchedulerOrderHistoryDBItem schedulerOrderHistoryDBItem;

	public SchedulerOrderStepHistoryDBItem() {
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "`HISTORY_ID`", insertable = false, updatable = false)
	public SchedulerOrderHistoryDBItem getSchedulerOrderHistoryDBItem() {
		return this.schedulerOrderHistoryDBItem;
	}

	public void setSchedulerOrderHistoryDBItem(
			SchedulerOrderHistoryDBItem schedulerOrderHistoryDBItem) {
		this.schedulerOrderHistoryDBItem = schedulerOrderHistoryDBItem;
	}

	@Id
	public SchedulerOrderStepHistoryCompoundKey getId() {
		return id;
	}

	public void setId(SchedulerOrderStepHistoryCompoundKey id) {
		this.id = id;
	}

	 

	@Column(name = "`TASK_ID`", nullable = false)
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@Column(name = "`TASK_ID`", nullable = false)
	public Long getTaskId() {
		return taskId;
	}

	@Column(name = "`STATE`", nullable = true)
	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "`STATE`", nullable = true)
	public String getState() {
		return state;
	}

	@Column(name = "`START_TIME`", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartTime() {
		return startTime;
	}

	@Column(name = "`START_TIME`", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "`END_TIME`", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime() {
		return endTime;
	}

	@Column(name = "`END_TIME`", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "`ERROR`", nullable = true)
	public Boolean isError() {
		return error;
	}

	@Column(name = "`ERROR`", nullable = true)
	public void setError(Boolean error) {
		this.error = error;
	}

	@Column(name = "`ERROR_CODE`", nullable = true)
	public String getErrorCode() {
		return errorCode;
	}

	@Column(name = "`ERROR_CODE`", nullable = true)
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "`ERROR_TEXT`", nullable = true)
	public String getErrorText() {
		return errorText;
	}

	@Column(name = "`ERROR_TEXT`", nullable = true)
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	@Transient
	public String getStartTimeIso() {
		if (this.getStartTime() == null) {
			return "";
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			String startTimeIso = formatter.format(this.getStartTime());
			return startTimeIso;
		}
	}

	@Transient
	public String getEndTimeIso() {
		if (this.getEndTime() == null) {
			return "";
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			String endTimeIso = formatter.format(this.getEndTime());
			return endTimeIso;
		}
	}

	@Transient
	public Long getLogId() {
		return this.getTaskId();
	}

	@Transient
	public String getIdentifier() {
		return getTitle() + getLogId().toString();
	}

}
