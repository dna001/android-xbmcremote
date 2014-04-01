package org.xbmc.android.remote.smartwatch2.controls;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.control.ControlListItem;
import org.xbmc.android.remote.R;
import org.xbmc.android.remote.smartwatch2.XBMCRemoteExtensionService;

/**
 * ListControlExtension displays a scrollable list, based on a string array.
 * Tapping on list items opens a swipable detail view.
 */
public class ListControlExtension extends ManagedControlExtension {

    /**
     * String array with dummy data to be displayed in list.
     */
    private String[] mListContent = {
            "List Item 1", "List Item 2", "List Item 3", "List Item 4", "List Item 5",
            "List Item 6", "List Item 7", "List Item 8", "List Item 9", "List Item 10"
    };

    protected int mLastKnowPosition = 0;

    /**
     * @see ManagedControlExtension#ManagedControlExtension(Context, String,
     *      ControlManagerCostanza, Intent)
     */
    public ListControlExtension(Context context, String hostAppPackageName,
            ControlManagerSmartWatch2 controlManager, Intent intent) {
        super(context, hostAppPackageName, controlManager, intent);
        Log.d(XBMCRemoteExtensionService.LOG_TAG, "ListControl constructor");
    }

    @Override
    public void onResume() {
        Log.d(XBMCRemoteExtensionService.LOG_TAG, "onResume");
        showLayout(R.layout.sw2_list, null);
        sendListCount(R.id.sw2_list, mListContent.length);

        // If requested, move to the correct position in the list.
        /*int startPosition = getIntent().getIntExtra(GalleryTestControl.EXTRA_INITIAL_POSITION, 0);
        mLastKnowPosition = startPosition;
        sendListPosition(R.id.sw2_list, startPosition);*/
    }

    @Override
    public void onPause() {
        super.onPause();
        // Position is saved into Control's Intent, possibly to be used later.
        //getIntent().putExtra(GalleryTestControl.EXTRA_INITIAL_POSITION, mLastKnowPosition);
    }

    @Override
    public void onRequestListItem(final int layoutReference, final int listItemPosition) {
        Log.d(XBMCRemoteExtensionService.LOG_TAG, "onRequestListItem() - position " + listItemPosition);
        if (layoutReference != -1 && listItemPosition != -1 && layoutReference == R.id.sw2_list) {
            ControlListItem item = createControlListItem(listItemPosition);
            if (item != null) {
                sendListItem(item);
            }
        }
    }

    @Override
    public void onListItemSelected(ControlListItem listItem) {
        super.onListItemSelected(listItem);
        // We save the last "selected" position, this is the current visible
        // list item index. The position can later be used on resume
        mLastKnowPosition = listItem.listItemPosition;
    }

    @Override
    public void onListItemClick(final ControlListItem listItem, final int clickType,
            final int itemLayoutReference) {
        Log.d(XBMCRemoteExtensionService.LOG_TAG, "Item clicked. Position " + listItem.listItemPosition
                + ", itemLayoutReference " + itemLayoutReference + ". Type was: "
                + (clickType == Control.Intents.CLICK_TYPE_SHORT ? "SHORT" : "LONG"));

        if (clickType == Control.Intents.CLICK_TYPE_SHORT) {
            /*Intent intent = new Intent(mContext, GalleryTestControl.class);
            // Here we pass the item position to the next control. It woudl
            // alsobe possible to put some unique item id in the listitem and
            // pass listItem.listItemId here.
            intent.putExtra(GalleryTestControl.EXTRA_INITIAL_POSITION, listItem.listItemPosition);
            mControlManager.startControl(intent);*/
        }
    }

    protected ControlListItem createControlListItem(int position) {

        ControlListItem item = new ControlListItem();
        item.layoutReference = R.id.sw2_list;
        item.dataXmlLayout = R.layout.sw2_list_item;
        item.listItemPosition = position;
        // We use position as listItemId. Here we could use some other unique id
        // to reference the list data
        item.listItemId = position;

        // Header data
        Bundle textBundle = new Bundle();
        textBundle.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.sw2_list_item_text);
        textBundle.putString(Control.Intents.EXTRA_TEXT, mListContent[position]);

        item.layoutData = new Bundle[1];
        item.layoutData[0] = textBundle;

        return item;
    }

}
