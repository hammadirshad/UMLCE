package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.EvalErrorDetail;
import com.mbe.umlce.model.EvaluationError;

public interface EvalErrorDetailRepository extends CrudRepository<EvalErrorDetail,Integer> {
	
	List<EvalErrorDetail> findAll();

	EvalErrorDetail findById(int Id);
	
	List<EvalErrorDetail> findByError(EvaluationError error);

}
