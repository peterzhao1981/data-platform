package com.mode.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mode.dao.GaDailyProcessDao;
import com.mode.entity.GaDailyProcess;
import com.mode.util.Status;

/**
 * Created by zhaoweiwei on 17/1/23.
 */
@Service
public class GaDailyProcessService {

    @Autowired
    private GaDailyProcessDao gaDailyProcessDao;

    @Transactional
    public List<GaDailyProcess> getGaDailyProcesses() {
        List<GaDailyProcess> gaDailyProcesses = gaDailyProcessDao.getDailyProcessesForUpdate
                (System.currentTimeMillis());
        System.out.println("gaDailyProcesses -> " + gaDailyProcesses.size());
        for (GaDailyProcess gaDailyProcess : gaDailyProcesses) {
            gaDailyProcess.setStatus(Status.DONE);
            gaDailyProcessDao.updateGaDailyProcess(gaDailyProcess);
            System.out.println("updated id -> " +  gaDailyProcess.getId());
        }
        return gaDailyProcesses;
    }

}
