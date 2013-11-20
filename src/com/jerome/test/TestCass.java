package com.jerome.test;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import me.prettyprint.cassandra.model.BasicColumnDefinition;
import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnIndexType;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class TestCass {
	protected static Cluster tutorialCluster;
	private Cluster cluster;
	private final String DYN_KEYSPACE = "logdb";
	private final String DYN_CF = "user";
	private StringSerializer stringSerializer = StringSerializer.get();
	private LongSerializer longSerializer = LongSerializer.get();

	public TestCass() {
		CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator(
				"192.168.103.18:9160,192.168.103.5:9160");
		cassandraHostConfigurator.setMaxActive(20);

		cassandraHostConfigurator.setCassandraThriftSocketTimeout(3000);
		cassandraHostConfigurator.setMaxWaitTimeWhenExhausted(4000);
		cluster = HFactory.createCluster("cluster_name",
				cassandraHostConfigurator);

	}

	public void createSchema() {

		ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("MyKeyspace",
                "ColumnFamilyName",
                ComparatorType.BYTESTYPE);

		KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition("MyKeyspace_1",
		              ThriftKsDef.DEF_STRATEGY_CLASS,
		              2,
		              Arrays.asList(cfDef));
		System.out.println(1);
		cluster.addKeyspace(newKeyspace, true);
		System.out.println(2);		
		cluster.getConnectionManager().shutdown();
	}

	// private final static String HOST_PORT =
	// "192.168.103.18:9160,192.168.103.5:9160,192.168.103.216:9160,192.168.103.215:9160";
	public static void main(String[] args) throws InvalidRequestException,
			TException {
		new TestCass().createSchema();
		;
		// tutorialCluster =
		// HFactory.getOrCreateCluster("a","192.168.103.18:9160");
		// ConfigurableConsistencyLevel ccl = new
		// ConfigurableConsistencyLevel();
		// ccl.setDefaultReadConsistencyLevel(HConsistencyLevel.ONE);
		// System.out.println(tutorialCluster.describeClusterName());
		// System.out.println(tutorialCluster.describeKeyspaces());

	}
}
