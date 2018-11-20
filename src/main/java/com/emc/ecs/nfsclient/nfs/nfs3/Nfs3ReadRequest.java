/**
 * Copyright 2016-2018 Dell Inc. or its subsidiaries. All rights reserved.
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
package com.emc.ecs.nfsclient.nfs.nfs3;

import java.io.FileNotFoundException;

import com.emc.ecs.nfsclient.nfs.NfsReadRequest;
import com.emc.ecs.nfsclient.rpc.Credential;

/**
 * The request, as specified by RFC 1813 (https://tools.ietf.org/html/rfc1813).
 * 
 * <p>
 * Procedure READ reads data from a file.
 * </p>
 * 
 * @author seibed
 */
public class Nfs3ReadRequest extends NfsReadRequest {

    /**
     * Creates the request, as specified by RFC 1813
     * (https://tools.ietf.org/html/rfc1813).
     * 
     * <p>
     * Procedure READ reads data from a file.
     * </p>
     * 
     * @param fileHandle
     *            The file handle of the file from which data is to be read.
     *            This must identify a file system object of type, NF3REG.
     * @param offset
     *            The position within the file at which the read is to begin. An
     *            <code>offset</code> of 0 means to read data starting at the
     *            beginning of the file. If <code>offset</code> is greater than
     *            or equal to the size of the file, the status, NFS3_OK, is
     *            returned with <code>size</code> set to 0 and <code>eof</code>
     *            set to <code>true</code>, subject to access permissions
     *            checking.
     * @param size
     *            The number of bytes of data that are to be read. If
     *            <code>size</code> is 0, the READ will succeed and return 0
     *            bytes of data, subject to access permissions checking.
     *            <code>size</code> must be less than or equal to the value of
     *            the <code>rtmax</code> field in the FSINFO reply structure for
     *            the file system that contains file. If greater, the server may
     *            return only <code>rtmax</code> bytes, resulting in a short
     *            read.
     * @param credential
     *            The credential used for RPC authentication.
     * @throws FileNotFoundException
     */
    public Nfs3ReadRequest(byte[] fileHandle, long offset, int size, Credential credential)
            throws FileNotFoundException {
        super(fileHandle, offset, size, credential, Nfs3.VERSION);
    }

}
