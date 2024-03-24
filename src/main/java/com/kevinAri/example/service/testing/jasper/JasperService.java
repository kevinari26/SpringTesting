package com.kevinAri.example.service.testing.jasper;

import com.kevinAri.example.model.jasper.TestEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;


@Service
public class JasperService {

    public Object testJasper () {
        try {
            JRDataSource dataSource;
            // bisa pakai hasil query
//            dataSource = new JRBeanCollectionDataSource(repo.findAll());
            // bisa pakai semua jenis Collection<T>
//            dataSource = new JRBeanCollectionDataSource(Arrays.asList(1, 2, 3, 4));
            dataSource = new JRBeanCollectionDataSource(Arrays.asList(
                    new TestEntity("nama1", 123, new Date()),
                    new TestEntity("nama2", 1234, new Date())
            ));
            dataSource = new JRBeanCollectionDataSource(Collections.singletonList(
                    new Object()
            ));
            TestEntity testEntity = new TestEntity();
            testEntity.setNama("ok");
            System.out.println(testEntity.getNama());

//            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/jasper/testJasper.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/jasper/TEST_JASPER.jrxml");
            HashMap<String,Object> params = new HashMap<>();
            params.put("pathLogo", "src/main/resources/jasper/testLogo.png");
            params.put("createdBy", "Kevin Arisaputra 2");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/jasper/outputJasper.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
