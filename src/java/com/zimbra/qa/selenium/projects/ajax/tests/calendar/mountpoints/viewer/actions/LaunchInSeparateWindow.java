/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2013, 2014, 2015, 2016 Synacor, Inc.
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
package com.zimbra.qa.selenium.projects.ajax.tests.calendar.mountpoints.viewer.actions;

import java.util.Calendar;
import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.CalendarWorkWeekTest;
import com.zimbra.qa.selenium.projects.ajax.ui.SeparateWindow;
import com.zimbra.qa.selenium.projects.ajax.ui.SeparateWindow.Locators;

public class LaunchInSeparateWindow extends CalendarWorkWeekTest {

	public LaunchInSeparateWindow() {
		logger.info("New "+ LaunchInSeparateWindow.class.getCanonicalName());
		super.startingPage = app.zPageCalendar;
	}
	
	
	@Test( description = "Grantee with view rights launches grantor's calendar in the new window",
			groups = { "functional" })
			
	public void LaunchInSeparateWindow_01() throws HarnessException {
		
		String body = null;
		ZimbraAccount Owner = (new ZimbraAccount()).provision().authenticate();

		// Owner creates a folder, shares it with current user with viewer rights
		String ownerFoldername = "ownerfolder"+ ConfigProperties.getUniqueString();        
		Owner.soapSend(
					"<CreateFolderRequest xmlns='urn:zimbraMail'>"
				+		"<folder name='" + ownerFoldername +"' l='1' view='appointment'/>"
				+	"</CreateFolderRequest>");
		
		FolderItem ownerFolder = FolderItem.importFromSOAP(Owner, ownerFoldername);
		ZAssert.assertNotNull(ownerFolder, "Verify the new owner folder exists");
		
		Owner.soapSend(
					"<FolderActionRequest xmlns='urn:zimbraMail'>"
				+		"<action id='"+ ownerFolder.getId() +"' op='grant'>"
				+			"<grant d='" + app.zGetActiveAccount().EmailAddress + "' gt='usr' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		

		// Current user creates the mountpoint that points to the share
		String mountpointFoldername = "mountpoint"+ ConfigProperties.getUniqueString();
		app.zGetActiveAccount().soapSend(
					"<CreateMountpointRequest xmlns='urn:zimbraMail'>"
				+		"<link l='1' name='"+ mountpointFoldername +"' view='appointment' rid='"+ ownerFolder.getId() +"' zid='"+ Owner.ZimbraId +"'/>"
				+	"</CreateMountpointRequest>");
		
		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointFoldername);
		ZAssert.assertNotNull(mountpoint, "Verify the subfolder is available");

		// Click to Refresh button to load the mounted shared calender 
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);
		SeparateWindow window = null;
		
		try {
			// Launch shared folder in separate window through context menu
			window = (SeparateWindow)app.zTreeCalendar.zTreeItem(Action.A_RIGHTCLICK, Button.B_LAUNCH_IN_SEPARATE_WINDOW, mountpoint);
			window.zWaitForActive();
			body = window.sGetBodyText();

			// Verify launched calender in new windows shows all calender data correctly
			ZAssert.assertStringContains(body, "Day Work Week Week Month" , "Verify calender views are shown in new window");
			ZAssert.assertStringContains(body, "Sunday Monday Tuesday Wednesday Thursday Friday Saturday" , "Verify weekday names are shown in new window");
			ZAssert.assertStringContains(body, ownerFoldername, "Verify owners calender name is displayed in new window");

		} finally {
			if (window != null) {
				window.zCloseWindow();
				window = null;
			}
		}
	}

	@Test( description = "Grantee with view rights launches grantor's calendar with appt in the new window and clicks on the appt",
			groups = { "functional" })

	public void LaunchInSeparateWindow_02() throws HarnessException {

		String apptSubject = "Test";
		String apptContent = ConfigProperties.getUniqueString();
		String foldername = "folder" + ConfigProperties.getUniqueString();
		String mountpointname = "mountpoint" + ConfigProperties.getUniqueString();
		Calendar now = this.calendarWeekDayUTC;
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);

		// Create a folder to share
		FolderItem root = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.UserRoot);
		ZAssert.assertNotNull(root, "Verify the inbox is available");

		FolderItem calendarFolder = FolderItem.importFromSOAP(ZimbraAccount.AccountA(), FolderItem.SystemFolder.Calendar);

		// Create a folder to share
		ZimbraAccount.AccountA().soapSend(
				"<CreateFolderRequest xmlns='urn:zimbraMail'>"
				+		"<folder name='" + foldername + "' l='" + calendarFolder.getId() + "' view='appointment'/>"
				+	"</CreateFolderRequest>");
		FolderItem folder = FolderItem.importFromSOAP(ZimbraAccount.AccountA(), foldername);
		
		// Share the folder 
		ZimbraAccount.AccountA().soapSend(
				"<FolderActionRequest xmlns='urn:zimbraMail'>"
				+		"<action id='"+ folder.getId() +"' op='grant'>"
				+			"<grant d='"+ app.zGetActiveAccount().EmailAddress +"' gt='usr' perm='r' view='appointment'/>"
				+		"</action>"
				+	"</FolderActionRequest>");

		// Mount the shared folder at grantee
		app.zGetActiveAccount().soapSend(
				"<CreateMountpointRequest xmlns='urn:zimbraMail'>"
				+		"<link l='1' name='"+ mountpointname +"'  rid='"+ folder.getId() +"' zid='"+ ZimbraAccount.AccountA().ZimbraId +"' view='appointment' color='5'/>"
				+	"</CreateMountpointRequest>");

		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointname);

		// Create appointment
		ZimbraAccount.AccountA().soapSend(
				"<CreateAppointmentRequest xmlns='urn:zimbraMail'>"
				+		"<m l='"+ folder.getId() +"' >"
				+			"<inv method='REQUEST' type='event' status='CONF' draft='0' class='PUB' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"
				+				"<s d='"+ startUTC.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<e d='"+ endUTC.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<or a='"+ ZimbraAccount.AccountA().EmailAddress +"'/>"
				+				"<at role='REQ' ptst='NE' rsvp='1' a='" + app.zGetActiveAccount().EmailAddress + "'/>"
				+			"</inv>"
				+			"<e a='"+ app.zGetActiveAccount().EmailAddress +"' t='t'/>"
				+			"<su>"+ apptSubject +"</su>"
				+			"<mp content-type='text/plain'>"
				+				"<content>" + apptContent + "</content>"
				+			"</mp>"
				+		"</m>"
				+	"</CreateAppointmentRequest>");
		
		// Verify appointment exists in current view
        ZAssert.assertTrue(app.zPageCalendar.zVerifyAppointmentExists(apptSubject), "Appointment not displayed in current view");


		// Launch shared folder in separate window through context menu
		SeparateWindow window = (SeparateWindow)app.zTreeCalendar.zTreeItem(Action.A_RIGHTCLICK, Button.B_LAUNCH_IN_SEPARATE_WINDOW, mountpoint);

		try { 
			window.zWaitForActive();
			String body = window.sGetBodyText();

			// Verify launched calender in new windows shows all calender data correctly
			ZAssert.assertStringContains(body, "Day Work Week Week Month" , "Verify calender views are shown in new window");
			ZAssert.assertStringContains(body, "Sunday Monday Tuesday Wednesday Thursday Friday Saturday" , "Verify weekday names are shown in new window");
			ZAssert.assertStringContains(body, foldername, "Verify owners calender name is displayed in new window");
			 
			
			// Verify aapointment on launched calender in new windows is clickable and shows appointment details correctly
			window.zWaitForActive();
			window.sClickAt(Locators.openApptOnLaunchedWindow, "0,0");
			SleepUtil.sleepMedium();
			window.zWaitForActive();
			
			String bodyOfAppt = window.sGetBodyText();
			ZAssert.assertStringContains(bodyOfAppt , "Close" , "Verify appt shows Close label");
			ZAssert.assertStringContains(bodyOfAppt , "Subject :" , "Verify appt shows subject header");
			ZAssert.assertStringContains(bodyOfAppt , apptSubject , "Verify appt shows correct subject");
			ZAssert.assertStringContains(bodyOfAppt , app.zGetActiveAccount().EmailAddress , "Verify appt shows correct Email Address");
			ZAssert.assertStringContains(bodyOfAppt , apptContent , "Verify appt shows correct appt content");

		} finally {
			if (window != null) {
				window.zCloseWindow();
				window = null;
			}
		}
	}

}
