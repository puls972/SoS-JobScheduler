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
package sos.scheduler.editor.conf.container;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import sos.scheduler.editor.app.SOSJOEMessageCodes;

import sos.scheduler.editor.classes.FormBaseClass;
import sos.scheduler.editor.conf.listeners.JobListener;

public class JobEmailSettings extends FormBaseClass {

	private Combo			mailOnDelayAfterError	= null;
	private Text			mailCC					= null;
	private Text			mailBCC					= null;
	private Combo			mailOnError				= null;
	private Combo			mailOnWarning			= null;
	private Combo			mailOnSuccess			= null;
	private Combo			mailOnProcess			= null;
	private Text			mailTo					= null;

	private String[]		comboItems			= { "yes", "no", "" };

	@SuppressWarnings("unused")
	private final String	conClassName			= "JobEmailSettings";
	@SuppressWarnings("unused")
	private final String	conSVNVersion			= "$Id: JobEmailSettings.java 18346 2012-11-02 14:07:36Z ur $";

	// private boolean init = true;
	private JobListener		objJobDataProvider		= null;

	public JobEmailSettings(Composite pParentComposite, JobListener pobjJobDataProvider) {
		super(pParentComposite, pobjJobDataProvider);
		objJobDataProvider = pobjJobDataProvider;

		// init = true;
		createGroup();
		initForm();
		// init = false;
	}

	public void apply() {
		// if (isUnsaved())
		// addParam();
	}

	public boolean isUnsaved() {
		return false;
	}

	public void refreshContent() {
	}

	private void createGroup() {

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;

		GridLayout gridLayout4EMailGroup = new GridLayout();
		gridLayout4EMailGroup.marginRight = 5;
		gridLayout4EMailGroup.marginLeft = 5;
		gridLayout4EMailGroup.marginTop = 5;
		gridLayout4EMailGroup.numColumns = 2;

		Group group4EMail = SOSJOEMessageCodes.JOE_G_JobEmailSettings_Notifications.Control(new Group(objParent, SWT.NONE));
		group4EMail.setLayout(gridLayout4EMailGroup);
		group4EMail.setLayoutData(gridData);

		@SuppressWarnings("unused")
		Label labelMailOnError = SOSJOEMessageCodes.JOE_L_MailForm_MailOnError.Control(new Label(group4EMail, SWT.NONE));

//		mailOnError = SOSJOEMessageCodes.JOE_Cbo_MailForm_MailOnError.Control(new Combo(group4EMail, intComboBoxStyle));
		mailOnError = SOSJOEMessageCodes.JOE_Cbo_MailForm_MailOnError.Control(new Combo(group4EMail, SWT.READ_ONLY));
		// mailOnError.setItems(new String[]{"yes", "no", ""});
		mailOnError.setItems(comboItems);
		mailOnError.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				mailOnDelayAfterError.setEnabled(mailOnError.getText().equals("yes") || mailOnWarning.getText().equals("yes"));
				objJobDataProvider.setValue("mail_on_error", mailOnError.getText(), "no");
			}
		});
		GridData gd_mailOnError = new GridData(GridData.BEGINNING, GridData.CENTER, true, false);
		gd_mailOnError.minimumWidth = 150;
		mailOnError.setLayoutData(gd_mailOnError);

		@SuppressWarnings("unused")
		Label labelMailOnWarning = SOSJOEMessageCodes.JOE_L_MailForm_MailOnWarning.Control(new Label(group4EMail, SWT.NONE));

//		mailOnWarning = SOSJOEMessageCodes.JOE_Cbo_MailForm_MailOnWarning.Control(new Combo(group4EMail, intComboBoxStyle));
		mailOnWarning = SOSJOEMessageCodes.JOE_Cbo_MailForm_MailOnWarning.Control(new Combo(group4EMail, SWT.READ_ONLY));
		// mailOnWarning.setItems(new String[]{"yes", "no", ""});
		mailOnWarning.setItems(comboItems);
		mailOnWarning.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				mailOnDelayAfterError.setEnabled(mailOnWarning.getText().equals("yes") || mailOnWarning.getText().equals("yes"));
				objJobDataProvider.setValue("mail_on_warning", mailOnWarning.getText(), "no");
			}
		});
		GridData gd_mailOnWarning = new GridData(GridData.BEGINNING, GridData.CENTER, true, false);
		gd_mailOnWarning.minimumWidth = 150;
		mailOnWarning.setLayoutData(gd_mailOnWarning);

		@SuppressWarnings("unused")
		Label label3 = SOSJOEMessageCodes.JOE_L_MailForm_MailOnSuccess.Control(new Label(group4EMail, SWT.NONE));

//		mailOnSuccess = SOSJOEMessageCodes.JOE_Cbo_MailForm_MailOnSuccess.Control(new Combo(group4EMail, intComboBoxStyle));
		mailOnSuccess = SOSJOEMessageCodes.JOE_Cbo_MailForm_MailOnSuccess.Control(new Combo(group4EMail, SWT.READ_ONLY));
		// mailOnSuccess.setItems(new String[]{"yes", "no", ""});
		mailOnSuccess.setItems(comboItems);
		mailOnSuccess.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				objJobDataProvider.setValue("mail_on_success", mailOnSuccess.getText(), "no");
			}
		});
		GridData gd_mailOnSuccess = new GridData(GridData.BEGINNING, GridData.CENTER, true, false);
		gd_mailOnSuccess.minimumWidth = 150;
		mailOnSuccess.setLayoutData(gd_mailOnSuccess);

		@SuppressWarnings("unused")
		final Label mailOnProcessLabel = SOSJOEMessageCodes.JOE_L_MailForm_MailOnProcess.Control(new Label(group4EMail, SWT.NONE));

//		mailOnProcess = SOSJOEMessageCodes.JOE_Cbo_MailForm_MailOnProcess.Control(new Combo(group4EMail, intComboBoxStyle));
		mailOnProcess = SOSJOEMessageCodes.JOE_Cbo_MailForm_MailOnProcess.Control(new Combo(group4EMail, SWT.READ_ONLY));
		// mailOnProcess.setItems(new String[]{"yes", "no", ""});
		mailOnProcess.setItems(comboItems);
		mailOnProcess.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				objJobDataProvider.setValue("mail_on_process", mailOnProcess.getText(), "no");
			}
		});
		GridData gd_mailOnProcess = new GridData(GridData.BEGINNING, GridData.CENTER, true, false);
		gd_mailOnProcess.minimumWidth = 150;
		mailOnProcess.setLayoutData(gd_mailOnProcess);

		@SuppressWarnings("unused")
		final Label mailOnDelayLabel = SOSJOEMessageCodes.JOE_L_MailForm_MailOnDelayAfterError.Control(new Label(group4EMail, SWT.NONE));

//		mailOnDelayAfterError = SOSJOEMessageCodes.JOE_Cbo_MailForm_MailOnDelayAfterError.Control(new Combo(group4EMail, intComboBoxStyle));
		mailOnDelayAfterError = SOSJOEMessageCodes.JOE_Cbo_MailForm_MailOnDelayAfterError.Control(new Combo(group4EMail, SWT.READ_ONLY));
		mailOnDelayAfterError.setItems(new String[] { "all", "first_only", "last_only", "first_and_last_only", "" });
		mailOnDelayAfterError.setEnabled(mailOnError.getText().equals("yes") || mailOnWarning.getText().equals("yes"));
		mailOnDelayAfterError.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				objJobDataProvider.setValue("mail_on_delay_after_error", mailOnDelayAfterError.getText(), "no");
			}
		});
		mailOnDelayAfterError.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));

		final Label ddddLabel = new Label(group4EMail, SWT.HORIZONTAL | SWT.SEPARATOR);
		final GridData gridData_1 = new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1);
		gridData_1.heightHint = 8;
		ddddLabel.setLayoutData(gridData_1);

		@SuppressWarnings("unused")
		final Label mailToLabel = SOSJOEMessageCodes.JOE_L_MailForm_MailTo.Control(new Label(group4EMail, SWT.NONE));

		mailTo = SOSJOEMessageCodes.JOE_T_MailForm_MailTo.Control(new Text(group4EMail, SWT.BORDER));
		mailTo.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				objJobDataProvider.setValue("log_mail_to", mailTo.getText());
			}
		});
		mailTo.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));

		@SuppressWarnings("unused")
		final Label mailCcLabel = SOSJOEMessageCodes.JOE_L_MailForm_MailCC.Control(new Label(group4EMail, SWT.NONE));

		mailCC = SOSJOEMessageCodes.JOE_T_MailForm_MailCC.Control(new Text(group4EMail, SWT.BORDER));
		mailCC.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				objJobDataProvider.setValue("log_mail_cc", mailCC.getText());
			}
		});
		final GridData gridData_2 = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData_2.minimumWidth = 60;
		mailCC.setLayoutData(gridData_2);

		@SuppressWarnings("unused")
		final Label mailBccLabel = SOSJOEMessageCodes.JOE_L_MailForm_MailBCC.Control(new Label(group4EMail, SWT.NONE));

		gridData = new GridData(GridData.FILL, GridData.CENTER, false, false);
		gridData.minimumWidth = 60;
		mailBCC = SOSJOEMessageCodes.JOE_T_MailForm_MailBCC.Control(new Text(group4EMail, SWT.BORDER));
		mailBCC.setLayoutData(gridData);
		mailBCC.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				objJobDataProvider.setValue("log_mail_bcc", mailBCC.getText());
			}
		});

		final Label label_1 = new Label(group4EMail, SWT.HORIZONTAL | SWT.SEPARATOR);
		final GridData gridData_3 = new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1);
		gridData_3.heightHint = 8;
		label_1.setLayoutData(gridData_3);

		objParent.layout();
	}

	private void initForm() {

		mailOnError.setText(objJobDataProvider.getValue("mail_on_error"));
		mailOnWarning.setText(objJobDataProvider.getValue("mail_on_warning"));
		mailOnSuccess.setText(objJobDataProvider.getValue("mail_on_success"));
		mailOnProcess.setText(objJobDataProvider.getValue("mail_on_process"));
		mailOnDelayAfterError.setText(objJobDataProvider.getValue("mail_on_delay_after_error"));

		mailOnDelayAfterError.setEnabled(mailOnError.getText().equals("yes") || mailOnWarning.getText().equals("yes"));

		mailTo.setText(objJobDataProvider.getValue("log_mail_to"));
		mailCC.setText(objJobDataProvider.getValue("log_mail_cc"));
		mailBCC.setText(objJobDataProvider.getValue("log_mail_bcc"));

	}
}
