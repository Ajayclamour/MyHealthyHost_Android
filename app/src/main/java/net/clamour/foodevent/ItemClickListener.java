package net.clamour.foodevent;

/**
 * Created by clamour_5 on 1/9/2018.
 */

import android.view.View;

public interface ItemClickListener {

    void onClick(View view, int position, boolean isLongClick);
}