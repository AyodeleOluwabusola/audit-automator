/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.audit.automator.email;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Ayodele Oluwabusola
 * @since May 17, 2021 - 12:30:36 AM
 */
@Getter
@Setter
@ToString
public class EmailApiAuthenticator {

    private String username;
    private String password;
    private int port;
    private String hostname;
    private String fromTitle;

    private boolean startTls;
    private boolean smtpAuth;
    private boolean sslEnabled;
    private boolean mailDebug;
    private byte[] byteData;

    public EmailApiAuthenticator(String username, byte[] byteData, String password, int port, String hostname, String fromTitle) {
        this.username = username;
        this.password = password;
        this.port = port;
        this.hostname = hostname;
        this.fromTitle = fromTitle;
        this.byteData = byteData;
    }
    
}