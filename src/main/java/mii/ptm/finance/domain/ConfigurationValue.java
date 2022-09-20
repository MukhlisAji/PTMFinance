/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.ptm.finance.domain;

/**
 *
 * @author mukhlisaj
 */
public class ConfigurationValue {
    
    private String remedyServer;
    private String remedyUsername, remedyPassword;
    private String remedyPort;
    private String remedyMiddleFormInvoice;
    private String remedyMiddleFormReceipt;

    public ConfigurationValue(String remedyServer, String remedyUsername, String remedyPassword, String remedyPort, String remedyMiddleFormInvoice, String remedyMiddleFormReceipt) {
        this.remedyServer = remedyServer;
        this.remedyUsername = remedyUsername;
        this.remedyPassword = remedyPassword;
        this.remedyPort = remedyPort;
        this.remedyMiddleFormInvoice = remedyMiddleFormInvoice;
        this.remedyMiddleFormReceipt = remedyMiddleFormReceipt;
    }

    
    public String getRemedyServer() {
        return remedyServer;
    }

    public void setRemedyServer(String remedyServer) {
        this.remedyServer = remedyServer;
    }

    public String getRemedyUsername() {
        return remedyUsername;
    }

    public void setRemedyUsername(String remedyUsername) {
        this.remedyUsername = remedyUsername;
    }

    public String getRemedyPassword() {
        return remedyPassword;
    }

    public void setRemedyPassword(String remedyPassword) {
        this.remedyPassword = remedyPassword;
    }

    public String getRemedyPort() {
        return remedyPort;
    }

    public void setRemedyPort(String remedyPort) {
        this.remedyPort = remedyPort;
    }

    public String getRemedyMiddleFormInvoice() {
        return remedyMiddleFormInvoice;
    }

    public void setRemedyMiddleFormInvoice(String remedyMiddleFormInvoice) {
        this.remedyMiddleFormInvoice = remedyMiddleFormInvoice;
    }

    public String getRemedyMiddleFormReceipt() {
        return remedyMiddleFormReceipt;
    }

    public void setRemedyMiddleFormReceipt(String remedyMiddleFormReceipt) {
        this.remedyMiddleFormReceipt = remedyMiddleFormReceipt;
    }
    
    
}


