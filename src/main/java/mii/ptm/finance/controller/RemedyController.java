/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.ptm.finance.controller;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.AttachmentValue;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.EntryListInfo;
import com.bmc.arsys.api.Value;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import mii.ptm.finance.domain.BankName;
import mii.ptm.finance.domain.ConfigFile;
import mii.ptm.finance.domain.ConfigurationValue;
import mii.ptm.finance.domain.Invoice;
import mii.ptm.finance.domain.Receipt;
import mii.ptm.finance.domain.RemedyAPI;
import mii.ptm.finance.domain.SendPDF;
import mii.ptm.finance.poi.GeneratePDF;
import mii.ptm.finance.remedy.RemedyConnection;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author mukhlisaj
 */
@Controller
public class RemedyController {

    private static Logger logger = Logger.getLogger("Remedy Controller");

    @GetMapping("/welcome")
    public String welcome(Model model) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigFile.class);
        ConfigurationValue configValue = context.getBean(ConfigurationValue.class);

        logger.info("Trying to connect " + configValue.getRemedyUsername() + " to server " + configValue.getRemedyServer());
        //Test Connection
        RemedyConnection remedyConnection = new RemedyConnection();
        ARServerUser serverUser = remedyConnection.buildconnection(configValue);

        model.addAttribute("result", "Connected to " + configValue.getRemedyServer());
        model.addAttribute("result1", "Status " + serverUser.getUser());

        return "welcome";
    }

    @GetMapping("/printInvoice")
    public String printPDF() throws DocumentException, BadElementException, IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigFile.class);
        ConfigurationValue configValue = context.getBean(ConfigurationValue.class);

        RemedyConnection remedyConnection = new RemedyConnection();
        ARServerUser serverUser = remedyConnection.buildconnection(configValue);

        RemedyAPI remedyAPI = new RemedyAPI();
        List<EntryListInfo> elis = remedyAPI.getRemedyRecordByQuery(serverUser, configValue.getRemedyMiddleFormInvoice(), "'Status' = \"0\"");

        try {
            for (EntryListInfo listInfo : elis) {
                Invoice invoice = new Invoice();
                Entry requestRecord = serverUser.getEntry(configValue.getRemedyMiddleFormInvoice(), listInfo.getEntryID(), null);
                requestRecord.put(7, new Value("2"));
                serverUser.setEntry(configValue.getRemedyMiddleFormInvoice(), requestRecord.getEntryId(), requestRecord, null, 0);

                invoice.PaymentCode = getValueFromRemedy(requestRecord, 536870918);
                logger.info("payment code = " + invoice.PaymentCode);
                invoice.SalesOrder = getValueFromRemedy(requestRecord, 536870919);
                invoice.InvoiceNumber = getValueFromRemedy(requestRecord, 536870923);
                invoice.Date = getValueFromRemedy(requestRecord, 536870967);
                TimeZone indoZone = TimeZone.getTimeZone("Asia/Jakarta");
                SimpleDateFormat monthFormat = new SimpleDateFormat("dd/MM/yyyy");
                monthFormat.setTimeZone(indoZone);
                invoice.Date = dateFormat(invoice.Date, monthFormat);

                DecimalFormat df = new DecimalFormat(",###");
                invoice.Customer = getValueFromRemedy(requestRecord, 536870937);
                invoice.RequestID = getValueFromRemedy(requestRecord, 536870921);
                invoice.CustName = getValueFromRemedy(requestRecord, 536870931);
                invoice.CustAddress = getValueFromRemedy(requestRecord, 536870932);
                invoice.DescTransaction = getValueFromRemedy(requestRecord, 536870933);
                long sebelumPPN = Long.valueOf(getValueFromRemedy(requestRecord, 536870934));
                int PPN = Integer.valueOf(getValueFromRemedy(requestRecord, 536870963));
                invoice.Currency = getValueFromRemedy(requestRecord, 536870935);

//                NumberFormat kurensiUS = NumberFormat.getCurrencyInstance(Locale.US);
                long setelahPPN = 0l;
                if (PPN == 0) {
                    long amountPPN = (sebelumPPN * 11) / 100;
                    setelahPPN = sebelumPPN + amountPPN;
//                    if ("USD".equals(invoice.Currency)) {
//
//                        invoice.AmountBeforePPN = kurensiUS.format(sebelumPPN);
//                        invoice.AmountAfterPPN = kurensiUS.format(setelahPPN);
//                    } else {

                    invoice.AmountAfterPPN = df.format(setelahPPN);
                    invoice.PPN = df.format(amountPPN);
//                    }
                } else {
                    setelahPPN = sebelumPPN;
                    invoice.PPN = "-";
//                    if ("USD".equals(invoice.Currency)) {
//                        invoice.AmountAfterPPN = kurensiUS.format(sebelumPPN);
//                    } else {
                    invoice.AmountAfterPPN = df.format(sebelumPPN);
//                    }
                }
                invoice.AmountBeforePPN = df.format(sebelumPPN);
                invoice.TermOfPayment = getValueFromRemedy(requestRecord, 536870920);
                invoice.CompanyCode = getValueFromRemedy(requestRecord, 536870941);
                invoice.EsignName = getValueFromRemedy(requestRecord, 536870942);
                invoice.BankName = getValueFromRemedy(requestRecord, 536870914);
                invoice.InstanceID = getValueFromRemedy(requestRecord, 536870939);
                invoice.VirtualAccount = invoice.PaymentCode;
                String Uang = " Rupiah";
                if ("USD".equals(invoice.Currency)) {
                    Uang = " US Dollar";

                }
                String angkaTerbilang = angkaToTerbilang(setelahPPN) + Uang;
                angkaTerbilang = angkaTerbilang.replace("  ", " ");
                invoice.Terbilang = angkaTerbilang;

                requestRecord.put(7, new Value("1"));
                serverUser.setEntry(configValue.getRemedyMiddleFormInvoice(), requestRecord.getEntryId(), requestRecord, null, 0);
                logger.info("succeed");

                GeneratePDF generatePDF = new GeneratePDF();
                generatePDF.generatePDFInvoice(invoice);
            }
        } catch (ARException | IOException e) {

            logger.info("error : " + e);
        }

        return "result";

    }

    @GetMapping("/printReceipt")
    public String prinfTandaTerima() throws DocumentException, BadElementException, IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigFile.class);
        ConfigurationValue configValue = context.getBean(ConfigurationValue.class);

        RemedyConnection remedyConnection = new RemedyConnection();
        ARServerUser serverUser = remedyConnection.buildconnection(configValue);

        RemedyAPI remedyAPI = new RemedyAPI();
        List<EntryListInfo> elis = remedyAPI.getRemedyRecordByQuery(serverUser, configValue.getRemedyMiddleFormReceipt(), "'Status' = \"0\"");

        try {
            for (EntryListInfo listInfo : elis) {
                Receipt receipt = new Receipt();
                Entry requestRecord = serverUser.getEntry(configValue.getRemedyMiddleFormReceipt(), listInfo.getEntryID(), null);
                requestRecord.put(7, new Value("2"));
                serverUser.setEntry(configValue.getRemedyMiddleFormReceipt(), requestRecord.getEntryId(), requestRecord, null, 0);

                receipt.Paymentcode = getValueFromRemedy(requestRecord, 536870918);
                logger.info("paymentCode = " + receipt.Paymentcode);
                receipt.Date = getValueFromRemedy(requestRecord, 536870967);

                TimeZone indoZone = TimeZone.getTimeZone("Asia/Jakarta");
                SimpleDateFormat monthFormat = new SimpleDateFormat("dd/MM/yyyy");
                monthFormat.setTimeZone(indoZone);
                receipt.Date = dateFormat(receipt.Date, monthFormat);

                receipt.Customer = getValueFromRemedy(requestRecord, 536870937);
                receipt.RequestID = getValueFromRemedy(requestRecord, 536870921);
                receipt.CustName = getValueFromRemedy(requestRecord, 536870931);
                receipt.CustAddress = getValueFromRemedy(requestRecord, 536870932);
                receipt.Description = getValueFromRemedy(requestRecord, 536870933);
                long amountsebelumPPN = Long.valueOf(getValueFromRemedy(requestRecord, 536870934));
                DecimalFormat df = new DecimalFormat(",###");
                receipt.Amount = df.format(amountsebelumPPN);
                receipt.Currency = getValueFromRemedy(requestRecord, 536870935);
                receipt.InstanceID = getValueFromRemedy(requestRecord, 536870939);
                receipt.CompanyCode = getValueFromRemedy(requestRecord, 536870941);
                receipt.JenisTransaksi = getValueFromRemedy(requestRecord, 536870938);
                receipt.Kombinasi = getValueFromRemedy(requestRecord, 536870968);
                receipt.EsignName = getValueFromRemedy(requestRecord, 536870942);
                receipt.Currency = "IDR";
                String angkaTerbilang = angkaToTerbilang(amountsebelumPPN) + " Rupiah";
                angkaTerbilang = angkaTerbilang.replace("  ", " ");
                receipt.Terbilang = angkaTerbilang;

                String formBank = "PTM:SSC:FIN:ManualBilling:BankName";

                List<EntryListInfo> listBank = remedyAPI.getRemedyRecordByQuery(serverUser, formBank, "'Company' = \"" + receipt.CompanyCode + "\" AND 'Modul' = \"TANDA TERIMA / NON SD ATAU FI\"");
                logger.info("listBank : " + listBank);
                BankName bankName = new BankName();
                List<BankName> bankNames = new ArrayList<>();
                for (EntryListInfo bankinfo : listBank) {
                    Entry bankRecord = serverUser.getEntry(formBank, bankinfo.getEntryID(), null);
                    bankName = new BankName();
                    bankName.BankName = getValueFromRemedy(bankRecord, 536870914);
                    bankName.Biller = getValueFromRemedy(bankRecord, 536870915);
                    if (receipt.Customer.contains("OT9999")) {
                        
                    bankName.VirtualAccount = bankName.Biller + receipt.Kombinasi + "009999";
                    }else{
                    
                    bankName.VirtualAccount = bankName.Biller + receipt.Kombinasi + receipt.Customer.substring(4, 10);
                }
                    bankNames.add(bankName);

                }

                requestRecord.put(7, new Value("1"));
                serverUser.setEntry(configValue.getRemedyMiddleFormReceipt(), requestRecord.getEntryId(), requestRecord, null, 0);
                logger.info("succeed print receipt");
                GeneratePDF generatePDF = new GeneratePDF();
                generatePDF.generatePDFTandaTerima(receipt, bankNames);
            }
        } catch (ARException | IOException e) {

            logger.info("error : " + e);
        }
        return "result";
    }

    public String getValueFromRemedy(Entry requestRecord, Object fieldID) {
        if (requestRecord.get(fieldID).getValue() == null) {
            return "";
        }

        return requestRecord.get(fieldID).getValue().toString();
    }

    public static String angkaToTerbilang(Long angka) {
        String[] huruf = {"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};

        if (angka
                < 12) {
            return huruf[angka.intValue()];
        }
        if (angka >= 12 && angka
                <= 19) {
            return huruf[angka.intValue() % 10] + " Belas";
        }
        if (angka >= 20 && angka
                <= 99) {
            return angkaToTerbilang(angka / 10) + " Puluh " + huruf[angka.intValue() % 10];
        }
        if (angka >= 100 && angka
                <= 199) {
            return "Seratus " + angkaToTerbilang(angka % 100);
        }
        if (angka >= 200 && angka
                <= 999) {
            return angkaToTerbilang(angka / 100) + " Ratus " + angkaToTerbilang(angka % 100);
        }
        if (angka >= 1000 && angka
                <= 1999) {
            return "Seribu " + angkaToTerbilang(angka % 1000);
        }
        if (angka >= 2000 && angka
                <= 999999) {
            return angkaToTerbilang(angka / 1000) + " Ribu " + angkaToTerbilang(angka % 1000);
        }
        if (angka >= 1000000 && angka
                <= 999999999) {
            return angkaToTerbilang(angka / 1000000) + " Juta " + angkaToTerbilang(angka % 1000000);
        }
        if (angka >= 1000000000 && angka
                <= 999999999999L) {
            return angkaToTerbilang(angka / 1000000000) + " Milyar " + angkaToTerbilang(angka % 1000000000);
        }
        if (angka >= 1000000000000L && angka
                <= 999999999999999L) {
            return angkaToTerbilang(angka / 1000000000000L) + " Triliun " + angkaToTerbilang(angka % 1000000000000L);
        }
        if (angka >= 1000000000000000L && angka
                <= 999999999999999999L) {
            return angkaToTerbilang(angka / 1000000000000000L) + " Quadrilyun " + angkaToTerbilang(angka % 1000000000000000L);
        }

        return "";
    }

    private static String dateFormat(String date, SimpleDateFormat dateFormat) {

        if (date.isEmpty()) {
            return "";
        }
        String time = date.substring(11, 21);
        long longParsedTime = Long.parseLong(time);
        Date dateTime = new Date(longParsedTime * 1000);

        return dateFormat.format(dateTime);
    }

    public void sendPdftoWorkInfo(SendPDF sendPDF) throws IOException {
        //logger.info("++++++++++++++++++++++++ searching get Pull Data +++++++++++++++++++++++++++++++++");
        //Get configuration value from sscconfig.properties
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigFile.class);
        ConfigurationValue configValue = context.getBean(ConfigurationValue.class);

        //SRT connection setting up
        RemedyConnection remedyConnection = new RemedyConnection();
        ARServerUser remedySession = remedyConnection.buildconnection(configValue);;

        String workInfoFormName = "SRM:WorkInfo";
        Entry workInfoEntry = new Entry();

        AttachmentValue attachment = new AttachmentValue();
        attachment.setName(sendPDF.namafile);

        attachment.setValue(sendPDF.namafile);

        //attachment1 10001831
        workInfoEntry.put(10001831, new Value(attachment));
        workInfoEntry.put(10001821, new Value(sendPDF.srInstanceId));//SRInstanceID 
        workInfoEntry.put(1000000829, new Value(sendPDF.requstNumber));//Request Number 
        workInfoEntry.put(303449900, new Value("General Information"));//WorkInfoTypeSelection 
        workInfoEntry.put(10001952, new Value("Public"));//View Access  
        //Status
        workInfoEntry.put(10006800, new Value(sendPDF.namafile));//Summary 
        workInfoEntry.put(10000101, new Value(sendPDF.namafile)); //Notes 
        //SR_RequestNumber 
        //SRID
        workInfoEntry.put(10001953, new Value("Yes"));//Secure Log 
        workInfoEntry.put(10001950, new Value("General Information"));//WorkInfoType 
        //WorkInfoSubmitDate

        try {
            String resultAttachment = remedySession.createEntry(workInfoFormName, workInfoEntry);
        } catch (ARException e) {
            // TODO Auto-generated catch block
            logger.info("entry failed" + e.toString());
        }
        logger.info("OK");
    }

}
