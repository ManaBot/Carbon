/*
 * This file is part of Carbon, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016, Jamie Mansfield <https://www.jamierocks.uk/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package uk.jamierocks.mana.carbon.bootstrap.dependency;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The dependency manager.
 *
 * @author Jamie Mansifield
 * @since 2.0.0
 */
public final class DependencyManager {

    // From http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
    private static final char[] hexArray = "0123456789abcdef".toCharArray();

    private final Path librariesPath;

    public DependencyManager(Path librariesPath) {
        this.librariesPath = librariesPath;
    }

    /**
     * Checks if the said dependency exists, and if not download it.
     *
     * @param repo The repo of which the dependency comes from
     * @param dependency The dependency
     * @throws IOException in error
     * @throws NoSuchAlgorithmException in error
     * @since 2.0.0
     */
    public boolean checkDependency(String repo, Dependency dependency) throws IOException, NoSuchAlgorithmException {
        final Path depPath = this.librariesPath.resolve(dependency.getJarPath());
        final String remote = repo + dependency.getJarPath();

        if (Files.notExists(depPath) && !verifyDownload(remote, depPath)) {
            return false;
        }

        return Files.exists(depPath) || verifyDownload(remote, depPath);
    }

    private static boolean verifyDownload(String remote, Path path) throws IOException, NoSuchAlgorithmException {
        Files.createDirectories(path.getParent());

        String name = path.getFileName().toString();
        URL url = new URL(remote);

        System.out.println("Downloading " + name + "... This can take a while.");
        System.out.println(url);
        URLConnection con = url.openConnection();
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        try (ReadableByteChannel source = Channels.newChannel(new DigestInputStream(con.getInputStream(), md5));
                FileChannel out = FileChannel.open(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            out.transferFrom(source, 0, Long.MAX_VALUE);
        }

        String expected = getETag(con);
        if (!expected.isEmpty() && !expected.startsWith("{SHA1{")) {
            String hash = toHexString(md5.digest());
            if (hash.equals(expected)) {
                System.out.println("Successfully downloaded " + name + " and verified checksum!");
            } else {
                Files.delete(path);
                throw new IOException("Checksum verification failed: Expected " + expected + ", got " + hash);
            }
        }

        return true;
    }

    private static String getETag(URLConnection con) {
        String hash = con.getHeaderField("ETag");
        if (hash == null || hash.isEmpty()) {
            return "";
        }

        if (hash.startsWith("\"") && hash.endsWith("\"")) {
            hash = hash.substring(1, hash.length() - 1);
        }

        return hash;
    }

    public static String toHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
