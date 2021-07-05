package com.deliver.facade;

import com.deliver.model.Ticket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserPDFExporter {
    private List<Ticket> tickets;

    public UserPDFExporter(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    private void writeTableHeader(PdfPTable pTable){
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(BaseColor.BLUE);
        pdfPCell.setPadding(5);

        Font font = FontFactory.getFont("HELVETICA");
        font.setColor(BaseColor.WHITE);

        pdfPCell.setPhrase(new Phrase("Ticket ID", font));
        pTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("User Id", font));
        pTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Event Id", font));
        pTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Categoria", font));
        pTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Place", font));
        pTable.addCell(pdfPCell);
    }

    public void writeTableData(PdfPTable table) {
        for (Ticket ticket : tickets) {
            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(String.valueOf(ticket.getUserId()));
            table.addCell(String.valueOf(ticket.getEventId()));
            table.addCell(String.valueOf(ticket.getCategory()));
            table.addCell(String.valueOf(ticket.getPlace()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(BaseColor.BLUE);

        Paragraph p = new Paragraph("Tickets by Event", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
