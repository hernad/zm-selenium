/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012, 2013 Zimbra, Inc.
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
package com.zimbra.qa.selenium.projects.admin.tests.serverstatus;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.projects.admin.core.AdminCommonTest;
import com.zimbra.qa.selenium.projects.admin.ui.PageManageServerStatus;

public class NavigateServerStatus extends AdminCommonTest {
	
	public NavigateServerStatus() {
		logger.info("New "+ NavigateServerStatus.class.getCanonicalName());

		// All tests start at the "Accounts" page
		super.startingPage = app.zPageManageServerStatus;
	}
	
	/**
	 * Testcase : Navigate to Client Upload page
	 * Steps :
	 * 1. Verify navigation path -- "Home --> Monitor --> Server Status"
	 * @throws HarnessException
	 */
	@Test(	description = "Navigate to Server Status",
			groups = { "sanity" })
			public void NavigateServerStatus_01() throws HarnessException {
		
		/*
		 * Verify navigation path -- "Home --> Tools and Migraton --> Client Upload"
		 */
		ZAssert.assertTrue(app.zPageManageServerStatus.zVerifyHeader(PageManageServerStatus.Locators.HOME), "Verfiy the \"Home\" text exists in navigation path");
		ZAssert.assertTrue(app.zPageManageServerStatus.zVerifyHeader(PageManageServerStatus.Locators.MONITOR), "Verfiy the \"Monitor\" text exists in navigation path");
		ZAssert.assertTrue(app.zPageManageServerStatus.zVerifyHeader(PageManageServerStatus.Locators.SERVER_STATUS), "Verfiy the \"Server Status\" text exists in navigation path");
		
	}

}
