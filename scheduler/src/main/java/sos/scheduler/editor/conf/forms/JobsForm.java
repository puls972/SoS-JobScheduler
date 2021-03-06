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
package sos.scheduler.editor.conf.forms;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;

import sos.scheduler.editor.app.ContextMenu;
import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.listeners.JobsListener;

public class JobsForm extends SOSJOEMessageCodes implements IUpdateLanguage {

	@SuppressWarnings("unused")
	private final String		conSVNVersion	= "$Id: JobsForm.java 18299 2012-10-22 15:36:57Z ur $";

	private static Logger		logger			= Logger.getLogger(JobsForm.class);
	@SuppressWarnings("unused")
	private final String		conClassName	= "JobsForm";

	private JobsListener		listener		= null;
	private Group				group			= null;
	private static Table		table			= null;
	private Button				bNewJob			= null;
	private Button				bRemoveJob		= null;
	private Label				label			= null;
	private SchedulerDom		dom				= null;
	private ISchedulerUpdate	update			= null;
	private Button				butAssistent	= null;
	private Button				newOrderJob		= null;
	
	public JobsForm(Composite parent, int style, SchedulerDom dom, ISchedulerUpdate update) {
		super(parent, style);
		try {
			this.dom = dom;
			this.update = update;
			listener = new JobsListener(dom, update);
			initialize();
			setToolTipText();
			listener.fillTable(table);
		}
		catch (Exception e) {
			try {
//				new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName(), e);
				new sos.scheduler.editor.app.ErrorLog(JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()), e);
			}
			catch (Exception ee) {
				// tu nichts
			}
//			System.err.println("..error in JobsForm.init() " + e.getMessage());
			System.err.println(JOE_E_0002.params("JobsForm.init()") + e.getMessage());
		}
	}

	private void initialize() {
		try {
			this.setLayout(new FillLayout());
			createGroup();
			setSize(new org.eclipse.swt.graphics.Point(656, 400));
		}
		catch (Exception e) {
			try {
//				new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName(), e);
				new sos.scheduler.editor.app.ErrorLog(JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()), e);
			}
			catch (Exception ee) {
				// tu nichts
			}
//			System.err.println("..error in JobsForm.initialize() " + e.getMessage());
			System.err.println(JOE_E_0002.params("JobsForm.initialize()") + e.getMessage());
		}
	}

	/**
	 * This method initializes group
	 */
	private void createGroup() {
		try {
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			
			group = JOE_G_JobsForm_Jobs.Control(new Group(this, SWT.NONE));
			group.setLayout(gridLayout);
			
			createTable();
			
			bNewJob = JOE_B_JobsForm_NewStandaloneJob.Control(new Button(group, SWT.NONE));
			bNewJob.setLayoutData(new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.BEGINNING, false, false));
			getShell().setDefaultButton(bNewJob);
			bNewJob.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					listener.newJob(table, false);
					bRemoveJob.setEnabled(true);
				}
			});

			newOrderJob = JOE_B_JobsForm_NewOrderJob.Control(new Button(group, SWT.NONE));
			newOrderJob.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					listener.newJob(table, true);
					bRemoveJob.setEnabled(true);
				}
			});
			newOrderJob.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));

			butAssistent = JOE_B_JobsForm_JobWizard.Control(new Button(group, SWT.NONE));
			butAssistent.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					try {
						Utils.startCursor(getShell());
						JobAssistentForm assitent = new JobAssistentForm(dom, update);
						assitent.startJobAssistant();
					}
					catch (Exception ex) {
						try {
//							new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() + " ; could not start assistent.", ex);
							new sos.scheduler.editor.app.ErrorLog(JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()) + " ; " + JOE_M_0040.label(), ex);
						}
						catch (Exception ee) {
							// tu nichts
						}
//						System.out.println("..error " + ex.getMessage());
						System.out.println(JOE_E_0002.params("createGroup()" ) + ex.getMessage());
					}
					finally {
						Utils.stopCursor(getShell());
					}
				}
			});
			butAssistent.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
			
			bRemoveJob = JOE_B_JobsForm_RemoveJob.Control(new Button(group, SWT.NONE));
			bRemoveJob.setEnabled(false);
			bRemoveJob.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

//					int c = MainWindow.message(getShell(), "Do you want remove the job?", SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					int c = MainWindow.message(getShell(), JOE_M_RemoveJob.label(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					if (c != SWT.YES)
						return;

					if (Utils.checkElement(table.getSelection()[0].getText(1), dom, sos.scheduler.editor.app.Editor.JOBS, null))// wird der
																																// Job
																																// woandes
																																// verwendet?
						bRemoveJob.setEnabled(listener.deleteJob(table));
				}
			});
			bRemoveJob.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));

			label = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
			label.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false));
//			label.setText("Label");
		}
		catch (Exception e) {
			try {
//				new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName(), e);
				new sos.scheduler.editor.app.ErrorLog(JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()), e);
			}
			catch (Exception ee) {
				// tu nichts
			}
//			System.err.println("..error in JobsForm.createGroup() " + e.getMessage());
			System.err.println(JOE_E_0002.params("JobsForm.createGroup()" ) + e.getMessage());
		}
	}

	/**
	 * This method initializes table
	 */
	private void createTable() {
		try {
			table = JOE_Tbl_JobsForm_Table.Control(new Table(group, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK));
			table.addMouseListener(new MouseAdapter() {
				public void mouseDoubleClick(final MouseEvent e) {
					if (table.getSelectionCount() > 0)
						ContextMenu.goTo(table.getSelection()[0].getText(1), dom, Editor.JOB);
				}
			});
			table.setHeaderVisible(true);
			table.setLayoutData(new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.FILL, true, true, 1, 5));
			table.setLinesVisible(true);
			
			TableColumn tableColumn5 = JOE_TCl_JobsForm_Disabled.Control(new TableColumn(table, SWT.NONE));
			tableColumn5.setWidth(60);
			
			table.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					if (Utils.isElementEnabled("job", dom, (Element) e.item.getData())) {
						bRemoveJob.setEnabled(true);
					}
					else {
						bRemoveJob.setEnabled(false);
						return;
					}
					if (e.detail == SWT.CHECK) {
						TableItem item = (TableItem) e.item;
			//	if (!listener.hasJobComment((Element) item.getData())) {
							listener.setJobEnabled((Element) item.getData(), !item.getChecked());
				//}
				/*else {
							MainWindow.message(Messages.getString("MainListener.cannotDisable"), SWT.ICON_INFORMATION | SWT.OK);
							item.setChecked(false);
						}
						*/
					}
				}
			});
 

			TableColumn tableColumn = JOE_TCl_JobsForm_Name.Control(new TableColumn(table, SWT.NONE));
			tableColumn.setWidth(100);
			
			TableColumn tableColumn1 = JOE_TCl_JobsForm_Title.Control(new TableColumn(table, SWT.NONE));
			tableColumn1.setWidth(200);
			
			TableColumn tableColumn2 = JOE_TCl_JobsForm_SchedulerID.Control(new TableColumn(table, SWT.NONE));
			tableColumn2.setWidth(100);
			
			TableColumn tableColumn3 = JOE_TCl_JobsForm_ProcessClass.Control(new TableColumn(table, SWT.NONE));
			tableColumn3.setWidth(100);
			
			TableColumn tableColumn4 = JOE_TCl_JobsForm_Order.Control(new TableColumn(table, SWT.NONE));
			tableColumn4.setWidth(40);
		}
		catch (Exception e) {
			try {
//				new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName(), e);
				new sos.scheduler.editor.app.ErrorLog(JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()), e);
			}
			catch (Exception ee) {
				// tu nichts
			}
//			System.err.println("..error in JobsForm.createTable() " + e.getMessage());
			System.err.println(JOE_E_0002.params("JobsForm.createTable() ") + e.getMessage());
		}
	}

	public void setToolTipText() {
//
	}

	public static Table getTable() {
		return table;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
