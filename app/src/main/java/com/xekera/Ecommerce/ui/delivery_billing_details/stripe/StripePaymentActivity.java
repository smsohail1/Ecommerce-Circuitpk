package com.xekera.Ecommerce.ui.delivery_billing_details.stripe;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.CCFragment.CCNameFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.CCFragment.CCNumberFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.CCFragment.CCSecureCodeFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.CCFragment.CCValidityFragment;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;

public class StripePaymentActivity extends FragmentActivity implements FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.relativeParent)
    RelativeLayout relativeParent;

    @Inject
    protected SessionManager sessionManager;
    @Inject
    protected ToastUtil toastUtil;

    public CardFrontFragment cardFrontFragment;
    public CardBackFragment cardBackFragment;

    //This is our viewPager
    private ViewPager viewPager;

    CCNumberFragment numberFragment;
    //  CCNameFragment nameFragment;
    CCValidityFragment validityFragment;
    CCSecureCodeFragment secureCodeFragment;

    int total_item;
    boolean backTrack = false;

    private boolean mShowingBack = false;

    String cardNumber, cardCVV, cardValidity, cardName;
    View toastView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        ((App) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);

        cardFrontFragment = new CardFrontFragment();
        cardBackFragment = new CardBackFragment();

        if (savedInstanceState == null) {
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, cardFrontFragment).commit();

        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        getFragmentManager().addOnBackStackChangedListener(this);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == total_item)
                    btnNext.setText("SUBMIT");
                else
                    btnNext.setText("NEXT");

                Log.d("track", "onPageSelected: " + position);

                if (position == total_item) {
                    flipCard();
                    backTrack = true;
                } else if (position == total_item - 1 && backTrack) {
                    flipCard();
                    backTrack = false;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewPager.getCurrentItem();
                if (pos < total_item) {
                    viewPager.setCurrentItem(pos + 1);
                } else {
                    checkEntries();
                }

            }
        });

        relativeParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(StripePaymentActivity.this);
            }
        });


    }

    private void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void checkEntries() {
        // cardName = nameFragment.getName();
        cardNumber = numberFragment.getCardNumber();
        cardValidity = validityFragment.getValidity();
        cardCVV = secureCodeFragment.getValue();

//        if (TextUtils.isEmpty(cardName) || cardName.equalsIgnoreCase("")) {
//            Toast.makeText(StripePaymentActivity.this, "Enter Valid Name", Toast.LENGTH_SHORT).show();
//            viewPager.setCurrentItem(0);
//
//        }
        if (TextUtils.isEmpty(cardNumber) || !CreditCardUtils.isValid(cardNumber.replace(" ", ""))) {
            toastUtil.showToastShortTime("Enter Valid card number", toastView);
            // Toast.makeText(StripePaymentActivity.this, "Enter Valid card number", Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(0);
            //   flipCardOnBackPressed();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (mShowingBack) {
//
//                    }
//                    mShowingBack = true;
//                    flipCard();
//                    //  backTrack = false;
//
//                }
//            }, 200);

        } else if (TextUtils.isEmpty(cardValidity) || !CreditCardUtils.isValidDate(cardValidity)) {
            //  Toast.makeText(StripePaymentActivity.this, "Enter correct expiry date", Toast.LENGTH_SHORT).show();
            toastUtil.showToastShortTime("Any expiration date in the future is considered valid.", toastView);

            viewPager.setCurrentItem(1);
            // flipCardOnBackPressed();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //int pos = viewPager.getCurrentItem();
//                    //if (pos == total_item) {
//                    if (mShowingBack) {
//
//                    }
//                    mShowingBack = true;
//
//                    flipCard();
//                    //  backTrack = false;
//                    //} else if (pos == total_item - 1 && backTrack) {
//                    //  flipCard();
//                    // backTrack = false;
//                    //}
//                }
//            }, 200);

        } else if (TextUtils.isEmpty(cardCVV) || cardCVV.length() < 3) {
            toastUtil.showToastShortTime("Enter valid security number", toastView);
            //  Toast.makeText(StripePaymentActivity.this, "Enter valid security number", Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(2);

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //flipCard();
//                    // backTrack = true;
//                    if (mShowingBack) {
//
//                    }
//                    mShowingBack = false;
//
//
//                }
//            }, 200);

        } else {

            hideSoftKeyboard(StripePaymentActivity.this);
            toastUtil.showToastShortTime("Card details saved successfully.", toastView);

            sessionManager.createCreditCardSession(cardNumber, cardValidity, cardCVV);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();

                }
            }, 200);

        }
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        numberFragment = new CCNumberFragment();
        // nameFragment = new CCNameFragment();
        validityFragment = new CCValidityFragment();
        secureCodeFragment = new CCSecureCodeFragment();
        adapter.addFragment(numberFragment);
        // adapter.addFragment(nameFragment);
        adapter.addFragment(validityFragment);
        adapter.addFragment(secureCodeFragment);

        total_item = adapter.getCount() - 1;
        viewPager.setAdapter(adapter);

    }

    private void flipCard() {
        try {

            if (mShowingBack) {
                getFragmentManager().popBackStack();
                return;
            }
            // Flip to the back.
            //setCustomAnimations(int enter, int exit, int popEnter, int popExit)

            mShowingBack = true;

            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out)
                    .replace(R.id.fragment_container, cardBackFragment)
                    .addToBackStack(null)
                    .commit();
        } catch (Exception e) {

        }
    }

    private void flipCardOnBackPressed() {
        try {
            getFragmentManager().popBackStack();


            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out)
                    .replace(R.id.fragment_container, cardBackFragment)
                    .addToBackStack(null)
                    .commit();
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {
        int pos = viewPager.getCurrentItem();
        if (pos > 0) {
            viewPager.setCurrentItem(pos - 1);

        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);

            // hideSoftKeyboard(StripePaymentActivity.this);
//            try {
//                Thread.sleep(2000);
//                super.onBackPressed();
//                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

    }

    public void nextClick() {
        btnNext.performClick();
    }

}
