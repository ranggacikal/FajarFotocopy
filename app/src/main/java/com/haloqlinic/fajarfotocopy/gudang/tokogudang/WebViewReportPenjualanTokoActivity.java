package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

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
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.databinding.ActivityWebViewReportPengirimanBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityWebViewReportPenjualanTokoBinding;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.WebViewReportPengirimanActivity;

public class WebViewReportPenjualanTokoActivity extends AppCompatActivity {

    private ActivityWebViewReportPenjualanTokoBinding binding;

    String bulan_tahun, tanggal, pilihan, id_outlet;
    String link_web;

    private SharedPreferencedConfig preferencedConfig;

    WebView printWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewReportPenjualanTokoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        pilihan = getIntent().getStringExtra("pilihan");
        bulan_tahun = getIntent().getStringExtra("bulan_tahun");
        tanggal = getIntent().getStringExtra("tanggal");
        id_outlet = getIntent().getStringExtra("id_outlet");

        if (pilihan.equals("Hari") || pilihan.equals("hari")){
            link_web = "http://fajar-fotocopy.com/backend_fotocopy/index.php/API_fotocopy/" +
                    "getTransaksiByHari?hari="+tanggal+
                    "&id_outlet="+id_outlet;

        }else if (pilihan.equals("bulan") || pilihan.equals("Bulan")){
            link_web = "http://fajar-fotocopy.com/backend_fotocopy/index.php/API_fotocopy/" +
                    "getTransaksiByBulan?bulan="+bulan_tahun+
                    "&id_outlet="+id_outlet;
        }


        binding.webViewLaporanPenjualanToko.setWebViewClient(new myWebclient());
        binding.webViewLaporanPenjualanToko.getSettings().setJavaScriptEnabled(true);
        binding.webViewLaporanPenjualanToko.getSettings().setBuiltInZoomControls(true);
        binding.webViewLaporanPenjualanToko.loadUrl(link_web);

        Log.d("linkInvoice", "onCreate: "+link_web);

        binding.savePdfBtnLaporanPenjualanToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printWeb != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Calling createWebPrintJob()
                        PrintTheWebPage(printWeb);
                    } else {
                        // Showing Toast message to user
                        Toast.makeText(WebViewReportPenjualanTokoActivity.this, "Not available for device below Android LOLLIPOP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Showing Toast message to user
                    Toast.makeText(WebViewReportPenjualanTokoActivity.this, "WebPage not fully loaded", Toast.LENGTH_SHORT).show();
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
            binding.progressInvoiceLaporanPenjualanToko.setVisibility(View.GONE);
            printWeb = binding.webViewLaporanPenjualanToko;
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