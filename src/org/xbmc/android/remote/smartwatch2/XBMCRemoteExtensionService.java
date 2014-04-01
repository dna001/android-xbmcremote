package org.xbmc.android.remote.smartwatch2;

import android.util.Log;

import com.sonyericsson.extras.liveware.extension.util.ExtensionService;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.registration.DeviceInfoHelper;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;
import org.xbmc.android.remote.smartwatch2.controls.ControlManagerSmartWatch2;

/**
 * The Extension Service handles registration and keeps track of all
 * controls on all accessories.
 */
public class XBMCRemoteExtensionService extends ExtensionService {

    public static final String EXTENSION_KEY = "org.xbmc.android.remote.smartwatch2.key";

    public static final String LOG_TAG = "XBMCRemoteControlExtension";

    public XBMCRemoteExtensionService() {
        super(EXTENSION_KEY);
        Log.d(XBMCRemoteExtensionService.LOG_TAG, "Service: Contructor");
    }

    /**
     * {@inheritDoc}
     *
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(XBMCRemoteExtensionService.LOG_TAG, "Service: onCreate");
    }

    @Override
    protected RegistrationInformation getRegistrationInformation() {
        return new XBMCRemoteRegistrationInformation(this);
    }

    /*
     * (non-Javadoc)
     * @see com.sonyericsson.extras.liveware.aef.util.ExtensionService#
     * keepRunningWhenConnected()
     */
    @Override
    protected boolean keepRunningWhenConnected() {
        return false;
    }

    @Override
    public ControlExtension createControlExtension(String hostAppPackageName) {
        Log.d(XBMCRemoteExtensionService.LOG_TAG, "Service: createControlExtension");
        boolean advancedFeaturesSupported = DeviceInfoHelper.isSmartWatch2ApiAndScreenDetected(
                this, hostAppPackageName);
        if (advancedFeaturesSupported) {
            Log.d(XBMCRemoteExtensionService.LOG_TAG,
                    "Service: Advanced features supported, returning SmartWatch2 extension control manager");
            return new ControlManagerSmartWatch2(this, hostAppPackageName);
        } else {
            Log.d(XBMCRemoteExtensionService.LOG_TAG,
                    "Service: Advanced features not supported, exiting");
            // Possible to return a legacy control extension here
            throw new IllegalArgumentException("No control for: " +
                    hostAppPackageName);
        }
    }
}
