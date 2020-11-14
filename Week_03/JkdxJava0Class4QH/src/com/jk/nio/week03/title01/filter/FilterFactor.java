package com.jk.nio.week03.title01.filter;

import com.jk.util.StringUtil;

public class FilterFactor {

	
	public static HttpRequestFilter filterFactor(String code){
       
		 if(StringUtil.isNullOrEmpty(code, false) && StringUtil.equals("cm", code, true))
		{
			return new HttpRequestFilterCmImp();
		}
		
		else if(StringUtil.isNullOrEmpty(code, false) && StringUtil.equals("doc", code, true)) {
			
			return new HttpRequestFilterDocImp();
			
		}
		else {
			
			return new HttpRequestFilterDefImp();
		}
    }
	
}
