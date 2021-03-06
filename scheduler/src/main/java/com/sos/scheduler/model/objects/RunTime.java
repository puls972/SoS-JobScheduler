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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3-
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2011.01.17 at 03:00:56 PM MEZ
//


package com.sos.scheduler.model.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for run_time complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="run_time">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="at" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="at" type="{}Loose_date_time" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="date" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="date" use="required" type="{}String" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="weekdays" type="{}weekdays" minOccurs="0"/>
 *         &lt;element name="monthdays" type="{}monthdays" minOccurs="0"/>
 *         &lt;element name="ultimos" type="{}ultimos" minOccurs="0"/>
 *         &lt;element name="month" maxOccurs="12" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="weekdays" type="{}weekdays"/>
 *                   &lt;element name="monthdays" type="{}monthdays"/>
 *                   &lt;element name="ultimos" type="{}ultimos"/>
 *                 &lt;/choice>
 *                 &lt;attribute name="month">
 *                   &lt;simpleType>
 *                     &lt;list itemType="{}Month_name" />
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element ref="{}holidays"/>
 *           &lt;element ref="{}holiday" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="schedule" type="{}String" />
 *       &lt;attribute name="name" type="{}Name" />
 *       &lt;attribute name="title" type="{}String" />
 *       &lt;attribute name="substitute" type="{}Path" />
 *       &lt;attribute name="valid_from" type="{}Date_time" />
 *       &lt;attribute name="valid_to" type="{}Date_time" />
 *       &lt;attribute name="single_start" type="{}Time_of_day" />
 *       &lt;attribute name="begin" type="{}Time_of_day" />
 *       &lt;attribute name="end" type="{}Time_of_day" />
 *       &lt;attribute name="let_run" type="{}Yes_no" />
 *       &lt;attribute name="repeat" type="{}Duration" />
 *       &lt;attribute name="when_holiday" type="{}When_holiday" />
 *       &lt;attribute name="once" type="{}Yes_no" />
 *       &lt;attribute name="start_time_function" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "run_time", propOrder = {
    "period",
    "at",
    "date",
    "weekdays",
    "monthdays",
    "ultimos",
    "month",
    "holidays",
    "holiday"
})
public class RunTime extends JSObjBase {

    protected List<Period> period;
    protected List<RunTime.At> at;
    protected List<RunTime.Date> date;
    protected Weekdays weekdays;
    protected Monthdays monthdays;
    protected Ultimos ultimos;
    protected List<RunTime.Month> month;
    protected Holidays holidays;
    protected List<Holiday> holiday;
    @XmlAttribute(name = "schedule")
    protected String schedule;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "substitute")
    protected String substitute;
    @XmlAttribute(name = "valid_from")
    protected String validFrom;
    @XmlAttribute(name = "valid_to")
    protected String validTo;
    @XmlAttribute(name = "single_start")
    protected String singleStart;
    @XmlAttribute(name = "begin")
    protected String begin;
    @XmlAttribute(name = "end")
    protected String end;
    @XmlAttribute(name = "let_run")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String letRun;
    @XmlAttribute(name = "repeat")
    protected String repeat;
    @XmlAttribute(name = "when_holiday")
    protected WhenHoliday whenHoliday;
    @XmlAttribute(name = "once")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String once;
    @XmlAttribute(name = "start_time_function")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String startTimeFunction;

    /**
     * Gets the value of the period property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the period property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPeriod().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Period }
     *
     *
     */
    public List<Period> getPeriod() {
        if (period == null) {
            period = new ArrayList<Period>();
        }
        return period;
    }

    /**
     * Gets the value of the at property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the at property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAt().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RunTime.At }
     *
     *
     */
    public List<RunTime.At> getAt() {
        if (at == null) {
            at = new ArrayList<RunTime.At>();
        }
        return at;
    }

    /**
     * Gets the value of the date property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the date property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDate().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RunTime.Date }
     *
     *
     */
    public List<RunTime.Date> getDate() {
        if (date == null) {
            date = new ArrayList<RunTime.Date>();
        }
        return date;
    }

    /**
     * Gets the value of the weekdays property.
     *
     * @return
     *     possible object is
     *     {@link Weekdays }
     *
     */
    public Weekdays getWeekdays() {
        return weekdays;
    }

    /**
     * Sets the value of the weekdays property.
     *
     * @param value
     *     allowed object is
     *     {@link Weekdays }
     *
     */
    public void setWeekdays(final Weekdays value) {
        weekdays = value;
    }

    /**
     * Gets the value of the monthdays property.
     *
     * @return
     *     possible object is
     *     {@link Monthdays }
     *
     */
    public Monthdays getMonthdays() {
        return monthdays;
    }

    /**
     * Sets the value of the monthdays property.
     *
     * @param value
     *     allowed object is
     *     {@link Monthdays }
     *
     */
    public void setMonthdays(final Monthdays value) {
        monthdays = value;
    }

    /**
     * Gets the value of the ultimos property.
     *
     * @return
     *     possible object is
     *     {@link Ultimos }
     *
     */
    public Ultimos getUltimos() {
        return ultimos;
    }

    /**
     * Sets the value of the ultimos property.
     *
     * @param value
     *     allowed object is
     *     {@link Ultimos }
     *
     */
    public void setUltimos(final Ultimos value) {
        ultimos = value;
    }

    /**
     * Gets the value of the month property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the month property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonth().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RunTime.Month }
     *
     *
     */
    public List<RunTime.Month> getMonth() {
        if (month == null) {
            month = new ArrayList<RunTime.Month>();
        }
        return month;
    }

    /**
     * Gets the value of the holidays property.
     *
     * @return
     *     possible object is
     *     {@link Holidays }
     *
     */
    public Holidays getHolidays() {
    	if (holidays == null) {
    		holidays = new Holidays();
    	}
        return holidays;
    }

    /**
     * Sets the value of the holidays property.
     *
     * @param value
     *     allowed object is
     *     {@link Holidays }
     *
     */
    public void setHolidays(final Holidays value) {
        holidays = value;
    }

    /**
     * Gets the value of the holiday property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the holiday property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHoliday().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Holiday }
     *
     *
     */
    public List<Holiday> getHoliday() {
        if (holiday == null) {
            holiday = new ArrayList<Holiday>();
        }
        return holiday;
    }

    /**
     * Gets the value of the schedule property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * Sets the value of the schedule property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSchedule(final String value) {
        schedule = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(final String value) {
        name = value;
    }

    /**
     * Gets the value of the title property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @Override
	public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTitle(final String value) {
        title = value;
    }

    /**
     * Gets the value of the substitute property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSubstitute() {
        return substitute;
    }

    /**
     * Sets the value of the substitute property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSubstitute(final String value) {
        substitute = value;
    }

    /**
     * Gets the value of the validFrom property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the value of the validFrom property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setValidFrom(final String value) {
        validFrom = value;
    }

    /**
     * Gets the value of the validTo property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getValidTo() {
        return validTo;
    }

    /**
     * Sets the value of the validTo property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setValidTo(final String value) {
        validTo = value;
    }

    /**
     * Gets the value of the singleStart property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSingleStart() {
        return singleStart;
    }

    /**
     * Sets the value of the singleStart property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSingleStart(final String value) {
        singleStart = value;
    }

    /**
     * Gets the value of the begin property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBegin() {
        return begin;
    }

    /**
     * Sets the value of the begin property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBegin(final String value) {
        begin = value;
    }

    /**
     * Gets the value of the end property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEnd(final String value) {
        end = value;
    }

    /**
     * Gets the value of the letRun property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLetRun() {
        return letRun;
    }

    /**
     * Sets the value of the letRun property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLetRun(final String value) {
        letRun = value;
    }

    /**
     * Gets the value of the repeat property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRepeat() {
        return repeat;
    }

    /**
     * Sets the value of the repeat property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRepeat(final String value) {
        repeat = value;
    }

    /**
     * Gets the value of the whenHoliday property.
     *
     * @return
     *     possible object is
     *     {@link WhenHoliday }
     *
     */
    public WhenHoliday getWhenHoliday() {
        return whenHoliday;
    }

    /**
     * Sets the value of the whenHoliday property.
     *
     * @param value
     *     allowed object is
     *     {@link WhenHoliday }
     *
     */
    public void setWhenHoliday(final WhenHoliday value) {
        whenHoliday = value;
    }

    /**
     * Gets the value of the once property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOnce() {
        return once;
    }

    /**
     * Sets the value of the once property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOnce(final String value) {
        once = value;
    }

    /**
     * Gets the value of the startTimeFunction property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStartTimeFunction() {
        return startTimeFunction;
    }

    /**
     * Sets the value of the startTimeFunction property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStartTimeFunction(final String value) {
        startTimeFunction = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="at" type="{}Loose_date_time" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class At extends JSObjBase {

        @XmlAttribute(name = "at")
        protected String at;

        /**
         * Gets the value of the at property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAt() {
            return at;
        }

        /**
         * Sets the value of the at property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAt(final String value) {
            at = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="date" use="required" type="{}String" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "period"
    })
    public static class Date extends JSObjBase {

        protected List<Period> period;
        @XmlAttribute(name = "date", required = true)
        protected String date;

        /**
         * Gets the value of the period property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the period property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPeriod().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Period }
         *
         *
         */
        public List<Period> getPeriod() {
            if (period == null) {
                period = new ArrayList<Period>();
            }
            return period;
        }

        /**
         * Gets the value of the date property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getDate() {
            return date;
        }

        /**
         * Sets the value of the date property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setDate(final String value) {
            date = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="weekdays" type="{}weekdays"/>
     *         &lt;element name="monthdays" type="{}monthdays"/>
     *         &lt;element name="ultimos" type="{}ultimos"/>
     *       &lt;/choice>
     *       &lt;attribute name="month">
     *         &lt;simpleType>
     *           &lt;list itemType="{}Month_name" />
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "periodOrWeekdaysOrMonthdays"
    })
    public static class Month extends JSObjBase {

        @XmlElements({
            @XmlElement(name = "monthdays", type = Monthdays.class),
            @XmlElement(name = "ultimos", type = Ultimos.class),
            @XmlElement(name = "weekdays", type = Weekdays.class),
            @XmlElement(name = "period", type = Period.class)
        })
        protected List<Object> periodOrWeekdaysOrMonthdays;
        @XmlAttribute(name = "month")
        protected List<String> month;

        /**
         * Gets the value of the periodOrWeekdaysOrMonthdays property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the periodOrWeekdaysOrMonthdays property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPeriodOrWeekdaysOrMonthdays().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Monthdays }
         * {@link Ultimos }
         * {@link Weekdays }
         * {@link Period }
         *
         *
         */
        public List<Object> getPeriodOrWeekdaysOrMonthdays() {
            if (periodOrWeekdaysOrMonthdays == null) {
                periodOrWeekdaysOrMonthdays = new ArrayList<Object>();
            }
            return periodOrWeekdaysOrMonthdays;
        }

        /**
         * Gets the value of the month property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the month property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMonth().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         *
         *
         */
        public List<String> getMonth() {
            if (month == null) {
                month = new ArrayList<String>();
            }
            return month;
        }

    }

}
