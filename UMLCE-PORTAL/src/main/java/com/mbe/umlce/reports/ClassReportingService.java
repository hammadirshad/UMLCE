package com.mbe.umlce.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbe.umlce.beans.MistakeReportBean;
import com.mbe.umlce.model.Mistake;
import com.mbe.umlce.service.MistakeService;

@Service
public class ClassReportingService {

	@Autowired
	MistakeService mistakeService;

	public List<MistakeReportBean> getClassMistakesReport() {
		List<Mistake> mistakes = mistakeService.findAll();
		List<MistakeReportBean> mistakesreport = new ArrayList<MistakeReportBean>();
		HashMap<String, MistakeReportBean> mistakesreport1 = new HashMap<String, MistakeReportBean>();
		String key = null;
		for (Mistake mistake : mistakes) {
			key = mistake.getErrorName() + "," + mistake.getType() + ","
					+ mistake.getModelType() + "," + mistake.getElementName();
			if (mistakesreport1.containsKey(key)) {
				mistakesreport1.get(key).setCount(
						mistakesreport1.get(key).getCount() + 1);
			} else {
				MistakeReportBean mistakereportbean = new MistakeReportBean(
						mistake.getErrorName(), mistake.getType(),
						mistake.getModelType(), mistake.getElementName(), 1);
				mistakesreport1.put(key, mistakereportbean);
			}
		}
		mistakesreport = new ArrayList<MistakeReportBean>();
		for (Entry<String, MistakeReportBean> entry : mistakesreport1
				.entrySet()) {
			mistakesreport.add(entry.getValue());
		}
		Collections.sort(mistakesreport, new Comparator<MistakeReportBean>() {
			public int compare(MistakeReportBean m1, MistakeReportBean m2) {
				return m2.getCount().compareTo(m1.getCount());
			}
		});

		return mistakesreport;
	}

	public DefaultCategoryDataset getClassMistakesReportGraph() {
		List<MistakeReportBean> mistakesreport = getClassMistakesReport();
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (MistakeReportBean mistakeReportBean : mistakesreport) {
			dataset.addValue(mistakeReportBean.getCount(),
					mistakeReportBean.getErrorName(),
					mistakeReportBean.getElementName());
		}
		return dataset;
	}

}
