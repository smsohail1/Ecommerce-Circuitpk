package com.xekera.Ecommerce.ui.search_all_products

import android.Manifest
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.*
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.xekera.Ecommerce.App
import com.xekera.Ecommerce.R
import com.xekera.Ecommerce.data.rest.response.ProductResponse
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.Product
import com.xekera.Ecommerce.data.room.AppDatabase
import com.xekera.Ecommerce.data.room.model.Favourites
import com.xekera.Ecommerce.ui.BaseActivity
import com.xekera.Ecommerce.ui.adapter.SearchAllProductsAdapter
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel
import com.xekera.Ecommerce.ui.favourites.FavouritesFragment.*
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedFragment
import com.xekera.Ecommerce.util.*
import kotlinx.android.synthetic.main.dialog_share.*
import kotlinx.android.synthetic.main.fragment_dashboard_details.*


import javax.inject.Inject
import java.io.ByteArrayOutputStream
import java.io.*
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale


/**
 * A simple [Fragment] subclass.
 */
class SearchAllProductsFragment : Fragment(), SearchAllProductsMVP.View, SearchAllProductsAdapter.IShopDetailAdapter,
    View.OnClickListener {

//    @BindView(R.id.edtSearchProduct)
//    protected var edtSearchProduct: EditText? = null
//    @BindView(R.id.recyclerViewProductDetails)
//    protected var recyclerViewProductDetails: RecyclerView? = null
//    @BindView(R.id.progressbar)
//    protected var progressbar: ProgressBar? = null
//    @BindView(R.id.searchParent)
//    protected var searchParent: LinearLayout? = null
//    @BindView(R.id.allData)
//    protected var allData: LinearLayout? = null
//    @BindView(R.id.clearText)
//    protected var clearText: ImageView? = null

    @Inject
    lateinit var presenter: SearchAllProductsMVP.Presenter
    @Inject
    lateinit var utils: Utils
    @Inject
    lateinit var toastUtil: ToastUtil
    @Inject
    lateinit var snackUtil: SnackUtil
    @Inject
    lateinit var sessionManager: SessionManager
    @Inject
    lateinit var appDatabase: AppDatabase

    internal var isProgressBarShowing = false


    lateinit var shopDetailsAdapter: SearchAllProductsAdapter

    lateinit var shopDetails: List<ShoppingDetailModel>

    var productName = ""

    var progressDialogControllerPleaseWait: ProgressCustomDialogController? = null

    var favList: List<Favourites>? = null
    lateinit var toastView: View

    protected val currentDate: String
        get() {
            try {

                val c = Calendar.getInstance()
                val df = SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_TWO)
                return df.format(c.time)
            } catch (e: Exception) {
                return ""
            }

        }


    private val PICK_IMAGE_REQUEST = 1
    private var shareDialog: ShareDialog? = null


    // PERMISSIONS CODE
    private val REQUEST_CODE_ASK_PERMISSIONS = 123
    private var mPermissionDenied = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as App).appComponent.inject(this)
        //(getActivity()!!.getApplication() as App).getAppComponent().inject(this)

    }

    override fun onResume() {
        super.onResume()

        try {
            presenter.setView(this)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_dashboard_details, container, false)

        // initializeViews(v)


        return v
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews();

    }


    private fun initializeViews() {
        // ButterKnife.bind(this, v)
        presenter.setView(this)

        toastView = layoutInflater.inflate(R.layout.activity_toast_custom_view, null)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        progressDialogControllerPleaseWait = ProgressCustomDialogController(activity, R.string.please_wait)

        recyclerViewProductDetails?.layoutManager = LinearLayoutManager(activity)

        val dataTime = getCurrentDateTime()
        if (utils.isTextNullOrEmpty(sessionManager.keyRandomKey)) {
            sessionManager.keyRandomKey = "mobile$dataTime"
        }
        edtSearchProduct?.setText("")



        clearText?.setOnClickListener(this)

        shopDetails = ArrayList()

        isProgressBarShowing = true
        hideData()
        if (utils.isInternetAvailable) {
            progressbar?.visibility = View.VISIBLE


            Handler().postDelayed({
                presenter.setProductItemsDetails();
            }, 300)


        } else {
            showToastShortTime("Please connect to internet.")
        }


        edtSearchProduct?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(arg0: Editable) {
                // TODO Auto-generated method stub
                try {

                    if (!isProgressBarShowing) {
                        val text =
                            edtSearchProduct?.text.toString().toLowerCase(Locale.getDefault()).trim { it <= ' ' }

                        shopDetailsAdapter.filter(text)
                    }

                } catch (ex: Exception) {

                }

            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int,
                arg2: Int, arg3: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun onTextChanged(
                arg0: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                // TODO Auto-generated method stub
            }
        })

    }


    override fun showProgressDialogPleaseWait() {
        progressDialogControllerPleaseWait?.showDialog()
    }

    override fun hideProgressDialogPleaseWait() {
        progressDialogControllerPleaseWait?.hideDialog()
    }

    override fun showToastShortTime(message: String) {
        toastUtil?.showToastShortTime(message, toastView)
    }

    override fun showToastLongTime(message: String) {
        toastUtil?.showToastLongTime(message)
    }

    override fun showSnackBarShortTime(message: String, view: View) {
        snackUtil?.showSnackBarShortTime(view, message)
    }

    override fun showSnackBarLongTime(message: String, view: View) {
        snackUtil?.showSnackBarLongTime(view, message)
    }

    override fun showRecylerViewProductsDetail(shopDetailsAdapter: SearchAllProductsAdapter) {
        recyclerViewProductDetails?.adapter = shopDetailsAdapter
    }

    override fun getFavCounts() {
        (activity as BaseActivity).favCounts()

    }

    override fun setCountZero(counts: Int) {
        (activity as BaseActivity).addItemToCart(0)

    }

    override fun setDecrementCount(counts: Int) {
        (activity as BaseActivity).removeItemToCart(counts)

    }

    override fun showSnackBarShortTime(message: String) {
        showSnackBarShortTime(message, view!!)
    }

    override fun setFavouriteButtonStatus(status: Boolean, position: Int) {
        shopDetailsAdapter?.removeAll(status, position)
    }


    override fun setIsFavourites(isFavourites: Boolean, position: Int) {
        shopDetailsAdapter?.setIsfavourite(isFavourites, position)
    }


//    fun newInstance(ProductName: String): SearchAllProductsFragment? {
//        var fragment: SearchAllProductsFragment? = null
//        try {
//
//            val bundle = Bundle()
//            bundle.putString(KEY_SHOP_NAME_DETAILS, ProductName)
//            fragment = SearchAllProductsFragment()
//            fragment.arguments = bundle
//
//            return fragment
//        } catch (e: Exception) {
//
//        }
//
//        return fragment
//    }

    private fun getCurrentDateTime(): String {
        try {

            val c = Calendar.getInstance()
            val df = SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOUR)
            return df.format(c.time)
        } catch (e: Exception) {
            return ""
        }

    }

    override fun onAddButtonClick(productItems: Product) {

    }

    override fun onViewDetailsButtonClick(productItems: Product) {

    }


    override fun onIncrementButtonClick(
        quantity: Long, price: String, totalPrice: String, productName: String, cutPrice: Long,
        imgProductCopy: ImageView?, bitmap: Any?, imgUrl: String?, productID: String,
        isEmailSent: String, productDesc: String, imgArrList: List<String>,
        nameSku: String
    ) {

        presenter.saveProductDetails(
            quantity, price, totalPrice, productName, cutPrice, imgProductCopy!!, bitmap, imgUrl,
            productID, isEmailSent, productDesc, imgArrList,
            nameSku
        )
    }

    override fun onDecrementButtonClick(
        quantity: Long, price: String, totalPrice: String, productName: String, cutPrice: Long,
        imgProductCopy: ImageView?, bitmapAdd: Any?, imgUrl: String?, productID: String,
        isEmailSent: String, productDesc: String, imgArrList: List<String>,
        nameSku: String
    ) {

        presenter.saveProductDecrementDetails(
            quantity, price, totalPrice, productName, cutPrice, imgProductCopy!!, bitmapAdd,
            imgUrl, productID, isEmailSent, productDesc, imgArrList,
            nameSku
        )

    }

    override fun onFavouriteButtonClick(
        productItems: Product, position: Int, bitmap: Any?, quantity: String, imgUrl: String?,
        productID: String, isEmailFav: String, productDesc: String, imgArrList: List<String>,
        nameSku: String
    ) {

        presenter.isAlreadyAddedInFavourites(
            productItems,
            position,
            bitmap,
            quantity,
            imgUrl,
            productID,
            isEmailFav,
            productDesc,
            imgArrList,
            nameSku
        )

    }

    private fun bitmapToByteArray(bmp: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        bmp.recycle()

        return byteArray
    }


    override fun onCardClick(
        productName: String, price: String, cutPrice: Long, quantity: Long,
        imgList: List<String>, bitmapImg: Bitmap?, about: String, sku: String, productID: String,
        nameSku: String
    ) {
        utils?.hideSoftKeyboard(edtSearchProduct)

        Handler().postDelayed({
            val shopCardSelectedFragment = ShopCardSelectedFragment()

            (activity as BaseActivity).replaceFragmentForActivityTranstion(
                shopCardSelectedFragment.newInstance(
                    productName,
                    price, cutPrice, quantity, imgList, bitmapImg, about, sku, productID,
                    nameSku
                )
            )
        }, 200)

    }

    fun bitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val temp = android.util.Base64.encodeToString(b, Base64.DEFAULT)
        return temp
    }

    var isShareButtonClick = true

    override fun shareItemsDetails(productItems: Product, bitmapImg: Any?) {

        try {

            if (isShareButtonClick) {
                isShareButtonClick = false
                Handler().postDelayed({
                    showShareDialog(activity, productItems)
                    isShareButtonClick = true
                }, 300)
            }


        } catch (e: Exception) {
            Log.d("error", e.message)
        }


    }

    override fun onFavouriteBtnClickOnSever(productId: String, name: String) {
        presenter.addToFavouritesServer(productId, sessionManager.getusername(), sessionManager.email, name)

    }

    override fun showError(errorMsg: String) {
        showToastShortTime(errorMsg)

    }

    override fun addToFavoriteList(name: String) {
        shopDetailsAdapter.addFavList(name)
    }

    private fun showShareDialog(context: Context?, product: Product) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val v = dialog.window!!.decorView
        v.setBackgroundResource(android.R.color.transparent)

        dialog.setContentView(R.layout.dialog_share)

        val imgWhatsApp = dialog.findViewById(R.id.imgWhatsApp) as ImageView
        val imgFacebook = dialog.findViewById(R.id.imgFacebook) as ImageView
        val imgMessenger = dialog.findViewById(R.id.imgMessenger) as ImageView
        val imgTwitter = dialog.findViewById(R.id.imgTwitter) as ImageView
        val imgGmail = dialog.findViewById(R.id.imgGmail) as ImageView


        imgGmail.setOnClickListener {
            shareOnGmail(product, product.image_json!![0])
        }
        imgTwitter.setOnClickListener(View.OnClickListener {
            shareOnTwitter(product, product.image_json!![0])
        })

        imgWhatsApp.setOnClickListener(View.OnClickListener {
            /// selectImage();
            shareOnWhatsApp(product, product.image_json!![0])
        })


        imgFacebook.setOnClickListener(View.OnClickListener {
            val pm = activity!!.packageManager
            val isInstalled = isPackageInstalled(FACEBOOK_APP_PACKAGE, pm)
            if (isInstalled) {
                shareViaFacebook(product, product.image_json!![0])
            } else {
                shareViaFacebookLite(product, product.image_json!![0])

            }
        })

        imgMessenger.setOnClickListener(View.OnClickListener {
            val pm = activity!!.packageManager
            val isInstalled = isPackageInstalled(FACEBOOK_MESSENGER_PACKAGE, pm)
            if (isInstalled) {
                shareOnFacebookMessenger(product, product.image_json!![0])
            } else {
                shareOnFacebookMessengerLite(product, product.image_json!![0])
            }
        })

        dialog.show()
    }

    private fun shareOnFacebookMessenger(product: Product, url: String?) {

        val sendIntent = Intent()

        sendIntent.action = Intent.ACTION_SEND
        //        sendIntent.putExtra(Intent.EXTRA_TITLE,
        //                "Circuit.pk"
        //        );

        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            url + "\n\n" +
                    "Product Name: " + product.name + "\n" +
                    "New Price: " + product.Price + "\n" +
                    "Old Price: " + product.Regular_price + "\n" +
                    "Website: " + "https://circuit.pk/product/" + product.name_sku
        )


        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        sendIntent.type = "text/plain"
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        sendIntent.setPackage(FACEBOOK_MESSENGER_PACKAGE)

        try {
            startActivity(sendIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            toastUtil.showToastShortTime("Please install facebook messenger", toastView)
        }

    }

    private fun shareOnFacebookMessengerLite(product: Product, url: String?) {

        val sendIntent = Intent()

        sendIntent.action = Intent.ACTION_SEND
        //        sendIntent.putExtra(Intent.EXTRA_TITLE,
        //                "Circuit.pk"
        //        );

        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            (url + "\n\n" +
                    "Product Name: " + product.name + "\n" +
                    "New Price: " + product.Price + "\n" +
                    "Old Price: " + product.Regular_price + "\n" +
                    "Website: " + "https://circuit.pk/product/" + product.name_sku)
        )


        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        sendIntent.type = "text/plain"
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        sendIntent.setPackage(FACEBOOK_MESSENGER_LITE_PACKAGE)

        try {
            startActivity(sendIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            toastUtil.showToastShortTime("Please install facebook messenger", toastView)
        }

    }

    private fun shareViaFacebook(product: Product, url: String?) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND

        //        shareIntent.putExtra(Intent.EXTRA_TITLE,
        //                "Circuit.pk"
        //        );

        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            (url + "\n\n" +
                    "Product Name: " + product.name + "\n" +
                    "New Price: " + product.Price + "\n" +
                    "Old Price: " + product.Regular_price + "\n" +
                    "Website: " + "https://circuit.pk/product/" + product.name_sku)
        )


        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        shareIntent.type = "text/plain"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        shareIntent.setPackage(FACEBOOK_APP_PACKAGE)
        try {
            startActivity(shareIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            toastUtil.showToastShortTime("Please install Facebook app.", toastView)
        }

    }

    private fun shareViaFacebookLite(product: Product, url: String?) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND

        //        shareIntent.putExtra(Intent.EXTRA_TITLE,
        //                "Circuit.pk"
        //        );

        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            (url + "\n\n" +
                    "Product Name: " + product.name + "\n" +
                    "New Price: " + product.Price + "\n" +
                    "Old Price: " + product.Regular_price + "\n" +
                    "Website: " + "https://circuit.pk/product/" + product.name_sku)
        )


        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        shareIntent.type = "text/plain"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        shareIntent.setPackage(FACEBOOK_APP_LITE_PACKAGE)
        try {
            startActivity(shareIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            toastUtil.showToastShortTime("Please install Facebook app.", toastView)
        }

    }


    private fun shareOnWhatsApp(product: Product, url: String?) {
        val whatsappIntent = Intent()
        whatsappIntent.action = Intent.ACTION_SEND

        // whatsappIntent.setType("image/*");
        whatsappIntent.type = "text/plain"

        whatsappIntent.setPackage(WHATSAPP_PACKAGE)
        //        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
        //        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        whatsappIntent.putExtra(
            Intent.EXTRA_TEXT,
            (url + "\n\n" +
                    "Product Name: " + product.name + "\n" +
                    "New Price: " + product.Price + "\n" +
                    "Old Price: " + product.Regular_price + "\n" +
                    "Website: " + "https://circuit.pk/product/" + product.name_sku)
        )

        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);


        try {
            startActivity(whatsappIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            toastUtil.showToastShortTime("Whatsapp have not been installed.", toastView)
        }

    }

    private fun shareOnTwitter(product: Product, url: String?) {
        val twitter = Intent()
        twitter.action = Intent.ACTION_SEND

        twitter.type = "text/plain"

        twitter.setPackage(TWITTER_PACKAGE)
        //        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
        //        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        twitter.putExtra(
            Intent.EXTRA_TEXT,
            (url + "\n\n" +
                    "Product Name: " + product.name + "\n" +
                    "New Price: " + product.Price + "\n" +
                    "Old Price: " + product.Regular_price + "\n" +
                    "Website: " + "https://circuit.pk/product/" + product.name_sku)
        )

        twitter.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);


        try {
            startActivity(twitter)
        } catch (ex: android.content.ActivityNotFoundException) {
            toastUtil.showToastShortTime("Twitter have not been installed.", toastView)
        }

    }


    private fun shareOnWhatsApp(imagePath: Uri) {
        val whatsappIntent = Intent()
        whatsappIntent.action = Intent.ACTION_SEND

        // whatsappIntent.setType("image/*");
        whatsappIntent.type = "text/plain"

        whatsappIntent.setPackage(WHATSAPP_PACKAGE)
        //        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
        //        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        whatsappIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Website: " + "https://circuit.pk/"
        )


        // if (imagePath != null) {
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, imagePath)
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        whatsappIntent.type = "image/*"
        // }


        // whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);


        try {
            startActivity(whatsappIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            toastUtil.showToastShortTime("Whatsapp have not been installed.", toastView)
        }

    }

    private fun shareOnGmail(product: Product, url: String?) {

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val b = BitmapFactory.decodeResource(resources, R.drawable.icon_compnay_share)

        val gmail = Intent()
        gmail.action = Intent.ACTION_SEND
        gmail.type = "image/*"
        gmail.type = "text/plain"

        gmail.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        gmail.setPackage(GMAIL_PACKAGE)
        //        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");

        //        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        /*gmail.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + product.getName() + "\n" +
                        "New Price: " + product.getPrice() + "\n" +
                        "Old Price: " + product.getRegularPrice() + "\n" +
                        "Website: " + "https://circuit.pk/product/" + product.getNameSku());*/

        val productDescription = "Product Name: " + product.name + "\n" +
                "New Price: " + product.Price + "\n" +
                "Old Price: " + product.Regular_price + "\n" +
                "Website: " + "https://circuit.pk/product/" + product.name_sku + "\n\n"

        gmail.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val Weblink = "https://circuit.pk/"
        val mobileAppLink = "https://play.google.com/store/apps/details?id=" + activity!!.packageName
        val socialMediaLinks =
            "Social Media Pages:\n" + "Facebook: " + AppConstants.URL_CIRCUIT_PK_FACEBOOK_PAGE_URL + "\n" +
                    "Twitter: " + AppConstants.URL_CIRCUIT_PK_TWITTER_PAGE_URL + "\n" +
                    "Google Plus: " + AppConstants.URL_CIRCUIT_PK_GOOGLE_PLUS_PAGE_URL + "\n" +
                    "Pinterest: " + AppConstants.URL_CIRCUIT_PK_PINTEREST_PAGE_URL + "\n" +
                    "Youtube:" + AppConstants.URL_CIRCUIT_PK_YOUTUBE_PAGE_URL


        val textDesc =
            "I am using circuit.pk app.Here, you can purchase electronic components in low cost.\n\n" + "Below are the links of Website and mobile app.\n"
        val linkDesc =
            productDescription + textDesc + "Website: " + Weblink + "\n" + "Mobile App: " + mobileAppLink + "\n\n" + socialMediaLinks

        gmail.putExtra(Intent.EXTRA_TEXT, linkDesc)
        gmail.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(b))
        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);


        try {
            startActivity(gmail)
        } catch (ex: android.content.ActivityNotFoundException) {
            toastUtil.showToastShortTime("Gmail have not been installed.", toastView)
        }

    }


    private fun getLocalBitmapUri(bmp: Bitmap): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(
                activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 60, out)
            out.close()
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bmpUri
    }

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {

        var found = true

        try {

            packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {

            found = false
        }

        return found
    }


    protected fun shareOnFacebookMessenger(uri: Uri) {

        val sendIntent = Intent()

        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TITLE,
            "Check this app on: "
                    + GOOGLE_PLAY_STORE_URI
                    + "circit.pk"
        )


        sendIntent.putExtra(Intent.EXTRA_STREAM, uri)
        sendIntent.type = "image/*"

        sendIntent.setPackage(FACEBOOK_MESSENGER_PACKAGE)

        try {
            startActivity(sendIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            showToastShortTime("Please install facebook messenger")
        }

    }

    private fun shareOnFacebook() {


        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)

    }

    override fun showData() {
        searchParent?.visibility = View.VISIBLE
    }

    override fun hideData() {
        searchParent?.visibility = View.GONE
    }

    override fun showAllData() {
        allData?.visibility = View.VISIBLE
    }

    override fun hideAllData() {
        allData?.visibility = View.GONE
    }

    override fun setAdapterItems(products: List<Product?>?) {

//        shopDetailsAdapter = SearchAllProductsAdapter(
//            activity!!,
//            products, this
//        )
        shopDetailsAdapter = SearchAllProductsAdapter(
            activity!!, products as MutableList<Product>,
            this, utils, sessionManager
        )
        showRecylerViewProductsDetail(shopDetailsAdapter)
        hideCircularProgressBar()
        showAllData()
        showData()
        isProgressBarShowing = false
        presenter.setActionListener(object : SearchAllProductsPresenter.ProductItemActionListener {
            override fun onItemTap(imageView: ImageView, cartsCount: Int) {
                if (imageView != null) {
                    (activity as BaseActivity).makeFlyAnimation(imageView, cartsCount)
                    (activity as BaseActivity).addItemToCart(cartsCount)
                    //return true
                }
            }
        })
    }


    override fun addToCart(productItems: Product, imgProductCopy: ImageView) {
        presenter.addToCartApi(
            productItems.id, "1", productItems.Price, productItems.Regular_price,
            sessionManager.keyRandomKey, imgProductCopy
        )

    }

    override fun getCartCount(): Int {
        return (activity as BaseActivity).countsForActionBar()
    }

    override fun InternetError() {
        toastUtil.showToastShortTime("Please connect to internet", toastView)
    }

    override fun getFavourites(response: ProductResponse) {
        // presenter.getFavouritesList(response);

    }

    override fun hideCircularProgressBar() {
        progressbar?.visibility = View.GONE

    }

    override fun showCircularProgressBar() {
        progressbar?.visibility = View.VISIBLE

    }


    fun shareLinks() {
        shareDialog = ShareDialog(this)  // intialize facebook shareDialog.

        if (ShareDialog.canShow(ShareLinkContent::class.java)) {
            val linkContent = ShareLinkContent.Builder()
                .setQuote("test")
                .setContentTitle("Androidlift")
                .setContentDescription("Androidlift blog")
                .setContentUrl(Uri.parse("http://androidlift.info"))
                .build()

            shareDialog!!.show(linkContent)
        }
    }


    override fun removeItemFromCart(shoppingDetailModel: Product) {
        // showSnackBarShortTime("Please select atleast one item", getView());
        presenter.removeItem(shoppingDetailModel)

    }

    override fun getIsFavourites(productName: String, position: Int) {
        presenter.getFavouritesListByProductName(productName, position)
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermissions() {
        val hasReadPermission = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)
        val hasWritePermission =
            ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)


        if (hasReadPermission != PackageManager.PERMISSION_GRANTED || hasWritePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_ASK_PERMISSIONS
            )
            return
        } else {
            mPermissionDenied = false
            //permissionsGranted();
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission Allowed
                val hasReadPermission =
                    ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)
                val hasWritePermission =
                    ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                if (hasReadPermission == PackageManager.PERMISSION_GRANTED && hasWritePermission == PackageManager.PERMISSION_GRANTED) {
                    //permissionsGranted();
                    mPermissionDenied = false
                } else {
                    mPermissionDenied = true
                }
            } else {
                // showMissingPermissionError();
                // Permission Denied
                mPermissionDenied = true
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


    /**
     * Displays a dialog with error message explaining that the phone permission is missing.
     */
    private fun showMissingPermissionError() {
        toastUtil?.showToastLongTime("Please enable Read/Write Storage permission.")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.clearText -> {
                edtSearchProduct?.setText("")
                shopDetailsAdapter.filter("")
            }
        }//  showToastShortTime("d");
    }

    companion object {

        val KEY_SHOP_NAME_DETAILS = "shop_details_name"

        val FACEBOOK_MESSENGER_PACKAGE = "com.facebook.orca"
        val GOOGLE_PLAY_STORE_URI = "http://play.google.com/store/apps/details?id="
    }


}// Required empty public constructor

