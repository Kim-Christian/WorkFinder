package com.workfinder.workfinder.Service;

import com.workfinder.workfinder.Data.Platsannons;

/**
 * Created by Kim-Christian on 2017-08-28.
 */

public interface PlatsannonsServiceCallback {
    void serviceSuccess(Platsannons platsannons);
    void serviceFailure(Exception exception);
}
