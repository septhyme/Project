package lala;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import lala.UserInfo;

public class DBUtil {

	// �õ����ݿ�����
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/qiandao", "root", "11071258");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// ��һ����û�õ�������Ŀǰɾ����������
	public static UserInfo getUserByUid( String uid) {
		UserInfo result = null;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
		
			pstmt = con.prepareStatement("select * from teacher_information where t_id=?");
			
			pstmt.setString(1, new String(uid.getBytes("GBK"), "GBK"));
						rs = pstmt.executeQuery();
			if (rs.next()) {
				String u_name = new String(rs.getString("t_name").getBytes(
						"GBK"), "GBK");
				result = new UserInfo(u_name, Integer.parseInt(uid), u_name);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
			
		}//������Ϊֹ
	

	// ��ҳ�˼���û��Ƿ���ڼ������Ƿ���ȷ
	public static String webcheckUser(int i,String u_id, String u_pwd) {
		String result = null;
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			if(i<=7){
			st = con.createStatement();// �������
			rs = st.executeQuery("select t_name from teacher_information where t_id = '"
					+ u_id + "' and t_pwd = '" + u_pwd + "';");
			
			}
			else {
				st = con.createStatement();// �������
				rs = st.executeQuery("select s_name from student_information where s_id = '"
						+ u_id + "' and s_pwd = '" + u_pwd + "';");
			}
			
			while (rs.next()) {
				result = new String(rs.getString(1).getBytes("GBK"), "GBK");
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}
				//��ҳ��--�ϸ����û�id��������еĿγ̰��ϵ�ͬѧ������ѧ�ţ�����������ʾ
	public static  ResultSet webgetmyclass(Object i,Object u_id){
			    Connection con = getConnection();
				Statement st = null;
				ResultSet rs = null;
				String sql;
				if(i.equals(7)){
					sql="select * from xuanqianbiao xqb join student_information si on xqb.s_id=si.s_id join course_information ci on xqb.c_id=ci.c_id where t_id='"+u_id+"'order by ci.c_name,si.s_id asc  ;";
				}
				else{
					sql="select * from xuanqianbiao xqb join student_information si on xqb.s_id=si.s_id join course_information ci on xqb.c_id=ci.c_id where si.s_id='"+u_id+"';";
				}
				try {
					
					st = con.createStatement();// �������
					rs = st.executeQuery(sql);// ��ѯ���ݵ�sql���   
					 }
					catch (Exception e) {
					e.printStackTrace();
			
				}
					
			  return rs;
			  }
		 
		 //��ҳ��--�����û�ID�õ��γ����֣��ڲ˵������ʾ
	public static  ResultSet webgetmyclassname(Object i,Object u_id){
			    Connection con = getConnection(); 
				Statement st = null;
				ResultSet rs = null;

				try {
					if(i.equals(7)){
					st = con.createStatement();// �������
					rs = st.executeQuery("select * from xuanqianbiao xqb join course_information ci on xqb.c_id=ci.c_id where t_id='"+u_id+"'group by c_name;");// ��ѯ���ݵ�sql���   
					}
					else{
						st = con.createStatement();// �������
						rs = st.executeQuery("select * from xuanqianbiao xqb join course_information ci on xqb.c_id=ci.c_id where s_id='"+u_id+"'group by c_name;");// ��ѯ���ݵ�sql���   
						
					}
				
				}
					catch (Exception e) {
					e.printStackTrace();
			
				}
					
			  return rs;
			  }
		 //��ҳ�˸����û�������Ϣ
	public static String webupdateUserInfo(Object i, String u_id,String u_name,String u_pwd,String u_phone){
				Connection con = getConnection();
				PreparedStatement pstmt = null;
				try{
					if(i.equals(7)){
					pstmt = con.prepareStatement("update teacher_information set  t_name=?, t_pwd=?, t_phone=? where t_id=?;");
					pstmt.setString(1, new String(u_name.getBytes("GBK"), "GBK"));
					pstmt.setString(2, new String(u_pwd.getBytes("GBK"), "GBK"));
					pstmt.setString(3, new String(u_phone.getBytes("GBK"), "GBK"));
					pstmt.setInt(4, Integer.parseInt(u_id));
					pstmt.executeUpdate();
					}
					else{
						pstmt = con.prepareStatement("update student_information set  s_name=?, s_pwd=?, s_phone=? where s_id=?;");
						pstmt.setString(1, new String(u_name.getBytes("GBK"), "GBK"));
						pstmt.setString(2, new String(u_pwd.getBytes("GBK"), "GBK"));
						pstmt.setString(3, new String(u_phone.getBytes("GBK"), "GBK"));
						pstmt.setInt(4, Integer.parseInt(u_id));
						pstmt.executeUpdate();
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				finally{
					try{
						if(pstmt != null){
							pstmt.close();
							pstmt = null;
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					try{
						if(con != null){
							con.close();
							con = null;
						}
					}catch(Exception e){
						e.printStackTrace();
					}			
				}
				return u_name;
			}


//�ֻ��˼��ѧ���û��Ƿ���ڼ������Ƿ�ƥ��
	public static String android_check_student_user(String u_id, String u_pwd) {
String result = null;
Connection con = getConnection();
Statement st = null;
ResultSet rs = null;
try {
	st = con.createStatement();// �������
	rs = st.executeQuery("select s_name from student_information where s_id = '"
			+ u_id + "' and s_pwd = '" + u_pwd + "';");
	System.out.println("222222222222222222222" + u_id + u_pwd);

	while (rs.next()) {
		result = new String(rs.getString(1).getBytes("GBK"), "GBK");
		System.out.println("��ӡresultΪ------------" + result);
	}
} catch (Exception e) {
	e.printStackTrace();
} finally {
	try {
		if (rs != null) {
			rs.close();
			rs = null;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	try {
		if (st != null) {
			st.close();
			st = null;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	try {
		if (con != null) {
			con.close();
			con = null;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
return result;
}
//�ֻ���ע��ѧ����Ϣ

	public static String android_insert_student_user(String u_id,String u_name,String u_pwd,String u_phone,String u_phonecm){
	Connection con = getConnection();
	Statement st = null;
		try {
			u_name = new String(u_name.getBytes("GBK"), "ISO8859_1");
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			st = con.createStatement();
			String sql = "insert into student_information" +"(s_id,s_name,s_phone,s_phonecm,s_pwd) " +"values('"+u_id+"','"+u_name+"','"+u_phone+"','"+u_phonecm+"','"+u_pwd+"');";
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return u_id;
	}
	//�ֻ��˼���ʦ�û��Ƿ���ڼ������Ƿ�ƥ��
		public static String android_check_teacher_user(String u_id, String u_pwd) {
		String result = null;
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();// �������
			rs = st.executeQuery("select t_name from teacher_information where t_id = '"
					+ u_id + "' and t_pwd = '" + u_pwd + "';");
			System.out.println("222222222222222222222" + u_id + u_pwd);

			while (rs.next()) {
				result = new String(rs.getString(1).getBytes("GBK"), "GBK");
				System.out.println("��ӡresultΪ------------" + result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
		}
		//�ֻ��˲����ʦ��Ϣ
		public static String android_insert_teacher_user(String t_id,String t_name,String t_phone,String t_pwd){
			

			Connection con = getConnection();
			Statement st = null;
			try {
				t_name = new String(t_name.getBytes("GBK"), "ISO8859_1");
				
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			try {
				st = con.createStatement();
				String sql = "insert into teacher_information" +
					"(t_id,t_name,t_phone,t_pwd) " +
					"values('"+t_id+"','"+t_name+"','"+t_phone+"','"+t_pwd+"');";
				st.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				try{
					if(st != null){
						st.close();
						st = null;
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				try{
					if(con != null){
						con.close();
						con = null;
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}			
			}
			return t_id;
		}
	//��ʦ����ǩ������
	public static String open_permission(String t_id,String opms)  {

		Connection con = getConnection();
		Statement st = null;
		
		try {
			st = con.createStatement();
			String sql = "update xuanqianbiao set Qermissions = '"+opms+"' where t_id = '"+t_id+"';";
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return opms;
	}
	//��ʦ�ر�ǩ������
	public static String close_permission(String t_id,String cpms) {
		

		Connection con = getConnection();
		Statement st = null;
		try {
			cpms = new String(cpms.getBytes("GBK"), "ISO8859_1");
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			st = con.createStatement();
			String sql = "update xuanqianbiao set Qermissions= '"+cpms+"' where t_id = '"+t_id+"';";
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return cpms;
	}
	//ѧ��ˢ��ǩ��Ȩ��
	public static String SXquanxian(String sid,String qx) {
		Connection con = getConnection(); 
		Statement st = null;
		ResultSet rs = null;
		String result = null;
		System.out.println("�������ݿ⺯��"+sid+qx);
		try {
			st = con.createStatement();// �������
			rs = st.executeQuery("select c_id from xuanqianbiao where s_id = '"+ sid +"' and Qermissions ='"+ qx +"';");// ��ѯ���ݵ�sql���  
			
			while (rs.next()) {
				result = new String(rs.getString(1).getBytes("GBK"), "GBK");
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
		}
	//ѧ��ˢ�µõ��������ſγ̵�Ȩ��
	public static String SXquanxian1(String cid){
		String result = null;
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();// �������
			rs = st.executeQuery("select c_name from course_information where c_id = '"	+ cid + "';");
			System.out.println("�鿴�������Ŀγ���" + rs );
			while (rs.next()) {
				result = new String(rs.getString(1).getBytes("GBK"), "GBK");
				System.out.println("��ӡresultΪ------------" + result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//ѧ��ǩ��
	public static String QDgongneng(String str1,String str2,String str3,String str4){//����û� 1,id
		String result = null;
		String result1 = null;
		String result2 = null;
		Connection con = getConnection();
		Statement st = null;
		String s="t";
		String z="f";

		try {
				st = con.createStatement();// �������
				
				String rs1 = "select QD_number from xuanqianbiao where s_id = '"+ str2 + "' and Qermissions = '"+s+"';";
				ResultSet res = st.executeQuery(rs1);
				while (res.next()) {                    //�жϸ�ѧ���Ƿ���ǩ��Ȩ��
					result1 = res.getString(1);
					System.out.println("��ӡresult1Ϊ------------" + result1);
				}
				if (result1 == null)
				{
					result = z;               //û�оͰ�result��Ϊfֵ
					
				}
				else{
				st.executeUpdate("update xuanqianbiao set QD_number =QD_number + '"+str1+"' where s_id = '"+str2+"' and Qermissions = '"+s+"';");
				st.executeUpdate("update xuanqianbiao set GPS = '"+str3+","+str4+"' where s_id = '"+str2+"' and Qermissions = '"+s+"';");	
				//����Ϊ��Ȩ��ǩ����ѧ�����������ݸ��£���gps����д��
				String rs2 = "select QD_number from xuanqianbiao where s_id = '"+ str2 + "' and Qermissions = '"+s+"';";
				ResultSet ress = st.executeQuery(rs2);
				while (ress.next()) {
					result2 = ress.getString(1);
					System.out.println("��ӡresult2Ϊ------------" + result2);
				}
				//�жϸ����Ƿ�д��ɹ�
			
				if(result1.equals(result2)){
					result = z;
				}
				else{
					result = s;
				}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return result;//����ֵ
		}
	
		}
	