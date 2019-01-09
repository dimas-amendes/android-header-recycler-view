package dimasdark.com.testapp_customizedheaderrecyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

public interface HelperOnStartDragListener {

    /**
     * Interface when dragging action is started
     *
     * @param viewHolder ViewHolder which will be dragged.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);

    void onFinishDrag(@Nullable RecyclerView.ViewHolder viewHolder);
}