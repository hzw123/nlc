package com.nlc.nraas.tools;

import java.util.*;

import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;

public class PageUtils {
	private static final int size=10;
	private static final Sort SORT=new Sort(Direction.ASC,"id");
	public static PageRequest getPageRequest(int page,int size,String sortType,String direction){
		Sort sort = null;

        if (sortType!=null&&sortType.length()>0) {
            return new PageRequest(page - 1, size);
        } else if (direction!=null&&direction.length()>0) {
            if (Direction.ASC.equals(direction)) {
                sort = new Sort(Direction.ASC, sortType);
            } else {
                sort = new Sort(Direction.DESC, sortType);
            }
            return new PageRequest(page - 1, size, sort);
        } else {
            sort = new Sort(Direction.ASC, sortType);
            return new PageRequest(page= - 1, size, sort);
        }
    }
	
    public static PageRequest getPageRequest(int page, int size, String sortType) {
    	return getPageRequest(page, page, sortType,null);
    }
    public static PageRequest getPageRequest(Pageable pageable) {
    	System.err.println("分页信息："+pageable);
    	if(pageable!=null){
    		int page=pageable.getPageNumber()>0?pageable.getPageNumber():0;
        	Sort sort=pageable.getSort()!=null?pageable.getSort():SORT;
        	int size_t = pageable.getPageSize()>0?pageable.getPageSize():size;
        	return new PageRequest(page,size_t,sort);
    	}else{
    		return new PageRequest(0, size);
    	}
    }
    public static Map<String, Object> getPageMap(Page<?> objPage) {
         Map<String, Object> resultMap = new HashMap<String, Object>();
    	 resultMap.put("list", objPage.getContent()); // 数据集合，符合查询条件的所有记录数据
         resultMap.put("total", objPage.getTotalElements()); // 总记录数
         resultMap.put("totalPage", objPage.getTotalPages()); // 总页数
         resultMap.put("page", objPage.getNumber()); // 当前页码
         resultMap.put("size", objPage.getSize()); // 每页显示数量
    	return resultMap;
    }
}
