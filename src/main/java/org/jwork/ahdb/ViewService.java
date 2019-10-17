package org.jwork.ahdb;

import org.jwork.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.instrument.Instrumentation;
import java.util.List;

@Service
public class ViewService {
    private static final Logger log = LoggerFactory.getLogger(ViewService.class);

    @Autowired
    ViewRepository viewRepository;

    public void view() {
        U.Timer t = U.newTimer();
        long count = viewRepository.countNum();
        List<Object> lo = viewRepository.view();
        log.debug("time: {}", t.getTime());
    }
}
