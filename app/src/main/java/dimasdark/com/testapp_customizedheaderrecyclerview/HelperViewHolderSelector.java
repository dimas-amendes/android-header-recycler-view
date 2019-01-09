package dimasdark.com.testapp_customizedheaderrecyclerview;

/**
 * Notifies the ViewHolder on touching callbacks
 */
public interface HelperViewHolderSelector {

    /**
     * {@link TouchCallbackHelper}
     * registers a drag or swipe, indicating an active state
     */
    void onItemSelected();


    /**
     * {@link TouchCallbackHelper}
     * on finish a drag or swipe, indicating a clean state
     */
    void onItemClear();
}