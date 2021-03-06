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

/**
* \class TabLogItem 
* 
* \brief TabLogItem - 
* 
* \details
*
* \section TabLogItem.java_intro_sec Introduction
*
* \section TabLogItem.java_samples Some Samples
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
* \version 25.01.2012
* \see reference
*
* Created on 25.01.2012 11:47:41
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.sos.hibernate.classes.DbItem;

public class SosTabLogItem extends CTabItem {

	private Display		parentDisplay;
	private Composite	composite;
	private GridLayout	layout;
	private GridData	data;
	private Text		log;
	private Table		sourceTable;
	private String		sourceItemId;

	public SosTabLogItem(String caption, CTabFolder parent) {

		super(parent, SWT.NONE);
		setText(caption);
		parentDisplay = parent.getDisplay();

		composite = new Composite(parent, SWT.NONE);

		layout = new GridLayout(2, false);
		composite.setLayout(layout);

		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		log = new Text(composite, SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		log.setEditable(false);
		log.setBackground(new Color(parentDisplay, 255, 255, 255));
		log.setLayoutData(data);

		this.setControl(composite);

	}

	public void setSelection() {
		if (sourceTable != null) {
			int l = sourceTable.getItemCount();
			for (int i = 0; i < l; i++) {
				DbItem d = (DbItem) sourceTable.getItem(i).getData();
				String s = "";
				if (d.getLogId() != null) {
					s = d.getIdentifier();
				}
				if (sourceItemId == s) {
					sourceTable.setSelection(i);
					return;
				}
			}
		}
	}

	public void clearLog() {
		this.setText("Log");
		log.setText("");
	}

	public void addLog(Table table, String caption, String logContent) {
  	    this.setText(caption);
		if (logContent != null) {
			log.setText(logContent);
			sourceTable = table;
			DbItem d = (DbItem) table.getItem(table.getSelectionIndex()).getData();
			sourceItemId = d.getIdentifier();
		}
	}

}
