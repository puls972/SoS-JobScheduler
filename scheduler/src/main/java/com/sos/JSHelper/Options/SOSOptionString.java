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
package com.sos.JSHelper.Options;

/**
* \class JSOptionString 
* 
* \brief JSOptionString - 
* 
* \details
*
* \section JSOptionString.java_intro_sec Introduction
*
* \section JSOptionString.java_samples Some Samples
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
* \author eqbfd
* @version $Id: SOSOptionString.java 17737 2012-08-02 16:46:57Z kb $23.01.2009
* \see reference
*
* Created on 23.01.2009 15:45:36
 */

/**
 * @author eqbfd
 *
 */
public class SOSOptionString extends SOSOptionElement {

	private static final long	serialVersionUID	= -7931744980509663560L;
	@SuppressWarnings("unused")
	private final String	conClassName	= "JSOptionString";
	public final String ControlType = "text";

	/**
	 * \brief JSOptionString
	 *
	 * \details
	 *
	 * @param pPobjParent
	 * @param pPstrKey
	 * @param pPstrDescription
	 * @param pPstrValue
	 * @param pPstrDefaultValue
	 * @param pPflgIsMandatory
	 * @throws Exception
	 */
	public SOSOptionString(JSOptionsClass pPobjParent, String pPstrKey, String pPstrDescription, String pPstrValue, 
			String pPstrDefaultValue,
			boolean pPflgIsMandatory) {
		super(pPobjParent, pPstrKey, pPstrDescription, pPstrValue, pPstrDefaultValue, pPflgIsMandatory);
		
		this.intOptionType = isOptionTypeString;
		
	}
}
