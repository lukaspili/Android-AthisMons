package com.siu.android.athismons.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;
import com.siu.android.athismons.R;
import com.siu.android.athismons.fragment.IncidentFragment;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class IncidentTypesDialog extends DialogFragment {

    private IncidentFragment fragment;

    public IncidentTypesDialog(IncidentFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] items = getResources().getStringArray(R.array.incident_types);
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.incident_types_title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        fragment.updateIncidentType(items[item]);
                    }
                })
                .create();
    }
}
