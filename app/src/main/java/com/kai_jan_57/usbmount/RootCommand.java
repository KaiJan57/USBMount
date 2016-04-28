package com.kai_jan_57.usbmount;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RootCommand {
    public Process su;
    public DataOutputStream write;
    public BufferedReader read;

    public RootCommand() throws Exception {
        try {
            su = Runtime.getRuntime().exec("su");
            write = new DataOutputStream(su.getOutputStream());
            read = new BufferedReader((new InputStreamReader(su.getInputStream())));
            writeString("whoami");
            if (!readString(false).equals("root")) {
                exit();
                throw new Exception();
            }
        } catch (Exception e) {
            exit();
            throw new Exception();
        }
    }

    public void exit() {
        try {
            write.write("exit\n".getBytes("UTF-8"));
            write.flush();
            write.close();
        } catch (Exception e) {

        }
    }

    public boolean writeString(String command) {
        try {
            write.write((command + "\n").getBytes());
            write.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String readString(boolean checkready) {
        try {
            if (checkready && !read.ready()) {
                return null;
            }
            return read.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    public String[] readAll() {
        try {
            List<String> list = new ArrayList<String>();
            list.add(readString(false));
            while (true) {
                String tmp = readString(true);
                if (tmp == null) {
                    break;
                }
                list.add(tmp);
            }
            Object[] objectArray = list.toArray();
            return Arrays.copyOf(objectArray, objectArray.length, String[].class);
        } catch (Exception e) {
            return null;
        }
    }
}