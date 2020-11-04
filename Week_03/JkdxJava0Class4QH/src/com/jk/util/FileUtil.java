package com.jk.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
/**
 * 文件工具
 * @author qin.hui
 *
 */
public class FileUtil {

	/**
	 *  取文件 byte[] 值
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileByte(File file) throws Exception {

		byte[] result_ = new byte[0];

		FileInputStream fis = new FileInputStream(file);

		FileChannel fc = fis.getChannel();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		WritableByteChannel wbc = Channels.newChannel(baos);

		ByteBuffer by = ByteBuffer.allocate(1024);

		try {
			
			while (true) {
				int i = fc.read(by);
				if (i == 0 || i == -1)
					break;
				by.flip();
				wbc.write(by);
				by.clear();
			}
			
		} finally {
			// TODO: handle finally clause
			fis.close();
		}
		
		result_ = baos.toByteArray();
		
		
		return result_;

	}

}
