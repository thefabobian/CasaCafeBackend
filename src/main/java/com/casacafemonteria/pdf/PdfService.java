package com.casacafemonteria.pdf;

import com.casacafemonteria.bill.persistence.entities.BillEntity;
import com.casacafemonteria.detailsBill.persistence.entities.DetailBillEntity;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {
    public byte[] generarFacturaPDF(BillEntity factura, List<DetailBillEntity> detalles) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            document.add(new Paragraph("FACTURA"));
            document.add(new Paragraph("Fecha: " + factura.getDate()));
            document.add(new Paragraph("Cliente: " + factura.getCustomerEntity().getUserEntity().getUsername()));
            document.add(new Paragraph("DNI: " + factura.getCustomerEntity().getDni()));
            document.add(new Paragraph(" "));

            // Tabla
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Producto");
            table.addCell("Cantidad");
            table.addCell("Precio Unitario");
            table.addCell("Subtotal");

            double total = 0;
            for (DetailBillEntity detalle : detalles) {
                table.addCell(detalle.getProductEntity().getName());
                table.addCell(String.valueOf(detalle.getQuantity()));
                table.addCell(String.format("$ %.2f", detalle.getPriceUnitary()));
                double subtotal = detalle.getQuantity() * detalle.getPriceUnitary();
                table.addCell(String.format("$ %.2f", subtotal));
                total += subtotal;
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("TOTAL: $ " + String.format("%.2f", total)));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }
}
