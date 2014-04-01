package org.xbmc.android.remote.smartwatch2.controls;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.control.ControlTouchEvent;
import org.xbmc.android.remote.smartwatch2.XBMCRemoteExtensionService;

/**
 * The phone control manager manages which control to currently show on the
 * display. This class then forwards any life-cycle methods and events events to
 * the running control. This base class only handles API level 1 methods.
 */
public class ControlManagerBase extends ControlExtension {

    protected ControlExtension mCurrentControl = null;

    private static final int STATE_IDLE = 0;

    protected static final int STATE_STARTED = 1;

    protected static final int STATE_FOREGROUND = 2;

    protected int mControlState = STATE_IDLE;

    /**
     * Create phone control manager.
     *
     * @param context The context to use.
     * @param packageName The package name of the host application.
     */
    public ControlManagerBase(final Context context, final String packageName) {
        super(context, packageName);
        Log.v(XBMCRemoteExtensionService.LOG_TAG, "created");

    }

    @Override
    public void onDestroy() {
        Log.v(XBMCRemoteExtensionService.LOG_TAG, "ControlManager onDestroy");
        if (mCurrentControl != null) {
            mCurrentControl.onDestroy();
        }
    }

    @Override
    public void onStart() {
        Log.v(XBMCRemoteExtensionService.LOG_TAG, "onStart");
        mControlState = STATE_STARTED;
        if (mCurrentControl != null) {
            mCurrentControl.onStart();
        }
    }

    @Override
    public void onStop() {
        Log.v(XBMCRemoteExtensionService.LOG_TAG, "onStop");
        mControlState = STATE_IDLE;
        if (mCurrentControl != null) {
            mCurrentControl.onStop();
        }
    }

    @Override
    public void onPause() {
        Log.v(XBMCRemoteExtensionService.LOG_TAG, "onPause");
        mControlState = STATE_STARTED;
        if (mCurrentControl != null) {
            mCurrentControl.onPause();
        }
    }

    @Override
    public void onResume() {
        Log.v(XBMCRemoteExtensionService.LOG_TAG, "onResume");
        mControlState = STATE_FOREGROUND;
        if (mCurrentControl != null) {
            mCurrentControl.onResume();
        }
    }

    @Override
    public void onTouch(final ControlTouchEvent event) {
        Log.v(XBMCRemoteExtensionService.LOG_TAG, "onTouch");
        if (mCurrentControl != null) {
            mCurrentControl.onTouch(event);
        }
    }

    @Override
    public void onDoAction(int requestCode, Bundle bundle) {
        Log.v(XBMCRemoteExtensionService.LOG_TAG, "onDoAction");
        if (mCurrentControl != null) {
            mCurrentControl.onDoAction(requestCode, bundle);
        }
    }

    @Override
    public void onError(int code) {
        Log.d(XBMCRemoteExtensionService.LOG_TAG, "onError");
        if (mCurrentControl != null) {
            mCurrentControl.onError(code);
        }
    }

    @Override
    public void onKey(int action, int keyCode, long timeStamp) {
        Log.v(XBMCRemoteExtensionService.LOG_TAG, "onKey");
        if (mCurrentControl != null) {
            mCurrentControl.onKey(action, keyCode, timeStamp);
        }
    }

    /**
     * Start a new control. Any currently running control will be stopped.
     *
     * @param newControlId The control to start.
     */
    protected void startControl(final ControlExtension newControl) {

        Log.d(XBMCRemoteExtensionService.LOG_TAG, "ControlManager startControl");
        if (newControl != null) {
            // Stop the current control
            int savedState = mControlState;
            closeCurrentControl();
            mCurrentControl = newControl;
            // Start and resume the new control
            if (mCurrentControl != null) {
                onStart();
                if (savedState == STATE_FOREGROUND) {
                    onResume();
                }
            }
        }
    }

    protected void closeCurrentControl() {
        if (mCurrentControl != null) {
            if (mControlState == STATE_FOREGROUND) {
                onPause();
            }
            if (mControlState == STATE_STARTED) {
                onStop();
            }
            mCurrentControl.onDestroy();
        }
    }
}
