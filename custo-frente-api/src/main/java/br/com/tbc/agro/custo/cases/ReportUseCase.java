package br.com.tbc.agro.custo.cases;

import br.com.tbc.agro.core.domain.dto.ReportDTO;
import br.com.tbc.agro.custo.cases.dto.enums.ExportFormatEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.type.ResetTypeEnum;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReportUseCase {

    private final int[] width = {250, 90, 200, 50, 250, 90, 90, 90, 70, 70};
    private int[] x;

    public byte[] generateReport(
            final ReportDTO data,
            final String mediaType,
            final Boolean isSummary
    ) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        if (Objects.requireNonNull(ExportFormatEnum.fromValue(mediaType)) == ExportFormatEnum.XLSX) {
            return exportToExcelBytes(
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data),
                    isSummary
            );
        }
        return exportToPdf(
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data),
                isSummary
        );
    }

    public byte[] exportToPdf(final String data, final Boolean isSummary) throws Exception {
        final JasperPrint jasperPrint = generateTemplate(data.trim(), isSummary);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] exportToExcelBytes(final String data, final Boolean isSummary) throws Exception {

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final JasperPrint jasperPrint = generateTemplate(data.trim(), isSummary);
        final JRXlsxExporter exporter = getJrXlsxExporter(jasperPrint, byteArrayOutputStream);
        exporter.exportReport();
        return byteArrayOutputStream.toByteArray();
    }

    private static @NonNull JRXlsxExporter getJrXlsxExporter(
            final JasperPrint jasperPrint,
            final ByteArrayOutputStream byteArrayOutputStream
    ) {
        final JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));

        final SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setRemoveEmptySpaceBetweenColumns(false);
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setCollapseRowSpan(true);
        configuration.setFontSizeFixEnabled(true);
        configuration.setShowGridLines(false);
        exporter.setConfiguration(configuration);
        return exporter;
    }

    public JasperPrint generateTemplate(
            final String jsonString,
            final boolean isSummary
    ) throws Exception {
        final JasperDesign jasperDesign = new JasperDesign();
        calculateCoordinates(isSummary);
        configurePageSettings(jasperDesign);
        declareFields(jasperDesign);

        final JRDesignGroup groupFront = createGroup(
                jasperDesign,
                "FrontGroup",
                "$F{cdFrenteTrab}"
        );
        JRDesignGroup groupResorce = null;
        if (isSummary) {
            groupResorce = createGroup(jasperDesign, "ResourceGroup", "$F{cdRecurso}");
        }

        declareVariables(jasperDesign, groupFront);

        jasperDesign.setTitle(generateTitleBand(isSummary));
        jasperDesign.setColumnHeader(generateColumnHeader(isSummary));

        ((JRDesignSection) groupFront.getGroupHeaderSection()).addBand(generateFrontHeader(isSummary));

        if (isSummary) {
            ((JRDesignSection) groupResorce.getGroupHeaderSection()).addBand(generateResourceHeader());
        }

        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(generateDetailBand());
        ((JRDesignSection) groupFront.getGroupFooterSection()).addBand(generateFrontFooter(isSummary));
        jasperDesign.setSummary(generateSummaryBand());

        final JsonDataSource jsonDataSource = new JsonDataSource(
                new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8)), "items");
        return JasperFillManager.fillReport(
                JasperCompileManager.compileReport(jasperDesign),
                new HashMap<>(),
                jsonDataSource
        );
    }

    private void calculateCoordinates(final boolean isSummary) {
        x = new int[width.length];
        x[0] = 0;
        final int space = 5;
        int currentX = width[0] + space;
        for (int i = 1; i < width.length; i++) {
            if (!isSummary && i <= 3) {
                x[i] = 0;
            } else {
                x[i] = currentX;
                currentX += width[i] + space;
            }
        }
    }

    private void configurePageSettings(final JasperDesign jasperDesign) {
        final int lengthNeed = x[9] + width[9] + 40;
        jasperDesign.setName("RelatorioCustos");
        jasperDesign.setOrientation(OrientationEnum.LANDSCAPE);
        jasperDesign.setPageWidth(lengthNeed);
        jasperDesign.setColumnWidth(lengthNeed - 40);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);
        jasperDesign.setProperty(
                "net.sf.jasperreports.export.xls.remove.empty.space.between.columns",
                "true"
        );
    }

    private void declareFields(final JasperDesign jasperDesign) throws JRException {
        final String[][] fields = {
                {"cdFrenteTrab", "java.lang.Integer"},
                {"deFrenteTrab", "java.lang.String"},
                {"periodoProcessamento", "java.lang.String"},
                {"cdRecurso", "java.lang.Integer"},
                {"deRecurso", "java.lang.String"},
                {"tpRecurso", "java.lang.String"},
                {"cdTipoDespesa", "java.lang.Integer"},
                {"deTipoDespesa", "java.lang.String"},
                {"qtTonelada", "java.lang.Double"},
                {"qtHrKm", "java.lang.Double"},
                {"vlDespesa", "java.lang.Double"},
                {"vlUnitHrKm", "java.lang.Double"},
                {"vlUnitTon", "java.lang.Double"}
        };
        for (String[] field : fields) {
            final JRDesignField jrDesignField = new JRDesignField();
            jrDesignField.setName(field[0]);
            jrDesignField.setValueClassName(field[1]);
            jasperDesign.addField(jrDesignField);
        }
    }

    private JRDesignGroup createGroup(
            final JasperDesign jasperDesign,
            final String name,
            final String expression
    ) throws JRException {
        final JRDesignGroup group = new JRDesignGroup();
        group.setName(name);
        group.setExpression(new JRDesignExpression(expression));
        jasperDesign.addGroup(group);
        return group;
    }

    private void declareVariables(
            final JasperDesign jasperDesign,
            final JRDesignGroup groupFront
    ) throws JRException {
        jasperDesign.addVariable(createVariable(
                "sumVlFrente",
                "$F{vlDespesa}",
                ResetTypeEnum.GROUP,
                groupFront
        ));
        jasperDesign.addVariable(createVariable(
                "totalVl",
                "$F{vlDespesa}",
                ResetTypeEnum.REPORT,
                null
        ));
        jasperDesign.addVariable(createVariable(
                "totalTon",
                "$F{qtTonelada}",
                ResetTypeEnum.REPORT,
                null
        ));
        jasperDesign.addVariable(createVariable(
                "totalHr",
                "$F{qtHrKm}",
                ResetTypeEnum.REPORT,
                null
        ));
    }

    private JRDesignBand generateColumnHeader(final boolean isSummary) {
        final JRDesignBand jrDesignBand = new JRDesignBand();
        jrDesignBand.setHeight(40);
        jrDesignBand.addElement(createStaticText(
                "Quantidades",
                x[5],
                5,
                x[6] + width[6] - x[5],
                15,
                HorizontalTextAlignEnum.CENTER,
                true
        ));
        jrDesignBand.addElement(createStaticText(
                "Valor",
                x[7],
                5,
                x[9] + width[9] - x[7],
                15,
                HorizontalTextAlignEnum.CENTER,
                true
        ));

        final String[] labels = {
                "Frente",
                "Período",
                "Recurso",
                "Un",
                "Tipo de Despesa",
                "Toneladas",
                "Hr/Km",
                "Total",
                "Unit.Unid",
                "Unit.Ton"
        };
        for (int i = 0; i < labels.length; i++) {
            if (!isSummary && (i >= 1 && i <= 3)) {
                continue;
            }
            jrDesignBand.addElement(createStaticText(
                    labels[i],
                    x[i],
                    20,
                    width[i],
                    20,
                    i > 4 ? HorizontalTextAlignEnum.RIGHT : HorizontalTextAlignEnum.LEFT,
                    true
            ));
        }
        return jrDesignBand;
    }

    private JRDesignBand generateFrontHeader(final boolean isSummary) {
        final JRDesignBand jrDesignBand = new JRDesignBand();
        jrDesignBand.setHeight(20);
        final int lengthHeader = isSummary ? width[0] : x[4] - x[0];
        jrDesignBand.addElement(createTextField(
                "$F{cdFrenteTrab} + \" \" + $F{deFrenteTrab}",
                x[0],
                0,
                lengthHeader,
                20,
                true,
                null,
                HorizontalTextAlignEnum.LEFT
        ));
        jrDesignBand.addElement(createTextField(
                "$F{qtTonelada}",
                x[5],
                0,
                width[5],
                20,
                false,
                "#,##0.00",
                HorizontalTextAlignEnum.RIGHT
        ));
        jrDesignBand.addElement(createTextField(
                "$F{qtHrKm}",
                x[6],
                0,
                width[6],
                20,
                false,
                "#,##0.00",
                HorizontalTextAlignEnum.RIGHT
        ));
        return jrDesignBand;
    }

    private JRDesignBand generateResourceHeader() {
        final JRDesignBand jrDesignBand = new JRDesignBand();
        jrDesignBand.setHeight(15);
        final String expData = "new java.text.SimpleDateFormat(\"MM/yyyy\").format(new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse($F{periodoProcessamento}))";

        jrDesignBand.addElement(createTextField(
                expData,
                x[1],
                0,
                width[1],
                15,
                false,
                null,
                HorizontalTextAlignEnum.LEFT
        ));
        jrDesignBand.addElement(createTextField(
                "$F{cdRecurso} + \" \" + $F{deRecurso}",
                x[2],
                0,
                width[2],
                15,
                false,
                null,
                HorizontalTextAlignEnum.LEFT
        ));
        jrDesignBand.addElement(createTextField(
                "$F{tpRecurso}.equals(\"E\") ? \"Hora\" : \"Unid.\"",
                x[3],
                0,
                width[3],
                15,
                false,
                null,
                HorizontalTextAlignEnum.LEFT
        ));
        return jrDesignBand;
    }

    private JRDesignBand generateDetailBand() {
        final JRDesignBand jrDesignBand = new JRDesignBand();
        jrDesignBand.setHeight(15);
        jrDesignBand.addElement(createTextField(
                "$F{cdTipoDespesa} + \" \" + $F{deTipoDespesa}",
                x[4],
                0,
                width[4],
                15,
                false,
                null,
                HorizontalTextAlignEnum.LEFT
        ));
        jrDesignBand.addElement(createTextField(
                "$F{vlDespesa}",
                x[7],
                0,
                width[7],
                15,
                false,
                "R$ #,##0.00",
                HorizontalTextAlignEnum.RIGHT
        ));
        jrDesignBand.addElement(createTextField(
                "$F{vlUnitHrKm}",
                x[8],
                0,
                width[8],
                15,
                false,
                "R$ #,##0.00",
                HorizontalTextAlignEnum.RIGHT
        ));
        jrDesignBand.addElement(createTextField(
                "$F{vlUnitTon}",
                x[9],
                0,
                width[9],
                15,
                false,
                "R$ #,##0.00",
                HorizontalTextAlignEnum.RIGHT
        ));
        return jrDesignBand;
    }

    private JRDesignBand generateFrontFooter(final boolean isSummary) {
        final JRDesignBand jrDesignBand = new JRDesignBand();
        jrDesignBand.setHeight(25);
        if (isSummary) {
            jrDesignBand.addElement(createTextField(
                    "$F{tpRecurso}.equals(\"E\") ? \"Total Equipamento\" : \"Total Mão de Obra\"",
                    x[2],
                    5,
                    width[0],
                    20,
                    true,
                    null,
                    HorizontalTextAlignEnum.LEFT
            ));
        } else {
            jrDesignBand.addElement(createStaticText(
                    "Total Frente",
                    x[0],
                    5,
                    width[0],
                    20,
                    HorizontalTextAlignEnum.LEFT,
                    false
            ));
        }
        jrDesignBand.addElement(createTextField(
                "$V{sumVlFrente}",
                x[7],
                5,
                width[7],
                20,
                true,
                "R$ #,##0.00",
                HorizontalTextAlignEnum.RIGHT
        ));
        return jrDesignBand;
    }

    private JRDesignBand generateSummaryBand() {
        final JRDesignBand jrDesignBand = new JRDesignBand();
        jrDesignBand.setHeight(40);
        jrDesignBand.addElement(createStaticText(
                "Total Geral",
                x[0],
                10,
                width[0],
                20,
                HorizontalTextAlignEnum.LEFT,
                false
        ));
        jrDesignBand.addElement(createTextField(
                "$V{totalVl}",
                x[7],
                10,
                width[7],
                20,
                true,
                "R$ #,##0.00",
                HorizontalTextAlignEnum.RIGHT
        ));
        return jrDesignBand;
    }

    private JRDesignVariable createVariable(
            final String name,
            final String expression,
            final ResetTypeEnum resetTypeEnum,
            final JRDesignGroup jrDesignGroup
    ) {
        final JRDesignVariable jrDesignVariable = new JRDesignVariable();
        jrDesignVariable.setName(name);
        jrDesignVariable.setValueClass(Double.class);
        jrDesignVariable.setCalculation(CalculationEnum.SUM);
        jrDesignVariable.setResetType(resetTypeEnum);
        if (jrDesignGroup != null) {
            jrDesignVariable.setResetGroup(jrDesignGroup);
        }
        jrDesignVariable.setExpression(new JRDesignExpression(expression));
        return jrDesignVariable;
    }

    private JRDesignStaticText createStaticText(
            final String text,
            final int xPosition,
            final int yPosition,
            final int fieldWidth,
            final int height,
            final HorizontalTextAlignEnum alignEnum,
            final boolean underline
    ) {
        final JRDesignStaticText jrDesignStaticText = new JRDesignStaticText();
        jrDesignStaticText.setX(xPosition);
        jrDesignStaticText.setY(yPosition);
        jrDesignStaticText.setWidth(fieldWidth);
        jrDesignStaticText.setHeight(height);
        jrDesignStaticText.setText(text);
        jrDesignStaticText.setBold(true);
        jrDesignStaticText.setHorizontalTextAlign(alignEnum);
        if (underline) {
            jrDesignStaticText.getLineBox().getBottomPen().setLineWidth(0.5f);
        }
        return jrDesignStaticText;
    }

    private JRDesignTextField createTextField(
            final String expression,
            final int xPosition,
            final int yPosition,
            final int fieldWidth,
            final int height,
            final boolean bold,
            final String pattern,
            final HorizontalTextAlignEnum alignEnum
    ) {
        final JRDesignTextField jrDesignTextField = new JRDesignTextField();
        jrDesignTextField.setX(xPosition);
        jrDesignTextField.setY(yPosition);
        jrDesignTextField.setWidth(fieldWidth);
        jrDesignTextField.setHeight(height);
        jrDesignTextField.setBold(bold);
        jrDesignTextField.setHorizontalTextAlign(alignEnum);
        jrDesignTextField.setExpression(new JRDesignExpression(expression));
        if (pattern != null) {
            jrDesignTextField.setPattern(pattern);
        }
        return jrDesignTextField;
    }

    private JRDesignBand generateTitleBand(final boolean isSummary) {
        final JRDesignBand band = new JRDesignBand();
        band.setHeight(40);
        band.addElement(createStaticText(
                isSummary ? "Demonstrativo de Custo por Frente de Trabalho" : "Sumário de Custo por Frente",
                0,
                0,
                x[9] + width[9],
                40,
                HorizontalTextAlignEnum.CENTER,
                false
        ));
        return band;
    }
}
