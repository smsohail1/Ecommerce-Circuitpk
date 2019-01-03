package com.xekera.Ecommerce.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.xekera.Ecommerce.R;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    private Context context;
    private static final int MIN_CN_LENGTH = 8;

    public Utils(Context context) {
        this.context = context;
    }

    public int getScreenWidth(Activity activity) {
        if (activity != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            return metrics.widthPixels;
        } else {
            return 0;
        }
    }

    public int getScreenHeight(Activity activity) {
        if (activity != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            return metrics.heightPixels;
        } else {
            return 0;
        }
    }

    public int getBatteryLevel() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int batLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        return batLevel;
    }

    public boolean isInternetAvailable() {
        /*if(context != null) {
            try {
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                return isConnected;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }else {
            return false;
        }*/
        return isInternetAvailableMoreAccurate();
    }


    private void openAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


    public boolean isInternetAvailableMoreAccurate() {
        if (context != null) {
            try {
                boolean haveConnectedWifi = false;
                boolean haveConnectedMobile = false;
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected()) {
                            if (!(ni.getExtraInfo().toLowerCase().contains("theta")))
                                haveConnectedWifi = true;
                        }
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            haveConnectedMobile = true;
                }
                return haveConnectedWifi || haveConnectedMobile;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public String getStringFromResourceId(int stringResourceId) {
        if (context != null) {
            return context.getResources().getString(stringResourceId);
        } else {
            return "";
        }
    }

    public boolean isTextNullOrEmpty(String text) {
        if (text == null) {
            return true;
        }
        if (text.isEmpty()) {
            return true;
        }
        if (text.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }


    public boolean isTextNullOrEmptyOrZero(String text) {
        if (text == null) {
            return true;
        }
        if (text.isEmpty()) {
            return true;
        }

        if (text.equalsIgnoreCase("0")) {
            return true;
        }
        if (text.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }


    public void hideSoftKeyboard(EditText editText) {
        if (context != null) {
            InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(
                    editText.getWindowToken(), 0);
        }
    }
    

    public void showSoftKeyboard(EditText editText) {
        if (context != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public String removeLeadingAndTrailingSpaces(String str) {
        return str.replaceAll("^\\s+|\\s+$", "");
    }

    public boolean isEditTextNullOrEmpty(EditText edt) {
        if (edt.getText() != null) {
            if (edt.getText().toString().trim().isEmpty()) {
                return true;
            } else {
                String trimmed = removeLeadingAndTrailingSpaces(edt.getText().toString());
                edt.setText(trimmed);
                edt.setSelection(edt.getText().length());
                return false;
            }
        } else {
            return true;
        }
    }


    public boolean minCharactersLimit(String text, int minCharacterLength) {
        if (text == null) {
            return true;
        }
        if (text.isEmpty()) {
            return true;
        }
        if (text.equalsIgnoreCase("")) {
            return true;
        }
        if (text.length() < minCharacterLength) {
            return true;
        }

        return false;
    }


    public boolean isEditTextEmpty(EditText editText) {

        return editText.getText().toString().trim().equals("");
//        if(editText.getText().toString().trim().equals("")){
//            return true;
//        }else{
//         return false;
//        }
    }


    public File createImageFileWith() throws IOException {
        final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String imageFileName = "JPEG_" + timestamp;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "pics");
        storageDir.mkdirs();
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    public String uriToBase64(Uri uri) {
        String encodedImage = null;
        try {
            final InputStream imageStream = context.getContentResolver().openInputStream(uri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            encodedImage = encodeImage(selectedImage);
        } catch (Exception e) {
            encodedImage = "";
        }

        return encodedImage;
    }

    public String uriToBase64(byte[] arr) {
        return Base64.encodeToString(arr, 0);
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public byte[] uriToByteArr(Uri uri) {
        byte[] byteArray;
        try {
            final InputStream imageStream = context.getContentResolver().openInputStream(uri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            byteArray = getBytesFromBitmap(selectedImage);
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            byteArray = null;
        }
        return byteArray;
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public String convertSecondsToTimeSpent(int totalSeconds) {
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;
        int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
        int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        int hours = totalMinutes / MINUTES_IN_AN_HOUR;
        if (hours == 0) {
            return minutes + " mins spent";
        } else if (hours == 1) {
            return hours + " hour " + minutes + " mins spent ";
        } else if (hours > 1) {
            return hours + " hours " + minutes + " mins spent ";
        }
        return "";
    }

    public String getCurrentDate(String format) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(c.getTime());
    }

    private String convertByteArrayToBase64Image(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);

    }

    public void showJDBCLogs(String message) {
        Log.d("JDBC_LOGS", message + new SimpleDateFormat(AppConstants.TIME_FORMAT_TWO).format(new Date()));
    }

    public String getCurrentDateTime(String format) {

        String dateTime = "";
//        SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_ONE, Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        dateTime = String.valueOf(dateFormat.format(new Date()));
        return dateTime;
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public long getGMTTimestampByJavaDateString(String dateStringOriginal) throws ParseException {
        DateFormat dateFormatGmt1 = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
        Date date = dateFormatGmt1.parse(dateStringOriginal);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTime(date);
        String dateString = String.valueOf(calendar.get(Calendar.YEAR)) + "-"
                + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-"
                + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + " "
                + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + ":"
                + String.valueOf(calendar.get(Calendar.MINUTE)) + ":"
                + String.valueOf(calendar.get(Calendar.SECOND));

        DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date1 = dateFormatGmt.parse(dateString);
        long unixTime = (long) date1.getTime() / 1000;
        return unixTime;
    }

    public void setupParent(final Activity activity, View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard2(activity);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupParent(activity, innerView);
            }
        }
    }

    public void hideSoftKeyboard2(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void enableWifiAndConnectToInternet() {
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!wifiManager.isWifiEnabled() && !mWifi.isConnected()) {
                wifiManager.setWifiEnabled(true);
            }
        }
    }

    public boolean isWifiInternetAvailable() {
        if (context != null) {
            try {
                boolean haveConnectedWifi = false;
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected()) {
                            if (!(ni.getExtraInfo().toLowerCase().contains("theta")))
                                haveConnectedWifi = true;
                        }
                }
                return haveConnectedWifi;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public void disableWifi() {
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
            }
        }
    }

    /*public List<DistanceObject> readFileAndGetLatLongs(SessionManager sessionManager) throws Exception {
        String line = null;
        List<DistanceObject> latLngs = new ArrayList<>();
        File root = new File(Environment.getExternalStorageDirectory(), AppConstants.DIRECTORY_PICKUP_TRAVEL_LOG);
        File gpxfile = null;
        // Checking if file exists in today's date
        File[] files = root.listFiles();
        boolean isFound = false;
        for(File file : files){
            if(file.getName().length() > 18) {
                String filenameWithoutTime = file.getName().split("_")[0];
                String courierCodeInFile = file.getName().split("_")[2].split("\\.")[0];
                String dateOnly = "PK" + getCurrentDate().split("_")[0];
                if (filenameWithoutTime.equals(dateOnly)) {
                    if (courierCodeInFile.equals(sessionManager.getCourierCode())) {
                        gpxfile = file;
                        isFound = true;
                        break;
                    }
                }
            }
        }
        if(!isFound){
            throw new FileNotFoundException();
        }
        if (!gpxfile.exists()) {
            throw new FileNotFoundException();
        }
        FileInputStream fileInputStream = new FileInputStream(gpxfile);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while ((line = bufferedReader.readLine()) != null) {
            String[] splits = line.split("\\|");
            latLngs.add(new DistanceObject(Double.parseDouble(splits[1]), Double.parseDouble(splits[2]), splits[3]));
        }
        fileInputStream.close();

        bufferedReader.close();

        return latLngs;
    }*/

    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_THREE);
        return df.format(c.getTime());
    }

    public void updateEditText(EditText edt, String text) {
        edt.setText(text);
        edt.setSelection(edt.getText().length());
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    private static final float maxHeight = 1280.0f;
    private static final float maxWidth = 1280.0f;

    public Bitmap getCompressedBitmapMoreOptimized(String imagePath) {
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
            bmp = BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        if (bmp != null) {
            bmp.recycle();
        }
        ExifInterface exif;
        try {
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            File oldImageFile = new File(imagePath);
            oldImageFile.delete();
            refreshGallery(oldImageFile.getAbsolutePath());
            return scaledBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String savePhoto(Bitmap bmp, String directoryName) {
        FileOutputStream out = null;
        try {
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            File imageFile = getOutputMediaFile(directoryName);
            out = new FileOutputStream(imageFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
            refreshGallery(imageFile.getAbsolutePath());
            out = null;
            return imageFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String savePhoto(Bitmap bmp, String directoryName, String consignmentNumber) {
        FileOutputStream out = null;
        try {
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            File imageFile = getOutputMediaFile(directoryName, consignmentNumber);
            out = new FileOutputStream(imageFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
            refreshGallery(imageFile.getAbsolutePath());
            out = null;
            return imageFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public File getOutputMediaFile(String directoryName) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                directoryName);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("BOSS_DK", "Oops! Failed create "
                        + directoryName + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public File getOutputMediaFile(String directoryName, String filename) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                directoryName);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("BOSS_DK", "Oops! Failed create "
                        + directoryName + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + filename + ".jpg");

        return mediaFile;
    }

    public void refreshGallery(String filePath) {
        //refresh gallery to show new saved image
        MediaScannerConnection
                .scanFile(
                        context,
                        new String[]{filePath},
                        null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(
                                    String path, Uri uri) {
                                Log.i("ExternalStorage", "Scanned "
                                        + path + ":");
                                Log.i("ExternalStorage", "-> uri="
                                        + uri);
                            }
                        });
    }

    public static String getTrimmedMobileNumber(String phoneNumber) {
        try {
            String newPhoneNumber = "";
            newPhoneNumber = phoneNumber.replace(" ", "");
            if (newPhoneNumber.startsWith("92")) {
                newPhoneNumber = "+" + newPhoneNumber;
            }
            if (newPhoneNumber.startsWith("2")) {
                newPhoneNumber = "0" + newPhoneNumber;
            }
            if (newPhoneNumber.startsWith("21")) {
                newPhoneNumber = "0" + newPhoneNumber;
            }
            if (newPhoneNumber.startsWith("3")) {
                newPhoneNumber = "0" + newPhoneNumber;
            }
            if (newPhoneNumber.startsWith("+")) {
                char[] phoneArray = newPhoneNumber.toCharArray();
                int startPosition = 0;
                for (int i = 0; i < phoneArray.length; i++) {
                    if (phoneArray[i] != '+') {
                        startPosition = i;
                        break;
                    }
                }
                newPhoneNumber = newPhoneNumber.substring(startPosition, newPhoneNumber.length());
            }
            if (newPhoneNumber.startsWith("0")) {
                char[] phoneArray = newPhoneNumber.toCharArray();
                if (phoneArray[1] == '0') {
                    int startPosition = 0;
                    for (int i = 1; i < phoneArray.length; i++) {
                        if (phoneArray[i] != '0') {
                            startPosition = i;
                            break;
                        }
                    }
                    newPhoneNumber = newPhoneNumber.substring(startPosition, newPhoneNumber.length());
                } else {
                    newPhoneNumber = newPhoneNumber.substring(1, newPhoneNumber.length());
                }
            }
            if (newPhoneNumber.startsWith("92")) {
                newPhoneNumber = "+" + newPhoneNumber;
            }
            if (newPhoneNumber.startsWith("+920")) {
                char[] phoneArray = newPhoneNumber.toCharArray();
                int startPosition = 0;
                for (int i = 4; i < phoneArray.length; i++) {
                    if (phoneArray[i] != '0') {
                        startPosition = i;
                        break;
                    }
                }
                newPhoneNumber = "+92" + newPhoneNumber.substring(startPosition, newPhoneNumber.length());
            }
            if (!newPhoneNumber.startsWith("+92")) {
                newPhoneNumber = "+92" + newPhoneNumber;
            }
            return newPhoneNumber;
        } catch (Exception ex) {
            ex.printStackTrace();
            return phoneNumber.replace(" ", "");
        }
    }

    public String byteArrayToString(byte[] byteArrayImage) {
        if (byteArrayImage == null) {
            return "";
        }
        return Base64.encodeToString(byteArrayImage, Base64.NO_WRAP);
    }


}



















