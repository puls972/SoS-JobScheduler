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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

 
/**
* \class SchedulerOrderStepHistoryCompoundKey 
* 
* \brief SchedulerOrderStepHistoryCompoundKey - 
* 
* \details
*
* \section SchedulerOrderStepHistoryCompoundKey.java_intro_sec Introduction
*
* \section SchedulerOrderStepHistoryCompoundKey.java_samples Some Samples
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
* \version 18.10.2011
* \see reference
*
* Created on 18.10.2011 10:43:49
 */

@Embeddable
public class SchedulerOrderStepHistoryCompoundKey implements Serializable {
		 /**
		 * 
		 */
		private static final long	serialVersionUID	= 1L;
		 private Long historyId;
	     private Long step;

	     public SchedulerOrderStepHistoryCompoundKey() {
	    	  }
	     
	     public SchedulerOrderStepHistoryCompoundKey(Long historyId, Long step) {
	    	    this.historyId = historyId;
	    	    this.step =step;
	    	  }
	   
	     @Column(name="HISTORY_ID",nullable=false) 
	     public Long getHistoryId() {
	    	 return historyId;
	     }
	     
	     @Column(name="HISTORY_ID",nullable=false) 
	     public void setHistoryId(Long historyId) {
	       this.historyId = historyId;
	     }
	     
	     @Column(name="STEP",nullable=false)
	     public Long getStep() {
	    	 return step;
	     }
	     
	     @Column(name="STEP",nullable=false)
	     public void setStep(Long step) {
	       this.step = step;
	     }
	     
	     public boolean equals(Object key) {
	    	   boolean result = true;
	    	   if (!(key instanceof SchedulerOrderStepHistoryCompoundKey)) {return false;}
	    	    Long otherHistoryId = ((SchedulerOrderStepHistoryCompoundKey)key).getHistoryId();
	    	    Long otherStep = ((SchedulerOrderStepHistoryCompoundKey)key).getStep();
	    	    if (step == null || otherStep == null) {
	    	      result = false;
	    	    }else {
	    	      result = step.equals(otherStep);
	    	    }
	    	    if (historyId == null || otherHistoryId == null) {
	    	      result = false;
	    	    }else {
	    	      result = historyId.equals(otherHistoryId);
	    	    }
	    	   return result;
	    	  }

	    	  public int hashCode() {
	    	    int code = 0;
	    	    if (step!=null) {code +=step;}
	    	    if (historyId!=null) {code +=historyId;}
	    	    return code;
	    	  }
	 }
