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
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.databinding.ActivityWebViewSuratJalanBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityWebViewSuratJalanSupplierBinding;

public class WebViewSuratJalanSupplierActivity extends AppCompatActivity {

    private ActivityWebViewSuratJalanSupplierBinding binding;

    String id_status_penjualan_gudang;
    String status_penjualan_gudang, status_penjualan_gudang_intent;
    String link_web;

    private SharedPreferencedConfig preferencedConfig;

    WebView printWeb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_surat_jalan);
        binding = ActivityWebViewSuratJalanSupplierBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        id_status_penjualan_gudang = getIntent().getStringExtra("id_status_penjualan_gudang");
        status_penjualan_gudang_intent = getIntent().getStringExtra("status_penjualan_gudang");

        link_web = "http://fajar-fotocopy.com/backend_fotocopy/index.php/API_fotocopy/" +
                "getSuratJalanSupplier?id_status_penjualan_gudang="+id_status_penjualan_gudang;

        binding.webViewSuratJalanSupplier.setWebViewClient(new myWebclient());
        binding.webViewSuratJalanSupplier.getSettings().setJavaScriptEnabled(true);
        binding.webViewSuratJalanSupplier.getSettings().setBuiltInZoomControls(true);
        binding.webViewSuratJalanSupplier.loadUrl(link_web);

        binding.savePdfBtnSuratJalanSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printWeb != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Calling createWebPrintJob()
                        PrintTheWebPage(printWeb);
                    } else {
                        // Showing Toast message to user
                        Toast.makeText(WebViewSuratJalanSupplierActivity.this, "Not available for device below Android LOLLIPOP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Showing Toast message to user
                    Toast.makeText(WebViewSuratJalanSupplierActivity.this, "WebPage not fully loaded", Toast.LENGTH_SHORT).show();
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
            binding.progressInvoiceSuratJalanSupplier.setVisibility(View.GONE);
            printWeb = binding.webViewSuratJalanSupplier;
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
