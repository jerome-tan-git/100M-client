package com.jerome;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseConnector {
	private static HBaseConnector hbase;
	private static Configuration conf = null;
	private static int msgTTL = 168;
	private NumberFormat formatter = NumberFormat.getNumberInstance();

	static {
		conf = HBaseConfiguration.create();
	}

	public static HBaseConnector getInstance() {
		if (HBaseConnector.hbase == null) {
			try {
				HBaseConnector.hbase = new HBaseConnector();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return HBaseConnector.hbase;
	}

	public HBaseConnector() throws IOException {
		formatter.setMinimumIntegerDigits(13);
		formatter.setGroupingUsed(false);
		try {
			msgTTL = Integer.parseInt(SystemProperties.getInstance()
					.getProperty("hbase.msg.TTL.hr"));
		} catch (Exception e) {

		}
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (!admin.tableExists(SystemProperties.getInstance().getProperty(
				"hbase.usertable"))) {
			HTableDescriptor tableDescriptor = new HTableDescriptor(
					SystemProperties.getInstance().getProperty(
							"hbase.usertable"));
			// tableDescriptor.addFamily(new HColumnDescriptor("userID"));
			HColumnDescriptor serverIDCol = new HColumnDescriptor("serverID");
			serverIDCol.setMaxVersions(1);
			tableDescriptor.addFamily(serverIDCol);

			HColumnDescriptor lastAccessTimeCol = new HColumnDescriptor(
					"lastAccessTime");
			lastAccessTimeCol.setMaxVersions(1);
			tableDescriptor.addFamily(lastAccessTimeCol);

			admin.createTable(tableDescriptor);
		}

		if (!admin.tableExists(SystemProperties.getInstance().getProperty(
				"hbase.usermessage"))) {
			HTableDescriptor tableDescriptor = new HTableDescriptor(
					SystemProperties.getInstance().getProperty(
							"hbase.usermessage"));
			// tableDescriptor.addFamily(new
			// HColumnDescriptor("userID_timestamp"));
			HColumnDescriptor fromUserCol = new HColumnDescriptor("fromUser");
			fromUserCol.setMaxVersions(1);
			fromUserCol.setTimeToLive(msgTTL * 3600 * 1000);
			tableDescriptor.addFamily(new HColumnDescriptor(fromUserCol));

			HColumnDescriptor messageCol = new HColumnDescriptor("message");
			messageCol.setMaxVersions(1);
			messageCol.setTimeToLive(msgTTL * 3600 * 1000);
			tableDescriptor.addFamily(new HColumnDescriptor("message"));

			HColumnDescriptor timestampCol = new HColumnDescriptor("timestamp");
			timestampCol.setMaxVersions(1);
			timestampCol.setTimeToLive(msgTTL * 3600 * 1000);
			tableDescriptor.addFamily(new HColumnDescriptor("timestamp"));

			admin.createTable(tableDescriptor);
		}
		admin.close();
	}

	public String getServerIDByUserID(String _userID) {
		String serverID = null;
		HTableInterface table = null;
		try {
			table = HBaseConnectionPool.getHtable(SystemProperties
					.getInstance().getProperty("hbase.usertable"));
			Get g = new Get(Bytes.toBytes(_userID));
			Result a = table.get(g);
			// serverID =
			// a.getValue(Bytes.toBytes("serverID"),Bytes.toBytes("serverID"));
			for (KeyValue kv : a.raw()) {
				if (new String(kv.getFamily()).equals("serverID")) {
					serverID = new String(kv.getValue());
					break;
				}
			}
		} catch (IOException e) {
			return serverID;
		} finally {
			if (table != null) {
				try {
					table.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return serverID;
	}

	public void setServerIDByUserID(String _userID, String _serverID) {
		HTableInterface table = null;
		try {
			table = HBaseConnectionPool.getHtable(SystemProperties
					.getInstance().getProperty("hbase.usertable"));
			Put p = new Put(Bytes.toBytes(_userID));
			p.add(Bytes.toBytes("serverID"), Bytes.toBytes(""),
					Bytes.toBytes(_serverID));
			p.add(Bytes.toBytes("lastAccessTime"), Bytes.toBytes(""),
					Bytes.toBytes(System.currentTimeMillis() + ""));
			table.put(p);
		} catch (IOException e) {

		} finally {
			if (table != null) {
				try {
					table.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public String addUserMessage(String _userID, String _message,
			String _fromUser) {
		HTableInterface table = null;
		String msgID = null;
		try {
			table = HBaseConnectionPool.getHtable(SystemProperties
					.getInstance().getProperty("hbase.usermessage"));
			msgID = _userID + "_" + System.currentTimeMillis();
			Put p = new Put(Bytes.toBytes(msgID));
			p.add(Bytes.toBytes("fromUser"), Bytes.toBytes(""),
					Bytes.toBytes(_fromUser));
			p.add(Bytes.toBytes("message"), Bytes.toBytes(""),
					Bytes.toBytes(_message));
			p.add(Bytes.toBytes("timestamp"), Bytes.toBytes(""),
					Bytes.toBytes(System.currentTimeMillis() + ""));
			table.put(p);

		} catch (IOException e) {

		} finally {
			if (table != null) {
				try {
					table.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return msgID;
	}

	public ResultScanner getAllUserMessage(String _userID) {
		return this.getUserMessage(_userID, 0, 9999999999999L);
	}

	public ResultScanner getUserMessage(String _userID, long _fromTime,
			long _toTime) {
		ResultScanner result = null;
		HTableInterface table = null;
		try {
			table = HBaseConnectionPool.getHtable(SystemProperties
					.getInstance().getProperty("hbase.usermessage"));
			Scan s = new Scan();
			s.setCaching(10000);

			s.setStartRow(Bytes.toBytes(_userID + "_"
					+ formatter.format(_fromTime)));
			s.setStopRow(Bytes.toBytes(_userID + "_"
					+ formatter.format(_toTime)));
			result = table.getScanner(s);

		} catch (IOException e) {

		} finally {
			if (table != null) {
				try {
					table.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public void removeMessage(String _messageID) {
		HTableInterface table = null;
		try {
			table = HBaseConnectionPool.getHtable(SystemProperties
					.getInstance().getProperty("hbase.usermessage"));

			Delete del = new Delete(Bytes.toBytes(_messageID));
			table.delete(del);
		} catch (IOException e) {

		} finally {
			if (table != null) {
				try {
					table.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void batchRemoveMessageByUserID(String _userID) {
		this.batchRemoveMessage(_userID, 0, 9999999999999L);
	}

	public void batchRemoveMessage(String _userID, long _fromTime, long _toTime) {
		HTableInterface table = null;
		List<Delete> IDs = new ArrayList<Delete>();
		try {
			table = HBaseConnectionPool.getHtable(SystemProperties
					.getInstance().getProperty("hbase.usermessage"));
			Scan s = new Scan();
			s.setCaching(10000);

			s.setStartRow(Bytes.toBytes(_userID + "_"
					+ formatter.format(_fromTime)));
			s.setStopRow(Bytes.toBytes(_userID + "_"
					+ formatter.format(_toTime)));
			ResultScanner result = table.getScanner(s);
			for (Result rr = result.next(); rr != null; rr = result.next()) {
				for (KeyValue kv : rr.list()) {
					Delete del = new Delete(kv.getRow());
					IDs.add(del);
					if (IDs.size() > 1000) {
						table.delete(IDs);
						IDs.clear();
					}

				}
			}
			if (IDs.size() > 0) {
				table.delete(IDs);
			}
			result.close();
		} catch (IOException e) {

		} finally {
			if (table != null) {
				try {
					table.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
