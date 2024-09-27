package com.hallio.test;

import com.hallio.dms.FileManager;
import com.hallio.hms.HallManager;
import com.hallio.hms.schedule.ScheduleManager;

public class testExecutor {
    public static void main(String[] args){
        FileManager.createEnvironment();
        ScheduleManager.createDatabase();
        HallManager.createDatabase();
    }
}
