package com.test.vendor.sqlite;

import com.jsql.model.InjectionModel;
import com.jsql.model.exception.JSqlException;
import com.jsql.view.terminal.SystemOutTerminal;
import org.junitpioneer.jupiter.RetryingTest;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;

public class SqliteBlindSuiteIT extends ConcreteSqliteSuiteIT {
    
    @Override
    public void setupInjection() throws Exception {
        
        InjectionModel model = new InjectionModel();
        this.injectionModel = model;

        model.subscribe(new SystemOutTerminal());

        model.getMediatorUtils().getParameterUtil().initializeQueryString("http://localhost:8080/blind");
        model.getMediatorUtils().getParameterUtil().setListQueryString(Arrays.asList(
            new SimpleEntry<>("tenant", "sqlite"),
            new SimpleEntry<>("name", "1'*")
        ));
        
        model.setIsScanning(true);
        
        model
        .getMediatorUtils()
        .getConnectionUtil()
        .withMethodInjection(model.getMediatorMethod().getQuery())
        .withTypeRequest("GET");
        
        model.getMediatorVendor().setVendorByUser(model.getMediatorVendor().getSqlite());
        model.beginInjection();
    }
    
    @Override
    @RetryingTest(3)
    public void listDatabases() throws JSqlException {
        super.listDatabases();
    }
    
    @Override
    @RetryingTest(3)
    public void listTables() throws JSqlException {
        super.listTables();
    }
    
    @Override
    @RetryingTest(3)
    public void listColumns() throws JSqlException {
        super.listColumns();
    }
    
    @Override
    @RetryingTest(3)
    public void listValues() throws JSqlException {
        super.listValues();
    }
}
