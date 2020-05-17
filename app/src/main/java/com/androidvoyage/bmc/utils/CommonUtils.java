package com.androidvoyage.bmc.utils;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;

import com.androidvoyage.bmc.BCApplication;
import com.androidvoyage.bmc.common.CommonConstants;
import com.androidvoyage.bmc.interfaces.Callback;
import com.androidvoyage.bmc.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.text.TextUtils.isEmpty;
import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class CommonUtils {
    public static final String TAG = CommonUtils.class.getSimpleName();

    static final long DAY = 24 * 60 * 60 * 1000;
    public static final int STORAGE_PERMISSION_CODE = 1010;

    public static int getDisplayHeightInPixels(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getDisplayWidthInPixels(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static boolean inLastDay(Date aDate) {
        return aDate.getTime() > System.currentTimeMillis() - DAY;
    }

    /**
     * Gets date object from string in the format YYYY-MM-DDTHH:MM:SS.sssZ // 2018-04-22T05:27:05.523Z
     * @param dateString
     * @return date object if format was correct, returns null if format is not as specified here.
     */


    /**
     * This function is depracated in codebase. Please dont use it. It would be removed soon.
     * TODO: Remove this function and replace it.
     */
    /*@Deprecated
    public static void getApiResponse(final Context context, String endpoints, int Request, JSONObject jsonObject, final VolleyCallback volleyCallback) {
        String url = Endpoints.DOCSMART_DOMAIN + endpoints;
        Log.d(TAG, "get : " + url);
        Log.d(TAG, "get : " + jsonObject);
        final JsonObjectRequest req = new JsonObjectRequest(Request, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            volleyCallback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e(TAG, "Error in volley  " + error);
                    NetworkResponse networkResponse = error.networkResponse;
                    byte[] responseBytes = networkResponse.data;
                    String responseString = new String(responseBytes);
                    Log.e(TAG, "response error volley " + responseString);

                } catch (Exception exp) {

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String authHeader = "JWT " + Preferences.getToken();
                Log.e(TAG, "Sending Auth HEader :=  " + authHeader);
                params.put("Authorization", authHeader);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(req);


    }*/

    //This method hides keyboard from the screen
    public static void hideSoftKeyboard(View view, Context context) {


        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


    //This method checks if user is connected to internet
    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    //This method shows snackbar
    public static void snackbar(Context context, ViewGroup viewGroup, String message) {

        Snackbar snackbar = Snackbar.make(viewGroup, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
    }

    /*public static void snackbar(Context context, ViewGroup viewGroup, String message, ErrorCode errorCode) {

        Snackbar snackbar = Snackbar.make(viewGroup, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
    }*/

    //This method shows Toast
    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //This method shows snackbar
    public static void showSnackBar(View view, Context context, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        snackbar.show();
    }

    /*public static void showSnackBareErrorred(View view, Context context, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.Red));
        snackbar.show();
    }

    public static void showSnackBarWithAction(View view, String message, String action, final Callback callback) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getInstance(), R.color.colorPrimary));
        TextView tv = snackbar.getView().findViewById(R.id.snackbar_text); //snackbar_text
        tv.setTextColor(ContextCompat.getColor(App.getInstance(), R.color.white));
        snackbar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.invoke("OK");
            }
        });
        snackbar.show();
    }

    public static void alertBoxComingSoon(Context context) {

        //This method shows alert box saying Coming soon
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.layout_alert_box, null);
        dialogBuilder.setView(dialogView);
        TextView tv_coming_soon = dialogView.findViewById(R.id.tv_coming_soon);
        dialogBuilder.setIcon(R.drawable.docsmart_logo_svg);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

    }*/

    public static void alertBoxInternetConnection(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("No Internet Connection");
        alertDialogBuilder.setMessage("You are offline please check your internet connection");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /*public static void successDisaster(LinearLayout disasterpage, final Context context, String successfully_disaster_) {


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.disaster_success, null);
        dialogBuilder.setView(dialogView);
        TextView tv_coming_soon = dialogView.findViewById(R.id.tv_coming_soon);
        Button btn_positive = dialogView.findViewById(R.id.button_ok);
        final AlertDialog b = dialogBuilder.create();

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
                Intent i = new Intent(context, MainPageActivity.class);
                context.startActivity(i);
            }
        });

        b.show();

    }

    public static void showSuccessAlert(final Context context, String success_msg, final Callback<String> callback) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.layout_create_event, null);
        dialogBuilder.setView(dialogView);

        TextView tv_alert_sent = dialogView.findViewById(R.id.tv_alert_sent);
        tv_alert_sent.setText(success_msg);

        Button btn_positive = dialogView.findViewById(R.id.button_ok);
        final AlertDialog b = dialogBuilder.create();

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
                callback.invoke("done");
            }
        });
        b.show();
    }

    public static void SuccessAlertDialog(final Context context, String success_msg) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.layout_create_event, null);
        dialogBuilder.setView(dialogView);

        TextView tv_alert_sent = dialogView.findViewById(R.id.tv_alert_sent);
        tv_alert_sent.setText(success_msg);

        Button btn_positive = dialogView.findViewById(R.id.button_ok);
        final AlertDialog b = dialogBuilder.create();

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();

            }
        });
        b.show();
    }*/

    /*public static void cancelButton(final Context context) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.button_cancel_button, null);
        dialogBuilder.setView(dialogView);


        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }*/

    public static void requestLocationPermission(Activity activity, Context context, int PERMISSION_REQUEST_CODE) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(context, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

/*
    public static void requestLocationPermission(Activity activity, Context context, String permissionName, int PERMISSION_REQUEST_CODE, Callback<Boolean> retry) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            showPermissionDetails(context, permissionName, false, retry);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }
*/

    public static boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

   /* public static boolean checkPermissionwithcoarse(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }*/

   /*public static void showPermissionDetails(Context context, String permissionName, boolean deniedPermanently, Callback<Boolean> retry){
       String message = permissionName +" permission(s) are required to perform tasks and proceed.";
       if(deniedPermanently) message = permissionName +" permission(s) are required to perform tasks and proceed. \nPlease goto Settings > Applications > " +
              BCApplication.getAppContext().getResources().getString(R.string.app_name) + " > App info > permissions > and turn on the permission.";
        Dialogs.createAlertDialog(context, "Please grant this permission", message,
                (dialog, which) -> { if(retry != null) retry.invoke(which == Dialog.BUTTON_POSITIVE); }).show();
   }*/

    public static void setStatusBarColor(Context context, int color, boolean isLight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) context).getWindow().setStatusBarColor(ContextCompat.getColor(context, color));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isLight) {
                ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    public static void getNamefromDialog(Context context, String title, final EditText textView) {

        final Dialog mDialog = new Dialog(context);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(R.layout.dialog_edittext);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (!((Activity) context).isFinishing()) {
            mDialog.show();
        }

        TextView tvTitleDialogEt = mDialog.findViewById(R.id.tv_title_dialog_et);
        final EditText etDialogGetString = mDialog.findViewById(R.id.et_dialog_getString);
        TextView tvCancelDialogEt = mDialog.findViewById(R.id.tv_cancel_dialog_et);
        TextView tvOkayDialogEt = mDialog.findViewById(R.id.tv_okay_dialog_et);

        tvTitleDialogEt.setText(title);

        etDialogGetString.callOnClick();

        tvOkayDialogEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(etDialogGetString.getText().toString().trim());
                mDialog.dismiss();
            }
        });
        tvCancelDialogEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

    }

    public static Uri getOutputMediaFileUri(int type, String imageDirectoryName) {
        return Uri.fromFile(getOutputMediaFile(type, imageDirectoryName));
    }

    private static File getOutputMediaFile(int type, String imageDirectoryName) {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                imageDirectoryName);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(imageDirectoryName, "Oops! Failed create "
                        + imageDirectoryName + " directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static void openAppSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void openAppSettingsForPermission(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void showHidePassword(EditText editText, View v) {
        v.setSelected(!v.isSelected());
        if (editText != null && editText.getText().toString().length() > 0) {
            if (v.isSelected()) {
                editText.setTransformationMethod(null);
                editText.setSelection(editText.length());
            } else {
                editText.setTransformationMethod(new PasswordTransformationMethod());
                editText.setSelection(editText.length());
            }
        }
    }

    /*public static void showDialogTwoButton(Context context, String title, String message, String strNegative, String strPositive, final DialogClickListener dialogClickListener) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_alert_sure, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }

        TextView tvTitle = dialogView.findViewById(R.id.tv_title);
        TextView tvDialogMessage = dialogView.findViewById(R.id.tv_dialog_message);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tvPositive = dialogView.findViewById(R.id.tv_positive);

        tvTitle.setText(title);
        tvDialogMessage.setText(message);
        tvCancel.setText(strNegative);
        tvPositive.setText(strPositive);


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onButtonClick(dialog, 0);
                dialog.dismiss();
            }
        });

        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onButtonClick(dialog, 1);
                dialog.dismiss();
            }
        });

    }*/

    /*public static void showDialogTwoButton(Context context, String title, String message, String strPositive, final DialogClickListener dialogClickListener) {
        showDialogTwoButton(context, title, message, "Cancel", strPositive, dialogClickListener);
    }*/

    public static boolean checkNull(String text) {
        return text != null && text.trim().length() > 0;
    }


    public static Double roundOff(Double value, int places) {

        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;

    }

    public static float dp2px(int dip, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dip * scale + 0.5f;
    }

    /*public static void showDialogOneButton(Context context, String title, String msg, String btnCaption, DialogInterface.OnDismissListener onDismissListener) {
        final Dialog dialog = new Dialog(context, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_one_button);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        TextView tvDismiss = (TextView) dialog.findViewById(R.id.tv_dismiss);

        tvTitle.setText(title);
        tvMsg.setText(msg);
        tvDismiss.setText(btnCaption);

        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDismissListener != null) {
                    onDismissListener.onDismiss(dialog);
                }
                dialog.dismiss();
            }
        });

    }*/

    public static void focusOnView(ScrollView svCenter, final View view) {
        if (view != null) {
            svCenter.post(new Runnable() {
                @Override
                public void run() {
                    view.getParent().requestChildFocus(view, view);
                }
            });
        }
    }

    public static boolean focusOnView(final View view) {
        if (view != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.getParent().requestChildFocus(view, view);
                }
            });
        }
        return view == null;
    }

    public static String getSingleImagePath(Context context, Intent data) {
        if (data != null && data.getClipData() != null) {
            return FileUtils.getPath(context, data.getClipData().getItemAt(0).getUri());
        }
        if (data != null && data.getData() != null) {
            return FileUtils.getPath(context, data.getData());
        }
        return FileUtils.getPath(context, CommonUtils.getCaptureImageOutputUri(BCApplication.getAppContext()));
    }

    /*public static void showFilePreviewDialog(Context context, String url){
       if(url.contains(".pdf")){
           showDocDialog(context,url);
       }else {
           showImageDialog(context,url);
       }
    }*/


   /* public static void showDocDialog(Context context, String url) {
        if (url == null || url.length() == 0) {
            Toast.makeText(context, "url is malformed.", Toast.LENGTH_SHORT).show();
            return;
        }
        *//*new DocumentDialog(context, R.style.DialogSlideAnim,url).show();*//*
        context.startActivity(new Intent(context, FilePreviewActivity.class).putExtra("PDF_URL", url));
    }

    public static void showImageDialog(Context context, String url) {
        if (url == null || url.length() == 0) {
            Toast.makeText(context, "url is malformed.", Toast.LENGTH_SHORT).show();
            return;
        }
        final Dialog dialog = new Dialog(context, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_image_preview);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);

        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }

        ProgressBar pbLoader = dialog.findViewById(R.id.pb_image_dialog);

        dialog.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        pbLoader.setVisibility(View.VISIBLE);
        ImageLoader.loadImageFromUrl(url, (ImageView) dialog.findViewById(R.id.iv_preview), R.drawable.profilephotos, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {


                pbLoader.setVisibility(View.GONE);
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                pbLoader.setVisibility(View.GONE);
                return false;
            }
        });
    }*/

    /*public static void showImagesDialog(Context context, ArrayList<String> center_images) {
        if (center_images == null || center_images.size() == 0) {
            Toast.makeText(context, "images not validationAddData.", Toast.LENGTH_SHORT).show();
            return;
        }
        final Dialog dialog = new Dialog(context, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_image_vp_preview);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }

        dialog.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        RecyclerView rcvImages = dialog.findViewById(R.id.rcv_images);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL);
        rcvImages.setLayoutManager(staggeredGridLayoutManager);
        rcvImages.setAdapter(new CenterImagesAdapter(context, center_images));
    }*/

    @SuppressLint("SimpleDateFormat")
    public static String getTimeAMPMFormat(String s) {

        try {
            return new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("HH:mm").parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTime24HourFormat(String s) {

        try {
            return new SimpleDateFormat("HH:mm").format(new SimpleDateFormat("hh:mm a").parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void setTextWatcherForEdittext(final EditText edittext, final TextView tvError) {
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edittext.getText().toString().trim().length() > 0) {
                    tvError.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    //This method sets scrolling inside/nested scroll
    @SuppressLint("ClickableViewAccessibility")
    public static void setNestedScrollView(NestedScrollView view, Context context) {
        view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }

    public static boolean validateStringForEmpty(String StringToValidate) {

        return StringToValidate.equals("");
    }

    public static void hideKeyboard(@NonNull Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyboard(Context context, EditText et) {
        if (et != null) {
            et.requestFocus();
            InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        } else {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }
    }

    public static String getAddressInString(Context context, double lat, double lng) {
        // This method returns address in string format

        String CurrentLocation = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() >= 1) {

                Address obj = addresses.get(0);
                String add = obj.getAddressLine(0);
                add = add + "\n" + obj.getCountryName();
                add = add + "\n" + obj.getCountryCode();
                add = add + "\n" + obj.getAdminArea();
                add = add + "\n" + obj.getPostalCode();
                add = add + "\n" + obj.getSubAdminArea();
                add = add + "\n" + obj.getLocality();
                add = add + "\n" + obj.getSubThoroughfare();
                CurrentLocation = obj.getAddressLine(0);
                String NearestRailwaystation = obj.getLocality();

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return CurrentLocation;
    }

    public static String getAreaNameForLocation(Context context, double lat, double lng) {
        // This method returns address in string format

        String CurrentAreaLocation = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() >= 1) {

                Address obj = addresses.get(0);
                CurrentAreaLocation = obj.getSubAdminArea();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return CurrentAreaLocation;
    }

    /*public static HashMap<String, Double> getLatitudeLongitude(Context context) {
        Double Latitude = 0.0;
        Double Longitude = 0.0;

        HashMap<String, Double> HashMapLatLong = new HashMap<>();
        GpsTrackerService gps = new GpsTrackerService(context);

        if (gps.canGetLocation()) {

            Latitude = gps.getLatitude();
            Longitude = gps.getLongitude();
        } else {

            Toast.makeText(context, "Not Able to fetch your location", Toast.LENGTH_SHORT).show();

        }

        HashMapLatLong.put("Latitude", Latitude);
        HashMapLatLong.put("Longitude", Longitude);
        return HashMapLatLong;

    }*/

    public static boolean isInternetWorking(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }


    public static String getRealPathFromURI(Uri contentURI, Activity context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = context.managedQuery(contentURI, projection, null,
                null, null);
        if (cursor == null)
            return null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            // cursor.close();
            return s;
        }
        cursor.close();
        return null;
    }


    public static void fetchDateTime(final Context context, final Callback<String> callback) {
        int mYear, mMonth, mDay, mHours, mMins;
        Calendar calender_sugar;
        calender_sugar = Calendar.getInstance();

        mYear = calender_sugar.get(Calendar.YEAR);
        mMonth = calender_sugar.get(MONTH);
        mDay = calender_sugar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                int abc = 1;
                String pp = "0" + abc;
                monthOfYear = monthOfYear + 1;
                dayOfMonth = dayOfMonth;
                String m = String.valueOf(monthOfYear);
                String d = String.valueOf(dayOfMonth);

                if (monthOfYear < 10) {
                    m = "0" + monthOfYear;
                }
                if (dayOfMonth < 10) {
                    d = "0" + dayOfMonth;
                } else {
                    d = dayOfMonth + "";
                }
                String date = d + "/" + m + "/" + year;
                timepicker(context, date, callback);

            }
        }, mYear, mMonth, mDay);
        dpd.show();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        dpd.getDatePicker().setMinDate(calendar.getTime().getTime());

    }

    public static void fetchDateTime48(final Context context, final Callback<String> callback) {
        int mYear, mMonth, mDay, mHours, mMins;
        Calendar calender_sugar;
        calender_sugar = Calendar.getInstance();

        mYear = calender_sugar.get(Calendar.YEAR);
        mMonth = calender_sugar.get(MONTH);
        mDay = calender_sugar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                int abc = 1;
                String pp = "0" + abc;
                monthOfYear = monthOfYear + 1;
                dayOfMonth = dayOfMonth;
                String m = String.valueOf(monthOfYear);
                String d = String.valueOf(dayOfMonth);

                if (monthOfYear < 10) {
                    m = "0" + monthOfYear;
                }
                if (dayOfMonth < 10) {
                    d = "0" + dayOfMonth;
                } else {
                    d = dayOfMonth + "";
                }
                String date = d + "/" + m + "/" + year;
                timepicker(context, date, callback);

            }
        }, mYear, mMonth, mDay);
        dpd.show();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - (1000 * 60 * 60 * 48)/* minus 48 hours*/);
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    public static void fetchDateTime(final Long minDate, final long maxDate, final Context context, final Callback<String> callback) {
        int mYear, mMonth, mDay, mHours, mMins;
        Calendar calender_sugar;
        calender_sugar = Calendar.getInstance();

        mYear = calender_sugar.get(Calendar.YEAR);
        mMonth = calender_sugar.get(MONTH);
        mDay = calender_sugar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                int abc = 1;
                String pp = "0" + abc;
                monthOfYear = monthOfYear + 1;
                dayOfMonth = dayOfMonth;
                String m = String.valueOf(monthOfYear);
                String d = String.valueOf(dayOfMonth);

                if (monthOfYear < 10) {
                    m = "0" + monthOfYear;
                }
                if (dayOfMonth < 10) {
                    d = "0" + dayOfMonth;
                } else {
                    d = dayOfMonth + "";
                }
                String date = d + "/" + m + "/" + year;
                CommonUtils.timepicker(context, date, callback);

            }
        }, mYear, mMonth, mDay);
        dpd.show();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);

        if (minDate != null) {
            dpd.getDatePicker().setMinDate(minDate);
        }
        dpd.getDatePicker().setMaxDate(maxDate);

    }


    private static void timepicker(Context context, final String date, final Callback<String> callback) {
        final int mHours, mMins;
        Calendar calender_sugar;
        calender_sugar = Calendar.getInstance();


        mHours = calender_sugar.get(Calendar.HOUR_OF_DAY);
        mMins = calender_sugar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String time = hourOfDay + ":" + minute + ":" + "00";
                String dateTime11;
                if (hourOfDay < 10) {
                    time = "0" + hourOfDay + ":" + minute;
                }
                if (minute < 10) {
                    time = hourOfDay + ":" + "0" + minute;
                }

                if (hourOfDay < 10 && minute < 10) {
                    time = "0" + hourOfDay + ":" + "0" + minute;
                }
                dateTime11 = DateTimeUtils.convertDateTimePickerToReadableDateTimeFormat(date + " " + time);
                callback.invoke(dateTime11);
            }
        }, mHours, mMins, true);
        timePickerDialog.show();

    }


    public static void fetchTime(final Context context, final Callback<String> callback, boolean amPm) {
        final Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        callback.invoke(getTimeAMPMFormat(hourOfDay + ":" + minute));
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), amPm);
        timePickerDialog.show();
    }

    public static void fetchDate(final Context context, final Callback<String> callback) {
        int mYear, mMonth, mDay;
        Calendar calender_sugar;
        calender_sugar = Calendar.getInstance();

        mYear = calender_sugar.get(Calendar.YEAR);
        mMonth = calender_sugar.get(MONTH);
        mDay = calender_sugar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                int abc = 1;
                String pp = "0" + abc;
                monthOfYear = monthOfYear + 1;
                dayOfMonth = dayOfMonth;
                String m = String.valueOf(monthOfYear);
                String d = String.valueOf(dayOfMonth);

                if (monthOfYear < 10) {
                    m = "0" + monthOfYear;
                }
                if (dayOfMonth < 10) {
                    d = "0" + dayOfMonth;
                } else {
                    d = dayOfMonth + "";
                }
                String date = d + "/" + m + "/" + year;
                date = DateTimeUtils.convertDatePickerToReadableDateFormat(date);
                callback.invoke(date);

            }
        }, mYear, mMonth, mDay);
        dpd.show();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);

        dpd.getDatePicker().setMaxDate(calendar.getTime().getTime());
    }

    public static void fetchDate(final Context context, final Callback<String> callback, int startYear, int startMonth, int startDate) {
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                monthOfYear = monthOfYear + 1;
                String m = String.valueOf(monthOfYear);
                String d = String.valueOf(dayOfMonth);

                if (monthOfYear < 10) {
                    m = "0" + monthOfYear;
                }
                if (dayOfMonth < 10) {
                    d = "0" + dayOfMonth;
                }
                String date = d + "/" + m + "/" + year;
                date = DateTimeUtils.convertDatePickerToReadableDateFormat(date);
                callback.invoke(date);

            }
        }, startYear, startMonth, startDate);
        dpd.show();
    }


    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null) {
            Log.d(TAG, "no internet connection");
            return false;
        } else {
            if (info.isConnected()) {
                Log.d(TAG, " internet connection available...");
                return true;
            } else {
                Log.d(TAG, " internet connection");
                return true;
            }

        }
    }

    public static void fromHtml(String text, TextView textView) {
        if (isEmpty(text)) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(text));
        }
    }

    public static SpannableString Spanableshow(CharSequence text, int startDuration, int endDuration, @ColorInt int textColor) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(textColor);
        spannableString.setSpan(colorSpan, startDuration, endDuration, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidName(String target) {
        if (target == null) {
            return false;
        } else {
            return target.matches("[A-z\\']*");
        }
    }

    public static boolean isValidPhone(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.PHONE.matcher(target).matches();
        }
    }


    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '&' || chars[i] == '(' || chars[i] == '/') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static String capitalizeEachWordInSentence(String sentence) {
        if (sentence == null || sentence.length() == 0) {
            return "";
        }
        String[] strArray = sentence.split(" ");
        String newSentence = "";
        StringBuilder buffer = new StringBuilder();
        for (String s : strArray) {
            buffer.append(newSentence);
            buffer.append(" ");
            buffer.append(capitalize(s));
        }
        return buffer.toString().trim();
    }

    public static int getDiffYears(Date first) {
        Calendar a = Calendar.getInstance(Locale.getDefault());
        a.setTime(first);

        Calendar b = Calendar.getInstance(Locale.getDefault());
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
//            diff--;
        }
        return diff;
    }


    public static void cancelProgressDialog(ProgressDialog progress) {
        if (progress.isShowing() && progress != null)
            progress.cancel();

    }


    public static Address fetchaddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(context, "No address found for the location", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            return addresses.get(0);
        }
    }

    /*public static void spnState(Context context, Spinner spinner, String filename) {
        String json_string;
        JSONObject jsonObj;
        JSONArray jsonArray;
        json_string = loadJSONFromAsset(filename);
        ArrayList<String> messages = new ArrayList<String>();
        {
            try {
                jsonObj = new JSONObject(json_string);
                jsonArray = jsonObj.getJSONArray("indiastate");
                String formule, url;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    formule = jObj.getString("name");
                    messages.add(formule);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        CommonUtils.positionspinner(context, spinner, messages);
    }*/

    /*public static ArrayList<String> getStates(String filename){
        String json_string;
        JSONObject jsonObj;
        JSONArray jsonArray;
        json_string = loadJSONFromAsset(filename);
        ArrayList<String> messages = new ArrayList<String>();
        try {
            jsonObj = new JSONObject(json_string);
            jsonArray = jsonObj.getJSONArray("indiastate");
            String formule, url;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObj = jsonArray.getJSONObject(i);
                formule = jObj.getString("name");
                messages.add(formule);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messages;
    }*/


    public static void CommontextWatcher(final Context context, final EditText editText, final String message) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (editText.getText().length() > 0) {
                    editText.setError(null);
                } else {

                    editText.setError(message);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void downloadFile(String url, Context context) {
        int startIndex = url.lastIndexOf('.');
        int endIndex = url.length();

        String fileName = "GovernmentLiscense" + url.substring(startIndex, endIndex);
        String title = "Downloading " + fileName;
        Uri uri = Uri.parse(url);

// Create request for android download manager
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);

// set title and description
        request.setTitle(title);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//set the local destination for download file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setMimeType("*/*");
        downloadManager.enqueue(request);

    }


    public static File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                null,         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    public static String getRealPathFromURIDB(Uri contentUri) {
        Cursor cursor = null;
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor = BCApplication.getAppContext().getContentResolver().query(contentUri, null, null, null, null);
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String realPath = cursor.getString(index);
            cursor.close();
            return realPath;
        }
    }

    /*public static void fetchDate(final Long minDate, final Long maxDate, int year, int month, int date, final Context context, final Callback<String> callback) {
        int mYear, mMonth, mDay, mHours, mMins;
        Calendar calender_sugar;
        calender_sugar = Calendar.getInstance();

        mYear = calender_sugar.get(Calendar.YEAR);
        mMonth = calender_sugar.get(MONTH);
        mDay = calender_sugar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                int abc = 1;
                String pp = "0" + abc;
                monthOfYear = monthOfYear + 1;
                dayOfMonth = dayOfMonth;
                String m = String.valueOf(monthOfYear);
                String d = String.valueOf(dayOfMonth);

                if (monthOfYear < 10) {
                    m = "0" + monthOfYear;
                }
                if (dayOfMonth < 10) {
                    d = "0" + dayOfMonth;
                } else {
                    d = dayOfMonth + "";
                }
                String date = year + "-" + m + "-" + d;
                callback.invoke(date);
            }
        }, mYear, mMonth, mDay);
        dpd.updateDate(year, month, date);
        dpd.show();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);

        if (minDate != null) {
            dpd.getDatePicker().setMinDate(minDate);
        }
        if (maxDate != null)
            dpd.getDatePicker().setMaxDate(maxDate);

    }*/


    public static void callIntent(Context context, String mobile) {
        if (mobile != null && mobile.length() > 0) {
            String uri = "tel:" + mobile.trim();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            context.startActivity(intent);
            return;
        }
        Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
    }

    public static void messageIntent(Context context, String mobile, String msg) {
        if (mobile != null && mobile.length() > 0) {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:" + mobile.trim()));
            if (msg != null && msg.trim().length() > 0) {
                sendIntent.putExtra("sms_body", msg.trim());
            }
            context.startActivity(sendIntent);
            return;
        }
        Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
    }


    /**
     * Create a chooser intent to select the source to get image from.<br>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br>
     * All possible sources are added to the intent chooser.
     *
     * @param context          used to access Android APIs, like content resolve, it is your
     *                         activity/fragment/widget.
     * @param title            the title to use for the chooser UI
     * @param includeDocuments if to include KitKat documents activity containing all sources
     * @param includeCamera    if to include camera intents
     */
    public static Intent getPickImageChooserIntent(
            @NonNull Context context,
            CharSequence title,
            boolean includeDocuments,
            boolean includeCamera,
            boolean multiSelect) {

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();

        // collect all camera intents if Camera permission is available
        if (!isExplicitCameraPermissionRequired(context) && includeCamera) {
            allIntents.addAll(getCameraIntents(context, packageManager));
        }

        List<Intent> galleryIntents = getGalleryIntents(packageManager, Intent.ACTION_GET_CONTENT, includeDocuments, multiSelect);
        if (galleryIntents.size() == 0) {
            // if no intents found for get-content try pick intent action (Huawei P9).
            galleryIntents = getGalleryIntents(packageManager, Intent.ACTION_PICK, includeDocuments, multiSelect);
        }
        allIntents.addAll(galleryIntents);

        Intent target;
        if (allIntents.isEmpty()) {
            target = new Intent();
        } else {
            target = allIntents.get(allIntents.size() - 1);
            allIntents.remove(allIntents.size() - 1);
        }

        // Create a chooser from the main  intent
        Intent chooserIntent = Intent.createChooser(target, title);

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    /**
     * Get all Camera intents for capturing image using device camera apps.
     */
    public static List<Intent> getCameraIntents(
            @NonNull Context context, @NonNull PackageManager packageManager) {

        List<Intent> allIntents = new ArrayList<>();

        // Determine Uri of camera image to  save.
        Uri outputFileUri = getCaptureImageOutputUri(context);

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        return allIntents;
    }

    /**
     * Get URI to image received from capture by camera.
     *
     * @param context used to access Android APIs, like content resolve, it is your
     *                activity/fragment/widget.
     */
    public static Uri getCaptureImageOutputUri(@NonNull Context context) {
        Uri outputFileUri = null;
        File getImage = context.getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = FileProvider.getUriForFile(context, CommonConstants.AUTHORITY, new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    /**
     * Check if explicetly requesting camera permission is required.<br>
     * It is required in Android Marshmellow and above if "CAMERA" permission is requested in the
     * manifest.<br>
     * See <a
     * href="http://stackoverflow.com/questions/32789027/android-m-camera-intent-permission-bug">StackOverflow
     * question</a>.
     */
    public static boolean isExplicitCameraPermissionRequired(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && hasPermissionInManifest(context, "android.permission.CAMERA")
                && context.checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Check if the app requests a specific permission in the manifest.
     *
     * @param permissionName the permission to check
     * @return true - the permission in requested in manifest, false - not.
     */
    public static boolean hasPermissionInManifest(
            @NonNull Context context, @NonNull String permissionName) {
        String packageName = context.getPackageName();
        try {
            PackageInfo packageInfo =
                    context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            final String[] declaredPermisisons = packageInfo.requestedPermissions;
            if (declaredPermisisons != null && declaredPermisisons.length > 0) {
                for (String p : declaredPermisisons) {
                    if (p.equalsIgnoreCase(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    /**
     * Get all Gallery intents for getting image from one of the apps of the device that handle
     * images.
     */
    public static List<Intent> getGalleryIntents(@NonNull PackageManager packageManager, String action, boolean includeDocuments, boolean multiSelect) {
        List<Intent> intents = new ArrayList<>();
        Intent galleryIntent = new Intent(action);
        /*if (action.equals(Intent.ACTION_GET_CONTENT)) {
            galleryIntent.setType("image/*");
            *//*galleryIntent.setType("application/pdf");*//*
        } else {
            galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        }*/

        String[] mimeTypes = {"image/*", "application/pdf"};
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            galleryIntent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            galleryIntent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }


        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (multiSelect)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intents.add(intent);
        }

        // remove documents intent
        if (!includeDocuments) {
            for (Intent intent : intents) {
                if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                    intents.remove(intent);
                    break;
                }
            }
        }
        return intents;
    }

    //Requesting permission
    public static boolean requestStoragePermission(Context context, int requestCode) {
        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
            openAppSettingsForPermission((Activity) context,requestCode);
            return false;
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, requestCode);
        return false;
    }


    private static List<Intent> getOtherIntentList(Context context) {
        List<Intent> allIntents = new ArrayList<>();

        Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
        intentPDF.setType("application/pdf");
        intentPDF.addCategory(Intent.CATEGORY_OPENABLE);

        Intent intentTxt = new Intent(Intent.ACTION_GET_CONTENT);
        intentTxt.setType("text/plain");
        intentTxt.addCategory(Intent.CATEGORY_OPENABLE);

        Intent intentXls = new Intent(Intent.ACTION_GET_CONTENT);
        intentXls.setType("application/x-excel");
        intentXls.addCategory(Intent.CATEGORY_OPENABLE);

        PackageManager packageManager = context.getPackageManager();

        List activitiesPDF = packageManager.queryIntentActivities(intentPDF,
                0);
        boolean isIntentSafePDF = activitiesPDF.size() > 0;

        List activitiesTxt = packageManager.queryIntentActivities(intentTxt,
                0);
        boolean isIntentSafeTxt = activitiesTxt.size() > 0;

        List activitiesXls = packageManager.queryIntentActivities(intentXls,
                0);
        boolean isIntentSafeXls = activitiesXls.size() > 0;

        if (isIntentSafePDF)
            allIntents.add(getContentResolvedIntent(context, intentPDF, true));

        if (isIntentSafeTxt)
            allIntents.add(getContentResolvedIntent(context, intentTxt, true));
        if (isIntentSafeXls)
            allIntents.add(getContentResolvedIntent(context, intentXls, true));


        return allIntents;


    }


    private static Intent getContentResolvedIntent(Context context, Intent inputIntent, boolean multiSelect) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = null;
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(inputIntent, 0);
        for (ResolveInfo res : listGallery) {
            intent = new Intent(inputIntent);
            if (multiSelect)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }

        return intent;
    }


    public static Intent getPickOtherChooserIntent(@NonNull Context context, CharSequence title) {

        List<Intent> allIntents = new ArrayList<>();

        allIntents.addAll(getOtherIntentList(context));

        Intent target;
        if (allIntents.isEmpty()) {
            target = new Intent();
        } else {
            target = allIntents.get(allIntents.size() - 1);
            allIntents.remove(allIntents.size() - 1);
        }

        // Create a chooser from the main  intent
        Intent chooserIntent = Intent.createChooser(target, title);

        // Add all other intents
        chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));


        return chooserIntent;
    }

    /*public static Address currentLocation(Context context) {
        LocationTrack gpstracker = new LocationTrack(context);
        List<Address> addressList = new ArrayList<>();
        Address address = null;

        double longitude = gpstracker.getLongitude();
        double latitude = gpstracker.getLatitude();

        try {
            Geocoder geocoder;
            geocoder = new Geocoder(context, Locale.getDefault());

            addressList = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addressList != null && addressList.size() > 0) {
            address = addressList.get(0);
            Preferences.saveCurrentAddress(address);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Please select location");

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return address;
    }*/

    /*public static void currentLocationWithFused(Activity context, final Callback<Address> callback) {
        if (CommonUtils.checkPermission(context)) {
            FusedLocationProviderClient fusedLocationClient;
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            final Address[] tempaddress = {null};

            LocationTrack gpstracker = new LocationTrack(context);
            if (!gpstracker.canGetLocation()) {
                gpstracker.showSettingsAlert();
            } else {
                fusedLocationClient.getLastLocation().addOnSuccessListener(context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            Geocoder geocoder;
                            geocoder = new Geocoder(context, Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                tempaddress[0] = addresses.get(0);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.invoke(tempaddress[0]);
                        }
                    }


                })
                        .addOnFailureListener(context, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        }
//        TODO : Below lines are commented to check if they are really required. Developer should handle this requesting permission in activity
//        else {
//            CommonUtils.requestLocationPermission(context, context, 1212);
//        }
    }*/

    /*public static boolean checkblankETForTextShow(Context context, EditText editText, int str_error_message,TextView error_editText) {

        if (editText.getText().toString().matches("")) {
            error_editText.setVisibility(View.VISIBLE);
            error_editText.setText(context.getString(str_error_message));
            return false;
        } else {
            return true;
        }
    }*/

    /*public static void openMapsWithDirection(Context context, double latitude, double longitude){
        String uri = "http://maps.google.com/maps?f=d&hl=en" +
//                "&saddr="+latitude1+","+longitude1+
                "&daddr="+latitude+","+longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }*/

    public static void soonToast(Context context){
        Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show();
    }
}


