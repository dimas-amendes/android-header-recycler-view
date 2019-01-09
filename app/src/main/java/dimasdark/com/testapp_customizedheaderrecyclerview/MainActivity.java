package dimasdark.com.testapp_customizedheaderrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity implements HelperOnStartDragListener {

    private ItemTouchHelper touchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinkedHashMap<String, ArrayList<String>> mItems = new LinkedHashMap<>();
        mItems.put("Blue", new ArrayList<String>());
        mItems.put("Green", new ArrayList<String>());
        mItems.put("Purple", new ArrayList<String>());

        mItems.get("Blue").add("AAAAAAA");
        mItems.get("Green").add("BBBBBBB");
        mItems.get("Green").add("FFFFFFF");
        mItems.get("Purple").add("XXXXXXX");
        mItems.get("Purple").add("EEEEEEE")  ;

        ColorTextRecyclerViewAdapter recyclerViewAdapter = new ColorTextRecyclerViewAdapter(this, mItems, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recyclerViewAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(this,
                manager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
        TouchCallbackHelper helper = new TouchCallbackHelper(recyclerViewAdapter);
        touchHelper = new ItemTouchHelper(helper);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }

    @Override
    public void onFinishDrag(RecyclerView.ViewHolder viewHolder) {

    }
}
