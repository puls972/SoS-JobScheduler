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
package com.sos.jobnet.classes;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import org.eclipse.swt.custom.TableEditor;

import sos.scheduler.editor.app.ResourceManager;

import com.sos.jobnet.db.JobNetPlanDBItem;

import com.sos.dialog.classes.SOSTableItem;
import com.sos.dialog.components.SOSTableColumn;
import com.sos.hibernate.classes.DbItem;
import com.sos.hibernate.interfaces.ISOSTableItem;

/**
* \class JobNetTableItem 
* 
* \brief JobNetTableItem - 
* 
* \details
*
* \section JobNetTableItem.java_intro_sec Introduction
*
* \section JobNetTableItem.java_samples Some Samples
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
* Created on 14.12.2011 13:51:13
 */

public class SosJobNetPlanTableItem extends SOSTableItem implements ISOSTableItem {
    private JobNetPlanDBItem jobNetPlanDBItem = null;
    private String[]         textBuffer       = null;
    private static Logger    logger           = Logger.getLogger(SosJobNetPlanTableItem.class);

    private TableEditor      editorDispatcherSkipped;
    private TableEditor      editorRunnerSkipped;
    private TableEditor      editorWaiterSkipped;

    private Button           checkButtonWaiterSkipped;
    private Button           checkButtonRunnerSkipped;
    private Button           checkButtonDispatcherSkipped;

    public SosJobNetPlanTableItem(Table arg0, int arg1) {
        super(arg0, arg1);
     //   createColumnEditors();
    }

    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

    public DbItem getData() {
        return (JobNetPlanDBItem) super.getData();
    }

    public void setDBItem(DbItem d) {
        jobNetPlanDBItem = (JobNetPlanDBItem) d;
        this.setData(d);
    }

    public void setColor() {
        org.eclipse.swt.graphics.Color magenta = Display.getDefault().getSystemColor(SWT.COLOR_DARK_MAGENTA);
        org.eclipse.swt.graphics.Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
        org.eclipse.swt.graphics.Color blue = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
        org.eclipse.swt.graphics.Color white = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
        org.eclipse.swt.graphics.Color gray = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
        org.eclipse.swt.graphics.Color black = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
        org.eclipse.swt.graphics.Color yellow = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
        org.eclipse.swt.graphics.Color darkRed = Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED);
        org.eclipse.swt.graphics.Color green = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
        org.eclipse.swt.graphics.Color darkGreen = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN);
        org.eclipse.swt.graphics.Color darkYellow = Display.getDefault().getSystemColor(SWT.COLOR_DARK_YELLOW);
        org.eclipse.swt.graphics.Color darkBlue = Display.getDefault().getSystemColor(SWT.COLOR_DARK_BLUE);
        org.eclipse.swt.graphics.Color cyan = Display.getDefault().getSystemColor(SWT.COLOR_CYAN);
        org.eclipse.swt.graphics.Color darkCyan = Display.getDefault().getSystemColor(SWT.COLOR_DARK_CYAN);
        org.eclipse.swt.graphics.Color darkGray = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);

        this.setBackground(white);

        if (jobNetPlanDBItem.isJob()) {
            this.setBackground(0, gray);
            this.setBackground(2, gray);
        }

        if (jobNetPlanDBItem.isSubnet()) {
            this.setBackground(0, yellow);
            this.setBackground(2, yellow);
        }

        if (jobNetPlanDBItem.isConnector()) {
            this.setBackground(0, blue);
            this.setBackground(2, blue);
            this.setForeground(2, white);
        }

        if (jobNetPlanDBItem.getBootstrap()) {
            this.setBackground(0, green);
            this.setBackground(2, green);
            this.setForeground(2, white);
        }

        if (jobNetPlanDBItem.getIsRunnerOnDemand()) {
            this.setBackground(9, yellow);
        }

        if (jobNetPlanDBItem.getIsWaiterSkipped()) {
            this.setBackground(10, yellow);
        }

        if (jobNetPlanDBItem.getIsRunnerSkipped()) {
            this.setBackground(11, yellow);
        }
        if (jobNetPlanDBItem.getIsDispatcherSkipped()) {
            this.setBackground(12, yellow);
        }
        
        if (jobNetPlanDBItem.getExitCode() != 0) {
            this.setBackground(0, red);
            this.setBackground(4, red);
            this.setForeground(4, white);
        }


        colorSave();

    }

    private void createColumnEditors() {

        
            editorWaiterSkipped = new TableEditor(this.getParent());
            checkButtonWaiterSkipped = new Button(this.getParent(), SWT.CHECK);
            checkButtonWaiterSkipped.pack();

            editorWaiterSkipped.minimumWidth = checkButtonWaiterSkipped.getSize().x;
            editorWaiterSkipped.horizontalAlignment = SWT.CENTER;
            editorWaiterSkipped.setEditor(checkButtonWaiterSkipped, this, 8);

            editorRunnerSkipped = new TableEditor(this.getParent());
            checkButtonRunnerSkipped = new Button(this.getParent(), SWT.CHECK);
            checkButtonRunnerSkipped.pack();

            editorRunnerSkipped.minimumWidth = checkButtonRunnerSkipped.getSize().x;
            editorRunnerSkipped.horizontalAlignment = SWT.CENTER;
            editorRunnerSkipped.setEditor(checkButtonRunnerSkipped, this, 9);

            editorDispatcherSkipped = new TableEditor(this.getParent());
            checkButtonDispatcherSkipped = new Button(this.getParent(), SWT.CHECK);
            checkButtonDispatcherSkipped.pack();

            editorDispatcherSkipped.minimumWidth = checkButtonDispatcherSkipped.getSize().x;
            editorDispatcherSkipped.horizontalAlignment = SWT.CENTER;
            editorDispatcherSkipped.setEditor(checkButtonDispatcherSkipped, this, 10);
        

    }

    private void setImage(int column, Boolean checked) {
       
        
        Image checkedImage =  ResourceManager.getImageFromResource("/sos/scheduler/editor/icons/config.gif");
        Image uncheckedImage =  ResourceManager.getImageFromResource("/sos/scheduler/editor/icons/thin_close_view.gif");
        
        if (checked) {
            this.setImage(column, checkedImage);
        }else {
            this.setImage(column, uncheckedImage);
        }

    }
    public void setColumns() {
       
        JobNetPlanDBItem d = jobNetPlanDBItem;

        logger.debug("...creating tableItem: " + d.getPlanId() + ":" + getParent().getItemCount());

        textBuffer = new String[] { " ", 
                d.getUuid(), 
                d.getNodeType(), 
                String.valueOf(d.getJobNetNodeDBItem().getNode()), 
                String.valueOf(d.getExitCode()), 
                d.getExitMessage(), 
                String.valueOf(d.getStatusWaiter()), 
                String.valueOf(d.getStatusRunner()),
                String.valueOf(d.getStatusDispatcher()),
                "",
                "",
                "", 
                "",

                String.valueOf(d.getState()), 
                d.getPlannedStartTimeFormated(),
                d.getStartTimeFormated(), 
                d.getEndTimeFormated(), 
                d.getMessage() };
        this.setText(textBuffer);
        
        setImage(9,d.getIsRunnerOnDemand());
        setImage(10,d.getIsWaiterSkipped());
        setImage(11,d.getIsRunnerSkipped());
        setImage(12,d.getIsDispatcherSkipped());
        

         
        
        /* 
         *  checkButtonWaiterSkipped.setSelection(d.getIsWaiterSkipped());
            checkButtonRunnerSkipped.setSelection(d.getIsRunnerSkipped());
            checkButtonDispatcherSkipped.setSelection(d.getIsDispatcherSkipped());

            Combo combo = new Combo(this.getParent(), SWT.CHECK);
        combo.pack();

        editor.minimumWidth = combo.getSize ().x;
        editor.horizontalAlignment = SWT.CENTER;
        editor.setEditor(combo, this, 1);
        */

    }

    public String[] getTextBuffer() {
        return textBuffer;
    }

    @SuppressWarnings("unused")
    private final String conClassName = "JobNetTableItem";

    @Override
    public Color getBackground() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Color getForeground() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Color[] getBackgroundColumn() {
        return colorsBackground;
    }

    @Override
    public Color[] getForegroundColumn() {
        return colorsForeground;
    }

}
