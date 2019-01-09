package dimasdark.com.testapp_customizedheaderrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ColorTextRecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> implements TouchCallbackHelperAdapter {

    private Context context;
    private LinkedHashMap<String, ArrayList<String>> mTitlesItems;
    private ArrayList<String> mItems;
    private final HelperOnStartDragListener mDragStartListener;
    private ItemViewHolder oldHolder;

    //Header x Item
    private static final int HEADER_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    public ColorTextRecyclerViewAdapter(Context context, LinkedHashMap<String, ArrayList<String>> mTitlesItems, HelperOnStartDragListener dragStartListener) {
        this.context = context;
        this.mTitlesItems = mTitlesItems;
        this.mDragStartListener = dragStartListener;

        mItems = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : mTitlesItems.entrySet()) {
            //String key = entry.getKey();
            ArrayList<String> list = entry.getValue();
            mItems.add("");
            mItems.addAll(list);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(final int fromPosition, final int toPosition) {
        if (fromPosition < toPosition) {
            //Prevents from swiping the first position
            for (int i = fromPosition; i < toPosition; i++) {
                if (toPosition != 0) Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                if (toPosition != 0) Collections.swap(mItems, i, i - 1);
            }
        }
        mDragStartListener.onFinishDrag(null);

        if (fromPosition == 0) notifyItemMoved(fromPosition + 1, toPosition);
        else if (toPosition == 0) notifyItemMoved(fromPosition, toPosition + 1);
        else notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public ArrayList<String> getData() {
        return mItems;
    }

    public void addItem(String item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position).isEmpty()) return HEADER_VIEW;
        else return ITEM_VIEW;
    }

    private int getHeader(int position) {
        if (position != 0) {
            int headerPos = 0;
            for (int i = 0; i < position; i++) if (mItems.get(i).isEmpty()) headerPos++;

            return headerPos;
        } else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_VIEW) {
            view = LayoutInflater.from(context).inflate(R.layout.item_drag_mode, parent, false);
            return new ItemViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            ArrayList<String> keys = new ArrayList<>(mTitlesItems.keySet());
            String name = keys.get(getHeader(position));
            ((HeaderViewHolder) holder).categoryName.setText(name);
        } else {
            String item = mItems.get(position);
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.name.setText(item);

            itemHolder.drag.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mDragStartListener.onStartDrag(holder);
                            break;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;

        HeaderViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.header_title);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements HelperViewHolderSelector {

        private TextView name;
        private ImageView drag;
        private View itemView;

        ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            drag = itemView.findViewById(R.id.item_drag);
            name = itemView.findViewById(R.id.item_name);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}