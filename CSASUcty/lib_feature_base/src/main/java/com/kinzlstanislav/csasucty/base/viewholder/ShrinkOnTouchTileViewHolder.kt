package com.kinzlstanislav.csasucty.base.viewholder

interface ShrinkOnTouchTileViewHolder {

    var touchUpAction: () -> Unit

    fun shrink()

    fun shrinkBack()
}
