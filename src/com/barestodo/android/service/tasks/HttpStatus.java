package com.barestodo.android.service.tasks;

import android.content.res.Resources;

import static com.barestodo.android.R.string.*;

public enum HttpStatus {
    NOT_FOUND(not_found),BAD_REQUEST(invalid_request),FORBIDEN(authentication_problem),SERVER_ERROR(service_unavailable);

    private int labelId;

    HttpStatus(int labelId) {
        this.labelId=labelId;
    }


    public String getErrorMessage() {
        return Resources.getSystem().getString(labelId);
    }
}
