package lala;

import java.sql.Array;

import javax.sql.DataSource;

public class UserInfo {
	int u_id;
	String u_name;
	int u_phone;
	String si;
	public UserInfo(String si, int uid, String uName) {
		super();
		this.u_id = uid;
		u_name = uName;
		
	}
	public int getUid() {
		return u_id;
	}
	public int getu_phone() {
		return  u_phone;
	}
	public String getu_name() {
		return u_name;
	}

	}

