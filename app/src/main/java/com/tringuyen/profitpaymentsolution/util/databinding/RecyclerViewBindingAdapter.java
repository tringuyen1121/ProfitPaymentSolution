package com.tringuyen.profitpaymentsolution.util.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public abstract class RecyclerViewBindingAdapter<M> extends RecyclerView.Adapter<RecyclerViewBindingViewHolder<M>> {

    private Context context;
    private PublishSubject<M> itemClickedSubject;
    private PublishSubject<Pair<M, Integer>> itemClickedWithPositionSubject = PublishSubject.create();

    public RecyclerViewBindingAdapter(Context context) {
        this.context = context;
        this.itemClickedSubject = PublishSubject.create();
    }

    /**
     * Get specific item at {@code position}.
     *
     * @param position The position of the item in the list.
     * @return The item at the give position.
     */
    public abstract M getItem(int position);

    /**
     * Get layout id for a specific item at {@code position}.
     *
     * @param position The position of the item in the list.
     * @return The layout id of the item at the given position.
     */
    public abstract int getLayoutForItem(int position);

    /**
     * Get binding id for a specific item at {@code position}.
     *
     * @param position The position of the item in the list.
     * @return The binding id of the item at the given position.
     */
    public abstract int getBindingForItem(int position);

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    public abstract int getItemCount();

    @Override
    public int getItemViewType(int position) {
        return getLayoutForItem(position);
    }

    /**
     * This method is basically the same as {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}.
     * However, {@code viewType} is changed to {@code layoutId} since {@link RecyclerViewBindingAdapter#getItemViewType(int)}
     * will return layoutId instead. Each item will have it's own layout so layout is considered as the view type.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param layoutId The layout id of the new View.
     * @return A new ViewHolder that holds a View of the given layout id.
     */
    @Override
    public RecyclerViewBindingViewHolder<M> onCreateViewHolder(ViewGroup parent, int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        return new RecyclerViewBindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerViewBindingViewHolder<M> holder, final int position) {
        holder.itemView.setOnClickListener(v -> {
            itemClickedSubject.onNext(getItem(position));
            itemClickedWithPositionSubject.onNext(new Pair<>(getItem(position), position));
        });
        holder.bind(getBindingForItem(position), getItem(position));
    }

    @Override
    public void onViewAttachedToWindow(RecyclerViewBindingViewHolder<M> holder) {
        super.onViewAttachedToWindow(holder);
        holder.bindUIEvent();
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerViewBindingViewHolder<M> holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbindUIEvent();
    }

    /**
     * This method provides an {@link Observable} that emits an event whenever an item is clicked.
     *
     * @return The observable that can be subscribed to for item click event.
     */
    public Observable<M> onItemClicked() {
        return itemClickedSubject;
    }

    public Observable<Pair<M, Integer>> onItemClickedWithPosition() {
        return itemClickedWithPositionSubject;
    }

    public Context getContext() {
        return context;
    }

}
