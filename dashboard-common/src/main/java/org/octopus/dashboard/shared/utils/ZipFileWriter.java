package org.octopus.dashboard.shared.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileWriter {

	@SuppressWarnings("unused")
	private OutputStream os;
	private ZipOutputStream zout;
	private int fileSize = 0;

	public ZipFileWriter(final OutputStream os) {
		this.os = os;
		zout = new ZipOutputStream(os);
	}

	public void writeEntry(final String zipEntryName, final byte[] zipEntry) {

		try {
			ZipEntry ze = new ZipEntry(zipEntryName);
			zout.putNextEntry(ze);
			zout.write(zipEntry);
			fileSize += ze.getCompressedSize();
			zout.closeEntry();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getFileSize() {
		return fileSize;
	}

	public void close() {
		try {
			zout.close();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
