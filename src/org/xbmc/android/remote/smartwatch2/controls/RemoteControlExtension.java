package org.xbmc.android.remote.smartwatch2.controls;

import org.xbmc.android.remote.smartwatch2.XBMCRemoteExtensionService;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RemoteControlExtension extends ManagedControlExtension {

    /**
     * @see ManagedControlExtension#ManagedControlExtension(Context, String,
     *      ControlManagerCostanza, Intent)
     */
    public RemoteControlExtension(Context context, String hostAppPackageName,
            ControlManagerSmartWatch2 controlManager, Intent intent) {
        super(context, hostAppPackageName, controlManager, intent);
        Log.d(XBMCRemoteExtensionService.LOG_TAG, "RemoteControl constructor");
    }
}
