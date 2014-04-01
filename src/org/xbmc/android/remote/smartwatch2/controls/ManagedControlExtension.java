package org.xbmc.android.remote.smartwatch2.controls;

import android.content.Context;
import android.content.Intent;

import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;

public class ManagedControlExtension extends ControlExtension {

    /**
     * Name for extra data put in ManagedControlExtension intents. Identifies
     * boolean data. If the following boolean value is set to true, the new
     * extension is not kept in the history stack. As soon as the user navigates
     * away from it, the extension is not available on the back stack.
     */
    public static final String EXTENSION_NO_HISTORY = "EXTENSION_NO_HISTORY";
    /**
     * Name for extra data put in ManagedControlExtension intents. Identifies
     * boolean data. If the following boolean value is set to true, extension
     * handles presses of back button. As this can break the extension's
     * navigation pattern this should be used with caution;
     */
    public static final String EXTENSION_OVERRIDES_BACK = "EXTENSION_OVERRIDES_BACK";
    private Intent mIntent;
    protected ControlManagerSmartWatch2 mControlManager;

    /**
     * Constructor for ManagedControlExtension. Should not be called directly,
     * is called by ControlManager.
     *
     * @param context
     * @param hostAppPackageName
     * @param controlManager the ControlManager that handles this extension's
     *            lifecycle
     * @param intent The intent used to handle the state of the
     *            ManagedControlExtension
     */
    public ManagedControlExtension(Context context, String hostAppPackageName,
            ControlManagerSmartWatch2 controlManager, Intent intent) {
        super(context, hostAppPackageName);
        this.mIntent = intent;
        mControlManager = controlManager;
    }

    /**
     * @return Return the intent that started this controlExtension.
     */
    public Intent getIntent() {
        return mIntent;
    }

}
