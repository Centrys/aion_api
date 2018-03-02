/*******************************************************************************
 * Copyright (c) 2017-2018 Aion foundation.
 *
 *     This file is part of the aion network project.
 *
 *     The aion network project is free software: you can redistribute it
 *     and/or modify it under the terms of the GNU General Public License
 *     as published by the Free Software Foundation, either version 3 of
 *     the License, or any later version.
 *
 *     The aion network project is distributed in the hope that it will
 *     be useful, but WITHOUT ANY WARRANTY; without even the implied
 *     warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *     See the GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with the aion network project source files.
 *     If not, see <https://www.gnu.org/licenses/>.
 *
 * Contributors:
 *     Aion foundation.
 *
 ******************************************************************************/

package org.aion.api.type.core.account;

import org.aion.rlp.RLP;
import org.aion.rlp.RLPList;

import java.io.UnsupportedEncodingException;

public class KeystoreCrypto {
    private String cipher;
    private String cipherText;
    private String kdf;
    private String mac;
    private CipherParams cipherParams;
    private KdfParams kdfParams;

    byte[] toRlp() {
        byte[] bytesCipher = RLP.encodeString(this.cipher);
        byte[] bytesCipherText = RLP.encodeString(this.cipherText);
        byte[] bytesKdf = RLP.encodeString(this.kdf);
        byte[] bytesMac = RLP.encodeString(this.mac);
        byte[] bytesCipherParams = RLP.encodeElement(this.cipherParams.toRlp());
        byte[] bytesKdfParams = RLP.encodeElement(this.kdfParams.toRlp());
        return RLP.encodeList(bytesCipher, bytesCipherText, bytesKdf, bytesMac, bytesCipherParams, bytesKdfParams);
    }

    static KeystoreCrypto parse(byte[] bytes) throws UnsupportedEncodingException {
        RLPList list = (RLPList) RLP.decode2(bytes).get(0);
        KeystoreCrypto kc = new KeystoreCrypto();
        kc.setCipher(new String(list.get(0).getRLPData(), "UTF-8"));
        kc.setCipherText(new String(list.get(1).getRLPData(), "US-ASCII"));
        kc.setKdf(new String(list.get(2).getRLPData(), "UTF-8"));
        kc.setMac(new String(list.get(3).getRLPData(), "US-ASCII"));
        kc.setCipherParams(CipherParams.parse(list.get(4).getRLPData()));
        kc.setKdfParams(KdfParams.parse(list.get(5).getRLPData()));
        return kc;
    }

    // setters

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    void setCipherText(String ciphertext) {
        this.cipherText = ciphertext;
    }

    void setKdf(String kdf) {
        this.kdf = kdf;
    }

    void setMac(String mac) {
        this.mac = mac;
    }

    void setCipherParams(CipherParams cipherparams) {
        this.cipherParams = cipherparams;
    }

    void setKdfParams(KdfParams kdfparams) {
        this.kdfParams = kdfparams;
    }

    // getters

    public String getCipher() {
        return cipher;
    }

    String getCipherText() {
        return cipherText;
    }

    String getKdf() {
        return kdf;
    }

    String getMac() {
        return mac;
    }

    CipherParams getCipherParams() {
        return cipherParams;
    }

    KdfParams getKdfParams() {
        return kdfParams;
    }
}
