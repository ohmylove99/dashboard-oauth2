package org.octopus.dashboard.shared.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
@SuppressWarnings("unchecked")
public class ZipFileReader {

	private ZipInputStream zis;
	private int fileSize = 0;

	public ZipFileReader(final ZipInputStream is) {
		this.zis = is;
	}

	@SuppressWarnings("rawtypes")
	public List<ZipEntry> readEntries() {

		try {
			
			List<ZipEntry> zipEntries = new ArrayList();

			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {
				fileSize += ze.getCompressedSize();

				zipEntries.add(ze);
				ze = zis.getNextEntry();
			}
			return zipEntries;
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * File size compressed
	 * @return
	 */
	public int getFileSize() {
		return fileSize;
	}

	public void close() {
		try {
			zis.close();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
