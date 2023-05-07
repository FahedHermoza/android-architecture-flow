package com.fahedhermoza.kotlinapp.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fahedhermoza.kotlinapp.R
import com.fahedhermoza.kotlinapp.databinding.ItemProductBinding
import com.fahedhermoza.kotlinapp.domain.model.Product

/***
 * https://medium.com/swlh/how-to-use-view-binding-in-recyclerview-adapter-f818b96c678a
 */
class ProductsAdapter(
    private var products: List<Product>,
    val itemAction: (item: Product) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        var itemBinding: ItemProductBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //render
        with(holder) {
            with(products[position]) {
                binding.tvName.text = name
                binding.tvCost.text = "S./ $cost"
                binding.ivLogo.setImageResource(R.mipmap.ic_funko)

                binding.root.setOnClickListener {
                    itemAction(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun clear() {
        products = emptyList()
        notifyDataSetChanged()
    }

    fun update(data: List<Product>) {
        products = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)
}