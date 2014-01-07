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
package com.sos.scheduler.model.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.tools.RunTimeElement;
import com.sos.scheduler.model.tools.RunTimeElements;

/**
* \class JSObjRunTime
*
* \brief JSObjRunTime -
*
* \details
*
* \section JSObjRunTime.java_intro_sec Introduction
*
* \section JSObjRunTime.java_samples Some Samples
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
* \author oh
* @version $Id: JSObjRunTime.java 20718 2013-07-18 18:16:10Z kb $
* \see reference
*
* Created on 09.02.2011 15:15:40
 */

/**
 * @author oh
 *
 */
public class JSObjRunTime extends RunTime {

	private final String		conClassName		= "JSObjRunTime";
	@SuppressWarnings("unused")
	private static final Logger	logger				= Logger.getLogger(JSObjRunTime.class);

	private final JSObjPeriod	period;
	private boolean				useDefaultPeriod	= false;

	public JSObjRunTime(final SchedulerObjectFactory schedulerObjectFactory) {
		super();
		objFactory = schedulerObjectFactory;
		period = getPeriodObject();
	}

	@SuppressWarnings("unchecked")
	public JSObjRunTime(final SchedulerObjectFactory schedulerObjectFactory, final String xmlContent) {
		super();
		objFactory = schedulerObjectFactory;
		objJAXBElement = (JAXBElement<JSObjBase>) unMarshal(xmlContent);
		setObjectFieldsFrom(objJAXBElement.getValue());
		period = getPeriodObject();
	}

	public JSObjRunTime(final SchedulerObjectFactory schedulerObjectFactory, final RunTime pobjRunTime) {
		super();
		objFactory = schedulerObjectFactory;
		setObjectFieldsFrom(pobjRunTime);
		period = getPeriodObject();
	}

	/**
	 * \brief
	 * \detail
	 * The runtime attributes given in this element are relevant only if no period element
	 * is set in the subsequent elements.
	 *
	 * @return
	 */
	private JSObjPeriod getPeriodObject() {
		JSObjPeriod period = new JSObjPeriod(objFactory);
		period.setBegin(getBegin());
		period.setEnd(getEnd());
		period.setRepeat(getRepeat());
		period.setLetRun(getLetRun());
		period.setSingleStart(getSingleStart());
		period.setWhenHoliday(getWhenHoliday());
		return period;
	}

	public JSObjPeriod getRunTimePeriod() {
		return period;
	}

	//	private void buildStartTimeList() {
	//		if (hasDate()) {
	//			Iterator<RunTime.Date> itD = getDate().iterator();
	//			while (itD.hasNext()) {
	//				RunTime.Date date = itD.next();
	//				Iterator<Period> itP = date.getPeriod().iterator();
	//				while (itP.hasNext()) {
	//					Period p = itP.next();
	//					JSObjPeriod periodObject = new JSObjPeriod(objFactory);
	//					periodObject.setObjectFieldsFrom(p);
	////					startTimes.put(periodObject.getDtNextSingleStart(), periodObject);
	//				}
	//			}
	//		}
	//	}

	/* (non-Javadoc)
	 * @see com.sos.scheduler.model.objects.ISOSJsObjPeriod#getBegin()
	 */
	@Override
	public String getBegin() {
		return JSObjPeriod.normalizeTime(super.getBegin());
	}

	/* (non-Javadoc)
	 * @see com.sos.scheduler.model.objects.ISOSJsObjPeriod#getEnd()
	 */
	@Override
	public String getEnd() {
		return JSObjPeriod.normalizeTime(super.getEnd());
	}

	public boolean hasPeriod() {
		return getPeriod().size() != 0;
	}

	public boolean hasAt() {
		return getAt().size() != 0;
	}

	public boolean hasDate() {
		return getDate().size() != 0;
	}

	public boolean hasWeekdays() {
		return getWeekdays() != null;
	}

	public boolean hasMonth() {
		return getMonth().size() != 0;
	}

	public boolean hasMonthdays() {
		return (getMonthdays() != null && getMonthdays().getDayOrWeekday().size() != 0);
	}

	public boolean hasUltimos() {
		return getUltimos() != null;
	}

	public boolean hasHolidays() {
		return getHolidays() != null;
	}

	public boolean hasSubsequentRunTimes() {
		return hasPeriod() || hasAt() || hasDate() || hasWeekdays() || hasMonth() || hasMonthdays() || hasUltimos();
	}

	public List<DateTime> getDtSingleStarts(final Interval timeRange) {

		// get all runtimes one including one day before and one day after the given time range also
		// because some holiday defintion could move the calculated dates into the original timeRange given
		// in the parameter (see getStartDatesAwareHolidays)
		Interval extendedTimeRange = new Interval(timeRange.getStart().minusDays(1), timeRange.getEnd().plusDays(1));

		RunTimeElements runTimes = new RunTimeElements(timeRange);
		runTimes.putAll(getDtWeekdays(extendedTimeRange));
		runTimes.putAll(getDtPeriod(extendedTimeRange));
		runTimes.putAll(getDtAt(extendedTimeRange));
		runTimes.putAll(getDtDate(extendedTimeRange));
		runTimes.putAll(getDtMonthdays(extendedTimeRange));
		runTimes.putAll(getDtUltimos(extendedTimeRange));
		// the single start given in the run_time element is only relevant if no subsequent
		// period element is given.
		if (runTimes.size() == 0) {
			RunTimeElements periodStartTimes = period.getRunTimeElements(extendedTimeRange);
			runTimes.putAll(periodStartTimes);
		}

		List<DateTime> result = new ArrayList<DateTime>();
		for (DateTime d : getJsObjHolidays().getStartDatesAwareHolidays(runTimes)) {
			if (timeRange.contains(d)) // only dates inside the given time range should be in the result set
				result.add(d);
		}
		return result;
	}

	/**
	 * \brief Collection of all start times given by the date element
	 * \detail
	 * Collects all start times given by the date element in ascending order.
	 * @return
	 */
	public RunTimeElements getDtAt(final Interval timeRange) {
		RunTimeElements result = new RunTimeElements(timeRange);
		Iterator<JSObjAt> it1 = getJsObjAt().iterator();
		while (it1.hasNext()) {
			JSObjAt at = it1.next();
			for (RunTimeElement e : at.getRunTimeElements(timeRange).values()) {
				result.add(e);
			}
		}
		//		Collections.sort(result, DateTimeComparator.getInstance());
		return result;
	}

	/**
	 * \brief Collection of all start times given by the date element
	 * \detail
	 * Collects all start times given by the date element in ascending order.
	 * @return
	 */
	public RunTimeElements getDtDate(final Interval timeRange) {
		RunTimeElements result = new RunTimeElements(timeRange);
		Iterator<JSObjDate> it1 = getJsObjDate().iterator();
		while (it1.hasNext()) {
			JSObjDate date = it1.next();
			for (RunTimeElement e : date.getRunTimeElements(timeRange).values()) {
				result.add(e);
			}
		}
		//		Collections.sort(result, DateTimeComparator.getInstance());
		return result;
	}

	/**
	 * \brief Collection of all start times given by the monthdays element
	 * \detail
	 * Collects all start times given by the monthdays element in ascending order.
	 * @return
	 */
	public RunTimeElements getDtMonthdays(final Interval timeRange) {
		return getJsObjMonthdays().getRunTimeElements(timeRange);
	}

	/**
	 * \brief Collection of all start times given by the ultimos element
	 * \detail
	 * Collects all start times given by the ultimos element in ascending order.
	 * @return
	 */
	public RunTimeElements getDtUltimos(final Interval timeRange) {
		return getJsObjUltimos().getRunTimeElements(timeRange);
	}

	/**
	 * \brief Collection of all start times given by the period element
	 * \detail
	 * Collects all start times given by the period (as a direct sub element of run_time) element in ascending order.
	 * @return
	 */
	public RunTimeElements getDtPeriod(final Interval timeRange) {
		RunTimeElements result = new RunTimeElements(timeRange);
		Iterator<JSObjPeriod> it1 = getJsObjPeriod().iterator();
		while (it1.hasNext()) {
			JSObjPeriod period = it1.next();
			for (RunTimeElement e : period.getRunTimeElements(timeRange).values()) {
				result.add(e);
			}
		}
		//		Collections.sort(result, DateTimeComparator.getInstance());
		return result;
	}

	/**
	 * \brief Collection of all start times given by the weekdays element
	 * \detail
	 * Collects all start times given by weekdays
	 * @return
	 */
	public RunTimeElements getDtWeekdays(final Interval timeRange) {
		return getJsObjWeekdays().getRunTimeElements(timeRange);
	}

	public JSObjWeekdays getJsObjWeekdays() {
		JSObjWeekdays weekdays = new JSObjWeekdays(objFactory);
		if (hasWeekdays()) {
			weekdays.setObjectFieldsFrom(getWeekdays());
			weekdays.setHotFolderSrc(getHotFolderSrc());
		}
		return weekdays;
	}

	public JSObjMonthdays getJsObjMonthdays() {
		JSObjMonthdays monthdays = new JSObjMonthdays(objFactory);
		if (hasMonthdays()) {
			monthdays.setObjectFieldsFrom(getMonthdays());
			monthdays.setHotFolderSrc(getHotFolderSrc());
		}
		return monthdays;
	}

	public JSObjUltimos getJsObjUltimos() {
		JSObjUltimos ultimos = new JSObjUltimos(objFactory);
		if (hasUltimos()) {
			ultimos.setObjectFieldsFrom(getUltimos());
			ultimos.setHotFolderSrc(getHotFolderSrc());
		}
		return ultimos;
	}

	public List<JSObjDate> getJsObjDate() {
		List<JSObjDate> result = new ArrayList<JSObjDate>();
		if (hasDate()) {
			Iterator<RunTime.Date> it = getDate().iterator();
			while (it.hasNext()) {
				RunTime.Date d = it.next();
				JSObjDate date = new JSObjDate(objFactory);
				date.setObjectFieldsFrom(d);
				date.setHotFolderSrc(getHotFolderSrc());
				result.add(date);
			}
		}
		return result;
	}

	public List<JSObjAt> getJsObjAt() {
		List<JSObjAt> result = new ArrayList<JSObjAt>();
		if (hasAt()) {
			Iterator<RunTime.At> it = getAt().iterator();
			while (it.hasNext()) {
				RunTime.At d = it.next();
				JSObjAt date = new JSObjAt(objFactory);
				date.setObjectFieldsFrom(d);
				date.setHotFolderSrc(getHotFolderSrc());
				result.add(date);
			}
		}
		return result;
	}

	public JSObjHolidays getJsObjHolidays() {
		return getJsObjHolidays(true);
	}

	public JSObjHolidays getJsObjHolidays(final boolean resolveIncludes) {
		JSObjHolidays result = new JSObjHolidays(objFactory);
		if (hasHolidays()) {
			result.setObjectFieldsFrom(getHolidays());
			result.setHotFolderSrc(getHotFolderSrc());
			if (resolveIncludes)
				result.resolveIncludes();
		}
		return result;
	}

	public List<JSObjPeriod> getJsObjPeriod() {
		List<JSObjPeriod> result = new ArrayList<JSObjPeriod>();
		if (hasPeriod()) {
			Iterator<Period> it = getPeriod().iterator();
			while (it.hasNext()) {
				Period p = it.next();
				JSObjPeriod period = new JSObjPeriod(objFactory);
				period.setObjectFieldsFrom(p);
				period.setHotFolderSrc(getHotFolderSrc());
				result.add(period);
			}
		}
		return result;
	}

	/**
	 * \brief default period element for special use
	 * \detail
	 * In some cases it is sufficient that a run time element has no start time represented by period,
	 * e.g. <run_time><weekdays><day day="1" /></weekdays></run_time> means to run every Monday but
	 * has no start time. The period given by this method generates a default single_start at "23:59:59".
	 *
	 * @return
	 */
	public static List<Period> getDefaultPeriod(final SchedulerObjectFactory factory, final WhenHoliday h) {
		List<Period> result = new ArrayList<Period>();
		Period period = factory.createPeriod();
		period.setBegin(null);
		period.setEnd(null);
		period.setRepeat(null);
		period.setLetRun(conNO);
		period.setSingleStart("23:59:59");
		period.setWhenHoliday(h);
		result.add(period);
		return result;
	}

	public boolean useDefaultPeriod() {
		return useDefaultPeriod;
	}

	protected void setUseDefaultPeriod(final boolean useDefaultPeriod) {
		this.useDefaultPeriod = useDefaultPeriod;
	}

	@Override
	public Monthdays getMonthdays() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getMonthdays";

		Monthdays objM = super.getMonthdays();
		if (objM == null) {
			super.setMonthdays(new Monthdays());
		}

		return super.getMonthdays();
	} // private Monthdays getMonthdays

	public boolean hasSingleStart() {

		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::isSingleStart";

		return this.getSingleStart().length() > 0;

	} // private boolean isSingleStart
	public void setSingleStart(final boolean pflgValue) {

		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::setSingleStart";

		if (pflgValue == true) {
			this.setSingleStart(conYES);
		}
		else {
			this.setSingleStart(conEMPTY);
		}

	} // private void setSingleStart

	public void setOnce(final boolean pflgValue) {

		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::setOnce";


		if (pflgValue == true) {
			this.setOnce(conYES);
		}
		else {
			this.setOnce(conNO);
		}

	} // private void setOnce

	public boolean isOnce() {

		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::isOnce";

		return this.getOnce().equalsIgnoreCase(conYES);

	} // private boolean isOnce

	public boolean isLetRun() {

		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::isLetRun";

		return this.getLetRun().equalsIgnoreCase(conYES);

//	return boolean;
	} // private boolean isLetRun
	public void  setLetRun(final boolean pflgValue) {

		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::setLetRun";

		if (pflgValue == true) {
			this.setLetRun(conYES);
		}
		else {
			this.setLetRun(conNO);
		}

	} // private void  setLetRun
}
