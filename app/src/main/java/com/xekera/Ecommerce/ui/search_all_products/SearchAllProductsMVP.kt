package com.xekera.Ecommerce.ui.search_all_products

import android.graphics.Bitmap
import android.widget.ImageView
import com.xekera.Ecommerce.data.rest.INetworkListGeneral
import com.xekera.Ecommerce.data.rest.response.ProductResponse
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.AllProductsResponse
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.Product
import com.xekera.Ecommerce.data.room.model.AddToCart
import com.xekera.Ecommerce.data.room.model.Favourites
import com.xekera.Ecommerce.ui.adapter.SearchAllProductsAdapter
import okhttp3.ResponseBody

interface SearchAllProductsMVP {

    interface View {

        fun showProgressDialogPleaseWait()

        fun hideProgressDialogPleaseWait()

        fun showToastShortTime(message: String)

        fun showToastLongTime(message: String)

        fun showSnackBarShortTime(message: String, view: android.view.View)

        fun showSnackBarLongTime(message: String, view: android.view.View)

        fun showRecylerViewProductsDetail(shopDetailsAdapter: SearchAllProductsAdapter)

        fun setCountZero(counts: Int)

        fun setDecrementCount(counts: Int)

        fun showSnackBarShortTime(message: String)

        fun setFavouriteButtonStatus(status: Boolean, position: Int)


        fun setIsFavourites(isFavourites: Boolean, position: Int)

        fun hideCircularProgressBar()

        fun showCircularProgressBar()

        fun showData()

        fun hideData()

        fun showAllData()

        fun hideAllData()

        fun setAdapterItems(products: List<Product?>?)

        fun getFavourites(response: ProductResponse)

        fun getFavCounts();

        fun getCartCount(): Int
    }

    interface Presenter {
        fun setView(view: SearchAllProductsMVP.View)


        fun saveProductDetails(
            quantity: Long, price: String, totalPrice: String, productName: String, cutPrice: Long,
            imgProductCopy: ImageView, bitmap: Any?, imgUrl: String?, productID: String,
            isEmailSent: String, productDesc: String, imgArrList: List<String>, nameSku: String
        )

        fun saveProductDecrementDetails(
            quantity: Long, price: String, totalPrice: String,
            productName: String, cutPrice: Long, imgProductCopy: ImageView,
            bitmapAdd: Any?, imgUrl: String?, productID: String, isEmailSent: String,
            productDesc: String, imgArrList: List<String>, nameSku: String
        )


        fun updateItemCountInDB(
            quantity: String,
            itemPrice: String,
            productName: String,
            cutPrice: String,
            imgProductCopy: ImageView
        )

        fun updateItemCountInDBForDecrement(
            quantity: String,
            itemPrice: String,
            productName: String,
            cutPrice: String,
            imgProductCopy: ImageView
        )


        fun setActionListener(actionListener: SearchAllProductsPresenter.ProductItemActionListener)

        fun removeItem(shoppingDetailModel: Product)


        fun removeItem(productName: String, position: Int)

        fun addItemToFavourites(favourites: Favourites, isChecked: Boolean)

        fun getFavouritesList(response: ProductResponse)

        fun getFavouritesListByProductName(productName: String, position: Int)

        fun isAlreadyAddedInFavourites(
            productItems: Product, position: Int, bitmap: Any?, quantity: String,
            imgUrl: String?, productID: String, isEmailFav: String, productDesc: String,
            imgArrList: List<String>, nameSku: String
        )

        fun setProductItemsDetails()

        fun addToCartApi(
            productId: String?, quantity: String, price: String?, discountPrice: String?,
            randomKey: String, imgProductCopy: ImageView
        )
    }

    interface Model {

        fun getProductCount(productName: String, iFetchCartDetailsList: SearchAllProductsModel.IFetchCartDetailsList)

        fun saveProductDetails(addToCart: AddToCart, iSaveProductDetails: SearchAllProductsModel.ISaveProductDetails)

        fun updateItemCountInDB(
            quantity: String, itemPrice: String, productName: String, cutPrice: String,
            iSaveProductDetails: SearchAllProductsModel.ISaveProductDetails
        )

        fun removeSelectedCartDetails(
            productName: String,
            iRemoveSelectedItemDetails: SearchAllProductsModel.IRemoveSelectedItemDetails
        )

        fun getCartDetails(iFetchCartDetailsList: SearchAllProductsModel.IFetchCartDetailsList)

        fun removeFromFavourite(
            productName: String,
            iRemoveSelectedItemDetails: SearchAllProductsModel.IRemoveSelectedItemDetails
        )

        fun addItemToFavourites(favourites: Favourites, iSaveProductDetails: SearchAllProductsModel.ISaveProductDetails)

        fun checkItemAlreadyAddedOrNot(
            itemName: String,
            iFetchCartDetailsList: SearchAllProductsModel.IFetchFavDetailsList
        )

        fun getFavouriteDetailsList(iFetchOrderDetailsList: SearchAllProductsModel.IFetchOrderDetailsList)

        fun getFavouriteDetailsListByName(
            productName: String,
            iFetchOrderDetailsList: SearchAllProductsModel.IFetchOrderDetailsList
        )


        fun checkItemAlreadyAddedOrNot(
            itemName: String,
            iFetchCartDetailsList: SearchAllProductsModel.IFetchCartDetailsList
        )

        fun getProductItemsDetails(iNetworkListGeneral: INetworkListGeneral<AllProductsResponse>)
        fun addToCart(
            productId: String?, quantity: String, price: String?, discountPrice: String?,
            randomKey: String, iNetworkListGeneral: INetworkListGeneral<ResponseBody>
        )

    }
}