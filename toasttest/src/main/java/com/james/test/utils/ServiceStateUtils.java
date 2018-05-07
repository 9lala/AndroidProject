package com.james.test.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import java.util.Iterator;
import java.util.List;

public class ServiceStateUtils
{
    public static boolean isRunning(Context paramContext, Class<? extends Service> paramClass)
    {
        Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(2147483647).iterator();
        do
            if (!localIterator.hasNext())
                return false;
        while (!((ActivityManager.RunningServiceInfo)localIterator.next()).service.getClassName().equals(paramClass.getName()));
        return true;
    }
}