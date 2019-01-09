package dimasdark.com.testapp_customizedheaderrecyclerview;

/**
 * Interface with action to move
 * @onItemMove on item move
 * @onItemDismiss on finishing swipe
 */
public interface TouchCallbackHelperAdapter {

        boolean onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }