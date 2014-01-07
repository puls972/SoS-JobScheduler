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


package com.sos.scheduler.model.commands;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.sos.scheduler.model.answers.JSCmdBase;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="all_schedulers" type="{}Yes_no" />
 *       &lt;attribute name="restart" type="{}Yes_no" />
 *       &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="continue_exclusive_operation" type="{}Yes_no" />
 *       &lt;attribute name="cluster_member_id" type="{}String" />
 *       &lt;attribute name="delete_dead_entry" type="{}Yes_no" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "terminate")
public class Terminate extends JSCmdBase {

    @XmlAttribute(name = "all_schedulers")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String allSchedulers;
    @XmlAttribute(name = "restart")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String restart;
    @XmlAttribute(name = "timeout")
    protected BigInteger timeout;
    @XmlAttribute(name = "continue_exclusive_operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String continueExclusiveOperation;
    @XmlAttribute(name = "cluster_member_id")
    protected String clusterMemberId;
    @XmlAttribute(name = "delete_dead_entry")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String deleteDeadEntry;

    /**
     * Gets the value of the allSchedulers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllSchedulers() {
        return allSchedulers;
    }

    /**
     * Sets the value of the allSchedulers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllSchedulers(String value) {
        this.allSchedulers = value;
    }

    /**
     * Gets the value of the restart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestart() {
        return restart;
    }

    /**
     * Sets the value of the restart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestart(String value) {
        this.restart = value;
    }

    /**
     * Gets the value of the timeout property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTimeout() {
        return timeout;
    }

    /**
     * Sets the value of the timeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTimeout(BigInteger value) {
        this.timeout = value;
    }

    /**
     * Gets the value of the continueExclusiveOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContinueExclusiveOperation() {
        return continueExclusiveOperation;
    }

    /**
     * Sets the value of the continueExclusiveOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContinueExclusiveOperation(String value) {
        this.continueExclusiveOperation = value;
    }

    /**
     * Gets the value of the clusterMemberId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClusterMemberId() {
        return clusterMemberId;
    }

    /**
     * Sets the value of the clusterMemberId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClusterMemberId(String value) {
        this.clusterMemberId = value;
    }

    /**
     * Gets the value of the deleteDeadEntry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteDeadEntry() {
        return deleteDeadEntry;
    }

    /**
     * Sets the value of the deleteDeadEntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteDeadEntry(String value) {
        this.deleteDeadEntry = value;
    }

}
