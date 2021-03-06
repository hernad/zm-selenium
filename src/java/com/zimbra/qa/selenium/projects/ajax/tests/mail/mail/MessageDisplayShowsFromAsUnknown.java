/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2016 Synacor, Inc.
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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.mail;

import java.util.HashMap;
import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ConfigProperties;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCore;

public class MessageDisplayShowsFromAsUnknown extends AjaxCore {

	@SuppressWarnings("serial")
	public MessageDisplayShowsFromAsUnknown() {
		logger.info("New "+ MessageDisplayShowsFromAsUnknown.class.getCanonicalName());

		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = new HashMap<String, String>() {{
			put("zimbraPrefGroupMailBy", "message");
			put("zimbraPrefMessageViewHtmlPreferred", "TRUE");
		}};
	}


	@Bugs (ids = "16213")
	@Test (description = "Verify bug 16213 - Message display should show From=Unknown",
			groups = { "sanity" })

	public void MessageDisplayShowsFromAsUnknown_01() throws HarnessException {

		String subject = "Encoding test";
		String from = "Unknown";
		String mimeFile = ConfigProperties.getBaseDirectory() + "/data/public/mime/Bugs/Bug16213/bug16213att4501.txt";

		// Inject the sample mime
		injectMessage(app.zGetActiveAccount(), mimeFile);

		// Refresh current view
		app.zPageMail.zToolbarPressButton(Button.B_REFRESH);

		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		ZAssert.assertEquals(app.zPageMail.sGetText(
				"css=div[id='zv__TV-main'] div[id='zl__TV-main'] ul[id='zl__TV-main__rows'] div[class^='TopRow'] div span")
				.trim(), from, "Verify the default string for 'From' is 'Unknown'");
	}
}