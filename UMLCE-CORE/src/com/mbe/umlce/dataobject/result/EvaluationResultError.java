package com.mbe.umlce.dataobject.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EvaluationResultError implements Serializable {
	private static final long serialVersionUID = 5612727563715885474L;
	private String errorName;
	private int errorCount;
	private List<EvaluationResultErrorsDetail> detail = new ArrayList<EvaluationResultErrorsDetail>();

	public EvaluationResultError() {
	}

	public EvaluationResultError(String errorName, int errorCount,
			List<EvaluationResultErrorsDetail> detail) {
		super();
		this.errorName = errorName;
		this.errorCount = errorCount;
		this.detail = detail;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public List<EvaluationResultErrorsDetail> getDetail() {
		return detail;
	}

	public void addDetail(EvaluationResultErrorsDetail errorsDetail) {
		detail.add(errorsDetail);
	}

	public void setDetail(List<EvaluationResultErrorsDetail> detail) {
		this.detail = detail;
	}

}
