/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012, 2013, 2014, 2016 Synacor, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.qa.selenium.projects.admin.tests.antispamantivirusactivity;

import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.projects.admin.core.AdminCore;
import com.zimbra.qa.selenium.projects.admin.pages.PageManageAntiSpamAnitVirusActivity;

public class NavigateAntiSpamAntiVirus extends AdminCore {

	public NavigateAntiSpamAntiVirus() {
		logger.info("New "+ NavigateAntiSpamAntiVirus.class.getCanonicalName());
		super.startingPage = app.zPageManageAntispamAntiVirusActivity;
	}


	@Test (description = "Navigate to Anti-Spam/Anti-Virus Activity",
			groups = { "smoke" })

	public void NavigateAnitSpamAntiVirus_01() throws HarnessException {
		ZAssert.assertTrue(
				app.zPageManageAntispamAntiVirusActivity
						.zVerifyHeader(PageManageAntiSpamAnitVirusActivity.Locators.HOME),
				"Verfiy the 'Home' text exists in navigation path");
		ZAssert.assertTrue(
				app.zPageManageAntispamAntiVirusActivity
						.zVerifyHeader(PageManageAntiSpamAnitVirusActivity.Locators.MONITOR),
				"Verfiy the 'Monitor' text exists in navigation path");
		ZAssert.assertTrue(
				app.zPageManageAntispamAntiVirusActivity
						.zVerifyHeader(PageManageAntiSpamAnitVirusActivity.Locators.ANTISPAM_ANTIVIRUS_ACTIVITY),
				"Verfiy the 'Anti-Spam/Anti-Virus Activity' text exists in navigation path");
	}
}