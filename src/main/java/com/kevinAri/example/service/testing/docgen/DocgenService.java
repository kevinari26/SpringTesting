//package com.kevinAri.example.service.testing.docgen;
//
//import org.apache.poi.xwpf.usermodel.*;
//import org.jodconverter.core.DocumentConverter;
//import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//@Service
//public class DocgenService {
//    @Autowired
//    private DocumentConverter documentConverter;
//
//
//
//    public Object testDocGen () {
//        try {
//            Map<String, String> parameters = new HashMap<>();
////            parameters.put("param01", "parameter1");
////            parameters.put("paramNormal", "parameter2");
////            parameters.put("param1", "parameter3");
////            parameters.put("param2", "parameter4");
////            parameters.put("param3", "parameter5");
////            parameters.put("param31", "parameter6");
////            parameters.put("param32", "parameter7");
////            parameters.put("param4", "parameter8");
//            parameters.put("apsiFullName", "___Full Name");
//            parameters.put("aidrAddressIdx2", "___Alamat");
//            parameters.put("apsiMobilePhone1", "___Nomor Tel");
//            parameters.put("apsiEmail1", "___email");
//            parameters.put("apsiIdName", "___nama");
//            parameters.put("applAppliedPlafond", "___plafond");
//            parameters.put("applAppliedPlafondText", "___plafond text");
//            parameters.put("applAppliedTenor", "___tenor");
//            parameters.put("applAppliedTenorText", "___tenor text");
//            parameters.put("apltSurfaceArea", "___surface area");
//            parameters.put("apltCollateralAddress", "___coll address");
//            parameters.put("apltCollateralName", "___coll name");
//            parameters.put("apltDeveloper", "___developer");
//            parameters.put("apltBuildingArea", "___build area");
//
//
//            // testing
//            String templatePath;
//            String outputPath;
//            templatePath = "C:\\Users\\arisa\\Downloads\\Doc Mortgage\\kprBijakEdited.docx";
//            templatePath = "C:\\Users\\arisa\\Downloads\\Doc Mortgage\\kprBijak.rtf";
//            outputPath = "C:\\Users\\arisa\\Downloads\\Doc Mortgage\\outFromDocx.pdf";
//            outputPath = "C:\\Users\\arisa\\Downloads\\Doc Mortgage\\outFromRtfOld2.pdf";
//            byte[] byteArr = generatePdfFromRtfOld(parameters, templatePath);
//
//            // save ke directory
////            File outputFile = new File(outputPath);
////			try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
////				outputStream.write(byteArr);
////			}
//
//            System.out.println("selesai");
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_PDF).body(byteArr);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // cara lama (langsung buka file rtf dalam bentuk byte, lalu find and replace langsung parameternya)
//    public byte[] generatePdfFromRtfOld (Map<String, String> parameters, String templatePath) {
//        try {
//            // baca file template .rtf
//            byte[] encoded = Files.readAllBytes(Paths.get(templatePath));
//            String stringFile = new String(encoded);
//            // find and replace
//            for (Map.Entry<String,String> ele : parameters.entrySet()) {
//                // replace dengan regex, bisa case insensitive pakai regex "(?i)"
//                stringFile = stringFile.replaceAll("(?i)\\[%="+ele.getKey()+"%\\]", ele.getValue());
//                // replace tanpa regex, case sensitive
////                stringFile = stringFile.replace("[%="+ele.getKey()+"%]", ele.getValue());
////                System.out.println(ele.getKey() + "  " + ele.getValue());
//            }
//            // save file output
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            byte[] byteArr;
//            // convert dari rtf ke pdf menggunakan JodConverter
//            documentConverter.convert(new ByteArrayInputStream(stringFile.getBytes()), true)
//                    .as(DefaultDocumentFormatRegistry.RTF)
//                    .to(baos).as(DefaultDocumentFormatRegistry.PDF).execute();
//            byteArr = baos.toByteArray();
//            baos.close();
//
//            return byteArr;
//
//
//            // save file ke directory
////			File outputFile = new File(outputPath);
////			try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
////				outputStream.write(byteArr);
////			}
//        } catch (Exception e) {
////			e.printStackTrace();
//            return null;
//        }
//    }
//
//    // cara baru (convert rtf ke docx, olah dalam bentuk docx, lalu convert ke pdf)
//    public byte[] generatePdfFromRtfNew (Map<String, String> parameters, String templatePath) {
//        byte[] bs = null;
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            // convert dari rtf ke docx menggunakan JodConverter
//            documentConverter.convert(new FileInputStream(templatePath), true)
//                    .as(DefaultDocumentFormatRegistry.RTF)
//                    .to(baos).as(DefaultDocumentFormatRegistry.DOCX).execute();
//            // convert output stream ke input stream
//            InputStream fileTemplate = new ByteArrayInputStream(baos.toByteArray());
//
//            // find and replace
//            bs = algoFindAndReplace(parameters, fileTemplate);
//            return bs;
//        } catch (Exception e) {
////			e.printStackTrace();
//            return null;
//        }
//    }
//
//    // fungsi untuk generate dokumen dari file template .docx
//    public byte[] generatePdfFromDocx (Map<String, String> parameters, String templatePath) {
//        byte[] bs = null;
//        InputStream fileTemplate = null;
//        try {
//            // buka file
//            fileTemplate = new FileInputStream(templatePath);
//
//            // find and replace
//            bs = algoFindAndReplace(parameters, fileTemplate);
//            return bs;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    // fungsi rekursif untuk cari semua cell di dalam tabel (termasuk tabel di dalam tabel)
//    private List<XWPFTableCell> getAllCells (XWPFTableCell cell) {
//        List<XWPFTable> listTable = cell.getTables();
//        if (listTable.size() == 0) { // jika tidak ada tabel di dalam cell (base rekursif)
//            return Collections.singletonList(cell); // return dirinya sendiri
//        } else { // jika ada tabel di dalam cell
//            List<XWPFTableCell> listCell = new ArrayList<>();
//            listCell.add(cell); // masukkan dirinya sendiri ke dalam array
//            for (XWPFTable t: listTable) {
//                for (XWPFTableRow r: t.getRows()) {
//                    for (XWPFTableCell c: r.getTableCells()) {
//                        listCell.addAll(getAllCells(c));
//                    }
//                }
//            }
//            return listCell;
//        }
//    }
//
//    /* fungsi untuk find and replace pada data file docx
//    fungsi dapat mengatasi problem kata-kata yang terpotong pada paragraf docx
//    parameters adalah pasangan key value yang ingin diganti di dalam dokumen
//    contoh: key=paramOld, value=paramNew
//    semua kata paramOld di dalam dokumen akan diganti dengan kata paramNew
//    semua key case insensitive, tapi semua value case sensitive
//    */
//    private byte[] algoFindAndReplace (Map<String, String> parameters, InputStream dataDocx) {
//        try {
//            // ubah semua key di parameter jadi lower case
//            { // untuk free memory
//                Map<String, String> paramsLC = new HashMap<>();
//                for (Map.Entry<String, String> ele : parameters.entrySet()) {
//                    paramsLC.put(ele.getKey().toLowerCase(), ele.getValue());
//                }
//                parameters = paramsLC;
//            }
//
//            // baca file template .docx
//            XWPFDocument doc = new XWPFDocument(dataDocx);
//
//            // ambil semua paragraf di dalam dokumen
//            List<XWPFParagraph> listPara = new ArrayList<>(doc.getParagraphs());
//            // tambahkan semua paragraf dari tabel
//            {
//                // cari semua cell (termasuk tabel di dalam tabel)
//                List<XWPFTableCell> listC = new ArrayList<>();
//                for (XWPFTable t : doc.getTables()) {
//                    for (XWPFTableRow r : t.getRows()) {
//                        for (XWPFTableCell c : r.getTableCells()) {
//                            listC.addAll(getAllCells(c));
//                        }
//                    }
//                }
//                // add paragraf dari tabel ke array listPara
//                for (XWPFTableCell c : listC) {
//                    listPara.addAll(c.getParagraphs());
//                }
//            }
////            System.out.println("test");
//
//
//            // looping semua paragraf dari paragraf biasa maupun dari tabel
//            for (XWPFParagraph currPara: listPara) {
//                // ALGORITMA
//                // array parameter yang ditemukan
//                // contoh arrParam = [param1, param2, param3]
//                List<String> arrParam = new ArrayList<>();
//                // array lokasi parameter ditemukan (lokasi start dan end)
//                // contoh arrLok = [[5, 12, 20], [11, 18, 26]] | 5, 12, 20 adalah lokasi start
//                List<List<Integer>> arrLok = new ArrayList<>(Arrays.asList(new ArrayList<>(), new ArrayList<>()));
//
//                // cari di paragraf semua kata yang ada di dalam [%=...%] dan simpan hasilnya ke arrParam dan arrLok
//                Pattern pattern = Pattern.compile("\\[%=[^\\[%=]*%\\]"); // regex untuk cari semua kata di dalam [%=...%]
//                Matcher matcher = pattern.matcher(currPara.getText());
//                while (matcher.find()) {
//                    String tempParam = matcher.group().substring(matcher.group().indexOf("[%=") + 3, matcher.group().indexOf("%]")).toLowerCase();
//                    if (parameters.get(tempParam) != null) { // masukkan hasil jika kata tersebut terdaftar di parameter
//                        arrParam.add(tempParam);
//                        arrLok.get(0).add(matcher.start());
//                        arrLok.get(1).add(matcher.end());
//                    }
//                }
//
//                // jika ditemukan parameter yang perlu diganti
//                if (arrParam.size() != 0) {
//                    // buat array total panjang runs runsLength
//                    List<Integer> runsLength = new ArrayList<>(); // recursive length
//                    for (XWPFRun r : currPara.getRuns()) {
//                        runsLength.add(r.text().length());
//                    }
//                    if (runsLength.size() > 1) { // jika paragraf berisi lebih dari 1 runs
//                        for (int i = 1; i < currPara.getRuns().size(); i++) {
//                            runsLength.set(i, runsLength.get(i) + runsLength.get(i - 1));
//                        }
//                    }
//
//                    // replace kata-kata mulai dari paling belakang
//                    for (int i = arrParam.size() - 1; i >= 0; i--) {
//                        int runStr = -1, runEnd = -1;
//                        for (int j = 0; j < runsLength.size(); j++) { // cari runs pembuka yang berisi parameter (cari dari depan)
//                            if (arrLok.get(0).get(i) < runsLength.get(j)) { // lokasi start < panjang total runs
//                                runStr = j;
//                                for (int k = j; k < runsLength.size(); k++) { // cari runs penutup yang berisi parameter (cari lanjut dari posisi terakhir)
//                                    if (arrLok.get(1).get(i) <= runsLength.get(k)) { // lokasi end <= panjang total runs
//                                        runEnd = k;
//                                        break;
//                                    }
//                                }
//                                break;
//                            }
//                        }
////                        System.out.println(runStr + " | " + runEnd);
//
//                        // gabungkan runStr dan runEnd menjadi 1
//                        String tempStr = currPara.getRuns().get(runStr).text();
//                        // gabungkan hanya jika parameter terpotong
//                        int selisihRun = runEnd - runStr;
//                        if (selisihRun > 0) {
//                            for (int j = runStr + 1; j < runEnd + 1; j++) {
//                                tempStr = tempStr.concat(currPara.getRuns().get(j).text());
//                            }
//                            // hapus semua run yang sudah digabungkan
//                            for (int j = 0; j < selisihRun; j++) {
//                                currPara.removeRun(runStr + 1);
//                            }
//                        }
//
//                        // replace parameter dengan nilai yang sesuai
//                        tempStr = tempStr.replaceAll("(?i)\\[%=" + arrParam.get(i) + "%\\]", parameters.get(arrParam.get(i)));
//                        currPara.getRuns().get(runStr).setText(tempStr, 0);
//
//                        // update array runsLength jika ada penggabungan runs
//                        if (selisihRun > 0) {
//                            int newSize = currPara.getRuns().size(); // size runs yang baru
//                            for (int j = runStr; j < newSize; j++) {
//                                if (j != 0) { // jika bukan run pertama
//                                    runsLength.set(j, runsLength.get(j - 1) + currPara.getRuns().get(j).text().length());
//                                } else { // jika run pertama
//                                    runsLength.set(j, currPara.getRuns().get(j).text().length());
//                                }
//                            }
//                            // hapus sisa array di belakang
//                            runsLength.subList(newSize, runsLength.size()).clear();
//                        }
//                    }
//                }
//            }
//
//
//            // convert ke OutputStream
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            doc.write(baos);
//            doc.close();
//
//            // convert dari docx ke pdf menggunakan JodConverter
//            InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
//            baos = new ByteArrayOutputStream(); // kosongkan baos
//
//            documentConverter.convert(inputStream, true)
//                    .as(DefaultDocumentFormatRegistry.DOCX)
//                    .to(baos).as(DefaultDocumentFormatRegistry.PDF).execute();
//            // return
//            return baos.toByteArray();
//        } catch (Exception e) {
////			e.printStackTrace();
//            return null;
//        }
//    }
//}
