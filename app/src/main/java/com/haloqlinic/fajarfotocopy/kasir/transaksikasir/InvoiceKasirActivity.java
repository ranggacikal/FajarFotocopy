package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

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
import com.haloqlinic.fajarfotocopy.databinding.ActivityInvoiceKasirBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityListPengirimanKetoBinding;

public class InvoiceKasirActivity extends AppCompatActivity {

    private ActivityInvoiceKasirBinding binding;

    String id_status_penjualan;

    WebView printWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceKasirBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_status_penjualan = getIntent().getStringExtra("id_status_penjualan");

        binding.webViewKasir.setWebViewClient(new myWebclient());
        binding.webViewKasir.getSettings().setJavaScriptEnabled(true);
        binding.webViewKasir.getSettings().setBuiltInZoomControls(true);
        binding.webViewKasir.loadUrl("http://fajar-fotocopy.com/backend_fotocopy/index.php/API_invoice/getinvoice?id_status_penjualan="+id_status_penjualan);

        binding.savePdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printWeb != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Calling createWebPrintJob()
                        PrintTheWebPage(printWeb);
                    } else {
                        // Showing Toast message to user
                        Toast.makeText(InvoiceKasirActivity.this, "Not available for device below Android LOLLIPOP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Showing Toast message to user
                    Toast.makeText(InvoiceKasirActivity.this, "WebPage not fully loaded", Toast.LENGTH_SHORT).show();
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
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        String jobName = getString(R.string.app_name) + " Document";
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
        printJob = printManager.print(jobName, printAdapter, builder.build());

//        if(printJob.isCompleted()){
//            Toast.makeText(getApplicationContext(), R.string.print_complete, Toast.LENGTH_LONG).show();
//        }
//        else if(printJob.isFailed()){
//            Toast.makeText(getApplicationContext(), R.string.print_failed, Toast.LENGTH_LONG).show();
//        }
    }

    public class myWebclient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            binding.progressInvoiceKasir.setVisibility(View.GONE);
            printWeb = binding.webViewKasir;
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