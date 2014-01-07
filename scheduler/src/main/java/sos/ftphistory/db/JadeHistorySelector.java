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
package sos.ftphistory.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
* \class JadeFilesSelector 
* 
* \brief JadeFilesSelector - 
* 
* \details
*
* \section JadeFilesSelector.java_intro_sec Introduction
*
* \section JadeFilesSelector.java_samples Some Samples
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
* \version 13.09.2011
* \see reference
*
* Created on 13.09.2011 14:40:18
 */

public class JadeHistorySelector {

	@SuppressWarnings("unused")
	private final String	conClassName	= "JadeFilesSelector";
	private Date createdFrom;
	private Date createdTo;
	private String dateFormat;
	private SessionFactory sessionFactory=null;
	

	public JadeHistorySelector() {
		this.dateFormat = "dd.MM.yyyy hh:mm";
		//
	} 

	private Session getSession() {
		Configuration configuration = new Configuration();
		configuration.configure();
		sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
		sessionFactory.openSession();
        return session;
	}
	

	public List <JadeFilesDBItem>  getFilesFromTo() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
	 
			createdFrom = formatter.parse("07.09.2011 00:00");
			createdTo = formatter.parse("07.09.2011 00:00");
			Session session = getSession();

			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery("  from JadeFilesDBItem where  created >= :createdFrom and created <= :createdTo");

			query.setTimestamp("createdFrom", createdFrom);
			query.setTimestamp("createdTo", createdTo);

			List  <JadeFilesDBItem>  resultset = query.list();
	 
			transaction.commit();
			return resultset;

		}
	
	public List <JadeFilesHistoryDBItem>  	getFilesHistoryFromTo() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
	 
			createdFrom = formatter.parse("07.09.2011 00:00");
			createdTo = formatter.parse("07.09.2011 00:00");
			Session session = getSession();

			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery("  from JadeFilesDBItem where  created >= :createdFrom and created <= :createdTo");

			query.setTimestamp("createdFrom", createdFrom);
			query.setTimestamp("createdTo", createdTo);

			List  <JadeFilesHistoryDBItem>  resultset = query.list();
	 
			transaction.commit();
			return resultset;

		}

	public void setCreatedFrom(Date createdFrom) {
		this.createdFrom = createdFrom;
	}

	public void setCreatedTo(Date createdTo) {
		this.createdTo = createdTo;
	}
		 
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setCreatedFrom(String createdFrom) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		this.createdFrom = formatter.parse(createdFrom);
	}

	public void setCreatedTo(String createdTo) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		this.createdTo = formatter.parse(createdTo);
	} 	
}
