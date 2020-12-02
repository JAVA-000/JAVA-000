package com.gjz.test.batch.insert;

import com.gjz.test.batch.insert.service.BatchInsertService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BatchInsertApplicationTests {

	@Resource
	BatchInsertService batchInsertService;

	@Test
	void testBatchInsertBatchRecord(){
		batchInsertService.batchInsertBatchRecord();
	}

	@Test
	void testBatchInsertOrderDataMoreValues(){
		batchInsertService.batchInsertOrderDataMoreValues();
	}


	@Test
	void contextLoads() {
	}



}
