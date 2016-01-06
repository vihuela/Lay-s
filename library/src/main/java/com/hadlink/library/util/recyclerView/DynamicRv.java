package com.hadlink.library.util.recyclerView;

/**
 * @author Created by lyao on 2016/1/2.
 * @update
 * @description
 */
public class DynamicRv {


    /*public static void adjustItemSize(final View itemRootView, GridLayoutManager gridLayoutManager, int margin, int position) {

        if (position == 0) {
            itemRootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override public boolean onPreDraw() {
                    itemRootView.getViewTreeObserver().removeOnPreDrawListener(this);
                    int rvHeight = getRVHeight(itemRootView.getHeight());
                    if (OnGetRVHeight != null) OnGetRVHeight.GetIt(rvHeight);
                    return false;
                }
            });
        }
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) itemRootView.getLayoutParams();
        int left = gridLayoutManager.getSpanSizeLookup().getSpanIndex(position, gridLayoutManager.getSpanCount()) == 0 ? margin : margin / 2;//first col
        int right = gridLayoutManager.getSpanSizeLookup().getSpanIndex(position, gridLayoutManager.getSpanCount()) == gridLayoutManager.getSpanCount() - 1 ? margin : margin / 2;//end col
        int top = position < gridLayoutManager.getSpanCount() ? margin : 0;//first row
        layoutParams.setMargins(left, top, right, margin);
        itemRootView.setLayoutParams(layoutParams);


    }
    public static int getRVHeight(int itemHeight) {
        int row = Math.max(getItemCount() / spanCount + getItemCount() % spanCount, 1);
        return row * itemHeight + margin * (row + 1);
    }*/
}
