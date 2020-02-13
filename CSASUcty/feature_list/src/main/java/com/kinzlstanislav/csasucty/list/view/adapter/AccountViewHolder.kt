package com.kinzlstanislav.csasucty.list.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kinzlstanislav.csasucty.architecture.repository.model.Account
import com.kinzlstanislav.csasucty.base.viewholder.ShrinkOnTouchTileViewHolder
import com.kinzlstanislav.csasucty.base.viewholder.ShrinkOnTouchTileViewHolderImpl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_account.*

class AccountViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer,
        ShrinkOnTouchTileViewHolder by ShrinkOnTouchTileViewHolderImpl(containerView) {

    fun bind(account: Account) {
        account_name.text = account.name
    }
}