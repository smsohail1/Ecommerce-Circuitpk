package com.xekera.Ecommerce.ui.search_all_products

import android.graphics.Bitmap
import android.widget.ImageView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.xekera.Ecommerce.data.rest.INetworkListGeneral
import com.xekera.Ecommerce.data.rest.response.ProductResponse
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.AllProductsResponse
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.Product
import com.xekera.Ecommerce.data.room.model.AddToCart
import com.xekera.Ecommerce.data.room.model.Favourites
import com.xekera.Ecommerce.util.AppConstants
import com.xekera.Ecommerce.util.SessionManager
import com.xekera.Ecommerce.util.Utils
import okhttp3.ResponseBody

import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class SearchAllProductsPresenter(
    private val model: SearchAllProductsMVP.Model,
    private val sessionManager: SessionManager,
    private val utils: Utils
) : SearchAllProductsMVP.Presenter {
    private var view: SearchAllProductsMVP.View? = null
    private var actionListener: SearchAllProductsPresenter.ProductItemActionListener? = null

    val currentDate: String
        get() {
            try {

                val c = Calendar.getInstance()
                val df = SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_TWO)
                return df.format(c.time)
            } catch (e: Exception) {
                return ""
            }

        }

    override fun setView(view: SearchAllProductsMVP.View) {
        this.view = view
    }


    override fun setActionListener(actionListener: SearchAllProductsPresenter.ProductItemActionListener) {
        this.actionListener = actionListener
    }

    override fun removeItem(shoppingDetailModel: Product) {

        model.checkItemAlreadyAddedOrNot(
            shoppingDetailModel.name!!,
            object : SearchAllProductsModel.IFetchCartDetailsList {
                override fun onCartDetailsReceived(addToCarts: List<AddToCart>) {
                    if (addToCarts == null || addToCarts.size == 0) {
                        //view.showToastShortTime("Item not available in cart");
                        return
                    } else {
                        removeItem(shoppingDetailModel.name)

                    }
                }

                override fun onErrorReceived(ex: Exception) {
                    ex.printStackTrace()
                    view!!.showToastShortTime(ex.message!!)
                }
            })


    }

    private fun removeItem(productName: String) {
        model.removeSelectedCartDetails(productName, object : SearchAllProductsModel.IRemoveSelectedItemDetails {
            override fun onSuccess() {
                view!!.showToastShortTime("Item removed from cart.")
                getUpdatedTotalCount()

            }

            override fun onError(ex: Exception) {
                ex.printStackTrace()
                view!!.showToastShortTime(ex.message!!)
            }
        })

    }


    override fun removeItem(productName: String, position: Int) {
        model.removeFromFavourite(productName, object : SearchAllProductsModel.IRemoveSelectedItemDetails {
            override fun onSuccess() {
                view!!.showToastShortTime("Item removed from favourite.")
                view!!.setFavouriteButtonStatus(false, position)
                view!!.getFavCounts();

            }

            override fun onError(ex: Exception) {
                ex.printStackTrace()
                view!!.showToastShortTime(ex.message!!)
            }
        })


    }

    override fun addItemToFavourites(favourites: Favourites, isChecked: Boolean) {
        insertSelectedFavourites(favourites)
    }

    override fun getFavouritesList(response: ProductResponse) {
        model.getFavouriteDetailsList(object : SearchAllProductsModel.IFetchOrderDetailsList {
            override fun onCartDetailsReceived(favourites: List<Favourites>) {
                if (favourites == null || favourites.size == 0) {
                    //  view.setFavouriteList(favourites);
                    return
                } else {
                    // view.setFavouriteList(favourites);
                }
            }

            override fun onErrorReceived(ex: Exception) {
                ex.printStackTrace()


                view!!.showToastShortTime(ex.message!!)
            }
        })
    }

    override fun getFavouritesListByProductName(productName: String, position: Int) {
        // isFavourite(productName, position);
        model.getFavouriteDetailsListByName(productName, object : SearchAllProductsModel.IFetchOrderDetailsList {
            override fun onCartDetailsReceived(favourites: List<Favourites>) {
                if (favourites == null || favourites.size == 0) {
                    view!!.setIsFavourites(false, position)
                    // view.setFavouriteList(favourites);
                    return
                } else {
                    view!!.setIsFavourites(true, position)
                    //view.setFavouriteList(favourites);
                }
            }

            override fun onErrorReceived(ex: Exception) {
                ex.printStackTrace()
                view!!.showToastShortTime(ex.message!!)
            }
        })
    }

    override fun isAlreadyAddedInFavourites(
        productItems: Product, position: Int, bitmap: Any?,
        quantity: String, imgUrl: String?, productID: String,
        isEmailFav: String, productDesc: String, imgArrList: List<String>,
        nameSku: String
    ) {
        model.getFavouriteDetailsListByName(
            productItems.name!!,
            object : SearchAllProductsModel.IFetchOrderDetailsList {
                override fun onCartDetailsReceived(favourites: List<Favourites>) {
                    if (favourites == null || favourites.size == 0) {
                        //   byte[] bmp = bitmapToByteArray(bitmap);
                        val bmp = ByteArray(0)

                        var formattedDate = ""
                        formattedDate = currentDate

                        val jsonObjectImg = Gson().toJson(imgArrList)

                        val fav: Favourites
                        if (quantity.toInt() == 0) {
                            val totalPrice = java.lang.Long.valueOf(productItems.Price) * 1

                            fav = Favourites(
                                productItems.name, productItems.Price,
                                productItems.Regular_price.toString(), "In Stock", formattedDate,
                                bmp, "1", totalPrice.toString(), imgUrl, productID, isEmailFav, productDesc,
                                jsonObjectImg,
                                nameSku
                            )
                        } else {
                            val totalPrice =
                                java.lang.Long.valueOf(productItems.Price) * java.lang.Long.valueOf(quantity)

                            fav = Favourites(
                                productItems.name, productItems.Price,
                                productItems.Regular_price.toString(), "In Stock", formattedDate,
                                bmp, quantity, totalPrice.toString(), imgUrl, productID, isEmailFav, productDesc,
                                jsonObjectImg,
                                nameSku
                            )
                        }

                        addItemToFavourites(fav, true)

                    } else {
                        removeItem(productItems.name, position)

                    }
                }

                override fun onErrorReceived(ex: Exception) {
                    ex.printStackTrace()
                    view!!.showToastShortTime(ex.message!!)
                }
            })
    }

    override fun setProductItemsDetails() {

        model.getProductItemsDetails(object : INetworkListGeneral<AllProductsResponse> {
            override fun onSuccess(response: AllProductsResponse?) {
                if (response == null) {
                    view!!.showToastShortTime("Error while fetch data.")
                    view!!.hideCircularProgressBar()
                    view!!.hideData()
                    view!!.hideAllData()

                    return
                } else {

                    val productResponses = response.Products
                    if (productResponses == null) {
                        view!!.showToastShortTime("Error while fetch data.")
                        view!!.hideCircularProgressBar()
                        view!!.hideData()
                        view!!.hideAllData()
                        return
                    }
                    if (productResponses.size > 0) {

                        //view.getFavourites(response);
                        view!!.setAdapterItems(productResponses)
                        return
                    } else {
                        view!!.showToastShortTime("No product available.")
                        view!!.hideCircularProgressBar()
                        view!!.hideData()
                        view!!.hideAllData()
                        //
                        return
                    }
                }
            }

            override fun onFailure(t: Throwable) {
                view!!.hideCircularProgressBar()
                if (t.message != null) {
                    view!!.showToastShortTime(t.message!!)
                } else {
                    view!!.showToastShortTime("Error while fetching products.")
                }

            }
        })
    }

    override fun addToCartApi(
        productId: String?,
        quantity: String,
        price: String?,
        discountPrice: String?,
        randomKey: String,
        imgProductCopy: ImageView
    ) {
        view?.showProgressDialogPleaseWait()
        model.addToCart(
            productId,
            quantity,
            price,
            discountPrice,
            randomKey,
            object : INetworkListGeneral<ResponseBody> {
                override fun onSuccess(response: ResponseBody?) {
                    view?.hideProgressDialogPleaseWait()
                    view?.hideCircularProgressBar()

                    if (response == null) {
                        view?.showToastShortTime("Error while add to cart.")

                        return
                    } else {
                        view?.showToastShortTime("Item added to cart.")

                        if (actionListener != null)
                            actionListener!!.onItemTap(imgProductCopy, view!!.getCartCount())
                    }
                }

                override fun onFailure(t: Throwable) {
                    view?.hideCircularProgressBar()
                    view?.hideProgressDialogPleaseWait()
                    if (t?.message != null) {
                        view?.showToastShortTime(t.message!!)
                    } else {
                        view?.showToastShortTime("Error while add product.")
                    }

                }
            })
    }


    fun insertSelectedFavourites(favourites: Favourites) {

        addItem(favourites)

    }


    private fun addItem(favourites: Favourites) {

        model.addItemToFavourites(favourites, object : SearchAllProductsModel.ISaveProductDetails {
            override fun onProductDetailsSaved(isAdded: Boolean) {
                if (isAdded) {

                    view!!.showToastShortTime("Item added to favorites.")
                    view!!.getFavCounts();

                } else {


                    view!!.showToastShortTime("Error while add to favorites.")
                    view!!.getFavCounts();

                }
            }

            override fun onErrorReceived(ex: Exception) {

                view!!.showToastShortTime("Error while saving data.")

            }
        })
    }

    fun getFavourites() {
        model.getFavouriteDetailsList(object : SearchAllProductsModel.IFetchOrderDetailsList {
            override fun onCartDetailsReceived(favourites: List<Favourites>) {

                if (favourites == null || favourites.size == 0) {
                    val listFavourite = ArrayList<String>()

                    for (fav in favourites!!) {
                        listFavourite.add(fav.itemName)
                    }
                    val favouriteSet = HashSet(listFavourite)

                    sessionManager.isFavouriteList = favouriteSet

                    return
                } else {
                    val listFavourite = ArrayList<String>()

                    for (fav in favourites) {
                        listFavourite.add(fav.itemName)
                    }
                    val favouriteSet = HashSet(listFavourite)

                    sessionManager.isFavouriteList = favouriteSet

                }
            }

            override fun onErrorReceived(ex: Exception) {
                ex.printStackTrace()


                view!!.showToastShortTime(ex.message!!)
            }
        })
    }


    override fun saveProductDetails(
        quantity: Long, price: String, totalPrice: String, productName: String,
        cutPrice: Long, imgProductCopy: ImageView,
        bitmap: Any?, imgUrl: String?, productID: String, isEmailSent: String,
        productDesc: String, imgArrList: List<String>,
        nameSku: String
    ) {
        model.getProductCount(productName, object : SearchAllProductsModel.IFetchCartDetailsList {
            override fun onCartDetailsReceived(addToCartList: List<AddToCart>) {
                if (addToCartList == null || addToCartList.size == 0) {

                    //  byte[] bmp = bitmapToByteArray(bitmap);
                    val bmp = ByteArray(0)
                    var formattedDate = ""
                    formattedDate = currentDate
                    val jsonObjectImg = Gson().toJson(imgArrList)

                    val addToCart = AddToCart(
                        "", productName, totalPrice, quantity.toString(),
                        "N", bmp, cutPrice.toString(), price, formattedDate, imgUrl, productID,
                        isEmailSent, productDesc, jsonObjectImg,
                        nameSku
                    )
                    noProductFound(addToCart, imgProductCopy)
                    return
                } else {

                    updateItemCountInDB(
                        quantity.toString(), totalPrice,
                        productName, cutPrice.toString(), imgProductCopy
                    )
                }

            }

            override fun onErrorReceived(ex: Exception) {
                view!!.showToastShortTime("Error while in saving data.")

            }
        })

    }

    private fun bitmapToByteArray(bmp: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        bmp.recycle()
        return byteArray
    }

    override fun saveProductDecrementDetails(
        quantity: Long, price: String, totalPrice: String, productName: String,
        cutPrice: Long, imgProductCopy: ImageView, bitmapAdd: Any?,
        imgUrl: String?, productID: String, isEmailSent: String,
        productDesc: String, imgArrList: List<String>,
        nameSku: String
    ) {
        model.getProductCount(productName, object : SearchAllProductsModel.IFetchCartDetailsList {
            override fun onCartDetailsReceived(addToCartList: List<AddToCart>) {
                if (addToCartList == null || addToCartList.size == 0) {

                    var formattedDate = ""
                    formattedDate = currentDate
                    // byte[] byteImg = bitmapToByteArray(bitmapAdd);
                    val byteImg = ByteArray(0)
                    val jsonObjectImg = Gson().toJson(imgArrList)

                    val addToCart = AddToCart(
                        "", productName, totalPrice, quantity.toString(),
                        "N", byteImg, cutPrice.toString(), price, formattedDate, imgUrl,
                        productID, isEmailSent, productDesc, jsonObjectImg,
                        nameSku
                    )
                    noProductFoundForDecrement(addToCart, imgProductCopy)
                    return
                } else {

                    updateItemCountInDBForDecrement(
                        quantity.toString(), totalPrice,
                        productName, cutPrice.toString(), imgProductCopy
                    )
                }

            }

            override fun onErrorReceived(ex: Exception) {
                view!!.showToastShortTime("Error while in saving data.")

            }
        })

    }


    private fun noProductFound(addToCart: AddToCart, imgProductCopy: ImageView) {

        model.saveProductDetails(addToCart, object : SearchAllProductsModel.ISaveProductDetails {
            override fun onProductDetailsSaved(isAdded: Boolean) {
                if (isAdded) {
                    //view.showSnackBarShortTime("Item added to cart successfully.");
                    getUpdatedTotalCount(imgProductCopy)


                }
            }

            override fun onErrorReceived(ex: Exception) {
                view!!.showToastShortTime("Error while saving data.")

            }
        })
    }

    private fun noProductFoundForDecrement(addToCart: AddToCart, imgProductCopy: ImageView) {

        model.saveProductDetails(addToCart, object : SearchAllProductsModel.ISaveProductDetails {
            override fun onProductDetailsSaved(isAdded: Boolean) {
                if (isAdded) {
                    view!!.showToastShortTime("Cart updated successfully.")
                    getUpdatedTotalCountForDecrement(imgProductCopy)


                }
            }

            override fun onErrorReceived(ex: Exception) {
                view!!.showToastShortTime("Error while saving data.")

            }
        })
    }

    override fun updateItemCountInDB(
        quantity: String,
        itemPrice: String,
        productName: String,
        cutPrice: String,
        imgProductCopy: ImageView
    ) {
        model.updateItemCountInDB(
            quantity,
            itemPrice,
            productName,
            cutPrice,
            object : SearchAllProductsModel.ISaveProductDetails {
                override fun onProductDetailsSaved(updated: Boolean) {
                    if (updated) {
                        //  view.showToastShortTime("Cart updated successfully.");

                        getUpdatedTotalCount(imgProductCopy)
                    } else {
                        view!!.showToastShortTime("Error while saving data.")

                    }

                }

                override fun onErrorReceived(ex: Exception) {
                    ex.printStackTrace()
                    view!!.showToastShortTime("Error while saving data.")

                }
            })

    }


    override fun addToFavouritesServer(productId: String, username: String, email: String, name: String) {

        view?.showProgressDialogPleaseWait()
        val jsonObject = JsonObject()
        jsonObject.addProperty("postid", productId)
        jsonObject.addProperty("username", username)
        jsonObject.addProperty("email", email)
        model.addToFavouritesServer(jsonObject, object : INetworkListGeneral<ResponseBody> {
            override fun onSuccess(response: ResponseBody) {
                view?.hideProgressDialogPleaseWait()

                view?.showToastShortTime("Add to favorite")

                view?.addToFavoriteList(name)
            }

            override fun onFailure(t: Throwable) {
                view?.hideProgressDialogPleaseWait()
                if (t?.message != null) {
                    view?.showToastShortTime(t.message!!)
                } else {
                    view?.showToastShortTime("Error while add favourite.")
                }
            }
        })
    }

    override fun updateItemCountInDBForDecrement(
        quantity: String, itemPrice: String, productName: String, cutPrice: String,
        imgProductCopy: ImageView
    ) {
        model.updateItemCountInDB(
            quantity,
            itemPrice,
            productName,
            cutPrice,
            object : SearchAllProductsModel.ISaveProductDetails {
                override fun onProductDetailsSaved(updated: Boolean) {
                    if (updated) {
                        // view.showToastShortTime("Cart updated successfully.");

                        getUpdatedTotalCountForDecrement(imgProductCopy)
                    } else {
                        view!!.showToastShortTime("Error while saving data.")

                    }

                }

                override fun onErrorReceived(ex: Exception) {
                    ex.printStackTrace()
                    view!!.showToastShortTime("Error while saving data.")

                }
            })

    }


    private fun getUpdatedTotalCount(imgProductCopy: ImageView) {
        model.getCartDetails(object : SearchAllProductsModel.IFetchCartDetailsList {
            override fun onCartDetailsReceived(addToCarts: List<AddToCart>) {
                if (addToCarts == null || addToCarts.size == 0) {
                    //                    if (actionListener != null)
                    //                        actionListener.onItemTap(imgProductCopy, 0);
                    view!!.setCountZero(0)


                    return
                } else {
                    if (actionListener != null)
                        actionListener!!.onItemTap(imgProductCopy, addToCarts.size)

                }
            }

            override fun onErrorReceived(ex: Exception) {
                ex.printStackTrace()

                view!!.showToastShortTime(ex.message!!)
            }
        })
    }


    private fun getUpdatedTotalCount() {
        model.getCartDetails(object : SearchAllProductsModel.IFetchCartDetailsList {
            override fun onCartDetailsReceived(addToCarts: List<AddToCart>) {
                if (addToCarts == null || addToCarts.size == 0) {
                    view!!.setCountZero(0)

                    return
                } else {
                    view!!.setDecrementCount(addToCarts.size)

                }
            }

            override fun onErrorReceived(ex: Exception) {
                ex.printStackTrace()

                view!!.showToastShortTime(ex.message!!)
            }
        })
    }


    private fun getUpdatedTotalCountForDecrement(imgProductCopy: ImageView) {
        model.getCartDetails(object : SearchAllProductsModel.IFetchCartDetailsList {
            override fun onCartDetailsReceived(addToCarts: List<AddToCart>) {
                if (addToCarts == null || addToCarts.size == 0) {
                    view!!.setCountZero(0)


                    return
                } else {
                    //                    if (actionListener != null)
                    //                        actionListener.onItemTap(imgProductCopy, addToCarts.size());

                    view!!.setDecrementCount(addToCarts.size)
                }
            }

            override fun onErrorReceived(ex: Exception) {
                ex.printStackTrace()

                view!!.showToastShortTime(ex.message!!)
            }
        })
    }


    interface ProductItemActionListener {
        fun onItemTap(imageView: ImageView, cartsCount: Int)
    }
}

