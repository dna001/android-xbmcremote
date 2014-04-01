package org.xbmc.android.remote.smartwatch2;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.sonyericsson.extras.liveware.aef.registration.Registration;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;
import org.xbmc.android.remote.R;
import org.xbmc.android.remote.smartwatch2.controls.ControlManagerSmartWatch2;

/**
 * Provides information needed during extension registration
 */
public class XBMCRemoteRegistrationInformation extends RegistrationInformation {

    final Context mContext;

    /**
     * Create control registration object
     *
     * @param context The context
     */
    protected XBMCRemoteRegistrationInformation(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        mContext = context;
    }

    @Override
    public int getRequiredControlApiVersion() {
        return 2;
    }

    @Override
    public int getRequiredSensorApiVersion() {
        return 0;
    }

    @Override
    public int getRequiredNotificationApiVersion() {
        return 0;
    }

    @Override
    public int getRequiredWidgetApiVersion() {
        return 0;
    }


    @Override
    public boolean controlInterceptsBackButton() {
        // Extension has it's own navigation, handles back presses.
        return true;
    }

    /**
     * Get the extension registration information.
     *
     * @return The registration configuration.
     */
    @Override
    public ContentValues getExtensionRegistrationConfiguration() {
        Log.d(XBMCRemoteExtensionService.LOG_TAG, "getExtensionRegistrationConfiguration");
        String iconHostapp = ExtensionUtils.getUriString(mContext, R.drawable.icon);
        String iconExtension = ExtensionUtils
                .getUriString(mContext, R.drawable.icon);

        ContentValues values = new ContentValues();

        /*values.put(Registration.ExtensionColumns.CONFIGURATION_ACTIVITY,
                SamplePreferenceActivity.class.getName());
        values.put(Registration.ExtensionColumns.CONFIGURATION_TEXT,
                mContext.getString(R.string.configuration_text));*/
        values.put(Registration.ExtensionColumns.NAME, mContext.getString(R.string.extension_name));
        values.put(Registration.ExtensionColumns.EXTENSION_KEY,
        		XBMCRemoteExtensionService.EXTENSION_KEY);
        values.put(Registration.ExtensionColumns.HOST_APP_ICON_URI, iconHostapp);
        values.put(Registration.ExtensionColumns.EXTENSION_ICON_URI, iconExtension);
        values.put(Registration.ExtensionColumns.EXTENSION_48PX_ICON_URI, iconExtension);
        values.put(Registration.ExtensionColumns.NOTIFICATION_API_VERSION,
                getRequiredNotificationApiVersion());
        values.put(Registration.ExtensionColumns.PACKAGE_NAME, mContext.getPackageName());

        return values;
    }

    @Override
    public boolean isDisplaySizeSupported(int width, int height) {
        Log.d(XBMCRemoteExtensionService.LOG_TAG,
                "isDisplaySizeSupported: "
                        + ((width == ControlManagerSmartWatch2.getSupportedControlWidth(mContext) && height == ControlManagerSmartWatch2
                                .getSupportedControlHeight(mContext))));
        return ((width == ControlManagerSmartWatch2.getSupportedControlWidth(mContext) && height == ControlManagerSmartWatch2
                .getSupportedControlHeight(mContext)));
    }

}
