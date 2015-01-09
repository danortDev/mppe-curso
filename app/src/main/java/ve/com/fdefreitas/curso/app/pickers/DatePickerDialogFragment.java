package ve.com.fdefreitas.curso.app.pickers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by fernando on 25/08/14.
 *
 */
public class DatePickerDialogFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

    private static final String tag = DatePickerDialogFragment.class.getSimpleName();
    public static final String DATE_STRING = "date_string";
    private DatePickerDialogFragmentListener mListener;
    public DatePickerDialog datePickerDialog;
    public DatePickerDialogFragment datePickerDialogFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        datePickerDialogFragment = this;
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        return  datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        month = month +1;
        Bundle dateBundle = new Bundle();
        dateBundle.putInt("intYear", year);
        dateBundle.putInt("intMonth", month);
        dateBundle.putInt("intDay", day);
        dateBundle.putString(DATE_STRING, day + "/" + month + "/" + year);
        Log.d(tag, day + "/" + month + "/" + year);
        mListener.onDateDialogPositiveClick(datePickerDialogFragment, dateBundle);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DatePickerDialogFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement "
                    + DatePickerDialogFragmentListener.class.getSimpleName());
        }
    }

    public interface DatePickerDialogFragmentListener {
        public void onDateDialogPositiveClick(DialogFragment dialog, Bundle dateBundle);
    }
}
