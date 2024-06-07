package com.kevinAri.example.service.mortgage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kevinAri.example.util.CommonUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fit.pdfdom.PDFDomTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Service
public class MortgageAppService {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ScriptTestingService scriptTestingService;
    @Autowired
    MortgageApp3Service mortgageApp3Service;
//    @Autowired
//    DocgenService docgenService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public void execute() {
        try {
//            findAndReplacePcsm();
//            pdfToHtml();
//            zipFile();
            mortgageApp3Service.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // fungsi
    private static String setLeadingZero(String str, String format){
        NumberFormat formatter = new DecimalFormat(format);
        String res = formatter.format(Long.valueOf(str).longValue());
        return res;
    }

    // docgen
    private void cekDocgen() {
        String templateFieldsStr = "pelunasangpdesc, biayanotaripnpbht, biayatransferujroh, pelunasanahpdesc, biayanotaripk, biayatransfer, proptype, biayalegal, biayaujroh, biayanotarilou, premilifeins, insname, biayanotariroya, pelunasanhpdesc, jenisjaminan, biayanotariceksert, biayanotariapht, biayanotariskmht, biayaappraisal, premifireins";
        String catalogFieldsStr = "pelunasanahp, uangmukasyh, pelunasanahpdesc, biayanotaripk, alamatheader, biayalegal, angsuranbanding, biayanotariroya, cuhmcity, jenisjaminan, biayanotariapht, cuhmprop, pelunasangpdesc, cuhmcamat, biayanotaripnpbht, cpinterestbanding, premilifeins, rtrw, pelunasangp, biayaappraisal, cplimitbanding, city, biayatransfer, cuhmlurah, proptype, postalcd, fixedperbanding, pelunasanto, dendaketerlambatan, pelunasanhp, pelunasantodesc, insname, acuanbunga, districtcd, inscompname, adminfeefinal, cuhmzipcode, biayanotariskmht, tenormmbanding, address1, noref, biayanotarilou, pelunasanhpdesc, rtrw1, biayanotariceksert, subprodtype, subdistrictcd, cufullnm, premifireins";
        List<String> templateFields = Arrays.asList(templateFieldsStr.split(", "));
        List<String> catalogFields = Arrays.asList(catalogFieldsStr.split(", "));
        for (String templateField : templateFields) {
            if (!catalogFields.contains(templateField)) {
                System.out.println(templateField);
            }
        }
        System.out.println();
    }





    // read file
    private String readJson() throws Exception {
//        File jsonFile = Paths.get("src/main/resources/jsonFiles/mortgage/", "addtl_info.json").toFile();
        File jsonFile = Paths.get("C:\\Users\\arisa\\Downloads\\MortgageFile\\Pefindo\\process pefindoBigReport 6000 contract", "callbackForBeMortgage.json").toFile();
        JsonNode jsonNode = objectMapper.readTree(jsonFile);

        System.out.println();
        return "";
    }
    private void readExcel() throws Exception {
        File file = Paths.get("C:\\Users\\arisa\\Downloads\\MortgageFile\\Pervios\\2N641678680151389", "2N641678680151389.xlsx").toFile();
        Workbook workbook = new XSSFWorkbook(file);

        System.out.println(workbook.getSheetAt(0).getRow(11).getCell(2).toString());
        System.out.println(workbook.getSheetAt(0).getRow(11).getCell(2).getNumericCellValue());
        System.out.println(workbook.getSheetAt(0).getRow(12).getCell(2).getNumericCellValue());
        System.out.println(workbook.getSheetAt(0).getRow(13).getCell(2).getNumericCellValue());
        System.out.println(workbook.getSheetAt(0).getRow(13).getCell(2).toString());
        System.out.println(workbook.getSheetAt(0).getRow(28).getCell(2).toString());

        System.out.println();
        String temp = "1.03354223774194E8";
        System.out.println(excelNumberStrToBigDecimal(temp, null));
        temp = "5.0";
        System.out.println(excelNumberStrToBigDecimal(temp, 0));

    }
    private BigDecimal excelNumberStrToBigDecimal(String numberStr, Integer scale) {
        try  {
            // handling excel scientific format (ex: 1.03354223774194E8)
            String[] arrStr = numberStr.split("E");
            BigDecimal bigDecimal;
            if (arrStr.length > 1) {
                bigDecimal = CommonUtil.stringToBigDecimal(arrStr[0], BigDecimal.ZERO);
                BigDecimal power = new BigDecimal(10).pow(Integer.parseInt(arrStr[1]));
                bigDecimal = bigDecimal.multiply(power);
            }
            else {
                bigDecimal = CommonUtil.stringToBigDecimal(arrStr[0], BigDecimal.ZERO);
            }

            // potong angka di belakang koma
            if (scale != null) {
                bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_EVEN);
            }

            return bigDecimal;
        } catch (Exception e) {
            return null;
        }
    }
    private void fileToBase64() {
        try {
            // file to base64.txt
            Path path = Paths.get("C:\\Users\\arisa\\Downloads\\MortgageFile\\Pervios\\2N641678680151389", "2N641678680151389.xlsx");
            File file = path.toFile();
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String base64 = Base64.getEncoder().encodeToString(fileContent);

            // base64.txt to file
            File outputFile = new File("C:\\Users\\arisa\\Downloads\\MortgageFile\\Pervios\\2N641678680151389", "test.xlsx");
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(Base64.getDecoder().decode(base64));
            outputStream.close();

            System.out.println();
        } catch (Exception e) {

        }
    }
    private void readBvRv() {
        try {
//            String bvFileBase64 = "DQoNCkJWS1BSMDk4NzAxMjMwOTIxXzc7MjAyNC0wMi0xMjsxOTcwLTAxLTAxOzIwMjQtMDItMTM7S1BSMDk4NzAxMjMwOTIxO1RSSU8gRkVSRElBTlNZQUg7O1BUIFNBUkFOQSBCRVJLQUggTklBR0E7SkwuIEpBVElNQUtNVVIgIE5PLiAzNiBKQVRJTUFLTVVSIFBPTkRPSyBHRURFIEJFS0FTSSAgS09UQS4gMTc0MTM7MTc0MTM7Ozs7MDE7MTA7UEFLIFRSSU8gRkVSRElBTlNZQUg7MDE7MTs7Ozs7Ozs7Ozs7OzsxMDtQQUsgU0FNU1VESU47MDA7MTs7Ozs7Ozs7Ozs7OzsxMDtQQUsgSlVOQUVESSA7MDA7MTs7Ozs7Ozs7Ozs7OzswMjswMTswMTsyOzU7NTswMTswMTswMjswMztQRVI7MDA0OzAzOzAxOzAxOzAyOzAyOzAxO0NPTVBVVEVSOzA4MjEyMTIxMDA2ODs3MDsxO1NJMSBBREwgQVBQLiBJTkZPIEFQUCBCQUhXQSBBUFAgQk5SIE1FTkdBSlVLQU4gQVBMSUtBU0kgREFOIEJOUiBCRUtFUkpBIERJIEtOVFIgVFNCIDtTSTIgQURMIEtBUllBV0FOIFJTIE1BU01JVFJBLiBTSTIgVERLIEtFTkFMIEFQUCBUVFBJIFRBSFUgTkFNQSBLTlRSIDtTSTMgQURMIFNUQUYgS0VMVVJBSEFOIEpBVElNQUtNVVIuIFNJMyBUREsgS0VOQUwgQVBQIFRUUEkgVEFIVSBOQU1BIEtOVFIgO0FQUCBCTlIgTUVOR0FKVUtBTiBBUExJS0FTSSBEQU4gQk5SIEJFS0VSSkEgREkgS05UUiBUU0IgU0JHIEFTSVNURU4gTUFOQUdFUi4gQkRHIFVTQUhBIEtVTElORVIgLkFLVEZGUyBEQU4gQUxBVCBLRVJKQSBBREEuIFBMQU5HIEtOVFIgQURBLiBMR0tHTiBLTlRSIFBFUlRPS09BTi4gSU5GTyBTSTEgSlVNTEFIIEtBUllBV0FOIERJIFBSU0ggVFNCIEFEQSAyMCAgT1JHIFRUUCAgU0FBVCBWSVNJVCBBREEgNiBPUkFORyBLQVJZQVdBTiBURVJMSUhBVCBESSBLTlRSIFRTQiAuIFNJMi9LQVJZQVdBTiBSUyBNQVNNSVRSQSBEQU4gU0kzL1NUQUYgS0VMVVJBSEFOIEpBVElNQUtNVVIuICBLTkRTSSBLTlRSIEJBSUsgUEVSTUFORU4gLiBQSE9UTyBQTEFORyAgU0FCQU5BIEFEQUxBSCBOQU1BIEJSQU5EIFBSU0ggVFNCLiAgDQo=";
//            String rvFileBase64 = "DQoNCkJWS1BSMDI4NTAyMjMxMjI5XzY7MjAyNC0wMi0yODsyMDI0LTAyLTI5OzIwMjQtMDItMjk7S1BSMDI4NTAyMjMxMjI5O1NJVEkgU0FNU0lZQUg7O1RPS08gU0FMV0E7S01QIE1VQVJBIEJBSEFSSSBOTyAxOCBSVCAwMDIvMDEyIEpBS0FSVEEgVVRBUkEgUlQuMDAyMDEyIFRBTkpVTkcgUFJJT0sgVEFOSlVORyBQUklPSyBKQUtBUlRBIFVUQVJBIC4gIFdJTC4gS09UQSAxNDMxMDsxNDMxMDs7OzswMTsxMDtKVU1JQVRJIDswMTsxOzs7Ozs7Ozs7Ozs7OzEwO1NVTEFNSSA7MDE7MTs7Ozs7Ozs7Ozs7OzsxMDtNQVJJQSA7MDE7MTs7Ozs7Ozs7Ozs7OzswMTswMjswMzsyOzU7NTswMTswNDswMjswMztQRVI7MDEwOzAxOzAxOzAyOzAxOzAxOzAxO0tFTkRBUkFBTiBPUEVSQVNJT05BTDswOzEwOzE7U0kxIEFETCAgUEVHQVdBSSBBUFAuIEFQUCBQVU5ZQSBVU0FIQSBUT0tPIFNFTUJBS08gREFOIFJVTUFIIEtPTlRSQUsgMTAgUElOVFU7U0kyIEFETCAgVEVUQU5HR0EgTk8gMjAuIElORk8gU0kyIEFQUCBESUtFTkFMO1NJMyBBREwgVEVUQU5HR0EgUlQuMDkvMTIuIElORk8gU0kzIEFQUCBESUtFTkFMIERBTiAgU0VEQU5HIEtFTFVBUiBSVU1BSC4gO0FMQU1BVCBTRVNVQUkgU0FBVCBTVVJWRVkgQVBQIFNFREFORyBLRUxVQVIgS09UQS4gQVBQIE1FTUlMSUtJIFVTQUhBIFRPS08gU0VNQkFLTyBEQU4gMTAgUElOVFUgS09OVFJBS0FOIFBFVEFLLiBLTVAgTVVBUkEgQkFIQVJJIE4gU0FNQSBERU5HQU4gS0FNUFVORyBTRUhBVA0K";
//
//            String bvFileStr = new String(Base64.getDecoder().decode(bvFileBase64), StandardCharsets.UTF_8).trim();
//            List<String> listBvData = new ArrayList<>(Arrays.asList(bvFileStr.split(";")));

//            String temp = "PL18012500998;SORAYA;RAYA HAUS GGG;H 5 HHH;;;HUTANKRAMAT AAA;SARIASIH BBB;BEKASI CCC;0198;17421;15/03/2021;15/03/2021;15/03/2021;;0;0;1;BIMBANG;03;2;1;1;BIMBANG;2;1;;2000;2;;021;021;0361;0361;10;1;3;1;1;2;2;PC;BIMBANG;02;;2;4;02;BIMBANG;02;3;1;12;02;BIMBANG BIMBANG;jasa;SUDIRMAN III;S 17 JJJ;;;KARET TENGSIN DDD;TANAH ABANG EEE;JAKARTA PUSAT FFF;10220;0391;15/03/2021;15/03/2021;15/03/2021;;1;1;1;3;2;ASTRA;JOKO;CH;2;1;JOKO;JOKO;8;2;022;022;20000;20000;21;2;2;2;7;2;1;2;2;Laptop;1;JOKO;02;AGR;12;3;3;JOKO;01;KST;4;4;2;JOKO BAMBANG;15/03/2021 15:41;fsValdo";
            String temp = "PL20012200012;APP;Bernard GTG SIT dua;Bernard GTG Sit dua;11/04/1995;0811;1991406;test SIT Punya Bernard GTG;;Panggil Bernard GTG dua;Mama Bernard GTG dua;1;Istri Bernard GTG dua;39058-00;C;Positive;;;;;;;;;;;Orang Tua;AYAH Bernard GTG dua;Rumah Bernard GTG No 2;NO 17 JL SIT VIII SIT ke2;006;09;SUKASARI;SERANG BARU;0394;TANGERANG;17331;021;29370790;Memo Rumah Bernard GTG KE2;01;2;9;;Positive;;;;;;;;;;;;;;SECURITY;Security Bernard GTG dua;Kantor Bernard GTG dua;PERGUDANGAN DELTA SILICON 3;BLOK F9 NO 1 JL AGATHIS dua;006;09;SUKASARI;SERANG BARU;0394;BEKASI;17000;021;29617825;000;DT;BOS;043;note Kantor Bernard GTG Benar dua;021;7823;000;50;1;1;2;9;10000000;Positive;C;;;;;;;;Emergancy SIT dua;Emergency Bernard GTG dua;Emergency Bernard GTG SIT dua;addres econ 1 dua;addres econ 2 dua;006;09;SUKASARI;SERANG BARU;0394;BEKASI;17000;021;752517;0822;254121874;7;DATA ECON TDK ADA, NO PV SIT DUA.;2;9;C;Positive;;;;;;;;;;;;;;;KHOLIDA JAMIL;27/02/2020;25/02/2020 11:11:20;phnValdo;1";
            List<String> listTest = Arrays.asList(temp.split(";"));

            System.out.println();
        } catch (Exception e) {

        }
    }
    private void unzipFile() throws Exception {
        File zipFile = new File("C:\\Users\\arisa\\Downloads\\MortgageFile\\BVRV\\Request\\Sample Request", "testZip.zip");

        Map<String, ByteArrayOutputStream> mapFile = new HashMap<>();

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        // looping semua file yang ada di dalam zip
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            // write to baos
            int len;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = zis.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            mapFile.put(zipEntry.getName(), baos);
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }
    private void parsePefindoBigReport() throws Exception {
        // open file
        byte[] fileByte = Files.readAllBytes(Paths.get("D:\\Alpabit\\ProjectPermata\\responseBigReport.txt"));

        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readValue(new String(fileByte), JsonNode.class);
        JsonNode jsonNode1 = jsonNode.get("Body").get("GetCustomReportResponse").get("GetCustomReportResult").get("Contracts").get("ContractList");
        System.out.println(jsonNode1.get("Contract").size());
        System.out.println();

//        Map<String, Object> tempMap = new HashMap<>();
//        tempMap.put("key1", "val1");
//        tempMap.put("key2", "val2");
//        System.out.println(xmlMapper.writeValueAsString(tempMap));
    }
    private void findAndReplacePcsm() throws Exception {
        String directory = "D:\\Alpabit\\ProjectPermata\\Mortgage\\config-repository\\configCallWS\\services";
        String inFilePath = directory + "\\PCSM\\PCSM_BMCC_MAIN.xml";
        String outFilePath = directory + "\\output.xml";
        BufferedReader reader = new BufferedReader(new FileReader(inFilePath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath));

        String line = reader.readLine();
        boolean replaceWithFieldName = true;
        while (line != null) {
            // replace dengan field name
            if (replaceWithFieldName) {
                int start = line.indexOf("<");
                int end = line.indexOf(">");
                String fieldName = line.substring(start+1, end);
                writer.write(line.replace("?", fieldName) + "\n");
            }
            // replace dengan -9
            else {
                writer.write(line.replace("?", "-9") + "\n");
            }
            line = reader.readLine();
        }
        reader.close();
        writer.close();
    }
    private void pdfToHtml() throws Exception {
        PDDocument pdf = PDDocument.load(new File("C:\\Users\\arisa\\Downloads\\download.pdf"));
        PDFDomTree parser = new PDFDomTree();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer output = new PrintWriter(baos, true);
        parser.writeText(pdf, output);
        output.close();
        pdf.close();

//        File outputFile = new File("C:\\Users\\arisa\\Downloads", "kprBijak.html");
        File outputFile = new File("C:\\Users\\arisa\\Downloads", "base64.txt html2.txt");
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(Base64.getEncoder().encode(baos.toByteArray()));
//        outputStream.write(baos.toByteArray());
        outputStream.close();
    }
}
