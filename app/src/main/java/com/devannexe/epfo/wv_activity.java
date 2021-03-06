package com.devannexe.epfo;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.push.Push;

import static com.google.firebase.crash.FirebaseCrash.log;
import static com.google.firebase.crash.FirebaseCrash.report;

public class wv_activity extends AppCompatActivity {
    //Declaring Variables
    ProgressBar progress;
    AdView adView;
    private WebView brow;

    //Button go;
    //SearchView urledit;
    //ListView listView;

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCenter.start(getApplication(), "e65ff907-0496-40e2-8c49-55b86a0a26f2", Analytics.class, Crashes.class);
        Push.setSenderId("{727369200655}");
        Analytics.trackEvent("WV Activity");
        AppCenter.start(getApplication(), "e65ff907-0496-40e2-8c49-55b86a0a26f2", Push.class);

        Trace mytrace3 = FirebasePerformance.getInstance().newTrace("wv_activity");
        mytrace3.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wv_activity);
        report(new Exception("EPFO:Crash Log"));
        //I'm also creating a log message, which we'll look at in more detail later//
        log("wv_activity started");
        //logcat(Log.ERROR, TAG, "NPE caught");
        //report(ex);
        //Function Declaring
        //listView= findViewById(R.id.listView);
        progress = findViewById(R.id.progressBar);
        brow = findViewById(R.id.wv_brow);
        adView = findViewById(R.id.adView);
        //adView = new AdView(this);
        //adView.setAdSize(AdSize.SMART_BANNER);
        //adView.setAdUnitId(getString(R.string.banner_test));
        MobileAds.initialize(this,
                getString(R.string.admob_app_id));
        //mAdView = findViewById(R.id.adView);
        AdRequest adreq = new AdRequest.Builder().build();
        adView.loadAd(adreq);


//        go= findViewById(R.id.btn_search);
//        urledit= findViewById(R.id.urledit);
//        ((EditText) findViewById(R.id.urledit).findViewById( findViewById(R.id.urledit).getContext().getResources().getIdentifier("android:id/search_src_text", null, null))).setTextColor(Color.BLACK);
//        ((EditText) findViewById(R.id.urledit).findViewById( findViewById(R.id.urledit).getContext().getResources().getIdentifier("android:id/search_src_text", null, null))).setHintTextColor(Color.BLACK);
//        ((EditText) findViewById(R.id.urledit).findViewById( findViewById(R.id.urledit).getContext().getResources().getIdentifier("android:id/search_src_text", null, null))).setHintTextColor(Color.BLACK);

        brow.setWebViewClient(new ourViewClient());
        brow.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progress.setProgress(newProgress);
                if (newProgress == 100) {
                    progress.setVisibility(View.GONE);
                } else if (newProgress < 1) {
                    progress.setVisibility(View.GONE);
                } else {
                    progress.setVisibility(View.VISIBLE);
                }
            }
        });
        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";

        WebSettings wbset = brow.getSettings();

//        wbset.setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");
        wbset.setUserAgentString(newUA);
//        wbset.setUserAgentString(getString(R.string.desktop_view));
        wbset.getUseWideViewPort();
        wbset.getLoadWithOverviewMode();
        wbset.getUserAgentString();
        wbset.setJavaScriptEnabled(true);
        wbset.setAppCacheEnabled(true);
        wbset.getCacheMode();
        wbset.getCursiveFontFamily();
        wbset.getDatabaseEnabled();
        wbset.getJavaScriptCanOpenWindowsAutomatically();
        wbset.getMediaPlaybackRequiresUserGesture();
        wbset.setGeolocationEnabled(true);
        wbset.supportMultipleWindows();
        wbset.getAllowContentAccess();
        wbset.getAllowFileAccessFromFileURLs();
        wbset.getDisplayZoomControls();
        wbset.getDomStorageEnabled();
        wbset.getFantasyFontFamily();
        wbset.getLoadsImagesAutomatically();
        wbset.setDisplayZoomControls(true);
        wbset.setBuiltInZoomControls(true);
        wbset.setJavaScriptEnabled(true);
        wbset.setJavaScriptCanOpenWindowsAutomatically(true);
        wbset.getAllowFileAccessFromFileURLs();
        wbset.getAllowContentAccess();
        wbset.getAllowUniversalAccessFromFileURLs();
        wbset.setAllowUniversalAccessFromFileURLs(true);
        wbset.setAllowFileAccess(true);
        wbset.getJavaScriptCanOpenWindowsAutomatically();
        wbset.setJavaScriptEnabled(true);
        wbset.setJavaScriptCanOpenWindowsAutomatically(true);
        wbset.getDatabaseEnabled();
        wbset.getCacheMode();
        wbset.getMixedContentMode();
//        wbset.setUserAgentString("");

//        brow.clearCache(true);
//        brow.clearFormData();
//        brow.setWebViewClient(new WebViewClient());
//        wbset.setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("url");
        brow.loadUrl(url);

        brow.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimeType);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(wv_activity.this, Environment.DIRECTORY_DOWNLOADS, ".pdf");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                if (dm != null) {
                    dm.enqueue(request);
                }
                Analytics.trackEvent("file downloaded");
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }
        });


        /*url_epfo = getString(R.string.a_epfo_passbook);
        brow.loadUrl(url_epfo);*/
        /*
         //brow.loadUrl("http://www.google.com");
        urledit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                brow.setVisibility(View.VISIBLE);
                String editextvalue = urledit.getQuery().toString();
                if (!editextvalue.startsWith("http:\\"))
                    editextvalue = "https:\\" + editextvalue;
                String url = editextvalue;
                brow.loadUrl(url);
                //HIDE kEYBOARD AFTER CLICKING GO BUTTON
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(urledit.getWindowToken(),0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String editextvalue = urledit.getQuery().toString();

                return false;
            }
        });
        go.setOnClickListener( v -> {
            String editextvalue = urledit.getQuery().toString();
            if (!editextvalue.startsWith("http:\\"))
                editextvalue = "https:\\" + editextvalue;
            String url = editextvalue;
            brow.loadUrl(url);
            //HIDE kEYBOARD AFTER CLICKING GO BUTTON
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(urledit.getWindowToken(),0);
        } );
         */
        mytrace3.stop();

    }

    @Override
    public void onBackPressed() {
        if (brow.canGoBack()) {
            brow.goBack();
        } else {
            Intent intent = new Intent(wv_activity.this, portal_activity.class);
            startActivity(intent);
            finish();
        }
    }
}
