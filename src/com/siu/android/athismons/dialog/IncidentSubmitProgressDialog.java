package com.siu.android.athismons.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.siu.android.athismons.R;
import com.siu.android.athismons.fragment.IncidentFragment;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class IncidentSubmitProgressDialog extends DialogFragment {

    private IncidentFragment fragment;

    public IncidentSubmitProgressDialog(IncidentFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(R.string.incident_submit_progress_dialog_title);
        progressDialog.setMessage(getString(R.string.incident_submit_progress_dialog_message));
        progressDialog.setIndeterminate(true);

        progressDialog.setCancelable(true);

        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancel();
            }
        });

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                cancel();
            }
        });

        return progressDialog;
    }

    private void cancel() {
        fragment.stopIncidentSubmitTaskIfRunning();
        dismiss();
    }
}
