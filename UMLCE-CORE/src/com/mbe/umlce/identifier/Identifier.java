package com.mbe.umlce.identifier;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.Result;

public interface Identifier {
	public Result identifyMistakes(ModelFile modelFile) throws Exception;
}
