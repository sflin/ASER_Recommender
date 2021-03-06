package recommender.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Lists;

import cc.kave.commons.assertions.Asserts;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.json.JsonUtils;

public class ReadingArchiveEvents implements IReadingArchive {

	private ZipFile zipFile;
	private Enumeration<? extends ZipEntry> entries;

	public ReadingArchiveEvents(File file) {
		Asserts.assertTrue(file.exists(), String.format("The file '%s' does not exist.", file.getAbsolutePath()));
		Asserts.assertTrue(file.isFile(),
				String.format("The path '%s' does not point at a file.", file.getAbsolutePath()));
		try {
			zipFile = new ZipFile(file);
			entries = zipFile.entries();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasNext() {
		return entries.hasMoreElements();
	}

	@Override
	public <T> T getNext(Type classOfT) {
		try {
			ZipEntry next = entries.nextElement();
			//System.out.println(next.getName());
			InputStream in = zipFile.getInputStream(next);
			T obj = JsonUtils.fromJson(in, classOfT);
			in.close();

			return obj;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getNextPlain() {
		try {
			ZipEntry next = entries.nextElement();
			InputStream in = zipFile.getInputStream(next);
			StringWriter writer = new StringWriter();
			IOUtils.copy(in, writer, Charset.defaultCharset().toString());
			String str = writer.toString();
			in.close();

			return str;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> List<T> getAll(Class<T> c) {
		List<T> out = Lists.newLinkedList();
		while (hasNext()) {
			out.add(getNext(c));
		}
		return out;
	}

	@Override
	public int getNumberOfEntries() {
		return zipFile.size();
	}

	@Override
	public void close() {
		try {
			zipFile.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}