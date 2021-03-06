package name.syndarin.reddittop.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import io.reactivex.subjects.Subject;
import name.syndarin.reddittop.R;
import name.syndarin.reddittop.databinding.BindingItemRedditTop;
import name.syndarin.reddittop.entity.RedditItem;
import name.syndarin.reddittop.ui.binders.BindingComponentItemRedditTop;
import name.syndarin.reddittop.viewmodel.ViewModelRedditItem;

/**
 * Created by syndarin on 9/25/17.
 */

public class RedditItemsAdapter extends RecyclerView.Adapter<RedditItemsAdapter.RedditItemViewHolder> {

    static class RedditItemViewHolder extends RecyclerView.ViewHolder {

        private BindingItemRedditTop binding;
        private ViewModelRedditItem viewModel;

        public RedditItemViewHolder(BindingItemRedditTop binding, Subject<RedditItem> thumbnailClickSubject) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = new ViewModelRedditItem(thumbnailClickSubject);
            this.binding.setViewModel(viewModel);
        }

        public void bindRedditItem(RedditItem item) {
            viewModel.setRedditItem(item);
            binding.invalidateAll();
        }
    }

    private List<RedditItem> items = Collections.emptyList();

    private Subject<RedditItem> thumbnailClickSubject;

    @Override
    public RedditItemsAdapter.RedditItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BindingItemRedditTop binding = BindingItemRedditTop.inflate(inflater, new BindingComponentItemRedditTop());
        return new RedditItemViewHolder(binding, thumbnailClickSubject);
    }

    @Override
    public void onBindViewHolder(RedditItemsAdapter.RedditItemViewHolder holder, int position) {
        holder.bindRedditItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void updateItems(List<RedditItem> items) {
        int insertPosition = this.items.size();
        this.items = items;
        notifyItemRangeInserted(insertPosition, items.size());
    }

    public void setThumbnailClickSubject(Subject<RedditItem> thumbnailClickSubject) {
        this.thumbnailClickSubject = thumbnailClickSubject;
    }
}
