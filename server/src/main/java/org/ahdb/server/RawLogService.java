package org.ahdb.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.RawLog;
import org.ahdb.server.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RawLogService {

    final RawLogRepository rawLogRepository;

    public RawLog log(String type, String rawStr) {
        RawLog log = new RawLog()
                .setTime(U.timestampNow())
                .setType(type)
                .setRawStr(rawStr);
        rawLogRepository.save(log);
        return log;
    }

}
