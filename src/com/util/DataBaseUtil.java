package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

public class DataBaseUtil {
	static Statement queryWorkerStatement;
	static ResultSet queryWorkerResultset;
	static ResultSetMetaData queryWorkerResultSetMetaData;
	static Connection conn = null;
	private static String errorMesage = null;
	private static String new_ln;
	public static StringBuffer blankBuffer, sepBuffer;
	
	public static Connection getDatabaseConnection(String jdbcUrl, String jdbcDriver,String user, String password) {

		Connection conn = null;
		try {
			Class.forName(jdbcDriver);
			Properties properties = new Properties();
			properties.setProperty("user", user);
			properties.setProperty("password", password);
			conn = DriverManager.getConnection(jdbcUrl, properties);
			setConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void setConnection(Connection connn) {

		conn = connn;
		try {
			queryWorkerStatement = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public static String executeSql(String sql) {
		String results = null;
		if (isNotSelectSQL(sql)) {
			results = getSQlUpdateResult(sql);
			if (errorMesage != null) {
				results = errorMesage;
			}
		} else {
			Vector<?> rows = getSQlQueryResult(sql);
			if (errorMesage != null) {
				results = errorMesage;
			} else {
				results = formatSQLResultToWord(rows, getColumn());
			}
		}
		return results;
	}
	
	public static String getSQlUpdateResult(String sql) {

		String results = "NONE";
		try {
			// queryWorkerStatement = conn.createStatement();
			int resultnumber = queryWorkerStatement.executeUpdate(sql);
			results = String.valueOf(resultnumber);
			errorMesage = null;
		} catch (SQLException e) {
			e.printStackTrace();
			errorMesage = e.getMessage();
		}
		return results;
	}
	
	@SuppressWarnings("rawtypes")
	public static Vector<Vector> getSQlQueryResult(String sql) {
		Vector<Vector> rows = new Vector<Vector>();

		try {
			// queryWorkerStatement = conn.createStatement();
			queryWorkerResultset = queryWorkerStatement.executeQuery(sql);
			queryWorkerResultSetMetaData = queryWorkerResultset.getMetaData();
			rows = getAllRows(queryWorkerResultset, queryWorkerResultSetMetaData);
			errorMesage = null;
		} catch (SQLException e) {
			errorMesage = e.getMessage();
		}
		return rows;
	}
	
	@SuppressWarnings("rawtypes")
	public static Vector<Vector> getAllRows(ResultSet rs, ResultSetMetaData rsmd) {
		Vector<Vector> rows = new Vector<Vector>();
		try {
			while (rs.next()) {
				rows.addElement(getCurrentRow(rs, rsmd));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return rows;
	}
	
	public static Vector<String> getCurrentRow(ResultSet rs, ResultSetMetaData rsmd) {
		Vector<String> currentRow = new Vector<String>();
		int count = 0;
		try {
			count = rsmd.getColumnCount();
			for (int i = 1; i <= count; ++i) {
				try {
					if (rs.getObject(i) == null) {
						currentRow.addElement("{null}");
					} else {
						setColumnValue(currentRow, rsmd.getColumnType(i), rs, i);
					}
				} catch (SQLException qqqq) {
					currentRow.addElement("{error}");
				}
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		}
		return currentRow;
	}
	
	public static void setColumnValue(Vector<String> currentRow, int columnType, ResultSet resultSet, int index) {
		int dataType = columnType;
		try {
			switch (dataType) {
			case Types.CHAR:
				currentRow.addElement(resultSet.getString(index));
				break;
			case Types.VARCHAR:
				currentRow.addElement(resultSet.getString(index));
				break;
			case Types.DATE:
				currentRow.addElement(resultSet.getDate(index).toString());
				break;
			case Types.TIME:
				currentRow.addElement(resultSet.getTime(index).toString());
				break;
			case Types.TIMESTAMP:
				currentRow.addElement(resultSet.getTimestamp(index).toString());
				break;
			case Types.LONGVARCHAR:
			case Types.CLOB:
				currentRow.addElement(resultSet.getString(index));
				break;
			case Types.LONGVARBINARY:
			case Types.VARBINARY:
			case Types.BINARY:
			case Types.INTEGER:
				currentRow.addElement(String.valueOf(resultSet.getInt(index)));
				break;
			case Types.DECIMAL:
				currentRow.addElement(resultSet.getBigDecimal(index).stripTrailingZeros().toPlainString());
				break;
			case Types.DOUBLE:
				currentRow.addElement(String.valueOf(resultSet.getDouble(index)));
				break;
			case Types.FLOAT:
				currentRow.addElement(String.valueOf(resultSet.getFloat(index)));
				break;
			case Types.BLOB:
				currentRow.addElement(resultSet.getBlob(index).toString());
				break;
			default:
				currentRow.addElement(resultSet.getObject(index).toString());
				break;
			}
		} catch (Exception e) {
			try {
				currentRow.addElement(resultSet.getString(index));
			} catch (SQLException ee) {
				currentRow.addElement("{error}");
			}
		}
	}
	
	public static String formatSQLResultToWord(Vector<?> row, Vector<String> column) {

		StringBuffer s = new StringBuffer("");

		new_ln = System.getProperty("line.separator");

		int length = Integer.parseInt("100");
		int size = 0;

		int numCol = column.size();

		int[] width = new int[numCol];
		String[] columnName = new String[numCol];
		String[] value;
		ArrayList<String[]> valueList = new ArrayList<String[]>();

		for (int i = 0; i < numCol; i++) {
			columnName[i] = column.get(i).toString();
		}
		for (int i = 0; i < row.size(); i++) {
			value = new String[numCol];
			for (int j = 0; j < numCol; j++) {
				String v = null;
				try {
					v = ((Vector<?>) row.get(i)).get(j).toString();
				} catch (Exception e) {
					v = null;
				}
				if (v == null) {
					v = " ";
				}
				value[j] = v;
			}
			valueList.add(value);
		}

		for (int i = 0; i < numCol; i++) {
			int columnWidth = columnName[i].length();
			int valueWidth = 0;
			for (int ii = 0; ii < valueList.size(); ii++) {
				valueWidth = Math.max(valueList.get(ii)[i].length(), valueWidth);
			}
			width[i] = Math.max(columnWidth, valueWidth);
		}

		int status = -1;
		int before = 0;

		while (true) {
			for (int i = before; i < numCol; i++) {
				status = i;
				String cName = columnName[i];
				cName = appendBlank(width[i], cName) + " ";
				s = s.append(cName);
				size = size + width[i] + 1;
				if (size > length) {
					size = 0;
					break;
				}
			}
			s = s.append(new_ln);
			for (int i = before; i < (status + 1); i++) {
				String separator = "";
				separator = appendSeparator(width[i], separator) + " ";
				s = s.append(separator);
			}
			s = s.append(new_ln);
			for (int ii = 0; ii < valueList.size(); ii++) {
				for (int i = before; i < (status + 1); i++) {
					String v = valueList.get(ii)[i];
					v = appendBlank(width[i], v) + " ";
					s = s.append(v);
				}
				s = s.append(new_ln);
			}
			s = s.append(new_ln);
			before = status + 1;
			if (status == (numCol - 1)) {
				break;
			}
		}

		return s.toString();
	}
	
	public static String appendBlank(int width, String s) {

		blankBuffer = new StringBuffer(s);
		if (s.length() < width) {
			for (int i = 0; i < (width - s.length()); i++) {
				blankBuffer.append(" ");
			}
		}
		return blankBuffer.toString();
	}
	
	public static String appendSeparator(int width, String s) {

		sepBuffer = new StringBuffer(s);

		for (int i = 0; i < width; i++) {
			sepBuffer.append("-");
		}
		return sepBuffer.toString();
	}
	
	public static Vector<String> getColumn() {
		Vector<String> columnHeads = new Vector<String>();
		try {

			for (int i = 1; i <= queryWorkerResultSetMetaData.getColumnCount(); ++i) {
				if (queryWorkerResultSetMetaData.getColumnName(i) == null) {
					columnHeads.addElement(" ");
				} else {
					columnHeads.addElement(queryWorkerResultSetMetaData.getColumnName(i));
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return columnHeads;
	}
	
	public static boolean isNotSelectSQL(String sql) {

		boolean isSelect = false;

		String query = sql.replaceAll("\n", " ").toUpperCase();

		if (query.indexOf("SELECT ") == 0 && query.indexOf(" INTO ") != -1) {

			isSelect = false;

		} else if (query.indexOf("SELECT ") == 0) {

			isSelect = false;

		} else if (query.indexOf("INSERT ") == 0) {

			isSelect = true;

		} else if (query.indexOf("UPDATE ") == 0) {

			isSelect = true;

		} else if (query.indexOf("DELETE ") == 0) {

			isSelect = true;

		} else if (query.indexOf("CREATE TABLE ") == 0) {

			isSelect = true;

		} else if (query.indexOf("CREATE ") == 0) {

			isSelect = true;

		} else if (query.indexOf("CREATE ") == 0
				&& (query.indexOf("PROCEDURE ") != -1 || query.indexOf("PACKAGE ") != -1)) {

			isSelect = true;

		} else if (query.indexOf("CREATE ") == 0 && query.indexOf("FUNCTION ") != -1) {

			isSelect = true;

		} else if (query.indexOf("DROP TABLE ") == 0) {

			isSelect = true;

		} else if (query.indexOf("ALTER TABLE ") == 0) {

			isSelect = true;

		} else if (query.indexOf("CREATE SEQUENCE ") == 0) {

			isSelect = true;

		} else if (query.indexOf("CREATE SYNONYM ") == 0) {

			isSelect = true;

		} else if (query.indexOf("GRANT ") == 0) {

			isSelect = true;

		} else if (query.indexOf("EXECUTE ") == 0 || query.indexOf("CALL ") == 0) {

			isSelect = true;

		} else if (query.indexOf("COMMIT") == 0) {

			isSelect = true;

		} else if (query.indexOf("ROLLBACK") == 0) {

			isSelect = true;

		} else if (query.indexOf("EXPLAIN ") == 0) {

			isSelect = false;

		} else if (query.indexOf("DESC ") == 0 || query.indexOf("DESCRIBE ") == 0) {

			isSelect = true;

		} else if (query.indexOf("SHOW TABLES") == 0) {

			isSelect = false;

		}
		return isSelect;
	}
	
	public static void closeConnection() {

		try {
			if (queryWorkerStatement != null) {
				queryWorkerStatement.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
	}
}
