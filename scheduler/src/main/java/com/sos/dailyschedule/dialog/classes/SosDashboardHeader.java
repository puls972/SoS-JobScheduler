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
package com.sos.dailyschedule.dialog.classes;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import com.sos.JSHelper.Basics.JSToolBox;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.sos.dashboard.globals.DashBoardConstants;
import com.sos.dialog.interfaces.ITableView;

/**
* \class SosDashboardHeader 
* 
* \brief SosDashboardHeader - 
* 
* \details
*
* \section SosDashboardHeader.java_intro_sec Introduction
*
* \section SosDashboardHeader.java_samples Some Samples
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
* \version 27.01.2012
* \see reference
*
* Created on 27.01.2012 16:04:42
 */
public class SosDashboardHeader extends JSToolBox {
	@SuppressWarnings("unused")
	private final String		conClassName	= "SosDashboardHeader";
	private static final String	EMPTY_STRING	= "";
	private Display				display;
	public Timer				refreshTimer;
	public Timer				inputTimer;
	private DateTime			fromDate		= null;
	private DateTime			toDate			= null;
	private CCombo				cbSchedulerId	= null;
	private ITableView			main			= null;
	private Text				searchField		= null;
	private int					refresh			= 0;
	private Composite			parent;
	private Text				refreshInterval;
	private Button				refreshButton;

	public Text getRefreshInterval() {
		return refreshInterval;
	}
	public class RefreshTask extends TimerTask {
		public void run() {
			if (display == null) {
				display = Display.getDefault();
			}
			display.syncExec(new Runnable() {
				public void run() {
			
					 main.getList();  
				};
			});
		}
	}
	public class InputTask extends TimerTask {
		public void run() {
			if (display == null) {
				display = Display.getDefault();
			}
			display.syncExec(new Runnable() {
				public void run() {
					if (!getSearchField().equals(EMPTY_STRING)) {
						main.actualizeList();
						inputTimer.cancel();
					}
				};
			});
		}
	}

	public SosDashboardHeader(Composite parent_, ITableView main_) {
		super(DashBoardConstants.conPropertiesFileName);
		refreshTimer = new Timer();
		inputTimer = new Timer();
		refreshTimer.schedule(new RefreshTask(), 1000, 60000);
		main = main_;
		parent = parent_;
		createHeader();
	}

	public int getIntValue(String s, int d) {
		try {
			return Integer.parseInt(s);
		}
		catch (NumberFormatException n) {
			return d;
		}
	}

	public void setRefresh(int refresh) {
	    if (refresh <= 0){
	        refresh = 60;
	    }
		this.refresh = refresh;
	}
	
	public void setRefresh(String refresh) {
	    setRefresh(getIntValue(refresh,60));
    }

	public void resetRefreshTimer() {
	    
		refreshTimer.cancel();
		refreshTimer = new Timer();
		refreshTimer.schedule(new RefreshTask(), refresh * 1000, refresh * 1000);
	}

	public void resetInputTimer() {
		inputTimer.cancel();
		inputTimer = new Timer();
		inputTimer.schedule(new InputTask(), 1 * 1000, 1 * 1000);
	}


	private void createHeader() {
		refreshButton = new Button(parent, SWT.NONE);
		refreshButton.setLayoutData(new GridData(74, SWT.DEFAULT));
		refreshButton.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_Refresh));
		refreshInterval = new Text(parent, SWT.RIGHT | SWT.BORDER);
		refresh = getIntValue(refreshInterval.getText(), 60);
		final GridData gd_refreshInterval = new GridData(35, SWT.DEFAULT);
		gd_refreshInterval.minimumWidth = 50;
		refreshInterval.setLayoutData(gd_refreshInterval);
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_SchedulerID));
		
		cbSchedulerId = new CCombo(parent, SWT.BORDER);
		GridData gd_combo = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_combo.widthHint = 120;
		gd_combo.minimumWidth = 120;
		cbSchedulerId.setLayoutData(gd_combo);
		Label lblVon = new Label(parent, SWT.NONE);
		lblVon.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVon.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_FROM));
		fromDate = new DateTime(parent, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		Label lblBis = new Label(parent, SWT.NONE);
		lblBis.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBis.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_TO));
		toDate = new DateTime(parent, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		searchField = new Text(parent, SWT.BORDER);
		searchField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	public DateTime getFromDate() {
		return fromDate;
	}

	public Date getFrom() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, fromDate.getDay());
		cal.set(Calendar.MONTH, fromDate.getMonth());
		cal.set(Calendar.YEAR, fromDate.getYear());
		return cal.getTime();
	}

	public Text getSearchField() {
		return searchField;
	}

	public DateTime getToDate() {
		return toDate;
	}

	public Date getTo() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, toDate.getDay());
		cal.set(Calendar.MONTH, toDate.getMonth());
		cal.set(Calendar.YEAR, toDate.getYear());
		return cal.getTime();
	}

	public CCombo getCbSchedulerId() {
		return cbSchedulerId;
	}
	
	public void setEnabled(boolean enabled){
		refreshButton.setEnabled(enabled);
		searchField.setEnabled(false);
		toDate.setEnabled(enabled);
		fromDate.setEnabled(enabled);
		cbSchedulerId.setEnabled(enabled);
		refreshInterval.setEnabled(enabled);
	}

	public Button getRefreshButton() {
		return refreshButton;
	}

	public void reset() {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(new Date()); //heute
  
        fromDate.setDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        toDate.setDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        cbSchedulerId.setText("");
		searchField.setText("");
	}

    public Timer getRefreshTimer() {
        return refreshTimer;
    }
}
