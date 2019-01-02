package helper.update.widget.com.example.list;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import helper.update.widget.com.example.R;

public class SampleViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<SampleData> list = new ArrayList<>();
    private Context context;

    public SampleViewFactory(Context applicationContext, Intent intent) {
        context = applicationContext;
        list.add(new SampleData("andrew"));
        list.add(new SampleData("alex"));
        list.add(new SampleData("jack"));
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        //TODO try to figure out how to use it with list on android P
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        return getSampleView(list.get(position).getName());
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private RemoteViews getSampleView(String name) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sample_view);
        remoteViews.setTextViewText(R.id.sample_text, name);
        return remoteViews;
    }
}
