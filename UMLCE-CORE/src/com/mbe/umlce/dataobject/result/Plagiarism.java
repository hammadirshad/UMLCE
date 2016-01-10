package com.mbe.umlce.dataobject.result;

public class Plagiarism {

	String file1;
	String file2;
	float PlagPersentage;

	public Plagiarism() {
	}

	public Plagiarism(String file1, String file2, float plagPersentage) {
		super();
		this.file1 = file1;
		this.file2 = file2;
		PlagPersentage = plagPersentage;
	}

	public String getFile1() {
		return file1;
	}

	public void setFile1(String file1) {
		this.file1 = file1;
	}

	public String getFile2() {
		return file2;
	}

	public void setFile2(String file2) {
		this.file2 = file2;
	}

	public float getPlagPersentage() {
		return PlagPersentage;
	}

	public void setPlagPersentage(float plagPersentage) {
		PlagPersentage = plagPersentage;
	}

}
