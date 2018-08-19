package com.datahipster.app.service;

import com.datahipster.app.service.dto.RowCallBackHandler;
import com.datahipster.app.web.rest.json.AWSDataSource;
import com.datahipster.app.web.rest.json.AWSField;
import com.datahipster.app.web.rest.json.AWSSchema;
import com.datahipster.app.web.rest.json.AWSTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.utility.SchemaCrawlerUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class DataSourceService {


    public Connection connect(AWSDataSource request){
        Connection conn = null;
        try {
            Properties properties = new Properties();
            properties.put("user", request.getUser());
            properties.put("password", request.getPassword());
            conn = DriverManager.getConnection(createJdbcUrl(request), properties);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(checkDataSourceConnection(conn,request)){
            request.setConnection(conn);
        }
        return conn;
    }

    public AWSDataSource getDataSourceById(int id){
        return onePlaceDao.getDataSourceById(id);
    }

    private String createJdbcUrl(AWSDataSource dataSource){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(onePlaceDao.getDataSourcePrefix(dataSource.getEngine()));
        stringBuilder.append(dataSource.getHostname());
        stringBuilder.append(":");
        stringBuilder.append(dataSource.getPort());
        stringBuilder.append("/");
        stringBuilder.append(dataSource.getSchema());
        return stringBuilder.toString();

    }

    public List<AWSSchema> listSchemas(AWSDataSource dataSource) throws SchemaCrawlerException {
        List<AWSSchema> schemas = new ArrayList<>();
        final SchemaCrawlerOptionsBuilder optionsBuilder = new SchemaCrawlerOptionsBuilder();
        optionsBuilder.withSchemaInfoLevel(SchemaInfoLevelBuilder.standard());
        final SchemaCrawlerOptions options = optionsBuilder.toOptions();
        final Catalog catalog = SchemaCrawlerUtility.getCatalog(dataSource.getConnection(),
                options);

        for (final Schema schema: catalog.getSchemas()){
            AWSSchema awsSchema = new AWSSchema();
            awsSchema.setName(schema.getFullName());
            List<AWSTable> tables = new ArrayList<>();

            for (Table table: catalog.getTables(schema)){
                AWSTable awsTable = new AWSTable();
                awsTable.setName(table.getName());
                List<AWSField> awsFields = new ArrayList<>();

                for(Column column : table.getColumns()){
                    AWSField field = new AWSField();
                    field.setType(column.getType().getJavaSqlType().getName());
                    field.setName(column.getName());
                    awsFields.add(field);
                }
                awsTable.setFields(awsFields);
                tables.add(awsTable);
            }
            awsSchema.setTables(tables);
            schemas.add(awsSchema);
        }

        return schemas;
    }

    private boolean checkDataSourceConnection(Connection connection, AWSDataSource request){
        int result = 0;
        CallableStatement cs = null;
        try{
            cs = connection.prepareCall("SELECT 1");
            ResultSet resultSet = cs.executeQuery();
            while(resultSet.next()){
                result = resultSet.getInt(1);
            }
        }catch (Exception e){
            log.warn("Failed to connect to "+request.getHostname(),e);
            return false;
        }finally {
            try {
                if(cs != null){
                    cs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    public RowCallBackHandler executeQuery(AWSDataSource awsDataSource, String call) {
        RowCallBackHandler rowCallbackHandler = new RowCallBackHandler();
        Connection connection = awsDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        if (connection == null) {
            connection = this.connect(awsDataSource);
        }
        try {
            preparedStatement = connection.prepareStatement(call);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rowCallbackHandler.processRow(rs);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return rowCallbackHandler;
    }

}
