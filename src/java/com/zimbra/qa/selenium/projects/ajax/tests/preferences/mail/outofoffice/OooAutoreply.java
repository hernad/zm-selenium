/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.qa.selenium.projects.ajax.tests.preferences.mail.outofoffice;

import java.util.HashMap;
import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.items.MailItem;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCommonTest;

public class OooAutoreply extends AjaxCommonTest {
	final String autoreplyMessage = "OOO";

	public OooAutoreply() {
		logger.info("New " + OooAutoreply.class.getCanonicalName());

		// test starts in the Mail tab
		super.startingPage = app.zPageMail;

		// use an account with OOO auto-reply enabled
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				put("zimbraPrefOutOfOfficeReplyEnabled", "TRUE");
				put("zimbraPrefOutOfOfficeReply", autoreplyMessage);
			}
		};
	}

	@Test(description = "Enable auto-reply feature - Verify auto-reply message", groups = { "functional" })
	public void OooAutoreply_01() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		// Create the message data to be sent
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();

		// Send message to self
		account.soapSend("<SendMsgRequest xmlns='urn:zimbraMail'>" + "<m>"
				+ "<e t='t' a='" + account.EmailAddress + "'/>" + "<su>"
				+ subject + "</su>" + "<mp ct='text/plain'>"
				+ "<content>content"
				+ ZimbraSeleniumProperties.getUniqueString() + "</content>"
				+ "</mp>" + "</m>" + "</SendMsgRequest>");

		// Click on Mail tab
		app.zPageMain.zClickAt("id=zb__App__Mail_title", "0,0");

		// Click Get Mail button to view folder in list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		SleepUtil.sleepMedium();

		MailItem mailItem = MailItem.importFromSOAP(account, "in:inbox "
				+ autoreplyMessage);

		ZAssert.assertTrue(mailItem.dBodyText.contains(autoreplyMessage),
				"Verify auto-reply message is received");
	}
}
