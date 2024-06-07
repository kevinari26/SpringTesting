package com.kevinAri.example.service.mortgage;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPEngine;
import net.schmizz.sshj.sftp.StatefulSFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.InMemoryDestFile;
import net.schmizz.sshj.xfer.InMemorySourceFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


@Service
public class MortgageApp3Service {
    @Autowired
    ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public void execute() {
        try {
//            findAndReplacePcsm();
//            pdfToHtml();
//            zipFile();
//            setPassword();
//            uploadViaSftp();
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
    public void zipFile() throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream("halo12345".getBytes());
        List<Map<String, Object>> listFileToZip = new LinkedList<>(Arrays.asList(
            new HashMap<String, Object>() {{
                put("fileName", "test.txt");
                put("fileByte", "halo12345".getBytes());
            }}
        ));
        zipFile(listFileToZip, "null");
    }
    private ByteArrayOutputStream zipFile(List<Map<String, Object>> listFileToZip, String zipPassword) throws Exception {
        // file to zip content: byte[] fileByte, String fileName

        // create zip file
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boolean isPassword = zipPassword != null && !"".equals(zipPassword);
        ZipOutputStream zos;
        if (isPassword) zos = new ZipOutputStream(baos, zipPassword.toCharArray());
        else zos = new ZipOutputStream(baos);

        // add file to zip
        for (Map<String, Object> fileToZip : listFileToZip) {
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
            if (isPassword) {
                zipParameters.setEncryptFiles(true);
                zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
                zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
            }
            zipParameters.setFileNameInZip((String) fileToZip.get("fileName"));
            zos.putNextEntry(zipParameters);

            ByteArrayInputStream bais = new ByteArrayInputStream((byte[]) fileToZip.get("fileByte"));
            byte[] bytes = new byte[4096];
            int length;
            while ((length = bais.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
        }
        zos.close();

        // write zip to file
        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\arisa\\Downloads\\MortgageFile\\BVRV\\testZip", "testZip.zip"));
        baos.writeTo(fos);
        fos.close();

        return baos;
    }
    private void setPassword() throws Exception {
//        ZipFile zipFile = new ZipFile("C:\\Users\\arisa\\Downloads\\MortgageFile\\BVRV\\testZip.zip", "password".toCharArray());
        ZipFile zipFile = new ZipFile("C:\\Users\\arisa\\Downloads\\MortgageFile\\BVRV\\testZip\\testZip.zip", new char[]{'1', '2', '3'});
//        ZipParameters zipParameters = new ZipParameters();
//        zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
//        zipParameters.setEncryptFiles(true);
//        zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
//        zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
//        zipParameters.setFileNameInZip("test.txt");
//        zipFile.setUseUtf8CharsetForPasswords(true);
//        zipFile.setPassword("123".toCharArray());
        zipFile.setPassword(new char[]{'1', '2', '3'});
        zipFile.close();
        System.out.println("set pass");
//        zipFile.addFiles(filesToAdd, zipParameters);
    }
    private ZipParameters buildZipParameters(CompressionMethod compressionMethod, boolean encrypt,
                                             EncryptionMethod encryptionMethod, AesKeyStrength aesKeyStrength) {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(compressionMethod);
        zipParameters.setEncryptionMethod(encryptionMethod);
        zipParameters.setAesKeyStrength(aesKeyStrength);
        zipParameters.setEncryptFiles(encrypt);
        return zipParameters;
    }
    private char[] stringToPassword(String password) {
        if (password==null) password = "123";
        char[] passwordChar = new char[password.length()];
        for (int i = 0; i < password.length(); i++) {
            passwordChar[i] = password.charAt(i);
        }
        return passwordChar;
    }

    // ssh & sftp
    private void uploadViaSftp() throws Exception {
        // ssh connection
        SSHClient sshClient = new SSHClient();
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());
        sshClient.connect("10.87.249.55");
        sshClient.authPassword("plus_be", "Axway123");
        // sftp connection
        StatefulSFTPClient statefulSFTPClient = new StatefulSFTPClient(new SFTPEngine(sshClient).init());
//        statefulSFTPClient.cd("/STdata/plus_be/to_plus");

        InMemorySourceFile inMemorySourceFile = initInMemorySourceFile("test.txt", "halo".getBytes());
        statefulSFTPClient.put(inMemorySourceFile, "/STdata/plus_be/from_plus");
        System.out.println();
    }

    private InMemorySourceFile initInMemorySourceFile(String fileName, byte[] fileByte) {
        return new InMemorySourceFile() {
            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public long getLength() {
                return fileByte.length;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(fileByte);
            }
        };
    }
}
