package com.workfinder.workfinder.Service;

import com.workfinder.workfinder.Data.Matchningslista;

/**
 * Created by Kim-Christian on 2017-08-28.
 */

public interface MatchningServiceCallback {
    void serviceSuccess(Matchningslista matchningslista);
    void serviceFailure(Exception exception);
}
