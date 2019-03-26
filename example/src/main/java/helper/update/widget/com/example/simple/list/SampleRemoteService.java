package helper.update.widget.com.example.simple.list;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class SampleRemoteService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new SampleViewFactory(this.getApplicationContext(),
                intent));
    }
}
