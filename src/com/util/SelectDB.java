package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class SelectDB{

	String dbUrl="jdbc:oracle:thin:@10.196.13.6:1521:ytcxcdora";

	String theUser="echannel"; ;

	String thePw="passw0rd";  

	Connection c=null;

	Statement conn;

	ResultSet rs=null;

	public SelectDB()

	{

		try{

		
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();

			c = DriverManager.getConnection(dbUrl,theUser,thePw);

			conn=c.createStatement();

		}catch(Exception e){

			e.printStackTrace();

		}

	}

	public boolean executeUpdate(String sql)

	{

		try

		{

			conn.executeUpdate(sql);

			return true;

		}

		catch (SQLException e)

		{

			e.printStackTrace();

			return false;

		}

	}

	public ResultSet executeQuery(String sql)

	{

		rs=null;

		try

		{

			rs=conn.executeQuery(sql);

		}

		catch (SQLException e)

		{

			e.printStackTrace();

		}

		return rs;

	}

	public void close()
	{
		try
		{
			conn.close();
			c.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static String getProposalNo(String outProposalNo){
		ResultSet rs;
		SelectDB conn = new SelectDB();
		String outProp="";
		rs=conn.executeQuery("select  d.policy_no  from  tbl_policy  d where d.policy_app_no='"
				+ outProposalNo
				+ "'");
		try{
			
			while (rs.next())
			{
				outProp=rs.getString("policy_no");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return outProp;
	}

	public static String getNewProposalNo(String outProposalNo,String username){
		ResultSet rs;
		SelectDB conn = new SelectDB();
		String outProp="";
		String sql ="select tt.policy_no from"
				+ " (select aa.policy_no,aa.creator,  row_number() over(order by aa.created_date desc) as rn "
				+ " from (     select a.policy_no, a.id, a.created_date ,a.creator   "
				+ "from tbl_policy a, tbl_policy b   "
				+ " where b.policy_no = '"
				+ outProposalNo
				+ "'   "
				+ "   and a.creator = b.creator      "
				+ "   and a.Coverage_Code = b.Coverage_Code   "
				+ " and a.APP_TYPE = b.APP_TYPE  "
				+ "  and a.insured_name = b.insured_name   "
				+ "  and         to_char(a.end_date, 'yyyy-mm-dd') >      to_char((select b.end_date    "
				+ " from tbl_policy b          "
				+ " where b.policy_no = '"
				+ outProposalNo
				+ "'),         'yyyy-mm-dd')  "
				+ "   and a.policy_no is not null) aa    inner join tbl_policy_vehicle b  on aa.id = b.policy_id where b. plate_no = (select plate_no   from tbl_policy a    inner join tbl_policy_vehicle c     on a.id = c.policy_id     where a.policy_no = '"
				+ outProposalNo
				+ "')  and b.eng_no =(select eng_no    from tbl_policy a    inner join tbl_policy_vehicle c      on a.id = c.policy_id  where a.policy_no = '"
				+ outProposalNo
				+ "') and b.frm_no=(select frm_no    from tbl_policy a    inner join tbl_policy_vehicle c     on a.id = c.policy_id      where a.policy_no = '"
				+ outProposalNo
				+ "')) tt  where tt.rn = 1 and  tt.creator='"
				+ username
				+ "'";
		rs=conn.executeQuery(sql);
		try{
			if (rs.next())
			{
				outProp=rs.getString("policy_no");
			}else {
				outProp="最初保单";
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return outProp;
	}

	
	
}
