package com.mark.asciiartapp.util;

import java.io.File;

public class FileHelper {
    public static void initPath(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
    }
}
