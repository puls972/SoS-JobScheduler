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
package sos.scheduler.editor.classes;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

import sos.scheduler.editor.app.IOUtils;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.conf.listeners.JobListener;

/**
 * \class FileNameSelector
 * 
 * \brief FileNameSelector -
 * 
 * \details
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
 * \see reference
 * 
 */

public class FolderNameSelector extends Text {

    @SuppressWarnings("unused") private final String conClassName = "FolderNameSelector";
    @SuppressWarnings("unused") private final String conSVNVersion = "$Id: FolderNameSelector.java 18447 2012-11-21 00:58:01Z kb $";

    private JobListener objDataProvider = null;
    public boolean flgIsFileFromLiveFolder = false;

    private String strFolderName = "";
    private Menu objContextMenu = null;
    private FormBaseClass objParentForm = null;
    private String strI18NKey = "";

    public FolderNameSelector(Composite pobjComposite, int arg1) {
        super(pobjComposite, arg1);

       addFocusListener(getFocusAdapter());
        setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
        addMouseListener(getMouseListener());
        addContextMenue();
    }

    public void setParentForm(final FormBaseClass pobjParentForm) {
        objParentForm = pobjParentForm;
    }

    public void setLayoutData(Object pobjLayoutData) {
        super.setLayoutData(pobjLayoutData);
    }

    public String getFolderName() {
        return strFolderName;
    }

    private void addContextMenue() {
        if (objContextMenu == null) {
            objContextMenu = getMenu();
            if (objContextMenu == null) {
                objContextMenu = new Menu(getShell(), SWT.POP_UP);
            }

            MenuItem item = null;

            item = new MenuItem(objContextMenu, SWT.PUSH);
            item.addListener(SWT.Selection, CopyToClipboardListener());
            item.setText("Copy to Clipboard");

            item = new MenuItem(objContextMenu, SWT.PUSH);
            item.addListener(SWT.Selection, OpenListener());
            item.setText("Open");

            setMenu(objContextMenu);
        }
    }

    public void setI18NKey(final String pstrI18NKey) {
        strI18NKey = pstrI18NKey;
        setToolTipText(Messages.getTooltip(strI18NKey));
    }

    public String getI18NKey() {
        return strI18NKey;
    }

    public void setDataProvider(final JobListener pobjDataProvider) {
        objDataProvider = pobjDataProvider;
        refreshContent();
    }

    // private Listener getSaveAsListener() {
    //
    // return new Listener() {
    // public void handleEvent(Event e) {
    //
    // }
    // };
    // }
    //
    private Listener CopyToClipboardListener() {

        return new Listener() {
            public void handleEvent(Event e) {
            }
        };
    }

    private Listener OpenListener() {

        return new Listener() {
            public void handleEvent(Event e) {
                objParentForm.showWaitCursor();
                String strLastFolderName = getText();
                if (strLastFolderName.trim().length() <= 0) {
                    strLastFolderName = Options.getLastIncludeFolderName();
                }

                String strT = openDirectory(strLastFolderName);
                objParentForm.restoreCursor();
                if (strT.trim().length() > 0) {
                    setText(strT);
                }
                else {
                    e.doit = false;
                }
                objParentForm.restoreCursor();
            }
        };
    }

    private FocusAdapter getFocusAdapter() {
        return new FocusAdapter() {
            @Override public void focusGained(final FocusEvent e) {
                selectAll();
                String strT = Messages.getTooltip(strI18NKey);
                objParentForm.setStatusLine(strT);
            }

            @Override public void focusLost(final FocusEvent e) {

            }
        };
    }

    private MouseListener getMouseListener() {
        return (new MouseListener() {
            @Override public void mouseUp(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override public void mouseDown(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override public void mouseDoubleClick(MouseEvent arg0) {
                objParentForm.showWaitCursor();
                String strT = "";
                strFolderName = strT;
                if (flgIsFileFromLiveFolder) {
                    String strLiveFolderName = Options.getSchedulerHotFolder();
                    strT = IOUtils.openDirectoryFile("*.*", strLiveFolderName);
                    if (objDataProvider.isNotEmpty(strT)) {
                        File objFile = new File(strLiveFolderName, strT);
                        setText(objFile.getName());
                    }
                }
                else {
                    String strLastFolderName = Options.getLastPropertyValue(strI18NKey);
                    strT = openDirectory(strLastFolderName);

                    if (objDataProvider.isNotEmpty(strT)) {
                        File objFile = new File(strT);
                        if (objFile.canRead()) {
                            setText(objFile.getAbsoluteFile().toString());
                            if (flgIsFileFromLiveFolder == false) {
                                Options.setLastPropertyValue(strI18NKey, strLastFolderName);
                            }
                            strFolderName = strT;
                            setText(objFile.getName());
                            // evtl. ein CallBack einbauen ...
                            // applyFile2Include();
                            objParentForm.setStatusLine(String.format("Directory '%1$s' selected", strT));
                        }
                        else {
                            objParentForm.MsgWarning(String.format("Directory '%1$s' is not readable", strT));
                        }
                    }
                }
                objParentForm.restoreCursor();
            }

        });

    }

    public String openDirectory(final String pstrDirectoryName) {

        String filename = "";
        DirectoryDialog fdialog = new DirectoryDialog(MainWindow.getSShell(), SWT.OPEN);

        fdialog.setFilterPath(pstrDirectoryName);

        filename = fdialog.open();
        if (filename == null || filename.trim().length() == 0) {
            return filename;
        }
        filename = filename.replaceAll("\\\\", "/");

        return filename;

    }

    public void refreshContent() {

    }

    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}
