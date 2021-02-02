package com.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

/**
 * Project :  mtesense
 * Created :  java
 * Date    :  3/26/15
 */
public class FileUtil {
    /**
     * appends the data to the specified filename with file locking.
     * Uses the java.nio.channel package to implement the locking
     *
     * @param filename
     * @param data
     */
    public static void append(String filename, String data) {
        append_or_overwrite(filename, data, true);
    }

    /**
     * Copies src file to dst file. If the dst file does not exist, it is created.
     * Locking is put onto the dst file during the copy.
     * Uses the java.nio.channel package to implement the locking
     *
     * @return <Code>true</Code> if the copy had no exception<br>
     *         <Code>false</Code> if the copoy had exception
     */
    public static boolean copy(String src_filename, String dst_filename) {
        try {
            // Create channel on the source
            File src_file = new File(src_filename);
            @SuppressWarnings("resource")
			FileChannel srcChannel = new FileInputStream(src_file).getChannel();

            // Create channel on the destination
            @SuppressWarnings("unused")
            File dst_file = new File(dst_filename);
            // create a overwrite channel (with false)
            @SuppressWarnings("resource")
			FileChannel dstChannel = new FileOutputStream(dst_filename, false).getChannel();
            //FileLock lock = dstChannel.lock();
            try {

                // Copy file contents from source to destination
                dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

                // Close the channels
                srcChannel.close();
                dstChannel.close();
            } finally {
                //    lock.release();
            }

        } catch (IOException e) {
//            System.out.println(e.getClass() + ", " + e.getMessage());
        } catch (Exception e) {
//        	System.out.println(e.getMessage());
        }

        return true;
    }

    public static boolean appendImage(String filename, BufferedImage image) {

        try {
            File file = new File(filename);
            String format = "JPEG";
            ImageIO.write(image, format, file);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    /**
     * test for the existence of the given filename.
     *
     * @param filename full pathname of the file
     * @return true if the file exists<br>
     *         otherwise false
     */
    public static boolean exists(String filename) {
        return new File(filename).exists();

    }

    /**
     * Overwrites the given file with the data with file locking.
     * Uses the java.nio.channel package to implement the locking and
     * will create the file if file doesn't exist.
     *
     * @param filename
     * @param data
     */
    public static void overwrite(String filename, String data) {
        append_or_overwrite(filename, data, false);
    }

    
    public static String readLineContent(String filePath){
        BufferedReader reader = null;
        String lineContent="";
        try {
          reader = new BufferedReader(new FileReader(filePath));
          lineContent=reader.readLine();
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          if (reader != null) {
            try {
              reader.close();
            } catch (IOException e1) {
            }
          }
        }
        return lineContent;
      }
    
    
   
        
    public static void deleLinesContent(String filePath) throws Exception{
        List<String> listContent = new ArrayList<>();
        try{
          BufferedReader reader = new BufferedReader(new FileReader(filePath));
          String content = reader.readLine();
          while(content!=null){
        	  content = reader.readLine();
              if (content!=null) {
            	  listContent.add(content);
			}
        	  
              System.out.println(listContent);
          }
        } catch(Exception e){
        }
        writeFileContext(listContent,filePath);
        
      }
    
    

	public static void writeFileContext(List<String>  strings, String path) throws Exception {
		File file = new File(path);
        //如果没有文件就创建
        if (!file.isFile()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for (String l:strings){
            writer.write(l + "\r\n");
        }
        writer.close();
    }
    
    public static void fileLinesWrite(String filePath,String content){
        FileWriter fw = null;
        try {
          File file=new File(filePath);
          //如果文件夹不存在，则创建文件夹
            fw = new FileWriter(file);
        } catch (IOException e) {
          e.printStackTrace();
        }
          PrintWriter pw = new PrintWriter(fw);
          pw.println(content);
          pw.flush();
        try {
          fw.flush();
          pw.close();
          fw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    /**
     * reads the specified file and returns the content ps a StringBuffer
     *
     * @param filename
     * @return content of the file ps a StringBuffer
     */
    public static String read(String filename) {
        String content = null;

        try {
            @SuppressWarnings("unused")
            boolean copy = false;
            @SuppressWarnings("unused")
            FileChannel in_channel;
            @SuppressWarnings("unused")
            FileLock lock;

            // Get a file channel for the file
            File file = new File(filename);
            FileInputStream input = new FileInputStream(file);

            byte[] result = new byte[(int) file.length()];

            final int length = result.length;
            int offset = 0;
            long byte_read = 0;

            while (byte_read != -1 && offset < file.length()) {
                try {
                    byte_read = input.read(result, offset, length - offset);
                    if (byte_read >= 0)   // bytesRead == -1 when end-of-file is reached
                        offset += byte_read;

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            // copy the result into content to return
            // this is because we must have result declared inside the try/catch clause
            content = new String(result);
            input.close();    // close file
        } catch (Exception e) {
            return e.getMessage();
        }

        return content;
    }

    //--------------------------- private methods ---------------------------------

    /**
     * appends or overwrite the specified file with the data
     *
     * @param filename
     * @param data
     * @param append   - true for append, false for overwrite
     */
    private static void append_or_overwrite(String filename, String data, boolean append) {
        try {
            // Get a file channel for the file
            File file = new File(filename);
            @SuppressWarnings("resource")
			FileChannel channel = new FileOutputStream(file, append).getChannel();

            // Use the file channel to create a lock on the file.
            // This method blocks until it can retrieve the lock.
            FileLock lock = channel.lock();

            try {
                // position the file pointer at the end of the file
                //channel.position(channel.size());

                ByteBuffer bb = ByteBuffer.wrap(data.getBytes());

                @SuppressWarnings("unused")
				int byte_written = channel.write(bb);

                channel.close();
            } finally {
                lock.release();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
