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
package sos.ftp.profiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.eclipse.swt.SWT;
import sos.settings.SOSProfileSettings;
import sos.util.SOSLogger;
import sos.util.SOSString;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;


public class FTPDialogListener {

	/** @see sos.util.SOSString Object*/
	private              SOSString              sosString                     = null;

	/** Beinhaltet alle Profilnamen, die konfiguriert wurden*/
	private              String[]               profileNames                  = null;

	/** Hilfsvariable. name=profilname, wert= entsprechende FTPProfile Objekt*/
	private              HashMap                profiles                      = null;

	/** aktuell ausgew�hlte FTPProfile Objekt*/
	private              FTPProfile             currProfile                   = null;

	/** aktuell ausgew�hlte Profilname*/
	private              String                 currProfileName               = null;

	/** see sos.settings.SOSProfileSettings Object*/
	private              SOSProfileSettings     settings                      = null;

	/** In der Konfigurationsdatei haben die Profilsektionen einen Pr�fix.
	 * Unterhalb dieser Sektionen werden die Einstellungen/Zugangsdaten gespeichert.  
	 * 
	 * */
	private              String                 prefix                        = "profile ";

	/**
	 * Name der Konfigurationsdatei. Hier werden die FTP Profile gespeichert.
	 */
	private              String                 configFile                    = "";

	/** In dieser Auswahllisten stehen die Profilnamen*/
	private              Combo                  cboConnectname                = null;

	/** Falls es nicht erw�nscht ist, den Passwort zuspeichern, dann hat man auch die M�glichkeit dieser 
	 * w�hrend der Laufzeit anzugeben.*/
	private              String                 password                      = "";

	/** Flag */
	private              boolean                hasError                      = false;

	/** aktuelle Pfad auf der FTP Server*/
	private              Text                   txtPath                       = null;

	
	/**
	 * Konstruktor
	 * @param profile
	 * @param profilename
	 */
	public FTPDialogListener(FTPProfile profile, String profilename) {
		try {
			FTPProfile.log("calling " + sos.util.SOSClassUtil.getMethodName(), SOSLogger.DEBUG9);
			sosString = new SOSString();
			currProfile = profile;
			currProfileName = profilename; 
			profiles = new HashMap();
			profiles.put(profilename,profile);
		} catch (Exception e){
			FTPProfile.log("error in FTPDialogListener,  cause: " + e.toString(), 1);    		
		}
	}

	/**
	 * Konstruktor
	 * @param configfile
	 * @throws Exception
	 */
	public FTPDialogListener(File configfile) throws Exception{

		sosString = new SOSString();
		FTPProfile.log("calling " + sos.util.SOSClassUtil.getMethodName(), SOSLogger.DEBUG9);
		try {		

			configFile = configfile.getCanonicalPath();

			if(!new File(configFile).exists()) {
				new File(configFile).createNewFile();
			}

			settings = new SOSProfileSettings(configFile);
			ArrayList l = settings.getSections();
			profileNames = convert(settings.getSections().toArray());

			profiles = new HashMap();
			for(int i = 0; i < l.size(); i++) {
				String section = sosString.parseToString(l.get(i));

				if(section.toLowerCase().startsWith(prefix)) {
					String sectionWithoutPrefix = section.substring(prefix.length());
					Properties prop = settings.getSection(section);
					prop.setProperty("profilename", sectionWithoutPrefix);
					profiles.put(sectionWithoutPrefix, new FTPProfile(prop));
					if( currProfileName == null) {
						init(sectionWithoutPrefix);
						//currProfileName = sectionWithoutPrefix;
						//currProfile = prop;
					}
				}

			}

		} catch(Exception e) {			
			hasError = true; 
			FTPProfile.log("error in "+ sos.util.SOSClassUtil.getMethodName() + "could not read Profiles from " + configFile + ", cause: " + e.toString(), SOSLogger.WARN);
			FTPProfileDialog.message("could not read Profiles from " + configFile, SWT.ICON_WARNING);
		}
	}

	/**
	 * Initialisierung
	 * @param profile
	 */
	private void init(String profile) {

		currProfileName = profile;
		currProfile = (FTPProfile)profiles.get(profile);

	}

	
	private String[] convert(Object[] obj) throws Exception {
		FTPProfile.log("calling " + sos.util.SOSClassUtil.getMethodName(), SOSLogger.DEBUG9);
		ArrayList str = new ArrayList();
		String[] retVal = null;
		try {						
			for(int i = 0; i < obj.length; i++) {
				if(sosString.parseToString(obj[i]).startsWith(prefix)) {
					str.add(sosString.parseToString(obj[i]).substring(prefix.length()));
				}
			}
			retVal = new String[str.size()];
			for(int i = 0; i < str.size(); i++) {
				retVal[i] = str.get(i).toString();
			}

		} catch (Exception e) {
			FTPProfile.log("error in "+ sos.util.SOSClassUtil.getMethodName() + ", cause: " + e.toString(), SOSLogger.WARN);
			hasError = true;
		}
		return retVal;
	}

	/**
	 * Liefert alle konfigurierten Profilnamen
	 * @return
	 */
	public String[] getProfileNames() {
		try {
			profileNames = convert(settings.getSections().toArray());		
		} catch (Exception e) {
		}
		return profileNames;
	}

	/**
	 * Setzt alle Profilnamen
	 */
	public void setProfileNames(String[] profileNames) {
		this.profileNames = profileNames;
	}

	/**
	 * Liefert ein HaspMap Objekt.
	 * Dieser beinhaltet als name=profilname und value=entsprechende FTPProfile Objekt
	 * @return
	 */
	public HashMap getProfiles() {
		return profiles;
	}

	/**
	 * Erstellen einer neuen FTPProfile
	 * @param key
	 * @param profile
	 */
	public void setProfiles(String key, FTPProfile profile) {
		try {
			settings.getSections().add(prefix + key);
			this.profiles.put(key, profile);
		} catch (Exception e) {
			FTPProfileDialog.message("error in FTPDialoListener: " + e.getMessage(), 1);
		}
	}

	/**
	 * Liefert die aktuelle FTPProfile
	 * @return FTPProfile  Obejct
	 */
	public FTPProfile getCurrProfile() {
		return currProfile;
	}
	
	/**
	 * Setz die aktuelle FTPProfile
	 * @param currProfile
	 */
	public void setCurrProfile(FTPProfile currProfile) {
		this.currProfile = currProfile;
	}

	/**
	 * Liefert die aktuell ausgew�hlte Profilname
	 * @return
	 */
	public String getCurrProfileName() {
		return currProfileName;
	}

	/**
	 * Setzt die aktuelle ausgew�hle Profilname
	 * @param currProfileName
	 */
	public void setCurrProfileName(String currProfileName) {
		this.currProfileName = currProfileName;
		init(currProfileName);
	}

	/**
	 * L�scht die FTPProfile 
	 * @param profilename
	 */
	public void removeProfile(String profilename) {
		try {
			deleteProfile(profilename);
			getProfiles().remove(profilename);
			removeFromProfilenames(profilename);
			settings.getSections().remove(prefix +profilename);
			currProfile = new FTPProfile(new Properties());
			currProfileName = "";
		} catch(Exception e) {
			FTPProfileDialog.message("could not remove Profile: " + profilename + ": cause:\n" + e.getMessage(), SWT.ICON_WARNING);
		}

	}


	/**
	 * Liest den Inhalt der Datei file und gibt den Inhalt als Array von Byte zur�ck.
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException, Exception {
		FTPProfile.log("calling " + sos.util.SOSClassUtil.getMethodName(), SOSLogger.DEBUG9);
		byte[] bytes = null;
		try {
			InputStream is = new FileInputStream(file);

			long length = file.length();

			bytes = new byte[(int)length];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "+file.getName());
			}        
			is.close();

		} catch (Exception e){
			FTPProfile.log("error in "+ sos.util.SOSClassUtil.getMethodName() + ", cause: " + e.toString(), 1);    		
		}
		return bytes;
	}

	/**
	 * L�scht die Profilname aus der Konfigurationsdatei
	 * @param profilename
	 * @throws Exception
	 */
	private void deleteProfile(String profilename) throws Exception {
		FTPProfile.log("calling " + sos.util.SOSClassUtil.getMethodName(), SOSLogger.DEBUG9);
		try {

			setCurrProfileName(profilename);			
			String filename = configFile;


			byte[] b = getBytesFromFile(new File(filename));
			String s = new String(b);

			int pos1 = s.indexOf("[profile "+profilename+"]");
			int pos2 = s.indexOf("[", pos1+1);

			if(pos1 == -1 ) {
				pos1 = s.length();
				pos2 = -1;
				return;
			}

			if(pos2 == -1)
				pos2 = s.length();

			String s2 = s.substring(0, pos1) + s.substring(pos2);

			java.nio.ByteBuffer bbuf = java.nio.ByteBuffer.wrap(s2.getBytes());

			java.io.File file = new java.io.File(filename);

			boolean append = false;

			java.nio.channels.FileChannel wChannel = new java.io.FileOutputStream(file, append).getChannel();

			wChannel.write(bbuf);

			wChannel.close();

		} catch (java.io.IOException e) {
			hasError = true;	
			FTPProfile.log("error in "+ sos.util.SOSClassUtil.getMethodName() + ", cause: " + e.toString(), 1);    		

		} 
	}

	/**
	 * Speicher die FTPProfile in die Konfigurationsdatei
	 * @param savePassword
	 * @throws Exception
	 */
	public void saveProfile(boolean savePassword) throws Exception{
		try {
			
			FTPProfile profile = getCurrProfile();
			String filename = configFile;
			String profilename = currProfileName;

			byte[] b = getBytesFromFile(new File(filename));
			String s = new String(b);
		
			int pos1 = s.indexOf("[profile "+profilename+"]");
			int pos2 = s.indexOf("[", pos1+1);

			if(pos1 == -1 ) {		
				pos1 = s.length();
				pos2 = -1;
			}

			if(pos2 == -1)
				pos2 = s.length();

			String s2 = s.substring(0, pos1);
			
			s2 = s2 + "[profile " + profilename + "]\n\n";
			s2 = s2 + "host=" + sosString.parseToString(profile.getHost()) + "\n";
			s2 = s2 + "port=" + sosString.parseToString(profile.getPort()) + "\n";
			s2 = s2 + "user=" + sosString.parseToString(profile.getUser()) + "\n";
			try {
				if(savePassword && sosString.parseToString(profile.getPassword()).length() > 0) {				
					
					String encrypt =  "";
					if(sosString.parseToString(profile.getPassword()).endsWith("=")) {//ist bereits encrypted
						encrypt = sosString.parseToString(profile.getPassword());
					} else {
						String pass = profile.getPass(profilename);
						if(pass.length() > 8) {
							pass = pass.substring(pass.length()-8);
						}
						encrypt = SOSProfileCrypt.encrypt(pass , sosString.parseToString(profile.getPassword()));
					}
					s2 = s2 + "password=" +encrypt + "\n";

					profile.setPassword(encrypt);

					this.password =encrypt;

					getProfiles().put(profilename, profile);
				}
			} catch(Exception e) {

				throw e;
			}			
			s2 = s2 + "root=" + sosString.parseToString(profile.getRoot()) + "\n";
			s2 = s2 + "localdirectory=" + sosString.parseToString(profile.getLocaldirectory()) + "\n";
			s2 = s2 + "transfermode=" + sosString.parseToString(profile.getTransfermode()) + "\n";    		 
			s2 = s2 + "save_password=" +profile.isSavePassword() + "\n";
			s2 = s2 + "protocol=" + sosString.parseToString(profile.getProtocol()) + "\n";
			s2 = s2 + "use_proxy=" + profile.getUseProxy() + "\n";
			s2 = s2 + "proxy_server=" + sosString.parseToString(profile.getProxyServer()) + "\n";
			s2 = s2 + "proxy_port=" + sosString.parseToString(profile.getProxyPort()) + "\n";
			//auth_method=publickey oder password oder both
			s2 = s2 + "auth_method=" + sosString.parseToString(profile.getAuthMethod()) + "\n";
			s2 = s2 + "auth_file=" + sosString.parseToString(profile.getAuthFile()) + "\n";    		     		 
			s2 = s2 + "\n\n";	

			s2 = s2 + s.substring(pos2);

			// System.out.println("+++++++++++++++++++++++++++++++++++");			
			// System.out.println(s2);
			// System.out.println("+++++++++++++++++++++++++++++++++++");

			java.nio.ByteBuffer bbuf = java.nio.ByteBuffer.wrap(s2.getBytes());


			java.io.File file = new java.io.File(filename);

			boolean append = false;

			java.nio.channels.FileChannel wChannel = new java.io.FileOutputStream(file, append).getChannel();

			wChannel.write(bbuf);

			wChannel.close();

		} catch (Exception e) {

			hasError = true;
			FTPProfile.log("error in "+ sos.util.SOSClassUtil.getMethodName() + "could not save configurations File: " + configFile + ", cause: " + e.toString(), SOSLogger.WARN);    				
			FTPProfileDialog.message("could not save configurations File: " + configFile + ": cause:\n" + e.getMessage(), SWT.ICON_WARNING);    		 
		} 
	}	

	/**
	 * Auswahlliste
	 * @param cboConnectname_
	 */
	public void setConnectionsname(Combo cboConnectname_) {
		cboConnectname = cboConnectname_;
	}

	/**
	 * Verzeichnis
	 * @param txtPath
	 */
	public void setRemoteDirectory(Text txtPath) {
		this.txtPath = txtPath;
	}


	private void removeFromProfilenames(String profileName) throws Exception{
		ArrayList l = new ArrayList() ;		 
		for(int i = 0; i < profileNames.length ; i++) {
			if(!profileNames[i].equalsIgnoreCase(profileName)) {
				l.add(prefix + profileNames[i]);
			}
		}

		profileNames = convert(l.toArray());
	}

	/** 
	 * Der Password nicht ini Datei vorhanden. D.h. in Pop Up Fenster nachfragen
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Der Password nicht ini Datei vorhanden. D.h. in Pop Up Fenster nachfragen
	 * @param password
	 */
	public void setPassword(String password) {		
		this.password = password;
	}

	/**
	 * Sind FTP Fehler entsanden?
	 * @return
	 */
	public boolean hasError() {
		return hasError;
	}

	public static String getErrorMessage(Exception ex) {
		Throwable tr = ex.getCause();

		String s = "";

		if(ex.toString() != null)
			s = ex.toString();

		while (tr != null){
			if(s.indexOf(tr.toString()) == -1)
				s = (s.length() > 0 ? s + ", " : "") + tr.toString();
			tr = tr.getCause();
		}
		return s;
	}

	/**
	 * Aktualisieren der Auswahlliste
	 */
	public void refresh() {
		if(cboConnectname != null && cboConnectname.getText().length() > 0 && currProfile != null ) {
			cboConnectname.setItems(getProfileNames());
			cboConnectname.setText(currProfileName);
			if(txtPath != null && currProfile != null && currProfile.getRoot() != null)
				txtPath.setText(currProfile.getRoot());
		}
	}
}

