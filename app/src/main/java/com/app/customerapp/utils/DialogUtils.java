package com.app.customerapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.customerapp.R;
import com.app.customerapp.activity.AddAddressActivity;
import com.app.customerapp.activity.CustomerAppBaseActivity;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.listner.IDialogListener;


/**
 * Created by umesh on 11/9/15.
 */
public class DialogUtils {

    public static void showDialog(Context context, String message) {
        final MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(context);
        materialDialog.title(context.getString(R.string.text));
        materialDialog.content(message).positiveColorRes(R.color.blue_button_background).negativeColorRes(R.color.blue_button_background)
                .positiveText(context.getString(R.string.ok));
        if (!((CustomerAppBaseActivity) context).isFinishing())
            materialDialog.show();
    }


    public static boolean isMobileVarification(CustomerAppBaseActivity activity, String mobileNumber) {
        boolean checkmobileNumber = TextUtils.isEmpty(mobileNumber);
        if (checkmobileNumber) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_mobile_number));
            return false;
        }
        return true;
    }


    public static boolean isOTPVarification(CustomerAppBaseActivity activity, String otpEdit, String otpNumber) {

        boolean checkotpEditNumber = TextUtils.isEmpty(otpEdit);

        if (checkotpEditNumber) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_opt_number));
            return false;
        }
        if (!otpEdit.equalsIgnoreCase("123456")) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_opt_number_validate));
            return false;
        }
        return true;
    }

    public static void showDialogYesNo(Context context, final String msg, String yes, final String no, final IDialogListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yes_no);
        dialog.setCanceledOnTouchOutside(true);

        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setText(no);
        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
        tv_ok.setText(yes);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                listener.onCancel();
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    listener.onCancel();
                }
                return false;
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onCancel();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClickOk();
            }
        });
        dialog.show();
    }

    public static void showDialogNetwork(Context context, final String msg, final IDialogListener listener) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_network);
        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
        TextView msgg = (TextView) dialog.findViewById(R.id.view2);
        msgg.setText(msg);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClickOk();
            }
        });
        dialog.show();
    }

    public static void showDialogError(final Context context, final ErrorObject errors) {

        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.dialog_errors);
            TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_error_ok);
            TextView tv_error_code = (TextView) dialog.findViewById(R.id.tv_error_code);
            EditText tv_error_msg = (EditText) dialog.findViewById(R.id.tv_error_msg);
            TextView tv_support_number = (TextView) dialog.findViewById(R.id.tv_support_number);
            tv_error_code.setText(String.valueOf(errors.getErrorCode()));
            tv_error_msg.setText(errors.getErrorMessage());
            tv_support_number.setText(errors.getSupportContactNumber());

            dialog.findViewById(R.id.linear_support_number).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = errors.getSupportContactNumber();
                    if (number != null) {
                        try {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:" + number));
                            context.startActivity(callIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context, "Calling functinality is not founded", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Support number getting is null", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {

        }
    }

    public static boolean isLoginDetailSkip(CustomerAppBaseActivity activity, String name, String email) {

        boolean cname = TextUtils.isEmpty(name);
        boolean cEmail = TextUtils.isEmpty(email);
        boolean cEmailValid = Validation.isValidEmail(email);

        if (cname) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_name));
            return false;
        }

        if (cEmail) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_email));
            return false;
        }

        if (!cEmailValid) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_valid_email));
            return false;
        }

        return true;
    }

    public static boolean isLoginDetailVerify(CustomerAppBaseActivity activity, String name, String email, String flatNumber, String area,
                                              String locality, String city, String state, String pincode, boolean termCondition) {

        boolean cname = TextUtils.isEmpty(name);
        boolean cEmail = TextUtils.isEmpty(email);
        boolean cEmailValid = Validation.isValidEmail(email);
        boolean cflatNumber = TextUtils.isEmpty(flatNumber);
        boolean carea = TextUtils.isEmpty(area);

        if (cname) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_name));
            return false;
        }

        if (cEmail) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_email));
            return false;
        }

        if (!cEmailValid) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_valid_email));
            return false;
        }

        if (cflatNumber) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_flat_number));
            return false;
        }

        if (carea) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_area));
            return false;
        }
        boolean clocality = TextUtils.isEmpty(locality);
        boolean ccity = TextUtils.isEmpty(city);
        boolean cstate = TextUtils.isEmpty(state);
        boolean cpincode = TextUtils.isEmpty(pincode);

        if (clocality) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_locality));
            return false;
        }

        if (ccity) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_city));
            return false;
        }
        if (cstate) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_state));
            return false;
        }
        if (cpincode) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_pincode));
            return false;
        }

        if (!termCondition) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_term_condition));
            return false;
        }
        return true;
    }

    public static boolean isAddressVerifyEnter(AddAddressActivity activity, String flatNumber, String addressIdentifier, String locality, String building) {

        boolean cflatNumber = TextUtils.isEmpty(flatNumber);
        boolean caddressIdentifier = TextUtils.isEmpty(addressIdentifier);
        boolean clocality = TextUtils.isEmpty(locality);
        boolean cbuilding = TextUtils.isEmpty(building);


        if (caddressIdentifier) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_add_idenitifier));
            return false;
        }

        if (cflatNumber) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_flatnumber));
            return false;
        }

        if (cbuilding) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_building));
            return false;
        }

        if (clocality) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_locality));
            return false;
        }

        return true;

    }
}
