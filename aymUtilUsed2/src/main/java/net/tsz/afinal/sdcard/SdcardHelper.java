package net.tsz.afinal.sdcard;

/**
 * Created by Jaelyn on 16/3/4.
 */
import android.os.Environment;
import android.os.StatFs;
import java.io.File;

public final class SdcardHelper {
    public SdcardHelper() {
    }

    public static boolean ExistSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = (long)sf.getBlockSize();
        long freeBlocks = (long)sf.getAvailableBlocks();
        return freeBlocks * blockSize / 1024L;
    }

    public long getSDAllSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = (long)sf.getBlockSize();
        long allBlocks = (long)sf.getBlockCount();
        return allBlocks * blockSize / 1024L;
    }
}
