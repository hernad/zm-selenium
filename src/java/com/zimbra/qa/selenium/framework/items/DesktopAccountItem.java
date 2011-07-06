package com.zimbra.qa.selenium.framework.items;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;

public class DesktopAccountItem implements IItem {
   protected static Logger logger = LogManager.getLogger(IItem.class);
   public String accountName = null;
   public String fullName = null;
   public String emailAddress = null;
   public String password = null;
   public String incomingServer = null;
   public String port = null;
   public boolean ssl = false;
   
   public DesktopAccountItem() {
      super();
   }

   /**
    * Generate Desktop's Zimbra account Item with specified email address
    * @param emailAddress Email Address of Zimbra Account
    * @param password Password of Zimbra Account
    * @param port Port to access the Zimbra Accounr
    * @param incomingServer Zimbra Mail Server, if null, then it will pick the default from config.properties
    * @param ssl SSL triggered or not
    * @return Desktop Zimbra Account Item
    */
   public static DesktopAccountItem generateDesktopZimbraAccountItem(String emailAddress,
         String password, String port, String incomingServer, boolean ssl) {
      DesktopAccountItem desktopAccountItem = new DesktopAccountItem();
      desktopAccountItem.accountName = "name" + ZimbraSeleniumProperties.getUniqueString();
      desktopAccountItem.emailAddress = emailAddress;
      desktopAccountItem.password = password;
      desktopAccountItem.incomingServer = (incomingServer == null) ?
            ZimbraAccount.AccountZWC().ZimbraMailHost : incomingServer;
      desktopAccountItem.port = port;
      desktopAccountItem.ssl = ssl;

      return desktopAccountItem;
   }

   /**
    * Generate Desktop's Zimbra account Item with specified email address with default incoming server
    * @param emailAddress Email Address of Zimbra Account
    * @param password Password of Zimbra Account
    * @param port Port to access the Zimbra Accounr
    * @param ssl SSL triggered or not
    * @return Desktop Zimbra Account Item
    */
   public static DesktopAccountItem generateDesktopZimbraAccountItem(String emailAddress,
         String password, String port, boolean ssl) {

      return generateDesktopZimbraAccountItem(emailAddress, password, port, null, ssl);
   }

   /**
    * Generate Desktop's Yahoo account Item with specified email address
    * @param emailAddress Email Address of Yahoo Account
    * @param password Password of Yahoo Account
    * @return Desktop Yahoo Account Item
    */
   public static DesktopAccountItem generateDesktopYahooAccountItem(String emailAddress,
         String password) {
      DesktopAccountItem desktopAccountItem = new DesktopAccountItem();
      desktopAccountItem.accountName = "name" + ZimbraSeleniumProperties.getUniqueString();
      desktopAccountItem.fullName = "Yahoo" + ZimbraSeleniumProperties.getUniqueString();
      desktopAccountItem.emailAddress = emailAddress;
      desktopAccountItem.password = password;

      return desktopAccountItem;
   }

   /**
    * Generate Desktop's Gmail account Item with specified email address
    * @param emailAddress Email Address of Gmail Account
    * @param password Password of Gmail Account
    * @return Desktop Gmail Account Item
    */
   public static DesktopAccountItem generateDesktopGmailAccountItem(String emailAddress,
         String password) {
      DesktopAccountItem desktopAccountItem = new DesktopAccountItem();
      desktopAccountItem.accountName = "name" + ZimbraSeleniumProperties.getUniqueString();
      desktopAccountItem.fullName = "Gmail" + ZimbraSeleniumProperties.getUniqueString();
      desktopAccountItem.emailAddress = emailAddress;
      desktopAccountItem.password = password;

      return desktopAccountItem;
   }

   @Override
   public void createUsingSOAP(ZimbraAccount account) throws HarnessException {
      throw new HarnessException("Can't create desktop account using SOAP!");
   }

   @Override
   public String getName() {
      return accountName;
   }

   @Override
   public String prettyPrint() {
      StringBuilder sb = new StringBuilder();
      sb.append(DesktopAccountItem.class.getSimpleName()).append('\n');
      sb.append("accountName: ").append(accountName).append('\n');
      sb.append("emailAddress: ").append(emailAddress).append('\n');
      sb.append("password: ").append(password).append('\n');
      sb.append("incomingServer: ").append(incomingServer).append('\n');
      sb.append("port: ").append(port).append('\n');
      sb.append("ssl: ").append(ssl).append('\n');
      return (sb.toString());
   }
}
