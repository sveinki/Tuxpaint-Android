package org.tuxpaint;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.libsdl.app.SDLActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.content.res.AssetManager;



import android.content.pm.PackageManager;
import android.Manifest;

public class tuxpaintActivity extends SDLActivity {
    private static final String TAG = "Tux Paint";
    private static AssetManager mgr;
    private static native boolean managertojni(AssetManager mgr);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");
	super.onCreate(savedInstanceState);
	mgr = getResources().getAssets();
	managertojni(mgr);

        if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
	    Intent intent = new Intent(this, ConfigActivity.class);
	    this.startActivity(intent);
	}
    }



 static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("tuxpaint_png");
        System.loadLibrary("tuxpaint_fribidi");
        System.loadLibrary("SDL2");
        System.loadLibrary("tp_android_assets_fopen");
        System.loadLibrary("tuxpaint_intl");
        System.loadLibrary("tuxpaint_iconv");
        System.loadLibrary("tuxpaint_pixman");
        System.loadLibrary("tuxpaint_xml2");
        System.loadLibrary("tuxpaint_freetype");
        System.loadLibrary("tuxpaint_fontconfig");
        System.loadLibrary("tuxpaint_ffi");
        System.loadLibrary("tuxpaint_glib");
        System.loadLibrary("tuxpaint_cairo");
        System.loadLibrary("tuxpaint_harfbuzz_ng");
        System.loadLibrary("tuxpaint_pango");
        System.loadLibrary("tuxpaint_gdk_pixbuf");
        System.loadLibrary("tuxpaint_croco");
        System.loadLibrary("tuxpaint_rsvg");
        System.loadLibrary("SDL2_image");
        System.loadLibrary("SDL2_mixer");
        System.loadLibrary("SDL2_ttf");
        System.loadLibrary("SDL2_Pango");
        System.loadLibrary("tuxpaint");
 }




}
