package com.haloqlinic.fajarfotocopy.driver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.databinding.ActivityWebViewReportMintaBarangKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityWebViewSuratJalanBinding;
import com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo.WebViewReportMintaBarangKetoActivity;

public class WebViewSuratJalanActivity extends AppCompatActivity {

    private ActivityWebViewSuratJalanBinding binding;

    String id_status_pengiriman;
    String status_pengiriman, status_pengiriman_intent;
    String link_web;

    private SharedPreferencedConfig preferencedConfig;

    WebView printWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_surat_jalan);
        binding = ActivityWebViewSuratJalanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        id_status_pengiriman = getIntent().getStringExtra("id_status_pengiriman");
        status_pengiriman_intent = getIntent().getStringExtra("status_pengiriman");

        link_web = "http://fajar-fotocopy.com/backend_fotocopy/index.php/API_fotocopy/" +
                "getSuratJalan?id_status_pengiriman="+id_status_pengiriman;

        binding.webViewSuratJalan.setWebViewClient(new myWebclient());
        binding.webViewSuratJalan.getSettings().setJavaScriptEnabled(true);
        binding.webViewSuratJalan.getSettings().setBuiltInZoomControls(true);
        binding.webViewSuratJalan.loadUrl(link_web);

        binding.savePdfBtnSuratJalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printWeb != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Calling createWebPrintJob()
                        PrintTheWebPage(printWeb);
                    } else {
                        // Showing Toast message to user
                        Toast.makeText(WebViewSuratJalanActivity.this, "Not available for device below Android LOLLIPOP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Showing Toast message to user
                    Toast.makeText(WebViewSuratJalanActivity.this, "WebPage not fully loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    PrintJob printJob;

    // a boolean to check the status of printing
    boolean printBtnPressed = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void PrintTheWebPage(WebView webView) {
        // set printBtnPressed true
        printBtnPressed = true;

        // Creating  PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        // setting the name of job
        String jobName = getString(R.string.app_name) + " webpage" + webView.getUrl();

        // Creating  PrintDocumentAdapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        assert printManager != null;
        printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }


    public class myWebclient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            binding.progressInvoiceSuratJalan.setVisibility(View.GONE);
            printWeb = binding.webViewSuratJalan;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}