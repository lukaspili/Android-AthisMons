package com.siu.android.athismons.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.actionbarsherlock.app.SherlockFragment;
import com.siu.android.athismons.AppConstants;
import com.siu.android.athismons.R;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class PersonalSpaceFragment extends SherlockFragment {

    private WebView webView;
    private ProgressDialog webViewLoadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_space_fragment, container, false);
        webView = (WebView) view.findViewById(R.id.personal_space_webview);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                webViewLoadingDialog.setProgress(progress);

                if (progress == 100) {
                    webViewLoadingDialog.dismiss();
                }
            }
        });

        webViewLoadingDialog = new ProgressDialog(getActivity());
        webViewLoadingDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        webViewLoadingDialog.setMessage("Chargement en cours...");
        webViewLoadingDialog.setMax(100);
        webViewLoadingDialog.setCancelable(true);
        webViewLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                webView.stopLoading();
            }
        });

        webViewLoadingDialog.show();
        webView.loadUrl(AppConstants.PERSONAL_SPACE_URL);
    }
}
