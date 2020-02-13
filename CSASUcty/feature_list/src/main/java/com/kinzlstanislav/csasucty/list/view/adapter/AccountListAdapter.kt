package com.kinzlstanislav.csasucty.list.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinzlstanislav.csasucty.architecture.repository.model.Account
import com.kinzlstanislav.csasucty.base.extensions.inflateViewHolder
import com.kinzlstanislav.hoslaviceo2.list.R

class AccountListAdapter : RecyclerView.Adapter<AccountViewHolder>() {

    var accounts: MutableList<Account> = mutableListOf()
        set(value) {
            accounts.apply {
                clear()
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AccountViewHolder(parent.inflateViewHolder(R.layout.item_account))

    override fun getItemCount() = accounts.size

    override fun onBindViewHolder(
        holder: AccountViewHolder,
        position: Int
    ) {
        holder.bind(accounts[position])
    }
}