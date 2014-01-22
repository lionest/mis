package com.htlab.mis.web.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.htlab.mis.common.StatsInfo;
import com.htlab.mis.service.IrrService;
import com.htlab.mis.util.DateUtil;

public class ChartAction extends BaseAction {
	private String[] paletteColors = new String[]{"008ED6","BBDC00","D64646","AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","8E468E","588526","B3AA00","9D080D","A186BE","CC6600","FDC689","ABA000","F26D7D","FFF200","0054A6","F7941C","CC3300","006600","663300","6DCFF6","386520","452871","525698","625863","745632","846100","110066","226633","889966","558833","CFEFDD","A67EF3","DD44CC","EE2211","3FEF4A","9CCFAA","4AEECC"};
	
	@Autowired
	private IrrService irrService;

	public String show(){
		return SUCCESS;
	}
	

//	series = "[{ data: [ {x: '0', y: 1.71,color:'#3D96AE' }, {x: '1', y: 1.11 }, {x: '2', y: 3.27,color:'#3DffAE'} ] } ]";
//	categories = "['A','综合','C']";
	
	
	public String showIrrChart(){
		List<Date> dateList = DateUtil.getBeforeMonths(new Date(), 12);
		List<StatsInfo> list = irrService.statsIrr(dateList.get(0), dateList.get(dateList.size()-1));
		
		setChartData(list);
		return JSON;
	}

	public String showSupplierChart(){
		
		List<Date> dateList = DateUtil.getBeforeMonths(new Date(), 12);
		
		List<StatsInfo> list = irrService.statsSupplier(dateList.get(0), dateList.get(dateList.size()-1));
		setChartData(list);
		return JSON;
	}
	
	private void setChartData(List<StatsInfo> list) {
		StringBuffer categories = new StringBuffer("[");
		StringBuffer series = new StringBuffer("[{ data: [");
		for(int i=0;i<list.size();i++){
			StatsInfo info = list.get(i);
			categories.append("'").append(info.getName()).append("'");
			series.append("{ y: ").append(info.getCount()).append(",color:'#").append(paletteColors[i]).append("' }");
			
			if(i<list.size() -1){
				categories.append(",");
				series.append(",");
			}
		}
		categories.append("]");
		series.append("] } ]");
		
		resultMap.put("categories", categories.toString());
		resultMap.put("series", series.toString());
	}
	
}
