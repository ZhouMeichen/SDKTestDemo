package com.chivox;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.util.Log;

public class AiUtil {


    public static String sha1(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(message.getBytes(), 0, message.length());
            return bytes2hex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String md5(Context c, String fileName) {
        int bytes;
        byte buf[] = new byte[BUFFER_SIZE];
        try {
            InputStream is = c.getAssets().open(fileName);
            MessageDigest md = MessageDigest.getInstance("MD5");
            while ((bytes = is.read(buf, 0, BUFFER_SIZE)) > 0) {
                md.update(buf, 0, bytes);
            }
            is.close();
            return bytes2hex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static long getWordCount(String sent) {
        return sent.trim().split("\\W+").length;
    }


    public static long getHanziCount(String pin1yin1) {
        return pin1yin1.trim().split("-").length;
    }


    public static File externalFilesDir(Context c) {
        File f = c.getExternalFilesDir(null);
        // not support android 2.1
        if (f == null || !f.exists()) {
            f = c.getFilesDir();
        }
        return f;
    }


    public static String readFile(File file) {
        String res = null;
        try {
            FileInputStream fin = new FileInputStream(file);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    public static String readFileFromAssets(Context c, String fileName) {
        String res = null;
        try {
            InputStream in = c.getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    public static void writeToFile(String filePath, String string) {
        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            byte[] bytes = string.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeToFile(File f, String string) {
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(string);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeToFile(File f, InputStream is) {
        int bytes;
        byte[] buf = new byte[BUFFER_SIZE];
        try {
            FileOutputStream fos = new FileOutputStream(f);
            while ((bytes = is.read(buf, 0, BUFFER_SIZE)) > 0) {
                fos.write(buf, 0, bytes);
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static File unzipFile(Context c, String fileName) {
        try {
            String pureName = fileName.replaceAll("\\.[^.]*$", "");

            File filesDir = externalFilesDir(c);
            File targetDir = new File(filesDir, pureName);

            String md5sum = md5(c, fileName);
            File md5sumFile = new File(targetDir, ".md5sum");

            if (targetDir.isDirectory()) {
                if (md5sumFile.isFile()) {
                    String md5sum2 = readFile(md5sumFile);
                    if (md5sum2.equals(md5sum)) {// already extracted
                        return targetDir;
                    }
                }
                // remove old dirty resource
                removeDirectory(targetDir);
            }

            unzip(c, fileName, targetDir);
            writeToFile(md5sumFile, md5sum);
            return targetDir;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(tag, "Failed to extract resource", e);
        }
        return null;
    }

    private static String tag = "AiUtil";
    private static int BUFFER_SIZE = 8192;

    private static String bytes2hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    private static void removeDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    removeDirectory(files[i]);
                }
                files[i].delete();
            }
            directory.delete();
        }
    }

    public static File getFilesDir(Context context) {
        File targetDir = context.getExternalFilesDir(null);
        if (targetDir == null || !targetDir.exists()) {
            targetDir = context.getFilesDir();
        }
        return targetDir;
    }
    
    private static void unzip(Context c, String fileName, File targetDir)
            throws IOException {
        InputStream is = c.getAssets().open(fileName);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is,
                BUFFER_SIZE));

        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            if (ze.isDirectory()) {
                new File(targetDir, ze.getName()).mkdirs();
            } else {
                File file = new File(targetDir, ze.getName());
                File parentdir = file.getParentFile();
                if (parentdir != null && (!parentdir.exists())) {
                    parentdir.mkdirs();
                }
                int pos;
                byte[] buf = new byte[BUFFER_SIZE];
                OutputStream bos = new FileOutputStream(file);
                while ((pos = zis.read(buf, 0, BUFFER_SIZE)) > 0) {
                    bos.write(buf, 0, pos);
                }
                bos.flush();
                bos.close();
            }
        }
        zis.close();
        is.close();
    }
}
