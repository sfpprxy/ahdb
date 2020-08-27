package org.ahdb.service;

import org.ahdb.model.RawLog;
import org.ahdb.repo.RawLogRepository;
import org.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RawLogService {

    private static final Logger log = LoggerFactory.getLogger(RawLogService.class);

    @Inject
    RawLogRepository rawLogRepository;

    public RawLog log(String type, String rawStr) {
        RawLog log = new RawLog()
                .setTime(U.timestampNow())
                .setType(type)
                .setRawStr(rawStr);
        rawLogRepository.save(log);
        return log;
    }

}
