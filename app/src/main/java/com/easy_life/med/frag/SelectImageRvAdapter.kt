package com.easy_life.med.frag

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easy_life.med.R
import com.easy_life.med.act.EditAdsAct
import com.easy_life.med.databinding.SelectImageFragItemBinding
import com.easy_life.med.utils.AdapterCallback
import com.easy_life.med.utils.ImageManager
import com.easy_life.med.utils.ImagePicker
import com.easy_life.med.utils.ItemTouchMoveCallback

class SelectImageRvAdapter(val adapterCallback: AdapterCallback): RecyclerView.Adapter<SelectImageRvAdapter.ImageHolder>(), ItemTouchMoveCallback.ItemTouchAdapter {

    val mainArray = ArrayList<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {

        val viewBinding = SelectImageFragItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(viewBinding, parent.context, this)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position])
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onMove(startPos: Int, targetPos: Int) {
        val targetItem = mainArray[targetPos]
        mainArray[targetPos] = mainArray[startPos]
        mainArray[startPos] = targetItem
        notifyItemMoved(startPos, targetPos)

    }

    override fun onClear() {
        notifyDataSetChanged()
    }

    class ImageHolder(private val viewBinding: SelectImageFragItemBinding, val context : Context, val adapter: SelectImageRvAdapter) : RecyclerView.ViewHolder(viewBinding.root) {


        fun setData(bitMap: Bitmap){

            viewBinding.imEditImage.setOnClickListener {

                ImagePicker.getSingleImage(context as EditAdsAct)
                context.editImagePos = adapterPosition
            }

            viewBinding.imDelete.setOnClickListener {

                adapter.mainArray.removeAt(adapterPosition)
                adapter.notifyItemRemoved(adapterPosition)
                for(n in 0 until adapter.mainArray.size) adapter.notifyItemChanged(n)
                adapter.adapterCallback.onItemDelete()
            }

            viewBinding.tvTitle.text = context.resources.getStringArray(R.array.title_array)[adapterPosition]
            ImageManager.chooseScaleType(viewBinding.imageView, bitMap)
            viewBinding.imageView.setImageBitmap(bitMap)
          }
    }

    fun updateAdapter(newList : List<Bitmap>, needClear: Boolean) {
        if(needClear) mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }


}