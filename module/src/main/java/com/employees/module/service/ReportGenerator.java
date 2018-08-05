package com.employees.module.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.employees.module.Params;
import com.employees.module.dto.SalaryGroupWithCountsDto;
import com.employees.module.utils.Utils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class ReportGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(ReportGenerator.class);

	@Value("${report.path}")
	private String reportPath;

	@Value("${report.filename}")
	private String reportFilename;

	@Value("${report.destinationFile}")
	private String destinationFile;

	private EmployeeService employeeService;

	@Autowired
	public ReportGenerator(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Scheduled(fixedRateString = "${report.fixedDelay.in.milliseconds}")
	public void generateReport() {
		try {
			JasperReport report;
			try {
				report = (JasperReport) JRLoader.loadObjectFromFile(Utils.buildPath(reportPath, reportFilename));
				LOG.debug("Report loaded from File.");
			} catch (JRException e) {
				report = JasperCompileManager.compileReport(Utils.buildPath(reportPath, reportFilename));
				LOG.debug("Report compiled.");
			}
			File pdfFile = new File(Utils.buildPath(reportPath, destinationFile));
			final LocalDateTime lastGenerationDate = Utils.convertLongToLocalDateTime(pdfFile.lastModified());
			employeeService.reassignEmployeesToAppropriateSalaryGroups(lastGenerationDate);
			List<SalaryGroupWithCountsDto> salaryGroupsWithCounts = employeeService
					.calculateEmployeesAccordingToSalaryGroups();
			generateAndExportToPdf(salaryGroupsWithCounts, report);
			LOG.info("Generated report successfully.");
		} catch (JRException e) {
			LOG.error("Generating Report Failed. " + e);
		}

	}

	private void generateAndExportToPdf(List<SalaryGroupWithCountsDto> collection, JasperReport report)
			throws JRException {
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(collection);
		JRDataSource jrDataSource = new JREmptyDataSource();
		Map<String, Object> params = new HashMap<>();
		params.put(Params.DATE_TIME, LocalDateTime.now());
		params.put(Params.ITEM_DATA_SOURCE, itemsJRBean);
		JasperPrint print = JasperFillManager.fillReport(report, params, jrDataSource);
		JasperExportManager.exportReportToPdfFile(print, Utils.buildPath(reportPath, destinationFile));
	}
}
