package com.xekera.Ecommerce.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.varunest.sparkbutton.SparkButton
import com.wang.avi.AVLoadingIndicatorView
import com.xekera.Ecommerce.R
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.AllProductsResponse
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.Product
import kotlinx.android.synthetic.main.fragment_row_shop_details.view.*

import java.io.ByteArrayOutputStream
import java.util.ArrayList
import java.util.Locale

class SearchAllProductsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    lateinit var context: Context
    lateinit var productsItems: MutableList<Product>
    lateinit var productsItemsSearch: MutableList<Product>
    lateinit var iShopDetailAdapter: SearchAllProductsAdapter.IShopDetailAdapter
    lateinit var favList: MutableList<String>
    //    private ProductItemActionListener actionListener;

    constructor() {


    }

    constructor(
        context: Context,
        productsItems: MutableList<Product>,
        iShopDetailAdapter: SearchAllProductsAdapter.IShopDetailAdapter
    ) {
        this.context = context
        this.productsItems = productsItems
        this.iShopDetailAdapter = iShopDetailAdapter
        this.productsItemsSearch = mutableListOf()
        (this.productsItemsSearch as ArrayList<Product>).addAll(productsItems)
        this.favList = mutableListOf<String>()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_row_shop_details, parent, false)
        val productDetailDataListViewHolder = productDetailsDataListViewHolder(v)
        return productDetailDataListViewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val shoppingDetailModel = productsItems[position]

        if (holder is productDetailsDataListViewHolder) {
            val productDetailsDataListViewHolder = holder

//            productDetailsDataListViewHolder?.cardViewParent.setOnClickListener(this)
//            productDetailsDataListViewHolder?.cardViewParent.setOnClickListener { view ->
//                iShopDetailAdapter?.onItemClick(arrayList.get(position).name, position)
//            }

            if (shoppingDetailModel.name != null) {
                productDetailsDataListViewHolder.productNameLabelTextView!!.text = shoppingDetailModel.name
            } else {
                productDetailsDataListViewHolder.productNameLabelTextView!!.text = ""
            }
            if (shoppingDetailModel.Price != null) {
                productDetailsDataListViewHolder.priceTextView!!.text = shoppingDetailModel.Price

            } else {
                productDetailsDataListViewHolder.priceTextView!!.text = ""

            }

            if (favList.contains(shoppingDetailModel.name)) {
                productDetailsDataListViewHolder.favouriteButton!!.isChecked = true

            } else {
                productDetailsDataListViewHolder.favouriteButton!!.isChecked = false

            }



            if (shoppingDetailModel.Regular_price != null) {

                productDetailsDataListViewHolder.discountPriceTextView!!.text = shoppingDetailModel.Regular_price
                if (shoppingDetailModel.Regular_price.equals("0", ignoreCase = true)) {
                    productDetailsDataListViewHolder.discountLinearParent!!.visibility = View.GONE
                }
            } else {
                productDetailsDataListViewHolder.discountPriceTextView!!.text = ""
                productDetailsDataListViewHolder.discountLinearParent!!.visibility = View.GONE

            }

            try {
                if (shoppingDetailModel.image_json != null && shoppingDetailModel.image_json.size > 0) {

                    Glide.with(context)
                        .load(shoppingDetailModel.image_json[0])
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        // .override(130, 50)
                        .centerCrop()
                        .override(300, 300)
                        // .into(homeViewHolder.imgHomeItem);
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                                productDetailsDataListViewHolder.avloadingIndicatorView!!.visibility = View.GONE
                                productDetailsDataListViewHolder.imgProduct!!.setImageBitmap(resource)
                                productDetailsDataListViewHolder.imgProduct!!.visibility = View.VISIBLE
                                productDetailsDataListViewHolder.imgProductCopy!!.setImageBitmap(resource)

                            }

                            override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                                super.onLoadFailed(e, errorDrawable)
                                productDetailsDataListViewHolder.avloadingIndicatorView!!.visibility = View.GONE
                                productDetailsDataListViewHolder.imgProduct!!.visibility = View.VISIBLE

                            }
                        })

                } else {
                    Glide.with(context)
                        .load(R.drawable.placeholder)
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        // .override(130, 50)
                        .centerCrop()

                        // .into(homeViewHolder.imgHomeItem);
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                                productDetailsDataListViewHolder.avloadingIndicatorView!!.visibility = View.GONE
                                productDetailsDataListViewHolder.imgProduct!!.setImageBitmap(resource)
                                productDetailsDataListViewHolder.imgProduct!!.visibility = View.VISIBLE
                                productDetailsDataListViewHolder.imgProductCopy!!.setImageBitmap(resource)

                            }

                            override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                                super.onLoadFailed(e, errorDrawable)
                                productDetailsDataListViewHolder.avloadingIndicatorView!!.visibility = View.GONE
                                productDetailsDataListViewHolder.imgProduct!!.visibility = View.VISIBLE
                            }
                        })

                }


            } catch (e: Exception) {

            }


        }
    }


    inner class productDetailsDataListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        @BindView(R.id.linearParent)
//        protected var linearParent: LinearLayout? = null
//        @BindView(R.id.productNameLabelTextView)
//        var productNameLabelTextView: TextView? = null
//        @BindView(R.id.priceTextView)
//        var priceTextView: TextView? = null
//        @BindView(R.id.viewDetailsImageView)
//        var viewDetailsImageView: Button? = null
//        @BindView(R.id.AddImageView)
//        var AddImageView: Button? = null
//        @BindView(R.id.imgProduct)
//        var imgProduct: ImageView? = null
//        @BindView(R.id.imgProductCopy)
//        var imgProductCopy: ImageView? = null
//        @BindView(R.id.favouriteButton)
//        var favouriteButton: SparkButton? = null
//        @BindView(R.id.decrementImageButton)
//        var decrementImageButton: ImageView? = null
//        @BindView(R.id.incrementImageButton)
//        var incrementImageButton: ImageView? = null
//        @BindView(R.id.counterTextview)
//        var counterTextview: TextView? = null
//        @BindView(R.id.cardViewParent)
//        var cardViewParent: CardView? = null
//        @BindView(R.id.imgShareProductDetails)
//        var imgShareProductDetails: ImageView? = null
//        @BindView(R.id.avloadingIndicatorView)
//        var avloadingIndicatorView: AVLoadingIndicatorView? = null
//        @BindView(R.id.discountPriceTextView)
//        var discountPriceTextView: TextView? = null
//        @BindView(R.id.discountLinearParent)
//        var discountLinearParent: LinearLayout? = null


        //  init {
        //ButterKnife.bind(this, itemView)
        // itemView.setOnClickListener(this)

        // cardViewParent!!.isClickable = true

//            itemView.findViewById<View>(R.id.decrementImageButton).setOnClickListener(this)
//            itemView.findViewById<View>(R.id.incrementImageButton).setOnClickListener(this)
//            itemView.findViewById<View>(R.id.cardViewParent).setOnClickListener(this)
//            itemView.findViewById<View>(R.id.imgShareProductDetails).setOnClickListener(this)
//            itemView.findViewById<View>(R.id.favouriteButton).setOnClickListener(this)

        val decrementImageButton: ImageView = itemView.decrementImageButton


        val incrementImageButton: ImageView = itemView.incrementImageButton
        val cardViewParent: CardView = itemView.cardViewParent
        val imgShareProductDetails: ImageView = itemView.imgShareProductDetails
        val favouriteButton: SparkButton = itemView.favouriteButton

        val linearParent: LinearLayout = itemView.linearParent
        val productNameLabelTextView: TextView = itemView.productNameLabelTextView

        val priceTextView: TextView = itemView.priceTextView
        val viewDetailsImageView: Button = itemView.viewDetailsImageView


        val AddImageView: Button = itemView.AddImageView
        val imgProduct: ImageView = itemView.imgProduct

        val imgProductCopy: ImageView = itemView.imgProductCopy
        val counterTextview: TextView = itemView.counterTextview

        val avloadingIndicatorView: AVLoadingIndicatorView = itemView.avloadingIndicatorView
        val discountPriceTextView: TextView = itemView.discountPriceTextView
        val discountLinearParent: LinearLayout = itemView.discountLinearParent
        //}

        init {
            itemView.cardViewParent.setOnClickListener {

                val bitmap: Bitmap? = null


                try {
                    iShopDetailAdapter.onCardClick(
                        productsItems[layoutPosition].name!!,
                        productsItems[layoutPosition].Price!!,
                        java.lang.Long.valueOf(productsItems[layoutPosition].Regular_price),
                        java.lang.Long.valueOf(counterTextview!!.text.toString()),
                        productsItems[layoutPosition].image_json as List<String>, bitmap,
                        productsItems[layoutPosition].Des!!,
                        productsItems[layoutPosition].Product_Sku!!,
                        productsItems[layoutPosition].id!!,
                        productsItems[layoutPosition].name_sku!!
                    )

                } catch (e: Exception) {

                }
            }

            itemView.AddImageView.setOnClickListener {
                iShopDetailAdapter.onAddButtonClick(productsItems[layoutPosition])
            }
            itemView.viewDetailsImageView.setOnClickListener {
                iShopDetailAdapter.onViewDetailsButtonClick(productsItems[layoutPosition])
            }

            itemView.favouriteButton.setOnClickListener {


                if ((itemView.favouriteButton as SparkButton).isChecked) {
                    (itemView.favouriteButton as SparkButton).isChecked = false
                    favList.remove(productsItems[layoutPosition].name)


                } else {
                    (itemView.favouriteButton as SparkButton).playAnimation()
                    (itemView.favouriteButton as SparkButton).isChecked = true
                    favList.add(productsItems[layoutPosition].name!!)


                }


                val bitmapFavourite: Any? = null ?: ""

                iShopDetailAdapter.onFavouriteButtonClick(
                    productsItems[layoutPosition], layoutPosition,
                    bitmapFavourite, counterTextview!!.text.toString(),
                    productsItems[layoutPosition].image_json!![0],
                    productsItems[layoutPosition].id!!, "0",
                    productsItems[layoutPosition].Des!!,
                    productsItems[layoutPosition].image_json as List<String>,
                    productsItems[layoutPosition].name_sku!!
                )

            }

            itemView.decrementImageButton.setOnClickListener {

                if (java.lang.Long.valueOf(counterTextview!!.text.toString()) > 1) {

                    val dec = java.lang.Long.valueOf(counterTextview!!.text.toString()) - 1

                    counterTextview!!.text = dec.toString() + ""
                    val bitmapAdd: Any? = null ?: ""


                    val productPrice = java.lang.Long.valueOf(productsItems[layoutPosition].Price)
                    val itemQuantity = java.lang.Long.valueOf(counterTextview!!.text.toString())
                    iShopDetailAdapter.onDecrementButtonClick(
                        itemQuantity,
                        productPrice.toString(),
                        (productPrice * itemQuantity).toString(),
                        productsItems[layoutPosition].name!!,
                        java.lang.Long.valueOf(productsItems[layoutPosition].Regular_price),
                        imgProductCopy, bitmapAdd, productsItems[layoutPosition].image_json!![0],
                        productsItems[layoutPosition].id!!, "0",
                        productsItems[layoutPosition].Des!!,
                        productsItems[layoutPosition].image_json as List<String>,
                        productsItems[layoutPosition].name_sku!!
                    )

                } else {
                    counterTextview!!.text = "0"

                    iShopDetailAdapter.removeItemFromCart(productsItems[layoutPosition])


                }

            }

            itemView.incrementImageButton.setOnClickListener {
                val inc = java.lang.Long.valueOf(counterTextview!!.text.toString())
                val incrementLong = inc + 1
                counterTextview!!.text = incrementLong.toString() + ""
                val productPrice = java.lang.Long.valueOf(productsItems[layoutPosition].Price)
                val itemQuantity = java.lang.Long.valueOf(counterTextview!!.text.toString())


                val bitmapAdd: Any? = null ?: ""


                iShopDetailAdapter.onIncrementButtonClick(
                    java.lang.Long.valueOf(counterTextview!!.text.toString()),
                    productPrice.toString(),
                    (productPrice * itemQuantity).toString(),
                    productsItems[layoutPosition].name!!,
                    java.lang.Long.valueOf(productsItems[layoutPosition].Regular_price), imgProductCopy,
                    bitmapAdd, productsItems[layoutPosition].image_json!![0],
                    productsItems[layoutPosition].id!!, "0",
                    productsItems[layoutPosition].Des!!,
                    productsItems[layoutPosition].image_json as List<String>,
                    productsItems[layoutPosition].name_sku!!
                )
            }


            itemView.imgShareProductDetails.setOnClickListener {

                val bitmapImage: Any? = null ?: ""
                iShopDetailAdapter.shareItemsDetails(productsItems[layoutPosition], bitmapImage)
            }

        }
//
//        override fun onClick(v: View) {
//            when (v.id) {
//
//                R.id.cardViewParent -> {
//
//
//                    val bitmap: Bitmap? = null
//
//
//                    try {
//                        iShopDetailAdapter.onCardClick(
//                            productsItems[layoutPosition].name!!,
//                            productsItems[layoutPosition].Price!!,
//                            java.lang.Long.valueOf(productsItems[layoutPosition].Regular_price),
//                            java.lang.Long.valueOf(counterTextview!!.text.toString()),
//                            productsItems[layoutPosition].image_json as List<String>, bitmap, "",
//                            productsItems[layoutPosition].Product_Sku!!,
//                            productsItems[layoutPosition].id!!
//                        )
//
//                    } catch (e: Exception) {
//
//                    }
//
//                }
//
//                R.id.AddImageView -> iShopDetailAdapter.onAddButtonClick(productsItems[layoutPosition])
//                R.id.viewDetailsImageView -> iShopDetailAdapter.onViewDetailsButtonClick(productsItems[layoutPosition])
//
//                R.id.favouriteButton -> {
//
//                    if ((v.findViewById<View>(R.id.favouriteButton) as SparkButton).isChecked) {
//                        (v.findViewById<View>(R.id.favouriteButton) as SparkButton).isChecked = false
//                        favList.remove(productsItems[layoutPosition].name)
//
//
//                    } else {
//                        (v.findViewById<View>(R.id.favouriteButton) as SparkButton).playAnimation()
//                        (v.findViewById<View>(R.id.favouriteButton) as SparkButton).isChecked = true
//                        favList.add(productsItems[layoutPosition].name!!)
//
//
//                    }
//
//
//                    val bitmapFavourite: Bitmap? = null
//
//                    iShopDetailAdapter.onFavouriteButtonClick(
//                        productsItems[layoutPosition], layoutPosition,
//                        bitmapFavourite, counterTextview!!.text.toString(),
//                        productsItems[layoutPosition].image_json!![0],
//                        productsItems[layoutPosition].id!!, "0",
//                        "",
//                        productsItems[layoutPosition].image_json as List<String>
//                    )
//                }
//                R.id.decrementImageButton -> {
//
//                    if (java.lang.Long.valueOf(counterTextview!!.text.toString()) > 1) {
//
//                        val dec = java.lang.Long.valueOf(counterTextview!!.text.toString()) - 1
//
//                        counterTextview!!.text = dec.toString() + ""
//                        val bitmapAdd: Bitmap? = null
//
//
//                        val productPrice = java.lang.Long.valueOf(productsItems[layoutPosition].Price)
//                        val itemQuantity = java.lang.Long.valueOf(counterTextview!!.text.toString())
//                        iShopDetailAdapter.onDecrementButtonClick(
//                            itemQuantity,
//                            productPrice.toString(),
//                            (productPrice * itemQuantity).toString(),
//                            productsItems[layoutPosition].name!!,
//                            java.lang.Long.valueOf(productsItems[layoutPosition].Regular_price),
//                            imgProductCopy, bitmapAdd, productsItems[layoutPosition].image_json!![0],
//                            productsItems[layoutPosition].id!!, "0",
//                            "",
//                            productsItems[layoutPosition].image_json as List<String>
//                        )
//
//                    } else {
//                        counterTextview!!.text = "0"
//
//                        iShopDetailAdapter.removeItemFromCart(productsItems[layoutPosition])
//
//
//                    }
//                }
//
//                R.id.incrementImageButton -> {
//
//                    val inc = java.lang.Long.valueOf(counterTextview!!.text.toString())
//                    val incrementLong = inc + 1
//                    counterTextview!!.text = incrementLong.toString() + ""
//                    val productPrice = java.lang.Long.valueOf(productsItems[layoutPosition].Price)
//                    val itemQuantity = java.lang.Long.valueOf(counterTextview!!.text.toString())
//
//
//                    val bitmapAdd: Bitmap? = null
//
//
//                    iShopDetailAdapter.onIncrementButtonClick(
//                        java.lang.Long.valueOf(counterTextview!!.text.toString()),
//                        productPrice.toString(),
//                        (productPrice * itemQuantity).toString(),
//                        productsItems[layoutPosition].name!!,
//                        java.lang.Long.valueOf(productsItems[layoutPosition].Regular_price), imgProductCopy,
//                        bitmapAdd, productsItems[layoutPosition].image_json!![0],
//                        productsItems[layoutPosition].id!!, "0",
//                        "",
//                        productsItems[layoutPosition].image_json as List<String>
//                    )
//                }
//
//                R.id.imgShareProductDetails -> {
//
//                    val bitmapImage: Bitmap? = null
//                    iShopDetailAdapter.shareItemsDetails(productsItems[layoutPosition], bitmapImage)
//                }
//            }
//        }

    }

    override fun getItemCount(): Int {
        return productsItems.size
    }


    fun removeAll() {
        this.productsItems.clear()
        notifyDataSetChanged()
    }

    fun removeAll(status: Boolean, position: Int) {
        // productsItems.get(position).setFavourite(status);
        notifyDataSetChanged()
    }

    fun setIsfavourite(status: Boolean, position: Int) {
        notifyDataSetChanged()
    }

    fun refreshProduct() {
        notifyDataSetChanged()

    }

    interface IShopDetailAdapter {
        fun onAddButtonClick(productItems: Product)

        fun onViewDetailsButtonClick(productItems: Product)

        fun onFavouriteButtonClick(
            productItems: Product, position: Int, bitmap: Any?, quantity: String,
            imgUrl: String?, productID: String, isEmailFav: String, productDesc: String, imgArrList: List<String>,
            nameSku: String
        )

        fun onIncrementButtonClick(
            quantity: Long, price: String, totalPrice: String, productName: String,
            cutPrice: Long, imgProductCopy: ImageView?, bitmap: Any?, imgUrl: String?,
            productID: String, isEmailSent: String, productDesc: String, imgArrList: List<String>,
            nameSku: String
        )

        fun onDecrementButtonClick(
            quantity: Long, price: String, totalPrice: String, productName: String,
            cutPrice: Long, imgProductCopy: ImageView?, bitmapAdd: Any?, imgUrl: String?,
            productID: String, isEmailSent: String, productDesc: String, imgArrList: List<String>,
            nameSku: String
        )


        fun onCardClick(
            productName: String, price: String, cutPrice: Long, quantity: Long, imgList: List<String>,
            bitmapImg: Bitmap?, about: String, sku: String, productID: String,
            nameSku: String
        )


        fun shareItemsDetails(productItems: Product, bitmapImg: Any?)

        fun removeItemFromCart(shoppingDetailModel: Product)

        fun getIsFavourites(productName: String, position: Int)

    }


    private fun bitmapToByteArray(bmp: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val byteArray = stream.toByteArray()
        bmp.recycle()
        return byteArray
    }


    fun filter(charText: String) {
        var charText = charText
        try {

            charText = charText.toLowerCase(Locale.getDefault())
            productsItems.clear()
            if (charText.length == 0) {

                productsItems.addAll(productsItemsSearch)
            } else if (charText.length > 0) {
                for (wp in productsItemsSearch) {
                    if (wp.name!!.toLowerCase(Locale.getDefault()).trim { it <= ' ' }
                            .contains(charText)) {
                        productsItems.add(wp)

                    }
                }
            }

            notifyDataSetChanged()
        } catch (ex: Exception) {
        }

    }

}
