package src;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.lang.management.*;

class Bill extends Exception{

    void juke(){

        Thread thread = new Thread(new MultiRunnable());
        thread.setDaemon(true);
        thread.start();

        try {

            TimeUnit.SECONDS.sleep(2);
            System.out.println(">------------------------------------------------------------------------<");
            //System.out.println(" Location = " + System.getenv("LANG")); //ロケーションを調べるとき
            System.out.println(" JDK name          = " + System.getProperty("java.vm.name"));
            System.out.println(" openjdk version   = " + System.getProperty("java.version"));
            System.out.println(" JRE vendor        = " + System.getProperty("java.vendor"));
            System.out.println(" Java home         = " + System.getProperty("java.home"));
            System.out.println(" Library load path = " + System.getProperty("java.library.path"));
            System.out.println(" java temp file    = " + System.getProperty("java.io.tmpdir"));
            System.out.println(">------------------------------------------------------------------------<");
            System.out.println(" OS name           = " + System.getProperty("os.name"));
            System.out.println(" OS version        = " + System.getProperty("os.version"));
            System.out.println(" file separator    = " + System.getProperty("file.separator"));
            System.out.println(" path separator    = " + System.getProperty("path.separator"));
            System.out.println(" user name         = " + System.getProperty("user.name"));
            System.out.println(" user home         = " + System.getProperty("user.home"));
            System.out.println(" user dir          = " + System.getProperty("user.dir"));
            System.out.println(">------------------------------------------------------------------------<");

            Runtime runtime = Runtime.getRuntime();
            long free = runtime.freeMemory();
            long total = runtime.totalMemory();
            long max = runtime.maxMemory();
            
            MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
            MemoryUsage usage = mbean.getHeapMemoryUsage();
            
            System.out.println(" 自由なメモリサイズ             : " + free/1024/1024);
            System.out.println(" 総合的メモリサイズ             : " + total/1024/1024);
            System.out.println(" 最大のメモリサイズ             : " + max/1024/1024);
            
            System.out.println(" 起動時のメモリサイズ           : " + usage.getInit()/1024/1024);
            System.out.println(" 現在利用されているメモリサイズ : " + usage.getUsed()/1024/1024);
            System.out.println(" 保証されているメモリサイズ     : " + usage.getCommitted()/1024/1024);
            System.out.println(" 管理対象の最大メモリサイズ     : " + usage.getMax()/1024/1024);
            
            System.out.println(">------------------------------------------------------------------------<");
            System.out.println();
            
            File file = new File(System.getProperty("user.dir") + "/src/Cryptman.java");
            Date date = new Date(file.lastModified());
            Locale locale = new Locale("ja", "JP", "JP");
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "GGGy年 M月 d日 EEEE a h時 m分 s秒 Sミリ秒", locale);
            System.out.println(" 最終更新日時 = [ " + sdf.format(date) + " ]");
            System.out.println();
            System.out.println(">------------------------------------------------------------------------<");
        } catch (Exception e) {
            System.err.println("[ERROR] エラー出力：エラーが発生しました。");
            e.printStackTrace();
        } finally {
            System.gc();
            System.out.println();
            System.out.println(" 〜 スレッド・GC ① 処理を終了しました 〜 ");
            System.out.println();
            System.out.println(">------------------------------------------------------------------------<");
        }
    }
}

class MultiRunnable implements Runnable{

    @Override
    public void run() {

        try {

            Thread.sleep(500);
            
            /*
            keyを変更すれば、パスワードの生成は可能。
            keyはString型を変換している仕様です。
            */
            
            String key = "20200223"; //GitLab, hakoirimusume project.
            String original = "天皇誕生日";

            String algorithm = "Blowfish"; // 暗号化方式「Blowfish」

            System.out.println(">------------------------------------------------------------------------<");
            System.out.println();
            System.out.println(" ⦿ zinbei2のパスワード付きZIPファイルのキー生成... ");
            System.out.println();
            System.out.println(" ◎ 暗号化のキー = [ " + key + " ]");
            System.out.println();
            String encrypedResult = CipherHelper.encrypt(original, key, algorithm);
            System.out.println(" ○ 暗号化文字列 = [ " + encrypedResult + " ]");
            System.out.println();

            String decryptedResult = CipherHelper.decrypt(encrypedResult, key, algorithm);
            System.out.println(" ● 復号化文字列 = [ " + decryptedResult + " ]");
            System.out.println();

        } catch (Exception e) {
            System.err.println("[ERROR] エラー出力：エラーが発生しました。");
            e.printStackTrace();
        } finally {
            System.out.println(">------------------------------------------------------------------------<");
            System.gc();
            System.out.println();
            System.out.println(" 〜 スレッド・GC ② 処理を終了しました 〜 ");
            System.out.println();
        }
    }
}

class Cryptman {
    public static void main(final String... args){

        Bill obj = new Bill();
        obj.juke();
    }
}



