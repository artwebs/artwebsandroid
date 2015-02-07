package cn.artwebs.control;

import android.app.Activity;
import cn.artwebs.ListAdapter.ListAdapter;
import cn.artwebs.control.ArtListView.OnRefreshListener;

/**
 * Created by rsmac on 15/2/7.
 */
public class ArtListViewControlPage extends ArtListControlPage implements OnRefreshListener {

    public ArtListViewControlPage(Activity window, ListAdapter adapter) {
        super(window, adapter);
    }

    @Override
    public void onRefresh() {
        listener.loadMoreData(page,pageSize);
    }

    @Override
    public void onLoadMore() {
        listener.loadMoreData(page,pageSize);
    }
}
