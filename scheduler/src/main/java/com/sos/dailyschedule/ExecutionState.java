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
package com.sos.dailyschedule;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
* \class ExecutionStatus 
* 
* \brief ExecutionStatus - 
* 
* \details
*
* \section ExecutionStatus.java_intro_sec Introduction
*
* \section ExecutionStatus.java_samples Some Samples
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
* \version 13.12.2011
* \see reference
*
* Created on 13.12.2011 14:24:09
 */

public class ExecutionState {

	public void setPeriodBegin(Date periodBegin) {
		this.periodBegin = periodBegin;
	}


	@SuppressWarnings("unused")
	private final String	conClassName	= "ExecutionStatus";
	private Date schedulePlanned=null;
	private Date scheduleExecuted=null;
	private Date periodBegin=null;
	private int tolerance=5;
	private int toleranceUnit=Calendar.MINUTE;
	

	public ExecutionState() {
		//
	}
	
 
	public boolean isLate(){
		if (scheduleExecuted == null) {
			return (schedulePlanned.before(new Date()));
		}else {
			if (periodStart()) {
				return false;
			}else {
				Date now = schedulePlanned;
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(now);
				calendar.add(toleranceUnit, tolerance);
		 		Date scheduleToleranz = calendar.getTime();		
	
		 		return (scheduleExecuted.after(scheduleToleranz));
			}
		}
	}
	
	public boolean singleStart() {
		return (periodBegin == null);
	}
	
	public boolean periodStart() {
		return (periodBegin != null);
	}


	public String getLate() {
		if (isLate()) {
			return "late";
		}else {
			return "";
		}
	}
	
	public String getExecutionLateState() {
		
		String status=getExecutionState();
		if (isLate()) {
			status = status + ":late";
		}

		return status;
 	}

	public String getExecutionState() {
		String status="*";

		if (scheduleExecuted == null) {
		   status = "waiting";
		}else {
		   status = "executed";
		}
		
		return status;
 	}


	public void setSchedulePlanned(Date schedulePlanned) {
		this.schedulePlanned = schedulePlanned;
	}


	public void setScheduleExecuted(Date scheduleExecuted) {
		this.scheduleExecuted = scheduleExecuted;
	}


	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}


	public void setToleranceUnit(int toleranceUnit) {
		this.toleranceUnit = toleranceUnit;
	}

}
