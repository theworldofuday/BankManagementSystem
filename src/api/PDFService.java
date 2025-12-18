package api;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import models.*;
import utils.DataStorage;
import java.io.*;
import java.time.LocalDateTime;
import java. time.format.DateTimeFormatter;
import java.util.List;

public class PDFService {
    
    private static final String BANK_NAME = "SECURE BANK";
    private static final String OUTPUT_FOLDER = "generated/";
    private static final DateTimeFormatter formatter = DateTimeFormatter. ofPattern("dd-MM-yyyy HH:mm:ss");
    
    // Colors
    private static final BaseColor HEADER_COLOR = new BaseColor(46, 125, 50); // Green
    private static final BaseColor LIGHT_GRAY = new BaseColor(245, 245, 245);
    
    static {
        // Create output folder if not exists
        new File(OUTPUT_FOLDER).mkdirs();
    }
    
    // Generate Account Statement PDF
    public static String generateAccountStatement(String accountNumber) {
        Account account = DataStorage.getAccount(accountNumber);
        if (account == null) {
            System.out.println("⚠ Account not found");
            return null;
        }
        
        User user = DataStorage.getUser(account.getUserId());
        List<Transaction> transactions = DataStorage. getTransactionsByAccount(accountNumber);
        
        String fileName = OUTPUT_FOLDER + "Statement_" + accountNumber + "_" + 
                          System.currentTimeMillis() + ".pdf";
        
        try {
            Document document = new Document(PageSize. A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            
            // Bank Header
            addBankHeader(document);
            
            // Title
            Font titleFont = new Font(Font. FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
            Paragraph title = new Paragraph("ACCOUNT STATEMENT", titleFont);
            title.setAlignment(Element. ALIGN_CENTER);
            title. setSpacingAfter(20);
            document.add(title);
            
            // Account Information Table
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(10);
            infoTable.setSpacingAfter(20);
            
            addInfoRow(infoTable, "Account Holder", user.getName());
            addInfoRow(infoTable, "Account Number", accountNumber);
            addInfoRow(infoTable, "Account Type", account.getAccountType());
            addInfoRow(infoTable, "Current Balance", "₹" + String.format("%.2f", account.getBalance()));
            addInfoRow(infoTable, "Account Status", account.isActive() ? "Active" : "Inactive");
            addInfoRow(infoTable, "Statement Date", LocalDateTime.now().format(formatter));
            
            document.add(infoTable);
            
            // Transaction History
            Font sectionFont = new Font(Font. FontFamily.HELVETICA, 14, Font.BOLD, HEADER_COLOR);
            Paragraph transTitle = new Paragraph("Transaction History", sectionFont);
            transTitle.setSpacingAfter(10);
            document.add(transTitle);
            
            if (transactions.isEmpty()) {
                document.add(new Paragraph("No transactions found. "));
            } else {
                PdfPTable transTable = new PdfPTable(5);
                transTable.setWidthPercentage(100);
                transTable.setWidths(new float[]{2, 1. 5f, 1.5f, 1.5f, 2});
                
                // Header
                addTableHeader(transTable, "Transaction ID");
                addTableHeader(transTable, "Type");
                addTableHeader(transTable, "Amount");
                addTableHeader(transTable, "Balance");
                addTableHeader(transTable, "Date");
                
                // Data rows
                for (Transaction t : transactions) {
                    addTableCell(transTable, t.getTransactionId());
                    addTableCell(transTable, t.getTransactionType());
                    addTableCell(transTable, "₹" + String.format("%.2f", t.getAmount()));
                    addTableCell(transTable, "₹" + String.format("%.2f", t.getBalanceAfter()));
                    addTableCell(transTable, t.getTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                }
                
                document.add(transTable);
            }
            
            // Footer
            addFooter(document);
            
            document.close();
            
            System.out.println("✓ Statement PDF generated:  " + fileName);
            return fileName;
            
        } catch (Exception e) {
            System. out.println("⚠ Error generating PDF: " + e.getMessage());
            return null;
        }
    }
    
    // Generate Transaction Receipt PDF
    public static String generateTransactionReceipt(Transaction transaction, User user, Account account) {
        String fileName = OUTPUT_FOLDER + "Receipt_" + transaction.getTransactionId() + ".pdf";
        
        try {
            Document document = new Document(PageSize.A5, 30, 30, 30, 30);
            PdfWriter. getInstance(document, new FileOutputStream(fileName));
            document.open();
            
            // Bank Header
            addBankHeader(document);
            
            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("TRANSACTION RECEIPT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(15);
            document.add(title);
            
            // Receipt Details
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            
            addInfoRow(table, "Transaction ID", transaction.getTransactionId());
            addInfoRow(table, "Date & Time", transaction.getTimestamp().format(formatter));
            addInfoRow(table, "Account Holder", user.getName());
            addInfoRow(table, "Account Number", account.getAccountNumber());
            addInfoRow(table, "Transaction Type", transaction.getTransactionType());
            addInfoRow(table, "Amount", "₹" + String.format("%.2f", transaction.getAmount()));
            addInfoRow(table, "Balance After", "₹" + String. format("%.2f", transaction.getBalanceAfter()));
            addInfoRow(table, "Description", transaction.getDescription());
            
            document.add(table);
            
            // Status
            Paragraph status = new Paragraph("\n✓ TRANSACTION SUCCESSFUL", 
                new Font(Font.FontFamily. HELVETICA, 12, Font. BOLD, HEADER_COLOR));
            status. setAlignment(Element.ALIGN_CENTER);
            document.add(status);
            
            // Footer
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor. GRAY);
            Paragraph footer = new Paragraph("\nThis is a computer-generated receipt and does not require a signature.", footerFont);
            footer.setAlignment(Element. ALIGN_CENTER);
            document. add(footer);
            
            document.close();
            
            System.out.println("✓ Receipt PDF generated: " + fileName);
            return fileName;
            
        } catch (Exception e) {
            System.out.println("⚠ Error generating PDF: " + e.getMessage());
            return null;
        }
    }
    
    // Helper methods
    private static void addBankHeader(Document document) throws DocumentException {
        Font bankFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, HEADER_COLOR);
        Paragraph bankName = new Paragraph("🏦 " + BANK_NAME, bankFont);
        bankName.setAlignment(Element.ALIGN_CENTER);
        document.add(bankName);
        
        Font taglineFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
        Paragraph tagline = new Paragraph("Your Trusted Banking Partner", taglineFont);
        tagline.setAlignment(Element. ALIGN_CENTER);
        tagline.setSpacingAfter(20);
        document.add(tagline);
        
        // Horizontal line
        LineSeparator line = new LineSeparator();
        line.setLineColor(HEADER_COLOR);
        document.add(new Chunk(line));
    }
    
    private static void addInfoRow(PdfPTable table, String label, String value) {
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font valueFont = new Font(Font. FontFamily.HELVETICA, 10);
        
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setBackgroundColor(LIGHT_GRAY);
        labelCell.setPadding(8);
        table.addCell(labelCell);
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(8);
        table.addCell(valueCell);
    }
    
    private static void addTableHeader(PdfPTable table, String text) {
        Font font = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(HEADER_COLOR);
        cell.setHorizontalAlignment(Element. ALIGN_CENTER);
        cell.setPadding(8);
        table.addCell(cell);
    }
    
    private static void addTableCell(PdfPTable table, String text) {
        Font font = new Font(Font.FontFamily. HELVETICA, 8);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }
    
    private static void addFooter(Document document) throws DocumentException {
        document.add(new Paragraph("\n"));
        
        Font footerFont = new Font(Font. FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.GRAY);
        Paragraph footer1 = new Paragraph("This is a computer-generated statement.", footerFont);
        Paragraph footer2 = new Paragraph("For any queries, contact: support@securebank.com | Helpline: 1800-XXX-XXXX", footerFont);
        
        footer1.setAlignment(Element.ALIGN_CENTER);
        footer2.setAlignment(Element.ALIGN_CENTER);
        
        document. add(footer1);
        document.add(footer2);
    }
}
