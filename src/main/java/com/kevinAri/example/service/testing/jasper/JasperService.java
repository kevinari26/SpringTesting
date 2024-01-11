package com.kevinAri.example.service.testing.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;


@Service
public class JasperService {

    public Object testJasper () {
        try {
            JRDataSource dataSource;
            // bisa pakai hasil query
//            dataSource = new JRBeanCollectionDataSource(repo.findAll());
            // bisa pakai semua jenis Collection<T>
            dataSource = new JRBeanCollectionDataSource(Arrays.asList(1, 2, 3, 4));

            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/jasper/testJasper.jrxml");
            HashMap<String,Object> params = new HashMap<>();
            params.put("pathLogo", "src/main/resources/jasper/testLogo.png");
            params.put("createdBy", "Kevin Arisaputra");
            JasperPrint jasperPrint = JasperFillManager.fillReport (jasperReport, params, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "outputJasper.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
