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
package sos.scheduler.editor.conf.listeners;


import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.jdom.Element;

import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.TreeData;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.SchedulerDom;

class Weekday {
	protected String day;
	protected String which;
	protected TreeMap elements = new TreeMap();
}

public class SpecificWeekdaysListener {


	public static final int               WEEKDAYS     = 0;

	public static final int               MONTHDAYS    = 1;

	public static final int               ULTIMOS      = 2;

	private             SchedulerDom      _dom         = null;

	public static       String[]          _daynames    = {"First","Second","Third","Fourth","Last","Second Last","Third Last","Fourth Last"};

	private             String[]          _usedDays    = null;

	private             Element           _runtime     = null;


	public SpecificWeekdaysListener(SchedulerDom dom, Element runtime) {

		_dom = dom;
		_runtime = runtime;

	}


	public void addDay(String day, String which) {
		boolean found=false;
		int index=0;
		for (int i = 0;i<_daynames.length;i++) {
			if (_daynames[i].equals(which)) index=i+1;
		}
		if (index>4)index=(-1)*(index-4);
		which = String.valueOf(index);
		Element daylist = _runtime.getChild("monthdays");
		if (daylist == null) {
			daylist = new Element("monthdays");
			_runtime.addContent(daylist);
		}


		if (daylist != null) {
			List list = daylist.getChildren("weekday");
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				if (e.getAttributeValue("day") != null && e.getAttributeValue("day").equalsIgnoreCase(day) && 
						e.getAttributeValue("which") != null && e.getAttributeValue("which").equalsIgnoreCase(which)) {
					found=true;
				}
			}
		}


		if (!found) {
			Element w = new Element("weekday").setAttribute("day", day.toLowerCase());
			w.setAttribute("which", which);
			daylist.addContent(w);
			_dom.setChanged(true);
			if(_runtime != null && _runtime.getParentElement() != null )
				//_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_runtime.getParentElement()), SchedulerDom.MODIFY);
				_dom.setChangedForDirectory(_runtime, SchedulerDom.MODIFY);
		}
	}



	public void deleteDay(String day_string) {
		String day = "";
		String which = "";
		int index=0;

		StringTokenizer t = new StringTokenizer(day_string.toLowerCase(), ".");
		// ----------------------------------------------------------

		//String token = "";

		if (t.hasMoreTokens())  which = t.nextToken();
		if (t.hasMoreTokens())  day = t.nextToken();

		for (int i = 0;i<_daynames.length;i++) {
			if (_daynames[i].equalsIgnoreCase(which)) index = i+1;
		}
		/*	 First       1=0
     	 Second      2=1
     	 Third       3=2
     	 Fourth      4=3
     	 Last       -1=4  5
     	 <--Second  -2=5  6
     	 <--Third=  -3=6  7
     	 <--Fourth  -4=7  8    */          	 

		if (index > 4)index = (index-4)*(-1); 

		which = String.valueOf(index);

		Element daylist = _runtime.getChild("monthdays");
		if (daylist != null) {
			List list = daylist.getChildren("weekday");
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				if (e.getAttributeValue("day") != null && e.getAttributeValue("day").equalsIgnoreCase(day) && 
						e.getAttributeValue("which") != null && e.getAttributeValue("which").equals(which)) {
					e.detach();

					// remove empty tag
					boolean isEmpty = true;
					List _list = _runtime.getChildren("monthdays");
					for(int i = 0; i < _list.size(); i++) {
						Element s = (Element)_list.get(i);
						if(s.getChildren().size() > 0) {
							//_elementName[_type] wird noch woanders verwendet
							isEmpty = false;
							break;
						}
					}

					if (list.size() == 0 && isEmpty) 
						_runtime.removeChild("monthdays");
					_dom.setChanged(true);
					if(_runtime != null && _runtime.getParentElement() != null )
						//_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_runtime.getParentElement()), SchedulerDom.MODIFY);
						_dom.setChangedForDirectory(_runtime, SchedulerDom.MODIFY);
					break;
				}
			}
		}
	}


	public String[] getDays() {


		TreeMap days = new TreeMap();
		String day = "";   
		String which = "";
		Weekday w = null;
		if (_runtime != null && _runtime.getChild("monthdays") != null) {
			Element daylist = _runtime.getChild("monthdays");
			List list = daylist.getChildren("weekday");
			int size = list.size();
			_usedDays=new String[size];

			Iterator it = list.iterator();
			int i = 0;
			int index = 0;
			while (it.hasNext()) {
				Element e = (Element) it.next();
				try {
					day = e.getAttributeValue("day");
					day = day.substring(0,1).toUpperCase() + day.substring(1).toLowerCase();
					which = e.getAttributeValue("which");
					w = (Weekday) days.get(day);
					if (w == null) w = new Weekday();

					w.which = w.which+","+which;
					w.day = day;
					w.elements.put(which,e);

					days.put(day,w);
				} catch (Exception ex) {
					try {
						new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() + " ;Invalid weekday element in monthdays" , ex);
					} catch(Exception ee) {
						//tu nichts
					}

					System.out.println("Invalid weekday element in monthdays:"+ex.getMessage());
				}
			}

			Iterator weekdayV_it = days.values().iterator();
			i = 0;
			while (weekdayV_it.hasNext()) {
				w =  (Weekday) weekdayV_it.next();

				StringTokenizer t = new StringTokenizer(w.which, ",");
				// ----------------------------------------------------------

				String token = "";

				while (t.hasMoreTokens()) {
					token = t.nextToken();
					if (!token.equals("") && token != null && !token.equals("null")) {
						i = Integer.parseInt(token)-1;

						/*	 First       1=0
                	 Second      2=1
                	 Third       3=2
                	 Fourth      4=3
                	 Last       -1=4
                	 <--Second  -2=5
                	 <--Third=  -3=6
                	 <--Fourth  -4=7     */          	 

						if (i < 0) i = 3 + (-1)*(i+1);
						_usedDays[index] = _daynames[i] + "." + w.day;
						index++;
					}
				}
			}
		}else {
			_usedDays = new String[0];
		}
		return _usedDays;
	}

	public void fillTreeDays(TreeItem parent, boolean expand) {

		//1.Reading Node "monthdays"
		//2.for each day making instance
		//3.             setting which (e.g. 1,3,-4)
		//4.Iterate all found days 
		//5.Create nodes for tree (parsing with tokenizer)

		parent.removeAll();

		TreeMap days = new TreeMap();
		String day = "";   
		String which = "";
		Weekday w = null;
		if (_runtime != null && _runtime.getChild("monthdays") != null) {
			Element daylist = _runtime.getChild("monthdays");
			List list = daylist.getChildren("weekday");
			//int size = list.size();


			Iterator it = list.iterator();
			int i = 0;
			while (it.hasNext()) {
				Element e = (Element) it.next();
				try {
					day = e.getAttributeValue("day");
					day = day.substring(0,1).toUpperCase() + day.substring(1).toLowerCase();

					which = e.getAttributeValue("which");
					w = (Weekday) days.get(day);
					if (w == null) w = new Weekday();

					w.which = w.which+","+which;
					w.day = day;
					w.elements.put(which,e);

					days.put(day,w);
				} catch (Exception ex) {
					try {
						new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() + " ;Invalid weekday element in monthdays" , ex);
					} catch(Exception ee) {
						//tu nichts
					}

					System.out.println("Invalid weekday element in monthdays:"+ex.getMessage());
				}
			}

			Iterator weekdayV_it = days.values().iterator();
			while (weekdayV_it.hasNext()) {
				w =  (Weekday) weekdayV_it.next();
				TreeItem itemDay = new TreeItem(parent, SWT.NONE);
				itemDay.setText(w.day);
				//itemDay.setData("max_occur", "1");
				//itemDay.setData("key", w.getName());
				
				
				if(!Utils.isElementEnabled("job", _dom, _runtime)) {
					itemDay.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
				}
				StringTokenizer t = new StringTokenizer(w.which, ",");
				// ----------------------------------------------------------

				String token = "";

				while (t.hasMoreTokens()) {
					token = t.nextToken();
					if (!token.equals("") && token != null && !token.equals("null")) {
						TreeItem item = new TreeItem(itemDay, SWT.NONE);
						i = Integer.parseInt(token)-1;
						if (i < 0) i = (i+1)*(-1) + 3;
                        Element e = (Element)w.elements.get(token);
						item.setText(_daynames[i]);

						item.setData("max_occur", "1");
						item.setData("key", e.getName() +"_@_");
						item.setData("copy_element", e);

						//item.setData(new TreeData(Editor.PERIODS, (Element)w.elements.get(token), Options.getHelpURL("periods")));
						item.setData(new TreeData(Editor.PERIODS, e, Options.getHelpURL("periods")));

						if(!Utils.isElementEnabled("job", _dom, _runtime)) {
							item.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
						}
					}
				}
				itemDay.setExpanded(expand);
			}
			parent.setExpanded(expand);
		}
	}

}
