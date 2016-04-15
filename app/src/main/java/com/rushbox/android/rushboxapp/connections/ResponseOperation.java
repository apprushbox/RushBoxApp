package com.rushbox.android.rushboxapp.connections;

/**
 * Created by Ronner on 08-04-2016.
 */
public class ResponseOperation {
    boolean resultOperation;
    String message;

    public ResponseOperation(boolean resulOperation, String message) {
        this.resultOperation = resulOperation;
        this.message = message;
    }

    public boolean isResultOperation() {
        return resultOperation;
    }

    public String getMessage() {
        return message;
    }
}
