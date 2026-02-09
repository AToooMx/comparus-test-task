package com.rsa.comparus.test.task.exception;

public class JdbcQueryExecutionException extends RuntimeException {

    public JdbcQueryExecutionException(Exception ex) {
        super(ex);
    }

}
