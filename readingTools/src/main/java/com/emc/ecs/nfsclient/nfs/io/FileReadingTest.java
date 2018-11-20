/**
 * Copyright 2017-2018 Dell Inc. or its subsidiaries. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.emc.ecs.nfsclient.nfs.io;

import java.io.IOException;

import com.emc.ecs.nfsclient.nfs.NfsReadResponse;
import com.emc.ecs.nfsclient.nfs.nfs3.Nfs3;
import com.emc.ecs.nfsclient.rpc.CredentialUnix;

/**
 * @author seibed
 *
 */
public class FileReadingTest {

    public static void main(String[] args) {
        String export = args[0];
        String fileName = args[1];
        boolean useStream = Boolean.valueOf(args[2]);
        try {
            if (useStream) {
                testStreamReading(export, fileName);
            } else {
                testReading(export, fileName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testStreamReading(String export, String fileName)
            throws Exception {
        Nfs3 nfs3 = new Nfs3(export, new CredentialUnix(0, 0, null), 3);
        NfsFileInputStream inputStream = null;
        try {
            NfsFile nfsFile = new Nfs3File(nfs3, fileName);
            int fileLength = (int) nfsFile.length();
            inputStream = new NfsFileInputStream(nfsFile);
            byte[] buffer = new byte[fileLength];
            System.out.println("Testing stream reading");
            int bytesRead = inputStream.read(buffer);
            if (NfsFileInputStream.EOF != inputStream.read()) {
                throw new Exception("Reading error - should have been at the end of the file");
            }
            if (fileLength != bytesRead) {
                throw new Exception("Reading error - read " + bytesRead
                        + " bytes, should have been " + fileLength);
            }
            System.out.println("Success!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
        }
    }

    public static void testReading(String export, String fileName)
            throws Exception {
        Nfs3 nfs3 = new Nfs3(export, new CredentialUnix(0, 0, null), 3);
        try {
            NfsFile nfsFile = new Nfs3File(nfs3, fileName);
            int fileLength = (int) nfsFile.length();
            byte[] buffer = new byte[fileLength];
            System.out.println("Testing with plain read");
            NfsReadResponse response = nfsFile.read(0, buffer.length, buffer, 0);
            if (!response.isEof()) {
                throw new Exception("Reading error - should have been at the end of the file");
            }
            if (fileLength != response.getBytesRead()) {
                throw new Exception("Reading error - read " + response.getBytesRead()
                        + " bytes, should have been " + fileLength);
            }
            System.out.println("Success!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
