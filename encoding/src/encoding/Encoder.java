package encoding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class Encoder {
	 /**
    *
    * @param args
    * @throws UnsupportedEncodingException
    * @throws IOException
    */
   public static void main(String[] args) throws UnsupportedEncodingException, IOException {
       //1、改工作空间为UTF-8
	   //2、输入项目地址
	   File source = new File("C:\\Users\\lfy\\0228ee\\manager-core");
       
       String sourceEncoding = "GBK";
       String targetEncoding = "UTF-8";
       getAllFiles(source, sourceEncoding, targetEncoding);
   }

   
   public static void getAllFiles(File file, String bm1, String bm2) throws FileNotFoundException, UnsupportedEncodingException, IOException {
       if (file.isDirectory()) {
           File[] test = file.listFiles();
           for (File test1 : test) {
               String str = test1.getPath();
               if (str.endsWith("java") & test1.isFile()) {
                   String[] s = str.split("\\.");
                   String filecope = s[0] + "cope." + s[1];
                   System.out.println(filecope);
                   File fil = new File(filecope);

                   InputStreamReader isr = new InputStreamReader(new FileInputStream(test1), bm1);
                   OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(fil), bm2);
                   int re = -1;
                   while ((re = isr.read()) != -1) {
                       osr.write(re);
                   }
                   isr.close();
                   osr.close();
                   
                   InputStreamReader isrr = new InputStreamReader(new FileInputStream(fil), bm2);
                   OutputStreamWriter osrw = new OutputStreamWriter(new FileOutputStream(test1), bm2);
                   int r = -1;
                   while ((r = isrr.read()) != -1) {
                       osrw.write(r);
                   }
                   isrr.close();
                   osrw.close();
                   boolean d = fil.delete();
                   System.out.println(str + "转换完成:" + d);
               }
               getAllFiles(test1, bm1, bm2);
           }
       }
   }

}
