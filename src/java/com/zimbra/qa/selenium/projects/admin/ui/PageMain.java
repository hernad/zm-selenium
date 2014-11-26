/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013, 2014 Zimbra, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.qa.selenium.projects.admin.ui;

import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsPage;
import com.zimbra.qa.selenium.framework.ui.AbsTab;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;


/**
 * This class defines the top menu bar of the admin console application
 * @author Matt Rhoades
 *
 */
public class PageMain extends AbsTab {

	public static class Locators {
		public static final String zSkinContainerLogo		= "xpath=//*[@id='skin_container_logo']";
		public static final String zSkinContainerUsername	= "css=div[id='skin_container_username']";
		//public static final String zSkinContainerLogoff		= "css=table[class='skin_table'] span[onclick='ZaZimbraAdmin.logOff();']";
		public static final String zLogoffDropDownArrow		="css=div.ImgNodeExpandedWhite";
		public static final String zLogOff = "zmi__ZA_LOGOFF__LOGOFF_title";
		public static final String zSkinContainerHelp		= "xpath=//*[@id='skin_container_help']";
		public static final String zSkinContainerDW			= "xpath=//*[@id='skin_container_dw']";
		public static final String REFRESH_BUTTON = "css=div.ImgSearchRefreshWhite";
	}

	public PageMain(AbsApplication application) {
		super(application);

		logger.info("new " + myPageName());
	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	/**
	 * If the "Logout" button is visible, assume the MainPage is active
	 */
	public boolean zIsActive() throws HarnessException {

		// Make sure the Admin Console is loaded in the browser
		if ( !MyApplication.zIsLoaded() )
			throw new HarnessException("Admin Console application is not active!");


		// Look for the Refresh Button
		boolean present = sIsElementPresent(Locators.zLogoffDropDownArrow);
		if ( !present ) {
			logger.debug("isActive() present = "+ present);
			return (false);
		}


		// Look for the Refresh Button.
		boolean visible = zIsVisiblePerPosition(Locators.zLogoffDropDownArrow, 0, 0);
		if ( !visible ) {
			logger.debug("isActive() visible = "+ visible);
			return (false);
		}

		logger.debug("isActive() = "+ true);
		return (true);
	}

	@Override
	public void zNavigateTo() throws HarnessException {

		if ( zIsActive() ) {
			// This page is already active
			return;
		}


		// 1. Logout
		// 2. Login as the default account
		if ( !((AppAdminConsole)MyApplication).zPageLogin.zIsActive() ) {
			((AppAdminConsole)MyApplication).zPageLogin.zNavigateTo();
		}
		((AppAdminConsole)MyApplication).zPageLogin.login();

		this.zWaitForActive();
	}

	/**
	 * Click the logout button
	 * @throws HarnessException
	 */
	public void logout() throws HarnessException {
		logger.debug("logout()");

		zNavigateTo();

		if ( !sIsElementPresent(Locators.zLogoffDropDownArrow) ) {
			throw new HarnessException("The refresh button is not present " + Locators.zLogoffDropDownArrow);
		}

		if ( !zIsVisiblePerPosition(Locators.zLogoffDropDownArrow, 10, 10) ) {
			throw new HarnessException("The refresh button is not visible " + Locators.zLogoffDropDownArrow);
		}

		// Click on logout
		sClickAt(Locators.zLogoffDropDownArrow,"");
		sClickAt(Locators.zLogOff,"");
		SleepUtil.sleepLong();


		/**
		 * Following WaitForPageToLoad() is needed to ensure successful log off operation.
		 */
		//sWaitForPageToLoad();
		// Sometimes there is a "confirm" popup.
		// Disable it using zimbraPrefAdminConsoleWarnOnExit=FALSE
		// This is the default configureation for the AdminConsoleAdmin() account


		((AppAdminConsole)MyApplication).zPageLogin.zWaitForActive();

		((AppAdminConsole)MyApplication).zSetActiveAcount(null);

	}

	public String getContainerUsername() throws HarnessException {
		logger.debug("getLoggedInAccount()");

		if ( !zIsActive() )
			throw new HarnessException("MainPage is not active");

		String username = sGetText(Locators.zSkinContainerUsername);
		return (username);

	}

	@Override
	public AbsPage zListItem(Action action, String item)
			throws HarnessException {
		return null;
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item)
			throws HarnessException {
		return null;
	}
	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption ,String item)
			throws HarnessException {
		return null;
	}

	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		return null;
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option)
			throws HarnessException {
		return null;
	}



}
