/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.ptm.finance.poi;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import mii.ptm.finance.controller.RemedyController;
import mii.ptm.finance.domain.BankName;
import mii.ptm.finance.domain.Invoice;
import mii.ptm.finance.domain.Receipt;
import mii.ptm.finance.domain.SendPDF;

/**
 *
 * @author mukhlisaj
 */
public class GeneratePDF {

    Document document = new Document(PageSize.A4, 25, 25, 20, 25);

    public void generatePDFInvoice(Invoice invoice) throws FileNotFoundException, DocumentException, BadElementException, IOException {
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("Invoice_" + invoice.PaymentCode + ".pdf"));

        document.open();

        Font fontHeader = FontFactory.getFont(FontFactory.COURIER_BOLD, 11, BaseColor.BLACK);
        Font fontUntukIsi = FontFactory.getFont(FontFactory.COURIER, 9, BaseColor.BLACK);
        Font fontTable = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
        Font fontTableHeader = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);
        Font font_Custom = new Font(Font.FontFamily.COURIER, 8, Font.BOLD | Font.UNDERLINE);

        Image logo = Image.getInstance(Toolkit.getDefaultToolkit().createImage("/usr/share/sscjava/files/" + invoice.CompanyCode + ".png"), Color.WHITE);
//        Image logo = Image.getInstance(Toolkit.getDefaultToolkit().createImage("C:\\Users\\mk.mukhlis.aji\\Documents\\ARnon\\" + invoice.CompanyCode + ".png"), Color.WHITE);

        logo.setPaddingTop(15);
        PdfPTable header = new PdfPTable(2);
        header.setHorizontalAlignment(header.ALIGN_RIGHT);
        header.setWidthPercentage(100);

        header.setWidths(new float[]{80, 20});
        // set defaults
        header.setTotalWidth(500f);
        header.setLockedWidth(true);
        header.getDefaultCell().setFixedHeight(40);
        header.getDefaultCell().setBorderColor(BaseColor.WHITE);
        header.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        header.addCell("");
        header.addCell(logo);
        document.add(header);
        Paragraph paragraph = new Paragraph();

        PdfPCell cell = new PdfPCell();

        PdfPTable segment1 = new PdfPTable(4);
        segment1.setTotalWidth(545f);
        segment1.setWidths(new int[]{56, 2, 17, 25});
        segment1.setLockedWidth(true);
        segment1.getDefaultCell().setBorderColor(BaseColor.WHITE);
        cell = new PdfPCell();

        cell.setBorder(Rectangle.NO_BORDER);
        paragraph = new Paragraph("INVOICE");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontHeader);
        paragraph.setSpacingBefore(5f);
        cell.addElement(paragraph);
        segment1.addCell("");
        segment1.addCell("");
        segment1.addCell(cell);
        segment1.addCell("");

        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(10, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("PAYMENT CODE");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(10, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": " + invoice.PaymentCode);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(10, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Sales Order");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": " + invoice.SalesOrder);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Invoice Number");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": " + invoice.InvoiceNumber);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        cell = new PdfPCell();
        cell.setRowspan(4);
        paragraph = new Paragraph("SSC Order To Cash\nGraha Elnusa 5th Floor\nJl. TB Simatupang Kav. 1 B\nJakarta 12560 Indonesia");
        paragraph.setLeading(9, 0);
        paragraph.setFont(fontTable);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setSpacingBefore(5f);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Date");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": " + invoice.Date);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

//        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Customer");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": " + invoice.Customer);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

//        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Page");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": 1 from 1");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

//        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Request ID");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": " + invoice.RequestID);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        document.add(segment1);

        paragraph = new Paragraph("\n\n");

        document.add(paragraph);

        PdfPTable segment2 = new PdfPTable(6);
        segment2.setTotalWidth(545f);
        segment2.setLockedWidth(true);
        segment2.getDefaultCell().setBorderColor(BaseColor.WHITE);
        segment2.setWidths(new int[]{10, 25, 15, 10, 25, 15});

        cell = new PdfPCell(new Phrase("Bill To :", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.5f);
        segment2.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        segment2.addCell(cell);
        segment2.addCell("");
        cell = new PdfPCell(new Phrase("Ship To :", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.5f);
        segment2.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        segment2.addCell(cell);
        segment2.addCell("");
        cell = new PdfPCell(new Phrase(invoice.CustName + "\n" + invoice.CustAddress, fontTable));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        segment2.addCell(cell);
        segment2.addCell("");
        cell = new PdfPCell(new Phrase(invoice.CustName + "\n" + invoice.CustAddress, fontTable));
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        segment2.addCell(cell);
        segment2.addCell("");

        document.add(segment2);

        PdfPTable segment3 = new PdfPTable(4);
        segment3.setTotalWidth(545f);
        segment3.setWidths(new int[]{5, 55, 15, 20});
        segment3.setLockedWidth(true);
        segment3.setSpacingBefore(10f);

        cell = new PdfPCell(new Phrase("ITEM", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidthBottom(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase("DESCRIPTION", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase("UNIT PRICE", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase("TOTAL", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);

        cell = new PdfPCell();
        cell.setBorderWidthBottom(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase(invoice.DescTransaction, fontTable));
        cell.setFixedHeight(30f);
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase(invoice.AmountBeforePPN + "", fontTable));
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase(invoice.Currency + " " + invoice.AmountBeforePPN, fontTable));
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell();
        cell.setBorderWidthTop(0f);
//        cell.setBorderWidthTop(0f);
//        cell.setBorderWidthRight(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase("PPN 11%", fontTable));
//        cell.setFixedHeight(10f);
//        cell.setBorderWidthTop(0f);
//        cell.setBorderWidthRight(0f);
        cell.setBorderWidthTop(0f);
        cell.setBorderWidthLeft(0f);
        cell.setFixedHeight(15f);
        segment3.addCell(cell);
        cell = new PdfPCell();
//        cell.setBorderWidthTop(0f);
//        cell.setBorderWidthRight(0f);
        cell.setBorderWidthTop(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase(invoice.Currency + " " + invoice.PPN, fontTable));
//        cell.setBorderWidthTop(0f);
        cell.setBorderWidthTop(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);

        document.add(segment3);

        PdfPTable segment3b = new PdfPTable(4);
        segment3b.setWidths(new int[]{5, 55, 15, 20});
        segment3b.setTotalWidth(545f);
        segment3b.setLockedWidth(true);
        segment3b.setSpacingBefore(5f);

        cell = new PdfPCell();
        cell.setColspan(2);
        cell.setBorderWidth(0f);
        segment3b.addCell(cell);

        cell = new PdfPCell(new Phrase("TOTAL", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        segment3b.addCell(cell);

        cell = new PdfPCell(new Phrase(invoice.Currency + " " + invoice.AmountAfterPPN, fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        segment3b.addCell(cell);

        document.add(segment3b);

        PdfPTable segment3c = new PdfPTable(1);
        segment3c.setTotalWidth(545f);
        segment3c.setSpacingBefore(8f);
        segment3c.setLockedWidth(true);

        cell = new PdfPCell(new Phrase("Terbilang", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        segment3c.addCell(cell);
        cell = new PdfPCell(new Phrase(invoice.Terbilang, fontTable));
        cell.setPaddingLeft(5f);
        cell.setFixedHeight(20f);
        segment3c.addCell(cell);

        document.add(segment3c);

        PdfPTable segment4 = new PdfPTable(2);
        segment4.setTotalWidth(545f);
        segment4.setLockedWidth(true);
        segment4.setWidths(new int[]{63, 37});
        segment4.setSpacingBefore(10f);

        cell = new PdfPCell(new Phrase("Keterangan Tambahan dan Perintah Khusus", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        segment4.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        segment4.addCell(cell);

        Phrase notes = new Phrase();
        cell = new PdfPCell();
        cell.setFixedHeight(180f);
        notes.setLeading(12, 0);
        notes.add(new Chunk("1. This computer generated invoice is issued by PT. Pertamina (Persero), Head Office, and required no signature", fontTable));
        notes.add(new Chunk("\n2. Apabila customer melakukan pemotongan PPH (Pajak Penghasilan), maka customer wajib mengirimkan Bukti Potong PPH Paling lambat 7 hari setelah dilakukan pembayaran dan dikirimkan ke alamat email", fontTable));
        notes.add(new Chunk(" ssc.collection@pertamina.com", FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE, 8, BaseColor.BLACK)));
        notes.add(new Chunk(" dengan subject email (BPPPH_CustomerNo_Invoice No)", fontTable));
        notes.add(new Chunk("\n3. Terms Of Payment : " + invoice.TermOfPayment, fontTable));
        notes.add(new Chunk("\n4. Payment, please be transferred to a Virtual Account Pertamina", fontTable));

        cell.addElement(notes);

        PdfPTable Bank = new PdfPTable(3);
        Bank.setWidths(new int[]{25, 35, 40});
        Bank.setTotalWidth(325f);
        Bank.setLockedWidth(true);
        Bank.setSpacingBefore(5f);
        Bank.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        Bank.addCell(new Phrase("Bank Name", font_Custom));
        Bank.addCell(new Phrase("Bank Account", font_Custom));
        Bank.addCell("");
        Bank.addCell(new Phrase(invoice.BankName, fontTable));
        Bank.addCell(new Phrase(invoice.PaymentCode, fontTableHeader));
        Bank.addCell(new Phrase("PT. PERTAMINA PATRA NIAGA", fontTable));

        cell.addElement(Bank);
        segment4.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);

        PdfPTable signature = new PdfPTable(1);
        signature.setTotalWidth(115f);
        signature.setLockedWidth(true);
        signature.setSpacingBefore(30f);
        signature.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        Image sign = Image.getInstance(Toolkit.getDefaultToolkit().createImage("/usr/share/sscjava/files/" + invoice.EsignName + ".jpeg"), Color.WHITE);
//        Image sign = Image.getInstance(Toolkit.getDefaultToolkit().createImage("C:\\Users\\mk.mukhlis.aji\\Documents\\ARnon\\" + invoice.EsignName + ".jpeg"), Color.WHITE);
        sign.scaleAbsolute(90, 65);
        sign.setAlignment(Element.ALIGN_CENTER);

        PdfPCell cell1 = new PdfPCell(new Phrase("PERTAMINA", fontTableHeader));
        cell1.setFixedHeight(20f);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);
        signature.addCell(cell1);

        cell1 = new PdfPCell(new Phrase("SSC Order to Cash", fontTableHeader));
        cell1.setFixedHeight(20f);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);
        signature.addCell(cell1);

        cell1 = new PdfPCell();
        cell1.addElement(sign);
        cell1.setBorder(Rectangle.NO_BORDER);
        signature.addCell(cell1);

        cell1 = new PdfPCell(new Phrase(invoice.EsignName, fontTable));
        cell1.setFixedHeight(15f);
        cell.setPaddingTop(8f);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);
        signature.addCell(cell1);

        cell1 = new PdfPCell(new Phrase("Manager Order to Cash", fontTableHeader));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);
        signature.addCell(cell1);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);

        cell.addElement(signature);

        segment4.addCell(cell);
        document.add(segment4);

        paragraph = new Paragraph();
        paragraph.setLeading(9, 0);
        paragraph.setFont(fontTable);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setSpacingBefore(5f);
        paragraph.add(new Chunk("If you have any questions about this invoice,\n"));
        paragraph.add(new Chunk("please contact to :\n"));
        paragraph.add(new Chunk("SSC Collection\n", new Font(Font.FontFamily.COURIER, 8, Font.ITALIC)));
        paragraph.add(new Chunk("email : collection.ssc@pertamina.com\n", new Font(Font.FontFamily.COURIER, 8, Font.ITALIC)));
        paragraph.add(new Chunk("Telp : 1500234-3-1\n", new Font(Font.FontFamily.COURIER, 8, Font.ITALIC)));

        document.add(paragraph);
        document.close();

        //sending pdf to srm:workinfo
        SendPDF sendPDF = new SendPDF();
        sendPDF.namafile = "Invoice_" + invoice.PaymentCode + ".pdf";
        sendPDF.srInstanceId = invoice.InstanceID;
        sendPDF.requstNumber = invoice.RequestID;
        RemedyController remedyController = new RemedyController();
        remedyController.sendPdftoWorkInfo(sendPDF);

    }

    public void generatePDFTandaTerima(Receipt receipt, List<BankName> bankNames) throws FileNotFoundException, DocumentException, BadElementException, IOException {
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("Receipt_" + receipt.Paymentcode + ".pdf"));

        document.open();

        Font fontHeader = FontFactory.getFont(FontFactory.COURIER_BOLD, 11, BaseColor.BLACK);
        Font fontUntukIsi = FontFactory.getFont(FontFactory.COURIER, 9, BaseColor.BLACK);
        Font fontTable = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
        Font fontTableHeader = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, BaseColor.BLACK);
        Font font_Custom = new Font(Font.FontFamily.COURIER, 8, Font.BOLD | Font.UNDERLINE);

        Image logo = Image.getInstance(Toolkit.getDefaultToolkit().createImage("/usr/share/sscjava/files/" + receipt.CompanyCode + ".png"), Color.WHITE);
//        Image logo = Image.getInstance(Toolkit.getDefaultToolkit().createImage("C:\\Users\\mk.mukhlis.aji\\Documents\\ARnon\\" + receipt.CompanyCode + ".png"), Color.WHITE);

        logo.setPaddingTop(15);
        PdfPTable header = new PdfPTable(2);
        header.setHorizontalAlignment(header.ALIGN_RIGHT);
        header.setWidthPercentage(100);

        header.setWidths(new float[]{80, 20});
        // set defaults
        header.setTotalWidth(500f);
        header.setLockedWidth(true);
        header.getDefaultCell().setFixedHeight(40);
        header.getDefaultCell().setBorderColor(BaseColor.WHITE);
        header.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        header.addCell("");
        header.addCell(logo);
        document.add(header);
        Paragraph paragraph = new Paragraph();

        PdfPCell cell = new PdfPCell();

        PdfPTable segment1 = new PdfPTable(4);
        segment1.setTotalWidth(545f);
        segment1.setWidths(new int[]{56, 2, 17, 25});
        segment1.setLockedWidth(true);
        segment1.getDefaultCell().setBorderColor(BaseColor.WHITE);
        cell = new PdfPCell();

        cell.setBorder(Rectangle.NO_BORDER);
        paragraph = new Paragraph("TANDA TERIMA");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontHeader);
        paragraph.setSpacingBefore(5f);
        cell.addElement(paragraph);
        segment1.addCell("");
        segment1.addCell("");
        segment1.addCell(cell);
        segment1.addCell("");

        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(10, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("PAYMENT CODE");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(10, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": " + receipt.Paymentcode);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(10, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Sales Order");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": -");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Invoice Number");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": -");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        cell = new PdfPCell();
        cell.setRowspan(4);
        paragraph = new Paragraph("SSC Order To Cash\nGraha Elnusa 5th Floor\nJl. TB Simatupang Kav. 1 B\nJakarta 12560 Indonesia");
        paragraph.setLeading(9, 0);
        paragraph.setFont(fontTable);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setSpacingBefore(5f);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Date");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": " + receipt.Date);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

//        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Customer");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": " + receipt.Customer);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

//        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Page");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": 1 from 1");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

//        segment1.addCell("");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph("Request ID");
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setLeading(7, 0);
        paragraph = new Paragraph(": " + receipt.RequestID);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setFont(fontTable);
        paragraph.setSpacingBefore(5f);
        paragraph.setLeading(6, 0);
        cell.addElement(paragraph);
        segment1.addCell(cell);

        document.add(segment1);

        paragraph = new Paragraph("\n\n");

        document.add(paragraph);

        PdfPTable segment2 = new PdfPTable(6);
        segment2.setTotalWidth(545f);
        segment2.setLockedWidth(true);
        segment2.getDefaultCell().setBorderColor(BaseColor.WHITE);
        segment2.setWidths(new int[]{10, 25, 15, 10, 25, 15});

        cell = new PdfPCell(new Phrase("Bill To :", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.5f);
        segment2.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        segment2.addCell(cell);
        segment2.addCell("");
        cell = new PdfPCell(new Phrase("Ship To :", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.5f);
        segment2.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        segment2.addCell(cell);
        segment2.addCell("");
        cell = new PdfPCell(new Phrase(receipt.CustName + "\n" + receipt.CustAddress, fontTable));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        segment2.addCell(cell);
        segment2.addCell("");
        cell = new PdfPCell(new Phrase(receipt.CustName + "\n" + receipt.CustAddress, fontTable));
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        segment2.addCell(cell);
        segment2.addCell("");

        document.add(segment2);

        PdfPTable segment3 = new PdfPTable(4);
        segment3.setTotalWidth(545f);
        segment3.setWidths(new int[]{5, 55, 15, 20});
        segment3.setLockedWidth(true);
        segment3.setSpacingBefore(10f);

        cell = new PdfPCell(new Phrase("ITEM", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidthBottom(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase("DESCRIPTION", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase("UNIT PRICE", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase("TOTAL", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);

        cell = new PdfPCell();
        cell.setBorderWidthBottom(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase(receipt.Description, fontTable));
        cell.setFixedHeight(30f);
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase(receipt.Amount + "", fontTable));
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase(receipt.Currency + " " + receipt.Amount, fontTable));
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell();
        cell.setBorderWidthTop(0f);
//        cell.setBorderWidthTop(0f);
//        cell.setBorderWidthRight(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase("", fontTable));
//        cell.setFixedHeight(10f);
//        cell.setBorderWidthTop(0f);
//        cell.setBorderWidthRight(0f);
        cell.setBorderWidthTop(0f);
        cell.setBorderWidthLeft(0f);
        cell.setFixedHeight(15f);
        segment3.addCell(cell);
        cell = new PdfPCell();
//        cell.setBorderWidthTop(0f);
//        cell.setBorderWidthRight(0f);
        cell.setBorderWidthTop(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);
        cell = new PdfPCell(new Phrase("", fontTable));
//        cell.setBorderWidthTop(0f);
        cell.setBorderWidthTop(0f);
        cell.setBorderWidthLeft(0f);
        segment3.addCell(cell);

        document.add(segment3);

        PdfPTable segment3b = new PdfPTable(4);
        segment3b.setWidths(new int[]{5, 55, 15, 20});
        segment3b.setTotalWidth(545f);
        segment3b.setLockedWidth(true);
        segment3b.setSpacingBefore(5f);

        cell = new PdfPCell();
        cell.setColspan(2);
        cell.setBorderWidth(0f);
        segment3b.addCell(cell);

        cell = new PdfPCell(new Phrase("TOTAL", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        segment3b.addCell(cell);

        cell = new PdfPCell(new Phrase(receipt.Currency + " " + receipt.Amount, fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        segment3b.addCell(cell);

        document.add(segment3b);

        PdfPTable segment3c = new PdfPTable(1);
        segment3c.setTotalWidth(545f);
        segment3c.setSpacingBefore(8f);
        segment3c.setLockedWidth(true);

        cell = new PdfPCell(new Phrase("Terbilang", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        segment3c.addCell(cell);
        cell = new PdfPCell(new Phrase(receipt.Terbilang, fontTable));
        cell.setPaddingLeft(5f);
        cell.setFixedHeight(20f);
        segment3c.addCell(cell);

        document.add(segment3c);

        PdfPTable segment4 = new PdfPTable(2);
        segment4.setTotalWidth(545f);
        segment4.setLockedWidth(true);
        segment4.setWidths(new int[]{63, 37});
        segment4.setSpacingBefore(10f);

        cell = new PdfPCell(new Phrase("Keterangan Tambahan dan Perintah Khusus", fontTableHeader));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        segment4.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        segment4.addCell(cell);

        Phrase notes = new Phrase();
        cell = new PdfPCell();
        cell.setFixedHeight(180f);
        notes.setLeading(12, 0);
        notes.add(new Chunk("1. This computer generated invoice is issued by PT. Pertamina (Persero), Head Office, and required no signature\n", fontTable));
        notes.add(new Chunk("\n2. Payment, please be transferred to a Virtual Account Pertamina", fontTable));

        cell.addElement(notes);

        PdfPTable Bank = new PdfPTable(3);
        Bank.setWidths(new int[]{25, 35, 40});
        Bank.setTotalWidth(325f);
        Bank.setLockedWidth(true);
        Bank.setSpacingBefore(5f);
        Bank.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        Bank.addCell(new Phrase("Bank Name", font_Custom));
        Bank.addCell(new Phrase("Bank Account", font_Custom));
        Bank.addCell("");

        int bankLooping = 0;
        while (bankNames.size() > bankLooping) {
            BankName bankName = bankNames.get(bankLooping);
            Bank.addCell(new Phrase(bankName.BankName, fontTable));
            Bank.addCell(new Phrase(bankName.VirtualAccount, fontTableHeader));
            Bank.addCell(new Phrase("PT. PERTAMINA PATRA NIAGA", fontTable));
            bankLooping++;
        }

        cell.addElement(Bank);
        segment4.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);

        PdfPTable signature = new PdfPTable(1);
        signature.setTotalWidth(115f);
        signature.setLockedWidth(true);
        signature.setSpacingBefore(30f);
        signature.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        Image sign = Image.getInstance(Toolkit.getDefaultToolkit().createImage("/usr/share/sscjava/files/" + receipt.EsignName + ".jpeg"), Color.WHITE);
//        Image sign = Image.getInstance(Toolkit.getDefaultToolkit().createImage("C:\\Users\\mk.mukhlis.aji\\Documents\\ARnon\\" + receipt.EsignName + ".jpeg"), Color.WHITE);
        sign.scaleAbsolute(90, 65);
        sign.setAlignment(Element.ALIGN_CENTER);

        PdfPCell cell1 = new PdfPCell(new Phrase("PERTAMINA", fontTableHeader));
        cell1.setFixedHeight(20f);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);
        signature.addCell(cell1);

        cell1 = new PdfPCell(new Phrase("SSC Order to Cash", fontTableHeader));
        cell1.setFixedHeight(20f);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);
        signature.addCell(cell1);

        cell1 = new PdfPCell();
        cell1.addElement(sign);
        cell1.setBorder(Rectangle.NO_BORDER);
        signature.addCell(cell1);

        cell1 = new PdfPCell(new Phrase(receipt.EsignName, fontTable));
        cell1.setFixedHeight(15f);
        cell.setPaddingTop(8f);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);
        signature.addCell(cell1);

        cell1 = new PdfPCell(new Phrase("Manager Order to Cash", fontTableHeader));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);
        signature.addCell(cell1);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);

        cell.addElement(signature);

        segment4.addCell(cell);
        document.add(segment4);

        paragraph = new Paragraph();
        paragraph.setLeading(9, 0);
        paragraph.setFont(fontTable);
        paragraph.setAlignment(paragraph.ALIGN_LEFT);
        paragraph.setSpacingBefore(5f);
        paragraph.add(new Chunk("If you have any questions about this invoice,\n"));
        paragraph.add(new Chunk("please contact to :\n"));
        paragraph.add(new Chunk("SSC Collection\n", new Font(Font.FontFamily.COURIER, 8, Font.ITALIC)));
        paragraph.add(new Chunk("email : collection.ssc@pertamina.com\n", new Font(Font.FontFamily.COURIER, 8, Font.ITALIC)));
        paragraph.add(new Chunk("Telp : 1500234-3-1\n", new Font(Font.FontFamily.COURIER, 8, Font.ITALIC)));

        document.add(paragraph);
        document.close();

        //sending pdf to srm:workinfo
        SendPDF sendPDF = new SendPDF();
        sendPDF.namafile = "Receipt_" + receipt.Paymentcode + ".pdf";
        sendPDF.srInstanceId = receipt.InstanceID;
        sendPDF.requstNumber = receipt.RequestID;
        RemedyController remedyController = new RemedyController();
        remedyController.sendPdftoWorkInfo(sendPDF);

    }

}
