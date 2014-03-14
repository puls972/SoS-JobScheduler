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
// Generated on: 2011.11.22 at 05:41:11 PM MEZ 
//


package com.sos.scheduler.model.answers;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}calendar"/>
 *         &lt;element ref="{}ERROR"/>
 *         &lt;element ref="{}history"/>
 *         &lt;element ref="{}jobs"/>
 *         &lt;element ref="{}job"/>
 *         &lt;element ref="{}ok"/>
 *         &lt;element ref="{}state"/>
 *       &lt;/sequence>
 *       &lt;attribute name="time" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "calendar",
    "error",
    "history",
    "jobs",
    "order",
    "job",
    "ok",
    "state"
})
@XmlRootElement(name = "answer")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
public class Answer {

    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    protected Calendar calendar;
    @XmlElement(name = "ERROR", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    protected ERROR error;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    protected History history;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    protected Jobs jobs;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    protected Order order;
    protected Job job;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    protected Ok ok;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    protected State state;
    @XmlAttribute(name = "time", required = true)
    @XmlSchemaType(name = "anySimpleType")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    protected String time;

    /**
     * Gets the value of the calendar property.
     * 
     * @return
     *     possible object is
     *     {@link Calendar }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * Sets the value of the calendar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Calendar }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public void setCalendar(Calendar value) {
        this.calendar = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link ERROR }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public ERROR getERROR() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link ERROR }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public void setERROR(ERROR value) {
        this.error = value;
    }

    /**
     * Gets the value of the history property.
     * 
     * @return
     *     possible object is
     *     {@link History }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public History getHistory() {
        return history;
    }

    /**
     * Sets the value of the history property.
     * 
     * @param value
     *     allowed object is
     *     {@link History }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public void setHistory(History value) {
        this.history = value;
    }

    /**
     * Gets the value of the jobs property.
     * 
     * @return
     *     possible object is
     *     {@link Jobs }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public Jobs getJobs() {
        return jobs;
    }

    /**
     * Sets the value of the jobs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Jobs }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public void setJobs(Jobs value) {
        this.jobs = value;
    }

    /**
     * Gets the value of the job property.
     * 
     * @return
     *     possible object is
     *     {@link Job }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public Job getJob() {
        return job;
    }
    public Order getOrder() {
        return order;
    }
    /**
     * Sets the value of the job property.
     * 
     * @param value
     *     allowed object is
     *     {@link Job }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public void setJob(Job value) {
        this.job = value;
    }

    /**
     * Gets the value of the ok property.
     * 
     * @return
     *     possible object is
     *     {@link Ok }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public Ok getOk() {
        return ok;
    }

    /**
     * Sets the value of the ok property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ok }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public void setOk(Ok value) {
        this.ok = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link State }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public State getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link State }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public void setState(State value) {
        this.state = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public String getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2011-11-22T05:41:11+01:00", comments = "JAXB RI v2.2.3-hudson-jaxb-ri-2.2.3-3-")
    public void setTime(String value) {
        this.time = value;
    }

}