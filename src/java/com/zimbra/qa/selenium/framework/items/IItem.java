/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2013 Zimbra, Inc.
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
package com.zimbra.qa.selenium.framework.items;

import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;

public interface IItem {

	
	/**
	 * Get the name of this item, such as subject, fileas, folder name, etc.
	 */
	public String getName();
	
	/**
	 * Create an object on the Zimbra server based on the object values
	 * @param account - the account used to create the object
	 * @throws HarnessException
	 */
	public void createUsingSOAP(ZimbraAccount account) throws HarnessException;
	
	/**
	 * Create a string version of this object suitable for using with a logger
	 */
	public String prettyPrint();

}
