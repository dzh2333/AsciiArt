package com.mark.asciiartapp.constants;

import android.os.Environment;

import java.io.File;

public class Constants {

    public static final String ASCII_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ascii";

    public static final String DCIM_CAMERA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM/Camera/";

    public static final String ASCII_CAMERA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AsciiCamera";

    public static final String[] FILTER_PATH = {"/data/hw_init/product"};
}
