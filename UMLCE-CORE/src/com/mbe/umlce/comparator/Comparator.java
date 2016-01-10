package com.mbe.umlce.comparator;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.Result;

public interface Comparator {
	public Result checkPlagiarism(ModelFile modelFile) throws Exception;
}
