package framework.items;

import framework.util.ZimbraAccount;

public class RecipientItem {

	public enum RecipientType {
		To, Cc, Bcc, From
	}
	public String emailAddress;
	public String name;
	public RecipientType type;
	
	public RecipientItem() {
	}
	
	public RecipientItem(ZimbraAccount account) {
		this(account.EmailAddress);
	}
	
	public RecipientItem(ZimbraAccount account, RecipientType type) {
		this(account.EmailAddress, type);
	}
	
	public RecipientItem(String email) {
		this(email, RecipientType.To);
	}
	
	public RecipientItem(String email, RecipientType type) {
		this.emailAddress = email;
		this.type = type;
	}
	
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(RecipientItem.class.getSimpleName()).append('\n');
		sb.append("Email: ").append(emailAddress).append('\n');
		sb.append("Name: ").append(name).append('\n');
		sb.append("Type: ").append(type).append('\n');
		return (sb.toString());
	}

}
