package com.me.security.common.exception;

import com.me.security.common.code.ServerCode;

public class InvalidDataException extends SourceRootException{

    public InvalidDataException() {
        super(ServerCode.BAD_REQUEST);
    }

    public InvalidDataException(ServerCode code) {
        super(code);
    }

    public InvalidDataException(ServerCode code, String message) {
        super(code, message);
    }
}
