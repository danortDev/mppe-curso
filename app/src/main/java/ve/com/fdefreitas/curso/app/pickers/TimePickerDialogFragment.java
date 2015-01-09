package ve.com.fdefreitas.curso.app.pickers;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by fernando on 25/08/14.
 * @since 25/08/14
 */
public class TimePickerDialogFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = TimePickerDialogFragment.class.getSimpleName();
    public static final String TIME_STRING = "time_string";
    public static final String DATE_MILIS = "date_milis";
    public static final String INT_HOUR = "intHour";
    public static final String INT_MINUTE = "intMinute";
    private TimePickerDialogFragmentListener mListener;
    public TimePickerDialog timePickerDialog;
    public TimePickerDialogFragment timePickerDialogFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        timePickerDialogFragment = this;
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,false);
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Bundle timeBundle = new Bundle();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        timeBundle.putInt(INT_HOUR, hourOfDay);
        timeBundle.putInt(INT_MINUTE, minute);
        timeBundle.putString(TIME_STRING, String.format("%02d", hourOfDay)
                + ":" + String.format("%02d", minute));
        timeBundle.putLong(DATE_MILIS, cal.getTime().getTime());
        Log.d(TAG, String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
        mListener.onTimeDialogPositiveClick(timePickerDialogFragment, timeBundle);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (TimePickerDialogFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + TimePickerDialogFragmentListener.class.getSimpleName());
        }
    }

    public interface TimePickerDialogFragmentListener {
        public void onTimeDialogPositiveClick(DialogFragment dialog, Bundle timeBundle);
    }
}
